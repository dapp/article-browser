package com.mdapp.articlebrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mdapp.articlebrowser.model.ArticlesListModel
import com.mdapp.articlebrowser.presenter.ArticlesListPresenter
import com.mdapp.articlebrowser.utils.DataTransport
import com.mdapp.articlebrowser.utils.Launcher
import com.mdapp.articlebrowser.view.ArticleListView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesListActivity : AppCompatActivity() {
    @Inject lateinit var model: ArticlesListModel
    @Inject lateinit var launcher: Launcher
    @Inject lateinit var dataTransport: DataTransport

    private var presenter: ArticlesListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articles_list)
        presenter = ArticlesListPresenter(model, launcher, dataTransport, lifecycleScope)
        presenter?.attachView(ArticleListView(findViewById(R.id.container)))
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }
}