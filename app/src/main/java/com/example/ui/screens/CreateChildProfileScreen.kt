package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsRepository
import com.example.model.AgeGroup
import com.example.model.AppFlowScreen
import com.example.model.CharacterAvatar
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun CreateChildProfileScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  var childName by remember { mutableStateOf("") }
  var selectedAgeGroup by remember { mutableStateOf(AgeGroup.LITTLE_EXPLORERS) }
  var childAge by remember { mutableStateOf(5) }
  var selectedAvatar by remember { mutableStateOf(KidsRepository.characterAvatars[0]) }
  var preferredLanguage by remember { mutableStateOf("English") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  val avatars = KidsRepository.characterAvatars
  val languages = listOf("English", "العربية", "Français", "اردو", "Bahasa")
  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(IvoryBackground)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("create_child_profile_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
      // Top Navigation
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = { viewModel.navigateToFlow(AppFlowScreen.PARENT_AUTH) },
          modifier = Modifier.testTag("back_from_create_profile")
        ) {
          Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Column {
          Text(
            text = "Create a Child Profile",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
          )
          Text(
            text = "Step 2 of 2: Set up your child's journey",
            fontSize = 11.sp,
            color = TextMuted
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Privacy Guarantee Callout
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = MintContainer,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text("🔒", fontSize = 20.sp)
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Child Privacy Guarantee: We only collect a first name or nickname. No child email or sensitive data is ever requested.",
            fontSize = 11.sp,
            color = OnMintContainer,
            lineHeight = 16.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 1. Child's Name / Nickname
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "1. What is your child's name or nickname?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = childName,
            onValueChange = {
              childName = it
              errorMessage = null
            },
            placeholder = { Text("e.g. Zayd, Maryam, Bilal...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("child_name_input"),
            shape = RoundedCornerShape(14.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. Age Group Selection Cards
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "2. Select Age Group & Learning Band",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(10.dp))

          AgeGroup.values().forEach { group ->
            val isSelected = selectedAgeGroup == group
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isSelected) MintContainer else IvoryBackground,
              border = androidx.compose.foundation.BorderStroke(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) EmeraldPrimary else CardBorder
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable {
                  selectedAgeGroup = group
                  childAge = when (group) {
                    AgeGroup.LITTLE_EXPLORERS -> 5
                    AgeGroup.YOUNG_LEARNERS -> 8
                    AgeGroup.YOUNG_SCHOLARS -> 11
                  }
                }
                .testTag("age_card_${group.name.lowercase()}")
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                  contentAlignment = Alignment.Center
                ) {
                  Text(group.emoji, fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = group.title,
                      fontSize = 14.sp,
                      fontWeight = FontWeight.Bold,
                      color = TextDark
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                      color = if (isSelected) EmeraldPrimary else Color(0xFFCBD5E1),
                      shape = RoundedCornerShape(6.dp)
                    ) {
                      Text(
                        text = group.ageRange,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                      )
                    }
                  }
                  Text(
                    text = group.subtitle,
                    fontSize = 11.sp,
                    color = TextMuted
                  )
                }

                RadioButton(
                  selected = isSelected,
                  onClick = {
                    selectedAgeGroup = group
                    childAge = when (group) {
                      AgeGroup.LITTLE_EXPLORERS -> 5
                      AgeGroup.YOUNG_LEARNERS -> 8
                      AgeGroup.YOUNG_SCHOLARS -> 11
                    }
                  },
                  colors = RadioButtonDefaults.colors(selectedColor = EmeraldPrimary)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3. Original Muslim Child Character Avatar Selector
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "3. Choose an Original Character Avatar",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "Selected: ${selectedAvatar.name} — ${selectedAvatar.characterTitle}",
            fontSize = 12.sp,
            color = EmeraldPrimary,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(10.dp))

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            items(avatars) { avatar ->
              val isChosen = selectedAvatar.id == avatar.id
              Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (isChosen) Color(avatar.bgHex) else IvoryBackground,
                border = androidx.compose.foundation.BorderStroke(
                  if (isChosen) 2.dp else 1.dp,
                  if (isChosen) EmeraldPrimary else CardBorder
                ),
                modifier = Modifier
                  .width(100.dp)
                  .clickable { selectedAvatar = avatar }
                  .testTag("avatar_choice_${avatar.name.lowercase()}")
              ) {
                Column(
                  modifier = Modifier.padding(10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Box(
                    modifier = Modifier
                      .size(46.dp)
                      .clip(CircleShape)
                      .background(Color.White),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(avatar.emoji, fontSize = 24.sp)
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(
                    text = avatar.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                  )
                  Text(
                    text = avatar.characterTitle,
                    fontSize = 9.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 4. Preferred Interface Language
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "4. Preferred Interface Language",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            languages.forEach { lang ->
              val isSelected = preferredLanguage == lang
              FilterChip(
                selected = isSelected,
                onClick = { preferredLanguage = lang },
                label = { Text(lang, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = EmeraldPrimary,
                  selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
              )
            }
          }
        }
      }

      if (errorMessage != null) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = errorMessage!!,
          color = CoralFun,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Save & Launch Button
      Button(
        onClick = {
          if (childName.isBlank()) {
            errorMessage = "Please enter your child's display name or nickname."
          } else {
            errorMessage = null
            viewModel.onChildProfileCreated(
              name = childName.trim(),
              age = childAge,
              ageGroup = selectedAgeGroup,
              avatar = selectedAvatar,
              preferredLanguage = preferredLanguage
            )
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("save_child_profile_button"),
        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
        shape = RoundedCornerShape(18.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp)
      ) {
        Text(
          text = "Save & Enter My World 🌟",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
      }
    }
  }
}
