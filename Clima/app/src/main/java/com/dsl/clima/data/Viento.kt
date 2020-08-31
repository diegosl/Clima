package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Viento(@Json(name = "speed") val velocidad: Double = 0.0) : Parcelable