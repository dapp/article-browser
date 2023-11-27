package com.mdapp.athletictest.di

import android.content.Context
import com.mdapp.athletictest.R
import com.mdapp.athletictest.data.AthleticService
import com.mdapp.athletictest.utils.DataTransport
import com.mdapp.athletictest.utils.Launcher
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//@Module
//@InstallIn(ActivityComponent::class)
//object ActivityModule {
//    @Provides
//    fun provideLauncher(@ActivityContext context: Context): Launcher = Launcher(context)
//}
