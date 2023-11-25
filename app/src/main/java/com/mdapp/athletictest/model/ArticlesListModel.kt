package com.mdapp.athletictest.model

import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.data.Author
import com.mdapp.athletictest.data.League
import javax.inject.Inject

class ArticlesListModel @Inject constructor() {
    @Inject lateinit var apiClient: ApiClient

    suspend fun getLeagues() : List<League>{
        return apiClient.getLeagues()
    }

    suspend fun getAuthors() : List<Author>{
        return apiClient.getAuthors()
    }

    suspend fun getArticles() : List<Article>{
        val allArticles = mutableListOf<Article>()
        val leagues = apiClient.getLeagues()
//        leagues.forEach {
//            allArticles.addAll(apiClient.getArticles(it.id))
//        }


        //Temporary to speed up dev
        allArticles.addAll(apiClient.getArticles(leagues[1].id))
        return allArticles
    }
}