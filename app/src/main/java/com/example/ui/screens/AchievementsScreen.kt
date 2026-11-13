package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun AchievementsScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val activeProfile by viewModel.activeProfile.collectAsState()
  val badges by viewModel.badges.collectAsState()
  val weeklyGoals by viewModel.weeklyGoals.collectAsState()
  val learningJourney by viewModel.learningJourney.collectAsState()
  var showCertificate by remember { mutableStateOf(false) }
  var selectedAchievementTab by remember { mutableIntStateOf(0) } // 0: Badges, 1: Learning Journey, 2: Weekly Goals

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("achievements_screen"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp)
  ) {
    // Top My Stars Header (Screen 16)
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(26.dp))
          .background(
            Brush.linearGradient(
              listOf(SunGoldLight, SunGold, CoralFun.copy(alpha = 0.85f))
            )
          )
          .padding(20.dp)
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("🏆", fontSize = 44.sp)
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "My Achievements & Star Vault",
            fontFamily = NunitoFontFamily,
            fontSize = 19.sp,
            fontWeight = FontWeight.ExtraBold,
            color = OnAmberContainer,
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = PureWhite.copy(alpha = 0.94f),
            shape = RoundedCornerShape(22.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Star, contentDescription = "Stars", tint = SunGoldDark, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "${activeProfile.stars} Learning Stars",
                fontFamily = NunitoFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = OnAmberContainer
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            color = PureWhite.copy(alpha = 0.72f),
            shape = RoundedCornerShape(14.dp)
          ) {
            Text(
              text = "🌿 Effort-based rewards • You never lose earned stars or badges when taking breaks!",
              fontFamily = NunitoSansFontFamily,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextDark,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }
      }
    }

    // Certificate Action Card
    item {
      Spacer(modifier = Modifier.height(14.dp))
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("view_certificate_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MintContainer)
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("📜", fontSize = 32.sp)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Certificate of Excellence",
              fontFamily = NunitoFontFamily,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark
            )
            Text(
              text = "Celebrate ${activeProfile.name}'s balanced learning milestones!",
              fontFamily = NunitoSansFontFamily,
              fontSize = 12.sp,
              color = OnMintContainer
            )
          }
          Button(
            onClick = { showCertificate = true },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.heightIn(min = 48.dp)
          ) {
            Text("View", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Section Switcher Tabs (My Badges | My Learning Journey | Weekly Learning Goals)
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = PureWhite,
        border = BorderStroke(1.dp, CardBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          val tabs = listOf("My Badges", "Journey", "Weekly Goals")
          tabs.forEachIndexed { idx, label ->
            val selected = selectedAchievementTab == idx
            Surface(
              onClick = { selectedAchievementTab = idx },
              shape = RoundedCornerShape(14.dp),
              color = if (selected) EmeraldPrimary else Color.Transparent,
              modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp)
                .testTag("achievement_tab_$idx")
            ) {
              Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 6.dp)) {
                Text(
                  text = label,
                  fontFamily = NunitoFontFamily,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (selected) PureWhite else TextMuted,
                  textAlign = TextAlign.Center
                )
              }
            }
          }
        }
      }
      Spacer(modifier = Modifier.height(14.dp))
    }

    when (selectedAchievementTab) {
      0 -> {
        item {
          Text(
            text = "My Badges (${badges.count { it.isUnlocked }} / ${badges.size} Unlocked)",
            fontFamily = NunitoFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 10.dp)
          )
        }
        items(badges) { badge ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 10.dp)
              .testTag("badge_card_${badge.id}"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (badge.isUnlocked) PureWhite else Color(0xFFFDFBF6)
            ),
            border = if (badge.isUnlocked) BorderStroke(1.dp, EmeraldLight.copy(alpha = 0.35f)) else BorderStroke(1.dp, CardBorder),
            elevation = CardDefaults.cardElevation(if (badge.isUnlocked) 2.dp else 0.dp)
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(54.dp)
                  .clip(CircleShape)
                  .background(if (badge.isUnlocked) AmberContainer else CardBorder.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = if (badge.isUnlocked) badge.iconEmoji else "🔒",
                  fontSize = 26.sp
                )
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = badge.name,
                    fontFamily = NunitoFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (badge.isUnlocked) TextDark else TextMuted
                  )
                  Surface(
                    color = if (badge.isUnlocked) MintContainer else Color(0xFFEEECE6),
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text(
                      text = if (badge.isUnlocked) "Earned ⭐" else "${(badge.progress * 100).toInt()}%",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (badge.isUnlocked) OnMintContainer else TextMuted,
                      modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                  text = badge.description,
                  fontFamily = NunitoSansFontFamily,
                  fontSize = 12.sp,
                  color = TextMuted
                )
                if (!badge.isUnlocked) {
                  Spacer(modifier = Modifier.height(6.dp))
                  LinearProgressIndicator(
                    progress = { badge.progress },
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(6.dp)
                      .clip(RoundedCornerShape(3.dp)),
                    color = SkyBlue,
                    trackColor = CardBorder
                  )
                }
              }
            }
          }
        }
      }

      1 -> {
        item {
          Text(
            text = "My Learning Journey",
            fontFamily = NunitoFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "A visual record of ${activeProfile.name}'s completed stories, lessons, games & memorization.",
            fontFamily = NunitoSansFontFamily,
            fontSize = 12.sp,
            color = TextMuted,
            modifier = Modifier.padding(bottom = 12.dp)
          )
        }
        items(learningJourney) { entry ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 10.dp)
              .testTag("journey_item_${entry.id}"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(1.dp)
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(48.dp)
                  .clip(RoundedCornerShape(14.dp))
                  .background(SkyContainer),
                contentAlignment = Alignment.Center
              ) {
                Text(entry.iconEmoji, fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = entry.title,
                  fontFamily = NunitoFontFamily,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextDark
                )
                Text(
                  text = "${entry.sectionType} • ${entry.completedLabel}",
                  fontFamily = NunitoSansFontFamily,
                  fontSize = 12.sp,
                  color = TextMuted
                )
              }
              Surface(shape = RoundedCornerShape(10.dp), color = AmberContainer) {
                Text(
                  text = "+${entry.starsEarned} ⭐",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = OnAmberContainer,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }

      2 -> {
        item {
          Surface(
            shape = RoundedCornerShape(18.dp),
            color = NightPurpleLight,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
          ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
              Text("🎯", fontSize = 26.sp)
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Weekly Learning Goals (Parent Configured)",
                  fontFamily = NunitoFontFamily,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = NightPurpleDark
                )
                Text(
                  text = "Encourages gentle, balanced learning across faith subjects — zero screen-time pressure.",
                  fontFamily = NunitoSansFontFamily,
                  fontSize = 11.sp,
                  color = TextDark
                )
              }
            }
          }
        }
        items(weeklyGoals) { goal ->
          val progress = (goal.completedCount.toFloat() / goal.targetCount.toFloat()).coerceIn(0f, 1f)
          val done = goal.completedCount >= goal.targetCount
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 10.dp)
              .testTag("weekly_goal_${goal.id}"),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(goal.iconEmoji, fontSize = 22.sp)
                  Spacer(modifier = Modifier.width(10.dp))
                  Column {
                    Text(
                      text = goal.areaName,
                      fontFamily = NunitoFontFamily,
                      fontSize = 14.sp,
                      fontWeight = FontWeight.Bold,
                      color = TextDark
                    )
                    Text(
                      text = "${goal.completedCount} of ${goal.targetCount} completed this week",
                      fontFamily = NunitoSansFontFamily,
                      fontSize = 12.sp,
                      color = TextMuted
                    )
                  }
                }
                if (done) {
                  Icon(Icons.Default.CheckCircle, contentDescription = "Goal Reached", tint = EmeraldPrimary)
                }
              }
              Spacer(modifier = Modifier.height(10.dp))
              LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(8.dp)
                  .clip(RoundedCornerShape(4.dp)),
                color = Color(goal.accentHex),
                trackColor = CardBorder
              )
            }
          }
        }
      }
    }
  }

  // Certificate Modal Dialog
  if (showCertificate) {
    Dialog(onDismissRequest = { showCertificate = false }) {
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = IvoryBackground,
        border = BorderStroke(3.dp, SunGoldDark),
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            fontFamily = NotoNaskhArabicFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark
          )
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "CERTIFICATE OF EXCELLENCE",
            fontFamily = NunitoFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = OnAmberContainer,
            textAlign = TextAlign.Center
          )
          Text(
            text = "My Muslim Kids Academy",
            fontFamily = NunitoSansFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextMuted
          )

          Spacer(modifier = Modifier.height(14.dp))
          Text("This honours certificate is proudly awarded to:", fontSize = 11.sp, color = TextDark)
          Text(
            text = activeProfile.name,
            fontFamily = NunitoFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = EmeraldPrimary
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "For joyful curiosity in learning Arabic letters, reciting short Surahs, practicing daily Sunnahs, and completing Islamic classroom lessons.",
            fontFamily = NunitoSansFontFamily,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = TextDark,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(14.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("⭐ ${activeProfile.stars}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SunGoldDark)
              Text("Stars Awarded", fontSize = 10.sp, color = TextMuted)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("🌱 ${activeProfile.ageGroup.title}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
              Text(activeProfile.ageGroup.ageRange, fontSize = 10.sp, color = TextMuted)
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = {
              viewModel.earnStars(10, "Certificate Celebrated!")
              showCertificate = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.heightIn(min = 48.dp)
          ) {
            Text("Alhamdulillah! Close", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
