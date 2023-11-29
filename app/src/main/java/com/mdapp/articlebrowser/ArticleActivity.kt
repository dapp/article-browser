package com.mdapp.articlebrowser

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mdapp.articlebrowser.model.ArticleModel
import com.mdapp.articlebrowser.presenter.ArticlePresenter
import com.mdapp.articlebrowser.utils.DataTransport
import com.mdapp.articlebrowser.view.ArticleView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {
    //TODO - create a model base class that this screen and the list screen can share
    @Inject lateinit var model: ArticleModel
    @Inject lateinit var dataTransport: DataTransport

    private var presenter: ArticlePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article)
        presenter = ArticlePresenter(model, dataTransport, lifecycleScope)
        presenter?.attachView(ArticleView(findViewById<View>(R.id.container) as ViewGroup))
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    companion object {
        const val ARTICLE_ID_KEY = "articleId"
        const val LEAGUE_NAME_KEY = "leagueName"
    }
}