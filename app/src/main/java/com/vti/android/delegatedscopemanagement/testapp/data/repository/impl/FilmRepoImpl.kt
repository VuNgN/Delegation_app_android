package com.vti.android.delegatedscopemanagement.testapp.data.repository.impl

import com.vti.android.delegatedscopemanagement.testapp.data.repository.FilmRepo
import com.vti.android.delegatedscopemanagement.testapp.data.restful.ApiService
import javax.inject.Inject

class FilmRepoImpl @Inject constructor(private val apiService: ApiService) : FilmRepo {
    override suspend fun getFilm() = apiService.getFilms()
    override suspend fun getFilmById(id: String) = apiService.getFilmById(id)
}