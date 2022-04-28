package ru.mobileup.features.pokemons.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.features.pokemons.domain.Pokemon
import ru.mobileup.features.pokemons.domain.PokemonId

@Serializable
class PokemonResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
class PokemonWrapperResponse(
    @SerialName("pokemon") val pokemon: PokemonResponse
)

fun PokemonResponse.toDomain(): Pokemon {
    return Pokemon(
        id = PokemonId(parseId(url)),
        name = formatName(name)
    )
}