package com.mdapp.athletictest.presenter

import androidx.annotation.VisibleForTesting
import com.mdapp.athletictest.ArticleActivity.Companion.ARTICLE_ID_KEY
import com.mdapp.athletictest.ArticleActivity.Companion.LEAGUE_NAME_KEY
import com.mdapp.athletictest.model.ArticlesListModel
import com.mdapp.athletictest.utils.DataTransport
import com.mdapp.athletictest.utils.Launcher
import com.mdapp.athletictest.view.ArticleListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticlesListPresenter(private val model: ArticlesListModel,
                            private val launcher: Launcher,
                            private val dataTransport: DataTransport,
                            private val scope: CoroutineScope) {

    private var view: ArticleListView? = null

    fun attachView(view: ArticleListView) {
        this.view = view
    }

    fun onResume() {
        view?.showLoading()
        scope.launch {
            val articlesJob = async { model.loadArticles() }
            val authorsJob = async { model.loadAuthors() }
            awaitAll(articlesJob, authorsJob)
            model.combineArticlesWithAuthors()

            withContext(Dispatchers.Main) {
                present()
            }
        }
    }

    @VisibleForTesting
    fun present() {
        view?.hideLoading()
        val leagueNames = model.leagues.map { league -> league.name }.toMutableList()
        leagueNames.add(ALL_LABEL)

        view?.populateLeagueSpinner(leagueNames)
        view?.setLeagueSpinnerOnClickBehavior { position ->
            onLeagueSelected(position)
        }
        view?.setArticleOnClickBehavior { article ->
            dataTransport.put(ARTICLE_ID_KEY, article.id)
            dataTransport.put(article.id, article)
            dataTransport.put(LEAGUE_NAME_KEY, model.getSelectedLeagueName())
            launcher.launchArticle(article.id)
        }
    }

    @VisibleForTesting
    fun onLeagueSelected(leagueIndex: Int) {
        if (model.selectLeague(leagueIndex)) {
            view?.showLoading()
            view?.hideArticles()
            view?.hideEmptyState()

            scope.launch {
                model.loadArticles()
                model.combineArticlesWithAuthors()

                withContext(Dispatchers.Main) {
                    view?.hideLoading()
                    view?.setArticles(model.articles)
                    if (model.articles.isNotEmpty()) {
                        view?.showArticles()
                    } else {
                        view?.showEmptyState()
                    }
                }
            }
        }
    }

    companion object {
        const val ALL_LABEL = "ALL"
    }
}