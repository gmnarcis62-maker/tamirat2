package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.*
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
        ProblemEntity("problem_no_power", "power", "گوشی روشن نمی‌شود", "No Power", "no-power", severity = "high", difficulty = "advanced", isCommon = true),
        ProblemEntity("problem_no_charge", "charging", "شارژ نمی‌شود", "No Charging", "no-charging", severity = "medium", difficulty = "intermediate", isCommon = true),
        ProblemEntity("problem_bootloop", "software", "بوت‌لوپ", "Bootloop", "bootloop", severity = "high", difficulty = "intermediate", isCommon = true),
        ProblemEntity("problem_touch_issue", "touch", "تاچ کار نمی‌کند", "Touch Not Working", "touch-not-working", severity = "medium", difficulty = "intermediate", isCommon = true),
        ProblemEntity("problem_no_signal", "network", "آنتن ندارد", "No Signal", "no-signal", severity = "medium", difficulty = "advanced", isCommon = true)
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