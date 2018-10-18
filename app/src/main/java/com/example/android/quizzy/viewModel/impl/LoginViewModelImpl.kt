package com.example.android.quizzy.viewModel.impl

import android.util.Log
import com.example.android.quizzy.api.LoginApi
import com.example.android.quizzy.model.Student
import com.example.android.quizzy.model.Teacher
import com.example.android.quizzy.model.User
import com.example.android.quizzy.util.Constants
import com.example.android.quizzy.viewModel.LoginViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class LoginViewModelImpl : LoginViewModel {

    private val TAG = "LoginViewModelImpl"

    private val api : LoginApi

    constructor(api: LoginApi) {
        this.api = api
    }

    override fun register(body: HashMap<String, Any>): Completable {
        Log.d(TAG, "register executes")
        return api.registerInFirebaseAuth(body.get(Constants.EMAIL_KEY) as String, body.get(Constants.PASSWORD_KEY) as String)
                .flatMapCompletable { AuthResult ->
                    Log.d(TAG, "registered in fire-base auth")
                    val user : User

                    if(body.containsKey(Constants.TELEPHONE_NUMBER_KEY)){
                        Log.d(TAG, "registered as teacher")
                        user = Teacher(AuthResult.user.uid)
                    }

                    else if(body.containsKey(Constants.TEACHER_TELEPHONE_NUMBER_KEY)){
                        Log.d(TAG, "registered as student")
                        user = Student(AuthResult.user.uid)
                    }

                    else{
                        Log.d(TAG, "not a teacher neither a student")
                        throw Exception()
                    }

                    addUserInfo(user, body)

                    api.registerInFirebaseDatabase(user)

                }

    }

    override fun login(body: HashMap<String, String>): Maybe<FirebaseUser> {
        Log.d(TAG, "login executes")
        return api.login(body[Constants.EMAIL_KEY], body[Constants.PASSWORD_KEY]).flatMap { AuthResult ->
             Maybe.just(AuthResult.user)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun addUserInfo(user : User, body: HashMap<String, Any>){

        if(body.containsKey(Constants.FIRST_NAME_KEY)){
            user.firstName = body.get(Constants.FIRST_NAME_KEY) as String
        }
        else{
            Log.d(TAG, "no first name field")
            throw Exception()
        }

        if(body.containsKey(Constants.LAST_NAME_KEY)){
            user.lastName = body.get(Constants.LAST_NAME_KEY) as String
        }
        else{
            Log.d(TAG, "no last name field")
            throw Exception()
        }

        if(body.containsKey(Constants.CITY_KEY)){
            user.city = body.get(Constants.CITY_KEY) as String
        }


        if(user is Teacher){
            if(body.containsKey(Constants.TELEPHONE_NUMBER_KEY)){
                user.telephoneNumber = body.get(Constants.TELEPHONE_NUMBER_KEY) as String
            }
            else{
                Log.d(TAG, "no telephone number field")
                throw Exception()
            }

            if(body.containsKey(Constants.SUBJECT_KEY)){
                user.subject = body.get(Constants.SUBJECT_KEY) as String
            }
            else{
                Log.d(TAG, "no subject field")
                throw Exception()
            }
        }
        else if (user is Student){
            if(body.containsKey(Constants.ACADEMIC_YEAR_KEY)){
                user.academicYear = body.get(Constants.ACADEMIC_YEAR_KEY) as String
            }

            if(body.containsKey(Constants.TEACHER_TELEPHONE_NUMBER_KEY)){
                user.teacherTelephoneNumber = body.get(Constants.TEACHER_TELEPHONE_NUMBER_KEY) as String
            }
            else{
                Log.d(TAG, "no teacher telephone number field")
                throw Exception()
            }
        }

    }

}