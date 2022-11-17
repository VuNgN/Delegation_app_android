package com.vti.android.delegatedscopemanagement.testapp.data.model

import com.squareup.moshi.Json

data class Film
    (
    val id: String,
    val title: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_title_romanised")
    val originalTitleRomanised: String,
    val image: String,
    @Json(name = "movie_banner")
    val movieBanner: String,
    val description: String,
    val director: String,
    val producer: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "running_time")
    val runningTime: String,
    @Json(name = "rt_score")
    val rtScore: String,
    val people: List<String>,
    val species: List<String>,
    val locations: List<String>,
    val vehicles: List<String>,
    val url: String
)