package com.mdapp.athletictest.data

import com.squareup.moshi.Json

data class League(
    val title: String?,
    val updatedAt: String?,
    val id: String,
    val name: String?,
    val deletedAt: String?,
    val sportType: String?,
    val shortName: String?,
    val createdAt: String?
)

data class Article(
    val id: String, val team: Team, val author: Author, val updatedAt: String,
    @Json(name = "imageUrlString")
    val imageUrl: String,
    val body: String,
    val league: League,
    val title: String,
    val deletedAt: String?,
    val createdAt: String) {

    fun combineWithAuthor(author: Author?): Article {
        return Article(this.id, this.team, author ?: this.author, this.updatedAt, this.imageUrl, this.body,
            this.league, this.title, this.deletedAt, this.createdAt)
    }
}

data class Team(val id: String)

data class Author(
    val id: String,
    val updatedAt: String?,
    val shortName: String?,
    @Json(name = "imageUrlString")
    val imageUrl: String?,
    val createdAt: String?,
    val name: String?,
    val deletedAt: String?,
    val title: String?
)

