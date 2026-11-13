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
import com.example.model.SectionTab
import com.example.model.Story
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

/**
 * SCREEN 6 — Story World
 * SCREEN 7 — Story Reading & Listening Player
 */
@Composable
fun StoryWorldScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val activeStory by viewModel.activeStory.collectAsState()
  val storyPage by viewModel.storyCurrentPage.collectAsState()
  val isAudioPlaying by viewModel.isStoryAudioPlaying.collectAsState()
  val ageFilter by viewModel.ageGroupFilter.collectAsState()

  val searchQuery by viewModel.storySearchQuery.collectAsState()
  val selectedCategory by viewModel.storyCategoryFilter.collectAsState()
  val selectedFormat by viewModel.storyFormatFilter.collectAsState()
  val fontSizeSp by viewModel.storyFontSize.collectAsState()
  val isAmbienceOn by viewModel.isStoryAmbiencePlaying.collectAsState()
  val subtitleLang by viewModel.storySubtitleLang.collectAsState()
  val favoriteStoryIds by viewModel.favoriteStoryIds.collectAsState()

  val categories = listOf(
    "All",
    "Stories of the Prophets",
    "Stories of the Companions",
    "Islamic History",
    "Good Character & Manners",
    "Muslim Heroes and Contributions",
    "Bedtime Stories",
    "Stories about Kindness, Honesty and Helping Others"
  )

  val formats = listOf("All", "Read & Listen", "Audio Only", "Illustrated")

  // Filtered stories list
  val filteredStories = remember(searchQuery, selectedCategory, selectedFormat, ageFilter) {
    KidsRepository.stories.filter { story ->
      val matchSearch = searchQuery.isBlank() || story.title.contains(searchQuery, ignoreCase = true) || story.summary.contains(searchQuery, ignoreCase = true)
      val matchCat = if (selectedCategory == "All") true else story.category == selectedCategory
      val matchFormat = if (selectedFormat == "All") true else story.format == selectedFormat
      val matchAge = if (ageFilter == null) true else story.targetAge == ageFilter
      matchSearch && matchCat && matchFormat && matchAge
    }.ifEmpty {
      // Graceful fallback to category/search match
      KidsRepository.stories.filter {
        (searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true)) &&
        (selectedCategory == "All" || it.category == selectedCategory)
      }
    }
  }

  val featuredStory = KidsRepository.stories.find { it.id == "story_nuh_ark" } ?: KidsRepository.stories.first()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("story_world_screen")
  ) {
    if (activeStory == null) {
      // SCREEN 6: STORY WORLD CATALOG
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
      ) {
        // Search Bar
        item {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.storySearchQuery.value = it },
            placeholder = { Text("Search stories by title or keyword...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = SkyBlue) },
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { viewModel.storySearchQuery.value = "" }) {
                  Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                }
              }
            },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .testTag("story_search_bar"),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = Color.White,
              unfocusedContainerColor = Color.White
            )
          )
        }

        // Featured Story Hero Banner
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 14.dp)
              .clickable { viewModel.openStory(featuredStory) }
              .testTag("featured_story_card"),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(containerColor = SkyContainer)
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  color = SkyBlueDark,
                  shape = RoundedCornerShape(10.dp)
                ) {
                  Text(
                    text = "FEATURED STORY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text("🎧 Audio Narrated", fontSize = 11.sp, color = SkyBlueDark, fontWeight = FontWeight.Bold)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("• ${featuredStory.readTimeMinutes} min", fontSize = 11.sp, color = TextMuted)
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White),
                  contentAlignment = Alignment.Center
                ) {
                  Text(text = featuredStory.coverEmoji, fontSize = 42.sp)
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = featuredStory.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = featuredStory.summary,
                    fontSize = 12.sp,
                    color = TextMuted,
                    maxLines = 2,
                    lineHeight = 16.sp
                  )
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = { viewModel.openStory(featuredStory) },
                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Read & Listen Now", fontWeight = FontWeight.Bold, color = Color.White)
              }
            }
          }
        }

        // Category Filter Chips
        item {
          Text(
            text = "Story Categories",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 6.dp)
          )
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 10.dp)
          ) {
            items(categories) { cat ->
              val isSelected = selectedCategory == cat
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.storyCategoryFilter.value = cat },
                label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SkyBlue,
                  selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
              )
            }
          }
        }

        // Format Filter Chips (Read & Listen, Audio Only, Illustrated)
        item {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 14.dp)
          ) {
            items(formats) { fmt ->
              val isSelected = selectedFormat == fmt
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.storyFormatFilter.value = fmt },
                label = { Text(fmt, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                leadingIcon = {
                  when (fmt) {
                    "Read & Listen" -> Text("🎧", fontSize = 12.sp)
                    "Audio Only" -> Text("🎙️", fontSize = 12.sp)
                    "Illustrated" -> Text("🎨", fontSize = 12.sp)
                    else -> Text("📚", fontSize = 12.sp)
                  }
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = EmeraldPrimary,
                  selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
              )
            }
          }
        }

        // Story Cards List
        items(filteredStories) { story ->
          val isFav = favoriteStoryIds.contains(story.id)
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .clickable { viewModel.openStory(story) }
              .testTag("story_card_${story.id}"),
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
                  .size(68.dp)
                  .clip(RoundedCornerShape(18.dp))
                  .background(MintContainer),
                contentAlignment = Alignment.Center
              ) {
                Text(text = story.coverEmoji, fontSize = 36.sp)
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Surface(
                    color = AmberContainer,
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text(
                      text = story.category,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold,
                      color = OnAmberContainer,
                      maxLines = 1
                    )
                  }
                  Text(
                    text = "• ${story.readTimeMinutes} min",
                    fontSize = 11.sp,
                    color = TextMuted
                  )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = story.title,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextDark
                )

                Text(
                  text = story.summary,
                  fontSize = 12.sp,
                  color = TextMuted,
                  maxLines = 2,
                  lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Surface(
                    color = SkyContainer,
                    shape = RoundedCornerShape(6.dp)
                  ) {
                    Text(
                      text = story.format,
                      fontSize = 9.sp,
                      color = SkyBlueDark,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }

                  Text(
                    text = "Ages: ${story.targetAge.ageRange}",
                    fontSize = 10.sp,
                    color = TextMuted
                  )
                }
              }

              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                IconButton(
                  onClick = { viewModel.toggleStoryFavorite(story.id) },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFav) CoralFun else TextMuted,
                    modifier = Modifier.size(18.dp)
                  )
                }

                IconButton(
                  onClick = { viewModel.openStory(story) },
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SkyContainer)
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Read Story",
                    tint = SkyBlueDark,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
            }
          }
        }
      }
    } else {
      // SCREEN 7: STORY READING AND LISTENING PLAYER
      val story = activeStory!!
      val totalPages = story.pages.size
      val currentPageObj = story.pages.getOrElse(storyPage) { story.pages.first() }
      val isFav = favoriteStoryIds.contains(story.id)

      var selectedQuizAnswer by remember { mutableStateOf<Int?>(null) }
      var quizSubmitted by remember { mutableStateOf(false) }

      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.White)
          .testTag("story_reader_modal")
      ) {
        // Player Top Bar
        Surface(
          color = IvoryBackground,
          tonalElevation = 3.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            IconButton(
              onClick = { viewModel.closeStory() },
              modifier = Modifier.testTag("close_story_reader")
            ) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = TextDark)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = story.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                maxLines = 1
              )
              Text(
                text = "Page ${storyPage + 1} of $totalPages • ${story.readTimeMinutes} min",
                fontSize = 11.sp,
                color = TextMuted
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              // Font size controls
              IconButton(
                onClick = { viewModel.changeStoryFontSize(-2) },
                modifier = Modifier.size(32.dp)
              ) {
                Text("A-", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
              }
              IconButton(
                onClick = { viewModel.changeStoryFontSize(2) },
                modifier = Modifier.size(32.dp)
              ) {
                Text("A+", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
              }

              // Favorite button
              IconButton(
                onClick = { viewModel.toggleStoryFavorite(story.id) },
                modifier = Modifier.size(36.dp)
              ) {
                Icon(
                  imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                  contentDescription = "Favorite",
                  tint = if (isFav) CoralFun else TextMuted
                )
              }
            }
          }
        }

        // Reader Scrollable Canvas
        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Respectful Scene Illustration (Environmental / Non-depictive of Prophets)
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                  Brush.verticalGradient(
                    listOf(
                      Color(0xFFE0F2FE),
                      Color(0xFFBAE6FD),
                      Color(0xFF7DD3FC)
                    )
                  )
                ),
              contentAlignment = Alignment.Center
            ) {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
              ) {
                Text(text = currentPageObj.sceneEmoji, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                  color = Color.White.copy(alpha = 0.9f),
                  shape = RoundedCornerShape(12.dp)
                ) {
                  Text(
                    text = currentPageObj.illustrationTitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SkyBlueDark,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }

          // Narration & Ambient Sound Controls
          item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Voice narration toggle with soundwave animation
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isAudioPlaying) MintContainer else IvoryBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isAudioPlaying) EmeraldPrimary else CardBorder),
                modifier = Modifier.clickable { viewModel.toggleStoryAudio() }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Voice Narration",
                    tint = if (isAudioPlaying) EmeraldPrimary else TextDark,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = if (isAudioPlaying) "Voice Playing 🔊" else "Listen Narration",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isAudioPlaying) OnMintContainer else TextDark
                  )
                }
              }

              // Background Ambience Sound Toggle
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isAmbienceOn) AmberContainer else IvoryBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isAmbienceOn) SunGoldDark else CardBorder),
                modifier = Modifier.clickable { viewModel.toggleStoryAmbience() }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = if (isAmbienceOn) "🌿 Nature Sound: ON" else "🍃 Ambience", fontSize = 11.sp, color = TextDark, fontWeight = FontWeight.SemiBold)
                }
              }
            }
          }

          // Story Text with Highlighted Narration Effect
          item {
            Spacer(modifier = Modifier.height(18.dp))
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isAudioPlaying) MintContainer.copy(alpha = 0.35f) else Color.Transparent,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = currentPageObj.text,
                fontSize = fontSizeSp.sp,
                lineHeight = (fontSizeSp + 10).sp,
                color = TextDark,
                textAlign = TextAlign.Start,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(8.dp)
              )
            }
          }

          // If last page: Moral Lesson + Comprehension Quiz + Related Activities
          if (storyPage == totalPages - 1) {
            item {
              Spacer(modifier = Modifier.height(20.dp))

              // What We Learned (Moral Lesson)
              Surface(
                shape = RoundedCornerShape(18.dp),
                color = AmberContainer,
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("💡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "What We Learned (Moral)",
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      color = OnAmberContainer
                    )
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(
                    text = story.moralLesson,
                    fontSize = 13.sp,
                    color = Color(0xFF451A03),
                    lineHeight = 20.sp
                  )
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              // Comprehension Quiz
              Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFFF1F5F9),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(16.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🎯", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Comprehension Challenge (+10 ⭐)",
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp,
                      color = TextDark
                    )
                  }

                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = story.reflectionQuestion,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                  )

                  Spacer(modifier = Modifier.height(10.dp))

                  story.quizOptions.forEachIndexed { idx, opt ->
                    val isPicked = selectedQuizAnswer == idx
                    val isCorrect = idx == story.correctOptionIndex
                    val btnBg = when {
                      quizSubmitted && isCorrect -> MintContainer
                      quizSubmitted && isPicked && !isCorrect -> CoralContainer
                      isPicked -> SkyContainer
                      else -> Color.White
                    }

                    Surface(
                      shape = RoundedCornerShape(12.dp),
                      color = btnBg,
                      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                        .clickable(enabled = !quizSubmitted) {
                          selectedQuizAnswer = idx
                        }
                    ) {
                      Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Text(
                          text = if (isPicked) "🔘" else "⚪",
                          fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                          text = opt,
                          fontSize = 13.sp,
                          color = TextDark,
                          fontWeight = if (isPicked) FontWeight.Bold else FontWeight.Normal
                        )
                      }
                    }
                  }

                  if (!quizSubmitted && selectedQuizAnswer != null) {
                    Button(
                      onClick = {
                        quizSubmitted = true
                        if (selectedQuizAnswer == story.correctOptionIndex) {
                          viewModel.earnStars(10, "Story Challenge Solved!")
                        }
                      },
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                      colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                      shape = RoundedCornerShape(12.dp)
                    ) {
                      Text("Check My Answer", fontWeight = FontWeight.Bold)
                    }
                  }

                  if (quizSubmitted) {
                    val correct = selectedQuizAnswer == story.correctOptionIndex
                    Text(
                      text = if (correct) "🎉 MashaAllah, that is correct! You earned 10 stars!" else "Almost! Revisit the story pages anytime to remember.",
                      fontSize = 12.sp,
                      color = if (correct) SuccessGreen else CoralFun,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(top = 8.dp)
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Related Game / Fun Zone shortcut
              OutlinedButton(
                onClick = {
                  viewModel.closeStory()
                  viewModel.selectSection(SectionTab.FUN_ZONE)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SunGoldDark)
              ) {
                Text("🎮 Play Related Activity in Fun Zone", color = SunGoldDark, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        // Reader Bottom Navigation Bar
        Surface(
          color = IvoryBackground,
          tonalElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Button(
              onClick = { if (storyPage > 0) viewModel.setStoryPage(storyPage - 1) },
              enabled = storyPage > 0,
              colors = ButtonDefaults.buttonColors(containerColor = SkyContainer, contentColor = OnSkyContainer),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Previous", fontSize = 13.sp)
            }

            // Page dots
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              for (i in 0 until totalPages) {
                Box(
                  modifier = Modifier
                    .size(if (i == storyPage) 10.dp else 6.dp)
                    .clip(CircleShape)
                    .background(if (i == storyPage) EmeraldPrimary else CardBorder)
                )
              }
            }

            Button(
              onClick = {
                if (storyPage < totalPages - 1) {
                  viewModel.setStoryPage(storyPage + 1)
                } else {
                  viewModel.earnStars(5, "Story Completed! 📖")
                  viewModel.closeStory()
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(
                text = if (storyPage < totalPages - 1) "Next" else "Finish 🎉",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
              if (storyPage < totalPages - 1) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
              }
            }
          }
        }
      }
    }
  }
}
