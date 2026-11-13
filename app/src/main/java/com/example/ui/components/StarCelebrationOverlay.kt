package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun StarCelebrationOverlay(
  message: String?,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(
    visible = message != null,
    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 70.dp, start = 16.dp, end = 16.dp)
  ) {
    if (message != null) {
      Surface(
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(
            Brush.horizontalGradient(
              listOf(Color(0xFFFEF08A), Color(0xFFFDE047), Color(0xFFF59E0B))
            )
          )
          .testTag("star_celebration_banner")
      ) {
        Row(
          modifier = Modifier
            .background(
              Brush.horizontalGradient(
                listOf(Color(0xFFFEF08A), Color(0xFFFDE047), Color(0xFFFBBF24))
              )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color(0xFFB45309),
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = message,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF78350F)
          )
        }
      }
    }
  }
}
