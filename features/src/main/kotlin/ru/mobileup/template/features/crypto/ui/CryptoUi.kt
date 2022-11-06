package ru.mobileup.template.features.crypto.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.mobileup.template.features.crypto.ui.details.CoinDetailsUi
import ru.mobileup.template.features.crypto.ui.list.CoinsListUi

@Composable
fun CryptoUi(
    component: CryptoComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier
    ) { child ->
        when (val instance = child.instance) {
            is CryptoComponent.Child.List -> CoinsListUi(component = instance.component)
            is CryptoComponent.Child.Details -> CoinDetailsUi(component = instance.component)
        }
    }
}
