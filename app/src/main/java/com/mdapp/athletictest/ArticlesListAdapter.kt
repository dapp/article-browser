package com.mdapp.athletictest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.data.Article
import com.squareup.picasso.Picasso
import java.net.URLDecoder
import java.net.URLEncoder


class ArticlesListAdapter(var articles: List<Article>) :
    RecyclerView.Adapter<ArticlesListAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.article_list_item, viewGroup, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if (articles[position].imageUrl.isNullOrEmpty()) {
            Log.d("image loading", "No image url for article title: ${articles[position].title}")
        } else {
            Log.d("image loading", "decoded image url is ${URLDecoder.decode(articles[position].imageUrl)}")
        }


        holder.title.text = articles[position].title
        holder.authorName.text = articles[position].author.name
        Picasso.get().load(URLDecoder.decode(articles[position].imageUrl ?: "http://www.google.com", "UTF-8")).into(holder.articleImage)
        Picasso.get().load(articles[position].author.imageUrl).into(holder.authorImage)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val articleImage: ImageView
        val authorImage: ImageView
        val authorName: TextView

        init {
            title = itemView.findViewById(R.id.title)
            articleImage = itemView.findViewById(R.id.articleImage)
            authorImage = itemView.findViewById(R.id.authorImage)
            authorName = itemView.findViewById(R.id.authorName)
        }
    }
}