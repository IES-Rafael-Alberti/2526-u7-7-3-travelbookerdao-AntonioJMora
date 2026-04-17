package es.iesra.datos

import es.iesra.dominio.Reserva
import es.iesra.dominio.ReservaHotel
import es.iesra.dominio.ReservaVuelo

/**
 * Implementación en memoria del repositorio de reservas.
 */
class ReservaRepository(private val vueloDAO: ReservaVueloDAO, private val hotelDAO : ReservaHotelDAO) : IReservaRepository {

    private val reservas = mutableListOf<Reserva>()

    init {
        // Cargar los datos desde los archivos al inicializar el repositorio
        reservas.addAll(vueloDAO.leerTodos())
        reservas.addAll(hotelDAO.leerTodos())
    }

    override fun agregar(reserva: Reserva): Boolean {
        var agregado = false
        // Si no existe, se agrega la reserva a la lista.
        if (!reservas.contains(reserva)) {
            reservas.add(reserva)
            // Persistir los datos en sus respectivos DAO
            when (reserva) {
                is ReservaVuelo -> vueloDAO.guardar(reserva)
                is ReservaHotel -> hotelDAO.guardar(reserva)
            }
            agregado = true
        }
        return agregado
    }

    override fun obtenerTodas(): List<Reserva> = reservas.toList()
    override fun obtenerPorId(id: Int): Reserva? {
        return obtenerTodas().find { it.id == id }
    }

    fun obtenerInformacion(id: Int): Reserva? {
        var res: Reserva? = null
        for (reserva in reservas) {
            if (reserva.id == id) {
                res = reserva
            }
        }
        return res
    }

    override fun eliminar(id: Int): Boolean {
        val reserva = obtenerInformacion(id)
        if (reserva != null) {
            reservas.remove(reserva)
            when (reserva) {
                is ReservaVuelo -> vueloDAO.eliminar(id)
                is ReservaHotel -> hotelDAO.eliminar(id)
            }
            return true
        }
        return false
    }

    override fun actualizar(reserva: Reserva): Boolean {
        return when (reserva) {
            is ReservaVuelo -> vueloDAO.actualizar(reserva)
            is ReservaHotel -> hotelDAO.actualizar(reserva)
            else -> false
        }
    }
}