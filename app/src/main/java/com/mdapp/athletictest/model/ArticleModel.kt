package com.mdapp.athletictest.model

import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.data.Article
import javax.inject.Inject

class ArticleModel @Inject constructor() {
    @Inject lateinit var apiClient: ApiClient
    var article: Article? = null
    var articleId: String? = null


    suspend fun loadArticle(articleId: String) {
        article = apiClient.getArticle(articleId)
    }
}