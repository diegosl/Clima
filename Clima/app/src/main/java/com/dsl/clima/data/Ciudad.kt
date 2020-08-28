package com.dsl.clima.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Array

@Parcelize
data class Ciudad(@Json(name = "id") val id: Int = -1,
                  @Json(name = "weather") val clima: List<Clima> = ArrayList(),
                  @Json(name = "name") val nombre: String = "",
                  @Json(name = "timezone") val zonaHoraria: Int = -1,
                  @Json(name = "dt") val fecha: Int = -1,
                  @Json(name = "main") val pronostico: Pronostico = Pronostico()
) : Parcelable