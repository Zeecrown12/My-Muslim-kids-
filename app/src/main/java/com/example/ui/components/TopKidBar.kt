package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgeGroup
import com.example.model.ChildProfile
import com.example.ui.theme.*

@Composable
fun TopKidBar(
  activeProfile: ChildProfile,
  onProfileClick: () -> Unit,
  onParentGateClick: () -> Unit,
  onAgeGroupToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .statusBarsPadding(),
    color = IvoryBackground,
    tonalElevation = 2.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Child Avatar & Name Pill
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(24.dp))
          .background(Color.White)
          .clickable { onProfileClick() }
          .padding(horizontal = 10.dp, vertical = 6.dp)
          .testTag("child_profile_pill"),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(activeProfile.avatarBgHex)),
          contentAlignment = Alignment.Center
        ) {
          Text(text = activeProfile.avatarEmoji, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = activeProfile.name,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = TextDark
          )
          Text(
            text = activeProfile.ageGroup.title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = EmeraldPrimary
          )
        }
      }

      // Middle: Star Vault & Streak Badges
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Stars pill
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
              Brush.horizontalGradient(
                listOf(AmberContainer, SunGoldLight)
              )
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("star_counter_pill"),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Stars",
            tint = SunGoldDark,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${activeProfile.stars}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            color = OnAmberContainer
          )
        }

        // Streak pill
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MintContainer)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("streak_counter_pill"),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "🔥", fontSize = 13.sp)
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "${activeProfile.streakDays}d",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = OnMintContainer
          )
        }

        // Parent Centre Button (Protected by Parental Gate)
        IconButton(
          onClick = onParentGateClick,
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color.White)
            .shadow(1.dp, CircleShape)
            .testTag("parent_gate_button")
        ) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Parent Centre (Protected)",
            tint = TextMuted,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
