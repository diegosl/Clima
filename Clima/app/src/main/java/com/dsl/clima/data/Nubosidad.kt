package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nubosidad(@Json(name = "all") val all: Double = 0.0) : Parcelable