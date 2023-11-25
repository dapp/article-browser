package com.mdapp.athletictest.data

import javax.inject.Inject

class ApiClient @Inject constructor(private val service: AthleticService) {
    suspend fun getLeagues() : List<League> = service.getLeagues()
    suspend fun getArticles(leagueId: String) : List<Article> = service.getArticlesForLeague(leagueId)
    suspend fun getAuthors() : List<Author> = service.getAuthors()
}