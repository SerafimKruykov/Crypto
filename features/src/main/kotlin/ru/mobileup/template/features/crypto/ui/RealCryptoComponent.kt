package ru.mobileup.template.features.crypto.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import kotlinx.parcelize.Parcelize
import ru.mobileup.template.core.ComponentFactory
import ru.mobileup.template.core.utils.toComposeState
import ru.mobileup.template.features.crypto.createCoinDetailsComponent
import ru.mobileup.template.features.crypto.createCoinListComponent
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.ui.details.CoinDetailsComponent
import ru.mobileup.template.features.crypto.ui.list.CoinsListComponent

class RealCryptoComponent(
    componentContext: ComponentContext,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, CryptoComponent {

    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: ChildStack<*, CryptoComponent.Child> by childStack(
        source = navigation,
        initialConfiguration = ChildConfig.List,
        handleBackButton = true,
        childFactory = ::createChild
    ).toComposeState(lifecycle)

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): CryptoComponent.Child = when (config) {
        is ChildConfig.List -> CryptoComponent.Child.List(
            componentFactory.createCoinListComponent(
                componentContext = componentContext,
                onOutput = ::onCoinsListOutput
            )
        )
        is ChildConfig.Details -> CryptoComponent.Child.Details(
            componentFactory.createCoinDetailsComponent(
                componentContext = componentContext,
                onOutput = ::onCoinDetailsOutput,
                coinId = config.coinId
            )
        )
    }

    private fun onCoinsListOutput(output: CoinsListComponent.Output) {
        when (output) {
            is CoinsListComponent.Output.CoinDetailsRequested -> {
                navigation.push(ChildConfig.Details(output.coinId))
            }
        }
    }

    private fun onCoinDetailsOutput(output: CoinDetailsComponent.Output) {
        when (output) {
            is CoinDetailsComponent.Output.BackPressed -> {
                navigation.pop()
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object List : ChildConfig

        @Parcelize
        data class Details(val coinId: CoinId) : ChildConfig
    }
}