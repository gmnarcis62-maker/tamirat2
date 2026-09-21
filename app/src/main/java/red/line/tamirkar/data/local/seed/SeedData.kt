package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.*
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.model.RepairDifficulty
import javax.inject.Inject

class SeedData @Inject constructor() {

    fun brands(): List<BrandEntity> = listOf(
        BrandEntity("brand_samsung", "سامسونگ", "سامسونگ", "Samsung", "samsung"),
        BrandEntity("brand_apple", "اپل", "اپل", "Apple", "apple"),
        BrandEntity("brand_xiaomi", "شیائومی", "شیائومی", "Xiaomi", "xiaomi"),
        BrandEntity("brand_huawei", "هوآوی", "هوآوی", "Huawei", "huawei"),
        BrandEntity("brand_oneplus", "وان‌پلاس", "وان پلاس", "OnePlus", "oneplus"),
        BrandEntity("brand_nokia", "نوکیا", "نوکیا", "Nokia", "nokia"),
        BrandEntity("brand_sony", "سونی", "سونی", "Sony", "sony"),
        BrandEntity("brand_motorola", "موتورولا", "موتورولا", "Motorola", "motorola"),
        BrandEntity("brand_google", "گوگل پیکسل", "گوگل پیکسل", "Google Pixel", "google-pixel"),
        BrandEntity("brand_nothing", "ناتینگ", "ناتینگ", "Nothing", "nothing")
    )

    fun categories(): List<ProblemCategoryEntity> = listOf(
        ProblemCategoryEntity("power", "روشن شدن / تغذیه", "Power", "power"),
        ProblemCategoryEntity("charging", "شارژ", "Charging", "charging"),
        ProblemCategoryEntity("display", "نمایشگر", "Display", "display"),
        ProblemCategoryEntity("touch", "تاچ", "Touch", "touch"),
        ProblemCategoryEntity("network", "شبکه", "Network", "network"),
        ProblemCategoryEntity("audio", "صدا", "Audio", "audio"),
        ProblemCategoryEntity("camera", "دوربین", "Camera", "camera"),
        ProblemCategoryEntity("software", "نرم‌افزار", "Software", "software")
    )

    fun problems(): List<ProblemEntity> = listOf(
        ProblemEntity(
            id = "problem_no_power",
            title = "گوشی روشن نمی‌شود",
            description = "دستگاه هیچ واکنشی به شارژر یا کلید Power نشان نمی‌دهد.",
            category = ProblemCategory.POWER,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf("هیچ واکنشی به کلید Power", "عدم نمایش شارژ", "جریان صفر روی منبع تغذیه"),
            commonCauses = listOf("خرابی باتری", "خرابی PMIC", "خرابی CPU"),
            estimatedFixTime = "30-60 دقیقه",
            estimatedCost = "200-800 هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("مولتی‌متر", "منبع تغذیه DC"),
            requiredParts = listOf("باتری", "PMIC"),
            warningNotes = listOf("قبل از هر اقدامی باتری را جدا کنید"),
            successRate = 75,
            isCommon = true,
            relatedProblems = listOf("problem_no_charge")
        ),
        ProblemEntity(
            id = "problem_no_charge",
            title = "شارژ نمی‌شود",
            description = "دستگاه روشن می‌شود اما با شارژر، درصد باتری افزایش نمی‌یابد.",
            category = ProblemCategory.CHARGING,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf("عدم افزایش درصد باتری", "قطع و وصل شارژ"),
            commonCauses = listOf("خرابی IC شارژ", "خرابی کانکتور"),
            estimatedFixTime = "20-40 دقیقه",
            estimatedCost = "100-500 هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("مولتی‌متر"),
            requiredParts = listOf("IC شارژ"),
            warningNotes = emptyList(),
            successRate = 85,
            isCommon = true,
            relatedProblems = listOf("problem_no_power")
        ),
        ProblemEntity(
            id = "problem_bootloop",
            title = "بوت‌لوپ",
            description = "دستگاه به طور مداوم در لوگوی اولیه راه‌اندازی مجدد می‌شود.",
            category = ProblemCategory.SOFTWARE,
            severity = ProblemSeverity.HIGH,
            symptoms = listOf("ری‌استارت مداوم", "ورود به Recovery"),
            commonCauses = listOf("خرابی نرم‌افزار", "خرابی Storage"),
            estimatedFixTime = "30-90 دقیقه",
            estimatedCost = "150-600 هزار تومان",
            difficulty = RepairDifficulty.MEDIUM,
            requiredTools = listOf("کامپیوتر", "کابل USB"),
            requiredParts = emptyList(),
            warningNotes = listOf("قبل از فلش، بکاپ NVRAM بگیرید"),
            successRate = 80,
            isCommon = true,
            relatedProblems = emptyList()
        ),
        ProblemEntity(
            id = "problem_touch_issue",
            title = "تاچ کار نمی‌کند",
            description = "صفحه نمایش روشن است اما تاچ پاسخ نمی‌دهد.",
            category = ProblemCategory.DISPLAY,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf("عدم پاسخ تاچ", "تاچ خودکار"),
            commonCauses = listOf("خرابی تاچ IC", "قطع شدن خطوط MIPI"),
            estimatedFixTime = "40-90 دقیقه",
            estimatedCost = "300-1200 هزار تومان",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("هیتر", "میکروسکوپ"),
            requiredParts = listOf("تاچ IC"),
            warningNotes = emptyList(),
            successRate = 70,
            isCommon = true,
            relatedProblems = emptyList()
        ),
        ProblemEntity(
            id = "problem_no_signal",
            title = "آنتن ندارد",
            description = "دستگاه SIM را تشخیص می‌دهد اما آنتن ندارد.",
            category = ProblemCategory.NETWORK,
            severity = ProblemSeverity.MEDIUM,
            symptoms = listOf("آنتن صفر", "جستجوی دائم"),
            commonCauses = listOf("خرابی IC آنتن", "قطع خطوط آنتن"),
            estimatedFixTime = "45-120 دقیقه",
            estimatedCost = "400-1500 هزار تومان",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("میکروسکوپ", "اسیلوسکوپ"),
            requiredParts = listOf("RF IC"),
            warningNotes = listOf("NVRAM backup ضروری است"),
            successRate = 65,
            isCommon = true,
            relatedProblems = emptyList()
        )
    )

    fun models(): List<DeviceModelEntity> = listOf(
        DeviceModelEntity(
            id = "samsung_sm_a525f",
            brandId = "brand_samsung",
            name = "Galaxy A52",
            nameEn = "Samsung Galaxy A52",
            modelNumber = "SM-A525F",
            normalizedName = "galaxy a52",
            chipset = "Snapdragon 720G",
            batteryCapacity = 4500
        ),
        DeviceModelEntity(
            id = "xiaomi_m2101k7ag",
            brandId = "brand_xiaomi",
            name = "Redmi Note 10",
            nameEn = "Xiaomi Redmi Note 10",
            modelNumber = "M2101K7AG",
            normalizedName = "redmi note 10",
            chipset = "Snapdragon 678",
            batteryCapacity = 5000
        )
    )

    fun diagnosisTrees(): List<DiagnosisTreeEntity> = listOf(
        DiagnosisTreeEntity(
            id = "tree_no_power",
            problemId = "problem_no_power",
            modelId = null,
            title = "درخت عیب‌یابی: گوشی روشن نمی‌شود",
            description = "راهنمای مرحله‌ای برای عیب‌یابی روشن نشدن دستگاه",
            version = 1
        )
    )

    fun diagnosisNodes(): List<DiagnosisNodeEntity> = listOf(
        DiagnosisNodeEntity(
            id = "node_check_battery_voltage",
            question = "ولتاژ باتری دستگاه چقدر است؟",
            description = "ابتدا ولتاژ باتری را با مولتی‌متر بررسی کنید.",
            options = emptyList(),
            isStartNode = true,
            isEndNode = false,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_battery_low",
            question = "",
            description = "ولتاژ باتری کمتر از حد نرمال است.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = true,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_check_power_button",
            question = "آیا با فشار دادن کلید Power واکنشی مشاهده می‌کنید؟",
            description = "باتری ولتاژ مناسب دارد. کلید Power را بررسی کنید.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = false,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_check_power_rails",
            question = "آیا ولتاژ روی خطوط تغذیه اصلی (VBAT, VPH_PWR) وجود دارد؟",
            description = "کلید Power واکنش ندارد. خطوط تغذیه را بررسی کنید.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = false,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_check_pmic",
            question = "",
            description = "خطوط تغذیه وجود دارند اما دستگاه روشن نمی‌شود.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = true,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_short_circuit",
            question = "",
            description = "ولتاژ روی خطوط اصلی وجود ندارد.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = true,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_check_boot_current",
            question = "هنگام فشار دادن Power، آیا جریان لحظه‌ای مشاهده می‌شود؟",
            description = "کلید Power واکنش دارد. جریان‌کشی را بررسی کنید.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = false,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_no_boot_current",
            question = "",
            description = "هیچ جریان لحظه‌ای مشاهده نمی‌شود.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = true,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        ),
        DiagnosisNodeEntity(
            id = "node_boot_current_present",
            question = "",
            description = "جریان لحظه‌ای مشاهده می‌شود.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = true,
            problemId = "problem_no_power",
            guideId = null,
            severity = null
        )
    )

    fun diagnosisOptions(): List<DiagnosisOptionEntity> = listOf(
        DiagnosisOptionEntity(
            id = "opt_battery_low",
            nodeId = "node_check_battery_voltage",
            title = "ولتاژ کمتر از 3.5V است",
            value = "low",
            nextNodeId = "node_battery_low",
            condition = "voltage < 3.5"
        ),
        DiagnosisOptionEntity(
            id = "opt_battery_normal",
            nodeId = "node_check_battery_voltage",
            title = "ولتاژ بین 3.5V تا 4.4V است",
            value = "normal",
            nextNodeId = "node_check_power_button",
            condition = "voltage >= 3.5 && voltage <= 4.4"
        ),
        DiagnosisOptionEntity(
            id = "opt_battery_unknown",
            nodeId = "node_check_battery_voltage",
            title = "اطلاع ندارم / امکان تست ندارم",
            value = "unknown",
            nextNodeId = "node_check_power_button",
            condition = null
        ),
        DiagnosisOptionEntity(
            id = "opt_power_no_response",
            nodeId = "node_check_power_button",
            title = "خیر، هیچ واکنشی ندارد",
            value = "no_response",
            nextNodeId = "node_check_power_rails",
            condition = null
        ),
        DiagnosisOptionEntity(
            id = "opt_power_response",
            nodeId = "node_check_power_button",
            title = "بله، واکنش دارد (ویبره/صدا/جریان)",
            value = "response",
            nextNodeId = "node_check_boot_current",
            condition = null
        ),
        DiagnosisOptionEntity(
            id = "opt_rails_ok",
            nodeId = "node_check_power_rails",
            title = "بله، ولتاژ وجود دارد",
            value = "ok",
            nextNodeId = "node_check_pmic",
            condition = null
        ),
        DiagnosisOptionEntity(
            id = "opt_rails_not_ok",
            nodeId = "node_check_power_rails",
            title = "خیر، ولتاژی وجود ندارد",
            value = "not_ok",
            nextNodeId = "node_short_circuit",
            condition = null
        ),
        DiagnosisOptionEntity(
            id = "opt_no_boot_current",
            nodeId = "node_check_boot_current",
            title = "خیر، جریانی مشاهده نمی‌شود",
            value = "no_current",
            nextNodeId = "node_no_boot_current",
            condition = null
        ),
        DiagnosisOptionEntity(
            id = "opt_boot_current_present",
            nodeId = "node_check_boot_current",
            title = "بله، جریان لحظه‌ای دارد",
            value = "current_present",
            nextNodeId = "node_boot_current_present",
            condition = null
        )
    )
}