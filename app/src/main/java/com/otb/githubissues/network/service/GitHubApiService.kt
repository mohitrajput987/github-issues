package com.otb.githubissues.network.service

import com.otb.githubissues.scene.issues.IssuesModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohit Rajput on 3/11/20.
 */
interface GitHubApiService {

    @GET("repos/{org_name}/{repo_name}/issues")
    suspend fun fetchIssues(@Path("org_name") organizationName: String, @Path("repo_name") repositoryName: String, @Query("state") state: String, @Query("page") page: Int, @Query("per_page") perPage: Int = 20): Response<List<IssuesModels.IssuesResponse>>
}