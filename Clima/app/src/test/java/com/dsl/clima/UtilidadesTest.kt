package com.dsl.clima

import com.dsl.clima.util.convertirGrado
import com.dsl.clima.util.convertirPorcentaje
import com.dsl.clima.util.convertirTempMinMax
import com.dsl.clima.util.unirCiudadPais
import org.junit.Assert
import org.junit.Test

class UtilidadesTest {
    @Test
    fun unirCiudadPais_isCorrect() {
        Assert.assertEquals(unirCiudadPais("Córdoba", "AR"), "Córdoba AR")
        Assert.assertEquals(unirCiudadPais("", ""), " ")
        //que pasa si viene vacios o con espacios tanto ciudad como pais?
    }

    @Test
    fun convertirGrado_isCorrect() {
        Assert.assertEquals(convertirGrado(20.3), "20°")
        Assert.assertEquals(convertirGrado(20.9), "21°")
        Assert.assertEquals(convertirGrado(-1.3), "-1°")
        Assert.assertEquals(convertirGrado(-1.6), "-2°")
    }

    @Test
    fun convertirPorcentaje_isCorrect() {
        Assert.assertEquals(convertirPorcentaje(55.0), "55%")
        Assert.assertEquals(convertirPorcentaje(34.6), "35%")
        Assert.assertEquals(convertirPorcentaje(0.0), "0%")
        Assert.assertEquals(convertirPorcentaje(-20.0), "20%")
    }

    @Test
    fun convertirTempMinMax_isCorrect() {
        Assert.assertEquals(convertirTempMinMax(15.3, 27.8), "15°/28°")
        Assert.assertEquals(convertirTempMinMax(-1.3, 10.3), "-1°/10°")
        Assert.assertEquals(convertirTempMinMax(20.6, 30.2), "21°/30°")
        Assert.assertEquals(convertirTempMinMax(15.3, 27.2), "15°/27°")
    }
}