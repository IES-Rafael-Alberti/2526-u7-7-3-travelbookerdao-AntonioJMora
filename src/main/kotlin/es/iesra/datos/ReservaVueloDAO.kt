package es.iesra.datos

import es.iesra.dominio.ReservaVuelo
import java.io.File

class ReservaVueloDAO(private val rutaArchivo: String = "datos/vuelos.txt") : Dao<ReservaVuelo> {

    init {
        val file = File(rutaArchivo)
        file.parentFile?.mkdirs()
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("id,descripcion,origen,destino,horaVuelo\n")
        }
    }

    override fun guardar(item: ReservaVuelo): Boolean {
        return try {
            val file = File(rutaArchivo)
            val linea = "${item.id},${item.descripcion},${item.origen},${item.destino},${item.horaVuelo}\n"
            file.appendText(linea)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun leerTodos(): List<ReservaVuelo> {
        val lista = mutableListOf<ReservaVuelo>()
        val file = File(rutaArchivo)
        if (!file.exists()) return lista

        val lineas = file.readLines().drop(1) // Ignorar cabecera
        for (linea in lineas) {
            if (linea.isBlank()) continue
            val datos = linea.split(",")
            if (datos.size >= 5) {
                try {
                    // Usamos el constructor estático. El ID y fecha se autogenerarán.
                    val reserva = ReservaVuelo.creaInstancia(
                        descripcion = datos[1],
                        origen = datos[2],
                        destino = datos[3],
                        horaVuelo = datos[4]
                    )
                    lista.add(reserva)
                } catch (e: Exception) {
                    println("Error al cargar vuelo: " + e.message)
                }
            }
        }
        return lista
    }

    override fun eliminar(id: Int): Boolean {
        var elementos = leerTodos().toMutableList()
        var encontrado = false
        var posicionParaBorrar = -1
        for (i in 0 until elementos.size) {
            if (elementos[i].id == id) {
                posicionParaBorrar = i
                encontrado = true
            }
        }
        if (encontrado == true) {
            elementos.removeAt(posicionParaBorrar)
            reescribirArchivo(elementos)
            return true
        }
        return false
    }

    override fun obtenerInformacion(id: Int): ReservaVuelo? {
        var miReserva: ReservaVuelo? = null
        var todasLasReservas = leerTodos()
        for (r in todasLasReservas) {
            if (r.id == id) {
                miReserva = r
            }
        }
        return miReserva
    }

    override fun actualizar(item: ReservaVuelo): Boolean {
        var elementos = leerTodos().toMutableList()
        var loHaEncontrado = false
        for (i in 0 until elementos.size) {
            if (elementos[i].id == item.id) {
                elementos[i] = item
                loHaEncontrado = true
            }
        }
        if (loHaEncontrado == true) {
            reescribirArchivo(elementos)
            return true
        }
        return false
    }

    private fun reescribirArchivo(lista: List<ReservaVuelo>) {
        val file = File(rutaArchivo)
        file.writeText("id,descripcion,origen,destino,horaVuelo\n")
        for (reserva in lista) {
            val linea = "${reserva.id},${reserva.descripcion},${reserva.origen},${reserva.destino},${reserva.horaVuelo}\n"
            file.appendText(linea)
        }
    }
}
