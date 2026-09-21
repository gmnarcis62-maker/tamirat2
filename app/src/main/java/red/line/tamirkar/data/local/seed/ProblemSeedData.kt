package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.ProblemEntity
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.model.RepairDifficulty

object ProblemSeedData {

    fun getAll(): List<ProblemEntity> = listOf(
        // POWER issues
        ProblemEntity(
            id = "prob_no_power",
            title = "عدم روشن شدن (No Power)",
            description = "گوشی هیچ واکنشی نشان نمی‌دهد، صفحه کاملاً خاموش است و هیچ LED یا لرزشی ندارد.",
            category = ProblemCategory.POWER,
            severity = ProblemSeverity.CRITICAL,
            symptoms = listOf(
                "هیچ علائم حیاتی ندارد",
                "با اتصال شارژر تغییری نمی‌کند",
                "کامپیوتر دستگاه را نمی‌شناسد",
                "هیچ صدایی از برد نمی‌آید"
            ),
            commonCauses = listOf(
                "خرابی IC تغذیه (PMU)",
                "آبخوردگی برد در ناحیه تغذیه",
                "خرابی کویل یا خازن تغذیه",
                "پارگی مسیر VBUS یا VBAT",
                "خرابی کلید پاور"
            ),
            estimatedFixTime = "۴۵-۱۲۰ دقیقه",
            estimatedCost = "۵۰۰-۲,۵۰۰ هزار تومان",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("منبع تغذیه", "میکروسکوپ", "هیتر", "قلم هویه", "مولتی‌متر"),
            requiredParts = listOf("IC PMU", "کویل Boost", "خازن‌های فیلتر"),
            warningNotes = listOf(
                "قبل از روشن کردن، حتماً جریان مصرفی را با منبع تغذیه بررسی کنید",
                "در صورت وجود اتصالی، IC تغذیه را تعویض نکنید تا علت اصلی برطرف نشده"
            ),
            successRate = 78,
            isCommon = true,
            relatedProblems = listOf("prob_no_charge", "prob_bootloop", "prob_water_damage")
        ),
        ProblemEntity(
            id = "prob_no_charge",
            title = "عدم شارژ (No Charge)",
            description = "گوشی روشن است اما با اتصال شارژر هیچ واکنشی نشان نمی‌دهد یا شارژ نمی‌شود.",
            category = ProblemCategory.CHARGING,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf(
                "علامت شارژ نمایش داده نمی‌شود",
                "شارژر متصل است اما درصد باتری ثابت می‌ماند",
                "پیام 'شارژ کند' یا 'عدم شناسایی شارژر'",
                "بوی سوختگی از پورت شارژ"
            ),
            commonCauses = listOf(
                "خرابی سوکت شارژ",
                "خرابی IC شارژ (Charging IC)",
                "خرابی باتری (Battery Protection Circuit)",
                "مسیر VBUS از سوکت تا IC شارژ قطع شده",
                "خرابی QHD (Quick Charge Detection)"
            ),
            estimatedFixTime = "۳۰-۹۰ دقیقه",
            estimatedCost = "۳۰۰-۱,۸۰۰ هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر", "مولتی‌متر", "منبع تغذیه"),
            requiredParts = listOf("سوکت شارژ", "IC شارژ", "باتری"),
            warningNotes = listOf(
                "قبل از تعویض IC شارژ، مسیر VBUS را از سوکت تا IC چک کنید",
                "از شارژرهای غیراستاندارد استفاده نکنید"
            ),
            successRate = 85,
            isCommon = true,
            relatedProblems = listOf("prob_no_power", "prob_slow_charge", "prob_battery_drain")
        ),
        ProblemEntity(
            id = "prob_slow_charge",
            title = "شارژ کند (Slow Charging)",
            description = "گوشی شارژ می‌شود اما خیلی کند؛ مثلاً ۱۰٪ در ساعت.",
            category = ProblemCategory.CHARGING,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "سرعت شارژ بسیار پایین است",
                "پیام 'شارژ کند' نمایش داده می‌شود",
                "با شارژر فست شارژ هم سرعت بالا نمی‌رود"
            ),
            commonCauses = listOf(
                "خرابی کابل یا آداپتور",
                "خشک شدن خمیر حرارتی روی IC شارژ",
                "ضعیف شدن باتری",
                "خرابی مسیر D+/D- برای شناسایی شارژر",
                "نرم‌افزاری: اپلیکیشن‌های پس‌زمینه زیاد"
            ),
            estimatedFixTime = "۲۰-۶۰ دقیقه",
            estimatedCost = "۲۰۰-۱,۲۰۰ هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("مولتی‌متر", "آمپرمتر"),
            requiredParts = listOf("کابل شارژ", "باتری"),
            warningNotes = listOf("ابتدا با کابل و شارژر سالم تست کنید"),
            successRate = 92,
            isCommon = true,
            relatedProblems = listOf("prob_no_charge", "prob_battery_drain")
        ),
        ProblemEntity(
            id = "prob_battery_drain",
            title = "مصرف بالای باتری (Battery Drain)",
            description = "باتری خیلی سریع خالی می‌شود؛ مثلاً ۵۰٪ در ۲-۳ ساعت.",
            category = ProblemCategory.BATTERY,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "گرمای زیاد پشت گوشی",
                "مصرف باتری در تنظیمات نشان‌دهنده اپلیکیشن مشکل‌دار",
                "در حالت Standby هم باتری خالی می‌شود"
            ),
            commonCauses = listOf(
                "ضعیف شدن باتری (ظرفیت کم شده)",
                "خرابی IC تغذیه (Leakage)",
                "اپلیکیشن‌های پس‌زمینه",
                "خرابی آنتن (جستجوی مداوم شبکه)",
                "خرابی سنسور مجاورت"
            ),
            estimatedFixTime = "۱۵-۴۵ دقیقه",
            estimatedCost = "۲۰۰-۸۰۰ هزار تومان",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("مولتی‌متر", "نرم‌افزار تشخیصی"),
            requiredParts = listOf("باتری"),
            warningNotes = listOf("ابتدا باتری را با تستر باتری چک کنید"),
            successRate = 95,
            isCommon = true,
            relatedProblems = listOf("prob_slow_charge", "prob_overheating")
        ),

        // DISPLAY issues
        ProblemEntity(
            id = "prob_black_screen",
            title = "صفحه سیاه (Black Screen)",
            description = "گوشی روشن است (لرزش یا صدا دارد) اما تصویر ندارد.",
            category = ProblemCategory.DISPLAY,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf(
                "گوشی لرزش دارد یا صدای نوتیفیکیشن می‌آید",
                "تاچ کار می‌کند اما تصویر نیست",
                "بک‌لایت وجود دارد اما تصویر ندارد"
            ),
            commonCauses = listOf(
                "خرابی LCD/OLED",
                "پارگی FPC نمایشگر",
                "خرابی IC DCDC بک‌لایت (Boost)",
                "خرابی کویل بک‌لایت",
                "آبخوردگی در ناحیه LCD"
            ),
            estimatedFixTime = "۳۰-۶۰ دقیقه",
            estimatedCost = "۱,۰۰۰-۵,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("ساکشن LCD", "هیتر", "میکروسکوپ"),
            requiredParts = listOf("LCD/OLED", "FPC", "IC DCDC"),
            warningNotes = listOf(
                "قبل از تعویض LCD مسیر بک‌لایت را با مولتی‌متر چک کنید",
                "از LCDهای OEM استفاده کنید"
            ),
            successRate = 88,
            isCommon = true,
            relatedProblems = listOf("prob_touch_issue", "prob_flickering")
        ),
        ProblemEntity(
            id = "prob_touch_issue",
            title = "مشکل تاچ (Touch Issue)",
            description = "تاچ کار نمی‌کند یا ناقص است؛ بعضی قسمت‌ها جواب نمی‌دهند.",
            category = ProblemCategory.DISPLAY,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf(
                "تاچ کلاً کار نمی‌کند",
                "بخش‌هایی از صفحه تاچ ندارند",
                "تاچ خودکار عمل می‌کند (Ghost Touch)",
                "تاچ با تأخیر پاسخ می‌دهد"
            ),
            commonCauses = listOf(
                "خرابی تاچ IC (Touch Controller)",
                "پارگی FPC تاچ",
                "خرابی LCD",
                "آبخوردگی در ناحیه تاچ",
                "نرم‌افزاری: Calibration از دست رفته"
            ),
            estimatedFixTime = "۳۰-۹۰ دقیقه",
            estimatedCost = "۸۰۰-۳,۵۰۰ هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر", "ساکشن LCD"),
            requiredParts = listOf("LCD با تاچ", "IC تاچ"),
            warningNotes = listOf("Ghost Touch معمولاً از آبخوردگی است"),
            successRate = 82,
            isCommon = true,
            relatedProblems = listOf("prob_black_screen", "prob_flickering")
        ),
        ProblemEntity(
            id = "prob_flickering",
            title = "چشمک‌زدن/لرزش صفحه (Screen Flickering)",
            description = "صفحه نمایش چشمک می‌زند، خطوط افقی/عمودی دارد یا brightness نوسان می‌کند.",
            category = ProblemCategory.DISPLAY,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "خطوط افقی یا عمودی روی صفحه",
                "چشمک‌زدن نور صفحه",
                "تغییر خودکار brightness",
                "رنگ‌های غیرطبیعی"
            ),
            commonCauses = listOf(
                "خرابی LCD",
                "پارگی FPC",
                "خرابی IC DCDC بک‌لایت",
                "نوسان ولتاژ",
                "خرابی سنسور نور"
            ),
            estimatedFixTime = "۳۰-۶۰ دقیقه",
            estimatedCost = "۸۰۰-۳,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("میکروسکوپ", "اسیلوسکوپ"),
            requiredParts = listOf("LCD", "IC DCDC"),
            warningNotes = listOf("اسیلوسکوپ برای بررسی نوسان ولتاژ بک‌لایت لازم است"),
            successRate = 85,
            isCommon = false,
            relatedProblems = listOf("prob_black_screen", "prob_touch_issue")
        ),

        // NETWORK issues
        ProblemEntity(
            id = "prob_no_signal",
            title = "عدم آنتن‌دهی (No Signal)",
            description = "گوشی SIM را می‌شناسد اما هیچ شبکه‌ای پیدا نمی‌کند.",
            category = ProblemCategory.NETWORK,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf(
                "نوار آنتن خالی است",
                "پیام 'بدون سرویس' یا 'جستجوی شبکه'",
                "SIM شناسایی می‌شود اما ثبت شبکه نمی‌شود",
                "فقط Emergency Calls"
            ),
            commonCauses = listOf(
                "خرابی IC آنتن (Transceiver)",
                "پارگی مسر RF از آنتن تا Transceiver",
                "خرابی PA (Power Amplifier)",
                "خرابی duplexer",
                "IMEI null یا Blacklisted"
            ),
            estimatedFixTime = "۶۰-۱۸۰ دقیقه",
            estimatedCost = "۵۰۰-۳,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("میکروسکوپ", "هیتر", "Network Analyzer"),
            requiredParts = listOf("IC Transceiver", "PA", "duplexer"),
            warningNotes = listOf(
                "ابتدا IMEI را چک کنید",
                "از نرم‌افزارهای RF Test Mode استفاده کنید"
            ),
            successRate = 70,
            isCommon = true,
            relatedProblems = listOf("prob_weak_signal", "prob_no_4g")
        ),
        ProblemEntity(
            id = "prob_weak_signal",
            title = "آنتن ضعیف (Weak Signal)",
            description = "آنتن وجود دارد اما خیلی ضعیف است؛ تماس قطع می‌شود.",
            category = ProblemCategory.NETWORK,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "۱-۲ خط آنتن",
                "تماس در indoors قطع می‌شود",
                "اینترنت موبایل کند است"
            ),
            commonCauses = listOf(
                "خرابی آنتن فیزیکی",
                "پارگی coaxial cable",
                "خرابی PA",
                "خرابی duplexer",
                "تداخل نرم‌افزاری"
            ),
            estimatedFixTime = "۴۵-۱۲۰ دقیقه",
            estimatedCost = "۴۰۰-۲,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر"),
            requiredParts = listOf("آنتن", "coaxial", "PA"),
            warningNotes = listOf("آنتن فیزیکی را اول چک کنید"),
            successRate = 80,
            isCommon = false,
            relatedProblems = listOf("prob_no_signal", "prob_no_4g")
        ),

        // BOOT issues
        ProblemEntity(
            id = "prob_bootloop",
            title = "بوت لوپ (Bootloop)",
            description = "گوشی روی لوگو می‌ماند و دوباره راه‌اندازی می‌شود.",
            category = ProblemCategory.SOFTWARE,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf(
                "روی لوگوی برند می‌ماند",
                "ری‌استارت مداوم",
                "بعد از فلش مشکل حل نمی‌شود"
            ),
            commonCauses = listOf(
                "خرابی NAND/eMMC (Bad Blocks)",
                "خرابی IC تغذیه (نوسان ولتاژ)",
                "آبخوردگی CPU",
                "نرم‌افزاری: خرابی boot partition",
                "خرابی RAM"
            ),
            estimatedFixTime = "۶۰-۳۰۰ دقیقه",
            estimatedCost = "۵۰۰-۴,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("منبع تغذیه", "میکروسکوپ", "هیتر", "باکس فلش"),
            requiredParts = listOf("eMMC/NAND", "IC PMU"),
            warningNotes = listOf(
                "ابتدا با فلش نرم‌افزاری تست کنید",
                "اگر بعد از فلش مشکل باقی بود، سخت‌افزار است"
            ),
            successRate = 65,
            isCommon = true,
            relatedProblems = listOf("prob_no_power", "prob_stuck_recovery")
        ),
        ProblemEntity(
            id = "prob_stuck_recovery",
            title = "گیر در Recovery Mode",
            description = "گوشی فقط به Recovery Mode می‌رود و بالا نمی‌آید.",
            category = ProblemCategory.SOFTWARE,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "صفحه No Command",
                "Recovery Menu نمایش داده می‌شود",
                "Wipe Data کار نمی‌کند"
            ),
            commonCauses = listOf(
                "خرابی boot partition",
                "خرابی system partition",
                "خرابی eMMC",
                "کلید ولوم خراب (همیشه فشار داده)"
            ),
            estimatedFixTime = "۳۰-۱۲۰ دقیقه",
            estimatedCost = "۲۰۰-۱,۵۰۰ هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("کامپیوتر", "ADB/Fastboot"),
            requiredParts = listOf("eMMC"),
            warningNotes = listOf("ابتدا کلید ولوم را چک کنید"),
            successRate = 85,
            isCommon = false,
            relatedProblems = listOf("prob_bootloop")
        ),

        // AUDIO issues
        ProblemEntity(
            id = "prob_no_sound",
            title = "عدم پخش صدا (No Sound)",
            description = "صدای مکالمه، اسپیکر یا هدفون پخش نمی‌شود.",
            category = ProblemCategory.AUDIO,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "صدای مکالمه نمی‌آید",
                "اسپیکر موییک پخش نمی‌کند",
                "هدفون کار نمی‌کند",
                "صدای خش‌دار"
            ),
            commonCauses = listOf(
                "خرابی اسپیکر",
                "پارگی FPC اسپیکر",
                "خرابی IC Audio (Codec)",
                "آبخوردگی در ناحیه اسپیکر",
                "خرابی جک هدفون"
            ),
            estimatedFixTime = "۲۰-۶۰ دقیقه",
            estimatedCost = "۲۰۰-۱,۲۰۰ هزار تومان",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("موتی‌متر", "هیتر"),
            requiredParts = listOf("اسپیکر", "IC Audio"),
            warningNotes = listOf("ابتدا با اسپیکر سالم تست کنید"),
            successRate = 92,
            isCommon = true,
            relatedProblems = listOf("prob_earpiece_low", "prob_mic_not_working")
        ),
        ProblemEntity(
            id = "prob_earpiece_low",
            title = "صدای مکالمه کم (Low Earpiece)",
            description = "صدای گوینده در مکالمه خیلی کم است حتی با حداکثر volume.",
            category = ProblemCategory.AUDIO,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "صدای مکالمه خیلی کم است",
                "صدای اسپیکر موییک نرمال است",
                "صدای خش‌دار در مکالمه"
            ),
            commonCauses = listOf(
                "گرفتگی شبکه گوشی",
                "خرابی earpiece",
                "خرابی IC Audio",
                "خرابی سنسور مجاورت"
            ),
            estimatedFixTime = "۱۵-۴۵ دقیقه",
            estimatedCost = "۱۵۰-۸۰۰ هزار تومان",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("قلم هویه", "مولتی‌متر"),
            requiredParts = listOf("earpiece", "IC Audio"),
            warningNotes = listOf("ابتدا شبکه گوشی را تمیز کنید"),
            successRate = 95,
            isCommon = true,
            relatedProblems = listOf("prob_no_sound", "prob_mic_not_working")
        ),
        ProblemEntity(
            id = "prob_mic_not_working",
            title = "عدم کارکرد میکروفون (Mic Issue)",
            description = "مخاطب صدای شما را نمی‌شنود یا صدا خیلی ضعیف است.",
            category = ProblemCategory.AUDIO,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "مخاطب صدا نمی‌شود",
                "صدای ضبط شده خیلی ضعیف است",
                "صدای خش‌دار در ضبط"
            ),
            commonCauses = listOf(
                "خرابی میکروفون",
                "پارگی FPC میکروفون",
                "خرابی IC Audio",
                "گرفتگی سوراخ میکروفون",
                "آبخوردگی"
            ),
            estimatedFixTime = "۲۰-۶۰ دقیقه",
            estimatedCost = "۲۰-۱,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("قلم هویه", "میکروسکوپ"),
            requiredParts = listOf("میکروفون", "IC Audio"),
            warningNotes = listOf("ابتدا سوراخ میکروفون را تمیز کنید"),
            successRate = 93,
            isCommon = true,
            relatedProblems = listOf("prob_no_sound", "prob_earpiece_low")
        ),

        // CAMERA issues
        ProblemEntity(
            id = "prob_camera_not_working",
            title = "عدم کارکرد دوربین (Camera Issue)",
            description = "دوربین باز نمی‌شود یا تصویر سیاه/سبز است.",
            category = ProblemCategory.CAMERA,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf(
                "اپلیکیشن دوربین کرش می‌کند",
                "تصویر سیاه است",
                "خطای 'Camera Failed'",
                "فوکوس کار نمی‌کند"
            ),
            commonCauses = listOf(
                "خرابی دوربین",
                "پارگی FPC دوربین",
                "خرابی IC ISP",
                "آبخوردگی",
                "نرم‌افزاری: خرابی Camera APK"
            ),
            estimatedFixTime = "۳۰-۹۰ دقیقه",
            estimatedCost = "۵۰۰-۲,۵۰۰ هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("میکروسکوپ", "هیتر"),
            requiredParts = listOf("دوربین", "IC ISP"),
            warningNotes = listOf("ابتدا با نرم‌افزار دیگر تست کنید"),
            successRate = 85,
            isCommon = true,
            relatedProblems = listOf("prob_camera_blur", "prob_camera_black")
        ),

        // BOARD issues
        ProblemEntity(
            id = "prob_water_damage",
            title = "آبخوردگی (Water Damage)",
            description = "گوشی با آب تماس داشته و علائم مختلفی نشان می‌دهد.",
            category = ProblemCategory.BOARD,
            severity = ProblemSeverity.CRITICAL,
            symptoms = listOf(
                "خوردگی در برد",
                "ICها اکسیده شده‌اند",
                "مسیرها پوسیده شده‌اند",
                "علائم متغیر (گاهی روشن، گاهی خاموش)"
            ),
            commonCauses = listOf(
                "نفوذ آب به داخل",
                "خوردگی الکتروشیمیایی",
                "اتصالی در برد",
                "خرابی ICهای مختلف"
            ),
            estimatedFixTime = "۱۲۰-۳۶۰ دقیقه",
            estimatedCost = "۱,۰۰۰-۵,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("میکروسکوپ", "هیتر", " ultrasonic cleaner", "مولتی‌متر"),
            requiredParts = listOf("ICهای مختلف", "خازن‌ها", "مقاومت‌ها"),
            warningNotes = listOf(
                "هرگز گوشی آبخورده را روشن نکنید",
                "اول با ultrasonic cleaner تمیز کنید",
                "تمام ICها را زیر میکروسکوپ بررسی کنید"
            ),
            successRate = 55,
            isCommon = true,
            relatedProblems = listOf("prob_no_power", "prob_bootloop", "prob_no_charge")
        ),
        ProblemEntity(
            id = "prob_overheating",
            title = "گرمای بیش از حد (Overheating)",
            description = "گوشی خیلی گرم می‌شود؛ دمای CPU بالای ۶۰ درجه.",
            category = ProblemCategory.BOARD,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf(
                "گرمای زیاد پشت گوشی",
                "کاهش عملکرد (Thermal Throttling)",
                "خاموش شدن خودکار",
                "باتری خیلی سریع خالی می‌شود"
            ),
            commonCauses = listOf(
                "خشک شدن خمیر حرارتی CPU",
                "خرابی IC تغذیه (Leakage)",
                "خرابی باتری",
                "اپلیکیشن‌های سنگین",
                "خرابی آنتن (جستجوی مداوم)"
            ),
            estimatedFixTime = "۳۰-۱۲۰ دقیقه",
            estimatedCost = "۳۰۰-۲,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر", "ترمال دوربین"),
            requiredParts = listOf("خمیر حرارتی", "IC PMU", "باتری"),
            warningNotes = listOf("با ترمال دوربین نقطه داغ را پیدا کنید"),
            successRate = 82,
            isCommon = true,
            relatedProblems = listOf("prob_battery_drain", "prob_no_power")
        ),

        // CONNECTIVITY
        ProblemEntity(
            id = "prob_wifi_not_working",
            title = "عدم کارکرد WiFi",
            category = ProblemCategory.CONNECTIVITY,
            severity = ProblemSeverity.MEDIUM,
            description = "WiFi روشن نمی‌شود یا شبکه‌ها را پیدا نمی‌کند.",
            symptoms = listOf("WiFi خاکستری است", "شبکه‌ها نمایش داده نمی‌شوند", "متصل می‌شود اما اینترنت ندارد"),
            commonCauses = listOf("خرابی IC WiFi/BT", "پارگی آنتن WiFi", "نرم‌افزاری"),
            estimatedFixTime = "۴۵-۱۲۰ دقیقه",
            estimatedCost = "۴۰۰-۲,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر"),
            requiredParts = listOf("IC WiFi"),
            warningNotes = listOf("ابتدا با نرم‌افزار تست کنید"),
            successRate = 80,
            isCommon = true,
            relatedProblems = listOf("prob_bluetooth_issue")
        ),
        ProblemEntity(
            id = "prob_bluetooth_issue",
            title = "مشکل Bluetooth",
            category = ProblemCategory.CONNECTIVITY,
            severity = ProblemSeverity.LOW,
            description = "Bluetooth روشن نمی‌شود یا دستگاه‌ها را پیدا نمی‌کند.",
            symptoms = listOf("BT خاکستری است", "جستجو نمیکند", "قطع و وصل می‌شود"),
            commonCauses = listOf("خرابی IC WiFi/BT", "نرم‌افزاری"),
            estimatedFixTime = "۳۰-۹۰ دقیقه",
            estimatedCost = "۴۰۰-۲,۰۰۰ هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر"),
            requiredParts = listOf("IC WiFi/BT"),
            warningNotes = emptyList(),
            successRate = 82,
            isCommon = false,
            relatedProblems = listOf("prob_wifi_not_working")
        ),

        // SENSOR
        ProblemEntity(
            id = "prob_fingerprint_not_working",
            title = "عدم کارکرد اثر انگشت",
            category = ProblemCategory.SENSOR,
            severity = ProblemSeverity.LOW,
            description = "سنسور اثر انگشت پاسخ نمی‌دهد یا خطا می‌دهد.",
            symptoms = listOf("اثر انگشت ثبت نمی‌شود", "شناسایی نمی‌کند", "خطای 'Sensor Not Found'"),
            commonCauses = listOf("خرابی سنسور", "پارگی FPC", "نرم‌افزاری"),
            estimatedFixTime = "۲۰-۶۰ دقیقه",
            estimatedCost = "۳۰۰-۱,۵۰۰ هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("قلم هویه"),
            requiredParts = listOf("سنسور اثر انگشت"),
            warningNotes = emptyList(),
            successRate = 88,
            isCommon = false,
            relatedProblems = listOf()
        ),
        ProblemEntity(
            id = "prob_proximity_not_working",
            title = "عدم کارکرد سنسور مجاورت",
            category = ProblemCategory.SENSOR,
            severity = ProblemSeverity.LOW,
            description = "در مکالمه صفحه خاموش نمی‌شود.",
            symptoms = listOf("صفحه در مکالمه روشن می‌ماند", "صفحه بعد از مکالمه خاموش نمی‌شود"),
            commonCauses = listOf("خرابی سنسور", "پارگی FPC", "نرم‌افزاری"),
            estimatedFixTime = "۱۵-۴۵ دقیقه",
            estimatedCost = "۲۰۰-۸۰۰ هزار تومان",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("قلم هویه"),
            requiredParts = listOf("سنسور مجاورت"),
            warningNotes = emptyList(),
            successRate = 92,
            isCommon = false,
            relatedProblems = listOf()
        ),

        // HARDWARE
        ProblemEntity(
            id = "prob_vibrator_not_working",
            title = "عدم کارکرد ویبره",
            category = ProblemCategory.HARDWARE,
            severity = ProblemSeverity.LOW,
            description = "گوشی لرزش ندارد.",
            symptoms = listOf("ویبره کار نمی‌کند", "صدا ضعیف است"),
            commonCauses = listOf("خرابی موتور ویبره", "پارگی FPC"),
            estimatedFixTime = "۱۵-۳۰ دقیقه",
            estimatedCost = "۱۰۰-۵۰۰ هزار تومان",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("قلم هویه"),
            requiredParts = listOf("موتور ویبره"),
            warningNotes = emptyList(),
            successRate = 96,
            isCommon = false,
            relatedProblems = listOf()
        ),
        ProblemEntity(
            id = "prob_power_button",
            title = "مشکل کلید پاور",
            category = ProblemCategory.HARDWARE,
            severity = ProblemSeverity.MEDIUM,
            description = "کلید پاور کار نمی‌کند یا گیر کرده است.",
            symptoms = listOf("پاور فشار نمی‌خورد", "گیر کرده", "خودکار فشار می‌خورد"),
            commonCauses = listOf("خرابی کلید فیزیکی", "پارگی FPC", "آبخوردگی"),
            estimatedFixTime = "۲۰-۴۵ دقیقه",
            estimatedCost = "۱۵۰-۸۰۰ هزار تومن",
            difficulty = RepairDifficulty.EASY,
            requiredTools = listOf("قلم هویه"),
            requiredParts = listOf("کلید پاور", "FPC"),
            warningNotes = emptyList(),
            successRate = 94,
            isCommon = true,
            relatedProblems = listOf()
        )
    )
}
