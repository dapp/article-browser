package com.mdapp.athletictest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mdapp.athletictest.ArticleActivity
import com.mdapp.athletictest.ArticleActivity.Companion.ARTICLE_ID_KEY
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Launcher @Inject constructor(@ActivityContext val context: Context) {
    fun launchArticle(articleId: String) {
        val intent = Intent(context, ArticleActivity::class.java)

        //TODO - use datatransport instead
        intent.extras?.putString(ARTICLE_ID_KEY, articleId)
        (context as Activity).startActivity(intent)
    }
}