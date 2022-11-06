package ru.mobileup.template.features.crypto.ui.details

import me.aartikov.replica.single.Loadable
import ru.mobileup.template.features.crypto.domain.DetailedCoin

interface CoinDetailsComponent {

    val coinState: Loadable<DetailedCoin>

    fun onRetryClick()

    fun onRefresh()

    fun onBackPressed()

    sealed interface Output {
        object BackPressed : Output
    }
}