package cc.worldmandia.webshoppersonal.db

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class JsonBasedRepository(private val storage: StringBasedStorage) {

    val json = Json { encodeDefaults = true }

    fun <T> loadOrFetch(key: String, fetcher: () -> T, serializer: KSerializer<T>): T {
        val raw = storage.get(key)
        if (raw != null) {
            return Json.decodeFromString(serializer, raw)
        }
        val fresh = fetcher()
        storage.put(key, json.encodeToString(serializer, fresh))
        return fresh
    }

    fun <T> save(key: String, value: T, serializer: KSerializer<T>) {
        storage.put(key, json.encodeToString(serializer, value))
    }
}