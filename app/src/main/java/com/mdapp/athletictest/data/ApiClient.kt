package com.mdapp.athletictest.data

import android.util.Log
import javax.inject.Inject

class ApiClient @Inject constructor(private val service: AthleticService) {
    suspend fun getLeagues() : List<League> = service.getLeagues()
    suspend fun getArticles(leagueId: String) : List<Article> {
        val articles =  service.getArticlesForLeague(leagueId)
        if (articles.isEmpty()) {
            Log.d("getArticles", "Empty Article List for League ID: $leagueId")
        }
        return articles
    }
    suspend fun getAuthors() : List<Author> = service.getAuthors()
    suspend fun getArticle(articleId: String) : Article = service.getArticle(articleId)
}