package com.example.android.quizzy;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    //TODO: Inject activities and fragments needing view models
}
