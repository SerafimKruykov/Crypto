package ru.mobileup.template.features.crypto.ui.list

import me.aartikov.replica.single.Loadable
import ru.mobileup.template.features.crypto.domain.Coin
import ru.mobileup.template.features.crypto.domain.Currency
import ru.mobileup.template.features.crypto.domain.CoinId

interface CoinsListComponent {
    val currencies: List<Currency>

    val selectedCurrency: Currency

    val coinsState: Loadable<List<Coin>>

    fun onTypeClick(currency: Currency)

    fun onCoinClick(coinId: CoinId)

    fun onRefresh()

    fun onRetryClick()

    sealed interface Output {
        data class CoinDetailsRequested(val coinId: CoinId) : Output
    }
}