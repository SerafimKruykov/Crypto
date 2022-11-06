package ru.mobileup.template.features.crypto.ui.details

import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Replica
import ru.mobileup.template.core.error_handling.ErrorHandler
import ru.mobileup.template.core.utils.observe
import ru.mobileup.template.features.crypto.domain.DetailedCoin

class RealCoinDetailsComponent(
    componentContext: ComponentContext,
    private val onOutput: (CoinDetailsComponent.Output) -> Unit,
    private val coinReplica: Replica<DetailedCoin>,
    errorHandler: ErrorHandler
) : ComponentContext by componentContext, CoinDetailsComponent {

    override val coinState by coinReplica.observe(lifecycle, errorHandler)

    override fun onRetryClick() {
        coinReplica.refresh()
    }

    override fun onRefresh() {
        coinReplica.refresh()
    }

    override fun onBackPressed() {
        onOutput(CoinDetailsComponent.Output.BackPressed)
    }
}