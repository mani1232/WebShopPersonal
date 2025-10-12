package cc.worldmandia.webshoppersonal

import cc.worldmandia.webshoppersonal.entity.SupportedLang
import cc.worldmandia.webshoppersonal.i18n.mainPage
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = false
                isLenient = true
                encodeDefaults = true
            })
        }
        backend()
    }).start(wait = true)
}

fun Application.backend() = routing {
    get("/") {
        call.respondText("Ktor: ${Greeting().greet()}")
    }
    route("/api") {
        get {
            call.respondText("Ok")
        }
        apiV1()
    }
}

fun Route.apiV1() = route("/v1") {
    get {
        call.respondText("Api v1 is here")
    }
    langApi()
}

fun Route.langApi() = route("/lang/{langName}") {
    fun RoutingContext.getLang(): SupportedLang {
        val langName = call.parameters["langName"]?.uppercase() ?: "FR"
        return SupportedLang.valueOf(langName)
    }

    get {
        call.respondText("Lang api is here for ${getLang().name}")
    }

    route("/page") {
        route("/main") {
            get {
                call.respond(getLang().mainPage())
            }
        }
    }
}