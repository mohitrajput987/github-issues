package com.otb.githubissues.scene.issues

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

class IssuesModels {
    @Entity(tableName = "issue")
    data class IssueEntity(
            @PrimaryKey
            val id: String,

            @ColumnInfo(name = "patch_url")
            val patchUrl: String,

            @ColumnInfo(name = "title")
            val title: String,

            @ColumnInfo(name = "number")
            val number: String,

            @ColumnInfo(name = "user_name")
            val userName: String,

            @ColumnInfo(name = "state")
            val state: String,

            @ColumnInfo(name = "github_owner")
            val githubOwner: String,

            @ColumnInfo(name = "github_repo_name")
            val githubRepoName: String,

            @ColumnInfo(name = "storedAt")
            val storedAt: Long
    )

    data class Issue(
            val id: String,
            val patchUrl: String,
            val title: String,
            val number: String,
            val userName: String,
            val state: String
    )

    data class IssuesResponse(
            @SerializedName("id")
            val id: String,

            @SerializedName("url")
            val url: String,

            @SerializedName("number")
            val number: String,

            @SerializedName("title")
            val title: String,

            @SerializedName("state")
            val state: String,

            @SerializedName("user")
            val user: UserResponse,

            @SerializedName("pull_request")
            val pullRequest: PullRequestResponse?
    )

    data class UserResponse(
            @SerializedName("id")
            val id: String,

            @SerializedName("login")
            val login: String,

            @SerializedName("https://avatars1.githubusercontent.com/u/799346?v=4")
            val imageUrl: String,

            @SerializedName("site_admin")
            val isSiteAdmin: Boolean
    )

    data class PullRequestResponse(
            @SerializedName("url")
            val url: String?,

            @SerializedName("patch_url")
            val patchUrl: String?,

            @SerializedName("diff_url")
            val diffUrl: String?
    )
}

enum class IssueState(val key: String) {
    OPEN("open"), CLOSE("closed")
}