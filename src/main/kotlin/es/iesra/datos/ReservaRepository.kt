package es.iesra.datos

import es.iesra.dominio.Reserva

/**
 * Implementación en memoria del repositorio de reservas.
 */
class ReservaRepository : IReservaRepository {
    private val reservas = mutableListOf<Reserva>()

    override fun agregar(reserva: Reserva): Boolean {
        var agregado = false
        // Si no existe, se agrega la reserva a la lista.
        if (!reservas.contains(reserva)) {
            reservas.add(reserva)
            agregado = true
        }
        return agregado
    }

    override fun obtenerTodas(): List<Reserva> = reservas.toList()
}