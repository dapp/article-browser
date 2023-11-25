package com.mdapp.athletictest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.contextaware.withContextAvailable
import androidx.lifecycle.lifecycleScope
import com.mdapp.athletictest.data.ApiClient
import com.mdapp.athletictest.data.League
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var response: List<League>
        lifecycleScope.launch {
            response = apiClient.getLeagues()

            withContext(Dispatchers.Main) {
                val text = findViewById<TextView>(R.id.hollo);
                text.text = response.toString();
            }
        }
    }
}