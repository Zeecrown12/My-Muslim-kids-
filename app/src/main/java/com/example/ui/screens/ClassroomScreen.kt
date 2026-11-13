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
import com.example.data.KidsRepository
import com.example.model.AgeGroup
import com.example.model.LessonItem
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun ClassroomScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val activeLesson by viewModel.activeLesson.collectAsState()
  val selectedSubjectFilter by viewModel.selectedLessonSubjectFilter.collectAsState()
  val lessonStep by viewModel.lessonStepIndex.collectAsState()
  val isAudioPlaying by viewModel.isLessonAudioPlaying.collectAsState()
  val selectedPracticeOption by viewModel.selectedPracticeOption.collectAsState()
  val isPracticeAnswerChecked by viewModel.isPracticeAnswerChecked.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("classroom_screen")
  ) {
    if (activeLesson == null) {
      // Screen 13: Islamic Classroom Catalog
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
      ) {
        // Classroom Banner
        item {
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF3E8FF),
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
                  text = "Islamic Classroom 🏫",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = NightPurpleDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Structured Islamic lessons at your own pace across 10 essential subjects.",
                  fontSize = 13.sp,
                  color = Color(0xFF4C1D95)
                )
              }
              Text("📚", fontSize = 36.sp)
            }
          }
        }

        // Religious Accuracy & Reviewer Verified Notice
        item {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = MintContainer,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 14.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("🛡️", fontSize = 20.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Authentic Sunnah curriculum • Verified by certified Islamic educators",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldDark
              )
            }
          }
        }

        // Subject Filter Chips (10 Subjects from Screen 13 spec)
        item {
          val subjects = listOf(
            "All",
            "Knowing Allah and foundational beliefs",
            "The Five Pillars of Islam",
            "The Six Articles of Faith",
            "Prophets and Messengers",
            "Seerah",
            "Islamic History",
            "Salah",
            "Wudu",
            "Islamic Manners and Character",
            "Basic Islamic Knowledge"
          )

          Text(
            text = "Subjects",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
          ) {
            items(subjects) { subject ->
              val isSelected = selectedSubjectFilter == subject
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.selectedLessonSubjectFilter.value = subject },
                label = {
                  Text(
                    text = if (subject == "Knowing Allah and foundational beliefs") "Knowing Allah ☝️"
                    else if (subject.length > 20) subject.take(18) + "..."
                    else subject,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = NightPurpleDark,
                  selectedLabelColor = Color.White,
                  containerColor = Color.White,
                  labelColor = Color(0xFF334155)
                )
              )
            }
          }
        }

        // Filtered Lesson Cards
        val filteredLessons = KidsRepository.lessons.filter {
          selectedSubjectFilter == "All" || it.subject == selectedSubjectFilter
        }

        items(filteredLessons) { lesson ->
          LessonCardRow(
            lesson = lesson,
            onOpen = { viewModel.openLesson(lesson) }
          )
          Spacer(modifier = Modifier.height(12.dp))
        }
      }
    } else {
      // Screen 13: 5-Step Lesson Player Modal
      val lesson = activeLesson!!
      Column(modifier = Modifier.fillMaxSize()) {
        // Lesson Player Top Bar
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
              onClick = { viewModel.closeLesson() },
              modifier = Modifier.testTag("lesson_back_button")
            ) {
              Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = EmeraldPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = lesson.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark,
                maxLines = 1
              )
              Text(
                text = "${lesson.subject} • ${lesson.estimatedMinutes} min",
                fontSize = 11.sp,
                color = Color.Gray
              )
            }
            // Audio Narration Toggle
            FilledTonalIconButton(
              onClick = { viewModel.toggleLessonAudio() },
              colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = if (isAudioPlaying) MintContainer else Color(0xFFF1F5F9)
              ),
              modifier = Modifier.testTag("lesson_audio_toggle")
            ) {
              Icon(
                if (isAudioPlaying) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                contentDescription = "Voice Narration",
                tint = if (isAudioPlaying) EmeraldPrimary else Color.Gray
              )
            }
          }
        }

        // 5-Step Stepper Progress Header
        Surface(
          color = Color(0xFFF8FAFC),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            val stepNames = listOf("1. Intro", "2. Explain", "3. Example", "4. Practice", "5. Review")
            stepNames.forEachIndexed { index, name ->
              val isPast = index < lessonStep
              val isCurrent = index == lessonStep
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { viewModel.lessonStepIndex.value = index }
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                      when {
                        isCurrent -> EmeraldPrimary
                        isPast -> Color(0xFFDCFCE7)
                        else -> Color(0xFFE2E8F0)
                      }
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  if (isPast) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                  } else {
                    Text(
                      text = "${index + 1}",
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (isCurrent) Color.White else Color(0xFF64748B)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = name.substringAfter(". "),
                  fontSize = 10.sp,
                  fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                  color = if (isCurrent) EmeraldPrimary else Color(0xFF64748B)
                )
              }
            }
          }
        }

        // Active Step Content
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
        ) {
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
          ) {
            when (lessonStep) {
              0 -> item { LessonIntroStep(lesson) }
              1 -> item { LessonExplanationStep(lesson, isAudioPlaying) }
              2 -> item { LessonExampleStep(lesson) }
              3 -> item {
                LessonPracticeStep(
                  lesson = lesson,
                  selectedOption = selectedPracticeOption,
                  isAnswerChecked = isPracticeAnswerChecked,
                  onSelectOption = { viewModel.selectPracticeAnswer(it) }
                )
              }
              4 -> item { LessonReviewStep(lesson, onComplete = { viewModel.completeLesson(lesson.id) }) }
            }
          }
        }

        // Bottom Navigation Bar
        Surface(
          color = Color.White,
          tonalElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedButton(
              onClick = { viewModel.prevLessonStep() },
              enabled = lessonStep > 0,
              shape = RoundedCornerShape(14.dp)
            ) {
              Text("Previous")
            }

            Button(
              onClick = { viewModel.nextLessonStep() },
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(14.dp),
              modifier = Modifier.testTag("lesson_next_step_button")
            ) {
              Text(
                text = if (lessonStep < 4) "Continue ➡️" else "Finish Lesson (+15 ⭐)",
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// Lesson Card Row
// ------------------------------------
@Composable
fun LessonCardRow(
  lesson: LessonItem,
  onOpen: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onOpen)
      .testTag("lesson_card_${lesson.id}"),
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
          .size(56.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(MintContainer),
        contentAlignment = Alignment.Center
      ) {
        Text(lesson.iconEmoji, fontSize = 28.sp)
      }
      Spacer(modifier = Modifier.width(14.dp))
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFFF3E8FF)
          ) {
            Text(
              text = "${lesson.estimatedMinutes} min",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = NightPurpleDark,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(lesson.targetAge.title, fontSize = 11.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = lesson.title,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = lesson.learningObjective,
          fontSize = 12.sp,
          color = Color(0xFF475569),
          maxLines = 2
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      Icon(Icons.Default.ChevronRight, contentDescription = "Open", tint = EmeraldPrimary)
    }
  }
}

// ------------------------------------
// Step 1: Introduction
// ------------------------------------
@Composable
fun LessonIntroStep(lesson: LessonItem) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(MintContainer),
          contentAlignment = Alignment.Center
        ) {
          Text(lesson.iconEmoji, fontSize = 40.sp)
        }
        Spacer(modifier = Modifier.height(14.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MintContainer
        ) {
          Text(
            text = "Step 1: Introduction",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = lesson.title,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark,
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = lesson.introduction,
          fontSize = 15.sp,
          color = Color(0xFF1E293B),
          textAlign = TextAlign.Center,
          lineHeight = 22.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Learning Objective Box
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = AmberContainer,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("🎯", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text("Learning Objective", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnAmberContainer)
          Text(lesson.learningObjective, fontSize = 13.sp, color = Color(0xFF78350F))
        }
      }
    }
  }
}

// ------------------------------------
// Step 2: Explanation (with Voice Narration Waveform)
// ------------------------------------
@Composable
fun LessonExplanationStep(lesson: LessonItem, isAudioPlaying: Boolean) {
  Column {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF3E8FF)
          ) {
            Text(
              text = "Step 2: Explanation",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = NightPurpleDark,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }

          if (isAudioPlaying) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("🔊 Voice Playing", fontSize = 12.sp, color = EmeraldPrimary, fontWeight = FontWeight.SemiBold)
              Spacer(modifier = Modifier.width(4.dp))
              Text("〰️〰️", fontSize = 12.sp, color = EmeraldPrimary)
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = lesson.explanation,
          fontSize = 15.sp,
          color = Color(0xFF1E293B),
          lineHeight = 24.sp
        )

        // Checkpoints list if available
        if (lesson.checkpoints.isNotEmpty()) {
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Key Foundations:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark
          )
          Spacer(modifier = Modifier.height(8.dp))
          lesson.checkpoints.forEach { cp ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color(0xFFF8FAFC),
              modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(cp.icon, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(cp.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                  Text(cp.description, fontSize = 12.sp, color = Color(0xFF475569))
                  if (cp.arabicText != null) {
                    Text(cp.arabicText, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// Step 3: Example in Everyday Life
// ------------------------------------
@Composable
fun LessonExampleStep(lesson: LessonItem) {
  Column {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = SkyContainer
        ) {
          Text(
            text = "Step 3: Everyday Life Example",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SkyBlueDark,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = "How this connects to your daily life 🌱",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = lesson.everydayExample,
          fontSize = 15.sp,
          color = Color(0xFF1E293B),
          lineHeight = 23.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = AmberContainer,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("💡", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("Did You Know?", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnAmberContainer)
              Text(lesson.funFact, fontSize = 12.sp, color = Color(0xFF78350F))
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// Step 4: Practice (Interactive Mini-Quiz)
// ------------------------------------
@Composable
fun LessonPracticeStep(
  lesson: LessonItem,
  selectedOption: Int?,
  isAnswerChecked: Boolean,
  onSelectOption: (Int) -> Unit
) {
  Column {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = CoralContainer
        ) {
          Text(
            text = "Step 4: Practice Check-in",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9A3412),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = lesson.practiceQuestion,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark
        )
        Spacer(modifier = Modifier.height(14.dp))

        lesson.practiceOptions.forEachIndexed { idx, opt ->
          val isSelected = selectedOption == idx
          val isCorrect = idx == lesson.practiceCorrectIndex
          val cardColor = when {
            !isAnswerChecked -> if (isSelected) MintContainer else Color.White
            isCorrect -> Color(0xFFDCFCE7)
            isSelected && !isCorrect -> Color(0xFFFFEDD5)
            else -> Color.White
          }

          Surface(
            shape = RoundedCornerShape(14.dp),
            color = cardColor,
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isSelected) EmeraldPrimary else Color(0xFFE2E8F0)
            ),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 5.dp)
              .clickable { onSelectOption(idx) }
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(if (isSelected) EmeraldPrimary else MintContainer),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "${'A' + idx}",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else EmeraldDark
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = opt,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1E293B),
                modifier = Modifier.weight(1f)
              )
              if (isAnswerChecked && isCorrect) {
                Text("✅", fontSize = 16.sp)
              }
            }
          }
        }

        if (isAnswerChecked) {
          Spacer(modifier = Modifier.height(12.dp))
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = if (selectedOption == lesson.practiceCorrectIndex) Color(0xFFDCFCE7) else AmberContainer,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = lesson.practiceExplanation,
              fontSize = 13.sp,
              color = EmeraldDark,
              modifier = Modifier.padding(12.dp)
            )
          }
        }
      }
    }
  }
}

// ------------------------------------
// Step 5: Review & Milestone Celebration
// ------------------------------------
@Composable
fun LessonReviewStep(lesson: LessonItem, onComplete: () -> Unit) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(2.dp),
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
            .background(Color(0xFFDCFCE7)),
          contentAlignment = Alignment.Center
        ) {
          Text("🏆", fontSize = 36.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "Step 5: Review & Takeaways",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Remember These Golden Lessons ⭐",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = EmeraldDark,
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(14.dp))

        lesson.reviewKeyPoints.forEach { point ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.Top
          ) {
            Text("✨", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = point,
              fontSize = 14.sp,
              color = Color(0xFF1E293B),
              lineHeight = 20.sp,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }
  }
}
