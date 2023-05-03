package com.fsa.to_do_app

import android.app.Application
import com.fsa.to_do_app.di.dataModule
import com.fsa.to_do_app.di.domainModule
import com.fsa.to_do_app.di.presentationModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(presentationModule, domainModule, dataModule)
        }
    }
}