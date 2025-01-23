package ru.chuikov.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configRouting(){
    routing {
        registration()
    }
}