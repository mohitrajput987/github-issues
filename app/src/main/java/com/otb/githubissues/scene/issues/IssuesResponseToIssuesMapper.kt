package com.otb.githubissues.scene.issues

class IssuesResponseToIssuesMapper {
    fun map(issuesResponse: List<IssuesModels.IssuesResponse>): List<IssuesModels.Issue> {
        return issuesResponse.map {
            IssuesModels.Issue(
                    it.id,
                    it.pullRequest?.patchUrl ?: "",
                    it.title,
                    it.number,
                    it.user.login,
                    it.state
            )
        }
    }

    fun mapFromDatabase(issuesEntities: List<IssuesModels.IssueEntity>): List<IssuesModels.Issue> {
        return issuesEntities.map {
            IssuesModels.Issue(
                    it.id,
                    it.patchUrl,
                    it.title,
                    it.number,
                    it.userName,
                    it.state
            )
        }
    }
}