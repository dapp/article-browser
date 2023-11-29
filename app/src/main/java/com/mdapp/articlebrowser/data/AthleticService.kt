package com.mdapp.articlebrowser.data

import retrofit2.http.GET
import retrofit2.http.Path

interface AthleticService {
    @GET("leagues")
    suspend fun getLeagues(): List<League>

    @GET("leagues/{leagueId}")
    suspend fun getLeague(@Path("leagueId") leagueId: String): League

    @GET("leagues/{leagueId}/articles")
    suspend fun getArticlesForLeague(@Path("leagueId") leagueId: String): List<Article>

    @GET("articles/{articleId}")
    suspend fun getArticle(@Path("articleId") articleId: String): Article

    @GET("authors/")
    suspend fun getAuthors(): List<Author>

    @GET("authors/{authorId}")
    suspend fun getAuthor(@Path("authorId") authorId: String): Author
}