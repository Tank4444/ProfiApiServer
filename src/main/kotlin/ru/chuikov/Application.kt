package ru.chuikov

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.chuikov.db.configureDatabases
import io.ktor.server.routing.*
import ru.chuikov.routes.configRouting
import ru.chuikov.routes.registration

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {

    install(DefaultHeaders)
    routing {
        swaggerUI(path = "openapi")
    }
    routing {
        openAPI(path = "openapi")
    }

    install(Koin) {
        slf4jLogger()
        modules(module {
//            single<HelloService> {
//                HelloService {
//                    println(environment.log.info("Hello, World!"))
//                }
//            }
        })
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    configureDatabases()
    configureSecurity()
    install(Resources)
    configRouting()
}
