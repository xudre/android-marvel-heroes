package com.xudre.marvelheroes.extension

import java.text.NumberFormat
import java.util.*

fun Number.currency(
    minDecimals: Int = 2,
    maxDecimals: Int = 2,
    currency: Currency = Currency.getInstance(Locale.US)
): String? {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

    currencyFormat.minimumFractionDigits = minDecimals
    currencyFormat.maximumFractionDigits = maxDecimals
    currencyFormat.currency = currency

    return currencyFormat.format(this.toDouble())
}
