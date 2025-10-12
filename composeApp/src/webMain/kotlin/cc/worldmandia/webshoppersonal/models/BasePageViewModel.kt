package cc.worldmandia.webshoppersonal.models

import cc.worldmandia.webshoppersonal.db.JsonBasedRepository
import cc.worldmandia.webshoppersonal.entity.BrowserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

const val browserDataId = "browserDataV1"

class BasePageViewModel(private val repo: JsonBasedRepository) {

    private val _browserData = MutableStateFlow(load(BrowserData()))
    val settings: StateFlow<BrowserData> = _browserData

    fun toggleTheme() {
        _browserData.update {
            val updated =
                it.copy(theme = if (it.theme == BrowserData.Theme.LIGHT) BrowserData.Theme.DARK else BrowserData.Theme.LIGHT)
            repo.save(
                browserDataId,
                updated,
                BrowserData.serializer()
            )
            updated
        }
    }

    private fun load(defaultValue: BrowserData = BrowserData()): BrowserData {
        return repo.loadOrFetch(browserDataId, fetcher = {
            defaultValue
        }, BrowserData.serializer())
    }

}