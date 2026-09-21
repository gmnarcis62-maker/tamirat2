package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.*
import javax.inject.Inject

class SeedData @Inject constructor() {

    fun brands(): List<BrandEntity> = listOf(
        BrandEntity("brand_samsung", "سامسونگ", "سامسونگ", "Samsung", "samsung"),
        BrandEntity("brand_apple", "اپل", "اپل", "Apple", "apple"),
        BrandEntity("brand_xiaomi", "شیائومی", "شیائومی", "Xiaomi", "xiaomi"),
        BrandEntity("brand_huawei", "هواوی", "هواوی", "Huawei", "huawei"),
        BrandEntity("brand_oneplus", "وان‌پلاس", "وان پلاس", "OnePlus", "oneplus"),
        BrandEntity("brand_nokia", "نوکیا", "نوکیا", "Nokia", "nokia"),
        BrandEntity("brand_sony", "سونی", "سونی", "Sony", "sony"),
        BrandEntity("brand_motorola", "موتورولا", "موتورولا", "Motorola", "motorola"),
        BrandEntity("brand_google", "گوگل پیکسل", "گوگل پیکسل", "Google Pixel", "google-pixel"),
        BrandEntity("brand_nothing", "ناتینگ", "ناتینگ", "Nothing", "nothing")
    )

    fun categories(): List<ProblemCategoryEntity> = listOf(
        ProblemCategoryEntity("power", "رون شدن / غذیه", "Power", "power"),
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

    // Diagnosis Tree for No Power problem
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
        // Root node
        DiagnosisNodeEntity(
            id = "node_check_battery_voltage",
            treeId = "tree_no_power",
            type = "QUESTION",
            title = "بررسی ولتاژ باتری",
            description = "ابتدا ولتاژ باتری را با مولتی‌متر بررسی کنید.",
            question = "ولتاژ باتری دستگاه چقدر است؟",
            instruction = "مولتی‌متر را روی حالت DC Voltage قرار دهید. پروب سیاه را به GND و پروب قرمز را به VBAT وصل کنید.",
            orderIndex = 1,
            isRoot = true
        ),
        // Node: Battery voltage is low
        DiagnosisNodeEntity(
            id = "node_battery_low",
            treeId = "tree_no_power",
            type = "RESULT",
            title = "باتری ضعیف یا خالی",
            description = "ولتاژ باتری کمتر از حد نرمال است.",
            question = null,
            instruction = "باتری را شارژ کنید یا تعویض نمایید. اگر بعد از شارژ مشکل حل نشد، باتری خراب است.",
            orderIndex = 2,
            isRoot = false
        ),
        // Node: Battery voltage is normal, check power button
        DiagnosisNodeEntity(
            id = "node_check_power_button",
            treeId = "tree_no_power",
            type = "QUESTION",
            title = "بررسی کلید Power",
            description = "باتری ولتاژ مناسب دارد. کلید Power را بررسی کنید.",
            question = "آیا با فشار دادن کلید Power واکنشی مشاهده می‌کنید؟",
            instruction = "کلید Power را چند ثانیه نگه دارید. آیا ویبره، صدا یا علائم جریان‌کشی روی منبع تغذیه مشاهده می‌شود؟",
            orderIndex = 3,
            isRoot = false
        ),
        // Node: Power button not responding, check power rails
        DiagnosisNodeEntity(
            id = "node_check_power_rails",
            treeId = "tree_no_power",
            type = "QUESTION",
            title = "بررسی خطوط تغذیه اصلی",
            description = "کلید Power واکنش ندارد. خطوط تغذیه را بررسی کنید.",
            question = "آیا ولتاژ روی خطوط تغذیه اصلی (VBAT, VPH_PWR) وجود دارد؟",
            instruction = "ولتاژ VBAT و VPH_PWR را روی برد اندازه‌گیری کنید. مقادیر نرمال: 3.7V - 4.4V",
            orderIndex = 4,
            isRoot = false
        ),
        // Node: Power rails OK, check PMIC
        DiagnosisNodeEntity(
            id = "node_check_pmic",
            treeId = "tree_no_power",
            type = "RESULT",
            title = "احتمال خرابی PMIC",
            description = "خطوط تغذیه وجود دارند اما دستگاه روشن نمی‌شود.",
            instruction = "PMIC (Power Management IC) را بررسی کنید. جریان‌کشی غیرطبیعی، داغ شدن PMIC یا عدم تولید ولتاژهای خروجی نشانه خرابی آن است. تست حرارتی و بررسی کوتاه در اطراف PMIC انجام دهید.",
            orderIndex = 5,
            isRoot = false
        ),
        // Node: Power rails not OK
        DiagnosisNodeEntity(
            id = "node_short_circuit",
            treeId = "tree_no_power",
            type = "RESULT",
            title = "کوتاهی در خط تغذیه",
            description = "ولتاژ روی خطوط اصلی وجود ندارد.",
            instruction = "احتمالاً کوتاهی در خط VBAT یا VPH_PWR وجود دارد. با مولتی‌متر حالت Buzzer تست کنید. خازن‌ها و دیودهای اطراف را بررسی نمایید.",
            orderIndex = 6,
            isRoot = false
        ),
        // Node: Power button responding
        DiagnosisNodeEntity(
            id = "node_check_boot_current",
            treeId = "tree_no_power",
            type = "QUESTION",
            title = "بررسی جریان‌کش بوت",
            description = "کلید Power واکنش دارد. جریان‌کشی را بررسی کنید.",
            question = "هنگام فشار دادن Power، آیا جریان لحظه‌ای مشاهده می‌شود؟",
            instruction = "دستگاه را به منبع تغذیه وصل کنید. کلید Power را فشار دهید و جریان را مشاهده کنید.",
            orderIndex = 7,
            isRoot = false
        ),
        // Node: No boot current
        DiagnosisNodeEntity(
            id = "node_no_boot_current",
            treeId = "tree_no_power",
            type = "RESULT",
            title = "عدم جریان‌کشی بوت",
            description = "هیچ جریان لحظه‌ای مشاهده نمی‌شود.",
            instruction = "CPU یا RAM/UFS ممکن است خراب باشند. همچنین کلاک‌های اصلی (Clock) را بررسی کنید. تست حرارتی CPU انجام دهید.",
            orderIndex = 8,
            isRoot = false
        ),
        // Node: Boot current present
        DiagnosisNodeEntity(
            id = "node_boot_current_present",
            treeId = "tree_no_power",
            type = "RESULT",
            title = "جریان‌کشی بوت وجود دارد",
            description = "جریان لحظه‌ای مشاهده می‌شود.",
            instruction = "اگر جریان لحظه‌ای دارد اما لوگو نمی‌آید: مشکل از نرم‌افزار (Bootloop) یا Storage (UFS/eMMC) است. فلش کنید یا Storage را بررسی نمایید. اگر جریان ثابت می‌کشد: کوتاهی یا خرابی PMIC/CPU.",
            orderIndex = 9,
            isRoot = false
        )
    )

    fun diagnosisOptions(): List<DiagnosisOptionEntity> = listOf(
        // From root: check battery voltage
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
        // From check power button
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
        // From check power rails
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
        // From check boot current
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
