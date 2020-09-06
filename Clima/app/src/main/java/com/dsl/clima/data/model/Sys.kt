package com.dsl.clima.data.model

import com.squareup.moshi.Json

data class Sys(@Json(name = "country") val pais: String = "")