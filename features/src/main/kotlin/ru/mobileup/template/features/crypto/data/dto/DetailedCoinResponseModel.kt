package ru.mobileup.template.features.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.domain.DetailedCoin

@Serializable
class DetailedCoinResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("image") val imageUrl: ImageUrl,
    @SerialName("description") val description: Description,
    @SerialName("categories") val categories: List<String>
)

@Serializable
class ImageUrl(
    @SerialName("large") val url: String
)

@Serializable
class Description(
    @SerialName("en") val descriptionText: String
)

fun DetailedCoinResponse.toDomain(): DetailedCoin = DetailedCoin(
    coinId = CoinId(value = id),
    coinName = name,
    coinIcon = imageUrl.url,
    description = description.descriptionText,
    categories = categories
)