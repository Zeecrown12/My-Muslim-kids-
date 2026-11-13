package com.example.model

enum class AppFlowScreen {
  WELCOME,              // Screen 1: Welcome Screen
  PARENT_AUTH,          // Screen 2: Parent Registration & Login
  CREATE_CHILD_PROFILE, // Screen 3: Create a Child Profile
  CHILD_MAIN_APP        // Screens 4 & 5: Child Home (My World) & Child Navigation
}

data class CharacterAvatar(
  val id: String,
  val name: String,
  val characterTitle: String,
  val emoji: String,
  val bgHex: Long,
  val personalityTrait: String
)

enum class AgeGroup(
  val title: String,
  val ageRange: String,
  val subtitle: String,
  val emoji: String
) {
  LITTLE_EXPLORERS(
    title = "Little Explorers",
    ageRange = "Ages 4–6",
    subtitle = "Picture-based stories, songs & Arabic letters",
    emoji = "🌱"
  ),
  YOUNG_LEARNERS(
    title = "Young Learners",
    ageRange = "Ages 7–9",
    subtitle = "Stories, beliefs, Quran, Salah & quizzes",
    emoji = "⭐"
  ),
  YOUNG_SCHOLARS(
    title = "Young Scholars",
    ageRange = "Ages 10–12",
    subtitle = "Seerah, Islamic history & deeper lessons",
    emoji = "🚀"
  )
}

data class ChildProfile(
  val id: String,
  val name: String,
  val age: Int,
  val ageGroup: AgeGroup,
  val avatarEmoji: String,
  val avatarBgHex: Long = 0xFFD4F3E2,
  val stars: Int = 45,
  val streakDays: Int = 5,
  val dailyMinutesSpent: Int = 18,
  val preferredLanguage: String = "English"
)

enum class SectionTab(
  val label: String,
  val iconEmoji: String,
  val badgeText: String? = null
) {
  MY_WORLD("My World", "🌍"),
  STORY_WORLD("Stories", "📖"),
  MMK_TV("Kids TV", "📺"),
  SONGS("Songs", "🎵"),
  FUN_ZONE("Fun Zone", "🎮"),
  CLASSROOM("Classroom", "🏫"),
  QURAN_ARABIC("Quran & Arabic", "🌙"),
  DUAS_SUNNAH("Duas", "🤲"),
  ACHIEVEMENTS("Rewards", "🏆"),
  PARENT_CENTRE("Parents", "🛡️")
}

data class StoryPage(
  val pageNumber: Int,
  val text: String,
  val sceneEmoji: String,
  val audioDurationSec: Int = 12,
  val illustrationTitle: String
)

data class Story(
  val id: String,
  val title: String,
  val category: String, // Stories of the Prophets, Companions, Islamic History, Good Manners, Muslim Heroes, Bedtime Stories, Kindness & Honesty
  val targetAge: AgeGroup,
  val summary: String,
  val coverEmoji: String,
  val readTimeMinutes: Int,
  val moralLesson: String,
  val reflectionQuestion: String,
  val quizOptions: List<String>,
  val correctOptionIndex: Int,
  val pages: List<StoryPage>,
  val isPremium: Boolean = false,
  val isCompleted: Boolean = false,
  val hasAudio: Boolean = true,
  val format: String = "Read & Listen", // "Read & Listen", "Audio Only", "Illustrated"
  val isFavorite: Boolean = false
)

data class VideoChapter(
  val timeSec: Int,
  val title: String
)

data class VideoItem(
  val id: String,
  val title: String,
  val series: String,
  val category: String = "Original Cartoon Series", // Original Cartoon Series, Islamic Adventures, Good Manners, Islamic History, Quran & Arabic, Short Learning, Ramadan & Eid
  val targetAge: AgeGroup,
  val duration: String,
  val durationSeconds: Int,
  val thumbnailEmoji: String,
  val description: String,
  val keyTakeaway: String,
  val chapters: List<VideoChapter>,
  val quizQuestion: String,
  val quizOptions: List<String>,
  val quizAnswerIndex: Int,
  val isPremium: Boolean = false,
  val isCompleted: Boolean = false,
  val isFavorite: Boolean = false,
  val availableQualities: List<String> = listOf("Auto (Best)", "1080p HD", "720p", "480p"),
  val availableSubtitles: List<String> = listOf("English", "العربية", "Français", "Off"),
  val availableAudioTracks: List<String> = listOf("English", "العربية")
)

data class SongItem(
  val id: String,
  val title: String,
  val artist: String,
  val duration: String,
  val category: String = "Islamic Nursery Songs", // Islamic Nursery Songs, Arabic Alphabet, 99 Names, Daily Duas, Good Manners, Salah, Ramadan & Eid, Islamic Knowledge
  val theme: String,
  val coverEmoji: String,
  val lyrics: List<String>,
  val educationalMessage: String,
  val targetAge: AgeGroup = AgeGroup.LITTLE_EXPLORERS,
  val hasVideoVersion: Boolean = false,
  val isFavorite: Boolean = false
)

enum class GameType {
  ARABIC_LETTER_MATCH,
  WUDU_ORDER_PUZZLE,
  PILLARS_OF_ISLAM,
  ISLAMIC_TRIVIA,
  GOOD_DEED_MATCH,
  MEMORY_CARDS,
  KNOWLEDGE_PUZZLE,
  TREASURE_HUNT
}

enum class GameplayType {
  MULTIPLE_CHOICE,
  MATCHING_PAIRS,
  STEP_ORDERING,
  MEMORY_MATCH,
  PICTURE_PUZZLE
}

data class GameCardItem(
  val id: String,
  val title: String,
  val category: String, // Islamic Knowledge Quizzes, Arabic Alphabet Games, Islamic Matching Games, Wudu & Salah Activities, Memory Games, Islamic Puzzles, Good-Deeds Challenges, Islamic Treasure Hunts
  val gameplayType: GameplayType,
  val targetAge: AgeGroup,
  val difficulty: String, // "Easy", "Medium", "Explorer"
  val description: String,
  val illustrationEmoji: String,
  val starReward: Int = 10,
  val isCompleted: Boolean = false
)

data class AdventureActivity(
  val id: String,
  val title: String,
  val type: String,
  val isCompleted: Boolean = false
)

data class LearningAdventure(
  val id: String,
  val title: String,
  val theme: String,
  val iconEmoji: String,
  val description: String,
  val activities: List<AdventureActivity>,
  val rewardBadgeName: String,
  val starReward: Int = 25,
  val isCompleted: Boolean = false
)

data class LessonCheckpoint(
  val title: String,
  val description: String,
  val arabicText: String? = null,
  val transliteration: String? = null,
  val icon: String
)

data class LessonItem(
  val id: String,
  val title: String,
  val subject: String, // Knowing Allah, Five Pillars, Six Articles, Prophets & Messengers, Seerah, Islamic History, Salah, Wudu, Manners & Character, Basic Islamic Knowledge
  val module: String,
  val targetAge: AgeGroup,
  val summary: String,
  val iconEmoji: String,
  val funFact: String,
  val estimatedMinutes: Int = 8,
  val learningObjective: String = "Understand key Islamic principles with love and clarity.",
  val introduction: String = "",
  val explanation: String = "",
  val everydayExample: String = "",
  val practiceQuestion: String = "",
  val practiceOptions: List<String> = emptyList(),
  val practiceCorrectIndex: Int = 0,
  val practiceExplanation: String = "",
  val reviewKeyPoints: List<String> = emptyList(),
  val checkpoints: List<LessonCheckpoint> = emptyList(),
  val isCompleted: Boolean = false
)

data class ArabicLetter(
  val arabicChar: String,
  val name: String,
  val transliteration: String,
  val exampleWordArabic: String,
  val exampleWordEnglish: String,
  val exampleWordMeaning: String,
  val exampleEmoji: String,
  val phoneticGuide: String,
  val isolated: String = "",
  val initialForm: String = "",
  val medialForm: String = "",
  val finalForm: String = "",
  val makhrajCategory: String = "Tongue (لسانية)"
)

data class ArabicWordItem(
  val id: String,
  val arabic: String,
  val transliteration: String,
  val englishMeaning: String,
  val category: String, // Everyday Expressions, Animals & Nature, Classroom & Home, Food & Kitchen
  val emoji: String,
  val audioLabel: String = "Listen"
)

data class AyahItem(
  val verseNumber: Int,
  val arabic: String,
  val transliteration: String,
  val englishTranslation: String
)

data class SurahItem(
  val number: Int,
  val nameArabic: String,
  val nameEnglish: String,
  val meaningEnglish: String,
  val totalVerses: Int,
  val revelationType: String, // Makki / Madani
  val revelationStoryForKids: String,
  val verses: List<AyahItem>
)

data class DuaItem(
  val id: String,
  val title: String,
  val category: String, // Daily Duas, Morning & Evening, Home & Travel, Manners & Character, Sunnah Habits
  val situation: String,
  val arabicText: String,
  val transliteration: String,
  val englishTranslation: String,
  val sunnahEtiquette: String,
  val emoji: String,
  val sourceRef: String = "Authentic Sunnah",
  val audioDurationSec: Int = 8,
  val isMemorized: Boolean = false,
  val isFavorite: Boolean = false
)

data class SunnahHabitItem(
  val id: String,
  val title: String,
  val description: String,
  val iconEmoji: String,
  val hadithRef: String,
  val isDoneToday: Boolean = false
)

data class BadgeItem(
  val id: String,
  val name: String,
  val description: String,
  val iconEmoji: String,
  val isUnlocked: Boolean,
  val progress: Float = 1.0f,
  val category: String
)

data class LearningJourneyEntry(
  val id: String,
  val title: String,
  val sectionType: String, // Story, Lesson, Game, Quran, Dua, Song
  val iconEmoji: String,
  val completedLabel: String,
  val starsEarned: Int
)

data class WeeklyLearningGoal(
  val id: String,
  val areaName: String,
  val iconEmoji: String,
  val completedCount: Int,
  val targetCount: Int,
  val accentHex: Long
)

data class SignedInDevice(
  val id: String,
  val deviceName: String,
  val platform: String,
  val lastActive: String,
  val isCurrentDevice: Boolean = false
)

data class ParentSettings(
  val dailyLimitMinutes: Int = 45,
  val breakReminderMinutes: Int = 20,
  val scheduleEnabled: Boolean = false,
  val scheduleWindow: String = "3:30 PM – 7:00 PM",
  val bedtimeLockEnabled: Boolean = false,
  val bedtimeHour: Int = 20, // 8:00 PM
  val isPremiumActive: Boolean = false,
  val activePlanName: String = "Free Explorer", // "Free Explorer", "Premium Monthly", "Premium Annual"
  val renewalDate: String = "12 Dec 2026",
  val paymentMethodSummary: String = "Google Play / Visa •••• 4242",
  val language: String = "English",
  val mathPinSolved: Boolean = false,
  val parentPinVerificationEnabled: Boolean = true,
  val videoAutoplayEnabled: Boolean = false,
  val audioOnlyModeDefault: Boolean = false,
  val allowedCategories: Set<String> = setOf(
    "Stories", "Kids TV", "Songs & Nasheeds", "Fun Zone", "Islamic Classroom", "Quran & Arabic", "Duas & Sunnah"
  ),
  val strictPrivacyNoTracking: Boolean = true
)

data class PaymentTransaction(
  val id: String,
  val date: String,
  val planTitle: String,
  val amountDisplay: String,
  val status: String, // "Paid", "Refunded", "Pending"
  val providerRef: String
)

enum class AdminNavSection(val title: String, val emoji: String) {
  DASHBOARD("Dashboard", "📊"),
  STORIES("Stories", "📖"),
  VIDEOS("Videos & Cartoons", "📺"),
  SONGS("Songs & Nasheeds", "🎵"),
  GAMES("Games & Activities", "🎮"),
  LESSONS("Lessons & Courses", "🏫"),
  QURAN_ARABIC("Quran & Arabic", "🌙"),
  DUAS_SUNNAH("Duas & Sunnah", "🤲"),
  LANGUAGES("Languages & Translations", "🌐"),
  CONTENT_REVIEW("Content Review", "✅"),
  PARENTS_PROFILES("Parents & Child Profiles", "👨‍👩‍👧‍👦"),
  SUBSCRIPTIONS("Subscriptions", "💳"),
  REPORTS_ANALYTICS("Reports & Analytics", "📈"),
  STAFF_PERMISSIONS("Staff & Permissions", "🔑"),
  PLATFORM_SETTINGS("Platform Settings", "⚙️")
}

enum class ReviewWorkflowStatus(val label: String) {
  DRAFT("Draft"),
  SUBMITTED("Submitted"),
  IN_REVIEW("In Review"),
  CHANGES_REQUIRED("Changes Required"),
  APPROVED("Approved"),
  SCHEDULED("Scheduled"),
  PUBLISHED("Published"),
  ARCHIVED("Archived")
}

data class ReviewStageCheck(
  val stageNumber: Int,
  val stageName: String, // Production Review, Islamic Review, Visual & Audio Review, Final Approval
  val passed: Boolean,
  val reviewerName: String,
  val notes: String
)

data class ContentReviewItem(
  val id: String,
  val title: String,
  val type: String, // Story, Cartoon, Educational Video, Song, Game, Lesson, Quran & Arabic, Duas & Sunnah
  val category: String = "General",
  val shortDescription: String = "",
  val mainContentBody: String = "",
  val submittedBy: String,
  val targetAge: AgeGroup,
  val language: String = "English",
  val durationLabel: String = "4 min",
  val sourceReference: String = "Verified Islamic Curriculum",
  val islamicAccuracyStatus: String,
  val arabicChecked: Boolean,
  val respectfulIllustrationChecked: Boolean,
  val workflowStatus: ReviewWorkflowStatus = ReviewWorkflowStatus.PUBLISHED,
  val status: String,
  val version: Int = 1,
  val scheduledDate: String? = null,
  val reviewerComment: String = "",
  val approvedBy: String? = null,
  val approvedTimestamp: String? = null,
  val stages: List<ReviewStageCheck> = emptyList()
)

data class AuditLogEntry(
  val id: String,
  val timestamp: String,
  val staffName: String,
  val role: String,
  val action: String
)

