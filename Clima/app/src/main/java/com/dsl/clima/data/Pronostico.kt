package com.dsl.clima.data

data class Pronostico(val id: Int,
                      val ubicacion: String,
                      val hora: String,
                      val temperatura: String,
                      val imagen: String,
                      val condicion: String)