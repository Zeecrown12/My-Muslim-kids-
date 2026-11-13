package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

// Brand & Visual Design System — Version 1.0 Typography
// English headings: Nunito
// English body: Nunito Sans
// Arabic interface: Noto Sans Arabic
// Quranic and longer Arabic text: Noto Naskh Arabic

val NunitoFontFamily = FontFamily(
  Font(R.font.nunito, FontWeight.Normal),
  Font(R.font.nunito, FontWeight.Medium),
  Font(R.font.nunito, FontWeight.SemiBold),
  Font(R.font.nunito, FontWeight.Bold),
  Font(R.font.nunito, FontWeight.ExtraBold)
)

val NunitoSansFontFamily = FontFamily(
  Font(R.font.nunito_sans, FontWeight.Normal),
  Font(R.font.nunito_sans, FontWeight.Medium),
  Font(R.font.nunito_sans, FontWeight.SemiBold),
  Font(R.font.nunito_sans, FontWeight.Bold)
)

val NotoSansArabicFontFamily = FontFamily(
  Font(R.font.noto_sans_arabic, FontWeight.Normal),
  Font(R.font.noto_sans_arabic, FontWeight.Bold)
)

val NotoNaskhArabicFontFamily = FontFamily(
  Font(R.font.noto_naskh_arabic, FontWeight.Normal),
  Font(R.font.noto_naskh_arabic, FontWeight.Bold)
)

val Typography = Typography(
  displayLarge = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 36.sp,
    lineHeight = 44.sp
  ),
  headlineLarge = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 28.sp,
    lineHeight = 36.sp
  ),
  headlineMedium = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    lineHeight = 28.sp
  ),
  titleLarge = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 26.sp
  ),
  titleMedium = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = 22.sp
  ),
  titleSmall = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 20.sp
  ),
  bodyLarge = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.2.sp
  ),
  bodyMedium = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp
  ),
  bodySmall = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp
  ),
  labelLarge = TextStyle(
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 20.sp
  ),
  labelMedium = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 16.sp
  ),
  labelSmall = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp
  )
)
