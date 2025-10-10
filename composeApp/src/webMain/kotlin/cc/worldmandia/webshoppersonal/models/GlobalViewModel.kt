package cc.worldmandia.webshoppersonal.models

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import cc.worldmandia.webshoppersonal.db.JsonBasedRepository
import kotlinx.serialization.serializer

class GlobalViewModel(private val repo: JsonBasedRepository) {

    suspend fun toggleTheme(): Boolean {
        return (!isDarkTheme()).also {
            repo.save("isDarkTheme", it, serializer<Boolean>())
        }
    }

    suspend fun isDarkTheme(): Boolean {
        return repo.loadOrFetch("isDarkTheme", fetcher = {
            false
        }, serializer<Boolean>())
    }

}