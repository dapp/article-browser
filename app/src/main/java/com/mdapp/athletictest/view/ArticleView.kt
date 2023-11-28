package com.mdapp.athletictest.view

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mdapp.athletictest.R
import com.squareup.picasso.Picasso

class ArticleView(view: ViewGroup) {
    private val title: TextView
    private val articleImage: ImageView
    private val authorImage: ImageView
    private val authorName: TextView
    private val body: TextView?

    init {
        title = view.findViewById(R.id.title)
        articleImage = view.findViewById(R.id.articleImage)
        authorImage = view.findViewById(R.id.authorImage)
        authorName = view.findViewById(R.id.authorName)
        body = view.findViewById(R.id.body)
    }

    fun setTitle(titleText: String) {
        title.text = titleText
    }

    fun setBody(bodyText: String) {
        body?.text = bodyText
    }

    fun setAuthorName(authorNameText: String) {
        authorName.text = authorNameText
    }

    fun setArticleImageFromUrl(imageUrl: String) {
        Picasso.get().load(imageUrl).into(articleImage)
    }

    fun setAuthorImageFromUrl(imageUrl: String) {
        Picasso.get().load(imageUrl).into(authorImage)
    }

    fun showLoading() {
        //TODO
    }

    fun hideLoading() {
        //TODO
    }
}