package com.mdapp.athletictest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mdapp.athletictest.data.Article


class ArticlesListAdapter(var articles: List<Article>) :
    RecyclerView.Adapter<ArticlesListAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.article_list_item, viewGroup, false)
        return ArticleViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.title.text = if (position < articles.size) articles[position].title else "wtf"
    }

    // total number of rows
    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView

        init {
            title = itemView.findViewById(R.id.title)
        }
    }
}