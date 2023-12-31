package com.mdapp.articlebrowser.model

import com.mdapp.articlebrowser.data.ApiClient
import com.mdapp.articlebrowser.data.Article
import com.mdapp.articlebrowser.data.Author
import com.mdapp.articlebrowser.data.League
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ArticlesListModel @Inject constructor() {
    @Inject lateinit var apiClient: ApiClient
    private var selectedLeagueIndex = 1 //the fist league has no articles
    private val authors = mutableListOf<Author>()

    val articles = mutableListOf<Article>()
    val leagues = mutableListOf<League>()

    private suspend fun getAuthors(): List<Author> {
        return apiClient.getAuthors()
    }

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
            // times out.  It may have trouble handling the traffic.  Leaving concurrent call code
            // in for an imaginary future where we fix the API
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