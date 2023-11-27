package com.mdapp.athletictest

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.ArticleActivity.Companion.ARTICLE_ID_KEY
import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.model.ArticlesListModel
import com.mdapp.athletictest.utils.DataTransport
import com.mdapp.athletictest.utils.Launcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesListActivity : AppCompatActivity() {
    @Inject lateinit var apiClient: ApiClient
    @Inject lateinit var model: ArticlesListModel
    @Inject lateinit var launcher: Launcher
    @Inject lateinit var dataTransport: DataTransport

    private val adapter = ArticlesListAdapter(listOf()) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articles_list)
    }

    override fun onResume() {
        super.onResume()
        val loadingIndicator = findViewById<View>(R.id.loadingIndicator)
        loadingIndicator.visibility = VISIBLE
        lifecycleScope.launch {
            val articlesJob = async { model.loadArticles() }
            val authorsJob = async { model.loadAuthors() }
            awaitAll(articlesJob, authorsJob)
            model.combineArticlesWithAuthors()

            withContext(Dispatchers.Main) {
                val leagueSelector = findViewById<AppCompatSpinner>(R.id.leagueSelector)

                val leagueSelections = model.leagues.map { league -> league.name }
                val selectorAdapter = ArrayAdapter<String>(this@ArticlesListActivity, android.R.layout.simple_spinner_item, leagueSelections)
                selectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                leagueSelector.adapter = selectorAdapter
                leagueSelector.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?,
                                                view: View?,
                                                position: Int,
                                                id: Long) {
                        onLeagueSelected(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

                val rv = findViewById<RecyclerView>(R.id.articles)
                val emptyState = findViewById<View>(R.id.emptyState)
                if (model.articles.isNotEmpty()) {
                    rv.visibility = VISIBLE
                    emptyState.visibility = GONE
                    rv.layoutManager = LinearLayoutManager(this@ArticlesListActivity);
                    adapter.articles = model.articles
                    adapter.onClickBehavior = { article ->
                        dataTransport.put(ARTICLE_ID_KEY, article.id)
                        dataTransport.put(article.id, article)
                        launcher.launchArticle(article.id)
                    }

                    rv.adapter = adapter
                } else {
                    rv.visibility = GONE
                    emptyState.visibility = VISIBLE
                }

                loadingIndicator.visibility = GONE
            }
        }
    }

    fun onLeagueSelected(leagueIndex: Int) {
        //To Do, this fires automatically the first time
        if (model.selectLeague(leagueIndex)) {
            lifecycleScope.launch {
                val loadingIndicator = findViewById<View>(R.id.loadingIndicator)
                loadingIndicator.visibility = VISIBLE

                val emptyState = findViewById<View>(R.id.emptyState)
                emptyState.visibility = GONE
                model.loadArticles()
                model.combineArticlesWithAuthors()

                //TODO implement empty state
                withContext(Dispatchers.Main) {
                    loadingIndicator.visibility = GONE
                    adapter.notifyDataSetChanged()
                    val rv = findViewById<RecyclerView>(R.id.articles)
                    if (model.articles.isNotEmpty()) {
                        rv.visibility = VISIBLE
                        emptyState.visibility = GONE
                    } else {
                        rv.visibility = GONE
                        emptyState.visibility = VISIBLE
                    }
                }
            }
        }
    }
}