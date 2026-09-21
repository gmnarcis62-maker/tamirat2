package red.line.tamirkar.data.local.seed

import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.DiagnosisOptionEntity
import red.line.tamirkar.data.local.entity.DiagnosisTreeEntity

object DiagnosisSeedData {

    private fun n(
        id: String,
        problemId: String,
        q: String,
        desc: String? = null,
        start: Boolean = false,
        end: Boolean = false
    ) = DiagnosisNodeEntity(
        id = id, question = q, description = desc, options = emptyList(),
        isStartNode = start, isEndNode = end, problemId = problemId,
        guideId = null, severity = null
    )

    private fun o(
        id: String,
        nodeId: String,
        title: String,
        next: String? = null,
        value: String? = null
    ) = DiagnosisOptionEntity(
        id = id, nodeId = nodeId, title = title, value = value,
        nextNodeId = next, condition = null
    )

    fun trees(): List<DiagnosisTreeEntity> = listOf(
        DiagnosisTreeEntity("tree_no_power", "problem_no_power", null, "عیب‌یابی: روشن نشدن", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_restart_random", "problem_restart_random", null, "عیب‌یابی: ری‌استارت تصادفی", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_bootloop", "problem_bootloop", null, "عیب‌یابی: بوت‌لوپ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_shutdown_low_battery", "problem_shutdown_low_battery", null, "عیب‌یابی: خاموشی در درصد بالا", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_display", "problem_no_display", null, "عیب‌یابی: بدون تصویر", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_power_button_stuck", "problem_power_button_stuck", null, "عیب‌یابی: کلید Power", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_dead_after_water", "problem_dead_after_water", null, "عیب‌یابی: آب‌خوردگی", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_hang_restart", "problem_hang_restart", null, "عیب‌یابی: هنگ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_auto_shutdown", "problem_auto_shutdown", null, "عیب‌یابی: خاموشی خودکار", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_vibration", "problem_no_vibration", null, "عیب‌یابی: ویبره", "راهنمای مرحله‌ای", 1),

        DiagnosisTreeEntity("tree_no_charge", "problem_no_charge", null, "عیب‌یابی: شارژ نشدن", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_slow_charge", "problem_slow_charge", null, "عیب‌یابی: شارژ کند", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_battery_drain", "problem_battery_drain", null, "عیب‌یابی: خالی شدن سریع", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_battery_swollen", "problem_battery_swollen", null, "عیب‌یابی: باد کردن باتری", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_overheat_charging", "problem_overheat_charging", null, "عیب‌یابی: داغ شدن در شارژ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_charging_port_damage", "problem_charging_port_damage", null, "عیب‌یابی: درگاه شارژ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_wireless_charge", "problem_wireless_charge_issue", null, "عیب‌یابی: شارژ بی‌سیم", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_battery_fake_percent", "problem_battery_fake_percent", null, "عیب‌یابی: درصد نادرست", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_no_charge_wireless", "problem_no_charge_wireless", null, "عیب‌یابی: شارژ بی‌سیم کند", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_charging_not_detected", "problem_charging_not_detected", null, "عیب‌یابی: عدم تشخیص شارژر", "راهنمای مرحله‌ای", 1),

        DiagnosisTreeEntity("tree_lcd_broken", "problem_lcd_broken", null, "عیب‌یابی: شکستگی LCD", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_touch_issue", "problem_touch_issue", null, "عیب‌یابی: تاچ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_ghost_touch", "problem_ghost_touch", null, "عیب‌یابی: تاچ خودکار", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_touch_partial", "problem_touch_partial", null, "عیب‌یابی: تاچ جزئی", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_display_lines", "problem_display_lines", null, "عیب‌یابی: خطوط روی صفحه", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_display_flicker", "problem_display_flicker", null, "عیب‌یابی: پرش تصویر", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_display_white", "problem_display_white", null, "عیب‌یابی: صفحه سفید", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_dead_pixels", "problem_dead_pixels", null, "عیب‌یابی: پیکسل سوخته", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_low_brightness", "problem_low_brightness", null, "عیب‌یابی: روشنایی کم", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_display_burn_in", "problem_display_burn_in", null, "عیب‌یابی: Burn-in", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_touch_delay", "problem_touch_delay", null, "عیب‌یابی: تاخیر تاچ", "راهنمای مرحله‌ای", 1),
        DiagnosisTreeEntity("tree_screen_protector", "problem_screen_protector_lifted", null, "عیب‌یابی: محافظ صفحه", "راهنمای مرحله‌ای", 1)
    )

    fun nodes(): List<DiagnosisNodeEntity> = buildList {

        // ═══════════ POWER ═══════════
        // no_power
        add(n("np_q1", "problem_no_power", "ولتاژ باتری چند ولت است؟", "با مولتی‌متر در حالت DC اندازه بگیرید", start = true))
        add(n("np_q2", "problem_no_power", "آیا کلید Power واکنش نشان می‌دهد؟", "ویبره، صدا یا تغییر جریان"))
        add(n("np_q3", "problem_no_power", "آیا جریان لحظه‌ای روی DC Power دیده می‌شود؟"))
        add(n("np_q4", "problem_no_power", "آیا ولتاژ VBAT و VPH_PWR روی برد وجود دارد؟"))
        add(n("np_e_bat", "problem_no_power", "باتری خراب یا خالی", "باتری را شارژ یا تعویض کنید", end = true))
        add(n("np_e_pwr_btn", "problem_no_power", "خرابی کلید Power", "کلید و مسیر آن را بررسی کنید", end = true))
        add(n("np_e_pmic", "problem_no_power", "خرابی PMIC", "تست حرارتی و تعویض PMIC", end = true))
        add(n("np_e_short", "problem_no_power", "کوتاهی در خط VBAT", "شناسایی و رفع کوتاهی", end = true))
        add(n("np_e_cpu", "problem_no_power", "خرابی CPU/RAM", "Reballing یا تعویض", end = true))
        add(n("np_e_boot", "problem_no_power", "مشکل نرم‌افزاری بوت", "تلاش برای فلش", end = true))

        // restart_random
        add(n("rr_q1", "problem_restart_random", "آیا باتری متورم یا داغ است؟", null, start = true))
        add(n("rr_q2", "problem_restart_random", "آیا فقط در اپ‌های سنگین ری‌استارت می‌شود؟"))
        add(n("rr_q3", "problem_restart_random", "آیا در حین شارژ هم ری‌استارت دارد؟"))
        add(n("rr_e_bat", "problem_restart_random", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("rr_e_thermal", "problem_restart_random", "داغ شدن CPU", "خمیر سیلیکون تعویض شود", end = true))
        add(n("rr_e_pmic", "problem_restart_random", "مشکل PMIC", "بررسی خطوط تغذیه", end = true))
        add(n("rr_e_sw", "problem_restart_random", "مشکل نرم‌افزاری", "بازنشانی یا فلش", end = true))

        // bootloop
        add(n("bl_q1", "problem_bootloop", "آیا وارد Recovery می‌شود؟", null, start = true))
        add(n("bl_q2", "problem_bootloop", "آیا بعد از آپدیت یا نصب اپ شروع شد؟"))
        add(n("bl_e_recovery", "problem_bootloop", "امکان بازیابی از Recovery", "Wipe cache یا Factory reset", end = true))
        add(n("bl_e_flash", "problem_bootloop", "نیاز به فلش رام", "رام رسمی نصب شود", end = true))
        add(n("bl_e_storage", "problem_bootloop", "خرابی eMMC/UFS", "تعویض IC حافظه", end = true))
        add(n("bl_e_cpu", "problem_bootloop", "خرابی CPU", "Reballing", end = true))

        // shutdown_low_battery
        add(n("slb_q1", "problem_shutdown_low_battery", "درصد خاموشی چقدر است؟", null, start = true))
        add(n("slb_q2", "problem_shutdown_low_battery", "آیا باتری متورم است؟"))
        add(n("slb_e_cal", "problem_shutdown_low_battery", "کالیبراسیون نادرست", "کالیبره کنید", end = true))
        add(n("slb_e_bat", "problem_shutdown_low_battery", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("slb_e_pmic", "problem_shutdown_low_battery", "مشکل PMIC", "بررسی خطوط", end = true))

        // no_display
        add(n("nd_q1", "problem_no_display", "آیا دستگاه صدا یا ویبره دارد؟", null, start = true))
        add(n("nd_q2", "problem_no_display", "آیا با نور چراغ‌قوه تصویر محو دیده می‌شود؟"))
        add(n("nd_q3", "problem_no_display", "آیا کابل LCD محکم است؟"))
        add(n("nd_e_backlight", "problem_no_display", "خرابی Backlight", "تعویض Backlight IC", end = true))
        add(n("nd_e_lcd", "problem_no_display", "خرابی LCD", "تعویض پنل", end = true))
        add(n("nd_e_mipi", "problem_no_display", "قطع خطوط MIPI", "بررسی مسیر", end = true))
        add(n("nd_e_cpu", "problem_no_display", "خرابی CPU", "Reballing", end = true))

        // power_button_stuck
        add(n("pbs_q1", "problem_power_button_stuck", "آیا کلید فیزیکی گیر کرده است؟", null, start = true))
        add(n("pbs_q2", "problem_power_button_stuck", "آیا با فشار سخت واکنش می‌دهد؟"))
        add(n("pbs_e_clean", "problem_power_button_stuck", "نیاز به تمیزکاری", "با الکل تمیز کنید", end = true))
        add(n("pbs_e_replace", "problem_power_button_stuck", "خرابی کلید", "تعویض کلید", end = true))

        // dead_after_water
        add(n("dw_q1", "problem_dead_after_water", "چند وقت پیش آب‌خوردگی رخ داده؟", null, start = true))
        add(n("dw_q2", "problem_dead_after_water", "آیا دستگاه در حال حاضر داغ می‌شود؟"))
        add(n("dw_e_ultrasonic", "problem_dead_after_water", "نیاز به اولتراسونیک", "شستشوی برد", end = true))
        add(n("dw_e_corrosion", "problem_dead_after_water", "خوردگی شدید", "تعویض قطعات معیوب", end = true))
        add(n("dw_e_hopeless", "problem_dead_after_water", "غیرقابل تعمیر", "برد سوخته", end = true))

        // hang_restart
        add(n("hr_q1", "problem_hang_restart", "آیا دستگاه داغ می‌شود؟", null, start = true))
        add(n("hr_q2", "problem_hang_restart", "آیا در حالت Safe Mode هم هنگ می‌کند؟"))
        add(n("hr_e_apps", "problem_hang_restart", "اپ مخرب", "پاکسازی اپ‌ها", end = true))
        add(n("hr_e_thermal", "problem_hang_restart", "داغ شدن CPU", "خمیر سیلیکون", end = true))
        add(n("hr_e_ram", "problem_hang_restart", "خرابی RAM", "Reballing", end = true))

        // auto_shutdown
        add(n("as_q1", "problem_auto_shutdown", "آیا خاموشی در تماس یا بازی است؟", null, start = true))
        add(n("as_q2", "problem_auto_shutdown", "آیا بعد از خاموشی سریع روشن می‌شود؟"))
        add(n("as_e_bat", "problem_auto_shutdown", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("as_e_thermal", "problem_auto_shutdown", "داغ شدن", "بررسی خنک‌کننده", end = true))
        add(n("as_e_pmic", "problem_auto_shutdown", "مشکل PMIC", "تعویض PMIC", end = true))

        // no_vibration
        add(n("nv_q1", "problem_no_vibration", "آیا در تست هپتیک هم ویبره ندارد؟", null, start = true))
        add(n("nv_q2", "problem_no_vibration", "آیا موتور ویبره ولتاژ می‌گیرد؟"))
        add(n("nv_e_motor", "problem_no_vibration", "خرابی موتور", "تعویض موتور ویبره", end = true))
        add(n("nv_e_sw", "problem_no_vibration", "مشکل نرم‌افزاری", "بازنشانی تنظیمات", end = true))

        // ═══════════ CHARGING ═══════════
        // no_charge
        add(n("nc_q1", "problem_no_charge", "آیا شارژر و کابل سالم هستند؟", null, start = true))
        add(n("nc_q2", "problem_no_charge", "آیا پورت شارژ تمیز و سالم است؟"))
        add(n("nc_q3", "problem_no_charge", "آیا ولتاژ VBUS روی برد وجود دارد؟"))
        add(n("nc_e_cable", "problem_no_charge", "خرابی کابل/شارژر", "تعویض کابل و شارژر", end = true))
        add(n("nc_e_port", "problem_no_charge", "خرابی پورت شارژ", "تعویض کانکتور", end = true))
        add(n("nc_e_ic", "problem_no_charge", "خرابی IC شارژ", "تعویض IC", end = true))
        add(n("nc_e_short", "problem_no_charge", "کوتاهی در خط VBUS", "رفع کوتاهی", end = true))

        // slow_charge
        add(n("sc_q1", "problem_slow_charge", "آیا شارژر اصلی است؟", null, start = true))
        add(n("sc_q2", "problem_slow_charge", "آیا کابل ضخیم و سالم است؟"))
        add(n("sc_e_charger", "problem_slow_charge", "شارژر ضعیف", "شارژر اصلی استفاده کنید", end = true))
        add(n("sc_e_cable", "problem_slow_charge", "کابل بی‌کیفیت", "کابل اصلی", end = true))
        add(n("sc_e_ic", "problem_slow_charge", "خرابی IC شارژ", "تعویض IC", end = true))

        // battery_drain
        add(n("bd_q1", "problem_battery_drain", "آیا در حالت پرواز هم سریع خالی می‌شود؟", null, start = true))
        add(n("bd_q2", "problem_battery_drain", "آیا دستگاه داغ است؟"))
        add(n("bd_e_app", "problem_battery_drain", "اپ‌های مصرفی", "محدود کردن اپ‌ها", end = true))
        add(n("bd_e_bat", "problem_battery_drain", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("bd_e_pmic", "problem_battery_drain", "نشتی PMIC", "تست حرارتی", end = true))

        // battery_swollen
        add(n("bs_q1", "problem_battery_swollen", "آیا درب پشت برآمده است؟", null, start = true))
        add(n("bs_e_replace", "problem_battery_swollen", "تعویض فوری باتری", "باتری را از دستگاه جدا کنید", end = true))

        // overheat_charging
        add(n("oc_q1", "problem_overheat_charging", "آیا با شارژر دیگر هم داغ می‌شود؟", null, start = true))
        add(n("oc_q2", "problem_overheat_charging", "آیا شارژ سریع فعال است؟"))
        add(n("oc_e_charger", "problem_overheat_charging", "شارژر معیوب", "شارژر را عوض کنید", end = true))
        add(n("oc_e_ic", "problem_overheat_charging", "خرابی IC شارژ", "تعویض IC", end = true))
        add(n("oc_e_bat", "problem_overheat_charging", "خرابی باتری", "تعویض باتری", end = true))

        // charging_port_damage
        add(n("cpd_q1", "problem_charging_port_damage", "آیا کابل شل است؟", null, start = true))
        add(n("cpd_q2", "problem_charging_port_damage", "آیا پین‌ها شکسته یا خم شده‌اند؟"))
        add(n("cpd_e_clean", "problem_charging_port_damage", "تمیزکاری", "با فشار هوا تمیز کنید", end = true))
        add(n("cpd_e_replace", "problem_charging_port_damage", "تعویض پورت", "کانکتور جدید نصب شود", end = true))

        // wireless_charge
        add(n("wc_q1", "problem_wireless_charge_issue", "آیا قاب ضخیم است؟", null, start = true))
        add(n("wc_q2", "problem_wireless_charge_issue", "آیا روی پد دیگر تست شد؟"))
        add(n("wc_e_case", "problem_wireless_charge_issue", "قاب ضخیم", "قاب را بردارید", end = true))
        add(n("wc_e_coil", "problem_wireless_charge_issue", "خرابی کویل", "تعویض کویل", end = true))
        add(n("wc_e_ic", "problem_wireless_charge_issue", "خرابی IC", "تعویض IC", end = true))

        // battery_fake_percent
        add(n("bfp_q1", "problem_battery_fake_percent", "آیا پرش درصد دارید؟", null, start = true))
        add(n("bfp_e_cal", "problem_battery_fake_percent", "کالیبراسیون", "کالیبره کنید", end = true))
        add(n("bfp_e_bat", "problem_battery_fake_percent", "خرابی باتری", "تعویض باتری", end = true))

        // no_charge_wireless
        add(n("ncw_q1", "problem_no_charge_wireless", "آیا با کابل شارژ می‌شود؟", null, start = true))
        add(n("ncw_e_pad", "problem_no_charge_wireless", "پد نامناسب", "پد اصلی استفاده کنید", end = true))
        add(n("ncw_e_coil", "problem_no_charge_wireless", "کویل بی‌کیفیت", "تعویض کویل", end = true))

        // charging_not_detected
        add(n("cnd_q1", "problem_charging_not_detected", "آیا با کابل دیگر تست شد؟", null, start = true))
        add(n("cnd_q2", "problem_charging_not_detected", "آیا خطوط D+/D- قطع هستند؟"))
        add(n("cnd_e_cable", "problem_charging_not_detected", "کابل معیوب", "تعویض کابل", end = true))
        add(n("cnd_e_port", "problem_charging_not_detected", "خرابی پورت", "تعویض پورت", end = true))
        add(n("cnd_e_ic", "problem_charging_not_detected", "خرابی IC USB", "تعویض IC", end = true))

        // ═══════════ DISPLAY ═══════════
        // lcd_broken
        add(n("lb_q1", "problem_lcd_broken", "آیا شکستگی ظاهری وجود دارد؟", null, start = true))
        add(n("lb_q2", "problem_lcd_broken", "آیا خطوط عمودی/افقی دیده می‌شود؟"))
        add(n("lb_e_replace", "problem_lcd_broken", "تعویض LCD", "تعویض پنل کامل", end = true))

        // touch_issue
        add(n("ti_q1", "problem_touch_issue", "آیا تاچ کل صفحه بی‌پاسخ است؟", null, start = true))
        add(n("ti_q2", "problem_touch_issue", "آیا بعد از تعویض گلس شروع شد؟"))
        add(n("ti_e_connector", "problem_touch_issue", "کابل تاچ شل است", "محکم کردن کابل", end = true))
        add(n("ti_e_ic", "problem_touch_issue", "خرابی تاچ IC", "تعویض IC", end = true))
        add(n("ti_e_digitizer", "problem_touch_issue", "خرابی دیجیتایزر", "تعویض تاچ", end = true))

        // ghost_touch
        add(n("gt_q1", "problem_ghost_touch", "آیا با شارژر غیراصل هم رخ می‌دهد؟", null, start = true))
        add(n("gt_q2", "problem_ghost_touch", "آیا با برداشتن شارژر متوقف می‌شود؟"))
        add(n("gt_e_charger", "problem_ghost_touch", "شارژر معیوب", "شارژر اصلی استفاده کنید", end = true))
        add(n("gt_e_ic", "problem_ghost_touch", "نویز IC تاچ", "بررسی خطوط", end = true))
        add(n("gt_e_digitizer", "problem_ghost_touch", "خرابی دیجیتایزر", "تعویض تاچ", end = true))

        // touch_partial
        add(n("tp_q1", "problem_touch_partial", "کدام قسمت بی‌پاسخ است؟", null, start = true))
        add(n("tp_e_flex", "problem_touch_partial", "قطع خطوط", "بررسی فلت", end = true))
        add(n("tp_e_digitizer", "problem_touch_partial", "دیجیتایزر خراب", "تعویض تاچ", end = true))

        // display_lines
        add(n("dl_q1", "problem_display_lines", "آیا خطوط ثابت هستند؟", null, start = true))
        add(n("dl_q2", "problem_display_lines", "آیا با فشار روی LCD تغییر می‌کنند؟"))
        add(n("dl_e_flex", "problem_display_lines", "قطع فلت", "تعویض فلت یا پنل", end = true))
        add(n("dl_e_lcd", "problem_display_lines", "خرابی LCD", "تعویض LCD", end = true))
        add(n("dl_e_ic", "problem_display_lines", "خرابی IC تصویر", "تعویض IC", end = true))

        // display_flicker
        add(n("df_q1", "problem_display_flicker", "آیا پرش فقط در روشنایی کم است؟", null, start = true))
        add(n("df_e_backlight", "problem_display_flicker", "Backlight ضعیف", "تعویض Backlight IC", end = true))
        add(n("df_e_lcd", "problem_display_flicker", "LCD معیوب", "تعویض LCD", end = true))

        // display_white
        add(n("dw_q1", "problem_display_white", "آیا صفحه کاملاً سفید است؟", null, start = true))
        add(n("dw_e_lcd", "problem_display_white", "خرابی LCD", "تعویض LCD", end = true))
        add(n("dw_e_ic", "problem_display_white", "خرابی IC", "تعویض IC", end = true))

        // dead_pixels
        add(n("dp_q1", "problem_dead_pixels", "چند پیکسل سوخته؟", null, start = true))
        add(n("dp_e_replace", "problem_dead_pixels", "تعویض LCD", "تعویض پنل", end = true))

        // low_brightness
        add(n("lbr_q1", "problem_low_brightness", "آیا در حداکثر روشنایی هم کم است؟", null, start = true))
        add(n("lbr_e_backlight", "problem_low_brightness", "Backlight ضعیف", "تعویض", end = true))
        add(n("lbr_e_lcd", "problem_low_brightness", "LCD فرسوده", "تعویض", end = true))

        // display_burn_in
        add(n("dbi_q1", "problem_display_burn_in", "آیا سایه ثابت روی صفحه دیده می‌شود؟", null, start = true))
        add(n("dbi_e_replace", "problem_display_burn_in", "تعویض OLED", "پنل جدید", end = true))

        // touch_delay
        add(n("td_q1", "problem_touch_delay", "آیا سیستم هم کند است؟", null, start = true))
        add(n("td_e_sw", "problem_touch_delay", "مشکل نرم‌افزاری", "بازنشانی", end = true))
        add(n("td_e_ic", "problem_touch_delay", "خرابی تاچ IC", "تعویض IC", end = true))

        // screen_protector
        add(n("sp_q1", "problem_screen_protector_lifted", "آیا لبه گلس بلند شده؟", null, start = true))
        add(n("sp_e_replace", "problem_screen_protector_lifted", "تعویض گلس", "گلس جدید", end = true))
    }

    fun options(): List<DiagnosisOptionEntity> = buildList {

        // ═══════════ POWER ═══════════
        // no_power
        add(o("np_o1", "np_q1", "کمتر از 3.5V", "np_e_bat"))
        add(o("np_o2", "np_q1", "بین 3.5 تا 4.4V", "np_q2"))
        add(o("np_o3", "np_q1", "نمی‌دانم", "np_q2"))
        add(o("np_o4", "np_q2", "بله (ویبره/صدا)", "np_q3"))
        add(o("np_o5", "np_q2", "خیر، هیچ واکنشی", "np_q4"))
        add(o("np_o6", "np_q3", "بله، جریان لحظه‌ای", "np_e_cpu"))
        add(o("np_o7", "np_q3", "خیر، جریان صفر", "np_e_boot"))
        add(o("np_o8", "np_q4", "بله، ولتاژ هست", "np_e_pmic"))
        add(o("np_o9", "np_q4", "خیر، ولتاژی نیست", "np_e_short"))
        add(o("np_o10", "np_q4", "نمی‌دانم", "np_e_pwr_btn"))

        // restart_random
        add(o("rr_o1", "rr_q1", "بله، متورم است", "rr_e_bat"))
        add(o("rr_o2", "rr_q1", "خیر، سالم است", "rr_q2"))
        add(o("rr_o3", "rr_q2", "بله، فقط در سنگین", "rr_e_thermal"))
        add(o("rr_o4", "rr_q2", "خیر، حتی در سبک", "rr_q3"))
        add(o("rr_o5", "rr_q3", "بله، در شارژ هم", "rr_e_pmic"))
        add(o("rr_o6", "rr_q3", "خیر", "rr_e_sw"))

        // bootloop
        add(o("bl_o1", "bl_q1", "بله، وارد Recovery می‌شود", "bl_e_recovery"))
        add(o("bl_o2", "bl_q1", "خیر، فقط لوگو", "bl_q2"))
        add(o("bl_o3", "bl_q2", "بله، بعد از آپدیت", "bl_e_flash"))
        add(o("bl_o4", "bl_q2", "خیر، ناگهانی", "bl_e_storage"))
        add(o("bl_o5", "bl_q2", "نمی‌دانم", "bl_e_cpu"))

        // shutdown_low_battery
        add(o("slb_o1", "slb_q1", "بین 30-50%", "slb_e_cal"))
        add(o("slb_o2", "slb_q1", "بین 50-80%", "slb_q2"))
        add(o("slb_o3", "slb_q2", "بله، متورم است", "slb_e_bat"))
        add(o("slb_o4", "slb_q2", "خیر، سالم", "slb_e_pmic"))

        // no_display
        add(o("nd_o1", "nd_q1", "بله، صدا دارد", "nd_q2"))
        add(o("nd_o2", "nd_q1", "خیر، کاملاً مرده", "nd_q3"))
        add(o("nd_o3", "nd_q2", "بله، تصویر محو هست", "nd_e_backlight"))
        add(o("nd_o4", "nd_q2", "خیر، کاملاً سیاه", "nd_e_lcd"))
        add(o("nd_o5", "nd_q3", "بله، محکم است", "nd_e_mipi"))
        add(o("nd_o6", "nd_q3", "خیر، شل است", "nd_e_cpu"))

        // power_button_stuck
        add(o("pbs_o1", "pbs_q1", "بله، گیر کرده", "pbs_e_clean"))
        add(o("pbs_o2", "pbs_q1", "خیر، آزاد است", "pbs_q2"))
        add(o("pbs_o3", "pbs_q2", "بله، سخت واکنش می‌دهد", "pbs_e_replace"))
        add(o("pbs_o4", "pbs_q2", "خیر، طبیعی است", "pbs_e_replace"))

        // dead_after_water
        add(o("dw_o1", "dw_q1", "کمتر از 24 ساعت", "dw_e_ultrasonic"))
        add(o("dw_o2", "dw_q1", "بیشتر از 24 ساعت", "dw_q2"))
        add(o("dw_o3", "dw_q2", "بله، داغ می‌شود", "dw_e_corrosion"))
        add(o("dw_o4", "dw_q2", "خیر، سرد است", "dw_e_hopeless"))

        // hang_restart
        add(o("hr_o1", "hr_q1", "بله، داغ می‌شود", "hr_e_thermal"))
        add(o("hr_o2", "hr_q1", "خیر، سرد است", "hr_q2"))
        add(o("hr_o3", "hr_q2", "بله، در Safe Mode هم", "hr_e_ram"))
        add(o("hr_o4", "hr_q2", "خیر، فقط در حالت عادی", "hr_e_apps"))

        // auto_shutdown
        add(o("as_o1", "as_q1", "بله، در تماس/بازی", "as_e_thermal"))
        add(o("as_o2", "as_q1", "خیر، تصادفی", "as_q2"))
        add(o("as_o3", "as_q2", "بله، سریع روشن می‌شود", "as_e_bat"))
        add(o("as_o4", "as_q2", "خیر، باید شارژ بزنم", "as_e_pmic"))

        // no_vibration
        add(o("nv_o1", "nv_q1", "بله، در تست هم نه", "nv_q2"))
        add(o("nv_o2", "nv_q1", "خیر، در تست کار می‌کند", "nv_e_sw"))
        add(o("nv_o3", "nv_q2", "بله، ولتاژ می‌گیرد", "nv_e_motor"))
        add(o("nv_o4", "nv_q2", "خیر، ولتاژ ندارد", "nv_e_sw"))

        // ═══════════ CHARGING ═══════════
        // no_charge
        add(o("nc_o1", "nc_q1", "بله، شارژر اصلی است", "nc_q2"))
        add(o("nc_o2", "nc_q1", "خیر، شارژر متفرقه", "nc_e_cable"))
        add(o("nc_o3", "nc_q2", "بله، تمیز و سالم", "nc_q3"))
        add(o("nc_o4", "nc_q2", "خیر، خراب یا کثیف", "nc_e_port"))
        add(o("nc_o5", "nc_q3", "بله، VBUS هست", "nc_e_ic"))
        add(o("nc_o6", "nc_q3", "خیر، VBUS نیست", "nc_e_short"))

        // slow_charge
        add(o("sc_o1", "sc_q1", "بله، شارژر اصلی", "sc_q2"))
        add(o("sc_o2", "sc_q1", "خیر، متفرقه", "sc_e_charger"))
        add(o("sc_o3", "sc_q2", "بله، سالم است", "sc_e_ic"))
        add(o("sc_o4", "sc_q2", "خیر، بی‌کیفیت", "sc_e_cable"))

        // battery_drain
        add(o("bd_o1", "bd_q1", "بله، در پرواز هم", "bd_q2"))
        add(o("bd_o2", "bd_q1", "خیر، در حالت عادی بدتر", "bd_e_app"))
        add(o("bd_o3", "bd_q2", "بله، داغ می‌شود", "bd_e_pmic"))
        add(o("bd_o4", "bd_q2", "خیر، سرد است", "bd_e_bat"))

        // battery_swollen
        add(o("bs_o1", "bs_q1", "بله، برآمده است", "bs_e_replace"))
        add(o("bs_o2", "bs_q1", "خیر، طبیعی است", "bs_e_replace"))

        // overheat_charging
        add(o("oc_o1", "oc_q1", "بله، با همه شارژرها", "oc_q2"))
        add(o("oc_o2", "oc_q1", "خیر، فقط با شارژر خاص", "oc_e_charger"))
        add(o("oc_o3", "oc_q2", "بله، شارژ سریع فعال", "oc_e_ic"))
        add(o("oc_o4", "oc_q2", "خیر، شارژ معمولی", "oc_e_bat"))

        // charging_port_damage
        add(o("cpd_o1", "cpd_q1", "بله، شل است", "cpd_q2"))
        add(o("cpd_o2", "cpd_q1", "خیر، محکم است", "cpd_e_clean"))
        add(o("cpd_o3", "cpd_q2", "بله، پین‌ها شکسته", "cpd_e_replace"))
        add(o("cpd_o4", "cpd_q2", "خیر، پین‌ها سالم", "cpd_e_clean"))

        // wireless_charge
        add(o("wc_o1", "wc_q1", "بله، قاب ضخیم", "wc_e_case"))
        add(o("wc_o2", "wc_q1", "خیر، قاب نازک", "wc_q2"))
        add(o("wc_o3", "wc_q2", "بله، پد دیگر تست شد", "wc_e_coil"))
        add(o("wc_o4", "wc_q2", "خیر، فقط یک پد", "wc_e_ic"))

        // battery_fake_percent
        add(o("bfp_o1", "bfp_q1", "بله، درصد پرش دارد", "bfp_e_cal"))
        add(o("bfp_o2", "bfp_q1", "خیر، ثابت است", "bfp_e_bat"))

        // no_charge_wireless
        add(o("ncw_o1", "ncw_q1", "بله، با کابل شارژ می‌شود", "ncw_e_coil"))
        add(o("ncw_o2", "ncw_q1", "خیر، با کابل هم کند", "ncw_e_pad"))

        // charging_not_detected
        add(o("cnd_o1", "cnd_q1", "بله، با کابل دیگر تست شد", "cnd_q2"))
        add(o("cnd_o2", "cnd_q1", "خیر، فقط یک کابل", "cnd_e_cable"))
        add(o("cnd_o3", "cnd_q2", "بله، خطوط قطع هستند", "cnd_e_ic"))
        add(o("cnd_o4", "cnd_q2", "خیر، خطوط سالم", "cnd_e_port"))

        // ═══════════ DISPLAY ═══════════
        // lcd_broken
        add(o("lb_o1", "lb_q1", "بله، شکستگی هست", "lb_e_replace"))
        add(o("lb_o2", "lb_q1", "خیر، شکستگی نیست", "lb_q2"))
        add(o("lb_o3", "lb_q2", "بله، خطوط دارد", "lb_e_replace"))
        add(o("lb_o4", "lb_q2", "خیر، تصویر سالم", "lb_e_replace"))

        // touch_issue
        add(o("ti_o1", "ti_q1", "بله، کل صفحه بی‌پاسخ", "ti_q2"))
        add(o("ti_o2", "ti_q1", "خیر، بخشی کار می‌کند", "ti_q2"))
        add(o("ti_o3", "ti_q2", "بله، بعد از تعویض گلس", "ti_e_connector"))
        add(o("ti_o4", "ti_q2", "خیر، ناگهانی", "ti_e_ic"))
        add(o("ti_o5", "ti_q2", "نمی‌دانم", "ti_e_digitizer"))

        // ghost_touch
        add(o("gt_o1", "gt_q1", "بله، با شارژر غیراصل", "gt_q2"))
        add(o("gt_o2", "gt_q1", "خیر، همیشه", "gt_e_ic"))
        add(o("gt_o3", "gt_q2", "بله، با برداشتن شارژر رفع می‌شود", "gt_e_charger"))
        add(o("gt_o4", "gt_q2", "خیر، رفع نمی‌شود", "gt_e_digitizer"))

        // touch_partial
        add(o("tp_o1", "tp_q1", "نوار بالا", "tp_e_flex"))
        add(o("tp_o2", "tp_q1", "نوار پایین", "tp_e_flex"))
        add(o("tp_o3", "tp_q1", "بخش میانی", "tp_e_digitizer"))

        // display_lines
        add(o("dl_o1", "dl_q1", "بله، خطوط ثابت هستند", "dl_q2"))
        add(o("dl_o2", "dl_q1", "خیر، متغیر هستند", "dl_e_flex"))
        add(o("dl_o3", "dl_q2", "بله، با فشار تغییر می‌کنند", "dl_e_flex"))
        add(o("dl_o4", "dl_q2", "خیر، ثابت می‌مانند", "dl_e_lcd"))
        add(o("dl_o5", "dl_q2", "نمی‌دانم", "dl_e_ic"))

        // display_flicker
        add(o("df_o1", "df_q1", "بله، فقط در روشنایی کم", "df_e_backlight"))
        add(o("df_o2", "df_q1", "خیر، همیشه پرش دارد", "df_e_lcd"))

        // display_white
        add(o("dw_o1", "dw_q1", "بله، سفید است", "dw_e_lcd"))
        add(o("dw_o2", "dw_q1", "خیر، رنگ دیگر", "dw_e_ic"))

        // dead_pixels
        add(o("dp_o1", "dp_q1", "یک یا دو پیکسل", "dp_e_replace"))
        add(o("dp_o2", "dp_q1", "بیشتر از 5 پیکسل", "dp_e_replace"))

        // low_brightness
        add(o("lbr_o1", "lbr_q1", "بله، حداکثر هم کم است", "lbr_e_lcd"))
        add(o("lbr_o2", "lbr_q1", "خیر، در حداکثر کافی است", "lbr_e_backlight"))

        // display_burn_in
        add(o("dbi_o1", "dbi_q1", "بله، سایه دیده می‌شود", "dbi_e_replace"))
        add(o("dbi_o2", "dbi_q1", "خیر، واضح است", "dbi_e_replace"))

        // touch_delay
        add(o("td_o1", "td_q1", "بله، سیستم کند است", "td_e_sw"))
        add(o("td_o2", "td_q1", "خیر، فقط تاچ کند", "td_e_ic"))

        // screen_protector
        add(o("sp_o1", "sp_q1", "بله، بلند شده", "sp_e_replace"))
        add(o("sp_o2", "sp_q1", "خیر، صاف است", "sp_e_replace"))
    }
}