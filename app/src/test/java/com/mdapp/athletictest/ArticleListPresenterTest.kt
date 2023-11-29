package com.mdapp.athletictest

import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.data.League
import com.mdapp.athletictest.model.ArticlesListModel
import com.mdapp.athletictest.presenter.ArticlesListPresenter
import com.mdapp.athletictest.presenter.ArticlesListPresenter.Companion.ALL_LABEL
import com.mdapp.athletictest.utils.DataTransport
import com.mdapp.athletictest.utils.Launcher
import com.mdapp.athletictest.view.ArticleListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ArticleListPresenterTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Mock lateinit var model: ArticlesListModel
    @Mock lateinit var launcher: Launcher
    @Mock lateinit var dataTransport: DataTransport
    @Mock lateinit var view: ArticleListView

    private val scope = TestScope(testDispatcher)
    private lateinit var sut: ArticlesListPresenter

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this);
        Dispatchers.setMain(testDispatcher)

        sut = ArticlesListPresenter(model, launcher, dataTransport, scope)
        sut.attachView(view)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onResume works`() = runTest {
        val spy = spy(sut)
        spy.onResume()
        advanceUntilIdle()
        verify(view).showLoading()
        verify(model).loadArticles()
        verify(model).loadAuthors()
        verify(model).combineArticlesWithAuthors()
        verify(spy).present()
    }

    @Test
    fun `presentation and selecting a league works correctly`() {
        val onLeagueSelectBehaviorCaptor = argumentCaptor<(Int) -> Unit>()

        whenever(model.leagues).thenReturn(getLeagues(3))
        sut.present()
        verify(view).hideLoading()

        val expectedLeagueItems = LEAGUE_NAMES.subList(0,3).toMutableList()
        expectedLeagueItems.add(ALL_LABEL)

        verify(view).populateLeagueSpinner(expectedLeagueItems)

        verify(view).setLeagueSpinnerOnClickBehavior(onLeagueSelectBehaviorCaptor.capture())
        val selectedLeagueIndex = 2
        onLeagueSelectBehaviorCaptor.firstValue.invoke(selectedLeagueIndex)
        verify(model).selectLeague(selectedLeagueIndex)
        //TODO - test coroutines in the behavior
    }

    @Test
    fun `verify clicking on articles works correctly`() {
        val onArticleClickBehaviorCaptor = argumentCaptor<(Article) -> Unit>()
        val leagueName = "leagueName"

        val articleId = "articleId"
        val article = mock<Article>()
        whenever(article.id).thenReturn(articleId)

        whenever(model.getSelectedLeagueName()).thenReturn(leagueName)
        whenever(model.leagues).thenReturn(getLeagues(3))
        sut.present()


        verify(view).setArticleOnClickBehavior(onArticleClickBehaviorCaptor.capture())
        onArticleClickBehaviorCaptor.firstValue.invoke(article)

        verify(dataTransport).put(ArticleActivity.ARTICLE_ID_KEY, articleId)
        verify(dataTransport).put(article.id, article)
        verify(dataTransport).put(ArticleActivity.LEAGUE_NAME_KEY, leagueName)
        verify(launcher).launchArticle(article.id)
    }

    private fun getLeagues(howMany: Int) : MutableList<League>? {
        val leagues = mutableListOf<League>()
        for (i in 0..<howMany) {
            leagues.add(getLeagueWithName(LEAGUE_NAMES[i]))
        }

        return leagues
    }

    private fun getLeagueWithName(name: String) : League =
        League(null, null, "id", name, null, null,
            null, null)

    companion object {
        val LEAGUE_NAMES = listOf("league1", "league2", "league3", "league4","league5")
    }
}