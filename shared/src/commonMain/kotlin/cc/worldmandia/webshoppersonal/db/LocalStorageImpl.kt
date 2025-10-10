package cc.worldmandia.webshoppersonal.db

import web.storage.localStorage

class LocalStorageImpl: StringBasedStorage {
    override suspend fun get(key: String): String? {
        return localStorage.getItem(key)
    }

    override suspend fun put(key: String, value: String) {
        localStorage.setItem(key, value)
    }

    override suspend fun remove(key: String) {
        localStorage.removeItem(key)
    }

    override suspend fun clear(key: String) {
        localStorage.clear()
    }
}