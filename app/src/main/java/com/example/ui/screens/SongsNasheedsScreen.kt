package com.example.ui.screens

import androidx.compose.animation.core.*
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
import com.example.model.SongItem
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

/**
 * SCREEN 10 — Songs and Nasheeds
 * Dedicated Islamic children's music and sing-along audio learning experience.
 */
@Composable
fun SongsNasheedsScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val activeSong by viewModel.activeSong.collectAsState()
  val isPlaying by viewModel.isSongPlaying.collectAsState()
  val songProgress by viewModel.songProgress.collectAsState()

  val selectedCategory by viewModel.songCategoryFilter.collectAsState()
  val isRepeat by viewModel.isSongRepeat.collectAsState()
  val isShuffle by viewModel.isSongShuffle.collectAsState()
  val isAudioOnly by viewModel.isSongAudioOnly.collectAsState()
  val favoriteSongIds by viewModel.favoriteSongIds.collectAsState()

  val categories = listOf(
    "All",
    "Islamic Nursery Songs",
    "Arabic Alphabet Songs",
    "Learning Allah's Beautiful Names",
    "Daily Duas and Remembrance",
    "Good Manners and Character",
    "Salah and Worship",
    "Ramadan and Eid Songs",
    "Islamic Knowledge Songs"
  )

  val filteredSongs = remember(selectedCategory) {
    if (selectedCategory == "All") {
      KidsRepository.songs
    } else {
      KidsRepository.songs.filter { it.category == selectedCategory }.ifEmpty { KidsRepository.songs }
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("songs_nasheeds_screen")
  ) {
    if (activeSong == null) {
      // SCREEN 10: SONGS CATALOG
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
      ) {
        // Hero Banner
        item {
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = MintContainer,
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
                  text = "Songs and Nasheeds 🎵",
                  fontSize = 20.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = EmeraldPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Sing along, memorize Arabic letters, and learn Allah's beautiful names through joyful melodies!",
                  fontSize = 13.sp,
                  color = OnMintContainer,
                  lineHeight = 18.sp
                )
              }
              Text("🎶", fontSize = 36.sp)
            }
          }
        }

        // Learn Through Songs Callout Card
        item {
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = AmberContainer,
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 14.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text("💡", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Learn Through Songs Feature",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = OnAmberContainer
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Nasheeds help children effortlessly memorize Arabic letters, days of the week, Islamic manners, simple duas, and basic Islamic concepts.",
                fontSize = 12.sp,
                color = Color(0xFF451A03),
                lineHeight = 16.sp
              )
            }
          }
        }

        // Category Filter Chips
        item {
          Text(
            text = "Categories",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 6.dp)
          )
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 14.dp)
          ) {
            items(categories) { cat ->
              val isSelected = selectedCategory == cat
              FilterChip(
                selected = isSelected,
                onClick = { viewModel.songCategoryFilter.value = cat },
                label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = EmeraldPrimary,
                  selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
              )
            }
          }
        }

        // Songs List
        items(filteredSongs) { song ->
          val isFav = favoriteSongIds.contains(song.id)
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .clickable { viewModel.openSong(song) }
              .testTag("song_item_${song.id}"),
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
                  .clip(RoundedCornerShape(18.dp))
                  .background(
                    Brush.linearGradient(
                      listOf(SunGoldLight, AmberContainer)
                    )
                  ),
                contentAlignment = Alignment.Center
              ) {
                Text(text = song.coverEmoji, fontSize = 32.sp)
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Surface(
                    color = SkyContainer,
                    shape = RoundedCornerShape(8.dp)
                  ) {
                    Text(
                      text = song.category,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold,
                      color = OnSkyContainer,
                      maxLines = 1
                    )
                  }

                  if (song.hasVideoVersion) {
                    Surface(
                      color = CoralContainer,
                      shape = RoundedCornerShape(6.dp)
                    ) {
                      Text(
                        text = "Video & Audio",
                        fontSize = 9.sp,
                        color = CoralFun,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                      )
                    }
                  }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = song.title,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextDark
                )

                Text(
                  text = "${song.artist} • ${song.duration}",
                  fontSize = 12.sp,
                  color = TextMuted
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                  onClick = { viewModel.toggleSongFavorite(song.id) },
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
                  onClick = { viewModel.openSong(song) },
                  modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(EmeraldPrimary)
                ) {
                  Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Song",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                  )
                }
              }
            }
          }
        }
      }
    } else {
      // SCREEN 10: SONG PLAYER WITH SING-ALONG LYRICS & CONTROLS
      val song = activeSong!!
      val isFav = favoriteSongIds.contains(song.id)

      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.White)
          .testTag("nasheed_player_modal")
      ) {
        // Player Header Bar
        Surface(
          color = IvoryBackground,
          tonalElevation = 2.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            IconButton(onClick = { viewModel.closeSong() }) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = TextDark)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "Now Playing Nasheed",
                fontSize = 11.sp,
                color = TextMuted,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = song.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                maxLines = 1
              )
            }
            IconButton(onClick = { viewModel.toggleSongFavorite(song.id) }) {
              Icon(
                imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFav) CoralFun else TextDark
              )
            }
          }
        }

        // Cover Artwork & Musical Animated Equalizer
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
              Brush.verticalGradient(
                listOf(EmeraldPrimary, EmeraldLight)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = song.coverEmoji, fontSize = 64.sp)
            Spacer(modifier = Modifier.height(10.dp))

            // Animated Equalizer Bars
            Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              verticalAlignment = Alignment.Bottom,
              modifier = Modifier.height(30.dp)
            ) {
              val infiniteTransition = rememberInfiniteTransition(label = "eq_bars")
              listOf(12.dp, 24.dp, 16.dp, 28.dp, 20.dp, 26.dp, 14.dp).forEachIndexed { index, baseHeight ->
                val barScale by infiniteTransition.animateFloat(
                  initialValue = 0.35f,
                  targetValue = 1.0f,
                  animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 320 + (index * 60), easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                  ),
                  label = "bar_$index"
                )
                Box(
                  modifier = Modifier
                    .width(4.dp)
                    .height(if (isPlaying) baseHeight * barScale else baseHeight * 0.35f)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White)
                )
              }
            }
          }
        }

        // Playback Scrub Progress Bar
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
          LinearProgressIndicator(
            progress = { songProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = EmeraldPrimary,
            trackColor = CardBorder
          )
          Spacer(modifier = Modifier.height(4.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            val totalSec = 165
            val currentSec = (songProgress * totalSec).toInt()
            Text("%d:%02d".format(currentSec / 60, currentSec % 60), fontSize = 11.sp, color = TextMuted)
            Text(song.duration, fontSize = 11.sp, color = TextMuted)
          }
        }

        // Full Controls (Shuffle, Prev, Play/Pause, Next, Repeat)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Shuffle
          IconButton(onClick = { viewModel.isSongShuffle.value = !isShuffle }) {
            Icon(
              imageVector = Icons.Default.Shuffle,
              contentDescription = "Shuffle",
              tint = if (isShuffle) EmeraldPrimary else TextMuted
            )
          }

          // Previous
          IconButton(onClick = { viewModel.prevSong() }) {
            Icon(Icons.Default.SkipPrevious, contentDescription = "Previous", tint = TextDark, modifier = Modifier.size(30.dp))
          }

          // Play / Pause
          FilledIconButton(
            onClick = { viewModel.toggleSongPlay() },
            modifier = Modifier.size(56.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = EmeraldPrimary)
          ) {
            Icon(
              imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = "Play/Pause",
              tint = Color.White,
              modifier = Modifier.size(32.dp)
            )
          }

          // Next
          IconButton(onClick = { viewModel.nextSong() }) {
            Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = TextDark, modifier = Modifier.size(30.dp))
          }

          // Repeat
          IconButton(onClick = { viewModel.isSongRepeat.value = !isRepeat }) {
            Icon(
              imageVector = Icons.Default.Repeat,
              contentDescription = "Repeat",
              tint = if (isRepeat) EmeraldPrimary else TextMuted
            )
          }
        }

        // Sing-Along Synchronized Lyrics & Educational Message
        Surface(
          color = IvoryBackground,
          shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
        ) {
          LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            item {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Text("🎤", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Sing-Along Lyrics & Learning",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = EmeraldPrimary
                )
              }
              Spacer(modifier = Modifier.height(10.dp))
            }

            // Lyrics Cards with highlighting
            items(song.lyrics.chunked(2)) { pair ->
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 8.dp)
              ) {
                Column(
                  modifier = Modifier.padding(12.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  pair.forEach { line ->
                    Text(
                      text = line,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = TextDark,
                      textAlign = TextAlign.Center
                    )
                  }
                }
              }
            }

            // Production Standards & Halal Acoustic Note
            item {
              Surface(
                shape = RoundedCornerShape(14.dp),
                color = AmberContainer,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 8.dp)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Text(
                    text = "Educational Core: ${song.educationalMessage}",
                    fontSize = 12.sp,
                    color = OnAmberContainer,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Production standard: Halal acoustic arrangement reviewed by Islamic scholars for pure family listening.",
                    fontSize = 11.sp,
                    color = Color(0xFF78350F)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
