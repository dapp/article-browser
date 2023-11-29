package com.mdapp.athletictest.model

import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.data.Author
import com.mdapp.athletictest.data.League
import kotlinx.coroutines.coroutineScope
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
    suspend fun loadArticles() = coroutineScope {
        articles.clear()

        if (leagues.isEmpty()) {
            leagues.addAll(apiClient.getLeagues())
        }

        if (selectedLeagueIndex >= 0) {
            articles.addAll(apiClient.getArticles(leagues[selectedLeagueIndex].id))
        } else {
            //Get all articles. this will take a while.
            //TODO - make all the calls in parallel.  When I make concurrent calls, the API always
            // times out.  It may have trouble handling the traffic
//            leagues.map {
//                async { articles.addAll(apiClient.getArticles(it.id)) }
//            }.awaitAll()
            leagues.forEach {
                articles.addAll(apiClient.getArticles(it.id))
            }
        }
    }

    suspend fun loadAuthors() {
        authors.clear()
        authors.addAll(getAuthors())
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
        if (selectedLeagueIndex != position && position < leagues.size) {
            selectedLeagueIndex = position
            return true
        } else if (position == leagues.size) {
            //get all articles
            selectedLeagueIndex = -1
            return true
        }

        return false
    }

    fun getSelectedLeagueName() = leagues[selectedLeagueIndex].name ?: ""
}