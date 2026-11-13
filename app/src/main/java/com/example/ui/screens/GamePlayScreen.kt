package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.data.KidsRepository
import com.example.model.GameCardItem
import com.example.model.GameplayType
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GamePlayScreen(
  gameCard: GameCardItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("game_play_screen_${gameCard.id}")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Header Bar
      Surface(
        color = Color.White,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onClose,
            modifier = Modifier.testTag("game_play_back_button")
          ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back to Fun Zone", tint = EmeraldPrimary)
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = gameCard.title,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark
            )
            Text(
              text = "${gameCard.category} • ${gameCard.difficulty}",
              fontSize = 12.sp,
              color = Color.Gray
            )
          }
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = AmberContainer
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("⭐", fontSize = 14.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "+${gameCard.starReward}",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = OnAmberContainer
              )
            }
          }
        }
      }

      // Game Content based on gameplay type
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
      ) {
        when (gameCard.gameplayType) {
          GameplayType.MULTIPLE_CHOICE -> MultipleChoiceQuizGame(gameCard, viewModel, onClose)
          GameplayType.MATCHING_PAIRS -> MatchingPairsGame(gameCard, viewModel, onClose)
          GameplayType.STEP_ORDERING -> StepOrderingGame(gameCard, viewModel, onClose)
          GameplayType.MEMORY_MATCH -> MemoryCardsGame(gameCard, viewModel, onClose)
          GameplayType.PICTURE_PUZZLE -> PicturePuzzleGame(gameCard, viewModel, onClose)
        }
      }
    }
  }
}

// ==========================================
// A. MULTIPLE-CHOICE QUIZ (Screen 12 - A)
// ==========================================
@Composable
fun MultipleChoiceQuizGame(
  gameCard: GameCardItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val questions = KidsRepository.triviaQuestions
  var currentIndex by remember { mutableIntStateOf(0) }
  var selectedOption by remember { mutableStateOf<Int?>(null) }
  var isAnswerChecked by remember { mutableStateOf(false) }
  var score by remember { mutableIntStateOf(0) }
  var quizCompleted by remember { mutableStateOf(false) }

  val q = questions[currentIndex]

  if (quizCompleted) {
    QuizCompletionCelebration(score = score, total = questions.size, onDone = onClose)
  } else {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      item {
        LinearProgressIndicator(
          progress = { (currentIndex + 1).toFloat() / questions.size },
          modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
          color = EmeraldPrimary,
          trackColor = MintContainer
        )
        Spacer(modifier = Modifier.height(16.dp))

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
            Box(
              modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MintContainer),
              contentAlignment = Alignment.Center
            ) {
              Text("❓", fontSize = 32.sp)
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = "Question ${currentIndex + 1} of ${questions.size}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = q.question,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark,
              textAlign = TextAlign.Center
            )
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
      }

      itemsIndexed(q.options) { idx, optionText ->
        val isSelected = selectedOption == idx
        val isCorrect = idx == q.correctIndex
        val containerColor = when {
          !isAnswerChecked -> if (isSelected) MintContainer else Color.White
          isCorrect -> Color(0xFFDCFCE7)
          isSelected && !isCorrect -> Color(0xFFFFEDD5)
          else -> Color.White
        }
        val borderColor = when {
          !isAnswerChecked -> if (isSelected) EmeraldPrimary else Color(0xFFE2E8F0)
          isCorrect -> EmeraldPrimary
          isSelected && !isCorrect -> CoralFun
          else -> Color(0xFFE2E8F0)
        }

        Surface(
          shape = RoundedCornerShape(18.dp),
          color = containerColor,
          border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(enabled = !isAnswerChecked) {
              selectedOption = idx
            }
            .testTag("quiz_option_$idx")
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isSelected) EmeraldPrimary else MintContainer),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${'A' + idx}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else EmeraldDark
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = optionText,
              fontSize = 15.sp,
              fontWeight = FontWeight.Medium,
              color = EmeraldDark,
              modifier = Modifier.weight(1f)
            )
            if (isAnswerChecked && isCorrect) {
              Text("✅", fontSize = 18.sp)
            }
          }
        }
      }

      item {
        Spacer(modifier = Modifier.height(16.dp))
        if (!isAnswerChecked) {
          Button(
            onClick = {
              if (selectedOption != null) {
                isAnswerChecked = true
                if (selectedOption == q.correctIndex) {
                  score++
                  viewModel.earnStars(5, "Correct Answer! Masha'Allah! ⭐")
                }
              }
            },
            enabled = selectedOption != null,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("quiz_check_button")
          ) {
            Text("Check Answer ✨", fontSize = 16.sp, fontWeight = FontWeight.Bold)
          }
        } else {
          // Explanation & Next
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (selectedOption == q.correctIndex) Color(0xFFDCFCE7) else AmberContainer,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = if (selectedOption == q.correctIndex) "Masha'Allah! Wonderful job! 🌟" else "Almost there! Keep trying 🌱",
                fontWeight = FontWeight.Bold,
                color = if (selectedOption == q.correctIndex) EmeraldDark else OnAmberContainer
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = q.explanation,
                fontSize = 13.sp,
                color = EmeraldDark
              )
            }
          }
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = {
              if (currentIndex < questions.size - 1) {
                currentIndex++
                selectedOption = null
                isAnswerChecked = false
              } else {
                quizCompleted = true
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("quiz_next_button")
          ) {
            Text(
              text = if (currentIndex < questions.size - 1) "Next Question ➡️" else "Finish Quiz 🏆",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

// ==========================================
// B. MATCHING PAIRS GAME (Screen 12 - B)
// ==========================================
@Composable
fun MatchingPairsGame(
  gameCard: GameCardItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val pairs = remember { KidsRepository.matchingPairs.take(4) }
  val leftItems = remember { pairs.map { it.leftItem } }
  val rightItems = remember { pairs.map { it.rightItem }.shuffled() }

  var selectedLeft by remember { mutableStateOf<String?>(null) }
  var selectedRight by remember { mutableStateOf<String?>(null) }
  val matchedPairs = remember { mutableStateListOf<String>() }

  val coroutineScope = rememberCoroutineScope()

  fun checkMatch(left: String, right: String) {
    val isMatch = pairs.any { it.leftItem == left && it.rightItem == right }
    if (isMatch) {
      matchedPairs.add(left)
      viewModel.earnStars(5, "Pair Matched! ⭐")
      selectedLeft = null
      selectedRight = null
      if (matchedPairs.size == pairs.size) {
        viewModel.earnStars(10, "All Pairs Matched! Barakallahu Feek! 🏆")
      }
    } else {
      coroutineScope.launch {
        delay(600)
        selectedLeft = null
        selectedRight = null
      }
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = MintContainer,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("🧩", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Tap an item on the left, then tap its match on the right!",
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          color = EmeraldDark
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Left Column
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        leftItems.forEach { item ->
          val isMatched = matchedPairs.contains(item)
          val isSelected = selectedLeft == item
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = when {
              isMatched -> Color(0xFFDCFCE7)
              isSelected -> AmberContainer
              else -> Color.White
            },
            border = androidx.compose.foundation.BorderStroke(
              1.5.dp,
              if (isSelected) OnAmberContainer else if (isMatched) EmeraldPrimary else Color(0xFFE2E8F0)
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(72.dp)
              .clickable(enabled = !isMatched) {
                selectedLeft = item
                if (selectedRight != null) checkMatch(item, selectedRight!!)
              }
              .testTag("match_left_${item.take(4)}")
          ) {
            Box(
              modifier = Modifier.fillMaxSize().padding(8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = item,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isMatched) EmeraldDark else Color(0xFF1E293B),
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }

      // Right Column
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        rightItems.forEach { item ->
          val matchedLeft = pairs.firstOrNull { it.rightItem == item }?.leftItem
          val isMatched = matchedLeft != null && matchedPairs.contains(matchedLeft)
          val isSelected = selectedRight == item
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = when {
              isMatched -> Color(0xFFDCFCE7)
              isSelected -> AmberContainer
              else -> Color.White
            },
            border = androidx.compose.foundation.BorderStroke(
              1.5.dp,
              if (isSelected) OnAmberContainer else if (isMatched) EmeraldPrimary else Color(0xFFE2E8F0)
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(72.dp)
              .clickable(enabled = !isMatched) {
                selectedRight = item
                if (selectedLeft != null) checkMatch(selectedLeft!!, item)
              }
              .testTag("match_right_${item.take(4)}")
          ) {
            Box(
              modifier = Modifier.fillMaxSize().padding(8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = item,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = if (isMatched) EmeraldDark else Color(0xFF1E293B),
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }
    }

    if (matchedPairs.size == pairs.size) {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("🎉 All Matched! Masha'Allah! ⭐", fontWeight = FontWeight.Bold, color = EmeraldDark)
          Spacer(modifier = Modifier.height(8.dp))
          Button(
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(14.dp)
          ) {
            Text("Collect Rewards & Done! 🏆")
          }
        }
      }
    }
  }
}

// ==========================================
// C. STEP ORDERING ACTIVITY (Screen 12 - C)
// ==========================================
@Composable
fun StepOrderingGame(
  gameCard: GameCardItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val originalSteps = remember { KidsRepository.wuduSteps }
  val currentSteps = remember { mutableStateListOf<KidsRepository.StepItem>().apply { addAll(originalSteps.shuffled()) } }
  var isSequenceCorrect by remember { mutableStateOf(false) }
  var feedbackMsg by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = SkyContainer,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("💧", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Arrange the 8 steps of Wudu in the correct Sunnah sequence using the ⬆️ ⬇️ arrows!",
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          color = SkyBlueDark
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      itemsIndexed(currentSteps) { idx, step ->
        val isCorrectPosition = step.stepNumber == (idx + 1)
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = if (isSequenceCorrect) Color(0xFFDCFCE7) else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSequenceCorrect) EmeraldPrimary else Color(0xFFE2E8F0)
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MintContainer),
              contentAlignment = Alignment.Center
            ) {
              Text("${idx + 1}", fontWeight = FontWeight.Bold, color = EmeraldDark)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(step.emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(step.title, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E293B))
              if (step.arabicText != null) {
                Text(step.arabicText, fontSize = 12.sp, color = EmeraldPrimary)
              }
            }
            // Move buttons
            IconButton(
              onClick = {
                if (idx > 0) {
                  val temp = currentSteps[idx]
                  currentSteps[idx] = currentSteps[idx - 1]
                  currentSteps[idx - 1] = temp
                }
              },
              enabled = idx > 0 && !isSequenceCorrect,
              modifier = Modifier.size(36.dp)
            ) {
              Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Move Up", tint = EmeraldPrimary)
            }
            IconButton(
              onClick = {
                if (idx < currentSteps.size - 1) {
                  val temp = currentSteps[idx]
                  currentSteps[idx] = currentSteps[idx + 1]
                  currentSteps[idx + 1] = temp
                }
              },
              enabled = idx < currentSteps.size - 1 && !isSequenceCorrect,
              modifier = Modifier.size(36.dp)
            ) {
              Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Move Down", tint = EmeraldPrimary)
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    if (!isSequenceCorrect) {
      Button(
        onClick = {
          val isCorrect = currentSteps.mapIndexed { i, s -> s.stepNumber == i + 1 }.all { it }
          if (isCorrect) {
            isSequenceCorrect = true
            viewModel.earnStars(15, "Wudu Sequence Solved! +15 Stars ⭐")
          } else {
            val correctCount = currentSteps.mapIndexed { i, s -> s.stepNumber == i + 1 }.count { it }
            feedbackMsg = "$correctCount out of 8 steps in place! Keep going 🌱"
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("verify_sequence_button")
      ) {
        Text("Check My Sequence ✨", fontWeight = FontWeight.Bold, fontSize = 15.sp)
      }
      if (feedbackMsg != null) {
        Text(
          text = feedbackMsg!!,
          fontSize = 12.sp,
          color = Color(0xFFD97706),
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
        )
      }
    } else {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("🎉", fontSize = 24.sp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Perfect Wudu sequence! Allah loves those who purify themselves.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark,
            modifier = Modifier.weight(1f)
          )
          Button(onClick = onClose, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
            Text("Done")
          }
        }
      }
    }
  }
}

// ==========================================
// D. MEMORY MATCH CARDS (Screen 12 - D)
// ==========================================
data class MemoryCard(val id: Int, val symbol: String, val label: String)

@Composable
fun MemoryCardsGame(
  gameCard: GameCardItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val symbols = listOf("🕋" to "Ka'bah", "🕌" to "Masjid", "📖" to "Qur'an", "🌙" to "Crescent")
  val deck = remember {
    (symbols + symbols).shuffled().mapIndexed { index, pair ->
      MemoryCard(index, pair.first, pair.second)
    }
  }

  val flippedIndices = remember { mutableStateListOf<Int>() }
  val matchedCardIds = remember { mutableStateListOf<Int>() }
  val coroutineScope = rememberCoroutineScope()

  fun onCardClick(index: Int) {
    if (flippedIndices.contains(index) || matchedCardIds.contains(index)) return
    if (flippedIndices.size < 2) {
      flippedIndices.add(index)
      if (flippedIndices.size == 2) {
        val c1 = deck[flippedIndices[0]]
        val c2 = deck[flippedIndices[1]]
        if (c1.symbol == c2.symbol) {
          matchedCardIds.add(c1.id)
          matchedCardIds.add(c2.id)
          flippedIndices.clear()
          viewModel.earnStars(5, "Pair Uncovered! ⭐")
          if (matchedCardIds.size == deck.size) {
            viewModel.earnStars(10, "Memory Challenge Mastered! 🏆")
          }
        } else {
          coroutineScope.launch {
            delay(800)
            flippedIndices.clear()
          }
        }
      }
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = Color(0xFFF3E8FF),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("🃏", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Find all 4 matching pairs of Islamic symbols!",
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          color = NightPurpleDark
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      horizontalArrangement = Arrangement.spacedBy(14.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      modifier = Modifier.weight(1f)
    ) {
      items(deck.size) { idx ->
        val card = deck[idx]
        val isFaceUp = flippedIndices.contains(idx) || matchedCardIds.contains(card.id)
        Surface(
          shape = RoundedCornerShape(18.dp),
          color = if (isFaceUp) Color.White else EmeraldPrimary,
          border = androidx.compose.foundation.BorderStroke(
            2.dp,
            if (matchedCardIds.contains(card.id)) SunGold else EmeraldPrimary
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onCardClick(idx) }
            .testTag("memory_card_$idx")
        ) {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            if (isFaceUp) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(card.symbol, fontSize = 36.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(card.label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
              }
            } else {
              Text("⭐", fontSize = 28.sp, color = SunGold)
            }
          }
        }
      }
    }

    if (matchedCardIds.size == deck.size) {
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("🎉 Fantastic Memory! You found all pairs! ⭐", fontWeight = FontWeight.Bold, color = EmeraldDark)
          Spacer(modifier = Modifier.height(8.dp))
          Button(onClick = onClose, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
            Text("Done")
          }
        }
      }
    }
  }
}

// ==========================================
// E. PICTURE PUZZLE GAME (Screen 12 - E)
// ==========================================
@Composable
fun PicturePuzzleGame(
  gameCard: GameCardItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val clues = listOf(
    "What holy direction do Muslims face in Salah?" to "Ka'bah in Makkah",
    "What sacred book was revealed to Prophet Muhammad ﷺ?" to "Holy Qur'an",
    "What sweet greeting means 'Peace be upon you'?" to "As-salamu alaykum",
    "What holy month do Muslims fast from dawn to sunset?" to "Ramadan"
  )
  val unlockedPieces = remember { mutableStateListOf<Int>() }
  var activeClueIndex by remember { mutableIntStateOf(0) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = AmberContainer,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("🖼️", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Solve the 4 knowledge clues to reveal the sacred Masjid illustration!",
          fontSize = 12.sp,
          color = OnAmberContainer
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 2x2 Puzzle Mosaic Grid
    Box(
      modifier = Modifier
        .size(240.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFFE2E8F0))
        .border(2.dp, EmeraldPrimary, RoundedCornerShape(20.dp))
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)) {
          PuzzlePieceBox(pieceIndex = 0, isUnlocked = unlockedPieces.contains(0), icon = "🕌", modifier = Modifier.weight(1f))
          PuzzlePieceBox(pieceIndex = 1, isUnlocked = unlockedPieces.contains(1), icon = "🕋", modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.weight(1f)) {
          PuzzlePieceBox(pieceIndex = 2, isUnlocked = unlockedPieces.contains(2), icon = "🌙", modifier = Modifier.weight(1f))
          PuzzlePieceBox(pieceIndex = 3, isUnlocked = unlockedPieces.contains(3), icon = "✨", modifier = Modifier.weight(1f))
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (unlockedPieces.size < 4) {
      val clue = clues[activeClueIndex]
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Clue ${activeClueIndex + 1} of 4",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldPrimary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = clue.first,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark
          )
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = {
              unlockedPieces.add(activeClueIndex)
              viewModel.earnStars(5, "Piece Revealed! ⭐")
              if (activeClueIndex < 3) {
                activeClueIndex++
              } else {
                viewModel.earnStars(15, "Full Puzzle Revealed! Masha'Allah! 🖼️")
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Reveal Piece: ${clue.second} ✨")
          }
        }
      }
    } else {
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("🏆 Masterpiece Unlocked! Masha'Allah! ⭐", fontWeight = FontWeight.Bold, color = EmeraldDark)
          Spacer(modifier = Modifier.height(8.dp))
          Button(onClick = onClose, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
            Text("Done")
          }
        }
      }
    }
  }
}

@Composable
fun PuzzlePieceBox(
  pieceIndex: Int,
  isUnlocked: Boolean,
  icon: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .border(1.dp, Color.White)
      .background(if (isUnlocked) MintContainer else Color(0xFF64748B)),
    contentAlignment = Alignment.Center
  ) {
    if (isUnlocked) {
      Text(icon, fontSize = 42.sp)
    } else {
      Text("🔒 Piece ${pieceIndex + 1}", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun QuizCompletionCelebration(
  score: Int,
  total: Int,
  onDone: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Card(
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(4.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text("🏆", fontSize = 54.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "Masha'Allah, Super Scholar!",
          fontSize = 20.sp,
          fontWeight = FontWeight.ExtraBold,
          color = EmeraldDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "You answered $score out of $total questions correctly!",
          fontSize = 14.sp,
          color = Color.Gray,
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = AmberContainer
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("⭐", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text("+${score * 5} Stars Awarded!", fontWeight = FontWeight.Bold, color = OnAmberContainer)
          }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
          onClick = onDone,
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
          Text("Back to Fun Zone 🎮", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
