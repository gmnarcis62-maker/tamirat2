package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.RepairGuideEntity
import red.line.tamirkar.domain.model.RepairDifficulty
import red.line.tamirkar.domain.model.RepairStep
import red.line.tamirkar.domain.model.TestStep

object RepairGuideSeedData {

    fun getAll(): List<RepairGuideEntity> = listOf(
        // Guide for No Power
        RepairGuideEntity(
            id = "guide_no_power",
            problemId = "prob_no_power",
            title = "راهنمای تعمیر عدم روشن شدن",
            description = "این راهنما مراحل عیب‌یابی و تعمیر گوشی را که هیچ علائم حیاتی ندارد، به صورت گام‌به‌گام توضیح می‌دهد.",
            steps = listOf(
                RepairStep(
                    stepNumber = 1,
                    title = "بررسی اولیه و تمیزکاری",
                    description = "گوشی را باز کنید و برد را از نظر خوردگی، سوختگی یا آبخوردگی بررسی کنید.",
                    detailedInstructions = listOf(
                        "گوشی را با ساکشن و هیتر باز کنید (برای گوشی‌هایی با پشت شیشه‌ای)",
                        "پیچ‌های فریم را باز کنید",
                        "برد را خارج کنید و زیر میکروسکوپ بررسی کنید",
                        "به دنبال علائم آبخوردگی (اکسیداسیون، خوردگی) باشید",
                        "ICهای تغذیه را از نظر ترک یا سوختگی بررسی کنید",
                        "اگر آبخوردگی وجود دارد، برد را در ultrasonic cleaner با مایع تمیزکننده قرار دهید"
                    ),
                    warnings = listOf(
                        "قبل از روشن کردن، حتماً اتصالی را بررسی کنید",
                        "اگر بوی سوختگی می‌آید، IC تغذیه را تعویض نکنید تا علت اصلی برطرف نشه"
                    ),
                    estimatedTime = "۱۰-۱۵ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("ساکشن LCD", "هیتر", "میکروسکوپ"),
                    partsNeeded = listOf()
                ),
                RepairStep(
                    stepNumber = 2,
                    title = "تست با منبع تغذیه (Power Supply)",
                    description = "برد را به منبع تغذیه متصل کنید و جریان مصرفی را بررسی کنید.",
                    detailedInstructions = listOf(
                        "منبع تغذیه را روی ۴.۲ ولت و ۲ آمپر تنظیم کنید",
                        "سیم مثبت را به VBAT (معمولاً +) و سیم منفی را به GND وصل کنید",
                        "دکمه پاور را فشار دهید و جریان را بخوانید",
                        "اگر جریان ۰ میلی‌آمپر است: مسیر VBAT یا VBUS قطع است",
                        "اگر جریان ثابت و بالا (۵۰۰mA+) است: اتصالی وجود دارد",
                        "اگر جریان ۲۰-۵۰mA و ثابت است: مشکل در IC PMU یا CPU است",
                        "اگر جریان ۱۰۰-۲۰۰mA و reboot می‌شود: مشکل در eMMC یا CPU است"
                    ),
                    warnings = listOf(
                        "بیش از ۲ آمپر جریان ندهید",
                        "اگر اتصالی وجود دارد، با تزریق ولتاژ نقطه داغ را پیدا کنید (روش Thermal Camera یا الکل)"
                    ),
                    estimatedTime = "۵-۱۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("منبع تغذیه", "مولتی‌متر"),
                    partsNeeded = listOf()
                ),
                RepairStep(
                    stepNumber = 3,
                    title = "بررسی مسیر VBUS و VBAT",
                    description = "مسیر تغذیه ا سوکت شارژ تا IC PMU را بررسی کنید.",
                    detailedInstructions = listOf(
                        "دو سر سوکت شارژ را با مولتی‌متر buzzer mode تست کنید",
                        "مسیر VBUS (معمولاً ۵V) را از سوکت تا IC شارژ بررسی کنید",
                        "مسیر VBAT (۳.۷-۴.۲V) را از IC شارژ تا IC PMU بررسی کنید",
                        "دیودها و فیوزهای مسیر را تست کنید",
                        "اگر پارگی وجود دارد، jumper بزنید",
                        "مقاومت‌های pull-up/pull-down را بررسی کنید"
                    ),
                    warnings = listOf("بعضی مسیرها زیر IC هستند و نیاز به باز کردن IC دارند"),
                    estimatedTime = "۱۵-۳۰ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("مولتی‌متر", "میکروسکوپ", "قلم هویه"),
                    partsNeeded = listOf("سیم جامپر")
                ),
                RepairStep(
                    stepNumber = 4,
                    title = "بررسی IC تغذیه (PMU)",
                    description = "IC PMU را بررسی کنید؛ این IC ولتاژهای مختلف را برای CPU، RAM و سایر بخش‌ها تولید می‌کند.",
                    detailedInstructions = listOf(
                        "دیتاشیت IC PMU را پیدا کنید (مثلاً PM8150, PM8150L, PM8008)",
                        "پین‌های ورودی (VBAT, VBUS) را تست کنید",
                        "پین‌های خروجی (۱.۸V, ۰.۸V, ۳.۳V, etc.) را با اسیلوسکوپ بررسی کنید",
                        "اگر ورودی دارید اما خروجی ندارید: IC PMU خراب است",
                        "اگر هیچ ورودی ندارید: مسیر قطع است",
                        "IC PMU را با هیتر و میکروسکوپ تعویض کنید"
                    ),
                    warnings = listOf(
                        "IC PMU معمولاً underfill دارد و باز کردن آن سخت است",
                        "CPU و RAM ممکن است در کنار PMU باشند؛ مراقب حرارت باشید"
                    ),
                    estimatedTime = "۳۰-۶۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("هیتر", "میکروسکوپ", "اسیلوسکوپ"),
                    partsNeeded = listOf("IC PMU")
                ),
                RepairStep(
                    stepNumber = 5,
                    title = "بررسی کویل و خازن‌های تغذیه",
                    description = "کویل‌های Boost/Buck و خازن‌های فیلتر را بررسی کنید.",
                    detailedInstructions = listOf(
                        "کویل‌های اطراف IC PMU را بررسی کنید (معمولاً ۲-۴ کویل)",
                        "مقاومت DC کویل‌ها را تست کنید (معمولاً ۰.۵-۲ اهم)",
                        "خازن‌های سرامیکی را از نظر ترک یا سوختگی بررسی کنید",
                        "خازن‌ها را در buzzer mode تست کنید (باید short نشوند)",
                        "کویل یا خازن خراب را تعویض کنید"
                    ),
                    warnings = listOf("خازن‌های short شده را پیدا و تعویض کنید"),
                    estimatedTime = "۱۵-۳۰ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("مولتی‌متر", "هیتر"),
                    partsNeeded = listOf("کویل Boost", "خازن فیلتر")
                ),
                RepairStep(
                    stepNumber = 6,
                    title = "بررسی CPU و RAM",
                    description = "اگر همه مراحل قبلی OK بود، مشکل ممکن است در CPU یا RAM باشد.",
                    detailedInstructions = listOf(
                        "CPU را زیر میکروسکوپ بررسی کنید (ترک، Reballed شدن)",
                        "RAM را بررسی کنید (اگر جداگانه است)",
                        "اگر CPU Reballed شده: ممکن است cold joint داشته باشد",
                        "CPU را Reball کنید (بسیار تخصصی)",
                        "اگر مشکل حل نشد: برد scrap است"
                    ),
                    warnings = listOf(
                        "Reballing CPU نیاز به تجهیزات تخصصی دارد",
                        "بسیاری از CPUها encrypted هستند و تعویض غیرممکن است"
                    ),
                    estimatedTime = "۶۰-۱۸۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("هیتر BGA", "استنسیل Reballing", "خمیر BGA"),
                    partsNeeded = listOf("خمیر BGA")
                ),
                RepairStep(
                    stepNumber = 7,
                    title = "تست نهایی",
                    description = "بعد از تعمیر، گوشی را تست کنید.",
                    detailedInstructions = listOf(
                        "برد را به منبع تغذیه وصل کنید و جریان را بررسی کنید",
                        "اگر جریان نرمال است (۲۰-۱۰۰mA spike و سپس کاهش): OK است",
                        "LCD را وصل کنید و روشن کنید",
                        "تاچ، آنتن، صدا و دوربین را تست کنید",
                        "گوشی را برای ۳۰ دقیقه stress test کنید"
                    ),
                    warnings = listOf("اگر دوباره خاموش شد، IC PMU را دوباره بررسی کنید"),
                    estimatedTime = "۱۰-۲۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("منبع تغذیه"),
                    partsNeeded = listOf()
                ),
                RepairStep(
                    stepNumber = 8,
                    title = "جمع‌آوری و تحویل",
                    description = "گوشی را مونتاژ کنید و به مشتری تحویل دهید.",
                    detailedInstructions = listOf(
                        "خمیر حرارتی جدید روی ICها بزنید",
                        "برد را در فریم قرار دهید",
                        "LCD را با چسب جدید نصب کنید",
                        "پیچ‌ها را با گشتاور مناسب ببندید",
                        "گوشی را تست نهایی کنید"
                    ),
                    warnings = listOf("از چسب اصلی LCD استفاده کنید"),
                    estimatedTime = "۱۵-۳۰ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("پیچ‌گوشتی", "چسب LCD"),
                    partsNeeded = listOf("چسب LCD")
                )
            ),
            warnings = listOf(
                "قبل از هر کاری، جریان مصرفی را با منبع تغذیه بررسی کنید",
                "اگر بوی سوختگی می‌آید، IC تغذیه را تعویض نکنید تا علت اصلی برطرف نشده",
                "Reballing CPU آخرین راه‌حل است"
            ),
            tips = listOf(
                "از thermal camera برای پیدا کردن نقطه داغ استفاده کنید",
                "دیتاشیت IC PMU را حتماً مطالعه کنید",
                "با الکل روی برد بریزید و روشن کنید؛ قسمت خشک شده نقطه داغ است",
                "جumper VBAT مستقیم به CPU فقط در صورت اطمینان از سلامت CPU"
            ),
            videoUrl = null,
            imageUrls = emptyList(),
            estimatedTime = "۲-۴ ساعت",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("منبع تغذیه", "میکروسکوپ", "هیتر", "مولتیمتر", "اسیلوسکوپ"),
            requiredParts = listOf("IC PMU", "کویل Boost", "خازن فیلتر", "سیم جامپر"),
            prerequisites = listOf(
                "آشنایی با الکترونیک SMD",
                "تسلط بر هیتر و میکروسکوپ",
                "دیتاشیت IC PMU"
            ),
            testSteps = listOf(
                TestStep(1, "اتصال به منبع تغذیه", "جریان ۲۰-۱۰۰mA spike و سپس کاهش", "LCD وصل کنید", "IC PMU را بررسی کنید"),
                TestStep(2, "روشن کردن LCD", "لوگو نمایش داده شود", "تاچ تست کنید", "LCD را بررسی کنید"),
                TestStep(3, "تست آنتن", "شبکه شناسایی شود", "مکالمه تست کنید", "IC Transceiver را بررسی کنید"),
                TestStep(4, "تست ۳۰ دقیقه‌ای", "گوشی خاموش نشود", "تعمیر موفق", "IC PMU را دوباره بررسی کنید")
            )
        ),

        // Guide for No Charge
        RepairGuideEntity(
            id = "guide_no_charge",
            problemId = "prob_no_charge",
            title = "راهنمای تعمیر عدم شارژ",
            description = "گوشی روشن است اما با اتصال شارژر هیچ واکنشی نشان نمی‌دهد.",
            steps = listOf(
                RepairStep(
                    stepNumber = 1,
                    title = "تست با کابل و شارژر سالم",
                    description = "ابتدا کابل و شارژر را با گوشی دیگر تست کنید.",
                    detailedInstructions = listOf(
                        "کابل و شارژر را با گوشی سالم تست کنید",
                        "پورت USB کامپیوتر را هم امتحان کنید",
                        "اگر با کابل دیگر OK شد: کابل خراب است",
                        "اگر مشکل باقی بود: ادامه دهید"
                    ),
                    warnings = listOf("ابتدا نرم‌افزاری را رد کنید"),
                    estimatedTime = "۵ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("شارژر سالم", "کابل سالم"),
                    partsNeeded = listOf()
                ),
                RepairStep(
                    stepNumber = 2,
                    title = "بررسی سوکت شارژ",
                    description = "سوکت شارژ را از نظر خم شدن، کثیفی یا سوختگی بررسی کنید.",
                    detailedInstructions = listOf(
                        "سوکت را با لوپ بررسی کنید",
                        "پین‌های داخل سوکت را بررسی کنید (نباید خم یا اکسیده شده باشند)",
                        "سوکت را با برس و الکل تمیز کنید",
                        "اگر سوکت خراب است: تعویض کنید"
                    ),
                    warnings = listOf("بعضی سوکت‌ها روی FPC هستند و تعویض سخت‌تر است"),
                    estimatedTime = "۱۰-۲۰ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("لوپ", "هیتر", "قلم هویه"),
                    partsNeeded = listOf("سوکت شارژ")
                ),
                RepairStep(
                    stepNumber = 3,
                    title = "بررسی مسیر VBUS",
                    description = "مسیر ۵V از سوکت تا IC شارژ را بررسی کنید.",
                    detailedInstructions = listOf(
                        "مولتی‌متر را روی DC Voltage بگذارید",
                        "پین VBUS سوکت را تست کنید (باید ۵V باشد)",
                        "مسیر VBUS تا IC شارژ را تست کنید",
                        "دیود و فیوز مسیر را بررسی کنید",
                        "اگر پارگی وجود دارد: jumper بزنید"
                    ),
                    warnings = listOf("بعضی گوشی‌ها QHD دارند و پین‌های D+/D- هم مهم هستند"),
                    estimatedTime = "۱۰-۲۰ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("مولتی‌متر"),
                    partsNeeded = listOf("دیود", "فیوز")
                ),
                RepairStep(
                    stepNumber = 4,
                    title = "بررسی IC شارژ",
                    description = "IC شارژ را بررسی و در صورت لزوم تعویض کنید.",
                    detailedInstructions = listOf(
                        "دیتاشیت IC شارژ را پیدا کنید",
                        "ورودی VBUS را تست کنید",
                        "خروجی VBAT (به باتری) را تست کنید",
                        "خروجی VSYS (به سیستم) را تست کنید",
                        "اگر ورودی دارید اما خروجی ندارید: IC شارژ خراب است",
                        "IC را با هیتر تعویض کنید"
                    ),
                    warnings = listOf("بعضی ICها underfill دارند"),
                    estimatedTime = "۳۰-۶۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("هیتر", "میکروسکوپ", "مولتی‌متر"),
                    partsNeeded = listOf("IC شارژ")
                ),
                RepairStep(
                    stepNumber = 5,
                    title = "بررسی باتری",
                    description = "باتری را با تستر یا ولتاژ مستقیم بررسی کنید.",
                    detailedInstructions = listOf(
                        "ولتاژ باتری را تست کنید (باید ۳.۷-۴.۲V باشد)",
                        "اگر ولتاژ زیر ۳V است: باتری deeply discharged است",
                        "باتری ا با منبع تغذیه شارژ کنید (۴.۲V, محدود جریان ۱A)",
                        "اگر باتری باد کرده: تعویض کنید",
                        "اگر Protection Circuit خراب است: باتری را تعویض کنید"
                    ),
                    warnings = listOf("باتری‌های Li-ion خطر آتش‌سوزی دارند"),
                    estimatedTime = "۱۰-۲۰ دقیقه",
                    isCritical = false,
                    toolsNeeded = listOf("مولتی‌متر", "منبع تغذیه"),
                    partsNeeded = listOf("باتری")
                ),
                RepairStep(
                    stepNumber = 6,
                    title = "تست نهایی",
                    description = "بعد از تعمیر، شارژ را تست کنید.",
                    detailedInstructions = listOf(
                        "شارژر را وصل کنید",
                        "علامت شارژ باید نمایش داده شود",
                        "جریان شارژ را با آمپرمتر بررسی کنید",
                        "۳۰ دقیقه شارژ کنید و درصد باتری را بررسی کنید"
                    ),
                    warnings = listOf("اگر شارژ کند است: QHD را بررسی کنید"),
                    estimatedTime = "۱۰-۱۵ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("آمپرمتر"),
                    partsNeeded = listOf()
                )
            ),
            warnings = listOf(
                "ابتدا کابل و شارژر را تست کنید",
                "قبل از تعویض IC شارژ، مسیر VBUS را بررسی کنید"
            ),
            tips = listOf(
                "بعضی ICها نیاز به programming دارند (مثل TI BQ系列)",
                "QHD (Quick Charge Detection) را فراموش نکنید",
                "بعد از تعویض IC، شارژر فست شارژ را تست کنید"
            ),
            videoUrl = null,
            imageUrls = emptyList(),
            estimatedTime = "۱-۲ ساعت",
            difficulty = RepairDifficulty.HARD,
            requiredTools = listOf("میکروسکوپ", "هیتر", "مولتی‌متر", "منبع تغذیه"),
            requiredParts = listOf("سوکت شارژ", "IC شارژ", "باتری"),
            prerequisites = listOf("آشنایی با مدار شارژ"),
            testSteps = listOf(
                TestStep(1, "اتصال شارژر", "علامت شارژ نمایش داده شود", "جریان را بررسی کنید", "IC شارژ را بررسی کنید"),
                TestStep(2, "شارژ ۳۰ دقیقه", "درصد باتری افزایش یابد", "تعمیر موفق", "باتری را بررسی کنید"),
                TestStep(3, "شارژ فست", "جریان بالای ۱A", "OK", "QHD را بررسی کنید")
            )
        ),

        // Guide for Bootloop
        RepairGuideEntity(
            id = "guide_bootloop",
            problemId = "prob_bootloop",
            title = "راهنمای تعمیر بوت لوپ",
            description = "گوشی روی لوگو می‌ماند و دوباره راه‌اندازی می‌شود.",
            steps = listOf(
                RepairStep(
                    stepNumber = 1,
                    title = "تست نرم‌افزاری",
                    description = "ابتدا با فلش نرم‌افزاری مشکل را رد کنید.",
                    detailedInstructions = listOf(
                        "گوشی را به Recovery Mode ببرید",
                        "Wipe Cache Partition را امتحان کنید",
                        "Factory Reset را انجام دهید (اگر داده‌ها مهم نیستند)",
                        "با Odin/Samsung Tool/SP Flash Tool فلش کنید",
                        "اگر بعد از فلش OK شد: نرم‌افزاری بود",
                        "اگر مشکل باقی بود: سخت‌افزاری است"
                    ),
                    warnings = listOf("Factory Reset همه داده‌ها را پاک می‌کند"),
                    estimatedTime = "۲۰-۶۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("کامپیوتر", "کابل USB"),
                    partsNeeded = listOf()
                ),
                RepairStep(
                    stepNumber = 2,
                    title = "بررسی eMMC/NAND",
                    description = "eMMC یا NAND را بررسی کنید؛ Bad Blocks باعث بوت لوپ می‌شوند.",
                    detailedInstructions = listOf(
                        "با نرم‌افزار eMMC Test (مثل Z3X, Octoplus) تست کنید",
                        "Health Status eMMC را بررسی کنید",
                        "اگر Health < ۲۰٪: eMMC را تعویض کنید",
                        "اگر eMMC soldered است: Reballing کنید",
                        "برای iPhone: با ۳uTools NAND health را بررسی کند"
                    ),
                    warnings = listOf(
                        "eMMC encrypted است و تعویض نیاز به programming دارد",
                        "Backup GPT و Boot Partition را قبل از تعویض ذخیره کنید"
                    ),
                    estimatedTime = "۶۰-۱۸۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("باکس فلش", "هیتر BGA"),
                    partsNeeded = listOf("eMMC/NAND")
                ),
                RepairStep(
                    stepNumber = 3,
                    title = "بررسی IC تغذیه",
                    description = "نوسان ولتاژ باعث ری‌استارت CPU می‌شود.",
                    detailedInstructions = listOf(
                        "برد را به منبع تغذیه وصل کنید",
                        "جریان را در حالت بوت بررسی کنید",
                        "اگر جریان ۱۰۰-۲۰۰mA و reboot می‌شود: مشکل تغذیه یا CPU است",
                        "ولتاژهای CPU (VDD_CORE, VDD_GPU) را با اسیلوسکوپ بررسی کنید",
                        "اگر نوسان دارید: IC PMU را تعویض کنید"
                    ),
                    warnings = listOf("نوسان ولتاژ CPU می‌تواند CPU را بسوزاند"),
                    estimatedTime = "۳۰-۶۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("منبع تغذیه", "اسیلوسکوپ"),
                    partsNeeded = listOf("IC PMU")
                ),
                RepairStep(
                    stepNumber = 4,
                    title = "بررسی CPU",
                    description = "اگر همه مراحل قبلی OK بود، CPU مشکل دارد.",
                    detailedInstructions = listOf(
                        "CPU را زیر میکروسکوپ بررسی کنید",
                        "اگر Reballed شده: cold joint دارد",
                        "CPU را Reball کنید",
                        "اگر مشکل حل نشد: برد scrap است"
                    ),
                    warnings = listOf("CPU encrypted است و تعویض غیرممکن"),
                    estimatedTime = "۶۰-۱۸۰ دقیقه",
                    isCritical = true,
                    toolsNeeded = listOf("هیتر BGA", "استنسیل"),
                    partsNeeded = listOf("خمیر BGA")
                )
            ),
            warnings = listOf(
                "ابتدا با فلش نرم‌افزاری تست کنید",
                "اگر بعد از فلش مشکل باقی بود: سخت‌افزار است"
            ),
            tips = listOf(
                "بوت لوپ بعد از آپدیت = نرم‌افزاری",
                "بوت لوپ ناگهانی = eMMC یا CPU",
                "بوت لوپ با گرم شدن = IC PMU"
            ),
            videoUrl = null,
            imageUrls = emptyList(),
            estimatedTime = "۲-۵ ساعت",
            difficulty = RepairDifficulty.EXPERT,
            requiredTools = listOf("باکس فلش", "هیتر BGA", "اسیلوسکوپ"),
            requiredParts = listOf("eMMC", "IC PMU"),
            prerequisites = listOf("تسلط بر فلش کردن", "Reballing"),
            testSteps = listOf(
                TestStep(1, "فلش نرم‌افزاری", "بعد از فلش روشن شود", "تعمیر موفق", "eMMC را بررسی کنید"),
                TestStep(2, "بوت کامل", "تا home screen بالا بیاید", "OK", "CPU را بررسی کنید"),
                TestStep(3, "تست ۳۰ دقیقه‌ای", "ری‌استارت نشود", "OK", "IC PMU را بررسی کنید")
            )
        )
    )

    fun getGuideForProblem(problemId: String): RepairGuideEntity? {
        return getAll().find { it.problemId == problemId }
    }
}
