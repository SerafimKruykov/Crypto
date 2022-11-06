package ru.mobileup.template.features.crypto.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class CoinId(val value: String) : Parcelable

@Parcelize
data class Coin(
    val coinId: CoinId,
    val coinName: String,
    val coinSymbol: String,
    val coinIcon: String,
    val currentPrice: Double,
    val priceChange: String
) : Parcelable
