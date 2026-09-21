package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.*
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.model.RepairDifficulty
import javax.inject.Inject

class SeedData @Inject constructor() {

    // ═══════════════════════════════════════════════════════════
    //  HELPER: ساخت DeviceModelEntity با فیلدهای اختیاری
    // ═══════════════════════════════════════════════════════════

    private fun model(
        id: String,
        brandId: String,
        name: String,
        nameEn: String,
        modelNumber: String,
        normalizedName: String,
        chipset: String = "",
        batteryCapacity: Int = 0,
        releaseYear: Int = 0,
        cpu: String = "",
        gpu: String = "",
        displayType: String = "",
        displaySize: String = "",
        os: String = ""
    ) = DeviceModelEntity(
        id = id,
        brandId = brandId,
        seriesId = null,
        name = name,
        nameEn = nameEn,
        modelNumber = modelNumber,
        normalizedName = normalizedName,
        chipset = chipset,
        batteryCapacity = batteryCapacity,
        releaseYear = releaseYear,
        cpu = cpu,
        gpu = gpu,
        displayType = displayType,
        displaySize = displaySize,
        os = os
    )

    // ═══════════════════════════════════════════════════════════
    //  BRANDS - 30 برند پرکاربرد
    // ═══════════════════════════════════════════════════════════

    fun brands(): List<BrandEntity> = listOf(
        BrandEntity("brand_samsung", "سامسونگ", "سامسونگ", "Samsung", "samsung"),
        BrandEntity("brand_apple", "اپل", "اپل", "Apple", "apple"),
        BrandEntity("brand_xiaomi", "شیائومی", "شیائومی", "Xiaomi", "xiaomi"),
        BrandEntity("brand_huawei", "هوآوی", "هوآوی", "Huawei", "huawei"),
        BrandEntity("brand_honor", "آنر", "آنر", "Honor", "honor"),
        BrandEntity("brand_oneplus", "وان‌پلاس", "وان پلاس", "OnePlus", "oneplus"),
        BrandEntity("brand_oppo", "اوپو", "اوپو", "Oppo", "oppo"),
        BrandEntity("brand_vivo", "ویوو", "ویوو", "Vivo", "vivo"),
        BrandEntity("brand_realme", "ریلمی", "ریلمی", "Realme", "realme"),
        BrandEntity("brand_nokia", "نوکیا", "نوکیا", "Nokia", "nokia"),
        BrandEntity("brand_sony", "سونی", "سونی", "Sony", "sony"),
        BrandEntity("brand_motorola", "موتورولا", "موتورولا", "Motorola", "motorola"),
        BrandEntity("brand_google", "گوگل پیکسل", "گوگل پیکسل", "Google Pixel", "google-pixel"),
        BrandEntity("brand_nothing", "ناتینگ", "ناتینگ", "Nothing", "nothing"),
        BrandEntity("brand_asus", "ایسوس", "ایسوس", "Asus", "asus"),
        BrandEntity("brand_lenovo", "لنوو", "لنوو", "Lenovo", "lenovo"),
        BrandEntity("brand_tcl", "تی‌سی‌ال", "تی‌سی‌ال", "TCL", "tcl"),
        BrandEntity("brand_infinix", "اینفینیکس", "اینفینیکس", "Infinix", "infinix"),
        BrandEntity("brand_tecno", "تکنو", "تکنو", "Tecno", "tecno"),
        BrandEntity("brand_itel", "آیتل", "آیتل", "Itel", "itel"),
        BrandEntity("brand_meizu", "میزو", "میزو", "Meizu", "meizu"),
        BrandEntity("brand_zte", "زدتی‌ای", "زدتی‌ای", "ZTE", "zte"),
        BrandEntity("brand_lg", "ال‌جی", "ال جی", "LG", "lg"),
        BrandEntity("brand_htc", "اچ‌تی‌سی", "اچ تی سی", "HTC", "htc"),
        BrandEntity("brand_blackberry", "بلک‌بری", "بلک بری", "BlackBerry", "blackberry"),
        BrandEntity("brand_alcatel", "آلکاتل", "آلکاتل", "Alcatel", "alcatel"),
        BrandEntity("brand_sharp", "شارپ", "شارپ", "Sharp", "sharp"),
        BrandEntity("brand_panasonic", "پاناسونیک", "پاناسونیک", "Panasonic", "panasonic"),
        BrandEntity("brand_micromax", "میکرومکس", "میکرومکس", "Micromax", "micromax"),
        BrandEntity("brand_lava", "لاوا", "لاوا", "Lava", "lava")
    )

    // ═══════════════════════════════════════════════════════════
    //  PROBLEM CATEGORIES
    // ═══════════════════════════════════════════════════════════

    fun categories(): List<ProblemCategoryEntity> = listOf(
        ProblemCategoryEntity("power", "روشن شدن / تغذیه", "Power", "power"),
        ProblemCategoryEntity("charging", "شارژ", "Charging", "charging"),
        ProblemCategoryEntity("display", "نمایشگر", "Display", "display"),
        ProblemCategoryEntity("touch", "تاچ", "Touch", "touch"),
        ProblemCategoryEntity("network", "شبکه", "Network", "network"),
        ProblemCategoryEntity("audio", "صدا", "Audio", "audio"),
        ProblemCategoryEntity("camera", "دوربین", "Camera", "camera"),
        ProblemCategoryEntity("software", "نرم‌افزار", "Software", "software"),
        ProblemCategoryEntity("sensor", "سنسور", "Sensor", "sensor"),
        ProblemCategoryEntity("water", "آب‌خوردگی", "Water Damage", "water")
    )

    // ═══════════════════════════════════════════════════════════
    //  MODELS - 150+ مدل پرکاربرد
    // ═══════════════════════════════════════════════════════════

    fun models(): List<DeviceModelEntity> = buildList {
        // ────── SAMSUNG ──────
        add(model("samsung_sm_a525f", "brand_samsung", "Galaxy A52", "Samsung Galaxy A52", "SM-A525F", "galaxy a52", "Snapdragon 720G", 4500, 2021, "Octa-core", "Adreno 618", "Super AMOLED", "6.5\"", "Android 11"))
        add(model("samsung_sm_a515f", "brand_samsung", "Galaxy A51", "Samsung Galaxy A51", "SM-A515F", "galaxy a51", "Exynos 9611", 4000, 2019))
        add(model("samsung_sm_a725f", "brand_samsung", "Galaxy A72", "Samsung Galaxy A72", "SM-A725F", "galaxy a72", "Snapdragon 720G", 5000, 2021))
        add(model("samsung_sm_a135f", "brand_samsung", "Galaxy A13", "Samsung Galaxy A13", "SM-A135F", "galaxy a13", "Exynos 850", 5000, 2022))
        add(model("samsung_sm_a045f", "brand_samsung", "Galaxy A04", "Samsung Galaxy A04", "SM-A045F", "galaxy a04", "Helio P35", 5000, 2022))
        add(model("samsung_sm_a145f", "brand_samsung", "Galaxy A14", "Samsung Galaxy A14", "SM-A145F", "galaxy a14", "Exynos 850", 5000, 2023))
        add(model("samsung_sm_a546b", "brand_samsung", "Galaxy A54", "Samsung Galaxy A54", "SM-A546B", "galaxy a54", "Exynos 1380", 5000, 2023))
        add(model("samsung_sm_s918b", "brand_samsung", "Galaxy S23 Ultra", "Samsung Galaxy S23 Ultra", "SM-S918B", "galaxy s23 ultra", "Snapdragon 8 Gen 2", 5000, 2023))
        add(model("samsung_sm_s911b", "brand_samsung", "Galaxy S23", "Samsung Galaxy S23", "SM-S911B", "galaxy s23", "Snapdragon 8 Gen 2", 3900, 2023))
        add(model("samsung_sm_s908b", "brand_samsung", "Galaxy S22 Ultra", "Samsung Galaxy S22 Ultra", "SM-S908B", "galaxy s22 ultra", "Snapdragon 8 Gen 1", 5000, 2022))
        add(model("samsung_sm_g991b", "brand_samsung", "Galaxy S21 FE", "Samsung Galaxy S21 FE", "SM-G991B", "galaxy s21 fe", "Snapdragon 888", 4500, 2022))
        add(model("samsung_sm_g998b", "brand_samsung", "Galaxy S21 Ultra", "Samsung Galaxy S21 Ultra", "SM-G998B", "galaxy s21 ultra", "Exynos 2100", 5000, 2021))
        add(model("samsung_sm_g975f", "brand_samsung", "Galaxy S10 Plus", "Samsung Galaxy S10 Plus", "SM-G975F", "galaxy s10 plus", "Exynos 9820", 4100, 2019))
        add(model("samsung_sm_n975f", "brand_samsung", "Galaxy Note 10 Plus", "Samsung Galaxy Note 10 Plus", "SM-N975F", "galaxy note 10 plus", "Exynos 9825", 4300, 2019))
        add(model("samsung_sm_n986b", "brand_samsung", "Galaxy Note 20 Ultra", "Samsung Galaxy Note 20 Ultra", "SM-N986B", "galaxy note 20 ultra", "Exynos 990", 4500, 2020))
        add(model("samsung_sm_a127f", "brand_samsung", "Galaxy A12", "Samsung Galaxy A12", "SM-A127F", "galaxy a12", "Helio P35", 5000, 2020))
        add(model("samsung_sm_a226b", "brand_samsung", "Galaxy A22 5G", "Samsung Galaxy A22 5G", "SM-A226B", "galaxy a22 5g", "Dimensity 700", 5000, 2021))
        add(model("samsung_sm_a336b", "brand_samsung", "Galaxy A33 5G", "Samsung Galaxy A33 5G", "SM-A336B", "galaxy a33 5g", "Exynos 1280", 5000, 2022))
        add(model("samsung_sm_a536b", "brand_samsung", "Galaxy A53 5G", "Samsung Galaxy A53 5G", "SM-A536B", "galaxy a53 5g", "Exynos 1280", 5000, 2022))
        add(model("samsung_sm_f926b", "brand_samsung", "Galaxy Z Fold 4", "Samsung Galaxy Z Fold 4", "SM-F926B", "galaxy z fold 4", "Snapdragon 8+ Gen 1", 4400, 2022))

        // ────── APPLE ──────
        add(model("apple_iphone_15_pro_max", "brand_apple", "iPhone 15 Pro Max", "Apple iPhone 15 Pro Max", "A2849", "iphone 15 pro max", "Apple A17 Pro", 4441, 2023))
        add(model("apple_iphone_15_pro", "brand_apple", "iPhone 15 Pro", "Apple iPhone 15 Pro", "A2848", "iphone 15 pro", "Apple A17 Pro", 3274, 2023))
        add(model("apple_iphone_15", "brand_apple", "iPhone 15", "Apple iPhone 15", "A3090", "iphone 15", "Apple A16 Bionic", 3349, 2023))
        add(model("apple_iphone_14_pro_max", "brand_apple", "iPhone 14 Pro Max", "Apple iPhone 14 Pro Max", "A2894", "iphone 14 pro max", "Apple A16 Bionic", 4323, 2022))
        add(model("apple_iphone_14_pro", "brand_apple", "iPhone 14 Pro", "Apple iPhone 14 Pro", "A2890", "iphone 14 pro", "Apple A16 Bionic", 3200, 2022))
        add(model("apple_iphone_14", "brand_apple", "iPhone 14", "Apple iPhone 14", "A2882", "iphone 14", "Apple A15 Bionic", 3279, 2022))
        add(model("apple_iphone_13_pro_max", "brand_apple", "iPhone 13 Pro Max", "Apple iPhone 13 Pro Max", "A2643", "iphone 13 pro max", "Apple A15 Bionic", 4352, 2021))
        add(model("apple_iphone_13_pro", "brand_apple", "iPhone 13 Pro", "Apple iPhone 13 Pro", "A2638", "iphone 13 pro", "Apple A15 Bionic", 3095, 2021))
        add(model("apple_iphone_13", "brand_apple", "iPhone 13", "Apple iPhone 13", "A2633", "iphone 13", "Apple A15 Bionic", 3240, 2021))
        add(model("apple_iphone_12_pro_max", "brand_apple", "iPhone 12 Pro Max", "Apple iPhone 12 Pro Max", "A2411", "iphone 12 pro max", "Apple A14 Bionic", 3687, 2020))
        add(model("apple_iphone_12_pro", "brand_apple", "iPhone 12 Pro", "Apple iPhone 12 Pro", "A2407", "iphone 12 pro", "Apple A14 Bionic", 2815, 2020))
        add(model("apple_iphone_12", "brand_apple", "iPhone 12", "Apple iPhone 12", "A2403", "iphone 12", "Apple A14 Bionic", 2815, 2020))
        add(model("apple_iphone_11_pro_max", "brand_apple", "iPhone 11 Pro Max", "Apple iPhone 11 Pro Max", "A2218", "iphone 11 pro max", "Apple A13 Bionic", 3969, 2019))
        add(model("apple_iphone_11", "brand_apple", "iPhone 11", "Apple iPhone 11", "A2221", "iphone 11", "Apple A13 Bionic", 3110, 2019))
        add(model("apple_iphone_xr", "brand_apple", "iPhone XR", "Apple iPhone XR", "A1984", "iphone xr", "Apple A12 Bionic", 2942, 2018))
        add(model("apple_iphone_xs_max", "brand_apple", "iPhone XS Max", "Apple iPhone XS Max", "A1921", "iphone xs max", "Apple A12 Bionic", 3174, 2018))
        add(model("apple_iphone_x", "brand_apple", "iPhone X", "Apple iPhone X", "A1901", "iphone x", "Apple A11 Bionic", 2716, 2017))
        add(model("apple_iphone_se_2022", "brand_apple", "iPhone SE 2022", "Apple iPhone SE (2022)", "A2783", "iphone se 2022", "Apple A15 Bionic", 2018, 2022))

        // ────── XIAOMI ──────
        add(model("xiaomi_m2101k7ag", "brand_xiaomi", "Redmi Note 10", "Xiaomi Redmi Note 10", "M2101K7AG", "redmi note 10", "Snapdragon 678", 5000, 2021))
        add(model("xiaomi_2201117ty", "brand_xiaomi", "Redmi Note 11", "Xiaomi Redmi Note 11", "2201117TY", "redmi note 11", "Snapdragon 680", 5000, 2022))
        add(model("xiaomi_23021raby", "brand_xiaomi", "Redmi Note 12", "Xiaomi Redmi Note 12", "23021RABY", "redmi note 12", "Snapdragon 685", 5000, 2023))
        add(model("xiaomi_2312draby", "brand_xiaomi", "Redmi Note 13", "Xiaomi Redmi Note 13", "2312DRABY", "redmi note 13", "Snapdragon 685", 5000, 2024))
        add(model("xiaomi_2201123g", "brand_xiaomi", "Xiaomi 12", "Xiaomi 12", "2201123G", "xiaomi 12", "Snapdragon 8 Gen 1", 4500, 2022))
        add(model("xiaomi_2210132g", "brand_xiaomi", "Xiaomi 13", "Xiaomi 13", "2210132G", "xiaomi 13", "Snapdragon 8 Gen 2", 4500, 2023))
        add(model("xiaomi_23127pn0cg", "brand_xiaomi", "Xiaomi 14", "Xiaomi 14", "23127PN0CG", "xiaomi 14", "Snapdragon 8 Gen 3", 4610, 2024))
        add(model("xiaomi_21061119ag", "brand_xiaomi", "Redmi 10", "Xiaomi Redmi 10", "21061119AG", "redmi 10", "Helio G88", 5000, 2021))
        add(model("xiaomi_220733sg", "brand_xiaomi", "Redmi 10A", "Xiaomi Redmi 10A", "220733SG", "redmi 10a", "Helio G25", 5000, 2022))
        add(model("xiaomi_23053rn02y", "brand_xiaomi", "Redmi 12", "Xiaomi Redmi 12", "23053RN02Y", "redmi 12", "Helio G88", 5000, 2023))
        add(model("xiaomi_23077raby", "brand_xiaomi", "Redmi 13C", "Xiaomi Redmi 13C", "23077RABY", "redmi 13c", "Helio G85", 5000, 2023))
        add(model("xiaomi_22011119uy", "brand_xiaomi", "Poco X4 Pro", "Xiaomi Poco X4 Pro", "22011119UY", "poco x4 pro", "Snapdragon 695", 5000, 2022))
        add(model("xiaomi_23049pcd8g", "brand_xiaomi", "Poco F5", "Xiaomi Poco F5", "23049PCD8G", "poco f5", "Snapdragon 7+ Gen 2", 5000, 2023))
        add(model("xiaomi_2311drk48g", "brand_xiaomi", "Poco X6 Pro", "Xiaomi Poco X6 Pro", "2311DRK48G", "poco x6 pro", "Dimensity 8300 Ultra", 5000, 2024))
        add(model("xiaomi_2109119dg", "brand_xiaomi", "Xiaomi 11T Pro", "Xiaomi 11T Pro", "2109119DG", "xiaomi 11t pro", "Snapdragon 888", 5000, 2021))
        add(model("xiaomi_m2007j3sy", "brand_xiaomi", "Mi 10T Pro", "Xiaomi Mi 10T Pro", "M2007J3SY", "mi 10t pro", "Snapdragon 865", 5000, 2020))
        add(model("xiaomi_2107119dc", "brand_xiaomi", "Xiaomi 11 Lite 5G NE", "Xiaomi 11 Lite 5G NE", "2107119DC", "xiaomi 11 lite 5g ne", "Snapdragon 778G", 4250, 2021))
        add(model("xiaomi_22081212ug", "brand_xiaomi", "Xiaomi 12T Pro", "Xiaomi 12T Pro", "22081212UG", "xiaomi 12t pro", "Snapdragon 8+ Gen 1", 5000, 2022))

        // ────── HUAWEI ──────
        add(model("huawei_p30_pro", "brand_huawei", "P30 Pro", "Huawei P30 Pro", "VOG-L29", "p30 pro", "Kirin 980", 4200, 2019))
        add(model("huawei_p40_pro", "brand_huawei", "P40 Pro", "Huawei P40 Pro", "ELS-NX9", "p40 pro", "Kirin 990 5G", 4200, 2020))
        add(model("huawei_p50_pro", "brand_huawei", "P50 Pro", "Huawei P50 Pro", "JAD-LX9", "p50 pro", "Snapdragon 888 4G", 4360, 2021))
        add(model("huawei_mate_40_pro", "brand_huawei", "Mate 40 Pro", "Huawei Mate 40 Pro", "NOH-NX9", "mate 40 pro", "Kirin 9000", 4400, 2020))
        add(model("huawei_mate_50_pro", "brand_huawei", "Mate 50 Pro", "Huawei Mate 50 Pro", "DCO-LX9", "mate 50 pro", "Snapdragon 8+ Gen 1 4G", 4700, 2022))
        add(model("huawei_nova_9", "brand_huawei", "Nova 9", "Huawei Nova 9", "NAM-LX9", "nova 9", "Snapdragon 778G 4G", 4300, 2021))
        add(model("huawei_nova_10", "brand_huawei", "Nova 10", "Huawei Nova 10", "NCO-LX1", "nova 10", "Snapdragon 778G 4G", 4000, 2022))
        add(model("huawei_y9a", "brand_huawei", "Y9a", "Huawei Y9a", "FRL-L22", "y9a", "Helio G80", 4300, 2020))
        add(model("huawei_y7a", "brand_huawei", "Y7a", "Huawei Y7a", "PPA-LX2A", "y7a", "Kirin 710A", 5000, 2020))
        add(model("huawei_p_smart_2021", "brand_huawei", "P Smart 2021", "Huawei P Smart 2021", "PPA-LX2B", "p smart 2021", "Kirin 710A", 5000, 2021))
        add(model("huawei_nova_7i", "brand_huawei", "Nova 7i", "Huawei Nova 7i", "JNY-LX1", "nova 7i", "Kirin 810", 4200, 2020))
        add(model("huawei_mate_20_pro", "brand_huawei", "Mate 20 Pro", "Huawei Mate 20 Pro", "LYA-L29", "mate 20 pro", "Kirin 980", 4200, 2018))

        // ────── HONOR ──────
        add(model("honor_50", "brand_honor", "Honor 50", "Honor 50", "NTH-NX9", "honor 50", "Snapdragon 778G", 4300, 2021))
        add(model("honor_60", "brand_honor", "Honor 60", "Honor 60", "RVL-AL09", "honor 60", "Snapdragon 778G+", 4800, 2021))
        add(model("honor_70", "brand_honor", "Honor 70", "Honor 70", "FNE-NX9", "honor 70", "Snapdragon 778G+", 4800, 2022))
        add(model("honor_90", "brand_honor", "Honor 90", "Honor 90", "REA-NX9", "honor 90", "Snapdragon 7 Gen 1", 5000, 2023))
        add(model("honor_x9a", "brand_honor", "Honor X9a", "Honor X9a", "RKY-LX1", "honor x9a", "Snapdragon 695", 5100, 2023))
        add(model("honor_x8", "brand_honor", "Honor X8", "Honor X8", "ANY-LX1", "honor x8", "Snapdragon 680", 4000, 2022))
        add(model("honor_magic_5_pro", "brand_honor", "Magic 5 Pro", "Honor Magic 5 Pro", "PGT-N19", "magic 5 pro", "Snapdragon 8 Gen 2", 5100, 2023))
        add(model("honor_magic_4_pro", "brand_honor", "Magic 4 Pro", "Honor Magic 4 Pro", "LGE-NX9", "magic 4 pro", "Snapdragon 8 Gen 1", 4600, 2022))

        // ────── ONEPLUS ──────
        add(model("oneplus_11", "brand_oneplus", "OnePlus 11", "OnePlus 11", "CPH2449", "oneplus 11", "Snapdragon 8 Gen 2", 5000, 2023))
        add(model("oneplus_10_pro", "brand_oneplus", "OnePlus 10 Pro", "OnePlus 10 Pro", "NE2213", "oneplus 10 pro", "Snapdragon 8 Gen 1", 5000, 2022))
        add(model("oneplus_9_pro", "brand_oneplus", "OnePlus 9 Pro", "OnePlus 9 Pro", "LE2123", "oneplus 9 pro", "Snapdragon 888", 4500, 2021))
        add(model("oneplus_9", "brand_oneplus", "OnePlus 9", "OnePlus 9", "LE2113", "oneplus 9", "Snapdragon 888", 4500, 2021))
        add(model("oneplus_nord_2", "brand_oneplus", "Nord 2", "OnePlus Nord 2", "DN2103", "nord 2", "Dimensity 1200", 4500, 2021))
        add(model("oneplus_nord_3", "brand_oneplus", "Nord 3", "OnePlus Nord 3", "CPH2493", "nord 3", "Dimensity 9000", 5000, 2023))
        add(model("oneplus_nord_ce_2", "brand_oneplus", "Nord CE 2", "OnePlus Nord CE 2", "IV2201", "nord ce 2", "Dimensity 900", 4500, 2022))
        add(model("oneplus_8_pro", "brand_oneplus", "OnePlus 8 Pro", "OnePlus 8 Pro", "IN2023", "oneplus 8 pro", "Snapdragon 865", 4510, 2020))

        // ────── OPPO ──────
        add(model("oppo_reno_8", "brand_oppo", "Reno 8", "Oppo Reno 8", "CPH2359", "reno 8", "Dimensity 1300", 4500, 2022))
        add(model("oppo_reno_8_pro", "brand_oppo", "Reno 8 Pro", "Oppo Reno 8 Pro", "CPH2357", "reno 8 pro", "Dimensity 8100-Max", 4500, 2022))
        add(model("oppo_find_x5", "brand_oppo", "Find X5", "Oppo Find X5", "CPH2307", "find x5", "Snapdragon 888", 4800, 2022))
        add(model("oppo_find_x5_pro", "brand_oppo", "Find X5 Pro", "Oppo Find X5 Pro", "CPH2305", "find x5 pro", "Snapdragon 8 Gen 1", 5000, 2022))
        add(model("oppo_a16", "brand_oppo", "A16", "Oppo A16", "CPH2269", "a16", "Helio G35", 5000, 2021))
        add(model("oppo_a54", "brand_oppo", "A54", "Oppo A54", "CPH2239", "a54", "Helio P35", 5000, 2021))
        add(model("oppo_a57", "brand_oppo", "A57", "Oppo A57", "CPH2387", "a57", "Snapdragon 680", 5000, 2022))
        add(model("oppo_a78", "brand_oppo", "A78", "Oppo A78", "CPH2565", "a78", "Snapdragon 680", 5000, 2023))

        // ────── VIVO ──────
        add(model("vivo_v27", "brand_vivo", "V27", "Vivo V27", "V2231", "v27", "Dimensity 7200", 4600, 2023))
        add(model("vivo_v25", "brand_vivo", "V25", "Vivo V25", "V2202", "v25", "Dimensity 900", 4500, 2022))
        add(model("vivo_v23", "brand_vivo", "V23", "Vivo V23", "V2130", "v23", "Dimensity 920", 4200, 2022))
        add(model("vivo_y22", "brand_vivo", "Y22", "Vivo Y22", "V2207", "y22", "Helio G85", 5000, 2022))
        add(model("vivo_y35", "brand_vivo", "Y35", "Vivo Y35", "V2205", "y35", "Snapdragon 680", 5000, 2022))
        add(model("vivo_y17", "brand_vivo", "Y17", "Vivo Y17", "V1901", "y17", "Helio P35", 5000, 2019))
        add(model("vivo_x90_pro", "brand_vivo", "X90 Pro", "Vivo X90 Pro", "V2219", "x90 pro", "Dimensity 9200", 4870, 2023))
        add(model("vivo_x80", "brand_vivo", "X80", "Vivo X80", "V2183", "x80", "Dimensity 9000", 4500, 2022))

        // ────── REALME ──────
        add(model("realme_11_pro", "brand_realme", "11 Pro", "Realme 11 Pro", "RMX3771", "11 pro", "Dimensity 7050", 5000, 2023))
        add(model("realme_10_pro", "brand_realme", "10 Pro", "Realme 10 Pro", "RMX3660", "10 pro", "Snapdragon 695", 5000, 2022))
        add(model("realme_9_pro", "brand_realme", "9 Pro", "Realme 9 Pro", "RMX3471", "9 pro", "Snapdragon 695", 5000, 2022))
        add(model("realme_gt_neo_3", "brand_realme", "GT Neo 3", "Realme GT Neo 3", "RMX3561", "gt neo 3", "Dimensity 8100", 5000, 2022))
        add(model("realme_c55", "brand_realme", "C55", "Realme C55", "RMX3710", "c55", "Helio G88", 5000, 2023))
        add(model("realme_c35", "brand_realme", "C35", "Realme C35", "RMX3511", "c35", "Unisoc Tiger T616", 5000, 2022))
        add(model("realme_narzo_60", "brand_realme", "Narzo 60", "Realme Narzo 60", "RMX3750", "narzo 60", "Dimensity 6020", 5000, 2023))
        add(model("realme_gt_2", "brand_realme", "GT 2", "Realme GT 2", "RMX3311", "gt 2", "Snapdragon 888", 5000, 2022))

        // ────── NOKIA ──────
        add(model("nokia_g21", "brand_nokia", "G21", "Nokia G21", "TA-1418", "g21", "Unisoc T606", 5050, 2022))
        add(model("nokia_g50", "brand_nokia", "G50", "Nokia G50", "TA-1362", "g50", "Snapdragon 480 5G", 5000, 2021))
        add(model("nokia_c21", "brand_nokia", "C21", "Nokia C21", "TA-1352", "c21", "Unisoc SC9863A", 3000, 2021))
        add(model("nokia_x20", "brand_nokia", "X20", "Nokia X20", "TA-1341", "x20", "Snapdragon 480 5G", 4470, 2021))
        add(model("nokia_8_3_5g", "brand_nokia", "8.3 5G", "Nokia 8.3 5G", "TA-1243", "8.3 5g", "Snapdragon 765G", 4500, 2020))

        // ────── SONY ──────
        add(model("sony_xperia_1_v", "brand_sony", "Xperia 1 V", "Sony Xperia 1 V", "XQ-DQ54", "xperia 1 v", "Snapdragon 8 Gen 2", 5000, 2023))
        add(model("sony_xperia_1_iv", "brand_sony", "Xperia 1 IV", "Sony Xperia 1 IV", "XQ-CT54", "xperia 1 iv", "Snapdragon 8 Gen 1", 5000, 2022))
        add(model("sony_xperia_5_iv", "brand_sony", "Xperia 5 IV", "Sony Xperia 5 IV", "XQ-CQ54", "xperia 5 iv", "Snapdragon 8 Gen 1", 5000, 2022))
        add(model("sony_xperia_10_iv", "brand_sony", "Xperia 10 IV", "Sony Xperia 10 IV", "XQ-CC54", "xperia 10 iv", "Snapdragon 695", 5000, 2022))

        // ────── MOTOROLA ──────
        add(model("motorola_edge_40", "brand_motorola", "Edge 40", "Motorola Edge 40", "XT2303", "edge 40", "Dimensity 8020", 4400, 2023))
        add(model("motorola_edge_30", "brand_motorola", "Edge 30", "Motorola Edge 30", "XT2203", "edge 30", "Snapdragon 778G+", 4020, 2022))
        add(model("motorola_moto_g84", "brand_motorola", "Moto G84", "Motorola Moto G84", "XT2347", "moto g84", "Snapdragon 695", 5000, 2023))
        add(model("motorola_moto_g73", "brand_motorola", "Moto G73", "Motorola Moto G73", "XT2333", "moto g73", "Dimensity 930", 5000, 2023))
        add(model("motorola_moto_g62", "brand_motorola", "Moto G62", "Motorola Moto G62", "XT2223", "moto g62", "Snapdragon 480+", 5000, 2022))

        // ────── GOOGLE PIXEL ──────
        add(model("google_pixel_8_pro", "brand_google", "Pixel 8 Pro", "Google Pixel 8 Pro", "GC3VE", "pixel 8 pro", "Google Tensor G3", 5050, 2023))
        add(model("google_pixel_8", "brand_google", "Pixel 8", "Google Pixel 8", "GKWS6", "pixel 8", "Google Tensor G3", 4575, 2023))
        add(model("google_pixel_7_pro", "brand_google", "Pixel 7 Pro", "Google Pixel 7 Pro", "GP4BC", "pixel 7 pro", "Google Tensor G2", 5000, 2022))
        add(model("google_pixel_7", "brand_google", "Pixel 7", "Google Pixel 7", "GVU6C", "pixel 7", "Google Tensor G2", 4355, 2022))
        add(model("google_pixel_6a", "brand_google", "Pixel 6a", "Google Pixel 6a", "GX7AS", "pixel 6a", "Google Tensor", 4410, 2022))
        add(model("google_pixel_6_pro", "brand_google", "Pixel 6 Pro", "Google Pixel 6 Pro", "GLUOG", "pixel 6 pro", "Google Tensor", 5003, 2021))

        // ────── NOTHING ──────
        add(model("nothing_phone_2", "brand_nothing", "Phone (2)", "Nothing Phone (2)", "A065", "phone 2", "Snapdragon 8+ Gen 1", 4700, 2023))
        add(model("nothing_phone_1", "brand_nothing", "Phone (1)", "Nothing Phone (1)", "A063", "phone 1", "Snapdragon 778G+", 4500, 2022))
        add(model("nothing_phone_2a", "brand_nothing", "Phone (2a)", "Nothing Phone (2a)", "A142", "phone 2a", "Dimensity 7200 Pro", 5000, 2024))

        // ────── ASUS ──────
        add(model("asus_zenfone_10", "brand_asus", "Zenfone 10", "Asus Zenfone 10", "AI2302", "zenfone 10", "Snapdragon 8 Gen 2", 4300, 2023))
        add(model("asus_zenfone_9", "brand_asus", "Zenfone 9", "Asus Zenfone 9", "AI2202", "zenfone 9", "Snapdragon 8+ Gen 1", 4300, 2022))
        add(model("asus_rog_phone_7", "brand_asus", "ROG Phone 7", "Asus ROG Phone 7", "AI2205", "rog phone 7", "Snapdragon 8 Gen 2", 6000, 2023))

        // ────── LENOVO ──────
        add(model("lenovo_legion_y70", "brand_lenovo", "Legion Y70", "Lenovo Legion Y70", "L71091", "legion y70", "Snapdragon 8+ Gen 1", 5100, 2022))
        add(model("lenovo_k14_plus", "brand_lenovo", "K14 Plus", "Lenovo K14 Plus", "XT2155", "k14 plus", "Unisoc T610", 5000, 2021))
        add(model("lenovo_tab_p11", "brand_lenovo", "Tab P11", "Lenovo Tab P11", "TB-J606F", "tab p11", "Snapdragon 662", 7700, 2020))
    }

    // ═══════════════════════════════════════════════════════════
    //  PROBLEMS (5 مشکل اولیه)
    // ═══════════════════════════════════════════════════════════

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
        )
    )

    // ═══════════════════════════════════════════════════════════
    //  DIAGNOSIS - Delegation به DiagnosisSeedData
    // ═══════════════════════════════════════════════════════════

    fun diagnosisTrees(): List<DiagnosisTreeEntity> = DiagnosisSeedData.trees()

    fun diagnosisNodes(): List<DiagnosisNodeEntity> = DiagnosisSeedData.nodes()

    fun diagnosisOptions(): List<DiagnosisOptionEntity> = DiagnosisSeedData.options()
}