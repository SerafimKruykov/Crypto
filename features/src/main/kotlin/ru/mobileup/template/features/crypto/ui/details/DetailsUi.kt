package ru.mobileup.template.features.crypto.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.aartikov.replica.single.Loadable
import ru.mobileup.template.core.theme.CoinTheme
import ru.mobileup.template.core.widget.RefreshingProgress
import ru.mobileup.template.core.widget.SwipeRefreshLceWidget
import ru.mobileup.template.features.R
import ru.mobileup.template.features.crypto.domain.CoinId
import ru.mobileup.template.features.crypto.domain.DetailedCoin

@Composable
fun CoinDetailsUi(component: CoinDetailsComponent, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            DetailsToolbar(
                onClick = { component.onBackPressed() },
                coinName = component.coinState.data?.coinName ?: ""
            )
            SwipeRefreshLceWidget(
                state = component.coinState,
                onRefresh = component::onRefresh,
                onRetryClick = component::onRetryClick
            ) { coin, refreshing ->
                CoinDetailsUiContent(coin)
                RefreshingProgress(refreshing)
            }

        }


    }
}

@Composable
fun DetailsToolbar(
    onClick: (() -> Unit)? = null,
    coinName: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { onClick?.invoke() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back_icon_description)
                )
            }
            Text(
                text = coinName,
                style = CoinTheme.typography.topBar.semiBold,
                color = CoinTheme.colors.text.title,
                modifier = Modifier.padding(start = 36.dp)
            )
        }
    }
}

@Composable
fun CoinDetailsUiContent(
    coin: DetailedCoin,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.height(4.dp))
        AsyncImage(
            contentDescription = null,
            model = ImageRequest.Builder(LocalContext.current)
                .data(coin.coinIcon)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)

        )

        Text(
            text = stringResource(id = R.string.coin_description),
            style = CoinTheme.typography.text.semiBold,
            color = CoinTheme.colors.text.detailsText,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = HtmlCompat
                .fromHtml(coin.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString(),
            style = CoinTheme.typography.text.normal,
            color = CoinTheme.colors.text.detailsText,

            )

        Text(
            text = stringResource(id = R.string.coin_categories),
            style = CoinTheme.typography.text.semiBold,
            color = CoinTheme.colors.text.detailsText,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = coin.categories.joinToString(),
            style = CoinTheme.typography.text.normal,
            color = CoinTheme.colors.text.detailsText,
            modifier = Modifier.padding(bottom = 34.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CoinDetailsUiPreview() {
    CoinDetailsUi(component = FakeCoinDetailsComponent())
}

class FakeCoinDetailsComponent : CoinDetailsComponent {

    override val coinState = Loadable(
        loading = true,
        data = DetailedCoin(
            coinId = CoinId("bitcoin"),
            coinName = "Btc",
            coinIcon = "",
            description = "This is a bitcoin description, first thing you should know about it is you should have invested in it 5 ears ago",
            categories = listOf(
                "Lost opportunities",
                "Regrets"
            )
        )
    )

    override fun onBackPressed() = Unit

    override fun onRetryClick() = Unit

    override fun onRefresh() = Unit
}