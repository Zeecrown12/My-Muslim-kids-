package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.KidsRepository
import com.example.model.AgeGroup
import com.example.model.SectionTab
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

/**
 * SCREEN 4 — Child Home: My World
 * Personalised home hub featuring Continue Journey, Story World, MMK TV,
 * Songs, Fun Zone, Classroom, Quran & Arabic, Duas, and Achievements.
 */
@Composable
fun HomeScreen(
  viewModel: KidsViewModel,
  onNavigateSection: (SectionTab) -> Unit,
  modifier: Modifier = Modifier
) {
  val activeProfile by viewModel.activeProfile.collectAsState()
  val allProfiles by viewModel.profiles.collectAsState()
  val ageFilter by viewModel.ageGroupFilter.collectAsState()

  var showProfileSwitchDialog by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("home_screen"),
    contentPadding = PaddingValues(bottom = 90.dp)
  ) {
    // 1. Top Section: Personalised Greeting & Header
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .clip(RoundedCornerShape(28.dp))
          .background(
            Brush.verticalGradient(
              listOf(EmeraldPrimary, EmeraldLight)
            )
          )
          .padding(20.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Assalamu Alaikum,",
                fontSize = 15.sp,
                color = MintContainer,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = "${activeProfile.name}! 🌟",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
              )
              Text(
                text = activeProfile.ageGroup.title,
                fontSize = 12.sp,
                color = SunGoldLight,
                fontWeight = FontWeight.SemiBold
              )
            }

            // Clickable Child Avatar (opens profile switcher)
            Surface(
              shape = RoundedCornerShape(24.dp),
              color = Color.White.copy(alpha = 0.2f),
              modifier = Modifier
                .clickable { showProfileSwitchDialog = true }
                .testTag("child_avatar_switcher")
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = activeProfile.avatarEmoji, fontSize = 28.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.SwapHoriz,
                  contentDescription = "Switch Profile",
                  tint = Color.White,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Brand Tagline Banner
          Surface(
            color = Color.White.copy(alpha = 0.15f),
            shape = RoundedCornerShape(14.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("✨", fontSize = 13.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Learn • Play • Watch • Explore • Grow in Faith",
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }

    // 2. Age Group Selector Chips
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AgeGroup.values().forEach { group ->
          val isSelected = ageFilter == group
          Surface(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(16.dp))
              .clickable { viewModel.setAgeFilter(group) }
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) EmeraldPrimary else CardBorder,
                shape = RoundedCornerShape(16.dp)
              ),
            color = if (isSelected) MintContainer else Color.White
          ) {
            Column(
              modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(text = group.emoji, fontSize = 16.sp)
              Text(
                text = group.title.split(" ").first(),
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) OnMintContainer else TextDark
              )
              Text(
                text = group.ageRange,
                fontSize = 9.sp,
                color = TextMuted
              )
            }
          }
        }
      }
    }

    // 3. Continue Your Journey Card (Resume last story/lesson)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .testTag("continue_journey_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SkyContainer),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("🚀", fontSize = 18.sp)
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Continue Your Journey",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = SkyBlueDark
              )
            }
            Surface(
              color = Color.White,
              shape = RoundedCornerShape(10.dp)
            ) {
              Text(
                text = "In Progress",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = SkyBlueDark
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Story: Prophet Yunus & the Whale",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Page 2 of 4 — In the deep ocean with the gentle whale",
            fontSize = 12.sp,
            color = TextMuted
          )

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = SkyBlue,
            trackColor = Color.White
          )

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = {
              val story = KidsRepository.stories.first()
              viewModel.openStory(story)
              onNavigateSection(SectionTab.STORY_WORLD)
            },
            colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().testTag("resume_journey_button")
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Resume Story Now", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
    }

    // 4. Main Content Sections Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Main Content Sections",
          fontSize = 17.sp,
          fontWeight = FontWeight.ExtraBold,
          color = TextDark
        )
        Text(
          text = "Learn & Play",
          fontSize = 12.sp,
          color = TextMuted
        )
      }
    }

    // 5. Grid of Content Section Cards
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        val sections = listOf(
          SectionCardInfo("Story World", "Illustrated Islamic Stories", "📖", SectionTab.STORY_WORLD, Color(0xFFEFF6FF), SkyBlueDark),
          SectionCardInfo("My Muslim Kids TV", "Original Cartoons & Shows", "📺", SectionTab.MMK_TV, Color(0xFFFFF1F2), CoralFun),
          SectionCardInfo("Songs & Nasheeds", "Sing-Along Islamic Audio", "🎵", SectionTab.SONGS, Color(0xFFF0FDF4), EmeraldPrimary),
          SectionCardInfo("Fun Zone", "Quizzes, Puzzles & Games", "🎮", SectionTab.FUN_ZONE, Color(0xFFFEF3C7), SunGoldDark),
          SectionCardInfo("Islamic Classroom", "Structured Lessons & Salah", "🏫", SectionTab.CLASSROOM, Color(0xFFF3E8FF), NightPurpleDark),
          SectionCardInfo("Quran & Arabic", "Letters & Juz Amma Surahs", "🌙", SectionTab.QURAN_ARABIC, Color(0xFFECFDF5), EmeraldDark),
          SectionCardInfo("Duas & Sunnah", "Daily Duas & Sunnah Habits", "🤲", SectionTab.DUAS_SUNNAH, Color(0xFFFFF7ED), Color(0xFFC2410C)),
          SectionCardInfo("My Achievements", "Stars, Badges & Certificates", "🏆", SectionTab.ACHIEVEMENTS, Color(0xFFFEF9C3), Color(0xFF854D0E))
        )

        sections.chunked(2).forEach { rowPair ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            rowPair.forEach { card ->
              Surface(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(20.dp))
                  .clickable { onNavigateSection(card.tab) }
                  .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
                  .testTag("home_section_${card.tab.name.lowercase()}"),
                color = card.bgColor,
                shadowElevation = 1.dp
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Box(
                    modifier = Modifier
                      .size(42.dp)
                      .clip(CircleShape)
                      .background(Color.White),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(text = card.emoji, fontSize = 22.sp)
                  }
                  Spacer(modifier = Modifier.height(10.dp))
                  Text(
                    text = card.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = card.textColor
                  )
                  Text(
                    text = card.subtitle,
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  // Profile Switcher Dialog
  if (showProfileSwitchDialog) {
    Dialog(onDismissRequest = { showProfileSwitchDialog = false }) {
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text(
            text = "Switch Child Profile",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(12.dp))

          allProfiles.forEach { profile ->
            val isCurrent = profile.id == activeProfile.id
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = if (isCurrent) MintContainer else IvoryBackground,
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable {
                  viewModel.switchProfile(profile)
                  showProfileSwitchDialog = false
                }
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(profile.avatarEmoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = profile.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                  )
                  Text(
                    text = "${profile.age} years old • ${profile.ageGroup.title}",
                    fontSize = 11.sp,
                    color = TextMuted
                  )
                }
                if (isCurrent) {
                  Icon(Icons.Default.Check, contentDescription = "Active", tint = EmeraldPrimary)
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          TextButton(
            onClick = {
              showProfileSwitchDialog = false
              onNavigateSection(SectionTab.PARENT_CENTRE)
            },
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("+ Manage Profiles in Parent Centre", color = EmeraldPrimary, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

private data class SectionCardInfo(
  val title: String,
  val subtitle: String,
  val emoji: String,
  val tab: SectionTab,
  val bgColor: Color,
  val textColor: Color
)
