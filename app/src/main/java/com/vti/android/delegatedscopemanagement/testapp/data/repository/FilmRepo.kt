package com.vti.android.delegatedscopemanagement.testapp.data.repository

import com.vti.android.delegatedscopemanagement.testapp.data.model.Film

interface FilmRepo {
    suspend fun getFilm(): List<Film>
    suspend fun getFilmById(id: String): Film
}