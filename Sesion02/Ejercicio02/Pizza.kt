package com.example.sesion02ejercicio02

/**
 * Modelo de datos para cada pizza.
 *
 * @param nombre  Nombre de la pizza que se muestra al usuario.
 * @param imagen  ID del recurso drawable (foto de la pizza en res/drawable/).
 * @param descripcion Breve descripción de los ingredientes principales.
 */
data class Pizza(
    val nombre      : String,
    val imagen      : Int,
    val descripcion : String
)
