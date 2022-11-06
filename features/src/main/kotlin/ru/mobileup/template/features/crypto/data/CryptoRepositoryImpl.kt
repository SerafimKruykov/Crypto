package ru.mobileup.template.features.crypto.data


import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.keyed.KeyedReplica
import me.aartikov.replica.single.ReplicaSettings
import ru.mobileup.template.features.crypto.data.dto.toDomain
import ru.mobileup.template.features.crypto.domain.Coin
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.domain.Currency
import ru.mobileup.template.features.crypto.domain.DetailedCoin
import kotlin.time.Duration.Companion.minutes

class CryptoRepositoryImpl(
    replicaClient: ReplicaClient,
    api: CryptoApi
) : CryptoRepository {

    override val coinsByExchangeReplica: KeyedReplica<Currency, List<Coin>> =
        replicaClient.createKeyedReplica(
            name = "coinsList",
            childName = { currency -> "currency = ${currency.value}" },
            childSettings = {
                ReplicaSettings(
                    staleTime = 1.minutes
                )
            }
        ) { currency ->
            api.getCoinsByExchange(currency.value).map { it.toDomain() }
        }

    override val coinByIdReplica: KeyedReplica<CoinId, DetailedCoin> =
        replicaClient.createKeyedReplica(
            name = "coinDetails",
            childName = { id -> "id = ${id.value}" },
            childSettings = {
                ReplicaSettings(
                    staleTime = 1.minutes
                )
            }
        ) { coinId ->
            api.getCoinDetails(coinId.value).toDomain()
        }
}