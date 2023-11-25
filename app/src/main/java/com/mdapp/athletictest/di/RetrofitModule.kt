package com.mdapp.athletictest.di

import android.content.Context
import com.mdapp.athletictest.R
import com.mdapp.athletictest.data.AthleticService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideAthleticsService(@ApplicationContext context: Context): AthleticService {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.baseUrl))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AthleticService::class.java)
    }
}
