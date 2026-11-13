package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.KidsRepository
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KidsViewModel : ViewModel() {

  // Screen-by-Screen Specification Flow State
  private val _appFlowScreen = MutableStateFlow(AppFlowScreen.WELCOME)
  val appFlowScreen: StateFlow<AppFlowScreen> = _appFlowScreen.asStateFlow()

  private val _isGuestMode = MutableStateFlow(false)
  val isGuestMode: StateFlow<Boolean> = _isGuestMode.asStateFlow()

  private val _currentLanguage = MutableStateFlow("English")
  val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

  private val _parentName = MutableStateFlow("Fatima")
  val parentName: StateFlow<String> = _parentName.asStateFlow()

  private val _parentEmail = MutableStateFlow("parent@mymuslimkids.com")
  val parentEmail: StateFlow<String> = _parentEmail.asStateFlow()

  private val _isParentLoggedIn = MutableStateFlow(false)
  val isParentLoggedIn: StateFlow<Boolean> = _isParentLoggedIn.asStateFlow()

  private val _profiles = MutableStateFlow(KidsRepository.defaultProfiles)
  val profiles: StateFlow<List<ChildProfile>> = _profiles.asStateFlow()

  private val _activeProfile = MutableStateFlow(KidsRepository.defaultProfiles[0])
  val activeProfile: StateFlow<ChildProfile> = _activeProfile.asStateFlow()

  private val _currentSection = MutableStateFlow(SectionTab.MY_WORLD)
  val currentSection: StateFlow<SectionTab> = _currentSection.asStateFlow()

  private val _ageGroupFilter = MutableStateFlow<AgeGroup?>(KidsRepository.defaultProfiles[0].ageGroup)
  val ageGroupFilter: StateFlow<AgeGroup?> = _ageGroupFilter.asStateFlow()

  // Story Reader State (Screen 6 & 7)
  private val _activeStory = MutableStateFlow<Story?>(null)
  val activeStory: StateFlow<Story?> = _activeStory.asStateFlow()

  private val _storyCurrentPage = MutableStateFlow(0)
  val storyCurrentPage: StateFlow<Int> = _storyCurrentPage.asStateFlow()

  private val _isStoryAudioPlaying = MutableStateFlow(false)
  val isStoryAudioPlaying: StateFlow<Boolean> = _isStoryAudioPlaying.asStateFlow()

  val storySearchQuery = MutableStateFlow("")
  val storyCategoryFilter = MutableStateFlow("All")
  val storyFormatFilter = MutableStateFlow("All") // "All", "Read & Listen", "Audio Only", "Illustrated"
  val storyFontSize = MutableStateFlow(16) // 14..22 sp
  val isStoryAmbiencePlaying = MutableStateFlow(false)
  val storySubtitleLang = MutableStateFlow("English")
  private val _favoriteStoryIds = MutableStateFlow(setOf("story_yunus", "story_bedtime_dhikr"))
  val favoriteStoryIds: StateFlow<Set<String>> = _favoriteStoryIds.asStateFlow()

  // Video Player State (Screen 8 & 9)
  private val _activeVideo = MutableStateFlow<VideoItem?>(null)
  val activeVideo: StateFlow<VideoItem?> = _activeVideo.asStateFlow()

  private val _isVideoPlaying = MutableStateFlow(false)
  val isVideoPlaying: StateFlow<Boolean> = _isVideoPlaying.asStateFlow()

  private val _videoProgressSec = MutableStateFlow(0)
  val videoProgressSec: StateFlow<Int> = _videoProgressSec.asStateFlow()

  val videoSearchQuery = MutableStateFlow("")
  val videoCategoryFilter = MutableStateFlow("All")
  val videoQuality = MutableStateFlow("Auto (Best)")
  val videoSubtitle = MutableStateFlow("Off")
  val videoAudioLang = MutableStateFlow("English")
  val isVideoFullscreen = MutableStateFlow(false)
  val isVideoMuted = MutableStateFlow(false)
  private val _favoriteVideoIds = MutableStateFlow(setOf("tv_helper_detectives_1"))
  val favoriteVideoIds: StateFlow<Set<String>> = _favoriteVideoIds.asStateFlow()

  // Song Player State (Screen 10)
  private val _activeSong = MutableStateFlow<SongItem?>(null)
  val activeSong: StateFlow<SongItem?> = _activeSong.asStateFlow()

  private val _isSongPlaying = MutableStateFlow(false)
  val isSongPlaying: StateFlow<Boolean> = _isSongPlaying.asStateFlow()

  private val _songProgress = MutableStateFlow(0f)
  val songProgress: StateFlow<Float> = _songProgress.asStateFlow()

  val songCategoryFilter = MutableStateFlow("All")
  val isSongRepeat = MutableStateFlow(false)
  val isSongShuffle = MutableStateFlow(false)
  val isSongAudioOnly = MutableStateFlow(true)
  private val _favoriteSongIds = MutableStateFlow(setOf("song_5_pillars", "song_bismillah"))
  val favoriteSongIds: StateFlow<Set<String>> = _favoriteSongIds.asStateFlow()

  // Lesson & Classroom State
  private val _activeLesson = MutableStateFlow<LessonItem?>(null)
  val activeLesson: StateFlow<LessonItem?> = _activeLesson.asStateFlow()

  // Surah & Quran State
  private val _activeSurah = MutableStateFlow<SurahItem?>(null)
  val activeSurah: StateFlow<SurahItem?> = _activeSurah.asStateFlow()

  private val _selectedLetter = MutableStateFlow<ArabicLetter?>(null)
  val selectedLetter: StateFlow<ArabicLetter?> = _selectedLetter.asStateFlow()

  private val _playingAyahIndex = MutableStateFlow(0)
  val playingAyahIndex: StateFlow<Int> = _playingAyahIndex.asStateFlow()

  private val _isAyahPlaying = MutableStateFlow(false)
  val isAyahPlaying: StateFlow<Boolean> = _isAyahPlaying.asStateFlow()

  // Duas & Sunnah
  private val _duas = MutableStateFlow(KidsRepository.dailyDuas)
  val duas: StateFlow<List<DuaItem>> = _duas.asStateFlow()

  private val _sunnahHabits = MutableStateFlow(KidsRepository.sunnahHabits)
  val sunnahHabits: StateFlow<List<SunnahHabitItem>> = _sunnahHabits.asStateFlow()

  // Rewards & Badges (Screen 16)
  private val _badges = MutableStateFlow(KidsRepository.badges)
  val badges: StateFlow<List<BadgeItem>> = _badges.asStateFlow()

  private val _weeklyGoals = MutableStateFlow(KidsRepository.defaultWeeklyGoals)
  val weeklyGoals: StateFlow<List<WeeklyLearningGoal>> = _weeklyGoals.asStateFlow()

  private val _learningJourney = MutableStateFlow(KidsRepository.learningJourney)
  val learningJourney: StateFlow<List<LearningJourneyEntry>> = _learningJourney.asStateFlow()

  private val _starCelebration = MutableStateFlow<String?>(null)
  val starCelebration: StateFlow<String?> = _starCelebration.asStateFlow()

  // Parent Settings, Controls & Billing (Screens 17–19)
  private val _parentSettings = MutableStateFlow(ParentSettings())
  val parentSettings: StateFlow<ParentSettings> = _parentSettings.asStateFlow()

  private val _isParentGatePassed = MutableStateFlow(false)
  val isParentGatePassed: StateFlow<Boolean> = _isParentGatePassed.asStateFlow()

  private val _signedInDevices = MutableStateFlow(KidsRepository.signedInDevices)
  val signedInDevices: StateFlow<List<SignedInDevice>> = _signedInDevices.asStateFlow()

  private val _paymentTransactions = MutableStateFlow(KidsRepository.paymentTransactions)
  val paymentTransactions: StateFlow<List<PaymentTransaction>> = _paymentTransactions.asStateFlow()

  // Admin Studio State (Screens 20–23)
  private val _isAdminStaffVerified = MutableStateFlow(false)
  val isAdminStaffVerified: StateFlow<Boolean> = _isAdminStaffVerified.asStateFlow()

  val adminNavSection = MutableStateFlow(AdminNavSection.DASHBOARD)

  private val _reviewItems = MutableStateFlow(KidsRepository.contentReviewItems)
  val reviewItems: StateFlow<List<ContentReviewItem>> = _reviewItems.asStateFlow()

  private val _auditLogs = MutableStateFlow(KidsRepository.defaultAuditLogs)
  val auditLogs: StateFlow<List<AuditLogEntry>> = _auditLogs.asStateFlow()

  // Active Game inside Fun Zone (Screens 11 & 12)
  private val _activeGame = MutableStateFlow<GameType?>(null)
  val activeGame: StateFlow<GameType?> = _activeGame.asStateFlow()

  private val _activeGameCard = MutableStateFlow<GameCardItem?>(null)
  val activeGameCard: StateFlow<GameCardItem?> = _activeGameCard.asStateFlow()

  val selectedGameCategory = MutableStateFlow("All")

  private val _activeAdventure = MutableStateFlow<LearningAdventure?>(null)
  val activeAdventure: StateFlow<LearningAdventure?> = _activeAdventure.asStateFlow()

  // Classroom State (Screen 13)
  val lessonStepIndex = MutableStateFlow(0) // 0: Intro, 1: Explanation, 2: Example, 3: Practice, 4: Review
  val isLessonAudioPlaying = MutableStateFlow(false)
  val selectedLessonSubjectFilter = MutableStateFlow("All")
  val selectedPracticeOption = MutableStateFlow<Int?>(null)
  val isPracticeAnswerChecked = MutableStateFlow(false)

  // Quran & Arabic State (Screen 14)
  val quranTab = MutableStateFlow(0) // 0: Alphabet, 1: Vocabulary, 2: Short Surahs, 3: Pronunciation (Makharij)
  val isRepeatAfterReciter = MutableStateFlow(false)
  val showTransliteration = MutableStateFlow(true)
  val showTranslation = MutableStateFlow(true)
  val selectedMakhrajFilter = MutableStateFlow("All")
  val activeVocabCategory = MutableStateFlow("All")

  // Duas & Sunnah State (Screen 15)
  val duasTab = MutableStateFlow(0)  // 0: Daily Duas, 1: Sunnah Habits, 2: Tasbih Adhkar, 3: My Daily Routines
  private val _activeDuaForPlayer = MutableStateFlow<DuaItem?>(null)
  val activeDuaForPlayer: StateFlow<DuaItem?> = _activeDuaForPlayer.asStateFlow()

  val isDuaAudioPlaying = MutableStateFlow(false)
  val duaAudioSpeed = MutableStateFlow(1.0f) // 0.8f, 1.0f, 1.2f
  private val _favoriteDuaIds = MutableStateFlow(setOf("dua_wake_up", "dua_before_eating", "dua_sleeping"))
  val favoriteDuaIds: StateFlow<Set<String>> = _favoriteDuaIds.asStateFlow()

  val tasbihCount = MutableStateFlow(0)
  val activeTasbihDhikr = MutableStateFlow(KidsRepository.tasbihDhikrs[0])

  // Sub-tab toggles
  val parentTab = MutableStateFlow(0) // 0: Profiles, 1: Controls & Security, 2: Subscription & Billing, 3: Admin Studio

  init {
    // Media playback ticker simulation
    viewModelScope.launch {
      while (true) {
        delay(1000)
        if (_isStoryAudioPlaying.value) {
          // auto progress or soundwave animation
        }
        if (_isVideoPlaying.value) {
          val current = _videoProgressSec.value
          val maxSec = _activeVideo.value?.durationSeconds ?: 300
          if (current < maxSec) {
            _videoProgressSec.value = current + 1
          } else {
            _isVideoPlaying.value = false
          }
        }
        if (_isSongPlaying.value) {
          val next = _songProgress.value + 0.02f
          if (next >= 1f) {
            _songProgress.value = 0f
            _isSongPlaying.value = false
          } else {
            _songProgress.value = next
          }
        }
      }
    }
  }

  fun navigateToFlow(flow: AppFlowScreen) {
    _appFlowScreen.value = flow
  }

  fun setLanguage(language: String) {
    _currentLanguage.value = language
  }

  fun exploreAsGuest() {
    _isGuestMode.value = true
    _currentSection.value = SectionTab.MY_WORLD
    _appFlowScreen.value = AppFlowScreen.CHILD_MAIN_APP
    triggerCelebration("Welcome Explorer! Enjoy exploring My Muslim Kids! ⭐")
  }

  fun onParentRegistered(name: String, email: String) {
    _parentName.value = name
    _parentEmail.value = email
    _isParentLoggedIn.value = true
    _appFlowScreen.value = AppFlowScreen.CREATE_CHILD_PROFILE
  }

  fun onParentLoggedIn(email: String) {
    _parentEmail.value = email
    _isParentLoggedIn.value = true
    _isGuestMode.value = false
    _appFlowScreen.value = AppFlowScreen.CHILD_MAIN_APP
  }

  fun onChildProfileCreated(
    name: String,
    age: Int,
    ageGroup: AgeGroup,
    avatar: CharacterAvatar,
    preferredLanguage: String
  ) {
    val newProfile = ChildProfile(
      id = "child_${System.currentTimeMillis()}",
      name = name,
      age = age,
      ageGroup = ageGroup,
      avatarEmoji = avatar.emoji,
      avatarBgHex = avatar.bgHex,
      stars = 25,
      streakDays = 1,
      dailyMinutesSpent = 0,
      preferredLanguage = preferredLanguage
    )
    val list = _profiles.value + newProfile
    _profiles.value = list
    _activeProfile.value = newProfile
    _ageGroupFilter.value = ageGroup
    _isGuestMode.value = false
    _appFlowScreen.value = AppFlowScreen.CHILD_MAIN_APP
    _currentSection.value = SectionTab.MY_WORLD
    triggerCelebration("Assalamu Alaikum $name! Welcome to your learning world! ⭐ +25 Stars!")
  }

  fun selectSection(section: SectionTab) {
    if (section == SectionTab.PARENT_CENTRE && !_isParentGatePassed.value) {
      // Trigger parent gate dialog in UI
    }
    _currentSection.value = section
  }

  fun setParentGatePassed(passed: Boolean) {
    _isParentGatePassed.value = passed
  }

  fun switchProfile(profile: ChildProfile) {
    _activeProfile.value = profile
    _ageGroupFilter.value = profile.ageGroup
  }

  fun addProfile(name: String, age: Int, ageGroup: AgeGroup, emoji: String) {
    val newProfile = ChildProfile(
      id = "child_${System.currentTimeMillis()}",
      name = name,
      age = age,
      ageGroup = ageGroup,
      avatarEmoji = emoji,
      avatarBgHex = 0xFFD4F3E2,
      stars = 20,
      streakDays = 1,
      dailyMinutesSpent = 0
    )
    val list = _profiles.value + newProfile
    _profiles.value = list
    _activeProfile.value = newProfile
    _ageGroupFilter.value = ageGroup
    triggerCelebration("Welcome to My Muslim Kids, $name! ⭐ +20 Stars!")
  }

  fun setAgeFilter(ageGroup: AgeGroup?) {
    _ageGroupFilter.value = ageGroup
  }

  fun earnStars(amount: Int, reason: String) {
    val current = _activeProfile.value
    val updated = current.copy(stars = current.stars + amount)
    _activeProfile.value = updated
    _profiles.value = _profiles.value.map { if (it.id == updated.id) updated else it }
    triggerCelebration("MashaAllah! +$amount Stars! ⭐ $reason")
  }

  fun triggerCelebration(msg: String) {
    viewModelScope.launch {
      _starCelebration.value = msg
      delay(3200)
      _starCelebration.value = null
    }
  }

  // Stories
  fun openStory(story: Story) {
    _activeStory.value = story
    _storyCurrentPage.value = 0
    _isStoryAudioPlaying.value = false
    isStoryAmbiencePlaying.value = false
  }

  fun closeStory() {
    _activeStory.value = null
    _isStoryAudioPlaying.value = false
    isStoryAmbiencePlaying.value = false
  }

  fun setStoryPage(page: Int) {
    _storyCurrentPage.value = page
  }

  fun toggleStoryAudio() {
    _isStoryAudioPlaying.value = !_isStoryAudioPlaying.value
  }

  fun toggleStoryFavorite(storyId: String) {
    val current = _favoriteStoryIds.value
    if (current.contains(storyId)) {
      _favoriteStoryIds.value = current - storyId
    } else {
      _favoriteStoryIds.value = current + storyId
      triggerCelebration("Added to Story Favorites! ❤️")
    }
  }

  fun changeStoryFontSize(delta: Int) {
    val newSize = (storyFontSize.value + delta).coerceIn(14, 24)
    storyFontSize.value = newSize
  }

  fun toggleStoryAmbience() {
    isStoryAmbiencePlaying.value = !isStoryAmbiencePlaying.value
  }

  // Videos
  fun openVideo(video: VideoItem) {
    _activeVideo.value = video
    _videoProgressSec.value = 0
    _isVideoPlaying.value = true
  }

  fun toggleVideoPlay() {
    _isVideoPlaying.value = !_isVideoPlaying.value
  }

  fun seekVideo(seconds: Int) {
    _videoProgressSec.value = seconds
  }

  fun closeVideo() {
    _activeVideo.value = null
    _isVideoPlaying.value = false
    isVideoFullscreen.value = false
  }

  fun toggleVideoFavorite(videoId: String) {
    val current = _favoriteVideoIds.value
    if (current.contains(videoId)) {
      _favoriteVideoIds.value = current - videoId
    } else {
      _favoriteVideoIds.value = current + videoId
      triggerCelebration("Added to Saved Videos! ❤️")
    }
  }

  fun nextEpisode() {
    val list = KidsRepository.tvEpisodes
    val current = _activeVideo.value ?: return
    val currentIndex = list.indexOfFirst { it.id == current.id }
    val nextIndex = (currentIndex + 1) % list.size
    openVideo(list[nextIndex])
  }

  fun prevEpisode() {
    val list = KidsRepository.tvEpisodes
    val current = _activeVideo.value ?: return
    val currentIndex = list.indexOfFirst { it.id == current.id }
    val prevIndex = if (currentIndex <= 0) list.size - 1 else currentIndex - 1
    openVideo(list[prevIndex])
  }

  // Songs
  fun openSong(song: SongItem) {
    _activeSong.value = song
    _songProgress.value = 0f
    _isSongPlaying.value = true
  }

  fun toggleSongPlay() {
    _isSongPlaying.value = !_isSongPlaying.value
  }

  fun closeSong() {
    _activeSong.value = null
    _isSongPlaying.value = false
  }

  fun toggleSongFavorite(songId: String) {
    val current = _favoriteSongIds.value
    if (current.contains(songId)) {
      _favoriteSongIds.value = current - songId
    } else {
      _favoriteSongIds.value = current + songId
      triggerCelebration("Saved to Favorite Songs! 🎵")
    }
  }

  fun nextSong() {
    val list = KidsRepository.songs
    val current = _activeSong.value ?: return
    val currentIndex = list.indexOfFirst { it.id == current.id }
    val nextIndex = if (isSongShuffle.value) {
      val candidates = list.indices.filter { it != currentIndex }
      if (candidates.isNotEmpty()) candidates.random() else 0
    } else {
      (currentIndex + 1) % list.size
    }
    openSong(list[nextIndex])
  }

  fun prevSong() {
    val list = KidsRepository.songs
    val current = _activeSong.value ?: return
    val currentIndex = list.indexOfFirst { it.id == current.id }
    val prevIndex = if (currentIndex <= 0) list.size - 1 else currentIndex - 1
    openSong(list[prevIndex])
  }

  // Lessons (Screen 13)
  fun openLesson(lesson: LessonItem) {
    _activeLesson.value = lesson
    lessonStepIndex.value = 0
    selectedPracticeOption.value = null
    isPracticeAnswerChecked.value = false
    isLessonAudioPlaying.value = false
  }

  fun closeLesson() {
    _activeLesson.value = null
    isLessonAudioPlaying.value = false
  }

  fun nextLessonStep() {
    val current = lessonStepIndex.value
    if (current < 4) {
      lessonStepIndex.value = current + 1
    } else {
      completeLesson(_activeLesson.value?.id ?: "")
    }
  }

  fun prevLessonStep() {
    val current = lessonStepIndex.value
    if (current > 0) {
      lessonStepIndex.value = current - 1
    }
  }

  fun toggleLessonAudio() {
    isLessonAudioPlaying.value = !isLessonAudioPlaying.value
  }

  fun selectPracticeAnswer(index: Int) {
    selectedPracticeOption.value = index
    isPracticeAnswerChecked.value = true
    val correct = _activeLesson.value?.practiceCorrectIndex ?: 0
    if (index == correct) {
      earnStars(10, "Correct Answer! Masha'Allah! ⭐")
    }
  }

  fun completeLesson(lessonId: String) {
    earnStars(15, "Lesson Completed! Masha'Allah! ⭐")
    closeLesson()
  }

  // Quran & Alphabet (Screen 14)
  fun openSurah(surah: SurahItem) {
    _activeSurah.value = surah
    _playingAyahIndex.value = 0
    _isAyahPlaying.value = false
  }

  fun closeSurah() {
    _activeSurah.value = null
    _isAyahPlaying.value = false
  }

  fun toggleAyahAudio(index: Int) {
    if (_playingAyahIndex.value == index && _isAyahPlaying.value) {
      _isAyahPlaying.value = false
    } else {
      _playingAyahIndex.value = index
      _isAyahPlaying.value = true
    }
  }

  fun selectLetter(letter: ArabicLetter?) {
    _selectedLetter.value = letter
  }

  // Duas & Sunnah (Screen 15)
  fun openDuaPlayer(dua: DuaItem) {
    _activeDuaForPlayer.value = dua
    isDuaAudioPlaying.value = true
  }

  fun closeDuaPlayer() {
    _activeDuaForPlayer.value = null
    isDuaAudioPlaying.value = false
  }

  fun toggleDuaAudioPlay() {
    isDuaAudioPlaying.value = !isDuaAudioPlaying.value
  }

  fun setDuaAudioSpeed(speed: Float) {
    duaAudioSpeed.value = speed
  }

  fun toggleDuaFavorite(id: String) {
    val current = _favoriteDuaIds.value
    if (current.contains(id)) {
      _favoriteDuaIds.value = current - id
    } else {
      _favoriteDuaIds.value = current + id
      triggerCelebration("Saved to Favorite Duas! ❤️")
    }
  }

  fun incrementTasbih() {
    val target = activeTasbihDhikr.value.targetCount
    val next = tasbihCount.value + 1
    if (next >= target) {
      tasbihCount.value = 0
      earnStars(10, "${activeTasbihDhikr.value.transliteration} Complete! Masha'Allah! ⭐")
    } else {
      tasbihCount.value = next
    }
  }

  fun resetTasbih() {
    tasbihCount.value = 0
  }

  fun selectTasbihDhikr(dhikr: KidsRepository.TasbihDhikr) {
    activeTasbihDhikr.value = dhikr
    tasbihCount.value = 0
  }

  fun toggleDuaMemorized(id: String) {
    val list = _duas.value.map {
      if (it.id == id) {
        val newState = !it.isMemorized
        if (newState) earnStars(10, "Dua Memorized! ⭐")
        it.copy(isMemorized = newState)
      } else it
    }
    _duas.value = list
  }

  fun toggleSunnahHabit(id: String) {
    val list = _sunnahHabits.value.map {
      if (it.id == id) {
        val newState = !it.isDoneToday
        if (newState) earnStars(5, "Sunnah Practiced! ⭐")
        it.copy(isDoneToday = newState)
      } else it
    }
    _sunnahHabits.value = list
  }

  // Games & Adventures (Screens 11 & 12)
  fun openGame(type: GameType) {
    _activeGame.value = type
  }

  fun closeGame() {
    _activeGame.value = null
  }

  fun openGameCard(gameCard: GameCardItem) {
    _activeGameCard.value = gameCard
  }

  fun closeGameCard() {
    _activeGameCard.value = null
  }

  fun openAdventure(adv: LearningAdventure) {
    _activeAdventure.value = adv
  }

  fun closeAdventure() {
    _activeAdventure.value = null
  }

  // Parent controls, Profiles, Security & Billing (Screens 17–19)
  fun updateParentSettings(settings: ParentSettings) {
    _parentSettings.value = settings
  }

  fun editChildProfile(profileId: String, newName: String, newAge: Int, newAgeGroup: AgeGroup, newEmoji: String, newLanguage: String) {
    val updatedList = _profiles.value.map {
      if (it.id == profileId) {
        it.copy(
          name = newName,
          age = newAge,
          ageGroup = newAgeGroup,
          avatarEmoji = newEmoji,
          preferredLanguage = newLanguage
        )
      } else it
    }
    _profiles.value = updatedList
    if (_activeProfile.value.id == profileId) {
      _activeProfile.value = updatedList.first { it.id == profileId }
      _ageGroupFilter.value = newAgeGroup
    }
    triggerCelebration("Updated ${newName}'s profile! ✨")
  }

  fun removeChildProfile(profileId: String) {
    if (_profiles.value.size <= 1) {
      triggerCelebration("At least one child profile is required.")
      return
    }
    val updatedList = _profiles.value.filterNot { it.id == profileId }
    _profiles.value = updatedList
    if (_activeProfile.value.id == profileId) {
      _activeProfile.value = updatedList.first()
      _ageGroupFilter.value = updatedList.first().ageGroup
    }
    triggerCelebration("Child profile removed safely.")
  }

  fun toggleAllowedCategory(category: String) {
    val current = _parentSettings.value
    val nextSet = if (current.allowedCategories.contains(category)) {
      current.allowedCategories - category
    } else {
      current.allowedCategories + category
    }
    _parentSettings.value = current.copy(allowedCategories = nextSet)
  }

  fun updateWeeklyGoalTarget(goalId: String, newTarget: Int) {
    _weeklyGoals.value = _weeklyGoals.value.map {
      if (it.id == goalId) it.copy(targetCount = newTarget.coerceIn(1, 7)) else it
    }
  }

  fun signOutDevice(deviceId: String) {
    _signedInDevices.value = _signedInDevices.value.filterNot { it.id == deviceId && !it.isCurrentDevice }
    triggerCelebration("Device signed out securely.")
  }

  fun selectSubscriptionPlan(planName: String, isPremium: Boolean, priceDisplay: String) {
    val current = _parentSettings.value
    _parentSettings.value = current.copy(
      isPremiumActive = isPremium,
      activePlanName = planName,
      renewalDate = if (planName.contains("Annual")) "12 Nov 2027" else "12 Dec 2026"
    )
    if (isPremium) {
      val newTx = PaymentTransaction(
        id = "tx_${System.currentTimeMillis() % 10000}",
        date = "13 Nov 2026",
        planTitle = planName,
        amountDisplay = priceDisplay,
        status = "Paid",
        providerRef = "GP-${(1000..9999).random()}-2026"
      )
      _paymentTransactions.value = listOf(newTx) + _paymentTransactions.value
    }
    triggerCelebration("Switched to $planName! 🎉")
  }

  fun cancelSubscription() {
    val current = _parentSettings.value
    _parentSettings.value = current.copy(
      isPremiumActive = false,
      activePlanName = "Free Explorer"
    )
    triggerCelebration("Subscription cancelled. Access continues until end of period.")
  }

  fun restorePurchases() {
    val current = _parentSettings.value
    _parentSettings.value = current.copy(
      isPremiumActive = true,
      activePlanName = "Premium Monthly"
    )
    triggerCelebration("Purchases restored via Google Play Billing! ⭐")
  }

  fun toggleSubscription() {
    val current = _parentSettings.value
    val next = !current.isPremiumActive
    _parentSettings.value = current.copy(
      isPremiumActive = next,
      activePlanName = if (next) "Premium Monthly" else "Free Explorer"
    )
  }

  // Admin Studio Operations (Screens 20–23)
  fun verifyAdminStaff(staffId: String) {
    _isAdminStaffVerified.value = true
    addAuditLog(staffId.ifBlank { "Ustadh Ahmad" }, "Authorised Reviewer", "Signed into Private Admin Studio")
  }

  fun lockAdminStudio() {
    _isAdminStaffVerified.value = false
  }

  fun submitNewContentDraft(
    title: String,
    type: String,
    category: String,
    shortDescription: String,
    mainBody: String,
    ageGroup: AgeGroup,
    language: String,
    durationLabel: String,
    sourceRef: String,
    asDraft: Boolean
  ) {
    val status = if (asDraft) ReviewWorkflowStatus.DRAFT else ReviewWorkflowStatus.SUBMITTED
    val newItem = ContentReviewItem(
      id = "rev_${System.currentTimeMillis() % 100000}",
      title = title,
      type = type,
      category = category,
      shortDescription = shortDescription,
      mainContentBody = mainBody,
      submittedBy = "Studio Content Team",
      targetAge = ageGroup,
      language = language,
      durationLabel = durationLabel,
      sourceReference = sourceRef.ifBlank { "Pending Scholar Verification" },
      islamicAccuracyStatus = if (asDraft) "Draft Saved" else "Submitted for Stage 1",
      arabicChecked = false,
      respectfulIllustrationChecked = true,
      workflowStatus = status,
      status = status.label,
      version = 1,
      stages = listOf(
        ReviewStageCheck(1, "Production Review", !asDraft, if (asDraft) "Pending" else "Studio QA", "Draft uploaded."),
        ReviewStageCheck(2, "Islamic Review", false, "Pending Scholar", "Awaiting Islamic scholar review."),
        ReviewStageCheck(3, "Visual & Audio Review", false, "Pending Art Lead", "Awaiting media sharpness & clarity check."),
        ReviewStageCheck(4, "Final Approval", false, "Pending Admin", "Requires all 3 prior stages.")
      )
    )
    _reviewItems.value = listOf(newItem) + _reviewItems.value
    addAuditLog("Studio Editor", "Content Creator", "${if (asDraft) "Saved draft" else "Submitted for review"}: $title ($type)")
    triggerCelebration(if (asDraft) "Draft saved in Admin Studio!" else "Submitted to 4-Stage Review Queue!")
  }

  fun advanceReviewAction(
    itemId: String,
    newStatus: ReviewWorkflowStatus,
    comment: String,
    reviewerName: String = "Shaykh Yusuf Al-Amin"
  ) {
    _reviewItems.value = _reviewItems.value.map { item ->
      if (item.id == itemId) {
        val allPassed = newStatus == ReviewWorkflowStatus.APPROVED ||
          newStatus == ReviewWorkflowStatus.PUBLISHED ||
          newStatus == ReviewWorkflowStatus.SCHEDULED
        val updatedStages = item.stages.map { stage ->
          if (allPassed) stage.copy(passed = true, reviewerName = reviewerName)
          else if (newStatus == ReviewWorkflowStatus.CHANGES_REQUIRED && stage.stageNumber == 2) {
            stage.copy(passed = false, reviewerName = reviewerName, notes = comment.ifBlank { "Corrections requested" })
          } else stage
        }
        item.copy(
          workflowStatus = newStatus,
          status = newStatus.label,
          islamicAccuracyStatus = when (newStatus) {
            ReviewWorkflowStatus.PUBLISHED, ReviewWorkflowStatus.APPROVED, ReviewWorkflowStatus.SCHEDULED -> "Verified by Scholar"
            ReviewWorkflowStatus.CHANGES_REQUIRED -> "Changes Required"
            else -> "In Review"
          },
          arabicChecked = allPassed || item.arabicChecked,
          reviewerComment = comment.ifBlank { item.reviewerComment },
          approvedBy = if (allPassed) reviewerName else item.approvedBy,
          approvedTimestamp = if (allPassed) "13 Nov 2026 • Just now" else item.approvedTimestamp,
          version = if (newStatus == ReviewWorkflowStatus.CHANGES_REQUIRED) item.version + 1 else item.version,
          stages = updatedStages
        )
      } else item
    }
    addAuditLog(reviewerName, "Senior Islamic Reviewer", "Marked item as ${newStatus.label}: $comment")
  }

  private fun addAuditLog(staff: String, role: String, action: String) {
    val entry = AuditLogEntry(
      id = "log_${System.currentTimeMillis()}",
      timestamp = "Today • Just now",
      staffName = staff,
      role = role,
      action = action
    )
    _auditLogs.value = listOf(entry) + _auditLogs.value
  }
}
