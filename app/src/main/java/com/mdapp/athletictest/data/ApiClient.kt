package com.mdapp.athletictest.data

import javax.inject.Inject

class ApiClient @Inject constructor(private val service: AthleticService) {
    suspend fun getLeagues() : List<League> = service.getLeagues()
}