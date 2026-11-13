package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.KidsRepository
import com.example.model.DuaItem
import com.example.model.SunnahHabitItem
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun DuasSunnahScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val selectedTab by viewModel.duasTab.collectAsState()
  val duas by viewModel.duas.collectAsState()
  val habits by viewModel.sunnahHabits.collectAsState()
  val activeDuaForPlayer by viewModel.activeDuaForPlayer.collectAsState()
  val favoriteDuaIds by viewModel.favoriteDuaIds.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("duas_sunnah_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // 4 Sub-Tabs Header (Daily Duas, Sunnah Habits, Tasbih Adhkar, My Daily Duas)
      Surface(
        color = Color.White,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        ScrollableTabRow(
          selectedTabIndex = selectedTab,
          containerColor = Color.White,
          contentColor = EmeraldPrimary,
          edgePadding = 16.dp
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = { viewModel.duasTab.value = 0 },
            text = { Text("Daily Duas 🤲", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 1,
            onClick = { viewModel.duasTab.value = 1 },
            text = { Text("Sunnah Habits ⭐", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 2,
            onClick = { viewModel.duasTab.value = 2 },
            text = { Text("Tasbih Adhkar 📿", fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 3,
            onClick = { viewModel.duasTab.value = 3 },
            text = { Text("My Daily Routines 🌅", fontWeight = FontWeight.Bold) }
          )
        }
      }

      // Content for active tab
      when (selectedTab) {
        0 -> DailyDuasTabContent(duas, favoriteDuaIds, viewModel)
        1 -> SunnahHabitsTabContent(habits, viewModel)
        2 -> TasbihAdhkarTabContent(viewModel)
        3 -> MyDailyRoutinesTabContent(duas, viewModel)
      }
    }

    // Screen 15: Dua Learning Player Modal
    if (activeDuaForPlayer != null) {
      DuaLearningPlayerDialog(
        dua = activeDuaForPlayer!!,
        viewModel = viewModel,
        onClose = { viewModel.closeDuaPlayer() }
      )
    }
  }
}

// ------------------------------------
// 1. Daily Duas Tab
// ------------------------------------
@Composable
fun DailyDuasTabContent(
  duas: List<DuaItem>,
  favoriteDuaIds: Set<String>,
  viewModel: KidsViewModel
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize(),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = AmberContainer,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 14.dp)
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Daily Duas & Supplications 🤲",
              fontSize = 18.sp,
              fontWeight = FontWeight.ExtraBold,
              color = OnAmberContainer
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Every dua is verified from authentic Hadith collections. Tap to listen and learn!",
              fontSize = 12.sp,
              color = Color(0xFF78350F)
            )
          }
          Text("✨", fontSize = 28.sp)
        }
      }
    }

    items(duas) { dua ->
      val isFav = favoriteDuaIds.contains(dua.id)
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp)
          .clickable { viewModel.openDuaPlayer(dua) }
          .testTag("dua_card_${dua.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Header Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(dua.emoji, fontSize = 24.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = dua.title,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  color = EmeraldDark
                )
                Text(
                  text = "When: ${dua.situation}",
                  fontSize = 11.sp,
                  color = Color.Gray
                )
              }
            }

            IconButton(onClick = { viewModel.toggleDuaFavorite(dua.id) }) {
              Icon(
                if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFav) Color(0xFFE11D48) else Color.Gray
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Arabic with full Tashkeel
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF8FAFC),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = dua.arabicText,
              fontFamily = NotoNaskhArabicFontFamily,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldPrimary,
              textAlign = TextAlign.End,
              lineHeight = 32.sp,
              modifier = Modifier.padding(12.dp)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Transliteration
          Text(
            text = dua.transliteration,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF334155),
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(4.dp))

          // Translation
          Text(
            text = "\"${dua.englishTranslation}\"",
            fontSize = 12.sp,
            color = Color(0xFF475569),
            lineHeight = 16.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Source and Actions Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(shape = RoundedCornerShape(8.dp), color = MintContainer) {
              Text(
                text = "Ref: ${dua.sourceRef}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              OutlinedButton(
                onClick = { viewModel.toggleDuaMemorized(dua.id) },
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
              ) {
                if (dua.isMemorized) {
                  Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Memorized ⭐", fontSize = 11.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                } else {
                  Text("Mark Memorized", fontSize = 11.sp)
                }
              }

              Spacer(modifier = Modifier.width(8.dp))

              FilledTonalIconButton(
                onClick = { viewModel.openDuaPlayer(dua) },
                colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = EmeraldPrimary),
                modifier = Modifier.size(36.dp)
              ) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Play Audio", tint = Color.White, modifier = Modifier.size(18.dp))
              }
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// 2. Sunnah Habits Checklist Tab
// ------------------------------------
@Composable
fun SunnahHabitsTabContent(
  habits: List<SunnahHabitItem>,
  viewModel: KidsViewModel
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
      ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
          Column(modifier = Modifier.weight(1f)) {
            Text("Sunnah Habits Checklist ⭐", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = EmeraldDark)
            Spacer(modifier = Modifier.height(2.dp))
            Text("Follow the steps of our beloved Prophet ﷺ every day for extra barakah!", fontSize = 12.sp, color = Color(0xFF047857))
          }
          Text("🌱", fontSize = 28.sp)
        }
      }
    }

    items(habits) { habit ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp)
          .clickable { viewModel.toggleSunnahHabit(habit.id) }
          .testTag("habit_card_${habit.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = if (habit.isDoneToday) Color(0xFFDCFCE7) else Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(habit.iconEmoji, fontSize = 26.sp)
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(habit.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
            Text(habit.description, fontSize = 12.sp, color = Color(0xFF475569))
            Text("Source: ${habit.hadithRef}", fontSize = 10.sp, color = Color.Gray)
          }
          Checkbox(
            checked = habit.isDoneToday,
            onCheckedChange = { viewModel.toggleSunnahHabit(habit.id) },
            colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
          )
        }
      }
    }
  }
}

// ------------------------------------
// 3. Tasbih Adhkar Tab (Interactive Counter)
// ------------------------------------
@Composable
fun TasbihAdhkarTabContent(viewModel: KidsViewModel) {
  val currentCount by viewModel.tasbihCount.collectAsState()
  val activeDhikr by viewModel.activeTasbihDhikr.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Dhikr Selector Chips
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 14.dp)
    ) {
      items(KidsRepository.tasbihDhikrs) { dhikr ->
        val isSelected = activeDhikr.id == dhikr.id
        FilterChip(
          selected = isSelected,
          onClick = { viewModel.selectTasbihDhikr(dhikr) },
          label = { Text(dhikr.transliteration, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    // Active Dhikr Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(3.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(activeDhikr.arabic, fontFamily = NotoNaskhArabicFontFamily, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(activeDhikr.transliteration, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Text("\"${activeDhikr.englishMeaning}\"", fontSize = 13.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(shape = RoundedCornerShape(8.dp), color = MintContainer) {
          Text(activeDhikr.rewardNotice, fontSize = 11.sp, color = EmeraldDark, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Interactive Tap Counter Bead
    Box(
      modifier = Modifier
        .size(160.dp)
        .clip(CircleShape)
        .background(
          Brush.radialGradient(
            colors = listOf(MintContainer, EmeraldPrimary)
          )
        )
        .clickable { viewModel.incrementTasbih() }
        .testTag("tasbih_counter_button"),
      contentAlignment = Alignment.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$currentCount", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        Text("/ ${activeDhikr.targetCount}", fontSize = 14.sp, color = Color(0xFFDCFCE7), fontWeight = FontWeight.Bold)
        Text("TAP 📿", fontSize = 12.sp, color = SunGold, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedButton(
      onClick = { viewModel.resetTasbih() },
      shape = RoundedCornerShape(12.dp)
    ) {
      Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text("Reset Counter", fontSize = 12.sp)
    }
  }
}

// ------------------------------------
// 4. "My Daily Duas" Gentle Routine Tab
// ------------------------------------
@Composable
fun MyDailyRoutinesTabContent(
  duas: List<DuaItem>,
  viewModel: KidsViewModel
) {
  val routines = listOf(
    "Morning Routine" to listOf("dua_wake_up", "dua_enter_bathroom", "dua_leave_bathroom"),
    "Mealtime Routine" to listOf("dua_before_eating", "dua_after_eating"),
    "Going Out & Coming Home" to listOf("dua_leaving_home", "dua_entering_home"),
    "Bedtime Routine" to listOf("dua_sleeping")
  )

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = SkyContainer,
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
      ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
          Column(modifier = Modifier.weight(1f)) {
            Text("My Daily Duas Routine 🌅", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = SkyBlueDark)
            Spacer(modifier = Modifier.height(2.dp))
            Text("A gentle, unpressured daily routine to bring rememberance of Allah into every moment.", fontSize = 12.sp, color = Color(0xFF0369A1))
          }
          Text("🕊️", fontSize = 28.sp)
        }
      }
    }

    routines.forEach { (routineName, duaIds) ->
      item {
        Text(
          text = routineName,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark,
          modifier = Modifier.padding(vertical = 8.dp)
        )
      }

      val routineDuas = duas.filter { duaIds.contains(it.id) }
      items(routineDuas) { dua ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { viewModel.openDuaPlayer(dua) }
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(dua.emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(dua.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
              Text(dua.arabicText, fontFamily = NotoNaskhArabicFontFamily, fontSize = 15.sp, color = EmeraldPrimary, maxLines = 1)
            }
            FilledTonalIconButton(
              onClick = { viewModel.openDuaPlayer(dua) },
              colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MintContainer),
              modifier = Modifier.size(32.dp)
            ) {
              Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// Screen 15: Dua Learning Player Dialog
// ------------------------------------
@Composable
fun DuaLearningPlayerDialog(
  dua: DuaItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val isPlaying by viewModel.isDuaAudioPlaying.collectAsState()
  val audioSpeed by viewModel.duaAudioSpeed.collectAsState()

  Dialog(onDismissRequest = onClose) {
    Card(
      shape = RoundedCornerShape(26.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(shape = RoundedCornerShape(8.dp), color = MintContainer) {
            Text(dua.category, fontSize = 11.sp, color = EmeraldDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
          }
          IconButton(onClick = onClose) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(MintContainer),
          contentAlignment = Alignment.Center
        ) {
          Text(dua.emoji, fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(dua.title, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = EmeraldDark)
        Text("When to recite: ${dua.situation}", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(12.dp))

        // Large Arabic Text with full Tashkeel
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFF8FAFC),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = dua.arabicText,
            fontFamily = NotoNaskhArabicFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp,
            modifier = Modifier.padding(14.dp)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Transliteration & Translation
        Text(dua.transliteration, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(4.dp))
        Text("\"${dua.englishTranslation}\"", fontSize = 12.sp, color = Color(0xFF475569), textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(12.dp))

        // Sunnah Etiquette Box
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = AmberContainer,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("💡", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text("Sunnah Etiquette", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnAmberContainer)
              Text(dua.sunnahEtiquette, fontSize = 11.sp, color = Color(0xFF78350F))
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Speed & Audio Playback Controls
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf(0.8f, 1.0f, 1.2f).forEach { spd ->
              FilterChip(
                selected = audioSpeed == spd,
                onClick = { viewModel.setDuaAudioSpeed(spd) },
                label = { Text("${spd}x", fontSize = 10.sp) }
              )
            }
          }

          Button(
            onClick = { viewModel.toggleDuaAudioPlay() },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(if (isPlaying) Icons.Default.Pause else Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(if (isPlaying) "Pause" else "Repeat Audio", fontSize = 12.sp)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
          onClick = {
            viewModel.toggleDuaMemorized(dua.id)
            onClose()
          },
          colors = ButtonDefaults.buttonColors(containerColor = SunGoldDark),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("I Memorized This Dua! (+10 ⭐)")
        }
      }
    }
  }
}
