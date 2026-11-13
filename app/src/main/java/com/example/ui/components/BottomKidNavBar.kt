package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SectionTab
import com.example.ui.theme.*

/**
 * SCREEN 5 — Child Navigation
 * 5 primary child-friendly bottom tabs:
 * 1. Home — My World (🌍)
 * 2. Stories — Story World (📖)
 * 3. Watch — My Muslim Kids TV (📺)
 * 4. Games — Fun Zone (🎮)
 * 5. Learn — Islamic Classroom (🏫)
 */
@Composable
fun BottomKidNavBar(
  currentSection: SectionTab,
  onSelectSection: (SectionTab) -> Unit,
  modifier: Modifier = Modifier
) {
  val primaryTabs = listOf(
    ChildNavTab(SectionTab.MY_WORLD, "Home", "🌍"),
    ChildNavTab(SectionTab.STORY_WORLD, "Stories", "📖"),
    ChildNavTab(SectionTab.MMK_TV, "Watch", "📺"),
    ChildNavTab(SectionTab.FUN_ZONE, "Games", "🎮"),
    ChildNavTab(SectionTab.CLASSROOM, "Learn", "🏫")
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding(),
    color = Color.White,
    tonalElevation = 8.dp,
    shadowElevation = 10.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      primaryTabs.forEach { tabItem ->
        val isSelected = currentSection == tabItem.section
        val activeBg by animateColorAsState(
          targetValue = if (isSelected) MintContainer else Color.Transparent,
          label = "tab_bg"
        )
        val iconColor by animateColorAsState(
          targetValue = if (isSelected) EmeraldPrimary else TextMuted,
          label = "tab_text"
        )

        Column(
          modifier = Modifier
            .defaultMinSize(minWidth = 56.dp, minHeight = 48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(activeBg)
            .clickable { onSelectSection(tabItem.section) }
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("nav_tab_${tabItem.section.name.lowercase()}"),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = tabItem.iconEmoji,
            fontSize = if (isSelected) 22.sp else 20.sp
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = tabItem.label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
            color = iconColor
          )
        }
      }
    }
  }
}

private data class ChildNavTab(
  val section: SectionTab,
  val label: String,
  val iconEmoji: String
)
