package es.iesra.servicio

import es.iesra.datos.IReservaRepository
import es.iesra.dominio.ReservaHotel
import es.iesra.dominio.ReservaVuelo

/**
 * Implementación concreta de IReservaService.
 * Depende de la abstracción IReservaRepository, no de una implementación concreta.
 */
class ReservaService(private val repositorio: IReservaRepository) : IReservaService {

    override fun crearReservaVuelo(descripcion: String, origen: String, destino: String, horaVuelo: String) {
        val reservaVuelo = ReservaVuelo.creaInstancia(descripcion, origen, destino, horaVuelo)
        repositorio.agregar(reservaVuelo)
    }

    override fun crearReservaHotel(descripcion: String, ubicacion: String, numeroNoches: Int) {
        val reservaHotel = ReservaHotel.creaInstancia(descripcion, ubicacion, numeroNoches)
        repositorio.agregar(reservaHotel)
    }

    override fun listarReservas() = repositorio.obtenerTodas()
}