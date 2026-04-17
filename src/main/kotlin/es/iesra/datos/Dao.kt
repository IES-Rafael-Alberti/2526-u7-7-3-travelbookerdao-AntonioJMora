package es.iesra.datos

interface Dao<T> {
    fun guardar(item: T): Boolean
    fun eliminar(id: Int): Boolean
    fun obtenerInformacion(id: Int): T?
    fun actualizar(item: T): Boolean
    fun leerTodos(): List<T>
}