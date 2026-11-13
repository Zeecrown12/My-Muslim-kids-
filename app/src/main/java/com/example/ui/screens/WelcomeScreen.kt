package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.AppFlowScreen
import com.example.ui.components.BrandLogoMark
import com.example.ui.theme.*
import com.example.viewmodel.KidsViewModel

@Composable
fun WelcomeScreen(
  viewModel: KidsViewModel,
  modifier: Modifier = Modifier
) {
  val currentLanguage by viewModel.currentLanguage.collectAsState()
  var showLanguageDialog by remember { mutableStateOf(false) }

  val languages = listOf("English", "العربية (Arabic)", "Français (French)", "اردو (Urdu)", "Bahasa Indonesia")

  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          listOf(
            Color(0xFFFBFBF6),
            Color(0xFFF1F8F4),
            Color(0xFFE8F5EE)
          )
        )
      )
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("welcome_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 20.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Top Language Selector & Mini Branding
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Original Book + Geometric Star Emblem
        Row(verticalAlignment = Alignment.CenterVertically) {
          BrandLogoMark(size = 40.dp, monochrome = false)
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "My Muslim Kids",
            fontFamily = NunitoFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = EmeraldDark
          )
        }

        // Language Pill
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White,
          shadowElevation = 2.dp,
          modifier = Modifier
            .clickable { showLanguageDialog = true }
            .testTag("language_selector_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("🌐", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = currentLanguage.split(" ").first(),
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Icon(
              imageVector = Icons.Default.ArrowDropDown,
              contentDescription = "Select Language",
              tint = TextMuted,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Hero Illustration (Children reading illuminated storybook)
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .height(230.dp)
          .testTag("welcome_hero_card"),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
      ) {
        Box(modifier = Modifier.fillMaxSize()) {
          Image(
            painter = painterResource(id = R.drawable.welcome_hero_1794476017929),
            contentDescription = "Muslim children reading storybook",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )

          // Soft bottom vignette
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(60.dp)
              .align(Alignment.BottomCenter)
              .background(
                Brush.verticalGradient(
                  listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f))
                )
              )
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Tagline & Welcome Text
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
          color = AmberContainer,
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier.padding(bottom = 10.dp)
        ) {
          Text(
            text = "Learn • Play • Watch • Explore • Grow in Faith",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = OnAmberContainer,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          )
        }

        Text(
          text = "A Joyful & Safe Islamic World for Kids",
          fontFamily = NunitoFontFamily,
          fontSize = 22.sp,
          fontWeight = FontWeight.ExtraBold,
          color = TextDark,
          textAlign = TextAlign.Center,
          lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "A wonderful world of Islamic learning and fun! Original cartoons, animated stories, songs, Quran, and character-building adventures.",
          fontSize = 13.sp,
          color = TextMuted,
          textAlign = TextAlign.Center,
          lineHeight = 19.sp,
          modifier = Modifier.padding(horizontal = 8.dp)
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Buttons
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Primary Button: Parent / Guardian
        Button(
          onClick = { viewModel.navigateToFlow(AppFlowScreen.PARENT_AUTH) },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("parent_guardian_button"),
          shape = RoundedCornerShape(18.dp),
          colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
          elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Parent / Guardian",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }

        // Secondary Option: Explore the App (Restricted Preview)
        OutlinedButton(
          onClick = { viewModel.exploreAsGuest() },
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("explore_app_button"),
          shape = RoundedCornerShape(18.dp),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White.copy(alpha = 0.8f),
            contentColor = EmeraldPrimary
          ),
          border = androidx.compose.foundation.BorderStroke(2.dp, EmeraldPrimary)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = null,
              tint = EmeraldPrimary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Explore the App (Preview)",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Trust badge
      Text(
        text = "🛡️ Safe, 100% Halal, Ad-Free & Parent-Controlled",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = EmeraldDark
      )
    }

    // Language Selector Dialog
    if (showLanguageDialog) {
      Dialog(onDismissRequest = { showLanguageDialog = false }) {
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Choose Preferred Language",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Spacer(modifier = Modifier.height(12.dp))

            languages.forEach { lang ->
              val isSelected = currentLanguage == lang || currentLanguage.startsWith(lang.take(4))
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) MintContainer else IvoryBackground,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 6.dp)
                  .clickable {
                    viewModel.setLanguage(lang)
                    showLanguageDialog = false
                  }
              ) {
                Row(
                  modifier = Modifier.padding(12.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = lang,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) OnMintContainer else TextDark
                  )
                  if (isSelected) {
                    Icon(
                      imageVector = Icons.Default.Check,
                      contentDescription = "Selected",
                      tint = EmeraldPrimary,
                      modifier = Modifier.size(18.dp)
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
}
