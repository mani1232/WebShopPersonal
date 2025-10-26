package cc.worldmandia.webshoppersonal.di

import cc.worldmandia.webshoppersonal.db.JsonBasedRepository
import cc.worldmandia.webshoppersonal.db.StringBasedStorage
import cc.worldmandia.webshoppersonal.db.WebLocalStorageImpl
import cc.worldmandia.webshoppersonal.models.BasePageViewModel
import cc.worldmandia.webshoppersonal.models.MainPageViewModel
import cc.worldmandia.webshoppersonal.navSerializerModule
import org.koin.dsl.module

val viewModule = module {
    single { MainPageViewModel() }
    single { BasePageViewModel(get()) }
}

val coreModule = module {
    single {
        JsonBasedRepository(get(), navSerializerModule)
    }
    single<StringBasedStorage> { WebLocalStorageImpl() }
}