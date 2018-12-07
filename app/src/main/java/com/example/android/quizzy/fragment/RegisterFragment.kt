package com.example.android.quizzy.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.quizzy.QuizzyApplication
import com.example.android.quizzy.R
import com.example.android.quizzy.activity.MainActivity
import com.example.android.quizzy.model.Student
import com.example.android.quizzy.model.Teacher
import com.example.android.quizzy.model.User
import com.example.android.quizzy.util.Constants
import com.example.android.quizzy.util.Utils
import com.example.android.quizzy.viewModel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : Fragment() {

    private val TAG = "RegisterFragment"

    @Inject
    lateinit var loginViewModel : LoginViewModel

    private lateinit var transient: LoginFragment.LoginTransitionInterface

    private lateinit var auth : FirebaseAuth

    private lateinit var userInput : HashMap<String, Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transient = activity as LoginFragment.LoginTransitionInterface
        (activity?.application as QuizzyApplication).component.inject(this)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNextButtonOnClickListener()
        setClickLoginOnClickListener()
        setGoogleRegisterButtonOnClickListener()
    }

    private fun setGoogleRegisterButtonOnClickListener() {
        register_google_button.setOnClickListener{
            authUsingGoogle()
        }
    }

    private fun setNextButtonOnClickListener(){
        next_button.setOnClickListener {
            Log.d(TAG, "next button clicked")
            //Do not respond to user clicks for now
            it.isClickable = false

            //hide error text view
            register_error_text_view.visibility = View.GONE

            //check internet connection
            if(!Utils.isNetworkConnected(context)){
                showErrorMessage(R.string.no_internet_connection)
                return@setOnClickListener
            }

            //get email and password entered by user
            userInput = getUserInput()

            //check empty email or password
            if(!Utils.checkEmptyInputs(userInput[Constants.EMAIL_KEY] as String, userInput[Constants.PASSWORD_KEY] as String)){
                showErrorMessage(R.string.forgot_email_password)
                return@setOnClickListener
            }

            //check email validity
            if(!Utils.isValidEmail(userInput[Constants.EMAIL_KEY] as String)){
                showErrorMessage(R.string.invalid_email)
                return@setOnClickListener
            }

            //check password validity
            if(!Utils.isValidPassword(userInput[Constants.PASSWORD_KEY] as String)){
                showErrorMessage(R.string.invalid_password)
                return@setOnClickListener
            }

            performNext(userInput)
        }
    }

    private fun performNext(inputs : HashMap<String, Any>){
        if(register_radio_student.isChecked){
            transient.openFragment(RegisterStudentFragment.newInstance(inputs))
        }
        else if(register_radio_teacher.isChecked){
            transient.openFragment(RegisterTeacherFragment.newInstance(inputs))
        }
        else{
            showErrorMessage(R.string.check_student_teacher)
        }
    }

    private fun setClickLoginOnClickListener(){
        click_login_text_view.setOnClickListener{
            transient.openFragment(LoginFragment())
        }
    }

    /**
     * extract user inputs in a HashMap
     */
    private fun getUserInput() : HashMap<String, Any>{
        val userInput : HashMap<String, Any> = HashMap()
        userInput[Constants.EMAIL_KEY] = register_email_edit_text.text.trim().toString()
        userInput[Constants.PASSWORD_KEY] = register_password_edit_text.text.trim().toString()
        return userInput
    }

    /**
     * show error message to user
     */
    private fun showErrorMessage(messageId : Int){
        register_error_text_view.visibility = View.VISIBLE
        register_error_text_view.text = getString(messageId)
        next_button.isClickable = true
    }

    private fun authUsingGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(context!!, gso)

        val registerIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(registerIntent, Constants.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Got intent in onActivityResult")

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            Log.d(TAG, "Intent regards google login ")
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val result = GoogleSignIn.getSignedInAccountFromIntent(data)

            handleRegisterResult(result)
        }
    }

    private fun handleRegisterResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            registerWithCredential(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            e.printStackTrace()
            showErrorMessage(R.string.error_register)
        }
    }

    private fun registerWithCredential(account: GoogleSignInAccount) {
        Log.d(TAG, "Sign in with credential")
        //show loading progress bar
        register_loading_progress_bar.visibility = View.VISIBLE

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Sign in success
                        Log.d(TAG, "signInWithCredential:success")

                        val currentUser = auth.currentUser

                        loginViewModel.getUser(currentUser?.uid).subscribe({
                            Log.d(TAG, "got user")

                            //hide loading progress bar
                            register_loading_progress_bar.visibility = View.GONE

                            navigateUser(it)
                        } , {
                            Log.d(TAG, "got no user")

                            //hide loading progress bar
                            register_loading_progress_bar.visibility = View.GONE

                            val input : HashMap<String, Any> = HashMap()
                            input[Constants.ID_KEY] = currentUser?.uid as String

                            performNext(input)
                        })
                    } else {
                        // If sign in fails, display a message to the user.
                        //hide loading progress bar
                        register_loading_progress_bar.visibility = View.GONE

                        showErrorMessage(R.string.error_login)
                        Log.w(TAG, "signInWithCredential:failure", it.exception)
                    }
                }
    }

    private fun navigateUser(it: User?) {
        //Open Main Activity and attach teacher's number
        val intent = Intent(context, MainActivity::class.java)

        if (it is Teacher) {
            Log.d(TAG, "Got teacher with number : " + it.telephoneNumber)
            intent.putExtra(Constants.TELEPHONE_NUMBER_KEY, it.telephoneNumber)
        } else if (it is Student) {
            Log.d(TAG, "Got student with teacher's number : " + it.teacherTelephoneNumber)
            intent.putExtra(Constants.TEACHER_TELEPHONE_NUMBER_KEY, it.teacherTelephoneNumber)
            intent.putExtra(Constants.STUDENT_NAME_KEY, it.firstName + " " + it.lastName)
        } else {
            Log.d(TAG, "Neither a teacher nor a student")
            throw(Exception())
        }

        startActivity(intent)
        activity?.finish()
        Toast.makeText(activity, R.string.already_member, Toast.LENGTH_SHORT).show()
    }

}