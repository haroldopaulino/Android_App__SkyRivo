package com.harold.skyrivo

import android.app.Application
import com.harold.skyrivo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkyRivoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SkyRivoApp)
            modules(appModule)
        }
    }
}
