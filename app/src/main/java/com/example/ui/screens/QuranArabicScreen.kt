package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.window.Dialog
import com.example.data.KidsRepository
import com.example.model.ArabicLetter
import com.example.model.ArabicWordItem
import com.example.model.SurahItem
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun QuranArabicScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val selectedTab by viewModel.quranTab.collectAsState()
  val activeSurah by viewModel.activeSurah.collectAsState()
  val selectedLetter by viewModel.selectedLetter.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("quran_arabic_screen")
  ) {
    if (activeSurah == null) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Tab Selector Header (Screen 14: Alphabet, Vocabulary, Short Surahs, Makharij)
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
              onClick = { viewModel.quranTab.value = 0 },
              text = { Text("Alphabet 🔤", fontWeight = FontWeight.Bold) }
            )
            Tab(
              selected = selectedTab == 1,
              onClick = { viewModel.quranTab.value = 1 },
              text = { Text("Vocabulary 🗣️", fontWeight = FontWeight.Bold) }
            )
            Tab(
              selected = selectedTab == 2,
              onClick = { viewModel.quranTab.value = 2 },
              text = { Text("Short Surahs 🌙", fontWeight = FontWeight.Bold) }
            )
            Tab(
              selected = selectedTab == 3,
              onClick = { viewModel.quranTab.value = 3 },
              text = { Text("Pronunciation 🎯", fontWeight = FontWeight.Bold) }
            )
          }
        }

        // Tab Content
        when (selectedTab) {
          0 -> AlphabetTabContent(viewModel, onSelectLetter = { viewModel.selectLetter(it) })
          1 -> VocabularyTabContent(viewModel)
          2 -> ShortSurahsTabContent(viewModel, onOpenSurah = { viewModel.openSurah(it) })
          3 -> PronunciationTabContent(viewModel)
        }
      }
    } else {
      // Screen 14: Quran Recitation Player Modal
      SurahPlayerView(
        surah = activeSurah!!,
        viewModel = viewModel,
        onClose = { viewModel.closeSurah() }
      )
    }

    // Letter Detail Dialog
    if (selectedLetter != null) {
      LetterDetailDialog(
        letter = selectedLetter!!,
        onDismiss = { viewModel.selectLetter(null) },
        onSoundPlay = { viewModel.earnStars(2, "Sound Explored! ⭐") }
      )
    }
  }
}

// ------------------------------------
// 1. Alphabet Tab (All 28 Letters with Shapes)
// ------------------------------------
@Composable
fun AlphabetTabContent(
  viewModel: KidsViewModel,
  onSelectLetter: (ArabicLetter) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = MintContainer,
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp)
    ) {
      Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("🔤", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Tap any letter to hear the sound, explore 4 word shapes & practice phonics!",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = EmeraldDark
        )
      }
    }

    LazyVerticalGrid(
      columns = GridCells.Fixed(4),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(KidsRepository.arabicAlphabet) { letter ->
        Surface(
          shape = RoundedCornerShape(18.dp),
          color = Color.White,
          shadowElevation = 2.dp,
          modifier = Modifier
            .aspectRatio(0.9f)
            .clickable { onSelectLetter(letter) }
            .testTag("arabic_letter_${letter.name.lowercase()}")
        ) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = letter.arabicChar,
              fontFamily = NotoNaskhArabicFontFamily,
              fontSize = 32.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = letter.name,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark
            )
            Text(
              text = letter.exampleEmoji,
              fontSize = 14.sp
            )
          }
        }
      }
    }
  }
}

// ------------------------------------
// 2. Arabic Vocabulary Tab
// ------------------------------------
@Composable
fun VocabularyTabContent(viewModel: KidsViewModel) {
  val activeCategory by viewModel.activeVocabCategory.collectAsState()
  val categories = listOf("All", "Everyday Expressions", "Classroom & Home", "Animals & Nature", "Food & Drink")

  val filteredWords = KidsRepository.arabicVocabulary.filter {
    activeCategory == "All" || it.category == activeCategory
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = Color(0xFFF3E8FF),
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {
      Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("🗣️", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Learn essential Arabic words and Islamic everyday expressions with clear pronunciation!",
          fontSize = 12.sp,
          color = Color(0xFF4C1D95)
        )
      }
    }

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 12.dp)
    ) {
      items(categories) { cat ->
        val isSelected = activeCategory == cat
        FilterChip(
          selected = isSelected,
          onClick = { viewModel.activeVocabCategory.value = cat },
          label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(filteredWords) { word ->
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MintContainer),
              contentAlignment = Alignment.Center
            ) {
              Text(word.emoji, fontSize = 26.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = word.arabic,
                fontFamily = NotoNaskhArabicFontFamily,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldPrimary
              )
              Text(
                text = word.transliteration,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldDark
              )
              Text(
                text = word.englishMeaning,
                fontSize = 12.sp,
                color = Color.Gray
              )
            }
            FilledTonalIconButton(
              onClick = { viewModel.earnStars(2, "Arabic Word Practiced! ⭐") },
              colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MintContainer)
            ) {
              Icon(Icons.Default.VolumeUp, contentDescription = "Play Pronunciation", tint = EmeraldPrimary)
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// 3. Short Surahs Tab
// ------------------------------------
@Composable
fun ShortSurahsTabContent(
  viewModel: KidsViewModel,
  onOpenSurah: (SurahItem) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    item {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = AmberContainer,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 14.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("🌙", fontSize = 28.sp)
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Juz 'Amma Short Surahs",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = OnAmberContainer
            )
            Text(
              text = "Listen, follow highlighted ayahs, and practice with repeat-after-reciter mode!",
              fontSize = 12.sp,
              color = Color(0xFF78350F)
            )
          }
        }
      }
    }

    items(KidsRepository.shortSurahs) { surah ->
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp)
          .clickable { onOpenSurah(surah) }
          .testTag("surah_card_${surah.number}")
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(MintContainer),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${surah.number}",
              fontSize = 14.sp,
              fontWeight = FontWeight.ExtraBold,
              color = EmeraldPrimary
            )
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = surah.nameEnglish,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldDark
            )
            Text(
              text = "${surah.meaningEnglish} • ${surah.totalVerses} Ayahs (${surah.revelationType})",
              fontSize = 12.sp,
              color = Color.Gray
            )
          }
          Text(
            text = surah.nameArabic,
            fontFamily = NotoNaskhArabicFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldPrimary
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(Icons.Default.ChevronRight, contentDescription = "Open", tint = EmeraldPrimary)
        }
      }
    }
  }
}

// ------------------------------------
// 4. Pronunciation & Makharij Tab
// ------------------------------------
@Composable
fun PronunciationTabContent(viewModel: KidsViewModel) {
  val makhrajFilter by viewModel.selectedMakhrajFilter.collectAsState()
  val filters = listOf("All", "Throat (حلقية)", "Tongue (لسانية)", "Lips (شفوية)")

  val filteredLetters = KidsRepository.arabicAlphabet.filter {
    makhrajFilter == "All" || it.makhrajCategory == makhrajFilter
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 90.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = SkyContainer,
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {
      Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("🎯", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Makharij Guide: Discover the origin points of Arabic sounds from the throat, tongue, and lips!",
          fontSize = 12.sp,
          color = SkyBlueDark
        )
      }
    }

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = PaddingValues(bottom = 12.dp)
    ) {
      items(filters) { f ->
        val isSelected = makhrajFilter == f
        FilterChip(
          selected = isSelected,
          onClick = { viewModel.selectedMakhrajFilter.value = f },
          label = { Text(f, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = SkyBlueDark,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(filteredLetters) { letter ->
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MintContainer),
              contentAlignment = Alignment.Center
            ) {
              Text(letter.arabicChar, fontFamily = NotoNaskhArabicFontFamily, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(letter.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                Spacer(modifier = Modifier.width(6.dp))
                Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFF1F5F9)) {
                  Text(letter.makhrajCategory, fontFamily = NotoSansArabicFontFamily, fontSize = 10.sp, color = Color(0xFF475569), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(letter.phoneticGuide, fontSize = 12.sp, color = Color(0xFF334155))
            }
            FilledTonalIconButton(
              onClick = { viewModel.earnStars(2, "Sound Replayed! ⭐") },
              colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MintContainer)
            ) {
              Icon(Icons.Default.VolumeUp, contentDescription = "Play", tint = EmeraldPrimary)
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// Surah Player View Modal (Screen 14)
// ------------------------------------
@Composable
fun SurahPlayerView(
  surah: SurahItem,
  viewModel: KidsViewModel,
  onClose: () -> Unit
) {
  val playingIndex by viewModel.playingAyahIndex.collectAsState()
  val isPlaying by viewModel.isAyahPlaying.collectAsState()
  val isRepeatMode by viewModel.isRepeatAfterReciter.collectAsState()
  val showTransliteration by viewModel.showTransliteration.collectAsState()
  val showTranslation by viewModel.showTranslation.collectAsState()

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
        IconButton(onClick = onClose) {
          Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = EmeraldPrimary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Surah ${surah.nameEnglish}",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldDark
          )
          Text(
            text = "${surah.nameArabic} • ${surah.totalVerses} Verses",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
        // Repeat After Reciter Toggle
        FilterChip(
          selected = isRepeatMode,
          onClick = { viewModel.isRepeatAfterReciter.value = !isRepeatMode },
          label = { Text("Repeat Mode 🔁", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = EmeraldPrimary,
            selectedLabelColor = Color.White
          )
        )
      }
    }

    // Toggles Row (Transliteration / Translation)
    Surface(color = Color(0xFFF8FAFC), modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterChip(
          selected = showTransliteration,
          onClick = { viewModel.showTransliteration.value = !showTransliteration },
          label = { Text("Transliteration", fontSize = 11.sp) }
        )
        FilterChip(
          selected = showTranslation,
          onClick = { viewModel.showTranslation.value = !showTranslation },
          label = { Text("Translation", fontSize = 11.sp) }
        )
      }
    }

    // Ayahs List
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
      // Surah Story Card for Kids
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MintContainer),
          modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
        ) {
          Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("📖", fontSize = 26.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("Story of this Surah for Children", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
              Text(surah.revelationStoryForKids, fontSize = 12.sp, color = Color(0xFF1E293B))
            }
          }
        }
      }

      itemsIndexed(surah.verses) { idx, ayah ->
        val isActive = playingIndex == idx
        Surface(
          shape = RoundedCornerShape(18.dp),
          color = if (isActive) Color(0xFFDCFCE7) else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            if (isActive) EmeraldPrimary else Color(0xFFE2E8F0)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable { viewModel.toggleAyahAudio(idx) }
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(if (isActive) EmeraldPrimary else MintContainer),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "${ayah.verseNumber}",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isActive) Color.White else EmeraldDark
                )
              }
              IconButton(onClick = { viewModel.toggleAyahAudio(idx) }, modifier = Modifier.size(32.dp)) {
                Icon(
                  if (isActive && isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                  contentDescription = "Play Verse",
                  tint = EmeraldPrimary
                )
              }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Verified Uthmani Arabic Text with Full Tashkeel
            Text(
              text = ayah.arabic,
              fontFamily = NotoNaskhArabicFontFamily,
              fontSize = 22.sp,
              fontWeight = FontWeight.Bold,
              color = EmeraldPrimary,
              textAlign = TextAlign.End,
              modifier = Modifier.fillMaxWidth(),
              lineHeight = 36.sp
            )
            if (showTransliteration) {
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = ayah.transliteration,
                fontSize = 13.sp,
                color = Color(0xFF334155),
                lineHeight = 18.sp
              )
            }
            if (showTranslation) {
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = ayah.englishTranslation,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp
              )
            }
          }
        }
      }
    }
  }
}

// ------------------------------------
// Letter Detail Dialog
// ------------------------------------
@Composable
fun LetterDetailDialog(
  letter: ArabicLetter,
  onDismiss: () -> Unit,
  onSoundPlay: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier.fillMaxWidth().padding(16.dp)
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
          Text(letter.makhrajCategory, fontSize = 12.sp, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Box(
          modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(MintContainer),
          contentAlignment = Alignment.Center
        ) {
          Text(letter.arabicChar, fontFamily = NotoNaskhArabicFontFamily, fontSize = 52.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(letter.name, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = EmeraldDark)
        Text(letter.phoneticGuide, fontSize = 13.sp, color = Color(0xFF475569), textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(14.dp))

        // 4 Letter Shapes Table (Isolated, Initial, Medial, Final)
        Text("Letter Shapes in Words:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          LetterShapeBox(label = "Isolated", shape = letter.isolated)
          LetterShapeBox(label = "Initial", shape = letter.initialForm)
          LetterShapeBox(label = "Medial", shape = letter.medialForm)
          LetterShapeBox(label = "Final", shape = letter.finalForm)
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Word Example
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = AmberContainer,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(letter.exampleEmoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                "${letter.exampleWordArabic} (${letter.exampleWordEnglish})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = OnAmberContainer
              )
              Text("Meaning: ${letter.exampleWordMeaning}", fontSize = 12.sp, color = Color(0xFF78350F))
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            onSoundPlay()
          },
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(Icons.Default.VolumeUp, contentDescription = null)
          Spacer(modifier = Modifier.width(6.dp))
          Text("Play Sound & Practice (+2 ⭐)")
        }
      }
    }
  }
}

@Composable
fun LetterShapeBox(label: String, shape: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Box(
      modifier = Modifier
        .size(44.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xFFF1F5F9)),
      contentAlignment = Alignment.Center
    ) {
      Text(shape, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
    }
    Spacer(modifier = Modifier.height(2.dp))
    Text(label, fontSize = 10.sp, color = Color.Gray)
  }
}
