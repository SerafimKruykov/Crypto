package ru.mobileup.template.features.crypto.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.aartikov.replica.single.Loadable
import ru.mobileup.template.core.theme.CoinTheme
import ru.mobileup.template.core.widget.RefreshingProgress
import ru.mobileup.template.core.widget.SwipeRefreshLceWidget
import ru.mobileup.template.features.R
import ru.mobileup.template.features.crypto.domain.Coin
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.domain.Currency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

@Preview(showSystemUi = true)
@Composable
fun PreviewRootUi() {
    CoinsListUi(FakeCoinsListComponent())
}


@Composable
fun CoinsListUi(
    component: CoinsListComponent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ExchangeTypeRow(
                types = component.currencies,
                selectedTypeId = component.selectedCurrency,
                onTypeClick = component::onTypeClick
            )
            SwipeRefreshLceWidget(state = component.coinsState,
                onRefresh =  component::onRefresh,
                onRetryClick = component::onRetryClick
            ) { coins, refreshing ->
                CoinsListContent(
                    coins = coins,
                    currency = component.selectedCurrency,
                    onCoinClick = component::onCoinClick
                )
                RefreshingProgress(refreshing, modifier = Modifier.padding(top = 4.dp))
            }

        }
    }
}

@Composable
fun ExchangeTypeRow(
    types: List<Currency>,
    selectedTypeId: Currency,
    onTypeClick: (Currency) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(117.dp)
            .fillMaxWidth(), elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                text = stringResource(id = R.string.list_of_crypto_currencies),
                style = CoinTheme.typography.topBar.semiBold,
                color = CoinTheme.colors.text.title,
            )
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                types.forEach { currency ->
                    ExchangeTypeItem(type = currency,
                        isSelected = currency == selectedTypeId,
                        onClick = { onTypeClick(currency) })
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExchangeTypeItem(
    type: Currency,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(32.dp)
            .width(89.dp),
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        shape = RoundedCornerShape(48.dp),

        color = when (isSelected) {
            true -> CoinTheme.colors.chip.chipSelected
            else -> CoinTheme.colors.chip.chipUnselected
        }

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = type.value.uppercase(), color = when (isSelected) {
                    true -> CoinTheme.colors.text.chipSelectedText
                    else -> CoinTheme.colors.text.chipUnselectedText
                }, style = MaterialTheme.typography.body1, textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun CoinsListContent(
    coins: List<Coin>,
    currency: Currency,
    onCoinClick: (CoinId) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(items = coins, key = { it.coinId.value }) { coin ->
            CoinItem(coin = coin, currency = currency, onClick = { onCoinClick(coin.coinId) })
        }
    }
}

@Composable
fun CoinItem(
    coin: Coin,
    currency: Currency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val decimalFormat = DecimalFormat(stringResource(id = R.string.decimal_format), DecimalFormatSymbols((Locale.ENGLISH)))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(bottom = 8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                contentDescription = null,
                model = ImageRequest.Builder(LocalContext.current).data(coin.coinIcon)
                    .crossfade(true).build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = coin.coinName,
                    style = CoinTheme.typography.coin.medium,
                    color = CoinTheme.colors.coin.coinName
                )
                Text(
                    text = coin.coinSymbol.uppercase(),
                    style = CoinTheme.typography.coin.normal,
                    color = CoinTheme.colors.coin.coinName
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = if (currency.value == "usd") "$" + decimalFormat.format(coin.currentPrice) else
                    "€" + decimalFormat.format(coin.currentPrice),

                style = CoinTheme.typography.coin.semiBold,
                color = CoinTheme.colors.text.title
            )
            Text(
                text = if (checkPositive(priceChange = coin.priceChange)) "+" + decimalFormat.format(
                    coin.priceChange.toDouble()
                ) + "%" else decimalFormat.format(coin.priceChange.toDouble()) + "%",
                style = CoinTheme.typography.coin.normal,
                color = if (checkPositive(priceChange = coin.priceChange)) CoinTheme.colors.text.positivePercentage else CoinTheme.colors.text.negativePercentage
            )
        }
    }
}

private fun checkPositive(priceChange: String): Boolean {
    return priceChange.toDouble() > 0.0
}


class FakeCoinsListComponent : CoinsListComponent {

    override val currencies = listOf(
        Currency("usd"),
        Currency("eur")
    )

    override val selectedCurrency = currencies[0]

    override val coinsState = Loadable(
        loading = true, data = listOf(
            Coin(
                coinId = CoinId(value = "btc1"),
                coinName = "Bitcoin",
                coinSymbol = "",
                coinIcon = "",
                currentPrice = 12398.098098,
                priceChange = "-1.2"
            ), Coin(
                coinId = CoinId(value = "btc2"),
                coinName = "Bitcoin",
                coinSymbol = "",
                coinIcon = "",
                currentPrice = 12398.098098,
                priceChange = "-1.2"
            ), Coin(
                coinId = CoinId(value = "btc3"),
                coinName = "Bitcoin",
                coinSymbol = "",
                coinIcon = "",
                currentPrice = 12398.098098,
                priceChange = "1.2"
            ), Coin(
                coinId = CoinId(value = "btc4"),
                coinName = "Bitcoin",
                coinSymbol = "",
                coinIcon = "",
                currentPrice = 12398.098098,
                priceChange = "-1.2"
            ), Coin(
                coinId = CoinId(value = "btc5"),
                coinName = "Bitcoin",
                coinSymbol = "",
                coinIcon = "",
                currentPrice = 12398.098098,
                priceChange = "1.2"
            )
        )
    )

    override fun onTypeClick(currency: Currency) = Unit

    override fun onCoinClick(coinId: CoinId) = Unit

    override fun onRefresh() = Unit

    override fun onRetryClick() = Unit
}



