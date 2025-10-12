package cc.worldmandia.webshoppersonal.db

import web.storage.localStorage

class WebLocalStorageImpl : StringBasedStorage {
    override fun get(key: String): String? {
        return localStorage.getItem(key)
    }

    override fun put(key: String, value: String) {
        localStorage.setItem(key, value)
    }

    override fun remove(key: String) {
        localStorage.removeItem(key)
    }

    override fun clear(key: String) {
        localStorage.clear()
    }
}