package com.mdapp.athletictest.view

import android.annotation.SuppressLint
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.ArticlesListAdapter
import com.mdapp.athletictest.R
import com.mdapp.athletictest.data.Article
import kotlinx.coroutines.CoroutineScope

class ArticleListView(view: ViewGroup) {
    private val leagueSelector: AppCompatSpinner
    private val recyclerView: RecyclerView
    private val emptyState :View
    private val loadingIndicator : View
    private val selectorAdapter: ArrayAdapter<String>
    private val scope: CoroutineScope

    private val adapter = ArticlesListAdapter(listOf()) {}

    init {
        scope = (view.context as LifecycleOwner).lifecycleScope
        loadingIndicator = view.findViewById(R.id.loadingIndicator)
        leagueSelector = view.findViewById(R.id.leagueSelector)

        selectorAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item)
        selectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        leagueSelector.adapter = selectorAdapter

        recyclerView = view.findViewById(R.id.articles)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context);

        emptyState = view.findViewById<View>(R.id.emptyState)
    }

    fun showLoading() {
        loadingIndicator.visibility = VISIBLE
    }

    fun hideLoading() {
        loadingIndicator.visibility = GONE
    }

    fun showEmptyState() {
        emptyState.visibility = VISIBLE
    }

    fun hideEmptyState() {
        emptyState.visibility = GONE
    }

    fun showArticles() {
        recyclerView.visibility = VISIBLE
    }

    fun hideArticles() {
        recyclerView.visibility = GONE
    }

    fun populateLeagueSpinner(leagues: List<String?>) {
        selectorAdapter.clear()
        selectorAdapter.addAll(leagues)
        selectorAdapter.notifyDataSetChanged()
    }

    fun setLeagueSpinnerOnClickBehavior(behavior: (Int) -> Unit) {
        leagueSelector.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long) {
                behavior.invoke(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArticles(articles: List<Article>) {
        adapter.articles = articles
        adapter.notifyDataSetChanged()
    }

    fun setArticleOnClickBehavior(behavior: (Article) -> Unit) {
        adapter.onClickBehavior = { article ->
            behavior.invoke(article)
        }
    }
}