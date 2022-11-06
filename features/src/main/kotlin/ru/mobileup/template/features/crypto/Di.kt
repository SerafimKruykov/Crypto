package ru.mobileup.template.features.crypto

import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.algebra.withKey
import org.koin.core.component.get
import org.koin.dsl.module
import ru.mobileup.template.core.ComponentFactory
import ru.mobileup.template.core.network.NetworkApiFactory
import ru.mobileup.template.features.crypto.data.CryptoApi
import ru.mobileup.template.features.crypto.data.CryptoRepository
import ru.mobileup.template.features.crypto.data.CryptoRepositoryImpl
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.ui.CryptoComponent
import ru.mobileup.template.features.crypto.ui.RealCryptoComponent
import ru.mobileup.template.features.crypto.ui.list.CoinsListComponent
import ru.mobileup.template.features.crypto.ui.details.CoinDetailsComponent
import ru.mobileup.template.features.crypto.ui.details.RealCoinDetailsComponent
import ru.mobileup.template.features.crypto.ui.list.RealCoinsListComponent

val cryptoModule = module {
    single<CryptoApi> { get<NetworkApiFactory>().createUnauthorizedApi() }
    single<CryptoRepository> { CryptoRepositoryImpl(
        replicaClient = get(),
        api = get()
    )
    }
}


fun ComponentFactory.createCryptoComponent(
    componentContext: ComponentContext
): CryptoComponent {
    return RealCryptoComponent(componentContext, get())
}

fun ComponentFactory.createCoinListComponent(
    componentContext: ComponentContext,
    onOutput: (CoinsListComponent.Output) -> Unit
): CoinsListComponent {
    val coinsByCurrencyReplica = get<CryptoRepository>().coinsByExchangeReplica
    return RealCoinsListComponent(
        componentContext = componentContext,
        onOutput =  onOutput,
        coinByCurrencyReplica =  coinsByCurrencyReplica,
        errorHandler =  get()
    )
}

fun ComponentFactory.createCoinDetailsComponent(
    componentContext: ComponentContext,
    onOutput: (CoinDetailsComponent.Output) -> Unit,
    coinId: CoinId
): CoinDetailsComponent{
    val coinByIdReplica = get<CryptoRepository>().coinByIdReplica.withKey(key = coinId)
    return RealCoinDetailsComponent(
        componentContext = componentContext,
        onOutput =onOutput,
        coinReplica = coinByIdReplica,
        errorHandler = get()
    )
}
