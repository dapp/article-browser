package com.mdapp.articlebrowser.model

import com.mdapp.articlebrowser.data.ApiClient
import com.mdapp.articlebrowser.data.Article
import javax.inject.Inject

class ArticleModel @Inject constructor() {
    @Inject lateinit var apiClient: ApiClient
    var article: Article? = null
    var articleId: String? = null
    var leagueName: String? = null

    suspend fun loadArticle(articleId: String) {
        article = apiClient.getArticle(articleId)
    }
}