package com.mdapp.athletictest.data

import retrofit2.http.GET

interface AthleticService {
    @GET("leagues")
    suspend fun getLeagues() : List<League>
}