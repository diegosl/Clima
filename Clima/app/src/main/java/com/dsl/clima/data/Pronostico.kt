package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class Pronostico(@Json(name = "temp") val temp: Double = 0.0,
                      @Json(name = "temp_min") val tempMin: Double = 0.0,
                      @Json(name = "temp_max") val tempMax: Double = 0.0,
                      @Json(name = "pressure") val presion: Double = 0.0,
                      @Json(name = "humidity") val humedad: Double = 0.0) : Parcelable