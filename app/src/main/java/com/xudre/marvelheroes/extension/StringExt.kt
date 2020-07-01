package com.xudre.marvelheroes.extension

import java.security.MessageDigest

fun String.hash(algorithm: String): String? {
    val buffer = StringBuffer()
    val messageDigest = MessageDigest.getInstance(algorithm)
    val bytes = messageDigest.digest(this.toByteArray())

    for (i in bytes.indices) {
        val hex = Integer.toHexString(bytes[i].toInt() and 0xFF or 0x100)

        buffer.append(hex.substring(1, 3))
    }

    return buffer.toString()
}

