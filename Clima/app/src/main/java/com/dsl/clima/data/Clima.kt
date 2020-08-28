package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clima(@Json(name = "main") val main: String = "",
                 @Json(name = "description") val descripcion: String = "",
                 @Json(name = "icon") val icono: String = "") : Parcelable