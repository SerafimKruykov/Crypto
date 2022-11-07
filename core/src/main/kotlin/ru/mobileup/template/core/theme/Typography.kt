package ru.mobileup.template.core.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.mobileup.template.core.R

data class CustomTypography(
    val topBar: TopBarTypography,
    val coin: CoinTypography,
    val text: TextTypography
)

data class TopBarTypography(
    val normal: TextStyle,
    val semiBold: TextStyle
)

data class CoinTypography(
    val normal: TextStyle,
    val medium: TextStyle,
    val semiBold: TextStyle
)

data class TextTypography(
    val normal: TextStyle,
    val semiBold: TextStyle
)

val LocalCustomTypography = staticCompositionLocalOf<CustomTypography?> { null }

val roboto = FontFamily(
    Font(R.font.roboto, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium)
)

val jakarta = FontFamily(
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold)
)

val AppTypography = CustomTypography(
    topBar = TopBarTypography(
        normal = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        semiBold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
    ),

    coin = CoinTypography(
        normal = TextStyle(
            fontFamily = jakarta,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        medium = TextStyle(
            fontFamily = jakarta,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        semiBold = TextStyle(
            fontFamily = jakarta,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    ),

    text = TextTypography(
        normal = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        semiBold = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )
    )
)

fun CustomTypography.toMaterialTypography(): Typography {
    return Typography(
        h1 = topBar.semiBold,
        h2 = topBar.normal,
        h3 = coin.semiBold,
        h4 = coin.medium,
        h5 = coin.normal,
        subtitle1 = text.semiBold,
        subtitle2 = text.normal,
        button = text.normal,
        caption = text.normal,
        overline = text.normal
    )
}
