package cc.worldmandia.webshoppersonal.models

import androidx.navigation3.runtime.NavKey
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
        update {
            it.theme = if (it.theme == BrowserData.Theme.LIGHT) BrowserData.Theme.DARK else BrowserData.Theme.LIGHT
        }
    }

    private fun update(updateFun: (BrowserData) -> Unit) {
        _browserData.update {
            it.copy().also { new ->
                updateFun(new)
                repo.save(
                    browserDataId,
                    new
                )
            }
        }
    }

    fun updateNavKey(newNavKey: NavKey) {
        update {
            it.navKey = newNavKey
        }
    }

    private fun load(defaultValue: BrowserData = BrowserData()): BrowserData {
        return repo.loadOrFetch(browserDataId, fetcher = {
            defaultValue
        })
    }

}