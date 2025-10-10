package cc.worldmandia.webshoppersonal.db

interface StorageCache<T> {
    suspend fun get(key: String): String?
    suspend fun put(key: String, value: T)
    suspend fun remove(key: String)
    suspend fun clear(key: String)
}

interface StringBasedStorage: StorageCache<String>