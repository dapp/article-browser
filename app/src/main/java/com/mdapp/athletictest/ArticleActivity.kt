package com.mdapp.athletictest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.model.ArticleModel
import com.mdapp.athletictest.utils.DataTransport
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLDecoder
import javax.inject.Inject

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {
    //TODO - create a model base class that this screen and the list screen can share
    @Inject lateinit var model: ArticleModel
    @Inject lateinit var dataTransport: DataTransport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article)

        //TODO - use DT for the ID as well
        model.articleId = dataTransport.get(ARTICLE_ID_KEY) as? String
        model.articleId?.let {
            model.article = dataTransport.get(it) as? Article
        } ?: kotlin.run {
            Log.e("ArticleActivity", "No article id provided")
        }

        val title = findViewById<TextView>(R.id.title)
        val articleImage = findViewById<ImageView>(R.id.articleImage)
        val authorImage = findViewById<ImageView>(R.id.authorImage)
        val authorName = findViewById<TextView>(R.id.authorName)
        val body = findViewById<TextView>(R.id.body)

        //TODO - create a shared resource to bing the article data for this screen and the list items
        title.text = model.article?.title
        authorName.text = model.article?.author?.name
        Picasso.get().load(
            URLDecoder.decode(model.article?.imageUrl ?: "http://www.google.com", "UTF-8")).into(articleImage)
        Picasso.get().load(model.article?.author?.imageUrl).into(authorImage)
        body.text = model.article?.body
    }

    override fun onResume() {
        super.onResume()
        //If we're coming from a push notification or web link, we won't have article data yet
        //TODO - fix reloading on back
        if (model.article == null && model.articleId != null) {
            val loadingIndicator = findViewById<View>(R.id.loadingIndicator)
            loadingIndicator.visibility = VISIBLE
            lifecycleScope.launch {
                //TODO - we won't have author data, so we need to consolidate the code to
                // get the article, and the authors and combine them
                model.articleId?.let { model.loadArticle(it) }
            }
        }
    }

    companion object {
        const val ARTICLE_ID_KEY = "articleId"
    }
}