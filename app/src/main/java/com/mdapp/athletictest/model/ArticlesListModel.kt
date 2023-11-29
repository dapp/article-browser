package com.mdapp.athletictest.model

import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.data.Author
import com.mdapp.athletictest.data.League
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesListModel @Inject constructor() {
    @Inject lateinit var apiClient: ApiClient
    private var selectedLeagueIndex = 1 //the fist league has no articles

    suspend fun getLeagues(): List<League> {
        return apiClient.getLeagues()
    }

    suspend fun getAuthors(): List<Author> {
        return apiClient.getAuthors()
    }

    suspend fun getArticles(): List<Article> {
        val allArticles = mutableListOf<Article>()
        val leagues = apiClient.getLeagues()
//        leagues.forEach {
//            allArticles.addAll(apiClient.getArticles(it.id))
//        }


        //Temporary to speed up dev
        allArticles.addAll(apiClient.getArticles(leagues[1].id))
        return allArticles
    }

    val articles = mutableListOf<Article>()
    val authors = mutableListOf<Author>()
    val leagues = mutableListOf<League>()
    suspend fun loadArticles() {
        articles.clear()

        if (leagues.isEmpty()) {
            leagues.addAll(apiClient.getLeagues())
        }

        articles.addAll(apiClient.getArticles(leagues[selectedLeagueIndex].id))
    }

    suspend fun loadAuthors() {
        authors.clear()
        authors.addAll(getAuthors())
    }

    fun loadData(scope: CoroutineScope) {
        scope.launch {
            var articleSummaries: List<Article> = listOf()
            val articlesJob = async { articleSummaries = getArticles() }
            val authorsJob = async { loadAuthors() }

            awaitAll(articlesJob, authorsJob)
            articles.addAll(articleSummaries.map { article ->
                val author = authors.find { author ->
                    author.id == article.author.id
                }

                article.combineWithAuthor(author)
            })
        }
    }

    fun combineArticlesWithAuthors() {
        for (i in articles.indices) {
            val author = authors.find { author ->
                author.id == articles[i].author.id
            }

            articles[i] = articles[i].combineWithAuthor(author)
        }
    }

    fun selectLeague(position: Int) : Boolean {
        if (selectedLeagueIndex != position) {
            selectedLeagueIndex = position
            return true
        }

        return false
    }

    fun getSelectedLeagueName() = leagues[selectedLeagueIndex].name ?: ""
}