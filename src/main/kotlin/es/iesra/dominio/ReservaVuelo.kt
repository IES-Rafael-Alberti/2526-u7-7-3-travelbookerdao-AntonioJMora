package es.iesra.dominio

import java.time.LocalDateTime

/**
 * Clase que representa una Reserva de Vuelo.
 * Hereda de Reserva y agrega atributos específicos: origen, destino y hora de vuelo.
 */
class ReservaVuelo private constructor(
    id: Int,
    descripcion: String,
    val origen: String,
    val destino: String,
    val horaVuelo: String               // Se espera un formato válido (ej: "15:30")
) : Reserva(id, LocalDateTime.now(), descripcion) {

    // Se sobreescribe la propiedad detalle para incluir origen y destino.
    override val detalle: String
        get() = "$id - $origen -> $destino - $descripcion"

    // Sobrescritura de toString para mostrar la información formateada.
    override fun toString(): String {
        return "Reserva de Vuelo: $detalle [Hora de vuelo: $horaVuelo, Fecha creación: $fechaCreacion]"
    }

    companion object {
        // Generador de ids únicos para ReservaVuelo.
        private var contador: Int = 1

        /**
         * Método de clase para crear una nueva instancia de ReservaVuelo.
         * Valida el formato de hora mediante una expresión regular.
         */
        fun creaInstancia(descripcion: String, origen: String, destino: String, horaVuelo: String): ReservaVuelo {
            // Validación simple del formato de hora (HH:mm)
            val regex = Regex("^([01]?\\d|2[0-3]):[0-5]\\d\$")
            require(regex.matches(horaVuelo)) { "El formato de la hora debe ser HH:mm" }
            val reserva = ReservaVuelo(contador, descripcion, origen, destino, horaVuelo)
            contador++
            return reserva
        }
    }
}