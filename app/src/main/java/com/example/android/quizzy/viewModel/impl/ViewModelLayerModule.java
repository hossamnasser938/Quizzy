package com.example.android.quizzy.viewModel.impl;

import com.example.android.quizzy.api.LoginApi;
import com.example.android.quizzy.viewModel.LoginViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelLayerModule {

    @Provides
    @Singleton
    LoginViewModel provideLoginViewModel(LoginApi api){
        return new LoginViewModelImpl(api);
    }

}
