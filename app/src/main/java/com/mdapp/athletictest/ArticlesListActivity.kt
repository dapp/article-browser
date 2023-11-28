package com.mdapp.athletictest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mdapp.athletictest.model.ArticlesListModel
import com.mdapp.athletictest.presenter.ArticlesListPresenter
import com.mdapp.athletictest.utils.DataTransport
import com.mdapp.athletictest.utils.Launcher
import com.mdapp.athletictest.view.ArticleListView
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