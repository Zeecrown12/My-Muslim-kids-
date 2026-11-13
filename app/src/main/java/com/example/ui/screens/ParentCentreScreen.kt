package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.model.AgeGroup
import com.example.model.ChildProfile
import com.example.ui.components.BrandLogoMark
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun ParentCentreScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val settings by viewModel.parentSettings.collectAsState()
  val parentTab by viewModel.parentTab.collectAsState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .testTag("parent_centre_screen")
  ) {
    // Top Parent Bar with Brand Logo
    Surface(
      color = PureWhite,
      tonalElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            BrandLogoMark(size = 38.dp, monochrome = false)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Parent Centre",
                fontFamily = NunitoFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
              )
              Text(
                text = "Family Dashboard & Safe Controls",
                fontSize = 11.sp,
                color = TextMuted
              )
            }
          }

          Surface(
            color = if (settings.isPremiumActive) AmberContainer else SkyContainer,
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = if (settings.isPremiumActive) "⭐ ${settings.activePlanName}" else "Free Plan",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (settings.isPremiumActive) OnAmberContainer else OnSkyContainer,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
          }
        }

        // Sub Tabs (Screens 17, 18, 19, 20–23)
        ScrollableTabRow(
          selectedTabIndex = parentTab,
          containerColor = PureWhite,
          contentColor = EmeraldPrimary,
          edgePadding = 12.dp
        ) {
          Tab(
            selected = parentTab == 0,
            onClick = { viewModel.parentTab.value = 0 },
            modifier = Modifier.testTag("parent_tab_overview"),
            text = { Text("Family & Profiles", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = parentTab == 1,
            onClick = { viewModel.parentTab.value = 1 },
            modifier = Modifier.testTag("parent_tab_controls"),
            text = { Text("Controls & Security", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = parentTab == 2,
            onClick = { viewModel.parentTab.value = 2 },
            modifier = Modifier.testTag("parent_tab_billing"),
            text = { Text("Billing & Plans", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = parentTab == 3,
            onClick = { viewModel.parentTab.value = 3 },
            modifier = Modifier.testTag("parent_tab_admin"),
            text = { Text("Admin Studio", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
          )
        }
      }
    }

    when (parentTab) {
      0 -> ParentOverviewProfilesTab(viewModel = viewModel, modifier = Modifier.weight(1f))
      1 -> ParentControlsSecurityTab(viewModel = viewModel, modifier = Modifier.weight(1f))
      2 -> SubscriptionBillingTab(viewModel = viewModel, modifier = Modifier.weight(1f))
      3 -> AdminStudioWorkspace(viewModel = viewModel, modifier = Modifier.weight(1f))
    }
  }
}

/**
 * Screen 17 — Parent Centre: Family Overview, Child Profiles, Learning Progress & Weekly Goals
 */
@Composable
private fun ParentOverviewProfilesTab(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val profiles by viewModel.profiles.collectAsState()
  val activeProfile by viewModel.activeProfile.collectAsState()
  val settings by viewModel.parentSettings.collectAsState()
  val parentName by viewModel.parentName.collectAsState()
  val parentEmail by viewModel.parentEmail.collectAsState()
  val weeklyGoals by viewModel.weeklyGoals.collectAsState()

  var showAddChildDialog by remember { mutableStateOf(false) }
  var editingProfile by remember { mutableStateOf<ChildProfile?>(null) }
  var removingProfile by remember { mutableStateOf<ChildProfile?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("parent_overview_tab"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 96.dp)
  ) {
    // Family Overview Card (Screen 17)
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 14.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Assalamu Alaikum, $parentName",
                fontFamily = NunitoFontFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
              )
              Text(
                text = parentEmail,
                fontSize = 12.sp,
                color = TextMuted
              )
            }
            Surface(shape = RoundedCornerShape(10.dp), color = MintContainer) {
              Text(
                text = "${profiles.size} Child Profiles",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldDark,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Account Notifications Banner
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = SkyContainer,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
              Text("🔔", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Family Update: ${activeProfile.name} earned ${activeProfile.stars} Stars • Safe 100% Halal Filter Active.",
                fontSize = 11.sp,
                color = OnSkyContainer,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }

    // Child Profiles Header + Add Child CTA
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Child Profiles (${profiles.size})",
          fontFamily = NunitoFontFamily,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )
        Button(
          onClick = { showAddChildDialog = true },
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .heightIn(min = 48.dp)
            .testTag("add_child_profile_button")
        ) {
          Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Add Child", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Child Profiles Cards with Switch, Edit, Remove
    items(profiles, key = { it.id }) { profile ->
      val isActive = profile.id == activeProfile.id
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp)
          .clickable { viewModel.switchProfile(profile) }
          .testTag("profile_item_${profile.name.lowercase()}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isActive) MintContainer else PureWhite
        ),
        border = if (isActive) BorderStroke(1.5.dp, EmeraldPrimary) else null,
        elevation = CardDefaults.cardElevation(if (isActive) 3.dp else 1.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(50.dp)
              .clip(CircleShape)
              .background(Color(profile.avatarBgHex)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = profile.avatarEmoji, fontSize = 26.sp)
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = profile.name,
                fontFamily = NunitoFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              if (isActive) {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(color = EmeraldPrimary, shape = RoundedCornerShape(6.dp)) {
                  Text(
                    text = "ACTIVE",
                    fontSize = 9.sp,
                    color = PureWhite,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                  )
                }
              }
            }
            Text(
              text = "${profile.age} yrs • ${profile.ageGroup.title} • ${profile.preferredLanguage}",
              fontSize = 12.sp,
              color = TextMuted
            )
            Text(
              text = "⭐ ${profile.stars} Stars • 🔥 ${profile.streakDays}d Streak • ${profile.dailyMinutesSpent}m today",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = EmeraldDark
            )
          }

          IconButton(
            onClick = { editingProfile = profile },
            modifier = Modifier
              .size(48.dp)
              .testTag("edit_profile_${profile.name.lowercase()}")
          ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit ${profile.name}", tint = EmeraldDark, modifier = Modifier.size(18.dp))
          }
          if (profiles.size > 1) {
            IconButton(
              onClick = { removingProfile = profile },
              modifier = Modifier
                .size(48.dp)
                .testTag("remove_profile_${profile.name.lowercase()}")
            ) {
              Icon(Icons.Default.DeleteOutline, contentDescription = "Remove ${profile.name}", tint = OnCoralContainer, modifier = Modifier.size(18.dp))
            }
          }
        }
      }
    }

    // Per-Child Learning Progress Breakdown
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Learning Progress (${activeProfile.name})",
            fontFamily = NunitoFontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(10.dp))
          AnalyticsRow("📖 Stories Read", "4 Completed", 0.8f, SkyBlue)
          AnalyticsRow("🌙 Quran & Arabic Letters", "24 Letters Learned", 0.86f, EmeraldPrimary)
          AnalyticsRow("🤲 Duas Memorized", "5 Duas Mastered", 0.65f, SunGoldDark)
          AnalyticsRow("🎮 Quizzes & Adventures", "92% Accuracy", 0.92f, NightPurpleDark)
        }
      }
    }

    // Weekly Learning Goals Configuration (Parent-Controlled)
    item {
      Spacer(modifier = Modifier.height(12.dp))
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Weekly Learning Goals",
            fontFamily = NunitoFontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "Gentle weekly targets to encourage balanced faith learning (no streak punishment).",
            fontSize = 11.sp,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(10.dp))

          weeklyGoals.forEach { goal ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Text(goal.iconEmoji, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text(goal.areaName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                  Text("Progress: ${goal.completedCount} / ${goal.targetCount} per week", fontSize = 11.sp, color = TextMuted)
                }
              }
              Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                  onClick = { viewModel.updateWeeklyGoalTarget(goal.id, goal.targetCount - 1) },
                  modifier = Modifier.size(48.dp)
                ) {
                  Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease goal", tint = TextMuted)
                }
                Text(
                  text = "${goal.targetCount}",
                  fontWeight = FontWeight.ExtraBold,
                  color = EmeraldPrimary,
                  fontSize = 14.sp
                )
                IconButton(
                  onClick = { viewModel.updateWeeklyGoalTarget(goal.id, goal.targetCount + 1) },
                  modifier = Modifier.size(48.dp)
                ) {
                  Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase goal", tint = EmeraldPrimary)
                }
              }
            }
          }
        }
      }
    }

    // Quick Content Category Preferences Summary
    item {
      Spacer(modifier = Modifier.height(12.dp))
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Content Category Preferences",
            fontFamily = NunitoFontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "${settings.allowedCategories.size} of 7 learning sections enabled for child exploration.",
            fontSize = 11.sp,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedButton(
            onClick = { viewModel.parentTab.value = 1 },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .heightIn(min = 48.dp)
          ) {
            Text("Customise Category & Screen-Time Controls →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }

  // Add Child Dialog
  if (showAddChildDialog) {
    ChildProfileEditorDialog(
      dialogTitle = "Add New Child Profile",
      confirmLabel = "Create Profile 🎉",
      initialName = "",
      initialAge = 6,
      initialAgeGroup = AgeGroup.LITTLE_EXPLORERS,
      initialEmoji = "⭐",
      initialLanguage = "English",
      onDismiss = { showAddChildDialog = false },
      onConfirm = { name, age, ageGroup, emoji, _ ->
        viewModel.addProfile(name, age, ageGroup, emoji)
        showAddChildDialog = false
      }
    )
  }

  // Edit Child Dialog
  if (editingProfile != null) {
    val target = editingProfile!!
    ChildProfileEditorDialog(
      dialogTitle = "Edit ${target.name}'s Profile",
      confirmLabel = "Save Changes ✓",
      initialName = target.name,
      initialAge = target.age,
      initialAgeGroup = target.ageGroup,
      initialEmoji = target.avatarEmoji,
      initialLanguage = target.preferredLanguage,
      onDismiss = { editingProfile = null },
      onConfirm = { name, age, ageGroup, emoji, lang ->
        viewModel.editChildProfile(target.id, name, age, ageGroup, emoji, lang)
        editingProfile = null
      }
    )
  }

  // Remove Child Confirmation Dialog
  if (removingProfile != null) {
    val target = removingProfile!!
    Dialog(onDismissRequest = { removingProfile = null }) {
      Surface(shape = RoundedCornerShape(22.dp), color = PureWhite, modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
          Text("Remove ${target.name}'s Profile?", fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "This removes ${target.name}'s local child profile from this family dashboard. You can create a new profile anytime.",
            fontSize = 12.sp,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(14.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { removingProfile = null }, modifier = Modifier.weight(1f)) {
              Text("Cancel")
            }
            Button(
              onClick = {
                viewModel.removeChildProfile(target.id)
                removingProfile = null
              },
              colors = ButtonDefaults.buttonColors(containerColor = OnCoralContainer),
              modifier = Modifier.weight(1f)
            ) {
              Text("Remove")
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ChildProfileEditorDialog(
  dialogTitle: String,
  confirmLabel: String,
  initialName: String,
  initialAge: Int,
  initialAgeGroup: AgeGroup,
  initialEmoji: String,
  initialLanguage: String,
  onDismiss: () -> Unit,
  onConfirm: (String, Int, AgeGroup, String, String) -> Unit
) {
  var name by remember { mutableStateOf(initialName) }
  var ageText by remember { mutableStateOf(initialAge.toString()) }
  var selectedGroup by remember { mutableStateOf(initialAgeGroup) }
  var selectedEmoji by remember { mutableStateOf(initialEmoji) }
  var selectedLang by remember { mutableStateOf(initialLanguage) }

  val emojis = listOf("⭐", "🦁", "🌸", "🦅", "🌙", "🐬", "🦋", "🚀")
  val languages = listOf("English", "Arabic", "French", "Urdu")

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = PureWhite,
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(dialogTitle, fontFamily = NunitoFontFamily, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)

        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Child Name") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
          value = ageText,
          onValueChange = {
            ageText = it
            val age = it.toIntOrNull() ?: 6
            selectedGroup = when {
              age <= 6 -> AgeGroup.LITTLE_EXPLORERS
              age <= 9 -> AgeGroup.YOUNG_LEARNERS
              else -> AgeGroup.YOUNG_SCHOLARS
            }
          },
          label = { Text("Age (4–12)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )

        Text("Learning Band: ${selectedGroup.title} (${selectedGroup.ageRange})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)

        Text("Preferred Language:", fontSize = 12.sp, color = TextMuted)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          languages.forEach { lang ->
            FilterChip(
              selected = selectedLang == lang,
              onClick = { selectedLang = lang },
              label = { Text(lang, fontSize = 11.sp) }
            )
          }
        }

        Text("Avatar Character:", fontSize = 12.sp, color = TextMuted)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          emojis.forEach { emo ->
            val isPicked = selectedEmoji == emo
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (isPicked) MintContainer else IvoryBackground)
                .clickable { selectedEmoji = emo },
              contentAlignment = Alignment.Center
            ) {
              Text(text = emo, fontSize = 18.sp)
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
          OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) {
            Text("Cancel")
          }
          Button(
            onClick = {
              if (name.isNotBlank()) {
                val age = (ageText.toIntOrNull() ?: 6).coerceIn(4, 12)
                onConfirm(name.trim(), age, selectedGroup, selectedEmoji, selectedLang)
              }
            },
            enabled = name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f).heightIn(min = 48.dp)
          ) {
            Text(confirmLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
private fun AnalyticsRow(title: String, stat: String, progress: Float, color: Color) {
  Column(modifier = Modifier.padding(bottom = 10.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextDark)
      Text(stat, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
    }
    Spacer(modifier = Modifier.height(4.dp))
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(7.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = color,
      trackColor = CardBorder
    )
  }
}
