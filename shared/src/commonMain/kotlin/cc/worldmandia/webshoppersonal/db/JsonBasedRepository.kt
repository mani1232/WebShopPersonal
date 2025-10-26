package cc.worldmandia.webshoppersonal.db

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

class JsonBasedRepository(val storage: StringBasedStorage, val serializerModule: SerializersModule) {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        encodeDefaults = true
        serializersModule = serializerModule
    }

    inline fun <reified T> loadOrFetch(key: String, fetcher: () -> T): T {
        val raw = storage.get(key)
        if (raw != null) {
            return json.decodeFromString(raw)
        }
        val fresh = fetcher()
        storage.put(key, json.encodeToString(fresh))
        return fresh
    }

    inline fun <reified T> save(key: String, value: T) {
        storage.put(key, json.encodeToString(value))
    }
}