package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.KidsRepository
import com.example.model.*
import com.example.ui.components.BrandLogoMark
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun AdminStudioWorkspace(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val isVerified by viewModel.isAdminStaffVerified.collectAsState()
  val navSection by viewModel.adminNavSection.collectAsState()
  val reviewItems by viewModel.reviewItems.collectAsState()
  val auditLogs by viewModel.auditLogs.collectAsState()
  val profiles by viewModel.profiles.collectAsState()

  var staffEmailInput by remember { mutableStateOf("ustadh.ahmad@mymuslimkids.com") }
  var staffPasscode by remember { mutableStateOf("STAFF-2026") }

  if (!isVerified) {
    // Private Staff Access Gate (Screen 20 Security Requirement)
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
        .testTag("admin_staff_gate"),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = BorderStroke(1.5.dp, TextDark.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(22.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          BrandLogoMark(size = 48.dp, monochrome = false)
          Spacer(modifier = Modifier.height(10.dp))
          Surface(shape = RoundedCornerShape(10.dp), color = CoralContainer) {
            Text(
              text = "PRIVATE STAFF & SCHOLAR PORTAL",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = OnCoralContainer,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Admin Studio Access",
            fontFamily = NunitoFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Authorised staff only. Child accounts cannot access content publishing, Islamic review, or analytics.",
            fontFamily = NunitoSansFontFamily,
            fontSize = 12.sp,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(14.dp))
          OutlinedTextField(
            value = staffEmailInput,
            onValueChange = { staffEmailInput = it },
            label = { Text("Staff Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = staffPasscode,
            onValueChange = { staffPasscode = it },
            label = { Text("Staff Access Code") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = { viewModel.verifyAdminStaff(staffEmailInput.substringBefore("@")) },
            colors = ButtonDefaults.buttonColors(containerColor = TextDark),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("admin_unlock_button")
          ) {
            Icon(Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Enter Admin Studio", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  } else {
    Column(
      modifier = modifier
        .fillMaxSize()
        .testTag("admin_studio_dashboard")
    ) {
      // Admin Studio Staff Bar + 15 Navigation Sections
      Surface(
        color = TextDark,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(top = 10.dp, bottom = 8.dp)) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              BrandLogoMark(size = 28.dp, monochrome = true, monochromeTint = PureWhite)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Admin Studio",
                  fontFamily = NunitoFontFamily,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = PureWhite
                )
                Text(
                  text = "Audit-Logged Session • Staff Verified",
                  fontSize = 10.sp,
                  color = SunGoldLight
                )
              }
            }
            TextButton(
              onClick = { viewModel.lockAdminStudio() },
              colors = ButtonDefaults.textButtonColors(contentColor = CoralFunLight)
            ) {
              Icon(Icons.Default.Lock, contentDescription = "Lock", modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Lock", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // 15-Section Admin Navigation Rail (Screen 20)
          LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            items(AdminNavSection.entries) { section ->
              val isSelected = navSection == section
              Surface(
                onClick = { viewModel.adminNavSection.value = section },
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) EmeraldPrimary else PureWhite.copy(alpha = 0.12f),
                modifier = Modifier
                  .heightIn(min = 38.dp)
                  .testTag("admin_nav_${section.name.lowercase()}")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(section.emoji, fontSize = 13.sp)
                  Spacer(modifier = Modifier.width(5.dp))
                  Text(
                    text = section.title,
                    fontFamily = NunitoFontFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                  )
                }
              }
            }
          }
        }
      }

      // Main Section Content Area
      when (navSection) {
        AdminNavSection.DASHBOARD -> AdminOverviewSection(
          reviewItems = reviewItems,
          auditLogs = auditLogs,
          childProfileCount = profiles.size,
          onJumpTo = { viewModel.adminNavSection.value = it }
        )

        AdminNavSection.STORIES,
        AdminNavSection.VIDEOS,
        AdminNavSection.SONGS,
        AdminNavSection.GAMES,
        AdminNavSection.LESSONS,
        AdminNavSection.QURAN_ARABIC,
        AdminNavSection.DUAS_SUNNAH -> AdminContentEditorSection(
          initialType = when (navSection) {
            AdminNavSection.STORIES -> "Story"
            AdminNavSection.VIDEOS -> "Cartoon"
            AdminNavSection.SONGS -> "Song"
            AdminNavSection.GAMES -> "Game"
            AdminNavSection.LESSONS -> "Lesson"
            AdminNavSection.QURAN_ARABIC -> "Quran & Arabic"
            else -> "Duas & Sunnah"
          },
          existingTitles = reviewItems.map { it.title } + KidsRepository.stories.map { it.title },
          onSubmit = { title, type, cat, desc, body, age, lang, dur, src, draft ->
            viewModel.submitNewContentDraft(title, type, cat, desc, body, age, lang, dur, src, draft)
          }
        )

        AdminNavSection.CONTENT_REVIEW -> AdminContentReviewSection(
          reviewItems = reviewItems,
          onReviewAction = { id, status, comment ->
            viewModel.advanceReviewAction(id, status, comment)
          }
        )

        AdminNavSection.REPORTS_ANALYTICS -> AdminReportsAnalyticsSection(
          childCount = profiles.size
        )

        else -> AdminManagementDirectorySection(
          section = navSection,
          profiles = profiles,
          auditLogs = auditLogs
        )
      }
    }
  }
}

@Composable
private fun AdminOverviewSection(
  reviewItems: List<ContentReviewItem>,
  auditLogs: List<AuditLogEntry>,
  childProfileCount: Int,
  onJumpTo: (AdminNavSection) -> Unit
) {
  val publishedCount = KidsRepository.stories.size +
    KidsRepository.tvEpisodes.size +
    KidsRepository.songs.size +
    KidsRepository.gameCards.size +
    KidsRepository.lessons.size +
    KidsRepository.dailyDuas.size
  val pendingCount = reviewItems.count {
    it.workflowStatus == ReviewWorkflowStatus.SUBMITTED ||
      it.workflowStatus == ReviewWorkflowStatus.IN_REVIEW ||
      it.workflowStatus == ReviewWorkflowStatus.CHANGES_REQUIRED
  }
  val scheduledCount = reviewItems.count { it.workflowStatus == ReviewWorkflowStatus.SCHEDULED }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp)
  ) {
    item {
      Text(
        text = "Platform Overview (Screen 20)",
        fontFamily = NunitoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
      )
      Spacer(modifier = Modifier.height(10.dp))

      // Top KPI Grid
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        AdminStatCard("Published Library", "$publishedCount Items", "📚", MintContainer, EmeraldDark, Modifier.weight(1f))
        AdminStatCard("Awaiting Review", "$pendingCount Pending", "⏳", AmberContainer, OnAmberContainer, Modifier.weight(1f))
      }
      Spacer(modifier = Modifier.height(8.dp))
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        AdminStatCard("Scheduled Releases", "$scheduledCount Upcoming", "🗓️", SkyContainer, OnSkyContainer, Modifier.weight(1f))
        AdminStatCard("Family Profiles", "1 Parent • $childProfileCount Kids", "👨‍👩‍👧‍👦", NightPurpleLight, NightPurpleDark, Modifier.weight(1f))
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Catalog Breakdown
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("Catalog Breakdown", fontFamily = NunitoFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Spacer(modifier = Modifier.height(8.dp))
          val catalog = listOf(
            "Stories" to "${KidsRepository.stories.size} published",
            "Cartoons & Videos" to "${KidsRepository.tvEpisodes.size} published",
            "Songs & Nasheeds" to "${KidsRepository.songs.size} published",
            "Games & Activities" to "${KidsRepository.gameCards.size} published",
            "Islamic Classroom Lessons" to "${KidsRepository.lessons.size} published",
            "Duas & Sunnah" to "${KidsRepository.dailyDuas.size} duas • ${KidsRepository.sunnahHabits.size} habits"
          )
          catalog.forEach { (label, count) ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(label, fontSize = 12.sp, color = TextDark)
              Text(count, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Quick Actions Row
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Button(
          onClick = { onJumpTo(AdminNavSection.STORIES) },
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f).heightIn(min = 48.dp)
        ) {
          Text("+ Upload Content", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Button(
          onClick = { onJumpTo(AdminNavSection.CONTENT_REVIEW) },
          colors = ButtonDefaults.buttonColors(containerColor = SkyBlueDark),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f).heightIn(min = 48.dp)
        ) {
          Text("Review Queue ($pendingCount)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
      Text(
        text = "Recent Staff Audit Logs",
        fontFamily = NunitoFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark,
        modifier = Modifier.padding(bottom = 8.dp)
      )
    }

    items(auditLogs) { log ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp)
      ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(CircleShape)
              .background(MintContainer),
            contentAlignment = Alignment.Center
          ) {
            Text("📝", fontSize = 16.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(log.action, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Text("${log.staffName} (${log.role}) • ${log.timestamp}", fontSize = 10.sp, color = TextMuted)
          }
        }
      }
    }
  }
}

@Composable
private fun AdminStatCard(
  label: String,
  value: String,
  emoji: String,
  bgColor: Color,
  textColor: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = bgColor,
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Text(emoji, fontSize = 20.sp)
      Spacer(modifier = Modifier.height(4.dp))
      Text(value, fontFamily = NunitoFontFamily, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = textColor)
      Text(label, fontSize = 11.sp, color = textColor.copy(alpha = 0.85f))
    }
  }
}

/**
 * Screen 21 — Adaptive Content Upload & Editor
 * Adapts form fields dynamically to the chosen content type.
 */
@Composable
private fun AdminContentEditorSection(
  initialType: String,
  existingTitles: List<String>,
  onSubmit: (String, String, String, String, String, AgeGroup, String, String, String, Boolean) -> Unit
) {
  val contentTypes = listOf("Story", "Cartoon", "Educational Video", "Song", "Game", "Lesson", "Quran & Arabic", "Duas & Sunnah")
  var selectedType by remember(initialType) { mutableStateOf(initialType) }
  var title by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Good Manners and Character") }
  var shortDesc by remember { mutableStateOf("") }
  var mainBody by remember { mutableStateOf("") }
  var selectedAge by remember { mutableStateOf(AgeGroup.YOUNG_LEARNERS) }
  var language by remember { mutableStateOf("English") }
  var durationOrObjective by remember { mutableStateOf("") }
  var sourceRef by remember { mutableStateOf("") }
  var showPreviewDialog by remember { mutableStateOf(false) }

  val isDuplicate = title.isNotBlank() && existingTitles.any { it.equals(title.trim(), ignoreCase = true) }

  LazyColumn(
    modifier = Modifier.fillMaxSize().testTag("admin_content_editor"),
    contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp)
  ) {
    item {
      Text(
        text = "Content Upload & Adaptive Editor (Screen 21)",
        fontFamily = NunitoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
      )
      Text(
        text = "Fields adapt automatically based on the selected content type.",
        fontSize = 12.sp,
        color = TextMuted
      )
      Spacer(modifier = Modifier.height(10.dp))

      LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(contentTypes) { type ->
          FilterChip(
            selected = selectedType == type,
            onClick = { selectedType = type },
            label = { Text(type, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = EmeraldPrimary,
              selectedLabelColor = PureWhite
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("$selectedType Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          if (isDuplicate) {
            Surface(shape = RoundedCornerShape(10.dp), color = AmberContainer) {
              Text(
                text = "⚠️ Duplicate Warning: Content with this title already exists in the catalog.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = OnAmberContainer,
                modifier = Modifier.padding(8.dp)
              )
            }
          }

          OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = shortDesc,
            onValueChange = { shortDesc = it },
            label = { Text("Short Description") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          // Adaptive field depending on content type!
          val mainFieldLabel = when (selectedType) {
            "Song" -> "Synchronized Lyrics & Acoustic/Duff Notes"
            "Quran & Arabic" -> "Verified Arabic Text (Full Tashkeel) & Tajwid Notes"
            "Duas & Sunnah" -> "Arabic Supplication, Transliteration & Meaning"
            "Lesson" -> "5-Step Lesson Plan (Intro, Explain, Example, Practice, Review)"
            "Game" -> "Game Rules, Question Set & Star Rewards"
            "Cartoon", "Educational Video" -> "Script, Chapter Timestamps & Subtitle Tracks"
            else -> "Story Pages & Moral Takeaway (No Prophet Depiction)"
          }

          OutlinedTextField(
            value = mainBody,
            onValueChange = { mainBody = it },
            label = { Text(mainFieldLabel) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          val secondaryLabel = when (selectedType) {
            "Lesson" -> "Learning Objectives"
            "Quran & Arabic" -> "Authorised Reciter Recording License"
            "Game" -> "Difficulty Level (Easy / Medium / Explorer)"
            else -> "Duration & Media Attachment (e.g. 4:30 MP4 / MP3)"
          }
          OutlinedTextField(
            value = durationOrObjective,
            onValueChange = { durationOrObjective = it },
            label = { Text(secondaryLabel) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          OutlinedTextField(
            value = sourceRef,
            onValueChange = { sourceRef = it },
            label = { Text("Islamic Source & Review References (Quran / Hadith)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          )

          // Age Group & Language Row
          Text("Target Age Band:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            AgeGroup.entries.forEach { age ->
              FilterChip(
                selected = selectedAge == age,
                onClick = { selectedAge = age },
                label = { Text(age.ageRange, fontSize = 11.sp) }
              )
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
              onClick = { showPreviewDialog = true },
              enabled = title.isNotBlank(),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).heightIn(min = 48.dp)
            ) {
              Text("Preview", fontSize = 12.sp)
            }
            OutlinedButton(
              onClick = {
                if (title.isNotBlank()) {
                  onSubmit(title.trim(), selectedType, category, shortDesc, mainBody, selectedAge, language, durationOrObjective.ifBlank { "5 min" }, sourceRef, true)
                  title = ""; shortDesc = ""; mainBody = ""
                }
              },
              enabled = title.isNotBlank(),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).heightIn(min = 48.dp)
            ) {
              Text("Save Draft", fontSize = 12.sp)
            }
          }

          Button(
            onClick = {
              if (title.isNotBlank()) {
                onSubmit(title.trim(), selectedType, category, shortDesc, mainBody, selectedAge, language, durationOrObjective.ifBlank { "5 min" }, sourceRef, false)
                title = ""; shortDesc = ""; mainBody = ""
              }
            },
            enabled = title.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
          ) {
            Text("Submit to 4-Stage Review Pipeline ✅", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }

  if (showPreviewDialog) {
    Dialog(onDismissRequest = { showPreviewDialog = false }) {
      Surface(shape = RoundedCornerShape(22.dp), color = PureWhite, modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text("Child Experience Preview", fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
          Spacer(modifier = Modifier.height(6.dp))
          Text("[$selectedType] $title", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Text("Target: ${selectedAge.title} • $category", fontSize = 12.sp, color = TextMuted)
          Spacer(modifier = Modifier.height(8.dp))
          Text(shortDesc.ifBlank { "Preview summary appears here for review." }, fontSize = 13.sp, color = TextDark)
          Spacer(modifier = Modifier.height(14.dp))
          Button(onClick = { showPreviewDialog = false }, modifier = Modifier.fillMaxWidth()) {
            Text("Close Preview")
          }
        }
      }
    }
  }
}
