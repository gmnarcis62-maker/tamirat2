package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.*
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.model.RepairDifficulty
import javax.inject.Inject

class SeedData @Inject constructor() {

    // ═══════════════════════════════════════════════════════════
    //  BRANDS - 30 برند پرکاربرد موبایل
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
        // ────── SAMSUNG (20 مدل) ──────
        add(DeviceModelEntity("samsung_sm_a525f", "brand_samsung", "Galaxy A52", "Samsung Galaxy A52", "SM-A525F", "galaxy a52", chipset = "Snapdragon 720G", batteryCapacity = 4500, releaseYear = 2021, cpu = "Octa-core", gpu = "Adreno 618", displayType = "Super AMOLED", displaySize = "6.5\"", os = "Android 11"))
        add(DeviceModelEntity("samsung_sm_a515f", "brand_samsung", "Galaxy A51", "Samsung Galaxy A51", "SM-A515F", "galaxy a51", chipset = "Exynos 9611", batteryCapacity = 4000, releaseYear = 2019))
        add(DeviceModelEntity("samsung_sm_a725f", "brand_samsung", "Galaxy A72", "Samsung Galaxy A72", "SM-A725F", "galaxy a72", chipset = "Snapdragon 720G", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("samsung_sm_a135f", "brand_samsung", "Galaxy A13", "Samsung Galaxy A13", "SM-A135F", "galaxy a13", chipset = "Exynos 850", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("samsung_sm_a045f", "brand_samsung", "Galaxy A04", "Samsung Galaxy A04", "SM-A045F", "galaxy a04", chipset = "Helio P35", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("samsung_sm_a145f", "brand_samsung", "Galaxy A14", "Samsung Galaxy A14", "SM-A145F", "galaxy a14", chipset = "Exynos 850", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("samsung_sm_a546b", "brand_samsung", "Galaxy A54", "Samsung Galaxy A54", "SM-A546B", "galaxy a54", chipset = "Exynos 1380", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("samsung_sm_s918b", "brand_samsung", "Galaxy S23 Ultra", "Samsung Galaxy S23 Ultra", "SM-S918B", "galaxy s23 ultra", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("samsung_sm_s911b", "brand_samsung", "Galaxy S23", "Samsung Galaxy S23", "SM-S911B", "galaxy s23", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 3900, releaseYear = 2023))
        add(DeviceModelEntity("samsung_sm_s908b", "brand_samsung", "Galaxy S22 Ultra", "Samsung Galaxy S22 Ultra", "SM-S908B", "galaxy s22 ultra", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("samsung_sm_g991b", "brand_samsung", "Galaxy S21 FE", "Samsung Galaxy S21 FE", "SM-G991B", "galaxy s21 fe", chipset = "Snapdragon 888", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("samsung_sm_g998b", "brand_samsung", "Galaxy S21 Ultra", "Samsung Galaxy S21 Ultra", "SM-G998B", "galaxy s21 ultra", chipset = "Exynos 2100", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("samsung_sm_g975f", "brand_samsung", "Galaxy S10 Plus", "Samsung Galaxy S10 Plus", "SM-G975F", "galaxy s10 plus", chipset = "Exynos 9820", batteryCapacity = 4100, releaseYear = 2019))
        add(DeviceModelEntity("samsung_sm_n975f", "brand_samsung", "Galaxy Note 10 Plus", "Samsung Galaxy Note 10 Plus", "SM-N975F", "galaxy note 10 plus", chipset = "Exynos 9825", batteryCapacity = 4300, releaseYear = 2019))
        add(DeviceModelEntity("samsung_sm_n986b", "brand_samsung", "Galaxy Note 20 Ultra", "Samsung Galaxy Note 20 Ultra", "SM-N986B", "galaxy note 20 ultra", chipset = "Exynos 990", batteryCapacity = 4500, releaseYear = 2020))
        add(DeviceModelEntity("samsung_sm_a127f", "brand_samsung", "Galaxy A12", "Samsung Galaxy A12", "SM-A127F", "galaxy a12", chipset = "Helio P35", batteryCapacity = 5000, releaseYear = 2020))
        add(DeviceModelEntity("samsung_sm_a226b", "brand_samsung", "Galaxy A22 5G", "Samsung Galaxy A22 5G", "SM-A226B", "galaxy a22 5g", chipset = "Dimensity 700", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("samsung_sm_a336b", "brand_samsung", "Galaxy A33 5G", "Samsung Galaxy A33 5G", "SM-A336B", "galaxy a33 5g", chipset = "Exynos 1280", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("samsung_sm_a536b", "brand_samsung", "Galaxy A53 5G", "Samsung Galaxy A53 5G", "SM-A536B", "galaxy a53 5g", chipset = "Exynos 1280", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("samsung_sm_f926b", "brand_samsung", "Galaxy Z Fold 4", "Samsung Galaxy Z Fold 4", "SM-F926B", "galaxy z fold 4", chipset = "Snapdragon 8+ Gen 1", batteryCapacity = 4400, releaseYear = 2022))

        // ────── APPLE (18 مدل) ──────
        add(DeviceModelEntity("apple_iphone_15_pro_max", "brand_apple", "iPhone 15 Pro Max", "Apple iPhone 15 Pro Max", "A2849", "iphone 15 pro max", chipset = "Apple A17 Pro", batteryCapacity = 4441, releaseYear = 2023))
        add(DeviceModelEntity("apple_iphone_15_pro", "brand_apple", "iPhone 15 Pro", "Apple iPhone 15 Pro", "A2848", "iphone 15 pro", chipset = "Apple A17 Pro", batteryCapacity = 3274, releaseYear = 2023))
        add(DeviceModelEntity("apple_iphone_15", "brand_apple", "iPhone 15", "Apple iPhone 15", "A3090", "iphone 15", chipset = "Apple A16 Bionic", batteryCapacity = 3349, releaseYear = 2023))
        add(DeviceModelEntity("apple_iphone_14_pro_max", "brand_apple", "iPhone 14 Pro Max", "Apple iPhone 14 Pro Max", "A2894", "iphone 14 pro max", chipset = "Apple A16 Bionic", batteryCapacity = 4323, releaseYear = 2022))
        add(DeviceModelEntity("apple_iphone_14_pro", "brand_apple", "iPhone 14 Pro", "Apple iPhone 14 Pro", "A2890", "iphone 14 pro", chipset = "Apple A16 Bionic", batteryCapacity = 3200, releaseYear = 2022))
        add(DeviceModelEntity("apple_iphone_14", "brand_apple", "iPhone 14", "Apple iPhone 14", "A2882", "iphone 14", chipset = "Apple A15 Bionic", batteryCapacity = 3279, releaseYear = 2022))
        add(DeviceModelEntity("apple_iphone_13_pro_max", "brand_apple", "iPhone 13 Pro Max", "Apple iPhone 13 Pro Max", "A2643", "iphone 13 pro max", chipset = "Apple A15 Bionic", batteryCapacity = 4352, releaseYear = 2021))
        add(DeviceModelEntity("apple_iphone_13_pro", "brand_apple", "iPhone 13 Pro", "Apple iPhone 13 Pro", "A2638", "iphone 13 pro", chipset = "Apple A15 Bionic", batteryCapacity = 3095, releaseYear = 2021))
        add(DeviceModelEntity("apple_iphone_13", "brand_apple", "iPhone 13", "Apple iPhone 13", "A2633", "iphone 13", chipset = "Apple A15 Bionic", batteryCapacity = 3240, releaseYear = 2021))
        add(DeviceModelEntity("apple_iphone_12_pro_max", "brand_apple", "iPhone 12 Pro Max", "Apple iPhone 12 Pro Max", "A2411", "iphone 12 pro max", chipset = "Apple A14 Bionic", batteryCapacity = 3687, releaseYear = 2020))
        add(DeviceModelEntity("apple_iphone_12_pro", "brand_apple", "iPhone 12 Pro", "Apple iPhone 12 Pro", "A2407", "iphone 12 pro", chipset = "Apple A14 Bionic", batteryCapacity = 2815, releaseYear = 2020))
        add(DeviceModelEntity("apple_iphone_12", "brand_apple", "iPhone 12", "Apple iPhone 12", "A2403", "iphone 12", chipset = "Apple A14 Bionic", batteryCapacity = 2815, releaseYear = 2020))
        add(DeviceModelEntity("apple_iphone_11_pro_max", "brand_apple", "iPhone 11 Pro Max", "Apple iPhone 11 Pro Max", "A2218", "iphone 11 pro max", chipset = "Apple A13 Bionic", batteryCapacity = 3969, releaseYear = 2019))
        add(DeviceModelEntity("apple_iphone_11", "brand_apple", "iPhone 11", "Apple iPhone 11", "A2221", "iphone 11", chipset = "Apple A13 Bionic", batteryCapacity = 3110, releaseYear = 2019))
        add(DeviceModelEntity("apple_iphone_xr", "brand_apple", "iPhone XR", "Apple iPhone XR", "A1984", "iphone xr", chipset = "Apple A12 Bionic", batteryCapacity = 2942, releaseYear = 2018))
        add(DeviceModelEntity("apple_iphone_xs_max", "brand_apple", "iPhone XS Max", "Apple iPhone XS Max", "A1921", "iphone xs max", chipset = "Apple A12 Bionic", batteryCapacity = 3174, releaseYear = 2018))
        add(DeviceModelEntity("apple_iphone_x", "brand_apple", "iPhone X", "Apple iPhone X", "A1901", "iphone x", chipset = "Apple A11 Bionic", batteryCapacity = 2716, releaseYear = 2017))
        add(DeviceModelEntity("apple_iphone_se_2022", "brand_apple", "iPhone SE 2022", "Apple iPhone SE (2022)", "A2783", "iphone se 2022", chipset = "Apple A15 Bionic", batteryCapacity = 2018, releaseYear = 2022))

        // ────── XIAOMI (18 مدل) ──────
        add(DeviceModelEntity("xiaomi_m2101k7ag", "brand_xiaomi", "Redmi Note 10", "Xiaomi Redmi Note 10", "M2101K7AG", "redmi note 10", chipset = "Snapdragon 678", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("xiaomi_2201117ty", "brand_xiaomi", "Redmi Note 11", "Xiaomi Redmi Note 11", "2201117TY", "redmi note 11", chipset = "Snapdragon 680", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("xiaomi_23021raby", "brand_xiaomi", "Redmi Note 12", "Xiaomi Redmi Note 12", "23021RABY", "redmi note 12", chipset = "Snapdragon 685", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("xiaomi_2312draby", "brand_xiaomi", "Redmi Note 13", "Xiaomi Redmi Note 13", "2312DRABY", "redmi note 13", chipset = "Snapdragon 685", batteryCapacity = 5000, releaseYear = 2024))
        add(DeviceModelEntity("xiaomi_2201123g", "brand_xiaomi", "Xiaomi 12", "Xiaomi 12", "2201123G", "xiaomi 12", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("xiaomi_2210132g", "brand_xiaomi", "Xiaomi 13", "Xiaomi 13", "2210132G", "xiaomi 13", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 4500, releaseYear = 2023))
        add(DeviceModelEntity("xiaomi_23127pn0cg", "brand_xiaomi", "Xiaomi 14", "Xiaomi 14", "23127PN0CG", "xiaomi 14", chipset = "Snapdragon 8 Gen 3", batteryCapacity = 4610, releaseYear = 2024))
        add(DeviceModelEntity("xiaomi_21061119ag", "brand_xiaomi", "Redmi 10", "Xiaomi Redmi 10", "21061119AG", "redmi 10", chipset = "Helio G88", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("xiaomi_220733sg", "brand_xiaomi", "Redmi 10A", "Xiaomi Redmi 10A", "220733SG", "redmi 10a", chipset = "Helio G25", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("xiaomi_23053rn02y", "brand_xiaomi", "Redmi 12", "Xiaomi Redmi 12", "23053RN02Y", "redmi 12", chipset = "Helio G88", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("xiaomi_23077raby", "brand_xiaomi", "Redmi 13C", "Xiaomi Redmi 13C", "23077RABY", "redmi 13c", chipset = "Helio G85", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("xiaomi_22011119uy", "brand_xiaomi", "Poco X4 Pro", "Xiaomi Poco X4 Pro", "22011119UY", "poco x4 pro", chipset = "Snapdragon 695", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("xiaomi_23049pcd8g", "brand_xiaomi", "Poco F5", "Xiaomi Poco F5", "23049PCD8G", "poco f5", chipset = "Snapdragon 7+ Gen 2", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("xiaomi_2311drk48g", "brand_xiaomi", "Poco X6 Pro", "Xiaomi Poco X6 Pro", "2311DRK48G", "poco x6 pro", chipset = "Dimensity 8300 Ultra", batteryCapacity = 5000, releaseYear = 2024))
        add(DeviceModelEntity("xiaomi_2109119dg", "brand_xiaomi", "Xiaomi 11T Pro", "Xiaomi 11T Pro", "2109119DG", "xiaomi 11t pro", chipset = "Snapdragon 888", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("xiaomi_m2007j3sy", "brand_xiaomi", "Mi 10T Pro", "Xiaomi Mi 10T Pro", "M2007J3SY", "mi 10t pro", chipset = "Snapdragon 865", batteryCapacity = 5000, releaseYear = 2020))
        add(DeviceModelEntity("xiaomi_2107119dc", "brand_xiaomi", "Xiaomi 11 Lite 5G NE", "Xiaomi 11 Lite 5G NE", "2107119DC", "xiaomi 11 lite 5g ne", chipset = "Snapdragon 778G", batteryCapacity = 4250, releaseYear = 2021))
        add(DeviceModelEntity("xiaomi_22081212ug", "brand_xiaomi", "Xiaomi 12T Pro", "Xiaomi 12T Pro", "22081212UG", "xiaomi 12t pro", chipset = "Snapdragon 8+ Gen 1", batteryCapacity = 5000, releaseYear = 2022))

        // ────── HUAWEI (12 مدل) ──────
        add(DeviceModelEntity("huawei_p30_pro", "brand_huawei", "P30 Pro", "Huawei P30 Pro", "VOG-L29", "p30 pro", chipset = "Kirin 980", batteryCapacity = 4200, releaseYear = 2019))
        add(DeviceModelEntity("huawei_p40_pro", "brand_huawei", "P40 Pro", "Huawei P40 Pro", "ELS-NX9", "p40 pro", chipset = "Kirin 990 5G", batteryCapacity = 4200, releaseYear = 2020))
        add(DeviceModelEntity("huawei_p50_pro", "brand_huawei", "P50 Pro", "Huawei P50 Pro", "JAD-LX9", "p50 pro", chipset = "Snapdragon 888 4G", batteryCapacity = 4360, releaseYear = 2021))
        add(DeviceModelEntity("huawei_mate_40_pro", "brand_huawei", "Mate 40 Pro", "Huawei Mate 40 Pro", "NOH-NX9", "mate 40 pro", chipset = "Kirin 9000", batteryCapacity = 4400, releaseYear = 2020))
        add(DeviceModelEntity("huawei_mate_50_pro", "brand_huawei", "Mate 50 Pro", "Huawei Mate 50 Pro", "DCO-LX9", "mate 50 pro", chipset = "Snapdragon 8+ Gen 1 4G", batteryCapacity = 4700, releaseYear = 2022))
        add(DeviceModelEntity("huawei_nova_9", "brand_huawei", "Nova 9", "Huawei Nova 9", "NAM-LX9", "nova 9", chipset = "Snapdragon 778G 4G", batteryCapacity = 4300, releaseYear = 2021))
        add(DeviceModelEntity("huawei_nova_10", "brand_huawei", "Nova 10", "Huawei Nova 10", "NCO-LX1", "nova 10", chipset = "Snapdragon 778G 4G", batteryCapacity = 4000, releaseYear = 2022))
        add(DeviceModelEntity("huawei_y9a", "brand_huawei", "Y9a", "Huawei Y9a", "FRL-L22", "y9a", chipset = "Helio G80", batteryCapacity = 4300, releaseYear = 2020))
        add(DeviceModelEntity("huawei_y7a", "brand_huawei", "Y7a", "Huawei Y7a", "PPA-LX2", "y7a", chipset = "Kirin 710A", batteryCapacity = 5000, releaseYear = 2020))
        add(DeviceModelEntity("huawei_p_smart_2021", "brand_huawei", "P Smart 2021", "Huawei P Smart 2021", "PPA-LX2", "p smart 2021", chipset = "Kirin 710A", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("huawei_nova_7i", "brand_huawei", "Nova 7i", "Huawei Nova 7i", "JNY-LX1", "nova 7i", chipset = "Kirin 810", batteryCapacity = 4200, releaseYear = 2020))
        add(DeviceModelEntity("huawei_mate_20_pro", "brand_huawei", "Mate 20 Pro", "Huawei Mate 20 Pro", "LYA-L29", "mate 20 pro", chipset = "Kirin 980", batteryCapacity = 4200, releaseYear = 2018))

        // ────── HONOR (8 مدل) ──────
        add(DeviceModelEntity("honor_50", "brand_honor", "Honor 50", "Honor 50", "NTH-NX9", "honor 50", chipset = "Snapdragon 778G", batteryCapacity = 4300, releaseYear = 2021))
        add(DeviceModelEntity("honor_60", "brand_honor", "Honor 60", "Honor 60", "RVL-AL09", "honor 60", chipset = "Snapdragon 778G+", batteryCapacity = 4800, releaseYear = 2021))
        add(DeviceModelEntity("honor_70", "brand_honor", "Honor 70", "Honor 70", "FNE-NX9", "honor 70", chipset = "Snapdragon 778G+", batteryCapacity = 4800, releaseYear = 2022))
        add(DeviceModelEntity("honor_90", "brand_honor", "Honor 90", "Honor 90", "REA-NX9", "honor 90", chipset = "Snapdragon 7 Gen 1", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("honor_x9a", "brand_honor", "Honor X9a", "Honor X9a", "RKY-LX1", "honor x9a", chipset = "Snapdragon 695", batteryCapacity = 5100, releaseYear = 2023))
        add(DeviceModelEntity("honor_x8", "brand_honor", "Honor X8", "Honor X8", "ANY-LX1", "honor x8", chipset = "Snapdragon 680", batteryCapacity = 4000, releaseYear = 2022))
        add(DeviceModelEntity("honor_magic_5_pro", "brand_honor", "Magic 5 Pro", "Honor Magic 5 Pro", "PGT-N19", "magic 5 pro", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 5100, releaseYear = 2023))
        add(DeviceModelEntity("honor_magic_4_pro", "brand_honor", "Magic 4 Pro", "Honor Magic 4 Pro", "LGE-NX9", "magic 4 pro", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 4600, releaseYear = 2022))

        // ────── ONEPLUS (8 مدل) ──────
        add(DeviceModelEntity("oneplus_11", "brand_oneplus", "OnePlus 11", "OnePlus 11", "CPH2449", "oneplus 11", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("oneplus_10_pro", "brand_oneplus", "OnePlus 10 Pro", "OnePlus 10 Pro", "NE2213", "oneplus 10 pro", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("oneplus_9_pro", "brand_oneplus", "OnePlus 9 Pro", "OnePlus 9 Pro", "LE2123", "oneplus 9 pro", chipset = "Snapdragon 888", batteryCapacity = 4500, releaseYear = 2021))
        add(DeviceModelEntity("oneplus_9", "brand_oneplus", "OnePlus 9", "OnePlus 9", "LE2113", "oneplus 9", chipset = "Snapdragon 888", batteryCapacity = 4500, releaseYear = 2021))
        add(DeviceModelEntity("oneplus_nord_2", "brand_oneplus", "Nord 2", "OnePlus Nord 2", "DN2103", "nord 2", chipset = "Dimensity 1200", batteryCapacity = 4500, releaseYear = 2021))
        add(DeviceModelEntity("oneplus_nord_3", "brand_oneplus", "Nord 3", "OnePlus Nord 3", "CPH2493", "nord 3", chipset = "Dimensity 9000", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("oneplus_nord_ce_2", "brand_oneplus", "Nord CE 2", "OnePlus Nord CE 2", "IV2201", "nord ce 2", chipset = "Dimensity 900", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("oneplus_8_pro", "brand_oneplus", "OnePlus 8 Pro", "OnePlus 8 Pro", "IN2023", "oneplus 8 pro", chipset = "Snapdragon 865", batteryCapacity = 4510, releaseYear = 2020))

        // ────── OPPO (8 مدل) ──────
        add(DeviceModelEntity("oppo_reno_8", "brand_oppo", "Reno 8", "Oppo Reno 8", "CPH2359", "reno 8", chipset = "Dimensity 1300", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("oppo_reno_8_pro", "brand_oppo", "Reno 8 Pro", "Oppo Reno 8 Pro", "CPH2357", "reno 8 pro", chipset = "Dimensity 8100-Max", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("oppo_find_x5", "brand_oppo", "Find X5", "Oppo Find X5", "CPH2307", "find x5", chipset = "Snapdragon 888", batteryCapacity = 4800, releaseYear = 2022))
        add(DeviceModelEntity("oppo_find_x5_pro", "brand_oppo", "Find X5 Pro", "Oppo Find X5 Pro", "CPH2305", "find x5 pro", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("oppo_a16", "brand_oppo", "A16", "Oppo A16", "CPH2269", "a16", chipset = "Helio G35", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("oppo_a54", "brand_oppo", "A54", "Oppo A54", "CPH2239", "a54", chipset = "Helio P35", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("oppo_a57", "brand_oppo", "A57", "Oppo A57", "CPH2387", "a57", chipset = "Snapdragon 680", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("oppo_a78", "brand_oppo", "A78", "Oppo A78", "CPH2565", "a78", chipset = "Snapdragon 680", batteryCapacity = 5000, releaseYear = 2023))

        // ────── VIVO (8 مدل) ──────
        add(DeviceModelEntity("vivo_v27", "brand_vivo", "V27", "Vivo V27", "V2231", "v27", chipset = "Dimensity 7200", batteryCapacity = 4600, releaseYear = 2023))
        add(DeviceModelEntity("vivo_v25", "brand_vivo", "V25", "Vivo V25", "V2202", "v25", chipset = "Dimensity 900", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("vivo_v23", "brand_vivo", "V23", "Vivo V23", "V2130", "v23", chipset = "Dimensity 920", batteryCapacity = 4200, releaseYear = 2022))
        add(DeviceModelEntity("vivo_y22", "brand_vivo", "Y22", "Vivo Y22", "V2207", "y22", chipset = "Helio G85", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("vivo_y35", "brand_vivo", "Y35", "Vivo Y35", "V2205", "y35", chipset = "Snapdragon 680", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("vivo_y17", "brand_vivo", "Y17", "Vivo Y17", "V1901", "y17", chipset = "Helio P35", batteryCapacity = 5000, releaseYear = 2019))
        add(DeviceModelEntity("vivo_x90_pro", "brand_vivo", "X90 Pro", "Vivo X90 Pro", "V2219", "x90 pro", chipset = "Dimensity 9200", batteryCapacity = 4870, releaseYear = 2023))
        add(DeviceModelEntity("vivo_x80", "brand_vivo", "X80", "Vivo X80", "V2183", "x80", chipset = "Dimensity 9000", batteryCapacity = 4500, releaseYear = 2022))

        // ────── REALME (8 مدل) ──────
        add(DeviceModelEntity("realme_11_pro", "brand_realme", "11 Pro", "Realme 11 Pro", "RMX3771", "11 pro", chipset = "Dimensity 7050", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("realme_10_pro", "brand_realme", "10 Pro", "Realme 10 Pro", "RMX3660", "10 pro", chipset = "Snapdragon 695", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("realme_9_pro", "brand_realme", "9 Pro", "Realme 9 Pro", "RMX3471", "9 pro", chipset = "Snapdragon 695", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("realme_gt_neo_3", "brand_realme", "GT Neo 3", "Realme GT Neo 3", "RMX3561", "gt neo 3", chipset = "Dimensity 8100", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("realme_c55", "brand_realme", "C55", "Realme C55", "RMX3710", "c55", chipset = "Helio G88", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("realme_c35", "brand_realme", "C35", "Realme C35", "RMX3511", "c35", chipset = "Unisoc Tiger T616", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("realme_narzo_60", "brand_realme", "Narzo 60", "Realme Narzo 60", "RMX3750", "narzo 60", chipset = "Dimensity 6020", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("realme_gt_2", "brand_realme", "GT 2", "Realme GT 2", "RMX3311", "gt 2", chipset = "Snapdragon 888", batteryCapacity = 5000, releaseYear = 2022))

        // ────── NOKIA (5 مدل) ──────
        add(DeviceModelEntity("nokia_g21", "brand_nokia", "G21", "Nokia G21", "TA-1418", "g21", chipset = "Unisoc T606", batteryCapacity = 5050, releaseYear = 2022))
        add(DeviceModelEntity("nokia_g50", "brand_nokia", "G50", "Nokia G50", "TA-1362", "g50", chipset = "Snapdragon 480 5G", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("nokia_c21", "brand_nokia", "C21", "Nokia C21", "TA-1352", "c21", chipset = "Unisoc SC9863A", batteryCapacity = 3000, releaseYear = 2021))
        add(DeviceModelEntity("nokia_x20", "brand_nokia", "X20", "Nokia X20", "TA-1341", "x20", chipset = "Snapdragon 480 5G", batteryCapacity = 4470, releaseYear = 2021))
        add(DeviceModelEntity("nokia_8_3_5g", "brand_nokia", "8.3 5G", "Nokia 8.3 5G", "TA-1243", "8.3 5g", chipset = "Snapdragon 765G", batteryCapacity = 4500, releaseYear = 2020))

        // ────── SONY (4 مدل) ──────
        add(DeviceModelEntity("sony_xperia_1_v", "brand_sony", "Xperia 1 V", "Sony Xperia 1 V", "XQ-DQ54", "xperia 1 v", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("sony_xperia_1_iv", "brand_sony", "Xperia 1 IV", "Sony Xperia 1 IV", "XQ-CT54", "xperia 1 iv", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("sony_xperia_5_iv", "brand_sony", "Xperia 5 IV", "Sony Xperia 5 IV", "XQ-CQ54", "xperia 5 iv", chipset = "Snapdragon 8 Gen 1", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("sony_xperia_10_iv", "brand_sony", "Xperia 10 IV", "Sony Xperia 10 IV", "XQ-CC54", "xperia 10 iv", chipset = "Snapdragon 695", batteryCapacity = 5000, releaseYear = 2022))

        // ────── MOTOROLA (5 مدل) ──────
        add(DeviceModelEntity("motorola_edge_40", "brand_motorola", "Edge 40", "Motorola Edge 40", "XT2303", "edge 40", chipset = "Dimensity 8020", batteryCapacity = 4400, releaseYear = 2023))
        add(DeviceModelEntity("motorola_edge_30", "brand_motorola", "Edge 30", "Motorola Edge 30", "XT2203", "edge 30", chipset = "Snapdragon 778G+", batteryCapacity = 4020, releaseYear = 2022))
        add(DeviceModelEntity("motorola_moto_g84", "brand_motorola", "Moto G84", "Motorola Moto G84", "XT2347", "moto g84", chipset = "Snapdragon 695", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("motorola_moto_g73", "brand_motorola", "Moto G73", "Motorola Moto G73", "XT2333", "moto g73", chipset = "Dimensity 930", batteryCapacity = 5000, releaseYear = 2023))
        add(DeviceModelEntity("motorola_moto_g62", "brand_motorola", "Moto G62", "Motorola Moto G62", "XT2223", "moto g62", chipset = "Snapdragon 480+", batteryCapacity = 5000, releaseYear = 2022))

        // ────── GOOGLE PIXEL (6 مدل) ──────
        add(DeviceModelEntity("google_pixel_8_pro", "brand_google", "Pixel 8 Pro", "Google Pixel 8 Pro", "GC3VE", "pixel 8 pro", chipset = "Google Tensor G3", batteryCapacity = 5050, releaseYear = 2023))
        add(DeviceModelEntity("google_pixel_8", "brand_google", "Pixel 8", "Google Pixel 8", "GKWS6", "pixel 8", chipset = "Google Tensor G3", batteryCapacity = 4575, releaseYear = 2023))
        add(DeviceModelEntity("google_pixel_7_pro", "brand_google", "Pixel 7 Pro", "Google Pixel 7 Pro", "GP4BC", "pixel 7 pro", chipset = "Google Tensor G2", batteryCapacity = 5000, releaseYear = 2022))
        add(DeviceModelEntity("google_pixel_7", "brand_google", "Pixel 7", "Google Pixel 7", "GVU6C", "pixel 7", chipset = "Google Tensor G2", batteryCapacity = 4355, releaseYear = 2022))
        add(DeviceModelEntity("google_pixel_6a", "brand_google", "Pixel 6a", "Google Pixel 6a", "GX7AS", "pixel 6a", chipset = "Google Tensor", batteryCapacity = 4410, releaseYear = 2022))
        add(DeviceModelEntity("google_pixel_6_pro", "brand_google", "Pixel 6 Pro", "Google Pixel 6 Pro", "GLUOG", "pixel 6 pro", chipset = "Google Tensor", batteryCapacity = 5003, releaseYear = 2021))

        // ────── NOTHING (3 مدل) ──────
        add(DeviceModelEntity("nothing_phone_2", "brand_nothing", "Phone (2)", "Nothing Phone (2)", "A065", "phone 2", chipset = "Snapdragon 8+ Gen 1", batteryCapacity = 4700, releaseYear = 2023))
        add(DeviceModelEntity("nothing_phone_1", "brand_nothing", "Phone (1)", "Nothing Phone (1)", "A063", "phone 1", chipset = "Snapdragon 778G+", batteryCapacity = 4500, releaseYear = 2022))
        add(DeviceModelEntity("nothing_phone_2a", "brand_nothing", "Phone (2a)", "Nothing Phone (2a)", "A142", "phone 2a", chipset = "Dimensity 7200 Pro", batteryCapacity = 5000, releaseYear = 2024))

        // ────── ASUS (3 مدل) ──────
        add(DeviceModelEntity("asus_zenfone_10", "brand_asus", "Zenfone 10", "Asus Zenfone 10", "AI2302", "zenfone 10", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 4300, releaseYear = 2023))
        add(DeviceModelEntity("asus_zenfone_9", "brand_asus", "Zenfone 9", "Asus Zenfone 9", "AI2202", "zenfone 9", chipset = "Snapdragon 8+ Gen 1", batteryCapacity = 4300, releaseYear = 2022))
        add(DeviceModelEntity("asus_rog_phone_7", "brand_asus", "ROG Phone 7", "Asus ROG Phone 7", "AI2205", "rog phone 7", chipset = "Snapdragon 8 Gen 2", batteryCapacity = 6000, releaseYear = 2023))

        // ────── LENOVO (3 مدل) ──────
        add(DeviceModelEntity("lenovo_legion_y70", "brand_lenovo", "Legion Y70", "Lenovo Legion Y70", "L71091", "legion y70", chipset = "Snapdragon 8+ Gen 1", batteryCapacity = 5100, releaseYear = 2022))
        add(DeviceModelEntity("lenovo_k14_plus", "brand_lenovo", "K14 Plus", "Lenovo K14 Plus", "XT2155", "k14 plus", chipset = "Unisoc T610", batteryCapacity = 5000, releaseYear = 2021))
        add(DeviceModelEntity("lenovo_tab_p11", "brand_lenovo", "Tab P11", "Lenovo Tab P11", "TB-J606F", "tab p11", chipset = "Snapdragon 662", batteryCapacity = 7700, releaseYear = 2020))
    }

    // ═══════════════════════════════════════════════════════════
    //  PROBLEMS (5 مشکل اولیه - برای سازگاری با ProblemSeedData)
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
    //  DIAGNOSIS TREES
    // ═══════════════════════════════════════════════════════════

    fun diagnosisTrees(): List<DiagnosisTreeEntity> = listOf(
        DiagnosisTreeEntity("tree_no_power", "problem_no_power", null, "درخت عیب‌یابی: روشن نشدن", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_charge", "problem_no_charge", null, "درخت عیب‌یابی: شارژ نشدن", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_bootloop", "problem_bootloop", null, "درخت عیب‌یابی: بوت‌لوپ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_touch", "problem_touch_issue", null, "درخت عیب‌یابی: تاچ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_signal", "problem_no_signal", null, "درخت عیب‌یابی: آنتن", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_display", "problem_no_display", null, "درخت عیب‌یابی: صفحه سیاه", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_sound", "problem_no_sound", null, "درخت عیب‌یابی: بی‌صدا", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_imei", "problem_no_imei", null, "درخت عیب‌یابی: IMEI", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_battery_drain", "problem_battery_drain", null, "درخت عیب‌یابی: خالی شدن سریع", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_wifi", "problem_wifi_issue", null, "درخت عیب‌یابی: WiFi", "راهنمای مرحله‌ای", 1)
    )

    // ═══════════════════════════════════════════════════════════
    //  DIAGNOSIS NODES (درخت روشن نشدن - نمونه)
    // ═══════════════════════════════════════════════════════════

    fun diagnosisNodes(): List<DiagnosisNodeEntity> = listOf(
        DiagnosisNodeEntity("node_check_battery_voltage", "ولتاژ باتری دستگاه چقدر است؟", "ابتدا ولتاژ باتری را با مولتی‌متر بررسی کنید.", emptyList(), true, false, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_battery_low", "", "ولتاژ باتری کمتر از حد نرمال است.", emptyList(), false, true, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_check_power_button", "آیا با فشار دادن کلید Power واکنشی مشاهده می‌کنید؟", "باتری ولتاژ مناسب دارد. کلید Power را بررسی کنید.", emptyList(), false, false, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_check_power_rails", "آیا ولتاژ روی خطوط تغذیه اصلی (VBAT, VPH_PWR) وجود دارد؟", "کلید Power واکنش ندارد. خطوط تغذیه را بررسی کنید.", emptyList(), false, false, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_check_pmic", "", "خطوط تغذیه وجود دارند اما دستگاه روشن نمی‌شود.", emptyList(), false, true, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_short_circuit", "", "ولتاژ روی خطوط اصلی وجود ندارد.", emptyList(), false, true, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_check_boot_current", "هنگام فشار دادن Power، آیا جریان لحظه‌ای مشاهده می‌شود؟", "کلید Power واکنش دارد.", emptyList(), false, false, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_no_boot_current", "", "هیچ جریان لحظه‌ای مشاهده نمی‌شود.", emptyList(), false, true, "problem_no_power", null, null),
        DiagnosisNodeEntity("node_boot_current_present", "", "جریان لحظه‌ای مشاهده می‌شود.", emptyList(), false, true, "problem_no_power", null, null)
    )

    fun diagnosisOptions(): List<DiagnosisOptionEntity> = listOf(
        DiagnosisOptionEntity("opt_battery_low", "node_check_battery_voltage", "ولتاژ کمتر از 3.5V است", "low", "node_battery_low", "voltage < 3.5"),
        DiagnosisOptionEntity("opt_battery_normal", "node_check_battery_voltage", "ولتاژ بین 3.5V تا 4.4V است", "normal", "node_check_power_button", "voltage >= 3.5 && voltage <= 4.4"),
        DiagnosisOptionEntity("opt_battery_unknown", "node_check_battery_voltage", "اطلاع ندارم", "unknown", "node_check_power_button", null),
        DiagnosisOptionEntity("opt_power_no_response", "node_check_power_button", "خیر، هیچ واکنشی ندارد", "no_response", "node_check_power_rails", null),
        DiagnosisOptionEntity("opt_power_response", "node_check_power_button", "بله، واکنش دارد", "response", "node_check_boot_current", null),
        DiagnosisOptionEntity("opt_rails_ok", "node_check_power_rails", "بله، ولتاژ وجود دارد", "ok", "node_check_pmic", null),
        DiagnosisOptionEntity("opt_rails_not_ok", "node_check_power_rails", "خیر، ولتاژی وجود ندارد", "not_ok", "node_short_circuit", null),
        DiagnosisOptionEntity("opt_no_boot_current", "node_check_boot_current", "خیر، جریانی مشاهده نمی‌شود", "no_current", "node_no_boot_current", null),
        DiagnosisOptionEntity("opt_boot_current_present", "node_check_boot_current", "بله، جریان لحظه‌ای دارد", "current_present", "node_boot_current_present", null)
    )
}