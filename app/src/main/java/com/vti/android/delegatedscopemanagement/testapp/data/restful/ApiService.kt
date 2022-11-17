package com.vti.android.delegatedscopemanagement.testapp.data.restful

import com.vti.android.delegatedscopemanagement.testapp.data.model.Film
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("films")
    suspend fun getFilms(): List<Film>

    @GET("films/{id}")
    suspend fun getFilmById(@Path("id") id: String): Film
}