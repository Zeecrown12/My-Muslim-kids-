package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppFlowScreen
import com.example.model.SectionTab
import com.example.ui.components.BottomKidNavBar
import com.example.ui.components.ParentalGateDialog
import com.example.ui.components.StarCelebrationOverlay
import com.example.ui.components.TopKidBar
import com.example.ui.screens.*
import com.example.ui.theme.IvoryBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.KidsViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val viewModel: KidsViewModel = viewModel()
        val appFlow by viewModel.appFlowScreen.collectAsState()
        val currentSection by viewModel.currentSection.collectAsState()
        val activeProfile by viewModel.activeProfile.collectAsState()
        val isGatePassed by viewModel.isParentGatePassed.collectAsState()
        val starCelebration by viewModel.starCelebration.collectAsState()

        var showParentGateDialog by remember { mutableStateOf(false) }

        when (appFlow) {
          // SCREEN 1: Welcome Screen
          AppFlowScreen.WELCOME -> {
            WelcomeScreen(viewModel = viewModel)
          }

          // SCREEN 2: Parent Registration & Login
          AppFlowScreen.PARENT_AUTH -> {
            BackHandler {
              viewModel.navigateToFlow(AppFlowScreen.WELCOME)
            }
            ParentAuthScreen(viewModel = viewModel)
          }

          // SCREEN 3: Create a Child Profile
          AppFlowScreen.CREATE_CHILD_PROFILE -> {
            BackHandler {
              viewModel.navigateToFlow(AppFlowScreen.PARENT_AUTH)
            }
            CreateChildProfileScreen(viewModel = viewModel)
          }

          // SCREENS 4 & 5: Child Home (My World) & Child Navigation
          AppFlowScreen.CHILD_MAIN_APP -> {
            val activeGameCard by viewModel.activeGameCard.collectAsState()
            val activeAdventure by viewModel.activeAdventure.collectAsState()
            val activeLesson by viewModel.activeLesson.collectAsState()
            val activeSurah by viewModel.activeSurah.collectAsState()
            val activeDuaForPlayer by viewModel.activeDuaForPlayer.collectAsState()
            val activeStory by viewModel.activeStory.collectAsState()
            val activeVideo by viewModel.activeVideo.collectAsState()
            val activeSong by viewModel.activeSong.collectAsState()

            // Smooth Android BackHandler handles nested modal/game dismissal before returning home
            BackHandler {
              when {
                activeGameCard != null -> viewModel.closeGameCard()
                activeAdventure != null -> viewModel.closeAdventure()
                activeLesson != null -> viewModel.closeLesson()
                activeSurah != null -> viewModel.closeSurah()
                activeDuaForPlayer != null -> viewModel.closeDuaPlayer()
                activeStory != null -> viewModel.closeStory()
                activeVideo != null -> viewModel.closeVideo()
                activeSong != null -> viewModel.closeSong()
                currentSection != SectionTab.MY_WORLD -> viewModel.selectSection(SectionTab.MY_WORLD)
                else -> viewModel.navigateToFlow(AppFlowScreen.WELCOME)
              }
            }

            Scaffold(
              modifier = Modifier
                .fillMaxSize()
                .background(IvoryBackground),
              containerColor = IvoryBackground,
              topBar = {
                TopKidBar(
                  activeProfile = activeProfile,
                  onProfileClick = {
                    if (isGatePassed) {
                      viewModel.parentTab.value = 0
                      viewModel.selectSection(SectionTab.PARENT_CENTRE)
                    } else {
                      showParentGateDialog = true
                    }
                  },
                  onParentGateClick = {
                    if (isGatePassed) {
                      viewModel.selectSection(SectionTab.PARENT_CENTRE)
                    } else {
                      showParentGateDialog = true
                    }
                  },
                  onAgeGroupToggle = {
                    // Quick age group switcher
                  }
                )
              },
              // SCREEN 5: Child Bottom Navigation Bar (5 primary tabs: Home, Stories, Watch, Games, Learn)
              bottomBar = {
                BottomKidNavBar(
                  currentSection = currentSection,
                  onSelectSection = { targetTab ->
                    if (targetTab == SectionTab.PARENT_CENTRE && !isGatePassed) {
                      showParentGateDialog = true
                    } else {
                      viewModel.selectSection(targetTab)
                    }
                  }
                )
              }
            ) { innerPadding ->
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(innerPadding)
              ) {
                // SCREEN 4: Child Home & Sub-screens
                when (currentSection) {
                  SectionTab.MY_WORLD -> HomeScreen(
                    viewModel = viewModel,
                    onNavigateSection = { tab ->
                      if (tab == SectionTab.PARENT_CENTRE && !isGatePassed) {
                        showParentGateDialog = true
                      } else {
                        viewModel.selectSection(tab)
                      }
                    }
                  )
                  SectionTab.STORY_WORLD -> StoryWorldScreen(viewModel = viewModel)
                  SectionTab.MMK_TV -> MmkTvScreen(viewModel = viewModel)
                  SectionTab.SONGS -> SongsNasheedsScreen(viewModel = viewModel)
                  SectionTab.FUN_ZONE -> FunZoneScreen(viewModel = viewModel)
                  SectionTab.CLASSROOM -> ClassroomScreen(viewModel = viewModel)
                  SectionTab.QURAN_ARABIC -> QuranArabicScreen(viewModel = viewModel)
                  SectionTab.DUAS_SUNNAH -> DuasSunnahScreen(viewModel = viewModel)
                  SectionTab.ACHIEVEMENTS -> AchievementsScreen(viewModel = viewModel)
                  SectionTab.PARENT_CENTRE -> ParentCentreScreen(viewModel = viewModel)
                }

                // Star Celebration Milestone Overlay
                StarCelebrationOverlay(message = starCelebration)

                // Protected Parental Gate Challenge Dialog
                if (showParentGateDialog) {
                  ParentalGateDialog(
                    onDismiss = { showParentGateDialog = false },
                    onVerified = {
                      viewModel.setParentGatePassed(true)
                      showParentGateDialog = false
                      viewModel.selectSection(SectionTab.PARENT_CENTRE)
                    }
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
