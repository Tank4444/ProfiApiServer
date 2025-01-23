package ru.chuikov

import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import ru.chuikov.routes.RegistrationRequest

fun Application.configValidation(){
    install(RequestValidation){

    }
}