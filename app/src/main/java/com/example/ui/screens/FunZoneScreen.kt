package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
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
import com.example.model.GameCardItem
import com.example.model.LearningAdventure
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun FunZoneScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val activeGameCard by viewModel.activeGameCard.collectAsState()
  val activeAdventure by viewModel.activeAdventure.collectAsState()
  val selectedCategory by viewModel.selectedGameCategory.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("fun_zone_screen")
  ) {
    if (activeGameCard != null) {
      // Screen 12: Game Play Screen
      GamePlayScreen(
        gameCard = activeGameCard!!,
        viewModel = viewModel,
        onClose = { viewModel.closeGameCard() }
      )
    } else {
      // Screen 11: Fun Zone Main Menu & Adventure Hub
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
      ) {
        // Hero Banner
        item {
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = AmberContainer,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 14.dp)
          ) {
            Row(
              modifier = Modifier.padding(18.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Fun Zone Arcade 🎮",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = OnAmberContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Interactive Islamic games, puzzles, and exciting learning adventures!",
                  fontSize = 13.sp,
                  color = Color(0xFF78350F)
                )
              }
              Text("🎯", fontSize = 36.sp)
            }
          }
        }

        // Section: Themed Learning Adventures (Screen 11 Feature)
        item {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Learning Adventures 🗺️",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark
            )
            Text(
              text = "5 Quests",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = SunGoldDark
            )
          }

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
          ) {
            items(KidsRepository.learningAdventures) { adventure ->
              AdventureCard(
                adventure = adventure,
                onClick = { viewModel.openAdventure(adventure) }
              )
            }
          }
        }

        // Category Filter Chips (8 Categories from Screen 11 spec)
        item {
          Text(
            text = "Game Categories",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          val categories = listOf(
            "All",
            "Islamic Knowledge Quizzes",
            "Arabic Alphabet Games",
            "Islamic Matching Games",
            "Wudu and Salah Activities",
            "Memory Games",
            "Islamic Puzzles",
            "Good-Deeds Challenges",
            "Islamic Treasure Hunts"
          )

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
          ) {
            items(categories) { cat ->
              val isSelected = selectedCategory == cat
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.selectedGameCategory.value = cat },
                label = {
                  Text(
                    text = cat,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = EmeraldPrimary,
                  selectedLabelColor = Color.White,
                  containerColor = Color.White,
                  labelColor = Color(0xFF334155)
                )
              )
            }
          }
        }

        // Filtered Games List
        val filteredGames = KidsRepository.gameCards.filter {
          selectedCategory == "All" || it.category == selectedCategory
        }

        items(filteredGames) { game ->
          GameCardRow(
            gameCard = game,
            onPlay = { viewModel.openGameCard(game) }
          )
          Spacer(modifier = Modifier.height(12.dp))
        }
      }
    }

    // Adventure Detail Dialog
    if (activeAdventure != null) {
      AdventureDetailDialog(
        adventure = activeAdventure!!,
        onDismiss = { viewModel.closeAdventure() },
        onStartActivity = { act ->
          // Find related game or start quest
          val matchedGame = KidsRepository.gameCards.firstOrNull { it.title.contains("Quiz", true) }
            ?: KidsRepository.gameCards[0]
          viewModel.closeAdventure()
          viewModel.openGameCard(matchedGame)
        }
      )
    }
  }
}

@Composable
fun AdventureCard(
  adventure: LearningAdventure,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .width(220.dp)
      .clickable(onClick = onClick)
      .testTag("adventure_card_${adventure.id}"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MintContainer),
          contentAlignment = Alignment.Center
        ) {
          Text(adventure.iconEmoji, fontSize = 22.sp)
        }
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = AmberContainer
        ) {
          Text(
            text = "+${adventure.starReward} ⭐",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = OnAmberContainer,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = adventure.title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = EmeraldDark,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = adventure.description,
        fontSize = 11.sp,
        color = Color.Gray,
        maxLines = 2
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text("3 Steps", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = EmeraldPrimary)
        Spacer(modifier = Modifier.width(4.dp))
        Text("• ${adventure.rewardBadgeName}", fontSize = 11.sp, color = Color(0xFF64748B), maxLines = 1)
      }
    }
  }
}

@Composable
fun GameCardRow(
  gameCard: GameCardItem,
  onPlay: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onPlay)
      .testTag("game_card_${gameCard.id}"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(60.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(MintContainer),
        contentAlignment = Alignment.Center
      ) {
        Text(gameCard.illustrationEmoji, fontSize = 30.sp)
      }
      Spacer(modifier = Modifier.width(14.dp))
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MintContainer
          ) {
            Text(
              text = gameCard.difficulty,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = gameCard.targetAge.title,
            fontSize = 11.sp,
            color = Color.Gray
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = gameCard.title,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = gameCard.description,
          fontSize = 12.sp,
          color = Color(0xFF475569),
          maxLines = 2
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Button(
        onClick = onPlay,
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
        modifier = Modifier.testTag("play_button_${gameCard.id}")
      ) {
        Icon(Icons.Default.PlayArrow, contentDescription = "Play", modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Play", fontWeight = FontWeight.Bold, fontSize = 13.sp)
      }
    }
  }
}

@Composable
fun AdventureDetailDialog(
  adventure: LearningAdventure,
  onDismiss: () -> Unit,
  onStartActivity: (adventureActivity: String) -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(adventure.iconEmoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(adventure.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
              Text(adventure.theme, fontSize = 12.sp, color = Color.Gray)
            }
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(adventure.description, fontSize = 13.sp, color = Color(0xFF334155))
        Spacer(modifier = Modifier.height(16.dp))

        Text("Adventure Quest Steps:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(8.dp))

        adventure.activities.forEachIndexed { idx, act ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (act.isCompleted) Color(0xFFDCFCE7) else AmberContainer),
              contentAlignment = Alignment.Center
            ) {
              if (act.isCompleted) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Done", tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
              } else {
                Text("${idx + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnAmberContainer)
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = act.title,
              fontSize = 13.sp,
              color = Color(0xFF1E293B),
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = { onStartActivity("start") },
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Begin Adventure Quest! 🌟", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
