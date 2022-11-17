package com.vti.android.delegatedscopemanagement.testapp.di

import com.vti.android.delegatedscopemanagement.testapp.data.repository.FilmRepo
import com.vti.android.delegatedscopemanagement.testapp.data.repository.impl.FilmRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideFilmRepo(impl: FilmRepoImpl): FilmRepo
}