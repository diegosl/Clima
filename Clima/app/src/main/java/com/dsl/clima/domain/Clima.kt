package com.dsl.clima.domain

import com.squareup.moshi.Json

data class Clima(@Json(name = "description") val descripcion: String = "",
                 @Json(name = "icon") val icono: String = "")