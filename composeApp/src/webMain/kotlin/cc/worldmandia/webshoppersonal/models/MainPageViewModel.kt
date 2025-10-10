package cc.worldmandia.webshoppersonal.models

import cc.worldmandia.webshoppersonal.db.JsonBasedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.serializer

class MainPageViewModel(private val repo: JsonBasedRepository) {

    var data: String = "—"

    fun load(key: String) {
        CoroutineScope(Dispatchers.Main).launch {
            data = repo.loadOrFetch(key, fetcher = {
                // TODO fetch from network
                "In dev fetcher"
            }, serializer = serializer())
        }
    }

    fun save(key: String, value: String) {
        CoroutineScope(Dispatchers.Main).launch {
            repo.save(key, value, serializer())
            data = value
        }
    }
}