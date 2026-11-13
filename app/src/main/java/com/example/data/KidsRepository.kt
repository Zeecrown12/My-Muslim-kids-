package com.example.data

import com.example.model.*

object KidsRepository {

  val defaultProfiles = listOf(
    ChildProfile(
      id = "child_1",
      name = "Zayd",
      age = 5,
      ageGroup = AgeGroup.LITTLE_EXPLORERS,
      avatarEmoji = "🦁",
      avatarBgHex = 0xFFD4F3E2,
      stars = 42,
      streakDays = 4,
      dailyMinutesSpent = 15
    ),
    ChildProfile(
      id = "child_2",
      name = "Maryam",
      age = 8,
      ageGroup = AgeGroup.YOUNG_LEARNERS,
      avatarEmoji = "🌸",
      avatarBgHex = 0xFFFEF3C7,
      stars = 88,
      streakDays = 7,
      dailyMinutesSpent = 24
    ),
    ChildProfile(
      id = "child_3",
      name = "Bilal",
      age = 11,
      ageGroup = AgeGroup.YOUNG_SCHOLARS,
      avatarEmoji = "🦅",
      avatarBgHex = 0xFFE0F2FE,
      stars = 135,
      streakDays = 12,
      dailyMinutesSpent = 35
    )
  )

  val characterAvatars = listOf(
    CharacterAvatar("char_zayd", "Zayd", "The Helper Detective", "🦁", 0xFFD4F3E2, "Courageous & loves helping elders"),
    CharacterAvatar("char_maryam", "Maryam", "Kindness Detective", "🌸", 0xFFFEF3C7, "Always smiling & sharing toys"),
    CharacterAvatar("char_bilal", "Bilal", "Star Gazer", "🦅", 0xFFE0F2FE, "Curious about Allah's universe"),
    CharacterAvatar("char_noor", "Noor", "Little Chef", "🌙", 0xFFFCE7F3, "Kindhearted & loves sharing treats"),
    CharacterAvatar("char_amina", "Amina", "Little Hafidhah", "⭐", 0xFFEDE9FE, "Recites Quran with sweet melodies"),
    CharacterAvatar("char_tariq", "Tariq", "The Honest Baker", "🥖", 0xFFFEF08A, "Truthful in speech & deed"),
    CharacterAvatar("char_hamza", "Hamza", "Sunnah Hero", "🧭", 0xFFDCFCE7, "Brave, energetic & loyal"),
    CharacterAvatar("char_safiyyah", "Safiyyah", "Curious Scholar", "📚", 0xFFE0E7FF, "Eager to learn Arabic & history")
  )

  val arabicAlphabet = listOf(
    ArabicLetter("أ", "Alif", "Alif", "أَرْنَب", "Arnab", "Rabbit", "🐰", "Starts with an 'a' sound like Apple", "ا", "اـ", "ـاـ", "ـا", "Throat (حلقية)"),
    ArabicLetter("ب", "Baa", "Baa", "بَيْت", "Bayt", "House", "🏠", "Starts with a 'b' sound like Ball, dot below!", "ب", "بـ", "ـبـ", "ـب", "Lips (شفوية)"),
    ArabicLetter("ت", "Taa", "Taa", "تُفَّاح", "Tuffah", "Apple", "🍎", "Starts with a 't' sound, has 2 smiling dots!", "ت", "تـ", "ـتـ", "ـت", "Tongue (لسانية)"),
    ArabicLetter("ث", "Thaa", "Thaa", "ثَعْلَب", "Tha'lab", "Fox", "🦊", "Starts with 'th' like Three, 3 dots on top!", "ث", "ثـ", "ـثـ", "ـث", "Tongue (لسانية)"),
    ArabicLetter("ج", "Jeem", "Jeem", "جَمَل", "Jamal", "Camel", "🐪", "Starts with 'j' sound like Jam, belly with a dot!", "ج", "جـ", "ـجـ", "ـج", "Tongue (لسانية)"),
    ArabicLetter("ح", "Haa", "Haa", "حَصَان", "Hisan", "Horse", "🐴", "Warm breathy 'h' sound from deep in the throat", "ح", "حـ", "ـحـ", "ـح", "Throat (حلقية)"),
    ArabicLetter("خ", "Khaa", "Khaa", "خُبْز", "Khubz", "Bread", "🍞", "Scratchy 'kh' sound with a dot above!", "خ", "خـ", "ـخـ", "ـخ", "Throat (حلقية)"),
    ArabicLetter("د", "Daal", "Daal", "دَرَاجَة", "Darraajah", "Bicycle", "🚲", "Clean 'd' sound like Door", "د", "دـ", "ـدـ", "ـد", "Tongue (لسانية)"),
    ArabicLetter("ذ", "Thaal", "Thaal", "ذُرَة", "Dhurah", "Corn", "🌽", "Soft 'th' sound like 'This' with a dot!", "ذ", "ذـ", "ـذـ", "ـذ", "Tongue (لسانية)"),
    ArabicLetter("ر", "Raa", "Raa", "رُمَّان", "Rumman", "Pomegranate", "🍇", "Rolling 'r' sound like River", "ر", "رـ", "ـرـ", "ـر", "Tongue (لسانية)"),
    ArabicLetter("ز", "Zay", "Zay", "زَهْرَة", "Zahrah", "Flower", "🌸", "Buzzing 'z' sound like Zebra with a dot!", "ز", "زـ", "ـزـ", "ـز", "Tongue (لسانية)"),
    ArabicLetter("س", "Seen", "Seen", "سَمَكَة", "Samakah", "Fish", "🐟", "Sharp 's' sound like Sun with 3 teeth", "س", "سـ", "ـسـ", "ـس", "Tongue (لسانية)"),
    ArabicLetter("ش", "Sheen", "Sheen", "شَمْس", "Shams", "Sun", "☀️", "Gentle 'sh' sound like Ship with 3 dots!", "ش", "شـ", "ـشـ", "ـش", "Tongue (لسانية)"),
    ArabicLetter("ص", "Saad", "Saad", "صَقْر", "Saqr", "Falcon", "🦅", "Deep emphatic 's' sound from the back of mouth", "ص", "صـ", "ـصـ", "ـص", "Tongue (لسانية)"),
    ArabicLetter("ض", "Daad", "Daad", "ضِفْدَع", "Difda'", "Frog", "🐸", "Unique Arabic letter 'D' spoken with tongue edge", "ض", "ضـ", "ـضـ", "ـض", "Tongue (لسانية)"),
    ArabicLetter("ط", "Taa (Heavy)", "Taa", "طَيَّارَة", "Tayyarah", "Aeroplane", "✈️", "Strong bold 'T' sound", "ط", "طـ", "ـطـ", "ـط", "Tongue (لسانية)"),
    ArabicLetter("ظ", "Dhaa (Heavy)", "Zhaa", "ظَبْي", "Zaby", "Gazelle", "🦌", "Heavy soft 'th' sound with a dot", "ظ", "ظـ", "ـظـ", "ـظ", "Tongue (لسانية)"),
    ArabicLetter("ع", "Ayn", "'Ayn", "عِنَب", "'Inab", "Grapes", "🍇", "Deep guttural throat sound, unique to Arabic!", "ع", "عـ", "ـعـ", "ـع", "Throat (حلقية)"),
    ArabicLetter("غ", "Ghayn", "Ghayn", "غَزَال", "Ghazaal", "Deer", "🦌", "Gargling 'gh' sound like Paris 'r'", "غ", "غـ", "ـغـ", "ـغ", "Throat (حلقية)"),
    ArabicLetter("ف", "Faa", "Faa", "فِيل", "Feel", "Elephant", "🐘", "Soft 'f' sound like Flower with a single loop", "ف", "فـ", "ـفـ", "ـف", "Lips (شفوية)"),
    ArabicLetter("ق", "Qaaf", "Qaaf", "قَمَر", "Qamar", "Moon", "🌙", "Deep 'q' sound from base of throat with 2 dots", "ق", "قـ", "ـقـ", "ـق", "Throat (حلقية)"),
    ArabicLetter("ك", "Kaaf", "Kaaf", "كِتَاب", "Kitaab", "Book", "📖", "Light 'k' sound like Kite with a mini mark", "ك", "كـ", "ـكـ", "ـك", "Tongue (لسانية)"),
    ArabicLetter("ل", "Laam", "Laam", "لَيْمُون", "Laymoon", "Lemon", "🍋", "Smooth 'l' sound like Lake", "ل", "لـ", "ـلـ", "ـل", "Tongue (لسانية)"),
    ArabicLetter("م", "Meem", "Meem", "مَسْجِد", "Masjid", "Mosque", "🕌", "Humming 'm' sound like Moon", "م", "مـ", "ـمـ", "ـم", "Lips (شفوية)"),
    ArabicLetter("ن", "Noon", "Noon", "نَجْمَة", "Najmah", "Star", "⭐", "Clear 'n' sound like Nest with a seed inside", "ن", "نـ", "ـنـ", "ـن", "Tongue (لسانية)"),
    ArabicLetter("هـ", "Haa (Light)", "Haa", "هِلَال", "Hilaal", "Crescent", "🌙", "Light gentle 'h' sound like Heart", "هـ", "هـ", "ـهـ", "ـه", "Throat (حلقية)"),
    ArabicLetter("و", "Waaw", "Waaw", "وَرْدَة", "Wardah", "Rose", "🌹", "Rounded 'w' sound like Water", "و", "وـ", "ـوـ", "ـو", "Lips (شفوية)"),
    ArabicLetter("ي", "Yaa", "Yaa", "يَد", "Yad", "Hand", "🖐️", "Friendly 'y' sound like Yellow with 2 dots below", "ي", "يـ", "ـيـ", "ـي", "Tongue (لسانية)")
  )

  val arabicVocabulary = listOf(
    ArabicWordItem("vocab_salam", "السَّلَامُ عَلَيْكُمْ", "Assalamu Alaykum", "Peace be upon you", "Everyday Expressions", "🤝"),
    ArabicWordItem("vocab_bismillah", "بِسْمِ اللَّهِ", "Bismillah", "In the name of Allah", "Everyday Expressions", "✨"),
    ArabicWordItem("vocab_alhamdulillah", "الْحَمْدُ لِلَّهِ", "Alhamdulillah", "Praise be to Allah", "Everyday Expressions", "🤲"),
    ArabicWordItem("vocab_subhanallah", "سُبْحَانَ اللَّهِ", "SubhanAllah", "Glory be to Allah", "Everyday Expressions", "🌟"),
    ArabicWordItem("vocab_jazakallah", "جَزَاكَ اللَّهُ خَيْرًا", "JazakAllahu Khayran", "May Allah reward you with goodness", "Everyday Expressions", "🎁"),
    ArabicWordItem("vocab_inshallah", "إِنْ شَاءَ اللَّهُ", "Insha'Allah", "If Allah wills", "Everyday Expressions", "🌱"),
    ArabicWordItem("vocab_kitab", "كِتَاب", "Kitaab", "Book", "Classroom & Home", "📖"),
    ArabicWordItem("vocab_qalam", "قَلَم", "Qalam", "Pen / Pencil", "Classroom & Home", "✏️"),
    ArabicWordItem("vocab_bayt", "بَيْت", "Bayt", "House", "Classroom & Home", "🏠"),
    ArabicWordItem("vocab_masjid", "مَسْجِد", "Masjid", "Mosque", "Classroom & Home", "🕌"),
    ArabicWordItem("vocab_shams", "شَمْس", "Shams", "Sun", "Animals & Nature", "☀️"),
    ArabicWordItem("vocab_qamar", "قَمَر", "Qamar", "Moon", "Animals & Nature", "🌙"),
    ArabicWordItem("vocab_maa", "مَاء", "Maa'", "Water", "Food & Drink", "💧"),
    ArabicWordItem("vocab_tamr", "تَمْر", "Tamr", "Dates", "Food & Drink", "🌴"),
    ArabicWordItem("vocab_zahrah", "زَهْرَة", "Zahrah", "Flower", "Animals & Nature", "🌸"),
    ArabicWordItem("vocab_jamal", "جَمَل", "Jamal", "Camel", "Animals & Nature", "🐪")
  )

  val shortSurahs = listOf(
    SurahItem(
      number = 1,
      nameArabic = "الفَاتِحَة",
      nameEnglish = "Al-Fatihah",
      meaningEnglish = "The Opening",
      totalVerses = 7,
      revelationType = "Makki",
      revelationStoryForKids = "This is the most recited Surah in Islam! We recite it in every single unit of prayer. It is a sweet conversation between you and Allah.",
      verses = listOf(
        AyahItem(1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "Bismillaahir-Rahmaanir-Raheem", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
        AyahItem(2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "Alhamdu lillaahi Rabbil-'aalameen", "All praise is due to Allah, Lord of all the worlds."),
        AyahItem(3, "الرَّحْمَٰنِ الرَّحِيمِ", "Ar-Rahmaanir-Raheem", "The Entirely Merciful, the Especially Merciful."),
        AyahItem(4, "مَالِكِ يَوْمِ الدِّينِ", "Maaliki Yawmid-Deen", "Sovereign of the Day of Recompense."),
        AyahItem(5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "Iyyaaka na'budu wa lyyaaka nasta'een", "It is You we worship and You we ask for help."),
        AyahItem(6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "Ihdinas-Siraatal-Mustaqeem", "Guide us to the straight path."),
        AyahItem(7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "Siraatal-ladheena an'amta 'alayhim ghayril-maghdoobi 'alayhim wa lad-daalleen", "The path of those upon whom You have bestowed favor, not of those who have evoked anger or of those who are astray.")
      )
    ),
    SurahItem(
      number = 112,
      nameArabic = "الإِخْلَاص",
      nameEnglish = "Al-Ikhlas",
      meaningEnglish = "The Sincerity",
      totalVerses = 4,
      revelationType = "Makki",
      revelationStoryForKids = "When people asked the Prophet Muhammad ﷺ about Allah, Allah revealed this beautiful Surah teaching that Allah is One, Eternal, and has no partners or equals. Reciting it equals one-third of the Quran!",
      verses = listOf(
        AyahItem(1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "Qul Huwal-laahu Ahad", "Say, 'He is Allah, [who is] One.'"),
        AyahItem(2, "اللَّهُ الصَّمَدُ", "Allahus-Samad", "Allah, the Eternal Refuge."),
        AyahItem(3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "Lam yalid wa lam yoolad", "He neither begets nor is born,"),
        AyahItem(4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "Wa lam yakul-lahu kufuwan ahad", "Nor is there to Him any equivalent.")
      )
    ),
    SurahItem(
      number = 113,
      nameArabic = "الفَلَق",
      nameEnglish = "Al-Falaq",
      meaningEnglish = "The Daybreak",
      totalVerses = 5,
      revelationType = "Makki",
      revelationStoryForKids = "This Surah is a special shield! It teaches children to seek Allah's protection from the darkness, envy, and unseen harms. We recite it every morning and before bed.",
      verses = listOf(
        AyahItem(1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "Qul a'oozu bi Rabbil-falaq", "Say, 'I seek refuge in the Lord of daybreak'"),
        AyahItem(2, "مِن شَرِّ مَا خَلَقَ", "Min sharri maa khalaq", "From the evil of that which He created"),
        AyahItem(3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "Wa min sharri ghaasiqin izaa waqab", "And from the evil of darkness when it settles"),
        AyahItem(4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "Wa min sharrin-naffaathaati fil 'uqad", "And from the evil of the blowers in knots"),
        AyahItem(5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "Wa min sharri haasidin izaa hasad", "And from the evil of an envier when he envies.")
      )
    ),
    SurahItem(
      number = 114,
      nameArabic = "النَّاس",
      nameEnglish = "An-Nas",
      meaningEnglish = "Mankind",
      totalVerses = 6,
      revelationType = "Makki",
      revelationStoryForKids = "The final Surah in the Quran! It reminds us that Allah is the King and Protector of all people, helping us turn away from bad whispers and stay pure and kind.",
      verses = listOf(
        AyahItem(1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "Qul a'oozu bi Rabbin-naas", "Say, 'I seek refuge in the Lord of mankind,'"),
        AyahItem(2, "مَلِكِ النَّاسِ", "Malikin-naas", "The Sovereign of mankind,"),
        AyahItem(3, "إِلَٰهِ النَّاسِ", "Ilaahin-naas", "The God of mankind,"),
        AyahItem(4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "Min sharril-waswaasil-khannaas", "From the evil of the retreating whisperer -"),
        AyahItem(5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "Allazee yuwaswisu fee sudoorin-naas", "Who whispers [evil] into the breasts of mankind -"),
        AyahItem(6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "Minal jinnati wan-naas", "From among the jinn and mankind.")
      )
    ),
    SurahItem(
      number = 108,
      nameArabic = "الكَوْثَر",
      nameEnglish = "Al-Kawthar",
      meaningEnglish = "Abundance",
      totalVerses = 3,
      revelationType = "Makki",
      revelationStoryForKids = "The shortest Surah in the Quran! Allah gave Prophet Muhammad ﷺ the good news of Al-Kawthar—a magical heavenly river sweeter than honey and whiter than milk.",
      verses = listOf(
        AyahItem(1, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ", "Innaaa a'tainaakal-Kawthar", "Indeed, We have granted you, [O Muhammad], Al-Kawthar."),
        AyahItem(2, "فَصَلِّ لِرَبِّكَ وَانْحَرْ", "Fasalli li Rabbika wanhar", "So pray to your Lord and sacrifice [to Him alone]."),
        AyahItem(3, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ", "Inna shaani'aka huwal abtar", "Indeed, your enemy is the one cut off.")
      )
    ),
    SurahItem(
      number = 103,
      nameArabic = "العَصْر",
      nameEnglish = "Al-'Asr",
      meaningEnglish = "The Declining Day",
      totalVerses = 3,
      revelationType = "Makki",
      revelationStoryForKids = "Imam Ash-Shafi'i said if people only pondered this 3-verse Surah, it would be enough for them! It teaches how precious time is, and how faith and good deeds save us.",
      verses = listOf(
        AyahItem(1, "وَالْعَصْرِ", "Wal 'Asr", "By time,"),
        AyahItem(2, "إِنَّ الْإِنسَانَ لَفِي خُسْرٍ", "Innal-insaana lafee khusr", "Indeed, mankind is in loss,"),
        AyahItem(3, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ", "Illal-ladheena aamanoo wa 'amilus-saalihaati wa tawaasaw bil-haqqi wa tawaasaw bis-sabr", "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.")
      )
    )
  )

  val dailyDuas = listOf(
    DuaItem(
      id = "dua_wake_up",
      title = "Upon Waking Up",
      category = "Daily Routines",
      situation = "When opening eyes in the morning",
      arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
      transliteration = "Alhamdu lillaahil-ladhee ahyaanaa ba'da maa amaatanaa wa ilayhin-nushoor",
      englishTranslation = "All praise is for Allah who gave us life after causing us to sleep, and to Him is our return.",
      sunnahEtiquette = "Rub face gently, smile, and thank Allah for a blessed new day.",
      emoji = "🌅",
      sourceRef = "Sahih al-Bukhari 6312",
      audioDurationSec = 8,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_before_eating",
      title = "Before Eating or Drinking",
      category = "Mealtime Manners",
      situation = "Before taking your first bite or sip",
      arabicText = "بِسْمِ اللَّهِ وَعَلَى بَرَكَةِ اللَّهِ",
      transliteration = "Bismillaahi wa 'alaa barakatillaah",
      englishTranslation = "In the name of Allah and with the blessings of Allah.",
      sunnahEtiquette = "Wash your hands first, sit down, and eat with your right hand.",
      emoji = "🍽️",
      sourceRef = "Al-Hakim 7084 & Abu Dawud",
      audioDurationSec = 6,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_after_eating",
      title = "After Finishing a Meal",
      category = "Mealtime Manners",
      situation = "When you finish eating your food",
      arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مُسْلِمِينَ",
      transliteration = "Alhamdu lillaahil-ladhee at'amanaa wa saqaanaa wa ja'alanaa muslimeen",
      englishTranslation = "All praise is for Allah who has fed us, given us drink, and made us Muslims.",
      sunnahEtiquette = "Never waste food, and thank the person who cooked with 'Jazakallahu Khayran'.",
      emoji = "🥣",
      sourceRef = "Sunan Abi Dawud 3850 & At-Tirmidhi",
      audioDurationSec = 8,
      isMemorized = false
    ),
    DuaItem(
      id = "dua_enter_bathroom",
      title = "Entering the Bathroom",
      category = "Cleanliness & Protection",
      situation = "Before stepping into the restroom",
      arabicText = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ وَالْخَبَائِثِ",
      transliteration = "Allaahumma innee a'oodhu bika minal-khubuthi wal-khabaa'ith",
      englishTranslation = "O Allah, I seek refuge in You from all foul and wicked spirits.",
      sunnahEtiquette = "Enter with your left foot first, keep quiet inside, and maintain cleanliness.",
      emoji = "🚪",
      sourceRef = "Sahih al-Bukhari 142 & Muslim 375",
      audioDurationSec = 7,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_leave_bathroom",
      title = "Leaving the Bathroom",
      category = "Cleanliness & Protection",
      situation = "Stepping out of the restroom",
      arabicText = "غُفْرَانَكَ",
      transliteration = "Ghufranaka",
      englishTranslation = "I seek Your forgiveness, O Allah.",
      sunnahEtiquette = "Step out with your right foot first and wash hands thoroughly with soap.",
      emoji = "🧼",
      sourceRef = "Sunan Abi Dawud 17 & At-Tirmidhi 7",
      audioDurationSec = 4,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_leaving_home",
      title = "Leaving the House",
      category = "Safety & Protection",
      situation = "Stepping outside for school or playing",
      arabicText = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
      transliteration = "Bismillaahi tawakkaltu 'alallaahi wa laa hawla wa laa quwwata illaa billaah",
      englishTranslation = "In the name of Allah, I place my trust in Allah, and there is no power or might except with Allah.",
      sunnahEtiquette = "Step out with left foot first; angels are assigned to guard and protect you.",
      emoji = "🏡",
      sourceRef = "Sunan Abi Dawud 5095 & At-Tirmidhi",
      audioDurationSec = 9,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_entering_home",
      title = "Entering the House",
      category = "Safety & Protection",
      situation = "Returning home from outside",
      arabicText = "بِسْمِ اللَّهِ وَلَجْنَا، وَبِسْمِ اللَّهِ خَرَجْنَا، وَعَلَى رَبِّنَا تَوَكَّلْنَا",
      transliteration = "Bismillaahi walajnaa, wa bismillaahi kharajnaa, wa 'alaa Rabbinaa tawakkalnaa",
      englishTranslation = "In the name of Allah we enter, in the name of Allah we leave, and upon our Lord we rely.",
      sunnahEtiquette = "Step in with right foot first and greet your family with a warm 'As-salamu alaykum'.",
      emoji = "🔑",
      sourceRef = "Sunan Abi Dawud 5096",
      audioDurationSec = 8,
      isMemorized = false
    ),
    DuaItem(
      id = "dua_sleeping",
      title = "Before Going to Sleep",
      category = "Bedtime Peace",
      situation = "Lying down in bed at night",
      arabicText = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
      transliteration = "Bismika Allaahumma amootu wa ahyaa",
      englishTranslation = "In Your name, O Allah, I die and I live.",
      sunnahEtiquette = "Make wudu before bed, dust your sheets 3 times, and lie on your right side.",
      emoji = "🌙",
      sourceRef = "Sahih al-Bukhari 6324 & Muslim",
      audioDurationSec = 6,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_knowledge",
      title = "For Increasing Knowledge",
      category = "School & Learning",
      situation = "Before starting study, Quran memorization or homework",
      arabicText = "رَبِّ زِدْنِي عِلْمًا",
      transliteration = "Rabbi zidnee 'ilmaa",
      englishTranslation = "My Lord, increase me in knowledge.",
      sunnahEtiquette = "Ask sincerely for beneficial knowledge that inspires kindness and good deeds.",
      emoji = "📚",
      sourceRef = "Surah Ta-Ha 20:114",
      audioDurationSec = 5,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_parents",
      title = "Dua for Parents (Birr al-Walidayn)",
      category = "Kindness & Akhlaq",
      situation = "Every day after prayer and when feeling love for parents",
      arabicText = "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
      transliteration = "Rabbir-hamhumaa kamaa rabbayaanee sagheeraa",
      englishTranslation = "My Lord, have mercy upon them both as they brought me up when I was little.",
      sunnahEtiquette = "Hug parents, listen with love, and pray for their long health and Paradise.",
      emoji = "❤️",
      sourceRef = "Surah Al-Isra 17:24",
      audioDurationSec = 7,
      isMemorized = true
    ),
    DuaItem(
      id = "dua_sneezing",
      title = "Etiquette of Sneezing",
      category = "Daily Manners",
      situation = "When sneezing and hearing others sneeze",
      arabicText = "الْحَمْدُ لِلَّهِ (يَرْحَمُكَ اللَّهُ)",
      transliteration = "Alhamdu lillaah (Yarhamukallaah)",
      englishTranslation = "All praise is for Allah (May Allah have mercy on you).",
      sunnahEtiquette = "Cover nose/mouth with an elbow, lower voice, and say Alhamdulillah.",
      emoji = "🤧",
      sourceRef = "Sahih al-Bukhari 6224",
      audioDurationSec = 5,
      isMemorized = false
    ),
    DuaItem(
      id = "dua_rain",
      title = "When It Rains",
      category = "Daily Routines",
      situation = "When rain starts pouring from the clouds",
      arabicText = "اللَّهُمَّ صَيِّبًا نَافِعًا",
      transliteration = "Allaahumma sayyiban naafi'aa",
      englishTranslation = "O Allah, make it a beneficial downpour.",
      sunnahEtiquette = "Smile at the blessing of rain, as duas are answered when rain falls.",
      emoji = "🌧️",
      sourceRef = "Sahih al-Bukhari 1032",
      audioDurationSec = 5,
      isMemorized = false
    )
  )

  val sunnahHabits = listOf(
    SunnahHabitItem("habit_smile", "Smile at someone today", "Smiling at your brother or sister is charity (Sadaqah).", "😊", "Tirmidhi 1956", true),
    SunnahHabitItem("habit_right_hand", "Eat and drink with your right hand", "The Prophet ﷺ taught: Mention the name of Allah and eat with your right hand.", "🤲", "Bukhari 5376", true),
    SunnahHabitItem("habit_salam", "Say As-salamu alaykum first", "Spread peace and greeting among people you know and do not know.", "🤝", "Muslim 54", false),
    SunnahHabitItem("habit_help", "Help mom or dad with a chore", "Serving your parents is one of the highest deeds in Islam.", "🧹", "Bukhari", true),
    SunnahHabitItem("habit_dhikr", "Say SubhanAllah 10 times", "Two words are light on the tongue but heavy on the scales of good deeds.", "📿", "Bukhari 6406", false)
  )

  data class TasbihDhikr(
    val id: String,
    val arabic: String,
    val transliteration: String,
    val englishMeaning: String,
    val targetCount: Int = 33,
    val rewardNotice: String
  )

  val tasbihDhikrs = listOf(
    TasbihDhikr("tasbih_subhanallah", "سُبْحَانَ اللَّهِ", "SubhanAllah", "Glory be to Allah", 33, "A tree is planted in Paradise for each repetition!"),
    TasbihDhikr("tasbih_alhamdulillah", "الْحَمْدُ لِلَّهِ", "Alhamdulillah", "All praise is for Allah", 33, "Fills the heavenly scales of good deeds with light!"),
    TasbihDhikr("tasbih_allahuakbar", "اللَّهُ أَكْبَرُ", "Allahu Akbar", "Allah is the Greatest", 34, "Greater than anything in the heavens and earth!"),
    TasbihDhikr("tasbih_astaghfirullah", "أَسْتَغْفِرُ اللَّهَ", "Astaghfirullah", "I seek Allah's forgiveness", 33, "Brings ease, rain, and peace into your day.")
  )

  val stories = listOf(
    Story(
      id = "story_yunus",
      title = "Prophet Yunus & the Whale",
      category = "Stories of the Prophets",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "How Prophet Yunus learned patience and called out to Allah from the depths of the ocean and the belly of a giant whale.",
      coverEmoji = "🐋",
      readTimeMinutes = 5,
      moralLesson = "Never give up hope, be patient with others, and always turn to Allah with sincere repentance whenever you make a mistake.",
      reflectionQuestion = "What special prayer did Prophet Yunus say inside the belly of the whale?",
      quizOptions = listOf(
        "La ilaha illa Anta, Subhanaka inni kuntu minaz-zalimeen",
        "Bismillahi wa 'ala barakatillah",
        "Alhamdu lillahi rabbil 'alameen"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Read & Listen",
      isFavorite = true,
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "Long ago, in the ancient city of Nineveh, lived Prophet Yunus (peace be upon him). He loved his people and called them to worship only Allah, the Creator of the heavens and earth. But the people were stubborn and would not listen.",
          sceneEmoji = "🏛️",
          audioDurationSec = 14,
          illustrationTitle = "The City of Nineveh"
        ),
        StoryPage(
          pageNumber = 2,
          text = "Feeling disheartened, Yunus decided to leave the city and board a ship across the wide blue sea. As the ship sailed into deep waters, dark clouds gathered and stormy waves began to rock the boat back and forth!",
          sceneEmoji = "⛵",
          audioDurationSec = 16,
          illustrationTitle = "Stormy Ocean Waves"
        ),
        StoryPage(
          pageNumber = 3,
          text = "To keep the ship from sinking, lots were drawn, and Yunus was chosen. He leaped into the sea, placing his trust in Allah. Suddenly, by Allah's command, a colossal, gentle whale swallowed Yunus whole—without hurting a single bone!",
          sceneEmoji = "🌊",
          audioDurationSec = 18,
          illustrationTitle = "The Gentle Whale"
        ),
        StoryPage(
          pageNumber = 4,
          text = "In the darkness of the night, the sea, and the whale's belly, Yunus prayed with all his heart: 'There is no deity except You; exalted are You! Indeed, I have been of the wrongdoers.' Allah heard his humble whisper and commanded the whale to safely deliver Yunus onto a warm, sandy shore under a shady gourd plant.",
          sceneEmoji = "🌿",
          audioDurationSec = 20,
          illustrationTitle = "Deliverance on the Shore"
        )
      )
    ),
    Story(
      id = "story_nuh_ark",
      title = "Prophet Nuh & the Great Ark",
      category = "Stories of the Prophets",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "How Prophet Nuh built a massive wooden vessel in the middle of dry land, following Allah's guidance with steadfast patience.",
      coverEmoji = "🚢",
      readTimeMinutes = 6,
      moralLesson = "Trusting Allah's commands even when others mock or misunderstand leads to safety and divine blessing.",
      reflectionQuestion = "What animals did Prophet Nuh welcome onto the Ark?",
      quizOptions = listOf(
        "A pair of each kind (male and female)",
        "Only birds",
        "Only fish"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Read & Listen",
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "For hundreds of years, Prophet Nuh called his people to worship Allah alone and turn away from injustice. But they closed their ears and laughed.",
          sceneEmoji = "🏜️",
          audioDurationSec = 15,
          illustrationTitle = "The Desert of Patience"
        ),
        StoryPage(
          pageNumber = 2,
          text = "Allah commanded Nuh to construct a towering three-decked Ark made of strong cedar wood and pitch. Passersby wondered: 'Why build a giant ship where there is no ocean?' Nuh smiled patiently and continued hammer and plank.",
          sceneEmoji = "🪵",
          audioDurationSec = 16,
          illustrationTitle = "Building the Great Ark"
        ),
        StoryPage(
          pageNumber = 3,
          text = "When the rains poured from the skies and water gushed from the earth, Nuh welcomed the believers and pairs of every creature—birds, gazelles, and sheep—aboard the safe haven.",
          sceneEmoji = "🌧️",
          audioDurationSec = 17,
          illustrationTitle = "Gathering the Pairs"
        ),
        StoryPage(
          pageNumber = 4,
          text = "The Ark floated peacefully on mountain-high waves until the storm passed. The ship came to rest safely upon Mount Judi, and a warm rainbow arched across the freshly cleansed sky.",
          sceneEmoji = "🌈",
          audioDurationSec = 18,
          illustrationTitle = "Peace on Mount Judi"
        )
      )
    ),
    Story(
      id = "story_sulaiman_ant",
      title = "Prophet Sulaiman & the Talking Ant",
      category = "Stories of the Prophets",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      summary = "Prophet Sulaiman had a magnificent army, but he paused his entire procession to listen to a tiny ant!",
      coverEmoji = "🐜",
      readTimeMinutes = 4,
      moralLesson = "Great leaders are gentle and merciful to even the smallest of Allah's creatures.",
      reflectionQuestion = "Why did Prophet Sulaiman stop his army?",
      quizOptions = listOf(
        "To protect the tiny ants from being stepped on",
        "Because it started raining heavily",
        "Because he was tired"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Read & Listen",
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "Allah blessed Prophet Sulaiman (peace be upon him) with great wisdom and the miraculous ability to understand the languages of birds, animals, and insects!",
          sceneEmoji = "👑",
          audioDurationSec = 12,
          illustrationTitle = "The Wise King"
        ),
        StoryPage(
          pageNumber = 2,
          text = "One sunny day, Sulaiman was marching with his soldiers through a green valley. A little ant spotted them coming and called out: 'O ants! Enter your dwellings so that Sulaiman and his soldiers do not crush you without noticing!'",
          sceneEmoji = "🐜",
          audioDurationSec = 15,
          illustrationTitle = "The Ant Valley"
        ),
        StoryPage(
          pageNumber = 3,
          text = "Sulaiman heard the little ant and smiled warmly. He commanded his huge army to halt and wait until every tiny ant was safe inside their home. Then he thanked Allah for His wonderful blessings.",
          sceneEmoji = "💛",
          audioDurationSec = 14,
          illustrationTitle = "Kindness to Small Creatures"
        )
      )
    ),
    Story(
      id = "story_salman_truth",
      title = "Salman Al-Farsi's Search for Truth",
      category = "Stories of the Companions",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      summary = "Young Salman left behind wealth and luxury in Persia, traveling through deserts for years until finding the beloved Prophet ﷺ in Madinah.",
      coverEmoji = "🧭",
      readTimeMinutes = 7,
      moralLesson = "Sincere determination to find truth and live by justice is always guided and rewarded by Allah.",
      reflectionQuestion = "What brilliant tactical idea did Salman suggest to protect Madinah?",
      quizOptions = listOf(
        "Digging a deep defensive trench (Khandaq)",
        "Building a high wall of stone",
        "Retreating to the mountains"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Read & Listen",
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "In ancient Persia, young Salman had palaces, gardens, and comfort. Yet his heart sought something deeper: Who created the stars, and how should we truly worship Him?",
          sceneEmoji = "🏰",
          audioDurationSec = 16,
          illustrationTitle = "The Palace in Persia"
        ),
        StoryPage(
          pageNumber = 2,
          text = "He gave up his riches and journeyed with desert caravans from Syria to Arabia, enduring hardships and captivity with unwavering patience.",
          sceneEmoji = "🐪",
          audioDurationSec = 18,
          illustrationTitle = "Caravan of Determination"
        ),
        StoryPage(
          pageNumber = 3,
          text = "When he met Prophet Muhammad ﷺ in Madinah, Salman recognized the signs of prophethood: his radiant humility, accepting gifts while never taking charity for himself. Salman embraced Islam with joyful tears.",
          sceneEmoji = "🌴",
          audioDurationSec = 18,
          illustrationTitle = "Arrival in Madinah"
        )
      )
    ),
    Story(
      id = "story_ibn_battuta",
      title = "Ibn Battuta: The Great Explorer",
      category = "Muslim Heroes and Contributions",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      summary = "Setting out on a donkey from Morocco at age 21, Ibn Battuta traversed 73,000 miles across Africa, the Middle East, India, and China.",
      coverEmoji = "🗺️",
      readTimeMinutes = 6,
      moralLesson = "Knowledge, exploration, and learning about diverse cultures enrich our understanding of Allah's vast world.",
      reflectionQuestion = "Where did Ibn Battuta begin his incredible journey?",
      quizOptions = listOf(
        "Tangier, Morocco",
        "Cairo, Egypt",
        "Istanbul, Turkey"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Read & Listen",
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "In 1325, 21-year-old Ibn Battuta bid farewell to his parents in Tangier, Morocco, with one initial goal: to complete the pilgrimage (Hajj) to Makkah.",
          sceneEmoji = "🎒",
          audioDurationSec = 15,
          illustrationTitle = "The Farewell at Tangier"
        ),
        StoryPage(
          pageNumber = 2,
          text = "Once he reached Makkah, his thirst for discovering Allah's earth grew. He traveled on sailboats, camels, and foot across 44 modern countries, writing detailed notes on geography and people.",
          sceneEmoji = "⛵",
          audioDurationSec = 17,
          illustrationTitle = "Sailing the Indian Ocean"
        ),
        StoryPage(
          pageNumber = 3,
          text = "Everywhere he traveled, Muslims welcomed him as a brother. His famous book of travels (Rihla) continues to inspire adventurers across the globe today.",
          sceneEmoji = "📜",
          audioDurationSec = 16,
          illustrationTitle = "The Book of Rihla"
        )
      )
    ),
    Story(
      id = "story_bedtime_dhikr",
      title = "The Whispering Stars: A Bedtime Dhikr",
      category = "Bedtime Stories",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      summary = "Join little Noor as she looks through her cozy bedroom window, counts the glowing stars, and says peaceful bedtime duas.",
      coverEmoji = "🌙",
      readTimeMinutes = 4,
      moralLesson = "Ending each day with gratitude, Dhikr, and peaceful reflection brings sweet dreams and calm hearts.",
      reflectionQuestion = "What words bring calm to our hearts before sleeping?",
      quizOptions = listOf(
        "Bismika Allahumma amutu wa ahya",
        "Allahu Akbar while jumping",
        "Saying nothing"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Audio Only",
      isFavorite = true,
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "The silver moon peeked through the window curtains. Noor tucked her cozy patchwork blanket up to her chin and listened to the gentle night breeze.",
          sceneEmoji = "🛏️",
          audioDurationSec = 14,
          illustrationTitle = "Cozy Bedroom Twilight"
        ),
        StoryPage(
          pageNumber = 2,
          text = "'One, two, three...' Noor counted the stars. 'SubhanAllah,' she whispered. 'Allah made each star twinkle like a jewel in the velvet sky.'",
          sceneEmoji = "✨",
          audioDurationSec = 14,
          illustrationTitle = "The Velvet Sky"
        ),
        StoryPage(
          pageNumber = 3,
          text = "Mama smoothed Noor's hair and recited Surah Al-Ikhlas, Al-Falaq, and An-Nas. Noor placed her hand under her cheek and drifted into peaceful sleep with Bismillah.",
          sceneEmoji = "😴",
          audioDurationSec = 15,
          illustrationTitle = "Sweet Islamic Dreams"
        )
      )
    ),
    Story(
      id = "story_secret_sadaqah",
      title = "The Secret Sadaqah of Madinah",
      category = "Stories about Kindness, Honesty and Helping Others",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "A heartwarming historical mystery: every night, poor families in Madinah woke up to find warm loaves and bags of grain on their doorsteps.",
      coverEmoji = "🌾",
      readTimeMinutes = 5,
      moralLesson = "The highest charity is given so quietly that the left hand does not know what the right hand gives.",
      reflectionQuestion = "Who was secretly carrying heavy sacks of food each night?",
      quizOptions = listOf(
        "Ali ibn Husayn (Zayn al-Abidin)",
        "A group of Roman merchants",
        "No one ever knew"
      ),
      correctOptionIndex = 0,
      hasAudio = true,
      format = "Read & Listen",
      pages = listOf(
        StoryPage(
          pageNumber = 1,
          text = "For many months in the tranquil city of Madinah, elderly widows and hungry families found sacks of flour and fresh dates outside their doors every dawn.",
          sceneEmoji = "🚪",
          audioDurationSec = 15,
          illustrationTitle = "The Morning Gift"
        ),
        StoryPage(
          pageNumber = 2,
          text = "No one ever saw the generous giver. He walked through the quiet streets in the dead of night, concealing his identity from all eyes.",
          sceneEmoji = "🌌",
          audioDurationSec = 16,
          illustrationTitle = "The Midnight Walk"
        ),
        StoryPage(
          pageNumber = 3,
          text = "Years later, when the righteous grandson of Ali, Zayn al-Abidin, passed away, the secret gifts stopped. Marks on his shoulders revealed he had carried sacks of charity alone for years solely for Allah's pleasure.",
          sceneEmoji = "❤️",
          audioDurationSec = 18,
          illustrationTitle = "A Pure Legacy"
        )
      )
    )
  )

  val tvEpisodes = listOf(
    VideoItem(
      id = "tv_helper_detectives_1",
      title = "The Secret Sadaqah Mystery",
      series = "The Helper Detectives",
      category = "Original Cartoon Series",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      duration = "8:30",
      durationSeconds = 510,
      thumbnailEmoji = "🕵️",
      description = "Someone has been secretly sweeping the mosque courtyard and leaving fresh flowers on Grandma Amina's porch! Detective Zayd and Maryam set out on a fun mission to uncover who is practicing secret charity.",
      keyTakeaway = "Giving charity secretly for the sake of Allah without boasting is one of the beloved deeds in Islam.",
      chapters = listOf(
        VideoChapter(0, "The Clues on the Porch"),
        VideoChapter(120, "Investigating the Courtyard"),
        VideoChapter(310, "The Footprints of Good Deeds"),
        VideoChapter(450, "The Best Charity is Secret")
      ),
      quizQuestion = "Why did the helper leave the charity in secret?",
      quizOptions = listOf(
        "To please Allah alone without showing off",
        "Because they were playing hide and seek",
        "Because they were shy"
      ),
      quizAnswerIndex = 0,
      isFavorite = true
    ),
    VideoItem(
      id = "tv_super_sunnah_1",
      title = "The Right-Hand Hero",
      series = "Super Sunnah Squad",
      category = "Good Manners",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      duration = "6:15",
      durationSeconds = 375,
      thumbnailEmoji = "🦸",
      description = "Meet the Super Sunnah Squad! Follow along as little Hamza learns how ordinary daily habits like eating with the right hand, sharing toys, and smiling turn him into a real-life neighborhood hero.",
      keyTakeaway = "Following the Prophet's ﷺ Sunnah fills our days with light, Barakah, and kindness.",
      chapters = listOf(
        VideoChapter(0, "Morning Mission"),
        VideoChapter(90, "Breakfast with the Right Hand"),
        VideoChapter(220, "The Smile Superpower"),
        VideoChapter(320, "Squad Celebration")
      ),
      quizQuestion = "Which hand did the Prophet ﷺ teach us to eat and drink with?",
      quizOptions = listOf(
        "The right hand",
        "The left hand",
        "Both hands together"
      ),
      quizAnswerIndex = 0
    ),
    VideoItem(
      id = "tv_bilal_questions_1",
      title = "Who Painted the Stars?",
      series = "Bilal's Big Questions",
      category = "Islamic Adventures",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      duration = "10:45",
      durationSeconds = 645,
      thumbnailEmoji = "🌌",
      description = "Bilal stares up at the twinkling night sky through his backyard telescope and wonders about the wonders of creation, constellations, and how the Quran describes the universe.",
      keyTakeaway = "Reflecting on the magnificent creation of Allah strengthens our awe and faith in Al-Khaliq (The Creator).",
      chapters = listOf(
        VideoChapter(0, "Looking at Orion's Belt"),
        VideoChapter(180, "The Quranic Ayah of the Heavens"),
        VideoChapter(360, "The Perfect Balance of the Planets"),
        VideoChapter(540, "Saying SubhanAllah")
      ),
      quizQuestion = "What name of Allah means 'The Creator'?",
      quizOptions = listOf(
        "Al-Khaliq",
        "Ar-Rahman",
        "Al-Ghafoor"
      ),
      quizAnswerIndex = 0
    ),
    VideoItem(
      id = "tv_noor_kitchen_1",
      title = "Eid Date Truffles!",
      series = "Noor's Little Kitchen",
      category = "Short Learning Videos",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      duration = "7:20",
      durationSeconds = 440,
      thumbnailEmoji = "🧁",
      description = "Join chef Noor as she rolls delicious, healthy date truffles coated in shredded coconut and crushed pistachios to give to elderly neighbours on Eid day.",
      keyTakeaway = "Making healthy treats and gifting food to neighbours spreads love and follows the Sunnah.",
      chapters = listOf(
        VideoChapter(0, "Washing Hands & Bismillah"),
        VideoChapter(110, "Pitting the Sunnah Dates"),
        VideoChapter(250, "Rolling in Coconut Snow"),
        VideoChapter(380, "Delivering to Neighbours")
      ),
      quizQuestion = "What special fruit did the Prophet ﷺ love to break his fast with?",
      quizOptions = listOf(
        "Dates (Tamr)",
        "Watermelon",
        "Oranges"
      ),
      quizAnswerIndex = 0
    ),
    VideoItem(
      id = "tv_kaaba_history_1",
      title = "Building the Ka'bah with Ibrahim & Ismail",
      series = "Tales of the Prophets",
      category = "Islamic History",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      duration = "12:10",
      durationSeconds = 730,
      thumbnailEmoji = "🕋",
      description = "An inspiring historical animation documenting Prophet Ibrahim and his brave young son Ismail raising the stone foundations of the sacred Ka'bah in the valley of Bakkah.",
      keyTakeaway = "Devoting our energy and teamwork to worship Allah leaves a blessed landmark that endures for generations.",
      chapters = listOf(
        VideoChapter(0, "The Desert of Makkah"),
        VideoChapter(210, "Lifting the Heavy Stones"),
        VideoChapter(450, "The Prayer for Righteous Offspring"),
        VideoChapter(620, "The Call to Hajj")
      ),
      quizQuestion = "Who helped Prophet Ibrahim build the Ka'bah?",
      quizOptions = listOf(
        "His son Ismail",
        "His brother Harun",
        "Prophet Musa"
      ),
      quizAnswerIndex = 0
    ),
    VideoItem(
      id = "tv_ramadan_eid_special",
      title = "The Crescent Moon Festival",
      series = "Islamic Celebrations",
      category = "Special Ramadan and Eid Content",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      duration = "9:15",
      durationSeconds = 555,
      thumbnailEmoji = "🌙",
      description = "The exciting eve of Eid! Watch the children prepare Eid clothes, bake spiced cookies, pack charity baskets for orphans, and spot the silver crescent moon.",
      keyTakeaway = "Eid is a time of worship, gratitude, joy, and sharing with those less fortunate.",
      chapters = listOf(
        VideoChapter(0, "Sighting the Hilal"),
        VideoChapter(180, "Packing Zakat al-Fitr"),
        VideoChapter(360, "The Eid Morning Takbeerat"),
        VideoChapter(490, "Spreading Hugs and Smiles")
      ),
      quizQuestion = "What special charity is given before the Eid prayer?",
      quizOptions = listOf(
        "Zakat al-Fitr",
        "Waqf",
        "Kaffarah"
      ),
      quizAnswerIndex = 0
    )
  )

  val songs = listOf(
    SongItem(
      id = "song_5_pillars",
      title = "The 5 Pillars of Islam Song",
      artist = "My Muslim Kids Choir",
      duration = "2:45",
      category = "Salah and Worship",
      theme = "Foundational Beliefs",
      coverEmoji = "🕌",
      lyrics = listOf(
        "Five pillars build our deen so strong,",
        "Sing together, sing along!",
        "Number one: Shahadah, we declare,",
        "None worthy of worship but Allah everywhere!",
        "Number two: Salah, five times a day,",
        "Bowing to our Maker as we pray.",
        "Number three: Zakah, we give to the poor,",
        "Opening mercy's golden door.",
        "Number four: Sawm in Ramadan's light,",
        "Fasting all day from dawn till night.",
        "Number five: Hajj to Makkah we go,",
        "Walking in harmony, high and low!",
        "Five bright pillars, firm and true,",
        "Guiding me and guiding you!"
      ),
      educationalMessage = "Helps children memorize the Five Pillars of Islam with a catchy, joyful rhythm.",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      hasVideoVersion = true,
      isFavorite = true
    ),
    SongItem(
      id = "song_bismillah",
      title = "Bismillah Everywhere I Go",
      artist = "Little Explorers Melody",
      duration = "2:15",
      category = "Islamic Nursery Songs",
      theme = "Daily Habits & Duas",
      coverEmoji = "✨",
      lyrics = listOf(
        "When I wake up in the bright morning glow,",
        "Bismillah everywhere I go!",
        "When I take a crunchy bite of sweet toast,",
        "Bismillah is what I say the most!",
        "When I step outside to jump and play,",
        "Bismillah starts my happy day!",
        "In the name of Allah, so kind and near,",
        "Bringing peace and driving away fear."
      ),
      educationalMessage = "Encourages young kids to say Bismillah before all good deeds.",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      isFavorite = true
    ),
    SongItem(
      id = "song_arabic_letters",
      title = "The Arabic Alphabet Nasheed",
      artist = "Zayd & The Phonics Pals",
      duration = "3:10",
      category = "Arabic Alphabet Songs",
      theme = "Arabic Language",
      coverEmoji = "🔤",
      lyrics = listOf(
        "Alif, Baa, Taa, Thaa,",
        "Jeem, Haa, Khaa!",
        "Letters of the holy Quran,",
        "Learning them with joy, young and old!",
        "Daal, Thaal, Raa, Zay,",
        "Seen, Sheen, Saad, Daad,",
        "Taa, Zhaa, 'Ayn, Ghayn,",
        "Faa, Qaaf, Kaaf, Laam,",
        "Meem, Noon, Haa, Waaw, Yaa!",
        "Now I know my Arabic letters so sweet,",
        "Every single word is a special treat!"
      ),
      educationalMessage = "Rhythmic phonics nasheed teaching letter recognition and sequence.",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      hasVideoVersion = true
    ),
    SongItem(
      id = "song_99_names",
      title = "The 99 Beautiful Names of Allah",
      artist = "Noor & The Young Scholars",
      duration = "3:40",
      category = "Learning Allah's Beautiful Names",
      theme = "Tawhid & Asmaul Husna",
      coverEmoji = "🌟",
      lyrics = listOf(
        "Huwa Allahul-ladhee laa ilaaha illaa Huw,",
        "Ar-Rahman, Ar-Raheem, Al-Malik, Al-Quddoos!",
        "As-Salam, Al-Mu'min, Al-Muhaymin, Al-Aziz!",
        "The Creator, The Shaper, The Loving and True,",
        "Knowing His Names brings peace into you!",
        "Al-Khaliq, Al-Bari', Al-Musawwir,",
        "Al-Ghaffar, Al-Qahhar, Al-Wahhab, Ar-Razzaq!",
        "Remembering our Lord in morning and night,",
        "Filling our hearts with His heavenly light!"
      ),
      educationalMessage = "Introduces children to Asmaul Husna (the 99 Names of Allah) and their gentle meanings.",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      isFavorite = true
    ),
    SongItem(
      id = "song_smile_sadaqah",
      title = "Smile, It's a Charity!",
      artist = "Noor & Friends",
      duration = "2:30",
      category = "Good Manners and Character",
      theme = "Good Manners (Akhlaq)",
      coverEmoji = "😊",
      lyrics = listOf(
        "You don't need a golden coin in your hand,",
        "To give the best gift across the land!",
        "Just turn your corners up real wide,",
        "Let the warmth shine from inside!",
        "Tabassumuka fee wajhi akheek,",
        "A smile for your brother is what we seek!",
        "Sadaqah, charity from the heart,",
        "That's how friendly communities start!"
      ),
      educationalMessage = "Teaches the Hadith: 'Your smiling in the face of your brother is charity.'",
      targetAge = AgeGroup.LITTLE_EXPLORERS
    ),
    SongItem(
      id = "song_ramadan_moon",
      title = "Welcome Ramadan Moon (Hilal Song)",
      artist = "My Muslim Kids Family",
      duration = "2:50",
      category = "Ramadan and Eid Songs",
      theme = "Islamic Celebrations",
      coverEmoji = "🌙",
      lyrics = listOf(
        "Silver crescent in the starry night,",
        "Filling all our homes with holy light!",
        "Marhaban ya Ramadan, welcome blessed days,",
        "Fasting and praying, walking in good ways!",
        "Lanterns glowing on the balcony wall,",
        "Peace and mercy descending upon all!"
      ),
      educationalMessage = "Celebrates welcoming the blessed month of Ramadan with joy and good deeds.",
      targetAge = AgeGroup.YOUNG_LEARNERS
    )
  )

  val lessons = listOf(
    LessonItem(
      id = "lesson_knowing_allah",
      title = "Knowing Allah: The Creator of All",
      subject = "Knowing Allah and foundational beliefs",
      module = "Tawhid & Belief",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      summary = "Who is Allah? He is the One who created the shining stars, warm sun, green trees, and you!",
      iconEmoji = "☝️",
      funFact = "Allah has 99 Most Beautiful Names! Saying His Names with love fills our heart with joy.",
      estimatedMinutes = 5,
      learningObjective = "Understand that Allah is our Loving Creator who is One and has no partners.",
      introduction = "Look up at the blue sky and all the stars at night. Have you ever wondered who made everything around us?",
      explanation = "Allah is our Creator (Al-Khaliq). He is One, without father, mother, or children. He is always watching over us with love and mercy. Allah is Al-Qareeb, closer to us than anything, and He listens whenever we whisper a prayer.",
      everydayExample = "When you share your toys with a friend or smile at your grandmother, Allah sees your good deed and loves you for it!",
      practiceQuestion = "Who created the heavens, the earth, and all living things?",
      practiceOptions = listOf("Allah, the One Creator", "The planets", "Scientists", "Nobody"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Masha'Allah! Allah created everything in perfect harmony.",
      reviewKeyPoints = listOf(
        "Allah is One (Ahad) with no partners.",
        "He is Al-Khaliq (The Creator) of all things.",
        "Allah loves kind, honest, and helpful children."
      ),
      checkpoints = listOf(
        LessonCheckpoint("Allah is One", "Say: Qul Huwa Allahu Ahad", "قُلْ هُوَ اللَّهُ أَحَدٌ", "Qul Huwal-laahu Ahad", "☝️"),
        LessonCheckpoint("Allah Hears Us", "He hears every prayer we whisper", "السَّمِيعُ", "As-Samee'", "👂"),
        LessonCheckpoint("Allah is Loving", "His mercy reaches all living creatures", "الرَّحْمَٰنُ", "Ar-Rahmaan", "❤️")
      )
    ),
    LessonItem(
      id = "lesson_five_pillars",
      title = "The 5 Pillars of Islam",
      subject = "The Five Pillars of Islam",
      module = "Foundations",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "Discover the five strong pillars that hold up the wonderful house of Islam.",
      iconEmoji = "🏛️",
      funFact = "Just like a beautiful tent needs strong poles to stand tall, our faith stands strong on these 5 pillars!",
      estimatedMinutes = 8,
      learningObjective = "Memorize the five pillars of Islam and their daily significance.",
      introduction = "Imagine building a strong house that protects a family from the storm. What holds it up? Strong pillars!",
      explanation = "The Prophet Muhammad ﷺ taught: 'Islam is built on five pillars: 1. Shahadah (Declaration of faith), 2. Salah (Five daily prayers), 3. Zakat (Giving charity to the needy), 4. Sawm (Fasting in Ramadan), and 5. Hajj (Pilgrimage to Makkah for those able).' Together, they guide a Muslim to live with gratitude, kindness, and worship.",
      everydayExample = "When you pray Dhuhr, save pocket money for charity box, or fast part of the day in Ramadan, you are strengthening your pillars!",
      practiceQuestion = "Which pillar of Islam is performed five times every single day?",
      practiceOptions = listOf("Salah (Prayer)", "Zakat (Charity)", "Hajj (Pilgrimage)", "Sawm (Fasting)"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Excellent! Salah is the daily meeting connecting us to Allah five times a day.",
      reviewKeyPoints = listOf(
        "1. Shahadah — Believing in Allah and His Messenger ﷺ.",
        "2. Salah — Five daily prayers.",
        "3. Zakat — Helping the poor and needy.",
        "4. Sawm — Fasting the blessed month of Ramadan.",
        "5. Hajj — Visiting the holy Ka'bah once in a lifetime."
      ),
      checkpoints = listOf(
        LessonCheckpoint("1. Shahadah", "Declaration of Faith", "شَهَادَة", "Shahadah", "☝️"),
        LessonCheckpoint("2. Salah", "5 Daily Prayers", "صَلَاة", "Salah", "🕌"),
        LessonCheckpoint("3. Zakat", "Purifying Charity", "زَكَاة", "Zakat", "🪙"),
        LessonCheckpoint("4. Sawm", "Fasting in Ramadan", "صَوْم", "Sawm", "🌙"),
        LessonCheckpoint("5. Hajj", "Pilgrimage to Makkah", "حَجّ", "Hajj", "🕋")
      )
    ),
    LessonItem(
      id = "lesson_six_articles_iman",
      title = "The 6 Articles of Faith (Iman)",
      subject = "The Six Articles of Faith",
      module = "Iman & Belief",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "What lives in the heart of a believer? Believing in Allah, Angels, Books, Messengers, the Last Day, and Destiny.",
      iconEmoji = "🌟",
      funFact = "The Arabic word 'Iman' comes from safety and trust! When you have faith, your heart feels safe.",
      estimatedMinutes = 8,
      learningObjective = "Understand the six pillars of belief taught in Hadith Jibril.",
      introduction = "When Angel Jibril visited the Prophet ﷺ in white clothes, he asked: 'What is Iman?'",
      explanation = "Iman means firm belief in: 1. Allah, 2. His pure Angels made of light, 3. His Holy Books, 4. His Messengers, 5. The Day of Judgment where every good deed counts, and 6. Al-Qadar (Divine Decree), knowing Allah's wisdom guides all events.",
      everydayExample = "When things don't go as planned and you say 'Alhamdulillah, Allah has a better plan', you are living belief in Al-Qadar!",
      practiceQuestion = "From what material did Allah create the noble Angels?",
      practiceOptions = listOf("Pure Light (Noor)", "Clay", "Smokeless Fire", "Water"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Spot on! Angels were created from light and always carry out Allah's commands.",
      reviewKeyPoints = listOf(
        "Faith has 6 pillars rooted deeply in the heart.",
        "Angels like Jibril and Mika'il protect and carry Allah's decrees.",
        "Believing in the Last Day inspires us to do good deeds every day."
      ),
      checkpoints = listOf(
        LessonCheckpoint("1. Belief in Allah", "Tawhid: Allah is One", "لَا إِلَٰهَ إِلَّا اللَّهُ", "Laa ilaaha illallaah", "☝️"),
        LessonCheckpoint("2. Belief in Angels", "Beings of light who obey Allah", "الْمَلَائِكَةُ", "Al-Mala'ikah", "🪽"),
        LessonCheckpoint("3. Belief in Holy Books", "Tawrat, Zabur, Injeel, Quran", "الْكُتُبُ", "Al-Kutub", "📖"),
        LessonCheckpoint("4. Belief in Messengers", "From Adam to Muhammad ﷺ", "الرُّسُلُ", "Ar-Rusul", "🕊️"),
        LessonCheckpoint("5. Belief in the Last Day", "Reward for all righteous deeds", "الْيَوْمُ الآخِرُ", "Al-Yawmul-Aakhir", "⚖️"),
        LessonCheckpoint("6. Belief in Qadar", "Allah's perfect wisdom and destiny", "الْقَدَرُ", "Al-Qadar", "🌌")
      )
    ),
    LessonItem(
      id = "lesson_prophets_messengers",
      title = "Prophets & Messengers: Guides of Light",
      subject = "Prophets and Messengers",
      module = "Prophets & History",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "From Prophet Adam to Prophet Muhammad ﷺ, Allah sent brave, kind teachers to guide humanity.",
      iconEmoji = "🕊️",
      funFact = "25 Prophets are mentioned by name in the holy Quran, and all called people to worship One Allah!",
      estimatedMinutes = 7,
      learningObjective = "Understand the noble role of the Prophets and their virtues of truthfulness and patience.",
      introduction = "Whenever people forgot how to be kind and just, Allah sent a Prophet to show the way back to the light.",
      explanation = "All Prophets were truthful (Sadiq), trustworthy (Ameen), and deeply compassionate. Prophet Nuh taught patience, Prophet Ibrahim taught courage in Tawhid, Prophet Musa spoke with Allah, Prophet Isa brought healing and love, and Prophet Muhammad ﷺ is the final Seal of the Prophets sent as a mercy to all creation.",
      everydayExample = "When someone hurts your feelings and you choose to forgive them with a smile, you are following the character of the Prophets!",
      practiceQuestion = "Who was the very first human being and the first Prophet of Allah?",
      practiceOptions = listOf("Prophet Adam (peace be upon him)", "Prophet Ibrahim", "Prophet Nuh", "Prophet Musa"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Barakallahu Feek! Prophet Adam was the first man and first Prophet created by Allah.",
      reviewKeyPoints = listOf(
        "Prophets were chosen role models with the best manners.",
        "They never claimed to be gods; they worshiped Allah alone.",
        "Prophet Muhammad ﷺ is the final Messenger for all mankind."
      ),
      checkpoints = listOf(
        LessonCheckpoint("Trustworthy (Ameen)", "They always told the absolute truth", null, null, "🛡️"),
        LessonCheckpoint("Mercy to All", "Prophet Muhammad ﷺ was Rahmah lil-'Alameen", "رَحْمَةً لِّلْعَالَمِينَ", "Rahmatan lil-'aalameen", "❤️")
      )
    ),
    LessonItem(
      id = "lesson_seerah_childhood",
      title = "Seerah: The Blessed Life of Prophet Muhammad ﷺ",
      subject = "Seerah",
      module = "Seerah & Sunnah",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      summary = "Explore how the Prophet ﷺ grew up in Makkah known by all as Al-Sadiq (the Truthful) and Al-Ameen (the Trustworthy).",
      iconEmoji = "📜",
      funFact = "Even before becoming a Prophet, people in Makkah gave him their most valuable treasures to hold because of his honesty!",
      estimatedMinutes = 10,
      learningObjective = "Appreciate the honesty, mercy, and gentleness of the Prophet ﷺ.",
      introduction = "How did an orphan boy in the desert mountains of Makkah become the most beloved leader in human history?",
      explanation = "Born in the Year of the Elephant in Makkah, Muhammad ﷺ loved caring for sheep and contemplation. In cave Hira, the first verses of Surah Al-'Alaq ('Read! In the name of your Lord who created') were revealed by Angel Jibril. He spent his entire life helping widows, feeding the hungry, and greeting children with warm smiles.",
      everydayExample = "Whenever you return a borrowed pencil to a classmate, you are acting with Amanah (trustworthiness) like the Prophet ﷺ!",
      practiceQuestion = "What special title did the people of Makkah give to young Muhammad ﷺ for his honesty?",
      practiceOptions = listOf("Al-Ameen (The Trustworthy)", "The Mighty Warrior", "The Golden Merchant", "The Poet"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Masha'Allah! Everyone trusted him completely and called him Al-Ameen.",
      reviewKeyPoints = listOf(
        "The Prophet ﷺ was kind to young children, animals, and trees.",
        "He spoke softly and never shouted or insulted anyone.",
        "Loving him means following his beautiful Sunnah daily."
      ),
      checkpoints = listOf(
        LessonCheckpoint("Cave Hira", "Where revelation first descended", "غَار حِرَاء", "Ghaar Hiraa", "⛰️"),
        LessonCheckpoint("Spread Peace", "Teachings of kindness and brotherhood", "أَفْشُوا السَّلَامَ", "Afshus-Salaam", "🕊️")
      )
    ),
    LessonItem(
      id = "lesson_islamic_history_heritage",
      title = "Islamic History: The Golden Age of Discovery",
      subject = "Islamic History",
      module = "History & Contributions",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      summary = "Discover how Muslim scholars invented algebra, designed flying gliders, built hospitals, and mapped the stars!",
      iconEmoji = "🔭",
      funFact = "The world's oldest existing university, Al-Qarawiyyin, was founded by a Muslim woman named Fatima al-Fihri in Fez, Morocco in 859 CE!",
      estimatedMinutes = 9,
      learningObjective = "Appreciate how faith inspired Muslim scholars to pioneer science, medicine, and mathematics.",
      introduction = "Did you know the camera in your tablet comes from an idea discovered by a Muslim scientist named Ibn al-Haytham?",
      explanation = "Inspired by Quranic verses calling us to ponder the universe, Muslim scientists made breathtaking breakthroughs. Al-Khwarizmi invented Algebra (Al-Jabr), Maryam al-Ijliya crafted precision astrolabes to navigate across oceans, and Ibn Sina wrote medicine encyclopedias studied for hundreds of years across the globe.",
      everydayExample = "When you do your maths homework or gaze at the stars with curiosity, you are carrying forward our rich heritage!",
      practiceQuestion = "What field of mathematics did Muslim scholar Al-Khwarizmi invent?",
      practiceOptions = listOf("Algebra (Al-Jabr)", "Geometry", "Calculus", "Roman Numerals"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Correct! The word 'Algebra' comes from his famous book Kitab al-Jabr!",
      reviewKeyPoints = listOf(
        "Islam encourages curiosity, reading, and learning science.",
        "Muslim scholars built libraries, observatories, and free hospitals.",
        "Boys and girls can become great scientists and benefit humanity."
      ),
      checkpoints = listOf(
        LessonCheckpoint("Seek Knowledge", "Seeking knowledge is an obligation upon every Muslim", "طَلَبُ الْعِلْمِ فَرِيضَةٌ", "Talabul-'ilmi fareedah", "📚"),
        LessonCheckpoint("Fatima al-Fihri", "Founded the first university", null, null, "🏛️")
      )
    ),
    LessonItem(
      id = "lesson_salah_journey",
      title = "The Journey of Salah (Prayer)",
      subject = "Salah",
      module = "Pillars & Worship",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "Salah is our direct meeting with Allah 5 times a day. Learn the postures, words, and meanings of prayer.",
      iconEmoji = "🕌",
      funFact = "When you bow in Sujud (prostration), you are closer to Allah than at any other moment!",
      estimatedMinutes = 8,
      learningObjective = "Master the five daily prayers, their timings, and posture sequence.",
      introduction = "Imagine having a private appointment with the King of kings whenever you need peace or help. That is Salah!",
      explanation = "Muslims pray five times each day: Fajr at dawn, Dhuhr at midday, Asr in the afternoon, Maghrib at sunset, and Isha at night. We stand with humility (Qiyam), bow with reverence (Ruku), prostrate with devotion (Sujud), and end with warm peace to the right and left (Tasleem).",
      everydayExample = "Hearing the Adhan call to prayer is your invitation to pause games, wash peacefully, and stand before Allah.",
      practiceQuestion = "How many obligatory prayers do Muslims perform each day?",
      practiceOptions = listOf("5 prayers", "3 prayers", "7 prayers", "1 prayer"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Well done! The 5 daily prayers are Fajr, Dhuhr, Asr, Maghrib, and Isha.",
      reviewKeyPoints = listOf(
        "Facing the Qiblah in Makkah with a clean body and heart.",
        "Reciting Surah Al-Fatihah with sweet contemplation.",
        "Salah washes away stress and fills the heart with serenity."
      ),
      checkpoints = listOf(
        LessonCheckpoint("1. Takbiratul Ihram", "Allahu Akbar to begin", "اللَّهُ أَكْبَرُ", "Allahu Akbar", "🙌"),
        LessonCheckpoint("2. Qiyam", "Reciting Surah Al-Fatihah", "الْحَمْدُ لِلَّهِ", "Alhamdu lillaah", "📖"),
        LessonCheckpoint("3. Ruku", "Bowing to our Creator", "سُبْحَانَ رَبِّيَ الْعَظِيمِ", "Subhaana Rabbiyal-'Adheem", "🙇"),
        LessonCheckpoint("4. Sujud", "Closest to Allah", "سُبْحَانَ رَبِّيَ الأَعْلَى", "Subhaana Rabbiyal-A'laa", "🙏"),
        LessonCheckpoint("5. Tasleem", "Spreading peace to left and right", "السَّلَامُ عَلَيْكُمْ", "Assalaamu 'alaykum", "🕊️")
      )
    ),
    LessonItem(
      id = "lesson_wudu_mastery",
      title = "How to Make Wudu Step-by-Step",
      subject = "Wudu",
      module = "Pillars & Worship",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      summary = "Cleanliness is half of faith! Learn the exact steps of ablution before our prayers.",
      iconEmoji = "💧",
      funFact = "The water droplets from Wudu wash away minor mistakes and make the faces of believers shine with light on the Day of Judgment!",
      estimatedMinutes = 6,
      learningObjective = "Perform ablution (Wudu) in the correct authentic Sunnah sequence without wasting water.",
      introduction = "Before we meet Allah in prayer, we wash ourselves with cool, pure water to feel fresh and clean.",
      explanation = "Start with Niyyah in the heart and say Bismillah. Wash your hands 3 times, rinse mouth 3 times, clean nose 3 times, wash face 3 times, wash arms past elbows (right then left) 3 times, wipe head and ears once, and wash feet past ankles (right then left) 3 times. Be gentle and never waste water!",
      everydayExample = "Even if you are making Wudu near a rushing river, the Prophet ﷺ advised never to waste water unnecessarily.",
      practiceQuestion = "What should we whisper before starting Wudu?",
      practiceOptions = listOf("Bismillah (In the name of Allah)", "Allahu Akbar", "SubhanAllah", "Astaghfirullah"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Masha'Allah! Starting with Bismillah brings Allah's blessing into our ablution.",
      reviewKeyPoints = listOf(
        "Wudu purifies body and soul before standing in prayer.",
        "Always start with the right side before the left.",
        "Conserve water—every single drop is a precious gift."
      ),
      checkpoints = listOf(
        LessonCheckpoint("1. Niyyah & Bismillah", "Make intention in your heart", "بِسْمِ اللَّهِ", "Bismillaah", "❤️"),
        LessonCheckpoint("2. Wash Hands", "Up to wrists 3 times", null, null, "🖐️"),
        LessonCheckpoint("3. Rinse Mouth & Nose", "3 times gently", null, null, "👄"),
        LessonCheckpoint("4. Wash Face", "Forehead to chin 3 times", null, null, "😊"),
        LessonCheckpoint("5. Wash Arms", "Past elbows, right then left", null, null, "💪"),
        LessonCheckpoint("6. Wipe Head & Ears", "Once with wet hands", null, null, "👂"),
        LessonCheckpoint("7. Wash Feet", "Past ankles, right then left", null, null, "🦶")
      )
    ),
    LessonItem(
      id = "lesson_honoring_parents",
      title = "Birr al-Walidayn: Honoring Parents",
      subject = "Islamic Manners and Character",
      module = "Akhlaq & Manners",
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      summary = "Why honoring mother and father is one of the highest deeds in Islam, with practical everyday ways to make them smile.",
      iconEmoji = "❤️",
      funFact = "Paradise lies under the feet of mothers! A companion asked the Prophet ﷺ: 'Who is most worthy of my good companionship?' He replied: 'Your mother' three times!",
      estimatedMinutes = 8,
      learningObjective = "Practice continuous kindness, obedience, and gentleness with parents.",
      introduction = "Who stayed awake when you were a tiny baby crying at night? Who taught you how to walk and talk with love?",
      explanation = "Allah says in Surah Al-Isra: 'And your Lord has decreed that you worship none but Him, and that you be kind to parents.' Even saying 'uff' or rolling eyes is not allowed. We honor them by listening, making their chores easier, speaking politely, and praying for their well-being daily.",
      everydayExample = "Surprise your parents today by picking up your shoes, making your bed, or bringing them a fresh glass of water with a smile!",
      practiceQuestion = "What special dua did Allah teach us in the Quran to pray for our parents?",
      practiceOptions = listOf(
        "Rabbir-hamhumaa kamaa rabbayaanee sagheeraa",
        "Rabbana aatina fid-dunya hasanah",
        "Rabbi zidnee 'ilmaa",
        "Bismillahi tawakkaltu 'alallah"
      ),
      practiceCorrectIndex = 0,
      practiceExplanation = "SubhanAllah! It means: 'My Lord, have mercy upon them both as they raised me when I was small.'",
      reviewKeyPoints = listOf(
        "Parents are our gateway to Paradise (Jannah).",
        "Never raise your voice or argue disrespectfully.",
        "Make dua for them every single day after your prayers."
      ),
      checkpoints = listOf(
        LessonCheckpoint("Speak with Softness", "Never say 'uff' or show anger", null, null, "🗣️"),
        LessonCheckpoint("Help Willingly", "Help without waiting to be asked", null, null, "🧹"),
        LessonCheckpoint("Continuous Dua", "Pray for their health and Jannah", "رَبِّ ارْحَمْهُمَا", "Rabbir-hamhumaa", "🤲")
      )
    ),
    LessonItem(
      id = "lesson_halal_haram_knowledge",
      title = "Basic Islamic Knowledge: Halal & Haram",
      subject = "Basic Islamic Knowledge",
      module = "Islamic Lifestyle",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      summary = "Learn what is pure and wholesome (Halal) and what Allah protects us from (Haram).",
      iconEmoji = "⚖️",
      funFact = "The Arabic word 'Halal' means permissible and pure. Most things in Allah's beautiful earth are Halal for us to enjoy!",
      estimatedMinutes = 7,
      learningObjective = "Distinguish between Halal (beneficial and permitted) and Haram (harmful and prohibited).",
      introduction = "When a caring parent says, 'Don't touch that hot oven!', they say it to protect you because they love you. Allah does the same!",
      explanation = "Halal means everything Allah has allowed because it is good, healthy, and clean for us—like delicious fruits, honest trading, kindness, and clean water. Haram means things Allah forbade because they harm our bodies or hearts—like lying, stealing, cheating, or hurting others. Saying Bismillah makes our food blessed!",
      everydayExample = "When you choose to tell the truth even when you made an accidental mistake, you chose the Halal, noble path!",
      practiceQuestion = "What does the word 'Halal' mean in Islam?",
      practiceOptions = listOf("Permissible, pure, and good", "Forbidden and bad", "Difficult", "Only for grown-ups"),
      practiceCorrectIndex = 0,
      practiceExplanation = "Masha'Allah! Halal is everything pure, permissible, and wholesome that Allah blessed for us.",
      reviewKeyPoints = listOf(
        "Allah only forbids things that harm us or society.",
        "Consuming Halal and earning honest income brings peace.",
        "Always check food ingredients and choose clean, healthy snacks."
      ),
      checkpoints = listOf(
        LessonCheckpoint("Tayyib (Pure)", "Eat from the good, clean things", "حَلَالًا طَيِّبًا", "Halaalan Tayyibaa", "🍎"),
        LessonCheckpoint("Honesty in Deeds", "Truthfulness leads to righteousness", null, null, "⭐")
      )
    )
  )

  val gameCards = listOf(
    GameCardItem(
      id = "game_trivia_grand",
      title = "Islamic Super Scholar Quiz",
      category = "Islamic Knowledge Quizzes",
      gameplayType = GameplayType.MULTIPLE_CHOICE,
      targetAge = AgeGroup.YOUNG_LEARNERS,
      difficulty = "Medium",
      description = "Test your knowledge on the Prophets, holy books, pillars, and holy cities!",
      illustrationEmoji = "🎓",
      starReward = 15
    ),
    GameCardItem(
      id = "game_arabic_letter_sound",
      title = "Arabic Alphabet Phonics Match",
      category = "Arabic Alphabet Games",
      gameplayType = GameplayType.MATCHING_PAIRS,
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      difficulty = "Easy",
      description = "Match Arabic letters to their friendly animal friends and sweet sounds!",
      illustrationEmoji = "🔤",
      starReward = 10
    ),
    GameCardItem(
      id = "game_islamic_terms_match",
      title = "Islamic Terms & Meanings Matcher",
      category = "Islamic Matching Games",
      gameplayType = GameplayType.MATCHING_PAIRS,
      targetAge = AgeGroup.YOUNG_LEARNERS,
      difficulty = "Medium",
      description = "Match key Islamic words like Iman, Ihsan, Salah, and Zakat to what they mean.",
      illustrationEmoji = "🧩",
      starReward = 15
    ),
    GameCardItem(
      id = "game_wudu_sequence",
      title = "Wudu Step-by-Step Sequence",
      category = "Wudu and Salah Activities",
      gameplayType = GameplayType.STEP_ORDERING,
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      difficulty = "Easy",
      description = "Arrange the 8 steps of Wudu in the exact Sunnah sequence from hands to feet!",
      illustrationEmoji = "💧",
      starReward = 15
    ),
    GameCardItem(
      id = "game_memory_islamic_symbols",
      title = "Islamic Memory Card Match",
      category = "Memory Games",
      gameplayType = GameplayType.MEMORY_MATCH,
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      difficulty = "Easy",
      description = "Flip cards and uncover matching pairs of the Ka'bah, Quran, Crescent, and Mosque!",
      illustrationEmoji = "🃏",
      starReward = 10
    ),
    GameCardItem(
      id = "game_knowledge_puzzle",
      title = "Holy Ka'bah & Masjid Puzzle",
      category = "Islamic Puzzles",
      gameplayType = GameplayType.PICTURE_PUZZLE,
      targetAge = AgeGroup.YOUNG_LEARNERS,
      difficulty = "Medium",
      description = "Solve Islamic trivia clues to reveal the sacred Masjid al-Haram illustration!",
      illustrationEmoji = "🖼️",
      starReward = 20
    ),
    GameCardItem(
      id = "game_good_deeds_challenge",
      title = "Sunnah Daily Deeds Challenge",
      category = "Good-Deeds Challenges",
      gameplayType = GameplayType.MULTIPLE_CHOICE,
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      difficulty = "Easy",
      description = "Choose the kindest Sunnah reaction for everyday scenarios at school and home.",
      illustrationEmoji = "⭐",
      starReward = 10
    ),
    GameCardItem(
      id = "game_treasure_hunt",
      title = "Makkah to Madinah Treasure Hunt",
      category = "Islamic Treasure Hunts",
      gameplayType = GameplayType.MULTIPLE_CHOICE,
      targetAge = AgeGroup.YOUNG_SCHOLARS,
      difficulty = "Explorer",
      description = "Follow clues along the historic Hijrah migration trail to uncover golden badges!",
      illustrationEmoji = "🗺️",
      starReward = 25
    )
  )

  val learningAdventures = listOf(
    LearningAdventure(
      id = "adv_good_manners",
      title = "The Good Manners Challenge",
      theme = "Akhlaq & Kindness",
      iconEmoji = "🤝",
      description = "Join Zayd & Maryam on a quest to spread warmth, smiles, and polite words throughout the neighborhood!",
      activities = listOf(
        AdventureActivity("act_manners_1", "Learn the Hadith on smiling as charity", "Lesson", true),
        AdventureActivity("act_manners_2", "Play the Good Deeds Choice Game", "Game", false),
        AdventureActivity("act_manners_3", "Practice saying Jazakallahu Khayran", "Dua", false)
      ),
      rewardBadgeName = "Kindness Explorer ⭐",
      starReward = 30
    ),
    LearningAdventure(
      id = "adv_arabic_letters",
      title = "The Arabic Letter Adventure",
      theme = "Arabic Language",
      iconEmoji = "🔤",
      description = "Journey through the enchanted Oasis of Letters to discover all 28 Arabic letters from Alif to Yaa!",
      activities = listOf(
        AdventureActivity("act_arabic_1", "Explore letters Alif to Khaa", "Alphabet", true),
        AdventureActivity("act_arabic_2", "Match letters to sounds in the Phonics Game", "Game", false),
        AdventureActivity("act_arabic_3", "Recite the Alphabet Nasheed", "Song", false)
      ),
      rewardBadgeName = "Arabic Champion 🔤",
      starReward = 30
    ),
    LearningAdventure(
      id = "adv_islamic_quest",
      title = "The Islamic Knowledge Quest",
      theme = "Foundations of Faith",
      iconEmoji = "🧭",
      description = "Embark on an epic quest to master the 5 Pillars of Islam and the 6 Articles of Iman!",
      activities = listOf(
        AdventureActivity("act_quest_1", "Complete the 5 Pillars builder lesson", "Classroom", true),
        AdventureActivity("act_quest_2", "Beat the Grand Trivia Challenge with 100%", "Quiz", false),
        AdventureActivity("act_quest_3", "Recite Surah Al-Ikhlas with meaning", "Quran", false)
      ),
      rewardBadgeName = "Grand Scholar 🎓",
      starReward = 40
    ),
    LearningAdventure(
      id = "adv_kindness_mission",
      title = "The Kindness Mission",
      theme = "Family & Parents",
      iconEmoji = "❤️",
      description = "A heartwarming mission to honor parents, assist siblings, and help neighbours with joyful acts.",
      activities = listOf(
        AdventureActivity("act_kindness_1", "Learn the Birr al-Walidayn lesson", "Classroom", true),
        AdventureActivity("act_kindness_2", "Recite the special Dua for parents", "Dua", false),
        AdventureActivity("act_kindness_3", "Complete 3 Sunnah habits today", "Sunnah", false)
      ),
      rewardBadgeName = "Loving Helper ❤️",
      starReward = 35
    ),
    LearningAdventure(
      id = "adv_wudu_adventure",
      title = "The Wudu Learning Adventure",
      theme = "Purity & Salah",
      iconEmoji = "💧",
      description = "Step into the soothing fountain courtyard to learn the complete purification steps before Salah.",
      activities = listOf(
        AdventureActivity("act_wudu_1", "Walk through the Wudu step-by-step guide", "Classroom", true),
        AdventureActivity("act_wudu_2", "Solve the 8-step Wudu ordering puzzle", "Game", false),
        AdventureActivity("act_wudu_3", "Learn the Dua after Wudu", "Dua", false)
      ),
      rewardBadgeName = "Purity Whiz 💧",
      starReward = 30
    )
  )

  // Interactive Match Pairs Dataset
  data class MatchingPair(val id: String, val leftItem: String, val rightItem: String, val category: String)

  val matchingPairs = listOf(
    MatchingPair("m_1", "أ (Alif)", "Arnab (Rabbit) 🐰", "Arabic Alphabet"),
    MatchingPair("m_2", "ب (Baa)", "Bayt (House) 🏠", "Arabic Alphabet"),
    MatchingPair("m_3", "ت (Taa)", "Tuffah (Apple) 🍎", "Arabic Alphabet"),
    MatchingPair("m_4", "ج (Jeem)", "Jamal (Camel) 🐪", "Arabic Alphabet"),
    MatchingPair("m_5", "Shahadah", "Declaring Allah is One ☝️", "Islamic Terms"),
    MatchingPair("m_6", "Salah", "5 Daily Prayers 🕌", "Islamic Terms"),
    MatchingPair("m_7", "Zakat", "Giving to the Needy 🪙", "Islamic Terms"),
    MatchingPair("m_8", "Sawm", "Fasting in Ramadan 🌙", "Islamic Terms"),
    MatchingPair("m_9", "Before Eating", "Bismillah 🍽️", "Duas in Situations"),
    MatchingPair("m_10", "After Eating", "Alhamdulillah 🥣", "Duas in Situations"),
    MatchingPair("m_11", "Leaving Home", "Tawakkaltu 'alallah 🚪", "Duas in Situations"),
    MatchingPair("m_12", "Prayer Rug", "Clean place for Salah 🕌", "Objects & Uses"),
    MatchingPair("m_13", "Miswak", "Natural toothstick for Cleanliness 🌿", "Objects & Uses"),
    MatchingPair("m_14", "Qur'an", "Sacred guidance from Allah 📖", "Objects & Uses")
  )

  // Step Ordering Data
  data class StepItem(val id: String, val stepNumber: Int, val title: String, val arabicText: String? = null, val emoji: String)

  val wuduSteps = listOf(
    StepItem("w_1", 1, "Make Niyyah & say Bismillah", "بِسْمِ اللَّهِ", "❤️"),
    StepItem("w_2", 2, "Wash both hands up to wrists 3x", null, "🖐️"),
    StepItem("w_3", 3, "Rinse mouth gently 3x", null, "👄"),
    StepItem("w_4", 4, "Sniff and blow water from nose 3x", null, "👃"),
    StepItem("w_5", 5, "Wash full face 3x", null, "😊"),
    StepItem("w_6", 6, "Wash arms past elbows 3x (right then left)", null, "💪"),
    StepItem("w_7", 7, "Wipe head and inside ears once", null, "👂"),
    StepItem("w_8", 8, "Wash feet past ankles 3x (right then left)", null, "🦶")
  )

  val badges = listOf(
    BadgeItem("badge_story_explorer", "Story Explorer", "Explored inspiring stories of the Prophets and Companions", "📖", true, 1.0f, "Stories"),
    BadgeItem("badge_arabic_star", "Arabic Letter Star", "Discovered all 28 Arabic letters & word shapes", "🔤", true, 1.0f, "Quran & Arabic"),
    BadgeItem("badge_dua_learner", "Dua Learner", "Practiced and memorized essential daily duas", "🤲", true, 1.0f, "Duas"),
    BadgeItem("badge_kindness_champion", "Kindness Champion", "Completed acts of kindness & Sunnah habits", "❤️", true, 1.0f, "Sunnah"),
    BadgeItem("badge_islamic_explorer", "Islamic Knowledge Explorer", "Completed foundational lessons in Islamic Classroom", "🧭", true, 1.0f, "Classroom"),
    BadgeItem("badge_quran_star", "Quran Learning Star", "Recited short Surahs in Repeat-After-Reciter mode", "🌙", false, 0.75f, "Quran & Arabic"),
    BadgeItem("badge_good_manners", "Good Manners Champion", "Finished The Good Manners Challenge quest", "🤝", false, 0.67f, "Adventures"),
    BadgeItem("badge_wudu_whiz", "Wudu Whiz", "Arranged all 8 steps of Wudu cleanly", "💧", true, 1.0f, "Puzzles")
  )

  val learningJourney = listOf(
    LearningJourneyEntry("lj_1", "Prophet Yunus & the Whale", "Story", "📖", "Completed • Audio & Quiz", 15),
    LearningJourneyEntry("lj_2", "How to Make Wudu Step-by-Step", "Lesson", "💧", "Completed • 5 Steps", 15),
    LearningJourneyEntry("lj_3", "Arabic Alphabet Phonics Match", "Game", "🔤", "Completed • 4 Pairs", 10),
    LearningJourneyEntry("lj_4", "Surah Al-Ikhlas Recitation", "Quran", "🌙", "Practiced • Repeat Mode", 10),
    LearningJourneyEntry("lj_5", "Before Eating & Waking Up Duas", "Dua", "🤲", "Memorized • Authentic Sunnah", 10),
    LearningJourneyEntry("lj_6", "The Pillars of Islam Nasheed", "Song", "🎵", "Listened • Sing-along", 5)
  )

  val defaultWeeklyGoals = listOf(
    WeeklyLearningGoal("wg_stories", "Islamic Stories", "📖", 2, 3, 0xFF42B9D4),
    WeeklyLearningGoal("wg_lessons", "Classroom Lessons", "🏫", 2, 2, 0xFF20A982),
    WeeklyLearningGoal("wg_quran", "Quran & Arabic", "🌙", 3, 4, 0xFFA88AE8),
    WeeklyLearningGoal("wg_duas", "Daily Duas & Sunnah", "🤲", 3, 3, 0xFFFFC94D)
  )

  val signedInDevices = listOf(
    SignedInDevice("dev_1", "Family Galaxy Tab S9", "Android App", "Active now", isCurrentDevice = true),
    SignedInDevice("dev_2", "Fatima's Pixel 8", "Android App", "Yesterday, 7:45 PM", isCurrentDevice = false),
    SignedInDevice("dev_3", "Home Browser (Chrome)", "Web Platform", "3 days ago", isCurrentDevice = false)
  )

  val paymentTransactions = listOf(
    PaymentTransaction("tx_104", "12 Nov 2026", "Premium Monthly (Family)", "$7.99 USD", "Paid", "GP-9841-2026"),
    PaymentTransaction("tx_103", "12 Oct 2026", "Premium Monthly (Family)", "$7.99 USD", "Paid", "GP-8120-2026"),
    PaymentTransaction("tx_102", "12 Sep 2026", "Free Trial Activation", "$0.00 USD", "Paid", "GP-7004-2026")
  )

  val triviaQuestions = listOf(
    TriviaQuestion(
      question = "What is the very first month of the Islamic Hijri calendar?",
      options = listOf("Muharram", "Ramadan", "Shawwal", "Dhul Hijjah"),
      correctIndex = 0,
      explanation = "Muharram is the first month of the Islamic lunar calendar and one of the four sacred months."
    ),
    TriviaQuestion(
      question = "How many times a day do Muslims pray the obligatory Salah?",
      options = listOf("3 times", "5 times", "7 times", "2 times"),
      correctIndex = 1,
      explanation = "Muslims pray five times daily: Fajr, Dhuhr, Asr, Maghrib, and Isha."
    ),
    TriviaQuestion(
      question = "Which angel brought the revelations of the Quran to Prophet Muhammad ﷺ?",
      options = listOf("Angel Mika'il", "Angel Jibril (Gabriel)", "Angel Israfil", "Angel Malik"),
      correctIndex = 1,
      explanation = "Angel Jibril (peace be upon him) was entrusted by Allah to deliver revelations to the Prophets."
    ),
    TriviaQuestion(
      question = "In which city is the holy Ka'bah located?",
      options = listOf("Madinah", "Jerusalem", "Makkah", "Cairo"),
      correctIndex = 2,
      explanation = "The holy Ka'bah is in Makkah, Saudi Arabia, built originally by Prophet Ibrahim and Ismail."
    ),
    TriviaQuestion(
      question = "What special greeting do Muslims share when meeting each other?",
      options = listOf("As-salamu alaykum", "Marhaban", "Sabah al-khayr", "Ahlan"),
      correctIndex = 0,
      explanation = "'As-salamu alaykum' means 'Peace be upon you' and spreads warmth and blessings."
    )
  )

  val contentReviewItems = listOf(
    ContentReviewItem(
      id = "rev_1",
      title = "Story: Prophet Ibrahim & the Cool Fire",
      type = "Story",
      category = "Stories of the Prophets",
      shortDescription = "Teaches young hearts about unwavering trust in Allah (Tawakkul) using respectful landscape art.",
      mainContentBody = "Prophet Ibrahim (peace be upon him) looked up at the stars, moon, and sun and knew only Allah, the Creator of all, deserves worship...",
      submittedBy = "Ustadh Ahmad (Islamic Studies Lead)",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      language = "English / Arabic",
      durationLabel = "5 min read",
      sourceReference = "Surah Al-Anbiya 21:68–69 • Tafsir Ibn Kathir",
      islamicAccuracyStatus = "Verified by Scholar",
      arabicChecked = true,
      respectfulIllustrationChecked = true,
      workflowStatus = ReviewWorkflowStatus.PUBLISHED,
      status = "Published",
      version = 3,
      reviewerComment = "Prophet non-depiction rule verified; warm narrative suitable for ages 7–9.",
      approvedBy = "Shaykh Yusuf Al-Amin (Senior Reviewer)",
      approvedTimestamp = "10 Nov 2026 • 14:20 UTC",
      stages = listOf(
        ReviewStageCheck(1, "Production Review", true, "Studio QA", "Audio levels crisp, assets sharp."),
        ReviewStageCheck(2, "Islamic Review", true, "Shaykh Yusuf", "Quranic references verified."),
        ReviewStageCheck(3, "Visual & Audio Review", true, "Art Director", "Respectful environmental art."),
        ReviewStageCheck(4, "Final Approval", true, "Admin Lead", "Published to live library.")
      )
    ),
    ContentReviewItem(
      id = "rev_2",
      title = "Cartoons: Zayd & Maryam Ep 3 - The Shared Iftar",
      type = "Cartoon",
      category = "Ramadan & Eid",
      shortDescription = "Zayd and Maryam prepare healthy dates and share dinner with their elderly neighbor.",
      mainContentBody = "Animated 1080p episode with English, Arabic, and French subtitles.",
      submittedBy = "Studio Creative Team",
      targetAge = AgeGroup.YOUNG_LEARNERS,
      language = "English",
      durationLabel = "6:45 min",
      sourceReference = "Hadith on feeding neighbours (Sahih Muslim)",
      islamicAccuracyStatus = "Verified by Scholar",
      arabicChecked = true,
      respectfulIllustrationChecked = true,
      workflowStatus = ReviewWorkflowStatus.SCHEDULED,
      status = "Scheduled",
      version = 2,
      scheduledDate = "15 Nov 2026 • 09:00 UTC",
      reviewerComment = "Approved for scheduled Ramadan Prep release.",
      approvedBy = "Ustadha Amina",
      approvedTimestamp = "11 Nov 2026 • 10:05 UTC",
      stages = listOf(
        ReviewStageCheck(1, "Production Review", true, "Video Ops", "1080p HLS & 3 subtitle tracks ready."),
        ReviewStageCheck(2, "Islamic Review", true, "Ustadha Amina", "Hadith citation accurate."),
        ReviewStageCheck(3, "Visual & Audio Review", true, "Audio Lead", "Clean voice mixing, no distractions."),
        ReviewStageCheck(4, "Final Approval", true, "Admin Lead", "Scheduled for release.")
      )
    ),
    ContentReviewItem(
      id = "rev_3",
      title = "Nasheed: Gratitude in My Heart (Alhamdulillah)",
      type = "Song",
      category = "Islamic Nursery Songs",
      shortDescription = "Joyful vocal & duff-only nasheed celebrating everyday blessings from Allah.",
      mainContentBody = "Alhamdulillah for the morning sky, Alhamdulillah for the birds that fly...",
      submittedBy = "Media Sound Lab",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      language = "English / Arabic",
      durationLabel = "2:40 min",
      sourceReference = "Islamic Acoustic Guidelines v1",
      islamicAccuracyStatus = "In Islamic Review",
      arabicChecked = true,
      respectfulIllustrationChecked = true,
      workflowStatus = ReviewWorkflowStatus.IN_REVIEW,
      status = "In Review",
      version = 1,
      reviewerComment = "Stage 1 passed. Awaiting Stage 2 Arabic pronunciation check for 'Alhamdulillah'.",
      stages = listOf(
        ReviewStageCheck(1, "Production Review", true, "Sound Engineer", "Duff & vocal balance verified."),
        ReviewStageCheck(2, "Islamic Review", false, "Pending Scholar", "Reviewing lyric clarity."),
        ReviewStageCheck(3, "Visual & Audio Review", true, "Child Psychologist", "Gentle tempo for ages 4–6."),
        ReviewStageCheck(4, "Final Approval", false, "Pending", "Awaiting Stage 2 sign-off.")
      )
    ),
    ContentReviewItem(
      id = "rev_4",
      title = "Lesson: Etiquette of Visiting Neighbours",
      type = "Lesson",
      category = "Islamic Manners and Character",
      shortDescription = "5-step structured lesson on knocking gently, smiling, and respecting privacy.",
      mainContentBody = "Introduction, Explanation, Everyday Example, Practice Quiz, Review.",
      submittedBy = "Sister Fatima (Early Childhood Educator)",
      targetAge = AgeGroup.LITTLE_EXPLORERS,
      language = "English",
      durationLabel = "7 min",
      sourceReference = "Surah An-Nur 24:27 • Al-Adab Al-Mufrad",
      islamicAccuracyStatus = "Changes Required",
      arabicChecked = false,
      respectfulIllustrationChecked = true,
      workflowStatus = ReviewWorkflowStatus.CHANGES_REQUIRED,
      status = "Changes Required",
      version = 2,
      reviewerComment = "Please add full harakat (tashkeel) diacritics on the Arabic greeting phrase in Step 2.",
      stages = listOf(
        ReviewStageCheck(1, "Production Review", true, "Curriculum Ops", "All 5 lesson steps complete."),
        ReviewStageCheck(2, "Islamic Review", false, "Ustadh Ahmad", "Add full diacritics on Arabic text."),
        ReviewStageCheck(3, "Visual & Audio Review", true, "Design Lead", "Bright & inviting cards."),
        ReviewStageCheck(4, "Final Approval", false, "Pending", "Returned to author for diacritics.")
      )
    )
  )

  val defaultAuditLogs = listOf(
    AuditLogEntry("log_1", "12 Nov • 09:42", "Shaykh Yusuf", "Islamic Reviewer", "Approved Story: Prophet Ibrahim & the Cool Fire (v3)"),
    AuditLogEntry("log_2", "11 Nov • 16:15", "Ustadha Amina", "Senior Editor", "Scheduled Cartoon Ep 3 for 15 Nov publication"),
    AuditLogEntry("log_3", "11 Nov • 11:08", "Ustadh Ahmad", "Islamic Reviewer", "Requested diacritics update on Lesson: Visiting Neighbours"),
    AuditLogEntry("log_4", "10 Nov • 08:30", "Admin Lead", "Platform Owner", "Updated COPPA Privacy Policy verification flag")
  )
}

data class TriviaQuestion(
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String
)
