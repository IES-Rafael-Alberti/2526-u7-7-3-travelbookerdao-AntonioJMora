package es.iesra.datos

import es.iesra.dominio.ReservaHotel
import java.io.File

class ReservaHotelDAO(private val rutaArchivo: String = "datos/hoteles.txt") : Dao<ReservaHotel> {

    init {
        val file = File(rutaArchivo)
        file.parentFile?.mkdirs()
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("id,descripcion,ubicacion,numeroNoches\n")
        }
    }

    override fun guardar(item: ReservaHotel): Boolean {
        return try {
            val file = File(rutaArchivo)
            val linea = "${item.id},${item.descripcion},${item.ubicacion},${item.numeroNoches}\n"
            file.appendText(linea)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun leerTodos(): List<ReservaHotel> {
        val lista = mutableListOf<ReservaHotel>()
        val file = File(rutaArchivo)
        if (!file.exists()) return lista

        val lineas = file.readLines().drop(1) // Ignorar cabecera
        for (linea in lineas) {
            if (linea.isBlank()) continue
            val datos = linea.split(",")
            if (datos.size >= 4) {
                try {
                    // Usamos el constructor estático. El ID y fecha se autogenerarán de la misma manera
                    val reserva = ReservaHotel.creaInstancia(
                        descripcion = datos[1],
                        ubicacion = datos[2],
                        numeroNoches = datos[3].toInt()
                    )
                    lista.add(reserva)
                } catch (e: Exception) {
                    println("Error al cargar hotel: " + e.message)
                }
            }
        }
        return lista
    }

    override fun eliminar(id: Int): Boolean {
        val elementos = leerTodos().toMutableList()
        val index = elementos.indexOfFirst { it.id == id }
        if (index != -1) {
            elementos.removeAt(index)
            reescribirArchivo(elementos)
            return true
        }
        return false
    }

    override fun obtenerInformacion(id: Int): ReservaHotel? {
        return leerTodos().find { it.id == id }
    }

    override fun actualizar(item: ReservaHotel): Boolean {
        val elementos = leerTodos().toMutableList()
        val index = elementos.indexOfFirst { it.id == item.id }
        if (index != -1) {
            elementos[index] = item
            reescribirArchivo(elementos)
            return true
        }
        return false
    }

    private fun reescribirArchivo(lista: List<ReservaHotel>) {
        val file = File(rutaArchivo)
        file.writeText("id,descripcion,ubicacion,numeroNoches\n")
        for (reserva in lista) {
            val linea = "${reserva.id},${reserva.descripcion},${reserva.ubicacion},${reserva.numeroNoches}\n"
            file.appendText(linea)
        }
    }
}
