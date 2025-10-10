package cc.worldmandia.webshoppersonal.koin

import cc.worldmandia.webshoppersonal.db.JsonBasedRepository
import cc.worldmandia.webshoppersonal.db.LocalStorageImpl
import cc.worldmandia.webshoppersonal.db.StringBasedStorage
import cc.worldmandia.webshoppersonal.models.GlobalViewModel
import cc.worldmandia.webshoppersonal.models.MainPageViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val coreModule = module {
    single { JsonBasedRepository(get()) }
    single { MainPageViewModel(get()) }
    single { GlobalViewModel(get()) }
}

val platformModule = module {
    single<StringBasedStorage> { LocalStorageImpl() }
}

fun startKoin() = startKoin {
    modules(listOf(platformModule, coreModule))
}