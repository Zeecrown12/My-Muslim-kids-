package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val KidsLightColorScheme = lightColorScheme(
  primary = EmeraldPrimary,
  onPrimary = Color.White,
  primaryContainer = MintContainer,
  onPrimaryContainer = OnMintContainer,
  secondary = SunGold,
  onSecondary = Color.White,
  secondaryContainer = AmberContainer,
  onSecondaryContainer = OnAmberContainer,
  tertiary = SkyBlue,
  onTertiary = Color.White,
  tertiaryContainer = SkyContainer,
  onTertiaryContainer = OnSkyContainer,
  background = IvoryBackground,
  onBackground = TextDark,
  surface = CardSurface,
  onSurface = TextDark,
  surfaceVariant = Color(0xFFF1F5F0),
  onSurfaceVariant = TextMuted,
  outline = CardBorder,
)

val KidsShapes = Shapes(
  small = RoundedCornerShape(12.dp),
  medium = RoundedCornerShape(20.dp),
  large = RoundedCornerShape(28.dp),
  extraLarge = RoundedCornerShape(36.dp)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // For kids app, maintain joyful vibrant palette
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = KidsLightColorScheme,
    typography = Typography,
    shapes = KidsShapes,
    content = content
  )
}
