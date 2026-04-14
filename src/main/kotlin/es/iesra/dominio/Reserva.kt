package es.iesra.dominio


import java.time.LocalDateTime

/**
 * Clase abstracta que define una Reserva.
 * Contiene los atributos comunes a todas las reservas.
 */
abstract class Reserva(
    val id: Int,                              // Identificador único, inmutable.
    val fechaCreacion: LocalDateTime,         // Fecha de creación, inmutable.
    val descripcion: String                   // Descripción de la reserva, no nula.
) {
    // Propiedad detalle que concatena id y descripción.
    open val detalle: String
        get() = "$id - $descripcion"

    // Método para mostrar el detalle de la reserva.
    open fun mostrarDetalle(): String = detalle
}
