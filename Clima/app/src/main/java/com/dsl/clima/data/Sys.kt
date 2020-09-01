package com.dsl.clima.data

import com.squareup.moshi.Json

data class Sys(@Json(name = "country") val pais: String = "")