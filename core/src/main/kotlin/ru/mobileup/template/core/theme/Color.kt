package ru.mobileup.template.core.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val isLight: Boolean,
    val primary: Color,
    val surface: Color,
    val arrowColor: Color,
    val secondary: Color,
    val coin: CoinColors,
    val chip: ChipColors,
    val text: TextColors
)

data class CoinColors(
    val coinName: Color,
    val coinSymbol: Color,
)

data class ChipColors(
    val chipUnselected: Color,
    val chipSelected: Color
)

data class TextColors(
    val title: Color,
    val chipUnselectedText: Color,
    val chipSelectedText: Color,
    val positivePercentage: Color,
    val negativePercentage: Color,
    val detailsText: Color
)

val LocalCustomColors = staticCompositionLocalOf<CustomColors?> { null }

val LightAppColors = CustomColors(
    isLight = true,
    primary = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    arrowColor = Color(0xFF757575),
    secondary = Color(0xFFFF9F00),

    coin = CoinColors(
        coinName = Color(0xFF525252),
        coinSymbol = Color(0xFF9B9B9B)
    ),

    chip = ChipColors(
        chipSelected = Color(0xFFFBEFDC),
        chipUnselected = Color(0xFFE0E0E0)
    ),

    text = TextColors(
        title = Color(0xFF3E3E3E),
        chipSelectedText = Color(0xFFFFAB21),
        chipUnselectedText = Color(0xDE000000),
        positivePercentage = Color(0xFF2A9D8F),
        negativePercentage = Color(0xFFEB5757),
        detailsText = Color.Black
    )
)

fun CustomColors.toMaterialColors(): Colors {
    return Colors(
        primary = primary,
        primaryVariant = secondary,
        secondary = coin.coinSymbol,
        secondaryVariant = chip.chipSelected,
        background = chip.chipUnselected,
        surface = surface,
        error = text.negativePercentage,
        isLight = isLight,
        onPrimary = primary,
        onSecondary = text.chipSelectedText,
        onBackground = text.chipSelectedText,
        onSurface = text.chipUnselectedText,
        onError = text.chipUnselectedText
    )
}