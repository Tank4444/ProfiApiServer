package ru.chuikov.routes

import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegistrationRequest(
    @SerialName("birth_date")
    val birthDate: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("patronymic")
    val patronymic: String?
)

@Serializable
data class RegistrationResponse(
    @SerialName("data")
    val `data`: Data
) {
    @Serializable
    data class Data(
        @SerialName("code")
        val code: Int,
        @SerialName("message")
        val message: String,
        @SerialName("user")
        val user: User
    ) {
        @Serializable
        data class User(
            @SerialName("email")
            val email: String,
            @SerialName("name")
            val name: String
        )
    }
}

fun Routing.registration() {
    post("/registration") {
        var registration: RegistrationRequest = call.receive<RegistrationRequest>()



    }
}