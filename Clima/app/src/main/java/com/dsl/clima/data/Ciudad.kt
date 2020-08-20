package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ciudad(@Json(name = "id") val id: Long,
                  @Json(name = "weather") val clima: Clima,
                  @Json(name = "name") val nombre: String,
                  @Json(name = "timezone") val zonaHoraria: Long,
                  @Json(name = "base") val base: String,
                  @Json(name = "dt") val fecha: Long,
                  @Json(name = "main") val pronostico: Pronostico) : Parcelable