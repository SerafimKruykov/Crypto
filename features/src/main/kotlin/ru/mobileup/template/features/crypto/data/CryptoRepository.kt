package ru.mobileup.template.features.crypto.data

import me.aartikov.replica.keyed.KeyedReplica
import ru.mobileup.template.features.crypto.domain.Coin
import ru.mobileup.template.features.crypto.domain.Currency
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.domain.DetailedCoin

interface CryptoRepository {
    val coinsByExchangeReplica: KeyedReplica<Currency, List<Coin>>

    val coinByIdReplica: KeyedReplica<CoinId, DetailedCoin>
}