package ru.mobileup.template.features.crypto.ui.list

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import kotlinx.parcelize.Parcelize
import me.aartikov.replica.keyed.KeyedReplica
import me.aartikov.replica.keyed.keepPreviousData
import ru.mobileup.template.core.error_handling.ErrorHandler
import ru.mobileup.template.core.utils.observe
import ru.mobileup.template.core.utils.persistent
import ru.mobileup.template.features.crypto.domain.Coin
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.domain.Currency

class RealCoinsListComponent(
    componentContext: ComponentContext,
    private val onOutput: (CoinsListComponent.Output) -> Unit,
    private val coinByCurrencyReplica: KeyedReplica<Currency, List<Coin>>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinsListComponent {

    override val currencies = listOf(
        Currency("usd"),
        Currency("eur")
    )

    override var selectedCurrency by mutableStateOf(currencies[0])
        private set

    override val coinsState by coinByCurrencyReplica
        .keepPreviousData()
        .observe(
            lifecycle,
            errorHandler,
            key = { selectedCurrency }
        )

    init {
        persistent(
            save = { PersistentState(selectedCurrency) },
            restore = { state -> selectedCurrency = state.selectedTypeId }
        )
    }

    override fun onTypeClick(currency: Currency) {
        selectedCurrency = currency
    }

    override fun onCoinClick(coinId: CoinId) {
        onOutput(CoinsListComponent.Output.CoinDetailsRequested(coinId))
    }

    override fun onRefresh() {
        coinByCurrencyReplica.refresh(selectedCurrency)
    }

    override fun onRetryClick() {
        coinByCurrencyReplica.refresh(selectedCurrency)
    }

    @Parcelize
    private data class PersistentState(
        val selectedTypeId: Currency
    ) : Parcelable
}