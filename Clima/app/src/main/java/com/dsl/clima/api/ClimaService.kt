package com.dsl.clima.api

import com.dsl.clima.data.Ciudad
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Se crea constante [BASE_URL] para contener la URL del web service
 */
private const val BASE_URL = "api.openweathermap.org/data/2.5/"
private const val API_KEY = "41176dfd0287c2f74238bb8996f5c104"

/**
 * Se construye un objeto [moshi].
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Se construye un objeto [retrofit].
 * Configuracion de [retrofit] para parsear JSON y uso de corutina
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * La interfaz [ApiService] contiene los metodos para consultar las diferentes peticiones
 * al web service.
 *
 * @author Sosa Ludueña Diego
 * @version 1.0
 */
interface ClimaService {
    @GET("weather")
    fun getProducts(@Query("q") cityName: String = "London",
                    @Query("appid") apiKey: String = API_KEY):
            Deferred<List<Ciudad>>
}

/**
 * Instacia [apiService] que crea comunicacion con API.
 */
object apiService {
    val retrofitService : ClimaService by lazy {
        retrofit.create(ClimaService::class.java)
    }
}