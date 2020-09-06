package com.dsl.clima.data.source.remote

import com.dsl.clima.data.model.DatosMeteorologicosActuales
import com.dsl.clima.data.model.DatosMeteorologicosActualesPrevistos
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
private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

/**
 * Se crea [API_KEY] para contener la KEY de la API Open Weather
 */
private const val API_KEY = "41176dfd0287c2f74238bb8996f5c104"

/**
 * Se crea [LANGUAGE] para contener el IDIOMA de la API Open Weather
 */
private const val LANGUAGE = "sp"

/**
 * Se crea [CELSIUS] para indicar la temperatura en Celsius de la API Open Weather
 */
private const val CELSIUS = "metric"

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
 * La interfaz [ClimaService] contiene los metodos para consultar las diferentes peticiones
 * al web service.
 *
 * @author Sosa Ludueña Diego
 * @version 1.0
 */
interface ClimaService {
    @GET("weather")
    fun getListaDatosMeteorologicosActuales(@Query("q") cityName: String = "Cordoba",
                                            @Query("lang") idioma: String = LANGUAGE,
                                            @Query("appid") apiKey: String = API_KEY):
            Deferred<List<DatosMeteorologicosActuales>>

    @GET("weather")
    fun getDatosMeteorologicosActuales(@Query("q") cityName: String = "Cordoba",
                                       @Query("lang") idioma: String = LANGUAGE,
                                       @Query("appid") apiKey: String = API_KEY):
            Deferred<DatosMeteorologicosActuales>

    @GET("onecall")
    fun getDatosMeteorologicosActualesPrevistos(@Query("lat") latitud: Double = 0.0,
                                                @Query("lon") longitud: Double = 0.0,
                                                @Query("units") unidad: String = CELSIUS,
                                                @Query("lang") idioma: String = LANGUAGE,
                                                @Query("appid") apiKey: String = API_KEY):
            Deferred<DatosMeteorologicosActualesPrevistos>
}

/**
 * Instacia [climaService] que crea comunicacion con API.
 */
object climaService {
    val retrofitService : ClimaService by lazy {
        retrofit.create(ClimaService::class.java)
    }
}