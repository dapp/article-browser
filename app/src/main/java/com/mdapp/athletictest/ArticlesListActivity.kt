package com.mdapp.athletictest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.model.ArticlesListModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesListActivity : AppCompatActivity() {
    @Inject lateinit var apiClient: ApiClient
    @Inject lateinit var model: ArticlesListModel

    val adapter = ArticlesListAdapter(listOf());
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articles_list)
    }

    override fun onResume() {
        super.onResume()
        var response: List<Article>
        val loadingIndicator = findViewById<View>(R.id.loadingIndicator)
        loadingIndicator.visibility = VISIBLE
        lifecycleScope.launch {
            response = model.getArticles()

            withContext(Dispatchers.Main) {
                val rv = findViewById<RecyclerView>(R.id.articles)
                rv.layoutManager = LinearLayoutManager(this@ArticlesListActivity);
                adapter.articles = response
                rv.adapter = adapter
                loadingIndicator.visibility = GONE
            }
        }
    }
}