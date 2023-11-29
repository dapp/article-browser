package com.mdapp.athletictest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mdapp.athletictest.ArticleActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Launcher @Inject constructor(@ActivityContext val context: Context) {
    fun launchArticle() {
        val intent = Intent(context, ArticleActivity::class.java)
        (context as Activity).startActivity(intent)
    }
}