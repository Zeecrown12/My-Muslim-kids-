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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.KidsRepository
import com.example.model.AgeGroup
import com.example.model.VideoItem
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

/**
 * SCREEN 8 — My Muslim Kids TV
 * SCREEN 9 — Cartoon and Video Player
 */
@Composable
fun MmkTvScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val activeVideo by viewModel.activeVideo.collectAsState()
  val isPlaying by viewModel.isVideoPlaying.collectAsState()
  val progressSec by viewModel.videoProgressSec.collectAsState()
  val ageFilter by viewModel.ageGroupFilter.collectAsState()

  val searchQuery by viewModel.videoSearchQuery.collectAsState()
  val selectedCategory by viewModel.videoCategoryFilter.collectAsState()
  val videoQuality by viewModel.videoQuality.collectAsState()
  val videoSubtitle by viewModel.videoSubtitle.collectAsState()
  val videoAudioLang by viewModel.videoAudioLang.collectAsState()
  val isFullscreen by viewModel.isVideoFullscreen.collectAsState()
  val isMuted by viewModel.isVideoMuted.collectAsState()
  val favoriteVideoIds by viewModel.favoriteVideoIds.collectAsState()

  val categories = listOf(
    "All",
    "Original Cartoon Series",
    "Islamic Adventures",
    "Good Manners",
    "Islamic History",
    "Quran and Arabic Learning",
    "Short Learning Videos",
    "Special Ramadan and Eid Content"
  )

  val filteredVideos = remember(searchQuery, selectedCategory, ageFilter) {
    KidsRepository.tvEpisodes.filter { ep ->
      val matchSearch = searchQuery.isBlank() || ep.title.contains(searchQuery, ignoreCase = true) || ep.description.contains(searchQuery, ignoreCase = true) || ep.series.contains(searchQuery, ignoreCase = true)
      val matchCategory = if (selectedCategory == "All") true else ep.category == selectedCategory
      val matchAge = if (ageFilter == null) true else ep.targetAge == ageFilter
      matchSearch && matchCategory && matchAge
    }.ifEmpty {
      KidsRepository.tvEpisodes.filter {
        (searchQuery.isBlank() || it.title.contains(searchQuery, ignoreCase = true)) &&
        (selectedCategory == "All" || it.category == selectedCategory)
      }
    }
  }

  val featuredEpisode = KidsRepository.tvEpisodes.first()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("mmk_tv_screen")
  ) {
    if (activeVideo == null) {
      // SCREEN 8: TV CATALOG
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
      ) {
        // Search Bar
        item {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.videoSearchQuery.value = it },
            placeholder = { Text("Search cartoons, series, or topics...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = CoralFun) },
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { viewModel.videoSearchQuery.value = "" }) {
                  Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                }
              }
            },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .testTag("video_search_bar"),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = Color.White,
              unfocusedContainerColor = Color.White
            )
          )
        }

        // Featured Cartoon Banner
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 14.dp)
              .clickable { viewModel.openVideo(featuredEpisode) }
              .testTag("featured_video_banner"),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(containerColor = CoralContainer)
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  color = CoralFun,
                  shape = RoundedCornerShape(10.dp)
                ) {
                  Text(
                    text = "FEATURED CARTOON",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
                Text(
                  text = "⭐ Popular with Children",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = OnCoralContainer
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(130.dp)
                  .clip(RoundedCornerShape(18.dp))
                  .background(
                    Brush.verticalGradient(
                      listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                    )
                  ),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = featuredEpisode.thumbnailEmoji, fontSize = 48.sp)
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "${featuredEpisode.series} • ${featuredEpisode.duration}",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }

                Box(
                  modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(CoralFun.copy(alpha = 0.9f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = featuredEpisode.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
              )
              Text(
                text = featuredEpisode.description,
                fontSize = 12.sp,
                color = TextMuted,
                maxLines = 2,
                lineHeight = 16.sp
              )
            }
          }
        }

        // Category Filter Chips
        item {
          Text(
            text = "Video Categories",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 6.dp)
          )
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 12.dp)
          ) {
            items(categories) { cat ->
              val isSelected = selectedCategory == cat
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.videoCategoryFilter.value = cat },
                label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = CoralFun,
                  selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
              )
            }
          }
        }

        // Continue Watching Row (PRD main element)
        item {
          Text(
            text = "Continue Watching",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 8.dp)
          )
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 16.dp)
          ) {
            items(KidsRepository.tvEpisodes.take(3)) { ep ->
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier
                  .width(170.dp)
                  .clickable { viewModel.openVideo(ep) }
              ) {
                Column(modifier = Modifier.padding(10.dp)) {
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(80.dp)
                      .clip(RoundedCornerShape(12.dp))
                      .background(Color(0xFF1E293B)),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(text = ep.thumbnailEmoji, fontSize = 28.sp)
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(text = ep.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark, maxLines = 1)
                  Text(text = ep.series, fontSize = 10.sp, color = TextMuted, maxLines = 1)
                  Spacer(modifier = Modifier.height(4.dp))
                  LinearProgressIndicator(
                    progress = { 0.45f },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = CoralFun,
                    trackColor = CardBorder
                  )
                }
              }
            }
          }
        }

        // Video Catalog List
        item {
          Text(
            text = "Episodes & Videos (${filteredVideos.size})",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 8.dp)
          )
        }

        items(filteredVideos) { video ->
          val isFav = favoriteVideoIds.contains(video.id)
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .clickable { viewModel.openVideo(video) }
              .testTag("video_card_${video.id}"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(140.dp)
                  .clip(RoundedCornerShape(16.dp))
                  .background(
                    Brush.verticalGradient(
                      listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                    )
                  ),
                contentAlignment = Alignment.Center
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = video.thumbnailEmoji, fontSize = 48.sp)
                  Spacer(modifier = Modifier.height(4.dp))
                  Surface(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Text(
                      text = video.duration,
                      color = Color.White,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                  }
                }

                Box(
                  modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(CoralFun.copy(alpha = 0.9f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                  )
                }

                // Favorite button top right
                IconButton(
                  onClick = { viewModel.toggleVideoFavorite(video.id) },
                  modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(32.dp)
                ) {
                  Icon(
                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Save",
                    tint = if (isFav) CoralFun else Color.White
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Surface(
                  color = CoralContainer,
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Text(
                    text = video.series,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnCoralContainer
                  )
                }
                Surface(
                  color = SkyContainer,
                  shape = RoundedCornerShape(8.dp)
                ) {
                  Text(
                    text = video.category,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = SkyBlueDark,
                    maxLines = 1
                  )
                }
                Text(
                  text = "• ${video.targetAge.title}",
                  fontSize = 11.sp,
                  color = TextMuted
                )
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = video.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = video.description,
                fontSize = 12.sp,
                color = TextMuted,
                maxLines = 2,
                lineHeight = 16.sp
              )
            }
          }
        }
      }
    } else {
      // SCREEN 9: CARTOON AND VIDEO PLAYER
      val video = activeVideo!!
      val isFav = favoriteVideoIds.contains(video.id)

      var showSettingsDialog by remember { mutableStateOf(false) }
      var quizAnswer by remember { mutableStateOf<Int?>(null) }
      var quizChecked by remember { mutableStateOf(false) }

      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.White)
          .testTag("video_player_modal")
      ) {
        // Player Top Bar
        Surface(
          color = IvoryBackground,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            IconButton(onClick = { viewModel.closeVideo() }) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = TextDark)
            }
            Text(
              text = video.title,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark,
              maxLines = 1,
              modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
              IconButton(onClick = { viewModel.toggleVideoFavorite(video.id) }) {
                Icon(
                  imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                  contentDescription = "Favorite",
                  tint = if (isFav) CoralFun else TextDark
                )
              }

              // Settings button (Quality & Subtitles)
              IconButton(onClick = { showSettingsDialog = true }) {
                Icon(Icons.Default.Settings, contentDescription = "Player Settings", tint = TextDark)
              }
            }
          }
        }

        // Animated Video Screen
        val videoHeight = if (isFullscreen) 300.dp else 220.dp
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(videoHeight)
            .background(Color(0xFF0F172A)),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(text = video.thumbnailEmoji, fontSize = 64.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = if (isPlaying) "Playing: ${video.title}" else "Paused",
              color = Color.White.copy(alpha = 0.9f),
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )

            if (videoSubtitle != "Off") {
              Spacer(modifier = Modifier.height(6.dp))
              Surface(
                color = Color.Black.copy(alpha = 0.7f),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text(
                  text = "Subtitles: $videoSubtitle (${video.keyTakeaway})",
                  color = Color.Yellow,
                  fontSize = 11.sp,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                  maxLines = 1
                )
              }
            }
          }

          // Screen Controls Bar
          Column(
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .fillMaxWidth()
              .background(Color.Black.copy(alpha = 0.65f))
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            val progressRatio = (progressSec.toFloat() / video.durationSeconds.toFloat()).coerceIn(0f, 1f)
            LinearProgressIndicator(
              progress = { progressRatio },
              modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = CoralFun,
              trackColor = Color.White.copy(alpha = 0.3f),
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                // Prev Episode
                IconButton(
                  onClick = { viewModel.prevEpisode() },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(Icons.Default.SkipPrevious, contentDescription = "Previous Episode", tint = Color.White)
                }

                // Rewind 10s
                IconButton(
                  onClick = { viewModel.seekVideo((progressSec - 10).coerceAtLeast(0)) },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(Icons.Default.Replay10, contentDescription = "Rewind", tint = Color.White)
                }

                // Play / Pause
                IconButton(
                  onClick = { viewModel.toggleVideoPlay() },
                  modifier = Modifier.size(36.dp)
                ) {
                  Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White
                  )
                }

                // Forward 10s
                IconButton(
                  onClick = { viewModel.seekVideo((progressSec + 10).coerceAtMost(video.durationSeconds)) },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(Icons.Default.Forward10, contentDescription = "Forward", tint = Color.White)
                }

                // Next Episode
                IconButton(
                  onClick = { viewModel.nextEpisode() },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(Icons.Default.SkipNext, contentDescription = "Next Episode", tint = Color.White)
                }
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                // Volume mute toggle
                IconButton(
                  onClick = { viewModel.isVideoMuted.value = !isMuted },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                    contentDescription = "Volume",
                    tint = Color.White
                  )
                }

                val currentMins = progressSec / 60
                val currentRemSecs = progressSec % 60
                val totalMins = video.durationSeconds / 60
                val totalRemSecs = video.durationSeconds % 60
                Text(
                  text = "%d:%02d / %d:%02d".format(currentMins, currentRemSecs, totalMins, totalRemSecs),
                  color = Color.White,
                  fontSize = 11.sp
                )

                // Fullscreen toggle
                IconButton(
                  onClick = { viewModel.isVideoFullscreen.value = !isFullscreen },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = if (isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                    contentDescription = "Fullscreen",
                    tint = Color.White
                  )
                }
              }
            }
          }
        }

        // Details, Chapters & Episode Challenge
        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          item {
            Text(
              text = video.title,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = video.description,
              fontSize = 13.sp,
              color = TextMuted,
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Key Takeaway Card
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = MintContainer,
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text("🌿", fontSize = 16.sp)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Key Takeaway",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = OnMintContainer
                  )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = video.keyTakeaway,
                  fontSize = 12.sp,
                  color = OnMintContainer
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = "Episode Chapters",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
          }

          items(video.chapters) { chapter ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = IvoryBackground,
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .clickable { viewModel.seekVideo(chapter.timeSec) }
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = chapter.title,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium,
                  color = TextDark
                )
                Text(
                  text = "%d:%02d".format(chapter.timeSec / 60, chapter.timeSec % 60),
                  fontSize = 11.sp,
                  color = CoralFun,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          // Episode Challenge
          item {
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = AmberContainer,
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text("🏆", fontSize = 16.sp)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Episode Challenge (+10 ⭐)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = OnAmberContainer
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = video.quizQuestion,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF451A03)
                )

                Spacer(modifier = Modifier.height(8.dp))

                video.quizOptions.forEachIndexed { idx, opt ->
                  val isSelected = quizAnswer == idx
                  Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 4.dp)
                      .clickable(enabled = !quizChecked) {
                        quizAnswer = idx
                      }
                  ) {
                    Text(
                      text = "${if (isSelected) "🔘" else "⚪"} $opt",
                      modifier = Modifier.padding(10.dp),
                      fontSize = 12.sp,
                      color = TextDark
                    )
                  }
                }

                if (!quizChecked && quizAnswer != null) {
                  Button(
                    onClick = {
                      quizChecked = true
                      if (quizAnswer == video.quizAnswerIndex) {
                        viewModel.earnStars(10, "Episode Challenge Solved!")
                      }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunGoldDark),
                    modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Text("Submit Answer", fontWeight = FontWeight.Bold)
                  }
                }

                if (quizChecked) {
                  val isCorrect = quizAnswer == video.quizAnswerIndex
                  Text(
                    text = if (isCorrect) "🎉 Correct! You earned 10 stars!" else "Good try! Watch the episode again to learn more.",
                    color = if (isCorrect) SuccessGreen else CoralFun,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp)
                  )
                }
              }
            }
          }
        }
      }

      // Video Quality & Subtitles Settings Dialog
      if (showSettingsDialog) {
        Dialog(onDismissRequest = { showSettingsDialog = false }) {
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
          ) {
            Column(modifier = Modifier.padding(20.dp)) {
              Text(
                text = "Player & Stream Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )

              Spacer(modifier = Modifier.height(14.dp))

              Text("Video Quality:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMuted)
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                video.availableQualities.forEach { q ->
                  FilterChip(
                    selected = videoQuality == q,
                    onClick = { viewModel.videoQuality.value = q },
                    label = { Text(q, fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = CoralFun, selectedLabelColor = Color.White)
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text("Subtitles:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMuted)
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                video.availableSubtitles.forEach { sub ->
                  FilterChip(
                    selected = videoSubtitle == sub,
                    onClick = { viewModel.videoSubtitle.value = sub },
                    label = { Text(sub, fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = EmeraldPrimary, selectedLabelColor = Color.White)
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text("Audio Language:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMuted)
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                video.availableAudioTracks.forEach { track ->
                  FilterChip(
                    selected = videoAudioLang == track,
                    onClick = { viewModel.videoAudioLang.value = track },
                    label = { Text(track, fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SkyBlue, selectedLabelColor = Color.White)
                  )
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              Button(
                onClick = { showSettingsDialog = false },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
              ) {
                Text("Done")
              }
            }
          }
        }
      }
    }
  }
}
