package com.mdapp.athletictest.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.mdapp.athletictest.ArticleActivity.Companion.ARTICLE_ID_KEY
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.model.ArticleModel
import com.mdapp.athletictest.utils.DataTransport
import com.mdapp.athletictest.view.ArticleView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticlePresenter(private val model: ArticleModel,
    private val dataTransport: DataTransport? = null,
    private val scope: CoroutineScope? = null) {

    private var view: ArticleView? = null

    fun attachView(view: ArticleView) {
        this.view = view
    }

    fun onResume() {
        getArticle()
        present()
    }
    fun present() {
        model.article?.title?.let { view?.setTitle(it) }
        model.article?.author?.name?.let { view?.setAuthorName(it) }
        model.article?.imageUrl?.let { view?.setArticleImageFromUrl(it) }
        model.article?.author?.imageUrl?.let { view?.setAuthorImageFromUrl(it) }
        model.article?.body?.let { view?.setBody(it) }
    }

    @VisibleForTesting
    fun getArticle() {
        model.articleId = dataTransport?.get(ARTICLE_ID_KEY) as? String
        model.articleId?.let {
            model.article = dataTransport?.get(it) as? Article
        } ?: kotlin.run {
            Log.e("ArticleActivity", "No article id provided")
        }

        if (model.article == null && model.articleId != null) {
            view?.showLoading()
            scope?.launch {
                //TODO - we won't have author data, so we need to consolidate the code to
                // get the article, and the authors and combine them
                model.articleId?.let { model.loadArticle(it) }
            }
        }
    }
}