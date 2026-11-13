package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

/**
 * Brand Logo Mark — Open Book + Geometric Crescent Star Emblem
 * Supports Full-Colour and Monochrome variations per Brand & Visual Design System v1.0.
 */
@Composable
fun BrandLogoMark(
  size: Dp = 44.dp,
  monochrome: Boolean = false,
  monochromeTint: Color = TextDark,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(size)
      .clip(RoundedCornerShape(size * 0.28f))
      .background(
        if (monochrome) {
          Brush.linearGradient(listOf(monochromeTint.copy(alpha = 0.12f), monochromeTint.copy(alpha = 0.12f)))
        } else {
          Brush.linearGradient(listOf(EmeraldPrimary, SkyBlue))
        }
      ),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(size * 0.68f)) {
      val w = this.size.width
      val h = this.size.height
      val strokeColor = if (monochrome) monochromeTint else Color.White
      val accentColor = if (monochrome) monochromeTint else SunGold

      // Open Book base
      val bookPath = Path().apply {
        moveTo(w * 0.12f, h * 0.62f)
        quadraticTo(w * 0.32f, h * 0.54f, w * 0.5f, h * 0.68f)
        quadraticTo(w * 0.68f, h * 0.54f, w * 0.88f, h * 0.62f)
        lineTo(w * 0.88f, h * 0.84f)
        quadraticTo(w * 0.68f, h * 0.76f, w * 0.5f, h * 0.88f)
        quadraticTo(w * 0.32f, h * 0.76f, w * 0.12f, h * 0.84f)
        close()
      }
      drawPath(bookPath, color = strokeColor)

      // Center spine line
      drawLine(
        color = if (monochrome) Color.White else EmeraldPrimary,
        start = Offset(w * 0.5f, h * 0.68f),
        end = Offset(w * 0.5f, h * 0.88f),
        strokeWidth = w * 0.05f
      )

      // 8-point geometric Islamic star rising from the book
      val cx = w * 0.5f
      val cy = h * 0.34f
      val r = w * 0.22f
      drawRoundRect(
        color = accentColor,
        topLeft = Offset(cx - r * 0.72f, cy - r * 0.72f),
        size = Size(r * 1.44f, r * 1.44f),
        cornerRadius = CornerRadius(w * 0.04f, w * 0.04f),
        style = Stroke(width = w * 0.07f)
      )
      drawCircle(
        color = accentColor,
        radius = r * 0.42f,
        center = Offset(cx, cy)
      )
    }
  }
}

@Composable
fun BrandWordmark(
  monochrome: Boolean = false,
  compact: Boolean = false,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    BrandLogoMark(size = if (compact) 36.dp else 46.dp, monochrome = monochrome)
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(
        text = "My Muslim Kids",
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = if (compact) 16.sp else 20.sp,
        color = if (monochrome) TextDark else EmeraldDark
      )
      if (!compact) {
        Text(
          text = "Learn • Play • Watch • Grow in Faith",
          fontFamily = NunitoSansFontFamily,
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextMuted
        )
      }
    }
  }
}
