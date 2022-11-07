package ru.mobileup.template.features.crypto.domain

class DetailedCoin(
    val coinId: CoinId,
    val coinName: String,
    val coinIcon: String,
    val description: String,
    val categories: List<String>
)