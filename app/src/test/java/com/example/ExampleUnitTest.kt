package com.example

import com.example.data.KidsRepository
import com.example.model.AgeGroup
import com.example.model.SectionTab
import com.example.viewmodel.KidsViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testKidsRepository_hasRequiredData() {
    assertTrue("Should have default child profiles", KidsRepository.defaultProfiles.isNotEmpty())
    assertEquals(28, KidsRepository.arabicAlphabet.size)
    assertTrue("Should have short surahs", KidsRepository.shortSurahs.isNotEmpty())
    assertTrue("Should have daily duas", KidsRepository.dailyDuas.isNotEmpty())
    assertTrue("Should have stories", KidsRepository.stories.isNotEmpty())
    assertTrue("Should have TV episodes", KidsRepository.tvEpisodes.isNotEmpty())
    assertTrue("Should have nasheeds", KidsRepository.songs.isNotEmpty())
    assertTrue("Should have lessons", KidsRepository.lessons.isNotEmpty())
    assertTrue("Should have badges", KidsRepository.badges.isNotEmpty())
    assertTrue("Should have trivia questions", KidsRepository.triviaQuestions.isNotEmpty())
  }

  @Test
  fun testKidsViewModel_initialState() {
    val viewModel = KidsViewModel()
    assertEquals(SectionTab.MY_WORLD, viewModel.currentSection.value)
    assertEquals("Zayd", viewModel.activeProfile.value.name)
    assertEquals(AgeGroup.LITTLE_EXPLORERS, viewModel.ageGroupFilter.value)
  }

  @Test
  fun testKidsViewModel_profileSwitchingAndStars() {
    val viewModel = KidsViewModel()
    val initialStars = viewModel.activeProfile.value.stars

    viewModel.earnStars(10, "Test Reward")
    assertEquals(initialStars + 10, viewModel.activeProfile.value.stars)

    val maryam = KidsRepository.defaultProfiles[1]
    viewModel.switchProfile(maryam)
    assertEquals("Maryam", viewModel.activeProfile.value.name)
    assertEquals(AgeGroup.YOUNG_LEARNERS, viewModel.ageGroupFilter.value)
  }

  @Test
  fun testKidsViewModel_duaMemorization() {
    val viewModel = KidsViewModel()
    val firstDua = viewModel.duas.value.first()
    val initialState = firstDua.isMemorized

    viewModel.toggleDuaMemorized(firstDua.id)
    val updatedDua = viewModel.duas.value.first { it.id == firstDua.id }
    assertEquals(!initialState, updatedDua.isMemorized)
  }

  @Test
  fun testKidsViewModel_flowTransitionsAndGuestMode() {
    val viewModel = KidsViewModel()
    assertEquals(com.example.model.AppFlowScreen.WELCOME, viewModel.appFlowScreen.value)

    viewModel.exploreAsGuest()
    assertTrue(viewModel.isGuestMode.value)
    assertEquals(com.example.model.AppFlowScreen.CHILD_MAIN_APP, viewModel.appFlowScreen.value)
    assertEquals(SectionTab.MY_WORLD, viewModel.currentSection.value)

    viewModel.navigateToFlow(com.example.model.AppFlowScreen.PARENT_AUTH)
    assertEquals(com.example.model.AppFlowScreen.PARENT_AUTH, viewModel.appFlowScreen.value)

    viewModel.onParentRegistered("Sister Amina", "amina@example.com")
    assertTrue(viewModel.isParentLoggedIn.value)
    assertEquals(com.example.model.AppFlowScreen.CREATE_CHILD_PROFILE, viewModel.appFlowScreen.value)

    val avatar = KidsRepository.characterAvatars.first()
    viewModel.onChildProfileCreated("Yusuf", 6, AgeGroup.LITTLE_EXPLORERS, avatar, "English")
    assertEquals(com.example.model.AppFlowScreen.CHILD_MAIN_APP, viewModel.appFlowScreen.value)
    assertEquals("Yusuf", viewModel.activeProfile.value.name)
  }

  @Test
  fun testParentCentre_profileManagementAndWeeklyGoals() {
    val viewModel = KidsViewModel()
    val initialCount = viewModel.profiles.value.size
    val firstProfile = viewModel.profiles.value.first()

    viewModel.editChildProfile(
      profileId = firstProfile.id,
      newName = "Zayd Ahmad",
      newAge = 7,
      newAgeGroup = AgeGroup.YOUNG_LEARNERS,
      newEmoji = "🦁",
      newLanguage = "Arabic"
    )
    assertEquals("Zayd Ahmad", viewModel.activeProfile.value.name)
    assertEquals(AgeGroup.YOUNG_LEARNERS, viewModel.activeProfile.value.ageGroup)

    val secondProfile = viewModel.profiles.value[1]
    viewModel.removeChildProfile(secondProfile.id)
    assertEquals(initialCount - 1, viewModel.profiles.value.size)

    val firstGoal = viewModel.weeklyGoals.value.first()
    viewModel.updateWeeklyGoalTarget(firstGoal.id, 6)
    assertEquals(6, viewModel.weeklyGoals.value.first { it.id == firstGoal.id }.targetCount)
  }

  @Test
  fun testSubscriptionsAndAdminReviewPipeline() {
    val viewModel = KidsViewModel()
    viewModel.selectSubscriptionPlan("Premium Annual", true, "$59.99")
    assertTrue(viewModel.parentSettings.value.isPremiumActive)
    assertEquals("Premium Annual", viewModel.parentSettings.value.activePlanName)

    viewModel.cancelSubscription()
    assertFalse(viewModel.parentSettings.value.isPremiumActive)
    assertEquals("Free Explorer", viewModel.parentSettings.value.activePlanName)

    viewModel.restorePurchases()
    assertTrue(viewModel.parentSettings.value.isPremiumActive)

    assertFalse(viewModel.isAdminStaffVerified.value)
    viewModel.verifyAdminStaff("ustadh.ahmad")
    assertTrue(viewModel.isAdminStaffVerified.value)

    val beforeItems = viewModel.reviewItems.value.size
    viewModel.submitNewContentDraft(
      title = "Kindness to Neighbours",
      type = "Story",
      category = "Good Manners",
      shortDescription = "Short story about sharing.",
      mainBody = "Page 1 content...",
      ageGroup = AgeGroup.YOUNG_LEARNERS,
      language = "English",
      durationLabel = "4 min",
      sourceRef = "Sahih Muslim",
      asDraft = false
    )
    assertEquals(beforeItems + 1, viewModel.reviewItems.value.size)
    val submitted = viewModel.reviewItems.value.first()
    assertEquals(com.example.model.ReviewWorkflowStatus.SUBMITTED, submitted.workflowStatus)

    viewModel.advanceReviewAction(
      itemId = submitted.id,
      newStatus = com.example.model.ReviewWorkflowStatus.APPROVED,
      comment = "Verified authentic"
    )
    val approved = viewModel.reviewItems.value.first { it.id == submitted.id }
    assertEquals(com.example.model.ReviewWorkflowStatus.APPROVED, approved.workflowStatus)
    assertTrue(approved.stages.all { it.passed })
  }
}
