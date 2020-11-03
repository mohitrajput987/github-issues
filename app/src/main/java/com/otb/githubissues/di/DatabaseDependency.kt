package com.otb.githubissues.di

import com.otb.githubissues.database.AppDatabase
import com.otb.preference.SharedPreferenceUtils
import org.koin.dsl.module

val databaseModule = module {
    single { SharedPreferenceUtils.getInstance(get()) }
    single { AppDatabase.getInstance(get()) }
    single { get<AppDatabase>().issuesDao() }
}