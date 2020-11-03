package com.otb.githubissues.di

import com.otb.githubissues.network.service.GitHubApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideGitHubApi(retrofit: Retrofit) = retrofit.create(GitHubApiService::class.java)
    factory { provideGitHubApi(retrofit = get()) }
}