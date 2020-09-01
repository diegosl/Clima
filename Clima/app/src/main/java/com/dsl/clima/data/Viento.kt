package com.dsl.clima.data

import com.squareup.moshi.Json

data class Viento(@Json(name = "speed") val velocidad: Double = 0.0)