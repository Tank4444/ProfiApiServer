package ru.chuikov.routes

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.io.readByteArray
import ru.chuikov.util.Watermark

fun Route.watermark() {

    get("/watermark") {
        val multipartData = call.receiveMultipart()
        var text = ""
        var fileBytes: ByteArray? = null
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    text = part.value
                }
                is PartData.FileItem -> {
                    fileBytes = part.provider().readRemaining().readByteArray()
                }
                else -> {}
            }
            part.dispose()
        }
        if (fileBytes!=null)  Watermark.addTextWatermark(text, fileBytes,)

    }
}