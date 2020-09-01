package com.dsl.clima.data

import com.squareup.moshi.Json

data class Nubosidad(@Json(name = "all") val all: Double = 0.0)