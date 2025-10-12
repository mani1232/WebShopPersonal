package cc.worldmandia.webshoppersonal.db

interface BaseStorage<T> {
    fun get(key: String): String?
    fun put(key: String, value: T)
    fun remove(key: String)
    fun clear(key: String)
}