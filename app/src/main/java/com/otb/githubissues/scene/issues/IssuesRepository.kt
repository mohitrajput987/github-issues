package com.otb.githubissues.scene.issues

import android.util.Log
import com.otb.githubissues.common.DataResult
import com.otb.githubissues.database.IssuesDao
import com.otb.githubissues.network.ErrorHandler
import com.otb.githubissues.network.service.GitHubApiService

class IssuesRepository(private val gitHubApiService: GitHubApiService, private val issuesDao: IssuesDao) : IssuesContract.Repository {
    companion object {
        const val PAGE_SIZE = 20
    }

    override suspend fun searchIssues(githubOwner: String, repoName: String, state: String, pageNumber: Int): DataResult<List<IssuesModels.Issue>> {
        val storedIssues = issuesDao.getIssues(githubOwner, repoName, state, ((pageNumber - 1) * PAGE_SIZE), PAGE_SIZE)
        if (storedIssues.isNotEmpty()) {
            val refreshInterval = 30 * 60 * 1000
            val oldestIssue = storedIssues.minBy { it.storedAt }
            if (System.currentTimeMillis() - oldestIssue!!.storedAt < refreshInterval) {
                return DataResult.DataSuccess(IssuesResponseToIssuesMapper().mapFromDatabase(storedIssues))
            } else {
                issuesDao.deleteIssues(githubOwner, repoName, state)
            }
        }

        val response = gitHubApiService.fetchIssues(githubOwner, repoName, state, pageNumber, PAGE_SIZE)
        return if (response.isSuccessful) {
            val issuesResponse = response.body()!!
            val issues = IssuesResponseToIssuesMapper().map(issuesResponse)
            issuesDao.insertAll(issues.map { IssuesModels.IssueEntity(it.id, it.patchUrl, it.title, it.number, it.userName, it.state, githubOwner, repoName, System.currentTimeMillis()) })
            DataResult.DataSuccess(issues)
        } else {
            DataResult.DataError(ErrorHandler.getError(response))
        }
    }
}