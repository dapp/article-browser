package com.mdapp.athletictest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.data.Article
import com.mdapp.athletictest.model.ArticleModel
import com.mdapp.athletictest.presenter.ArticlePresenter
import com.mdapp.athletictest.view.ArticleView
import java.net.URLDecoder

class ArticlesListAdapter(var articles: List<Article>, var onClickBehavior: (Article) -> Unit) :
    RecyclerView.Adapter<ArticlesListAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.article_list_item, viewGroup, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if (articles[position].imageUrl.isEmpty()) {
            Log.d("image loading", "No image url for article title: ${articles[position].title}")
        }

        val articleModel = ArticleModel().apply { article = articles[position]
        articleId = articles[position].id}

        val articlePresenter = ArticlePresenter(articleModel)
        articlePresenter.attachView(ArticleView(holder.itemView as ViewGroup))
        articlePresenter.present()

        holder.itemView.setOnClickListener {
            onClickBehavior.invoke(articles[position])
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}