package ru.mobileup.template.features.crypto.ui

import com.arkivanov.decompose.router.stack.ChildStack
import ru.mobileup.template.features.crypto.ui.details.CoinDetailsComponent
import ru.mobileup.template.features.crypto.ui.list.CoinsListComponent

interface CryptoComponent {
    val childStack: ChildStack<*, Child>

    sealed interface Child {
        class List(val component: CoinsListComponent) : Child
        class Details(val component: CoinDetailsComponent) : Child
    }
}