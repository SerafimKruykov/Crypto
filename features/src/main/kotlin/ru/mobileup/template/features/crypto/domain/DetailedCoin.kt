package ru.mobileup.template.features.crypto.domain

import ru.mobileup.template.features.crypto.data.dto.Description
import ru.mobileup.template.features.crypto.data.dto.ImageUrl

class DetailedCoin(
    val coinId: CoinId,
    val coinName: String,
    val coinIcon: String,
    val description: String,
    val categories: List<String>
)