package ru.chuikov.util

import io.ktor.server.engine.*

object JWTUtil {
    init {
        val secret = applicationEnvironment().config.property("jwt.secret").getString()
        val issuer = applicationEnvironment().config.property("jwt.issuer").getString()
        val audience = applicationEnvironment().config.property("jwt.audience").getString()
        val myRealm = applicationEnvironment().config.property("jwt.realm").getString()
    }

}