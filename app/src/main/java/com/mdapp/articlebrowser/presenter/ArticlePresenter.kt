package com.mdapp.articlebrowser.presenter

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.mdapp.articlebrowser.ArticleActivity.Companion.ARTICLE_ID_KEY
import com.mdapp.articlebrowser.ArticleActivity.Companion.LEAGUE_NAME_KEY
import com.mdapp.articlebrowser.data.Article
import com.mdapp.articlebrowser.model.ArticleModel
import com.mdapp.articlebrowser.utils.DataTransport
import com.mdapp.articlebrowser.view.ArticleView
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
        model.leagueName?.let { view?.setToolbarTitle(it) }
    }

    @VisibleForTesting
    fun getArticle() {
        model.articleId = dataTransport?.get(ARTICLE_ID_KEY) as? String
        model.leagueName = dataTransport?.get(LEAGUE_NAME_KEY) as? String
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