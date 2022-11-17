package com.vti.android.delegatedscopemanagement.testapp.di

import com.vti.android.delegatedscopemanagement.testapp.data.model.Counter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun counterProvide() = Counter()
}