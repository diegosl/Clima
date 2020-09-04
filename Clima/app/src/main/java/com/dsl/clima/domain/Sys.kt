package com.dsl.clima.domain

import com.squareup.moshi.Json

data class Sys(@Json(name = "country") val pais: String = "")