package ru.mobileup.template.features.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.template.features.crypto.domain.Coin
import ru.mobileup.template.features.crypto.domain.CoinId


@Serializable
class CoinResponseModel(
    @SerialName("id") val id: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("image") val imageUrl: String,
    @SerialName("current_price") val currentPrice: Double,
    @SerialName("price_change_percentage_24h") val priceChangePercentage: String
)

fun CoinResponseModel.toDomain(): Coin {
    return Coin(
        coinId = CoinId(value = id),
        coinName = name,
        coinSymbol = symbol,
        coinIcon = imageUrl,
        currentPrice = currentPrice,
        priceChange = priceChangePercentage
    )
}