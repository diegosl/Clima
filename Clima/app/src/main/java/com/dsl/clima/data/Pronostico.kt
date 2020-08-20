package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pronostico(@Json(name = "temp") val temp: Double,
                      @Json(name = "temp_min") val tempMin: Double,
                      @Json(name = "temp_max") val tempMax: Double,
                      @Json(name = "pressure") val presion: Double,
                      @Json(name = "humidity") val humedad: Double) : Parcelable