package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
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
import com.example.data.KidsRepository
import com.example.model.*
import com.example.ui.theme.*

/**
 * Screen 22 — Content Review and Approval (4-Stage Verification Pipeline & 8 Statuses)
 */
@Composable
fun AdminContentReviewSection(
  reviewItems: List<ContentReviewItem>,
  onReviewAction: (String, ReviewWorkflowStatus, String) -> Unit
) {
  var statusFilter by remember { mutableStateOf("All") }
  var selectedCommentMap by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

  val filterLabels = listOf("All") + ReviewWorkflowStatus.entries.map { it.label }
  val filtered = reviewItems.filter { statusFilter == "All" || it.workflowStatus.label == statusFilter }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("admin_content_review_section"),
    contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp)
  ) {
    item {
      Text(
        text = "Content Review & Approval (Screen 22)",
        fontFamily = NunitoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      // Mandatory Human Islamic Review Rule Banner
      Surface(
        shape = RoundedCornerShape(14.dp),
        color = MintContainer,
        border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
          Text("🛡️", fontSize = 20.sp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "4-Stage Gate: Production -> Islamic Review -> Visual & Audio -> Final Approval. AI spelling checks never replace human scholar sign-off.",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = OnMintContainer
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(filterLabels) { lbl ->
          FilterChip(
            selected = statusFilter == lbl,
            onClick = { statusFilter = lbl },
            label = { Text(lbl, fontSize = 11.sp) }
          )
        }
      }
      Spacer(modifier = Modifier.height(10.dp))
    }

    items(filtered) { item ->
      val statusColor = when (item.workflowStatus) {
        ReviewWorkflowStatus.PUBLISHED, ReviewWorkflowStatus.APPROVED -> MintContainer
        ReviewWorkflowStatus.SCHEDULED -> SkyContainer
        ReviewWorkflowStatus.CHANGES_REQUIRED -> CoralContainer
        else -> AmberContainer
      }
      val statusTextColor = when (item.workflowStatus) {
        ReviewWorkflowStatus.PUBLISHED, ReviewWorkflowStatus.APPROVED -> EmeraldDark
        ReviewWorkflowStatus.SCHEDULED -> OnSkyContainer
        ReviewWorkflowStatus.CHANGES_REQUIRED -> OnCoralContainer
        else -> OnAmberContainer
      }

      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp)
          .testTag("review_item_${item.id}")
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Surface(color = NightPurpleLight, shape = RoundedCornerShape(8.dp)) {
                Text(
                  text = "${item.type} • v${item.version}",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = NightPurpleDark,
                  modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                )
              }
              Surface(color = statusColor, shape = RoundedCornerShape(8.dp)) {
                Text(
                  text = item.workflowStatus.label,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = statusTextColor,
                  modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                )
              }
            }
            Text(item.targetAge.ageRange, fontSize = 11.sp, color = TextMuted)
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(item.title, fontFamily = NunitoFontFamily, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
          Text("Submitted by: ${item.submittedBy} • Source: ${item.sourceReference}", fontSize = 11.sp, color = TextMuted)

          if (item.reviewerComment.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Surface(shape = RoundedCornerShape(10.dp), color = IvoryBackground) {
              Text(
                text = "Reviewer note: ${item.reviewerComment}",
                fontSize = 11.sp,
                color = TextDark,
                modifier = Modifier.padding(8.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          // 4-Stage Pipeline Visual
          item.stages.forEach { st ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                if (st.passed) Icons.Default.CheckCircle else Icons.Default.Schedule,
                contentDescription = null,
                tint = if (st.passed) EmeraldPrimary else SunGoldDark,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Stage ${st.stageNumber}: ${st.stageName}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "(${st.reviewerName})",
                fontSize = 10.sp,
                color = TextMuted
              )
            }
          }

          if (item.approvedBy != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Approved by ${item.approvedBy} • ${item.approvedTimestamp ?: ""}",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = EmeraldDark
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          val draftComment = selectedCommentMap[item.id] ?: ""
          OutlinedTextField(
            value = draftComment,
            onValueChange = { selectedCommentMap = selectedCommentMap + (item.id to it) },
            label = { Text("Reviewer feedback or verification note") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
          )

          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
              onClick = {
                onReviewAction(
                  item.id,
                  ReviewWorkflowStatus.CHANGES_REQUIRED,
                  draftComment.ifBlank { "Corrections requested after Islamic/media check." }
                )
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).heightIn(min = 48.dp)
            ) {
              Text("Request Changes", fontSize = 11.sp, color = OnCoralContainer)
            }
            Button(
              onClick = {
                onReviewAction(
                  item.id,
                  ReviewWorkflowStatus.PUBLISHED,
                  draftComment.ifBlank { "All 4 stages verified & approved for children." }
                )
              },
              colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f).heightIn(min = 48.dp)
            ) {
              Text("Approve & Publish", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

/**
 * Screen 23 — Reports and Analytics
 */
@Composable
fun AdminReportsAnalyticsSection(childCount: Int) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("admin_reports_analytics"),
    contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp)
  ) {
    item {
      Text(
        text = "Reports & Analytics (Screen 23)",
        fontFamily = NunitoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
      )
      Spacer(modifier = Modifier.height(8.dp))

      Surface(
        shape = RoundedCornerShape(14.dp),
        color = SkyContainer,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
      ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
          Text("🔒", fontSize = 20.sp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "COPPA & Privacy Safe: Aggregate operational analytics only. Zero individual child tracking or behavioural ads.",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = OnSkyContainer
          )
        }
      }
    }

    // 1. Content Performance
    item {
      AnalyticsGroupCard(
        title = "Content Performance",
        emoji = "📚",
        rows = listOf(
          Triple("Most Viewed Story: Prophet Yunus & the Whale", "4,820 reads", 0.88f),
          Triple("Top Cartoon: Zayd & Maryam Ep 1", "6,140 plays", 0.94f),
          Triple("Popular Song: The 5 Pillars Nasheed", "5,210 listens", 0.82f),
          Triple("Most Played Game: Wudu Step-by-Step", "3,905 plays", 0.76f),
          Triple("Lesson Completion Rate", "86% avg", 0.86f)
        ),
        accent = EmeraldPrimary
      )
    }

    // 2. Family Engagement
    item {
      AnalyticsGroupCard(
        title = "Family Engagement",
        emoji = "👨‍👩‍👧‍👦",
        rows = listOf(
          Triple("Active Parent Accounts", "1,420 families", 0.85f),
          Triple("Active Child Profiles (Demo Device: $childCount)", "2,890 kids", 0.90f),
          Triple("Returning Weekly Families", "79% retention", 0.79f),
          Triple("Age Split: Little Explorers / Young Learners / Scholars", "38% / 41% / 21%", 0.72f)
        ),
        accent = SkyBlue
      )
    }

    // 3. Subscription Performance
    item {
      AnalyticsGroupCard(
        title = "Subscription Performance",
        emoji = "💳",
        rows = listOf(
          Triple("Free Explorer Accounts", "810 families", 0.57f),
          Triple("Premium Monthly Subscribers", "390 families", 0.64f),
          Triple("Premium Annual Subscribers", "220 families", 0.48f),
          Triple("Renewal Success Rate", "95.4% clean", 0.95f),
          Triple("Cancellations / Failed Payments", "2.1% / 0.8%", 0.12f)
        ),
        accent = SunGoldDark
      )
    }

    // 4. Technical Performance
    item {
      AnalyticsGroupCard(
        title = "Technical Performance & Stability",
        emoji = "⚡",
        rows = listOf(
          Triple("Crash-Free Android & Web Sessions", "99.84%", 0.99f),
          Triple("Video Playback Health (Zero Buffer)", "98.7%", 0.98f),
          Triple("Slow Loading / CDN Delivery Errors", "0.3% minor", 0.08f),
          Triple("Failed Admin Studio Uploads", "0 errors", 0.02f)
        ),
        accent = NightPurpleDark
      )
    }
  }
}

@Composable
private fun AnalyticsGroupCard(
  title: String,
  emoji: String,
  rows: List<Triple<String, String, Float>>,
  accent: Color
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = PureWhite),
    elevation = CardDefaults.cardElevation(2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 12.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(emoji, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(title, fontFamily = NunitoFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
      }
      Spacer(modifier = Modifier.height(10.dp))
      rows.forEach { (label, metric, prog) ->
        Column(modifier = Modifier.padding(bottom = 8.dp)) {
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontSize = 11.sp, color = TextDark, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            Text(metric, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = accent)
          }
          Spacer(modifier = Modifier.height(3.dp))
          LinearProgressIndicator(
            progress = { prog },
            modifier = Modifier
              .fillMaxWidth()
              .height(5.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = accent,
            trackColor = CardBorder
          )
        }
      }
    }
  }
}

@Composable
fun AdminManagementDirectorySection(
  section: AdminNavSection,
  profiles: List<ChildProfile>,
  auditLogs: List<AuditLogEntry>
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 96.dp)
  ) {
    item {
      Text(
        text = "${section.emoji} ${section.title}",
        fontFamily = NunitoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
      )
      Spacer(modifier = Modifier.height(10.dp))
    }

    when (section) {
      AdminNavSection.LANGUAGES -> {
        val langs = listOf(
          "English (Primary)" to "100% Translated & Verified",
          "العربية (Arabic)" to "100% Full Tashkeel Verified",
          "Français (French)" to "92% Stories & Duas Complete",
          "اردو (Urdu)" to "85% Stories & Classroom Complete"
        )
        items(langs) { (lang, status) ->
          DirectoryInfoCard(lang, status, "🌐")
        }
      }
      AdminNavSection.PARENTS_PROFILES -> {
        items(profiles) { child ->
          DirectoryInfoCard(
            "${child.avatarEmoji} ${child.name} (${child.ageGroup.title})",
            "Stars: ${child.stars} • Language: ${child.preferredLanguage} • Active Family Account",
            "👧"
          )
        }
      }
      AdminNavSection.SUBSCRIPTIONS -> {
        items(KidsRepository.paymentTransactions) { tx ->
          DirectoryInfoCard(
            "${tx.planTitle} • ${tx.amountDisplay}",
            "${tx.date} • Provider Ref: ${tx.providerRef} • Status: ${tx.status}",
            "💳"
          )
        }
      }
      AdminNavSection.STAFF_PERMISSIONS -> {
        val staff = listOf(
          "Shaykh Yusuf Al-Amin" to "Senior Islamic Reviewer • Final Islamic Approval",
          "Ustadh Ahmad" to "Curriculum & Hadith Reviewer • Stage 2 Sign-off",
          "Ustadha Amina" to "Senior Editor • Scheduling & Translation QA",
          "Media Sound Lab" to "Content Creator • Drafts & Audio Mixing Only"
        )
        items(staff) { (name, role) ->
          DirectoryInfoCard(name, role, "🔑")
        }
      }
      else -> {
        items(auditLogs) { log ->
          DirectoryInfoCard(log.action, "${log.staffName} (${log.role}) • ${log.timestamp}", "⚙️")
        }
      }
    }
  }
}

@Composable
private fun DirectoryInfoCard(title: String, subtitle: String, emoji: String) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = PureWhite),
    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
  ) {
    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier.size(40.dp).clip(CircleShape).background(MintContainer),
        contentAlignment = Alignment.Center
      ) {
        Text(emoji, fontSize = 18.sp)
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(title, fontFamily = NunitoFontFamily, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(subtitle, fontSize = 11.sp, color = TextMuted)
      }
    }
  }
}
