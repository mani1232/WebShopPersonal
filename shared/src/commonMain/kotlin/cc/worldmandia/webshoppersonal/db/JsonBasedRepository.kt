package cc.worldmandia.webshoppersonal.db

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class JsonBasedRepository(private val storage: StringBasedStorage) {

    suspend fun <T> loadOrFetch(key: String, fetcher: suspend () -> T, serializer: KSerializer<T>): T {
        val raw = storage.get(key)
        if (raw != null) {
            return Json.decodeFromString(serializer, raw)
        }
        val fresh = fetcher()
        storage.put(key, Json.encodeToString(serializer, fresh))
        return fresh
    }

    suspend fun <T> save(key: String, value: T, serializer: KSerializer<T>) {
        storage.put(key, Json.encodeToString(serializer, value))
    }
}