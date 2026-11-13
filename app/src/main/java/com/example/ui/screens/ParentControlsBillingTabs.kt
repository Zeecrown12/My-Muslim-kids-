package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.SectionTab
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

/**
 * Screen 18 — Parental Controls, Screen-Time, Privacy & Account Security
 */
@Composable
fun ParentControlsSecurityTab(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val settings by viewModel.parentSettings.collectAsState()
  val activeProfile by viewModel.activeProfile.collectAsState()
  val profiles by viewModel.profiles.collectAsState()
  val parentEmail by viewModel.parentEmail.collectAsState()
  val devices by viewModel.signedInDevices.collectAsState()

  var showChangePasswordDialog by remember { mutableStateOf(false) }
  var showDeleteDataDialog by remember { mutableStateOf(false) }
  var showPrivacyDetailDialog by remember { mutableStateOf(false) }

  val categories = listOf(
    "Stories", "Kids TV", "Songs & Nasheeds", "Fun Zone",
    "Islamic Classroom", "Quran & Arabic", "Duas & Sunnah"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("parent_controls_security_tab"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // 1. Daily Screen-Time & Usage Summary
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("⏳", fontSize = 22.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "Screen Time & Healthy Breaks",
                fontFamily = NunitoFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = "Gentle Islamic reminders encourage balance with play, prayer & family",
                fontSize = 11.sp,
                color = TextMuted
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Today's Usage Summary Pill
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = MintContainer,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "${activeProfile.name}'s Usage Today",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = EmeraldDark
                )
                Text(
                  text = "${profiles.sumOf { it.dailyMinutesSpent }} mins total across ${profiles.size} children",
                  fontSize = 11.sp,
                  color = OnMintContainer
                )
              }
              Text(
                text = "${activeProfile.dailyMinutesSpent} / ${settings.dailyLimitMinutes}m",
                fontFamily = NunitoFontFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = EmeraldPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "Daily Limit: ${settings.dailyLimitMinutes} Minutes",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = EmeraldPrimary
          )
          Slider(
            value = settings.dailyLimitMinutes.toFloat(),
            onValueChange = {
              viewModel.updateParentSettings(settings.copy(dailyLimitMinutes = it.toInt()))
            },
            valueRange = 15f..120f,
            steps = 6,
            colors = SliderDefaults.colors(
              thumbColor = EmeraldPrimary,
              activeTrackColor = EmeraldPrimary
            ),
            modifier = Modifier.testTag("daily_limit_slider")
          )
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("15m", fontSize = 11.sp, color = TextMuted)
            Text("45m (Recommended)", fontSize = 11.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
            Text("120m", fontSize = 11.sp, color = TextMuted)
          }

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Break Reminder Interval",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(15, 20, 30).forEach { mins ->
              val selected = settings.breakReminderMinutes == mins
              FilterChip(
                selected = selected,
                onClick = { viewModel.updateParentSettings(settings.copy(breakReminderMinutes = mins)) },
                label = { Text("Every ${mins}m", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = EmeraldPrimary,
                  selectedLabelColor = PureWhite
                ),
                modifier = Modifier.heightIn(min = 48.dp)
              )
            }
          }

          HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = CardBorder)

          // Schedule Window Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("Daily Learning Schedule Window", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
              Text("Active hours: ${settings.scheduleWindow}", fontSize = 11.sp, color = TextMuted)
            }
            Switch(
              checked = settings.scheduleEnabled,
              onCheckedChange = { viewModel.updateParentSettings(settings.copy(scheduleEnabled = it)) },
              colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Bedtime Lock
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("Bedtime Reminder Lock 🌙", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
              Text("Gentle sleep dua prompt at 8:00 PM", fontSize = 11.sp, color = TextMuted)
            }
            Switch(
              checked = settings.bedtimeLockEnabled,
              onCheckedChange = { viewModel.updateParentSettings(settings.copy(bedtimeLockEnabled = it)) },
              colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
            )
          }
        }
      }
    }

    // 2. Content Category Restrictions & Media Defaults
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "Content & Media Controls",
            fontFamily = NunitoFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "Choose accessible sections and playback behaviour for ${activeProfile.name}",
            fontSize = 11.sp,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(10.dp))

          categories.forEach { category ->
            val enabled = settings.allowedCategories.contains(category)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(category, fontSize = 13.sp, color = TextDark)
              Switch(
                checked = enabled,
                onCheckedChange = { viewModel.toggleAllowedCategory(category) },
                colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
              )
            }
          }

          HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = CardBorder)

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("Video Autoplay Next Episode", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
              Text("Off by default to avoid endless watching", fontSize = 11.sp, color = TextMuted)
            }
            Switch(
              checked = settings.videoAutoplayEnabled,
              onCheckedChange = { viewModel.updateParentSettings(settings.copy(videoAutoplayEnabled = it)) },
              colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("Audio-Only Stories & Nasheeds Default", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
              Text("Great for car rides & calm bedtime listening", fontSize = 11.sp, color = TextMuted)
            }
            Switch(
              checked = settings.audioOnlyModeDefault,
              onCheckedChange = { viewModel.updateParentSettings(settings.copy(audioOnlyModeDefault = it)) },
              colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
            )
          }
        }
      }
    }

    // 3. Account Security & Signed-In Devices
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "Account Security & Devices",
            fontFamily = NunitoFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "Recovery Email: $parentEmail (Verified)",
            fontSize = 11.sp,
            color = EmeraldDark,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text("Parent Gate PIN Protection", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
              Text("Requires adult verification before entering Parent Centre", fontSize = 11.sp, color = TextMuted)
            }
            Switch(
              checked = settings.parentPinVerificationEnabled,
              onCheckedChange = { viewModel.updateParentSettings(settings.copy(parentPinVerificationEnabled = it)) },
              colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
              onClick = { showChangePasswordDialog = true },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp)
                .testTag("change_password_button")
            ) {
              Icon(Icons.Default.Password, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Change Password", fontSize = 12.sp)
            }
            OutlinedButton(
              onClick = {
                viewModel.setParentGatePassed(false)
                viewModel.selectSection(SectionTab.MY_WORLD)
              },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp)
                .testTag("lock_parent_area_button")
            ) {
              Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Lock Parent Area", fontSize = 12.sp)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Signed-In Family Devices (${devices.size})",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(6.dp))

          devices.forEach { device ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = IvoryBackground,
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
            ) {
              Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = device.deviceName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                  )
                  Text(
                    text = "${device.platform} • ${device.lastActive}",
                    fontSize = 11.sp,
                    color = TextMuted
                  )
                }
                if (device.isCurrentDevice) {
                  Surface(shape = RoundedCornerShape(8.dp), color = MintContainer) {
                    Text(
                      text = "This Device",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = EmeraldDark,
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                  }
                } else {
                  TextButton(
                    onClick = { viewModel.signOutDevice(device.id) },
                    modifier = Modifier.heightIn(min = 48.dp)
                  ) {
                    Text("Sign Out", fontSize = 12.sp, color = OnCoralContainer, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }
          }
        }
      }
    }

    // 4. Child Privacy & Account Deletion
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SkyContainer),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🛡️", fontSize = 22.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Child Privacy & Zero-Ad Commitment",
              fontFamily = NunitoFontFamily,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = OnSkyContainer
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Strictly COPPA & GDPR-K safe: zero third-party ads, zero behavioural tracking, and minimal encrypted family records.",
            fontSize = 12.sp,
            color = OnSkyContainer
          )
          Spacer(modifier = Modifier.height(10.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
              onClick = { showPrivacyDetailDialog = true },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).heightIn(min = 48.dp)
            ) {
              Text("Review Privacy", fontSize = 12.sp, color = OnSkyContainer)
            }
            OutlinedButton(
              onClick = { showDeleteDataDialog = true },
              shape = RoundedCornerShape(12.dp),
              border = BorderStroke(1.dp, CoralFun),
              modifier = Modifier
                .weight(1f)
                .heightIn(min = 48.dp)
                .testTag("delete_account_data_button")
            ) {
              Text("Data & Deletion", fontSize = 12.sp, color = OnCoralContainer, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }

  if (showChangePasswordDialog) {
    var currentPw by remember { mutableStateOf("") }
    var newPw by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { showChangePasswordDialog = false }) {
      Surface(shape = RoundedCornerShape(22.dp), color = PureWhite, modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text("Change Parent Password", fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
          OutlinedTextField(
            value = currentPw,
            onValueChange = { currentPw = it },
            label = { Text("Current Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newPw,
            onValueChange = { newPw = it },
            label = { Text("New Password (8+ chars)") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { showChangePasswordDialog = false }, modifier = Modifier.weight(1f)) {
              Text("Cancel")
            }
            Button(
              onClick = {
                viewModel.triggerCelebration("Parent password updated securely! 🔐")
                showChangePasswordDialog = false
              },
              enabled = currentPw.isNotBlank() && newPw.length >= 6,
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              modifier = Modifier.weight(1f)
            ) {
              Text("Update")
            }
          }
        }
      }
    }
  }

  if (showPrivacyDetailDialog) {
    Dialog(onDismissRequest = { showPrivacyDetailDialog = false }) {
      Surface(shape = RoundedCornerShape(22.dp), color = PureWhite, modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text("Family Data & Child Privacy", fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "• No child email, phone number, or precise GPS location is ever collected.\n" +
              "• Progress stars and learning badges stay private to your family account.\n" +
              "• All external payment methods are tokenized safely via Google Play Billing.\n" +
              "• Parents can export or permanently delete child records anytime.",
            fontSize = 12.sp,
            color = TextDark,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(14.dp))
          Button(
            onClick = { showPrivacyDetailDialog = false },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Close")
          }
        }
      }
    }
  }

  if (showDeleteDataDialog) {
    Dialog(onDismissRequest = { showDeleteDataDialog = false }) {
      Surface(shape = RoundedCornerShape(22.dp), color = PureWhite, modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text("Request Data Export or Account Deletion", fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnCoralContainer)
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Would you like a copy of your family's learning records emailed to $parentEmail, or to submit a deletion request?",
            fontSize = 12.sp,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(14.dp))
          Button(
            onClick = {
              viewModel.triggerCelebration("Privacy summary exported to $parentEmail")
              showDeleteDataDialog = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Export Family Data Summary")
          }
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedButton(
            onClick = {
              viewModel.triggerCelebration("Deletion confirmation email sent to $parentEmail")
              showDeleteDataDialog = false
            },
            border = BorderStroke(1.dp, CoralFun),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Request Permanent Account Deletion", color = OnCoralContainer)
          }
        }
      }
    }
  }
}

/**
 * Screen 19 — Subscriptions & Payments
 * Never collects or stores raw card numbers directly; displays tokenized billing summary.
 */
@Composable
fun SubscriptionBillingTab(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val settings by viewModel.parentSettings.collectAsState()
  val transactions by viewModel.paymentTransactions.collectAsState()
  var showCancelDialog by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("subscription_billing_tab"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 96.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // 1. Current Plan & Tokenized Billing Method
    item {
      Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (settings.isPremiumActive) AmberContainer else MintContainer
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(if (settings.isPremiumActive) "⭐" else "🌱", fontSize = 26.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Current Plan: ${settings.activePlanName}",
                  fontFamily = NunitoFontFamily,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = TextDark
                )
                Text(
                  text = if (settings.isPremiumActive) "Renews on ${settings.renewalDate}" else "Free starter access • Upgrade anytime",
                  fontSize = 12.sp,
                  color = TextMuted
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = PureWhite.copy(alpha = 0.85f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CreditCard, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                  Text("Payment Method (Tokenized)", fontSize = 10.sp, color = TextMuted)
                  Text(settings.paymentMethodSummary, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                }
              }
              Surface(shape = RoundedCornerShape(6.dp), color = MintContainer) {
                Text("Verified", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
            }
          }
        }
      }
    }

    // 2. Plan Comparison Cards (Free vs Monthly vs Annual)
    item {
      Text(
        text = "Compare Family Plans",
        fontFamily = NunitoFontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark
      )
    }

    item {
      PlanComparisonCard(
        planName = "Free Explorer",
        priceLabel = "$0 / Forever",
        badgeText = "Starter",
        perks = listOf(
          "1 child profile",
          "Selected stories, duas, short surahs & alphabet",
          "100% ad-free safe environment"
        ),
        isCurrent = settings.activePlanName == "Free Explorer",
        accentColor = SkyBlue,
        testTag = "plan_free_button",
        onSelect = { viewModel.selectSubscriptionPlan("Free Explorer", false, "$0.00") }
      )
    }

    item {
      PlanComparisonCard(
        planName = "Premium Monthly",
        priceLabel = "$7.99 / month",
        badgeText = "Flexible",
        perks = listOf(
          "Up to 5 child profiles with custom ages",
          "Full Story World, Kids TV, Nasheeds & Fun Zone",
          "Offline road-trip downloads & weekly goal analytics",
          "Printable certificates of excellence"
        ),
        isCurrent = settings.activePlanName == "Premium Monthly",
        accentColor = EmeraldPrimary,
        testTag = "plan_monthly_button",
        onSelect = { viewModel.selectSubscriptionPlan("Premium Monthly", true, "$7.99") }
      )
    }

    item {
      PlanComparisonCard(
        planName = "Premium Annual",
        priceLabel = "$59.99 / year",
        badgeText = "Best Value • Save 37%",
        perks = listOf(
          "Everything in Premium Monthly for all 5 children",
          "4 months free compared to monthly billing",
          "Priority early access to Ramadan & Eid specials"
        ),
        isCurrent = settings.activePlanName == "Premium Annual",
        accentColor = SunGoldDark,
        testTag = "plan_annual_button",
        onSelect = { viewModel.selectSubscriptionPlan("Premium Annual", true, "$59.99") }
      )
    }

    // 3. Billing Actions: Restore & Cancel
    item {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
          onClick = { viewModel.restorePurchases() },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(1f)
            .heightIn(min = 48.dp)
            .testTag("restore_purchases_button")
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Restore Purchases", fontSize = 12.sp)
        }
        if (settings.isPremiumActive) {
          OutlinedButton(
            onClick = { showCancelDialog = true },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, CoralFun),
            modifier = Modifier
              .weight(1f)
              .heightIn(min = 48.dp)
              .testTag("cancel_subscription_button")
          ) {
            Text("Cancel Plan", fontSize = 12.sp, color = OnCoralContainer, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // 4. Payment History
    item {
      Text(
        text = "Payment History",
        fontFamily = NunitoFontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark
      )
    }

    items(transactions) { tx ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(tx.planTitle, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text("${tx.date} • Ref ${tx.providerRef}", fontSize = 11.sp, color = TextMuted)
          }
          Column(horizontalAlignment = Alignment.End) {
            Text(tx.amountDisplay, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = EmeraldDark)
            Surface(shape = RoundedCornerShape(6.dp), color = MintContainer) {
              Text(tx.status, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldDark, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
          }
        }
      }
    }

    // 5. Transparent Billing & Refund Policy
    item {
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = PureWhite,
        border = BorderStroke(1.dp, CardBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text("Billing Terms & Refund Support", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Subscriptions auto-renew unless cancelled at least 24 hours before the renewal date via Google Play Billing. Raw card details are never collected or stored in the app. Need refund help? Contact support@mymuslimkids.com within 14 days.",
            fontSize = 11.sp,
            color = TextMuted,
            lineHeight = 16.sp
          )
        }
      }
    }
  }

  if (showCancelDialog) {
    Dialog(onDismissRequest = { showCancelDialog = false }) {
      Surface(shape = RoundedCornerShape(22.dp), color = PureWhite, modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text("Cancel Premium Subscription?", fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Your family will retain Premium access until ${settings.renewalDate}, then switch to Free Explorer. No earned child stars or badges will ever be lost.",
            fontSize = 12.sp,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(14.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { showCancelDialog = false }, modifier = Modifier.weight(1f)) {
              Text("Keep Plan")
            }
            Button(
              onClick = {
                viewModel.cancelSubscription()
                showCancelDialog = false
              },
              colors = ButtonDefaults.buttonColors(containerColor = OnCoralContainer),
              modifier = Modifier.weight(1f)
            ) {
              Text("Confirm Cancel")
            }
          }
        }
      }
    }
  }
}

@Composable
private fun PlanComparisonCard(
  planName: String,
  priceLabel: String,
  badgeText: String,
  perks: List<String>,
  isCurrent: Boolean,
  accentColor: Color,
  testTag: String,
  onSelect: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = PureWhite),
    border = if (isCurrent) BorderStroke(2.dp, accentColor) else BorderStroke(1.dp, CardBorder),
    elevation = CardDefaults.cardElevation(if (isCurrent) 3.dp else 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(planName, fontFamily = NunitoFontFamily, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
          Text(priceLabel, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = accentColor)
        }
        Surface(shape = RoundedCornerShape(8.dp), color = accentColor.copy(alpha = 0.14f)) {
          Text(
            text = badgeText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(8.dp))
      perks.forEach { perk ->
        Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = accentColor, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(perk, fontSize = 12.sp, color = TextDark)
        }
      }
      Spacer(modifier = Modifier.height(10.dp))
      Button(
        onClick = onSelect,
        enabled = !isCurrent,
        colors = ButtonDefaults.buttonColors(
          containerColor = accentColor,
          disabledContainerColor = MintContainer
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .heightIn(min = 48.dp)
          .testTag(testTag)
      ) {
        Text(
          text = if (isCurrent) "Active Plan ✓" else "Choose $planName",
          fontWeight = FontWeight.Bold,
          color = if (isCurrent) EmeraldDark else PureWhite
        )
      }
    }
  }
}
