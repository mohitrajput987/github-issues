package com.otb.githubissues

import android.app.Application
import com.otb.githubissues.di.apiModule
import com.otb.githubissues.di.databaseModule
import com.otb.githubissues.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GitHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GitHubApp)
            androidFileProperties()
            modules(provideModules())
        }
    }

    private fun provideModules() = listOf(retrofitModule, apiModule, databaseModule)
}