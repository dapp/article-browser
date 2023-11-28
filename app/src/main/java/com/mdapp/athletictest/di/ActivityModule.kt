package com.mdapp.athletictest.di

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideScope(@ActivityContext context: Context): CoroutineScope = (context as LifecycleOwner).lifecycleScope
}
