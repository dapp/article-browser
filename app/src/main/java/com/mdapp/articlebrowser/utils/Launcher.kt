package com.mdapp.articlebrowser.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mdapp.articlebrowser.ArticleActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Launcher @Inject constructor(@ActivityContext val context: Context) {
    fun launchArticle() {
        val intent = Intent(context, ArticleActivity::class.java)
        (context as Activity).startActivity(intent)
    }
}