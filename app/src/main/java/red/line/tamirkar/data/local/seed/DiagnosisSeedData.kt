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

    private fun tree(id: String, problemId: String, title: String) =
        DiagnosisTreeEntity(id, problemId, null, title, "راهنمای مرحله‌ای", 1)

    // ═══════════════════════════════════════════════════════════
    //  TREES
    // ═══════════════════════════════════════════════════════════

    fun trees(): List<DiagnosisTreeEntity> = listOf(
        // POWER
        tree("tree_no_power", "problem_no_power", "عیب‌یابی: روشن نشدن"),
        tree("tree_restart_random", "problem_restart_random", "عیب‌یابی: ری‌استارت تصادفی"),
        tree("tree_bootloop", "problem_bootloop", "عیب‌یابی: بوت‌لوپ"),
        tree("tree_shutdown_low_battery", "problem_shutdown_low_battery", "عیب‌یابی: خاموشی در درصد بالا"),
        tree("tree_no_display", "problem_no_display", "عیب‌یابی: بدون تصویر"),
        tree("tree_power_button_stuck", "problem_power_button_stuck", "عیب‌یابی: کلید Power"),
        tree("tree_dead_after_water", "problem_dead_after_water", "عیب‌یابی: آب‌خوردگی"),
        tree("tree_hang_restart", "problem_hang_restart", "عیب‌یابی: هنگ"),
        tree("tree_auto_shutdown", "problem_auto_shutdown", "عیب‌یابی: خاموشی خودکار"),
        tree("tree_no_vibration", "problem_no_vibration", "عیب‌یابی: ویبره"),

        // CHARGING
        tree("tree_no_charge", "problem_no_charge", "عیب‌یابی: شارژ نشدن"),
        tree("tree_slow_charge", "problem_slow_charge", "عیب‌یابی: شارژ کند"),
        tree("tree_battery_drain", "problem_battery_drain", "عیب‌یابی: خالی شدن سریع"),
        tree("tree_battery_swollen", "problem_battery_swollen", "عیب‌یابی: باد کردن باتری"),
        tree("tree_overheat_charging", "problem_overheat_charging", "عیب‌یابی: داغ شدن در شارژ"),
        tree("tree_charging_port_damage", "problem_charging_port_damage", "عیب‌یابی: درگاه شارژ"),
        tree("tree_wireless_charge", "problem_wireless_charge_issue", "عیب‌یابی: شارژ بی‌سیم"),
        tree("tree_battery_fake_percent", "problem_battery_fake_percent", "عیب‌یابی: درصد نادرست"),
        tree("tree_no_charge_wireless", "problem_no_charge_wireless", "عیب‌یابی: شارژ بی‌سیم کند"),
        tree("tree_charging_not_detected", "problem_charging_not_detected", "عیب‌یابی: عدم تشخیص شارژر"),

        // DISPLAY
        tree("tree_lcd_broken", "problem_lcd_broken", "عیب‌یابی: شکستگی LCD"),
        tree("tree_touch_issue", "problem_touch_issue", "عیب‌یابی: تاچ"),
        tree("tree_ghost_touch", "problem_ghost_touch", "عیب‌یابی: تاچ خودکار"),
        tree("tree_touch_partial", "problem_touch_partial", "عیب‌یابی: تاچ جزئی"),
        tree("tree_display_lines", "problem_display_lines", "عیب‌یابی: خطوط روی صفحه"),
        tree("tree_display_flicker", "problem_display_flicker", "عیب‌یابی: پرش تصویر"),
        tree("tree_display_white", "problem_display_white", "عیب‌یابی: صفحه سفید"),
        tree("tree_dead_pixels", "problem_dead_pixels", "عیب‌یابی: پیکسل سوخته"),
        tree("tree_low_brightness", "problem_low_brightness", "عیب‌یابی: روشنایی کم"),
        tree("tree_display_burn_in", "problem_display_burn_in", "عیب‌یابی: Burn-in"),
        tree("tree_touch_delay", "problem_touch_delay", "عیب‌یابی: تاخیر تاچ"),
        tree("tree_screen_protector", "problem_screen_protector_lifted", "عیب‌یابی: محافظ صفحه"),

        // NETWORK
        tree("tree_no_signal", "problem_no_signal", "عیب‌یابی: آنتن"),
        tree("tree_no_imei", "problem_no_imei", "عیب‌یابی: IMEI"),
        tree("tree_wifi_issue", "problem_wifi_issue", "عیب‌یابی: وای‌فای"),
        tree("tree_bluetooth_issue", "problem_bluetooth_issue", "عیب‌یابی: بلوتوث"),
        tree("tree_no_sim_detected", "problem_no_sim_detected", "عیب‌یابی: عدم شناسایی SIM"),
        tree("tree_network_slow", "problem_network_slow", "عیب‌یابی: اینترنت کند"),
        tree("tree_gps_issue", "problem_gps_issue", "عیب‌یابی: GPS"),
        tree("tree_nfc_issue", "problem_nfc_issue", "عیب‌یابی: NFC"),
        tree("tree_dual_sim_issue", "problem_dual_sim_issue", "عیب‌یابی: سیم‌کارت دوم"),
        tree("tree_hotspot_issue", "problem_hotspot_issue", "عیب‌یابی: هات‌اسپات"),

        // AUDIO
        tree("tree_no_sound", "problem_no_sound", "عیب‌یابی: بی‌صدا"),
        tree("tree_mic_issue", "problem_mic_issue", "عیب‌یابی: میکروفون"),
        tree("tree_speaker_noise", "problem_speaker_noise", "عیب‌یابی: صدای خش‌دار"),
        tree("tree_earpiece_no_sound", "problem_earpiece_no_sound", "عیب‌یابی: اسپیکر مکالمه"),
        tree("tree_headphone_jack", "problem_headphone_jack", "عیب‌یابی: جک هدفون"),
        tree("tree_ringtone_low", "problem_ringtone_low", "عیب‌یابی: صدای زنگ ضعیف"),
        tree("tree_no_audio_call", "problem_no_audio_call", "عیب‌یابی: بی‌صدایی در تماس"),
        tree("tree_loudspeaker_crackling", "problem_loudspeaker_crackling", "عیب‌یابی: ترق‌ترق بلندگو")
    )

    // ═══════════════════════════════════════════════════════════
    //  NODES
    // ═══════════════════════════════════════════════════════════

    fun nodes(): List<DiagnosisNodeEntity> = buildList {

        // ═══════════ POWER ═══════════
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

        add(n("rr_q1", "problem_restart_random", "آیا باتری متورم یا داغ است؟", null, start = true))
        add(n("rr_q2", "problem_restart_random", "آیا فقط در اپ‌های سنگین ری‌استارت می‌شود؟"))
        add(n("rr_q3", "problem_restart_random", "آیا در حین شارژ هم ری‌استارت دارد؟"))
        add(n("rr_e_bat", "problem_restart_random", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("rr_e_thermal", "problem_restart_random", "داغ شدن CPU", "خمیر سیلیکون تعویض شود", end = true))
        add(n("rr_e_pmic", "problem_restart_random", "مشکل PMIC", "بررسی خطوط تغذیه", end = true))
        add(n("rr_e_sw", "problem_restart_random", "مشکل نرم‌افزاری", "بازنشانی یا فلش", end = true))

        add(n("bl_q1", "problem_bootloop", "آیا وارد Recovery می‌شود؟", null, start = true))
        add(n("bl_q2", "problem_bootloop", "آیا بعد از آپدیت یا نصب اپ شروع شد؟"))
        add(n("bl_e_recovery", "problem_bootloop", "امکان بازیابی از Recovery", "Wipe cache یا Factory reset", end = true))
        add(n("bl_e_flash", "problem_bootloop", "نیاز به فلش رام", "رام رسمی نصب شود", end = true))
        add(n("bl_e_storage", "problem_bootloop", "خرابی eMMC/UFS", "تعویض IC حافظه", end = true))
        add(n("bl_e_cpu", "problem_bootloop", "خرابی CPU", "Reballing", end = true))

        add(n("slb_q1", "problem_shutdown_low_battery", "درصد خاموشی چقدر است؟", null, start = true))
        add(n("slb_q2", "problem_shutdown_low_battery", "آیا باتری متورم است؟"))
        add(n("slb_e_cal", "problem_shutdown_low_battery", "کالیبراسیون نادرست", "کالیبره کنید", end = true))
        add(n("slb_e_bat", "problem_shutdown_low_battery", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("slb_e_pmic", "problem_shutdown_low_battery", "مشکل PMIC", "بررسی خطوط", end = true))

        add(n("nd_q1", "problem_no_display", "آیا دستگاه صدا یا ویبره دارد؟", null, start = true))
        add(n("nd_q2", "problem_no_display", "آیا با نور چراغ‌قوه تصویر محو دیده می‌شود؟"))
        add(n("nd_q3", "problem_no_display", "آیا کابل LCD محکم است؟"))
        add(n("nd_e_backlight", "problem_no_display", "خرابی Backlight", "تعویض Backlight IC", end = true))
        add(n("nd_e_lcd", "problem_no_display", "خرابی LCD", "تعویض پنل", end = true))
        add(n("nd_e_mipi", "problem_no_display", "قطع خطوط MIPI", "بررسی مسیر", end = true))
        add(n("nd_e_cpu", "problem_no_display", "خرابی CPU", "Reballing", end = true))

        add(n("pbs_q1", "problem_power_button_stuck", "آیا کلید فیزیکی گیر کرده است؟", null, start = true))
        add(n("pbs_q2", "problem_power_button_stuck", "آیا با فشار سخت واکنش می‌دهد؟"))
        add(n("pbs_e_clean", "problem_power_button_stuck", "نیاز به تمیزکاری", "با الکل تمیز کنید", end = true))
        add(n("pbs_e_replace", "problem_power_button_stuck", "خرابی کلید", "تعویض کلید", end = true))

        add(n("dw_q1", "problem_dead_after_water", "چند وقت پیش آب‌خوردگی رخ داده؟", null, start = true))
        add(n("dw_q2", "problem_dead_after_water", "آیا دستگاه در حال حاضر داغ می‌شود؟"))
        add(n("dw_e_ultrasonic", "problem_dead_after_water", "نیاز به اولتراسونیک", "شستشوی برد", end = true))
        add(n("dw_e_corrosion", "problem_dead_after_water", "خوردگی شدید", "تعویض قطعات معیوب", end = true))
        add(n("dw_e_hopeless", "problem_dead_after_water", "غیرقابل تعمیر", "برد سوخته", end = true))

        add(n("hr_q1", "problem_hang_restart", "آیا دستگاه داغ می‌شود؟", null, start = true))
        add(n("hr_q2", "problem_hang_restart", "آیا در حالت Safe Mode هم هنگ می‌کند؟"))
        add(n("hr_e_apps", "problem_hang_restart", "اپ مخرب", "پاکسازی اپ‌ها", end = true))
        add(n("hr_e_thermal", "problem_hang_restart", "داغ شدن CPU", "خمیر سیلیکون", end = true))
        add(n("hr_e_ram", "problem_hang_restart", "خرابی RAM", "Reballing", end = true))

        add(n("as_q1", "problem_auto_shutdown", "آیا خاموشی در تماس یا بازی است؟", null, start = true))
        add(n("as_q2", "problem_auto_shutdown", "آیا بعد از خاموشی سریع روشن می‌شود؟"))
        add(n("as_e_bat", "problem_auto_shutdown", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("as_e_thermal", "problem_auto_shutdown", "داغ شدن", "بررسی خنک‌کننده", end = true))
        add(n("as_e_pmic", "problem_auto_shutdown", "مشکل PMIC", "تعویض PMIC", end = true))

        add(n("nv_q1", "problem_no_vibration", "آیا در تست هپتیک هم ویبره ندارد؟", null, start = true))
        add(n("nv_q2", "problem_no_vibration", "آیا موتور ویبره ولتاژ می‌گیرد؟"))
        add(n("nv_e_motor", "problem_no_vibration", "خرابی موتور", "تعویض موتور ویبره", end = true))
        add(n("nv_e_sw", "problem_no_vibration", "مشکل نرم‌افزاری", "بازنشانی تنظیمات", end = true))

        // ═══════════ CHARGING ═══════════
        add(n("nc_q1", "problem_no_charge", "آیا شارژر و کابل سالم هستند؟", null, start = true))
        add(n("nc_q2", "problem_no_charge", "آیا پورت شارژ تمیز و سالم است؟"))
        add(n("nc_q3", "problem_no_charge", "آیا ولتاژ VBUS روی برد وجود دارد؟"))
        add(n("nc_e_cable", "problem_no_charge", "خرابی کابل/شارژر", "تعویض کابل و شارژر", end = true))
        add(n("nc_e_port", "problem_no_charge", "خرابی پورت شارژ", "تعویض کانکتور", end = true))
        add(n("nc_e_ic", "problem_no_charge", "خرابی IC شارژ", "تعویض IC", end = true))
        add(n("nc_e_short", "problem_no_charge", "کوتاهی در خط VBUS", "رفع کوتاهی", end = true))

        add(n("sc_q1", "problem_slow_charge", "آیا شارژر اصلی است؟", null, start = true))
        add(n("sc_q2", "problem_slow_charge", "آیا کابل ضخیم و سالم است؟"))
        add(n("sc_e_charger", "problem_slow_charge", "شارژر ضعیف", "شارژر اصلی استفاده کنید", end = true))
        add(n("sc_e_cable", "problem_slow_charge", "کابل بی‌کیفیت", "کابل اصلی", end = true))
        add(n("sc_e_ic", "problem_slow_charge", "خرابی IC شارژ", "تعویض IC", end = true))

        add(n("bd_q1", "problem_battery_drain", "آیا در حالت پرواز هم سریع خالی می‌شود؟", null, start = true))
        add(n("bd_q2", "problem_battery_drain", "آیا دستگاه داغ است؟"))
        add(n("bd_e_app", "problem_battery_drain", "اپ‌های مصرفی", "محدود کردن اپ‌ها", end = true))
        add(n("bd_e_bat", "problem_battery_drain", "باتری فرسوده", "تعویض باتری", end = true))
        add(n("bd_e_pmic", "problem_battery_drain", "نشتی PMIC", "تست حرارتی", end = true))

        add(n("bs_q1", "problem_battery_swollen", "آیا درب پشت برآمده است؟", null, start = true))
        add(n("bs_e_replace", "problem_battery_swollen", "تعویض فوری باتری", "باتری را از دستگاه جدا کنید", end = true))

        add(n("oc_q1", "problem_overheat_charging", "آیا با شارژر دیگر هم داغ می‌شود؟", null, start = true))
        add(n("oc_q2", "problem_overheat_charging", "آیا شارژ سریع فعال است؟"))
        add(n("oc_e_charger", "problem_overheat_charging", "شارژر معیوب", "شارژر را عوض کنید", end = true))
        add(n("oc_e_ic", "problem_overheat_charging", "خرابی IC شارژ", "تعویض IC", end = true))
        add(n("oc_e_bat", "problem_overheat_charging", "خرابی باتری", "تعویض باتری", end = true))

        add(n("cpd_q1", "problem_charging_port_damage", "آیا کابل شل است؟", null, start = true))
        add(n("cpd_q2", "problem_charging_port_damage", "آیا پین‌ها شکسته یا خم شده‌اند؟"))
        add(n("cpd_e_clean", "problem_charging_port_damage", "تمیزکاری", "با فشار هوا تمیز کنید", end = true))
        add(n("cpd_e_replace", "problem_charging_port_damage", "تعویض پورت", "کانکتور جدید نصب شود", end = true))

        add(n("wc_q1", "problem_wireless_charge_issue", "آیا قاب ضخیم است؟", null, start = true))
        add(n("wc_q2", "problem_wireless_charge_issue", "آیا روی پد دیگر تست شد؟"))
        add(n("wc_e_case", "problem_wireless_charge_issue", "قاب ضخیم", "قاب را بردارید", end = true))
        add(n("wc_e_coil", "problem_wireless_charge_issue", "خرابی کویل", "تعویض کویل", end = true))
        add(n("wc_e_ic", "problem_wireless_charge_issue", "خرابی IC", "تعویض IC", end = true))

        add(n("bfp_q1", "problem_battery_fake_percent", "آیا پرش درصد دارید؟", null, start = true))
        add(n("bfp_e_cal", "problem_battery_fake_percent", "کالیبراسیون", "کالیبره کنید", end = true))
        add(n("bfp_e_bat", "problem_battery_fake_percent", "خرابی باتری", "تعویض باتری", end = true))

        add(n("ncw_q1", "problem_no_charge_wireless", "آیا با کابل شارژ می‌شود؟", null, start = true))
        add(n("ncw_e_pad", "problem_no_charge_wireless", "پد نامناسب", "پد اصلی استفاده کنید", end = true))
        add(n("ncw_e_coil", "problem_no_charge_wireless", "کویل بی‌کیفیت", "تعویض کویل", end = true))

        add(n("cnd_q1", "problem_charging_not_detected", "آیا با کابل دیگر تست شد؟", null, start = true))
        add(n("cnd_q2", "problem_charging_not_detected", "آیا خطوط D+/D- قطع هستند؟"))
        add(n("cnd_e_cable", "problem_charging_not_detected", "کابل معیوب", "تعویض کابل", end = true))
        add(n("cnd_e_port", "problem_charging_not_detected", "خرابی پورت", "تعویض پورت", end = true))
        add(n("cnd_e_ic", "problem_charging_not_detected", "خرابی IC USB", "تعویض IC", end = true))

        // ═══════════ DISPLAY ═══════════
        add(n("lb_q1", "problem_lcd_broken", "آیا شکستگی ظاهری وجود دارد؟", null, start = true))
        add(n("lb_q2", "problem_lcd_broken", "آیا خطوط عمودی/افقی دیده می‌شود؟"))
        add(n("lb_e_replace", "problem_lcd_broken", "تعویض LCD", "تعویض پنل کامل", end = true))

        add(n("ti_q1", "problem_touch_issue", "آیا تاچ کل صفحه بی‌پاسخ است؟", null, start = true))
        add(n("ti_q2", "problem_touch_issue", "آیا بعد از تعویض گلس شروع شد؟"))
        add(n("ti_e_connector", "problem_touch_issue", "کابل تاچ شل است", "محکم کردن کابل", end = true))
        add(n("ti_e_ic", "problem_touch_issue", "خرابی تاچ IC", "تعویض IC", end = true))
        add(n("ti_e_digitizer", "problem_touch_issue", "خرابی دیجیتایزر", "تعویض تاچ", end = true))

        add(n("gt_q1", "problem_ghost_touch", "آیا با شارژر غیراصل هم رخ می‌دهد؟", null, start = true))
        add(n("gt_q2", "problem_ghost_touch", "آیا با برداشتن شارژر متوقف می‌شود؟"))
        add(n("gt_e_charger", "problem_ghost_touch", "شارژر معیوب", "شارژر اصلی استفاده کنید", end = true))
        add(n("gt_e_ic", "problem_ghost_touch", "نویز IC تاچ", "بررسی خطوط", end = true))
        add(n("gt_e_digitizer", "problem_ghost_touch", "خرابی دیجیتایزر", "تعویض تاچ", end = true))

        add(n("tp_q1", "problem_touch_partial", "کدام قسمت بی‌پاسخ است؟", null, start = true))
        add(n("tp_e_flex", "problem_touch_partial", "قطع خطوط", "بررسی فلت", end = true))
        add(n("tp_e_digitizer", "problem_touch_partial", "دیجیتایزر خراب", "تعویض تاچ", end = true))

        add(n("dl_q1", "problem_display_lines", "آیا خطوط ثابت هستند؟", null, start = true))
        add(n("dl_q2", "problem_display_lines", "آیا با فشار روی LCD تغییر می‌کنند؟"))
        add(n("dl_e_flex", "problem_display_lines", "قطع فلت", "تعویض فلت یا پنل", end = true))
        add(n("dl_e_lcd", "problem_display_lines", "خرابی LCD", "تعویض LCD", end = true))
        add(n("dl_e_ic", "problem_display_lines", "خرابی IC تصویر", "تعویض IC", end = true))

        add(n("df_q1", "problem_display_flicker", "آیا پرش فقط در روشنایی کم است؟", null, start = true))
        add(n("df_e_backlight", "problem_display_flicker", "Backlight ضعیف", "تعویض Backlight IC", end = true))
        add(n("df_e_lcd", "problem_display_flicker", "LCD معیوب", "تعویض LCD", end = true))

        add(n("dwht_q1", "problem_display_white", "آیا صفحه کاملاً سفید است؟", null, start = true))
        add(n("dwht_e_lcd", "problem_display_white", "خرابی LCD", "تعویض LCD", end = true))
        add(n("dwht_e_ic", "problem_display_white", "خرابی IC", "تعویض IC", end = true))

        add(n("dp_q1", "problem_dead_pixels", "چند پیکسل سوخته؟", null, start = true))
        add(n("dp_e_replace", "problem_dead_pixels", "تعویض LCD", "تعویض پنل", end = true))

        add(n("lbr_q1", "problem_low_brightness", "آیا در حداکثر روشنایی هم کم است؟", null, start = true))
        add(n("lbr_e_backlight", "problem_low_brightness", "Backlight ضعیف", "تعویض", end = true))
        add(n("lbr_e_lcd", "problem_low_brightness", "LCD فرسوده", "تعویض", end = true))

        add(n("dbi_q1", "problem_display_burn_in", "آیا سایه ثابت روی صفحه دیده می‌شود؟", null, start = true))
        add(n("dbi_e_replace", "problem_display_burn_in", "تعویض OLED", "پنل جدید", end = true))

        add(n("td_q1", "problem_touch_delay", "آیا سیستم هم کند است؟", null, start = true))
        add(n("td_e_sw", "problem_touch_delay", "مشکل نرم‌افزاری", "بازنشانی", end = true))
        add(n("td_e_ic", "problem_touch_delay", "خرابی تاچ IC", "تعویض IC", end = true))

        add(n("sp_q1", "problem_screen_protector_lifted", "آیا لبه گلس بلند شده؟", null, start = true))
        add(n("sp_e_replace", "problem_screen_protector_lifted", "تعویض گلس", "گلس جدید", end = true))

        // ═══════════ NETWORK ═══════════
        add(n("ns_q1", "problem_no_signal", "آیا SIM شناسایی می‌شود؟", null, start = true))
        add(n("ns_q2", "problem_no_signal", "آیا IMEI معتبر است؟", "با کد *#06# چک کنید"))
        add(n("ns_q3", "problem_no_signal", "آیا ولتاژ روی خطوط آنتن هست؟"))
        add(n("ns_e_sim", "problem_no_signal", "مشکل SIM یا اسلات", "تعویض اسلات یا SIM", end = true))
        add(n("ns_e_imei", "problem_no_signal", "مشکل NVRAM یا IMEI", "بازنویسی IMEI", end = true))
        add(n("ns_e_rf", "problem_no_signal", "خرابی IC RF", "تعویض RF IC", end = true))
        add(n("ns_e_baseband", "problem_no_signal", "خرابی Baseband", "Reballing یا تعویض", end = true))

        add(n("ni_q1", "problem_no_imei", "با کد *#06# چه چیزی نمایش داده می‌شود؟", null, start = true))
        add(n("ni_e_nvram", "problem_no_imei", "NVRAM خراب", "بازنویسی NVRAM", end = true))
        add(n("ni_e_bb", "problem_no_imei", "خرابی Baseband IC", "تعویض IC", end = true))

        add(n("wi_q1", "problem_wifi_issue", "آیا WiFi خاموش می‌شود؟", null, start = true))
        add(n("wi_q2", "problem_wifi_issue", "آیا شبکه‌ها را پیدا می‌کند؟"))
        add(n("wi_e_sw", "problem_wifi_issue", "مشکل نرم‌افزاری", "ریست شبکه", end = true))
        add(n("wi_e_ic", "problem_wifi_issue", "خرابی IC WiFi", "تعویض IC", end = true))
        add(n("wi_e_ant", "problem_wifi_issue", "قطع آنتن WiFi", "بررسی آنتن", end = true))

        add(n("bt_q1", "problem_bluetooth_issue", "آیا بلوتوث خاموش می‌شود؟", null, start = true))
        add(n("bt_e_sw", "problem_bluetooth_issue", "مشکل نرم‌افزاری", "ریست", end = true))
        add(n("bt_e_ic", "problem_bluetooth_issue", "خرابی IC ترکیبی WiFi/BT", "تعویض IC", end = true))

        add(n("nsd_q1", "problem_no_sim_detected", "آیا SIM در دستگاه دیگر کار می‌کند؟", null, start = true))
        add(n("nsd_e_sim", "problem_no_sim_detected", "SIM خراب", "تعویض SIM", end = true))
        add(n("nsd_e_slot", "problem_no_sim_detected", "اسلات خراب", "تعویض اسلات", end = true))
        add(n("nsd_e_ic", "problem_no_sim_detected", "خرابی IC", "تعویض IC", end = true))

        add(n("nsl_q1", "problem_network_slow", "آیا در همه جا کند است؟", null, start = true))
        add(n("nsl_e_operator", "problem_network_slow", "مشکل اپراتور", "با اپراتور دیگر تست کنید", end = true))
        add(n("nsl_e_apn", "problem_network_slow", "تنظیمات APN", "ریست APN", end = true))

        add(n("gps_q1", "problem_gps_issue", "آیا GPS آفلاین است؟", null, start = true))
        add(n("gps_e_sw", "problem_gps_issue", "مشکل نرم‌افزاری", "ریست موقعیت", end = true))
        add(n("gps_e_mod", "problem_gps_issue", "خرابی ماژول", "تعویض ماژول GPS", end = true))

        add(n("nfc_q1", "problem_nfc_issue", "آیا NFC خاموش می‌شود؟", null, start = true))
        add(n("nfc_e_sw", "problem_nfc_issue", "مشکل نرم‌افزاری", "ریست", end = true))
        add(n("nfc_e_ant", "problem_nfc_issue", "خرابی آنتن NFC", "تعویض آنتن", end = true))

        add(n("ds_q1", "problem_dual_sim_issue", "کدام اسلات کار نمی‌کند؟", null, start = true))
        add(n("ds_e_slot", "problem_dual_sim_issue", "خرابی اسلات دوم", "تعویض اسلات", end = true))

        add(n("hs_q1", "problem_hotspot_issue", "آیا هات‌اسپات روشن می‌شود؟", null, start = true))
        add(n("hs_e_sw", "problem_hotspot_issue", "مشکل نرم‌افزاری", "ریست شبکه", end = true))

        // ═══════════ AUDIO ═══════════
        add(n("nsnd_q1", "problem_no_sound", "آیا با هندزفری صدا دارد؟", null, start = true))
        add(n("nsnd_q2", "problem_no_sound", "آیا بلندگو صدای خش دارد؟"))
        add(n("nsnd_e_spk", "problem_no_sound", "خرابی بلندگو", "تعویض بلندگو", end = true))
        add(n("nsnd_e_ic", "problem_no_sound", "خرابی IC صدا", "تعویض IC", end = true))
        add(n("nsnd_e_conn", "problem_no_sound", "قطع کانکتور", "محکم کردن", end = true))

        add(n("mi_q1", "problem_mic_issue", "آیا در ضبط صدا هم کار نمی‌کند؟", null, start = true))
        add(n("mi_q2", "problem_mic_issue", "آیا با اپ دیگر هم مشکل هست؟"))
        add(n("mi_e_mic", "problem_mic_issue", "خرابی میکروفون", "تعویض میکروفون", end = true))
        add(n("mi_e_sw", "problem_mic_issue", "مشکل نرم‌افزاری", "ریست مجوزها", end = true))
        add(n("mi_e_ic", "problem_mic_issue", "خرابی IC صدا", "تعویض IC", end = true))

        add(n("spn_q1", "problem_speaker_noise", "آیا خش در همه صداها هست؟", null, start = true))
        add(n("spn_e_spk", "problem_speaker_noise", "خرابی بلندگو", "تعویض", end = true))
        add(n("spn_e_amp", "problem_speaker_noise", "خرابی آمپلی‌فایر", "تعویض IC", end = true))

        add(n("ep_q1", "problem_earpiece_no_sound", "آیا صدای مخاطب شنیده نمی‌شود؟", null, start = true))
        add(n("ep_e_spk", "problem_earpiece_no_sound", "خرابی اسپیکر مکالمه", "تعویض", end = true))
        add(n("ep_e_conn", "problem_earpiece_no_sound", "قطع کانکتور", "محکم کردن", end = true))

        add(n("hj_q1", "problem_headphone_jack", "آیا هدفون شناسایی می‌شود؟", null, start = true))
        add(n("hj_e_clean", "problem_headphone_jack", "گرد و غبار", "تمیز کردن", end = true))
        add(n("hj_e_jack", "problem_headphone_jack", "خرابی جک", "تعویض جک", end = true))

        add(n("rl_q1", "problem_ringtone_low", "آیا در حداکثر صدا هم ضعیف است؟", null, start = true))
        add(n("rl_e_clean", "problem_ringtone_low", "گرفتگی", "تمیز کردن", end = true))
        add(n("rl_e_spk", "problem_ringtone_low", "خرابی بلندگو", "تعویض", end = true))

        add(n("nac_q1", "problem_no_audio_call", "آیا هر دو طرف صدا ندارند؟", null, start = true))
        add(n("nac_e_sw", "problem_no_audio_call", "مشکل نرم‌افزاری", "ریست", end = true))
        add(n("nac_e_ic", "problem_no_audio_call", "خرابی IC صدا", "تعویض IC", end = true))

        add(n("lc_q1", "problem_loudspeaker_crackling", "آیا ترق‌ترق در همه صداها هست؟", null, start = true))
        add(n("lc_e_spk", "problem_loudspeaker_crackling", "خرابی بلندگو", "تعویض", end = true))
        add(n("lc_e_amp", "problem_loudspeaker_crackling", "خرابی آمپلی‌فایر", "تعویض IC", end = true))
    }

    // ═══════════════════════════════════════════════════════════
    //  OPTIONS
    // ═══════════════════════════════════════════════════════════

    fun options(): List<DiagnosisOptionEntity> = buildList {

        // ═══════════ POWER ═══════════
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

        add(o("rr_o1", "rr_q1", "بله، متورم است", "rr_e_bat"))
        add(o("rr_o2", "rr_q1", "خیر، سالم است", "rr_q2"))
        add(o("rr_o3", "rr_q2", "بله، فقط در سنگین", "rr_e_thermal"))
        add(o("rr_o4", "rr_q2", "خیر، حتی در سبک", "rr_q3"))
        add(o("rr_o5", "rr_q3", "بله، در شارژ هم", "rr_e_pmic"))
        add(o("rr_o6", "rr_q3", "خیر", "rr_e_sw"))

        add(o("bl_o1", "bl_q1", "بله، وارد Recovery می‌شود", "bl_e_recovery"))
        add(o("bl_o2", "bl_q1", "خیر، فقط لوگو", "bl_q2"))
        add(o("bl_o3", "bl_q2", "بله، بعد از آپدیت", "bl_e_flash"))
        add(o("bl_o4", "bl_q2", "خیر، ناگهانی", "bl_e_storage"))
        add(o("bl_o5", "bl_q2", "نمی‌دانم", "bl_e_cpu"))

        add(o("slb_o1", "slb_q1", "بین 30-50%", "slb_e_cal"))
        add(o("slb_o2", "slb_q1", "بین 50-80%", "slb_q2"))
        add(o("slb_o3", "slb_q2", "بله، متورم است", "slb_e_bat"))
        add(o("slb_o4", "slb_q2", "خیر، سالم", "slb_e_pmic"))

        add(o("nd_o1", "nd_q1", "بله، صدا دارد", "nd_q2"))
        add(o("nd_o2", "nd_q1", "خیر، کاملاً مرده", "nd_q3"))
        add(o("nd_o3", "nd_q2", "بله، تصویر محو هست", "nd_e_backlight"))
        add(o("nd_o4", "nd_q2", "خیر، کاملاً سیاه", "nd_e_lcd"))
        add(o("nd_o5", "nd_q3", "بله، محکم است", "nd_e_mipi"))
        add(o("nd_o6", "nd_q3", "خیر، شل است", "nd_e_cpu"))

        add(o("pbs_o1", "pbs_q1", "بله، گیر کرده", "pbs_e_clean"))
        add(o("pbs_o2", "pbs_q1", "خیر، آزاد است", "pbs_q2"))
        add(o("pbs_o3", "pbs_q2", "بله، سخت واکنش می‌دهد", "pbs_e_replace"))
        add(o("pbs_o4", "pbs_q2", "خیر، طبیعی است", "pbs_e_replace"))

        add(o("dw_o1", "dw_q1", "کمتر از 24 ساعت", "dw_e_ultrasonic"))
        add(o("dw_o2", "dw_q1", "بیشتر از 24 ساعت", "dw_q2"))
        add(o("dw_o3", "dw_q2", "بله، داغ می‌شود", "dw_e_corrosion"))
        add(o("dw_o4", "dw_q2", "خیر، سرد است", "dw_e_hopeless"))

        add(o("hr_o1", "hr_q1", "بله، داغ می‌شود", "hr_e_thermal"))
        add(o("hr_o2", "hr_q1", "خیر، سرد است", "hr_q2"))
        add(o("hr_o3", "hr_q2", "بله، در Safe Mode هم", "hr_e_ram"))
        add(o("hr_o4", "hr_q2", "خیر، فقط در حالت عادی", "hr_e_apps"))

        add(o("as_o1", "as_q1", "بله، در تماس/بازی", "as_e_thermal"))
        add(o("as_o2", "as_q1", "خیر، تصادفی", "as_q2"))
        add(o("as_o3", "as_q2", "بله، سریع روشن می‌شود", "as_e_bat"))
        add(o("as_o4", "as_q2", "خیر، باید شارژ بزنم", "as_e_pmic"))

        add(o("nv_o1", "nv_q1", "بله، در تست هم نه", "nv_q2"))
        add(o("nv_o2", "nv_q1", "خیر، در تست کار می‌کند", "nv_e_sw"))
        add(o("nv_o3", "nv_q2", "بله، ولتاژ می‌گیرد", "nv_e_motor"))
        add(o("nv_o4", "nv_q2", "خیر، ولتاژ ندارد", "nv_e_sw"))

        // ═══════════ CHARGING ═══════════
        add(o("nc_o1", "nc_q1", "بله، شارژر اصلی است", "nc_q2"))
        add(o("nc_o2", "nc_q1", "خیر، شارژر متفرقه", "nc_e_cable"))
        add(o("nc_o3", "nc_q2", "بله، تمیز و سالم", "nc_q3"))
        add(o("nc_o4", "nc_q2", "خیر، خراب یا کثیف", "nc_e_port"))
        add(o("nc_o5", "nc_q3", "بله، VBUS هست", "nc_e_ic"))
        add(o("nc_o6", "nc_q3", "خیر، VBUS نیست", "nc_e_short"))

        add(o("sc_o1", "sc_q1", "بله، شارژر اصلی", "sc_q2"))
        add(o("sc_o2", "sc_q1", "خیر، متفرقه", "sc_e_charger"))
        add(o("sc_o3", "sc_q2", "بله، سالم است", "sc_e_ic"))
        add(o("sc_o4", "sc_q2", "خیر، بی‌کیفیت", "sc_e_cable"))

        add(o("bd_o1", "bd_q1", "بله، در پرواز هم", "bd_q2"))
        add(o("bd_o2", "bd_q1", "خیر، در حالت عادی بدتر", "bd_e_app"))
        add(o("bd_o3", "bd_q2", "بله، داغ می‌شود", "bd_e_pmic"))
        add(o("bd_o4", "bd_q2", "خیر، سرد است", "bd_e_bat"))

        add(o("bs_o1", "bs_q1", "بله، برآمده است", "bs_e_replace"))
        add(o("bs_o2", "bs_q1", "خیر، طبیعی است", "bs_e_replace"))

        add(o("oc_o1", "oc_q1", "بله، با همه شارژرها", "oc_q2"))
        add(o("oc_o2", "oc_q1", "خیر، فقط با شارژر خاص", "oc_e_charger"))
        add(o("oc_o3", "oc_q2", "بله، شارژ سریع فعال", "oc_e_ic"))
        add(o("oc_o4", "oc_q2", "خیر، شارژ معمولی", "oc_e_bat"))

        add(o("cpd_o1", "cpd_q1", "بله، شل است", "cpd_q2"))
        add(o("cpd_o2", "cpd_q1", "خیر، محکم است", "cpd_e_clean"))
        add(o("cpd_o3", "cpd_q2", "بله، پین‌ها شکسته", "cpd_e_replace"))
        add(o("cpd_o4", "cpd_q2", "خیر، پین‌ها سالم", "cpd_e_clean"))

        add(o("wc_o1", "wc_q1", "بله، قاب ضخیم", "wc_e_case"))
        add(o("wc_o2", "wc_q1", "خیر، قاب نازک", "wc_q2"))
        add(o("wc_o3", "wc_q2", "بله، پد دیگر تست شد", "wc_e_coil"))
        add(o("wc_o4", "wc_q2", "خیر، فقط یک پد", "wc_e_ic"))

        add(o("bfp_o1", "bfp_q1", "بله، درصد پرش دارد", "bfp_e_cal"))
        add(o("bfp_o2", "bfp_q1", "خیر، ثابت است", "bfp_e_bat"))

        add(o("ncw_o1", "ncw_q1", "بله، با کابل شارژ می‌شود", "ncw_e_coil"))
        add(o("ncw_o2", "ncw_q1", "خیر، با کابل هم کند", "ncw_e_pad"))

        add(o("cnd_o1", "cnd_q1", "بله، با کابل دیگر تست شد", "cnd_q2"))
        add(o("cnd_o2", "cnd_q1", "خیر، فقط یک کابل", "cnd_e_cable"))
        add(o("cnd_o3", "cnd_q2", "بله، خطوط قطع هستند", "cnd_e_ic"))
        add(o("cnd_o4", "cnd_q2", "خیر، خطوط سالم", "cnd_e_port"))

        // ═══════════ DISPLAY ═══════════
        add(o("lb_o1", "lb_q1", "بله، شکستگی هست", "lb_e_replace"))
        add(o("lb_o2", "lb_q1", "خیر، شکستگی نیست", "lb_q2"))
        add(o("lb_o3", "lb_q2", "بله، خطوط دارد", "lb_e_replace"))
        add(o("lb_o4", "lb_q2", "خیر، تصویر سالم", "lb_e_replace"))

        add(o("ti_o1", "ti_q1", "بله، کل صفحه بی‌پاسخ", "ti_q2"))
        add(o("ti_o2", "ti_q1", "خیر، بخشی کار می‌کند", "ti_q2"))
        add(o("ti_o3", "ti_q2", "بله، بعد از تعویض گلس", "ti_e_connector"))
        add(o("ti_o4", "ti_q2", "خیر، ناگهانی", "ti_e_ic"))
        add(o("ti_o5", "ti_q2", "نمی‌دانم", "ti_e_digitizer"))

        add(o("gt_o1", "gt_q1", "بله، با شارژر غیراصل", "gt_q2"))
        add(o("gt_o2", "gt_q1", "خیر، همیشه", "gt_e_ic"))
        add(o("gt_o3", "gt_q2", "بله، با برداشتن شارژر رفع می‌شود", "gt_e_charger"))
        add(o("gt_o4", "gt_q2", "خیر، رفع نمی‌شود", "gt_e_digitizer"))

        add(o("tp_o1", "tp_q1", "نوار بالا", "tp_e_flex"))
        add(o("tp_o2", "tp_q1", "نوار پایین", "tp_e_flex"))
        add(o("tp_o3", "tp_q1", "بخش میانی", "tp_e_digitizer"))

        add(o("dl_o1", "dl_q1", "بله، خطوط ثابت هستند", "dl_q2"))
        add(o("dl_o2", "dl_q1", "خیر، متغیر هستند", "dl_e_flex"))
        add(o("dl_o3", "dl_q2", "بله، با فشار تغییر می‌کنند", "dl_e_flex"))
        add(o("dl_o4", "dl_q2", "خیر، ثابت می‌مانند", "dl_e_lcd"))
        add(o("dl_o5", "dl_q2", "نمی‌دانم", "dl_e_ic"))

        add(o("df_o1", "df_q1", "بله، فقط در روشنایی کم", "df_e_backlight"))
        add(o("df_o2", "df_q1", "خیر، همیشه پرش دارد", "df_e_lcd"))

        add(o("dwht_o1", "dwht_q1", "بله، سفید است", "dwht_e_lcd"))
        add(o("dwht_o2", "dwht_q1", "خیر، رنگ دیگر", "dwht_e_ic"))

        add(o("dp_o1", "dp_q1", "یک یا دو پیکسل", "dp_e_replace"))
        add(o("dp_o2", "dp_q1", "بیشتر از 5 پیکسل", "dp_e_replace"))

        add(o("lbr_o1", "lbr_q1", "بله، حداکثر هم کم است", "lbr_e_lcd"))
        add(o("lbr_o2", "lbr_q1", "خیر، در حداکثر کافی است", "lbr_e_backlight"))

        add(o("dbi_o1", "dbi_q1", "بله، سایه دیده می‌شود", "dbi_e_replace"))
        add(o("dbi_o2", "dbi_q1", "خیر، واضح است", "dbi_e_replace"))

        add(o("td_o1", "td_q1", "بله، سیستم کند است", "td_e_sw"))
        add(o("td_o2", "td_q1", "خیر، فقط تاچ کند", "td_e_ic"))

        add(o("sp_o1", "sp_q1", "بله، بلند شده", "sp_e_replace"))
        add(o("sp_o2", "sp_q1", "خیر، صاف است", "sp_e_replace"))

        // ═══════════ NETWORK ═══════════
        add(o("ns_o1", "ns_q1", "بله، SIM شناسایی می‌شود", "ns_q2"))
        add(o("ns_o2", "ns_q1", "خیر، SIM را نمی‌شناسد", "ns_e_sim"))
        add(o("ns_o3", "ns_q2", "بله، IMEI معتبر", "ns_q3"))
        add(o("ns_o4", "ns_q2", "خیر، IMEI صفر است", "ns_e_imei"))
        add(o("ns_o5", "ns_q3", "بله، ولتاژ هست", "ns_e_rf"))
        add(o("ns_o6", "ns_q3", "خیر، ولتاژ نیست", "ns_e_baseband"))

        add(o("ni_o1", "ni_q1", "IMEI صفر", "ni_e_nvram"))
        add(o("ni_o2", "ni_q1", "IMEI نامعتبر", "ni_e_bb"))

        add(o("wi_o1", "wi_q1", "بله، خاموش می‌شود", "wi_e_sw"))
        add(o("wi_o2", "wi_q1", "خیر، روشن می‌ماند", "wi_q2"))
        add(o("wi_o3", "wi_q2", "بله، شبکه‌ها را پیدا می‌کند", "wi_e_ant"))
        add(o("wi_o4", "wi_q2", "خیر، پیدا نمی‌کند", "wi_e_ic"))

        add(o("bt_o1", "bt_q1", "بله، خاموش می‌شود", "bt_e_sw"))
        add(o("bt_o2", "bt_q1", "خیر، روشن است", "bt_e_ic"))

        add(o("nsd_o1", "nsd_q1", "بله، در دستگاه دیگر کار می‌کند", "nsd_e_slot"))
        add(o("nsd_o2", "nsd_q1", "خیر، آن هم کار نمی‌کند", "nsd_e_sim"))
        add(o("nsd_o3", "nsd_q1", "تست نکردم", "nsd_e_ic"))

        add(o("nsl_o1", "nsl_q1", "بله، همه جا کند", "nsl_e_operator"))
        add(o("nsl_o2", "nsl_q1", "خیر، بعضی جاها", "nsl_e_apn"))

        add(o("gps_o1", "gps_q1", "بله، کاملاً آفلاین", "gps_e_mod"))
        add(o("gps_o2", "gps_q1", "خیر، دیر پیدا می‌کند", "gps_e_sw"))

        add(o("nfc_o1", "nfc_q1", "بله، خاموش می‌شود", "nfc_e_sw"))
        add(o("nfc_o2", "nfc_q1", "خیر، روشن است", "nfc_e_ant"))

        add(o("ds_o1", "ds_q1", "اسلات 1", "ds_e_slot"))
        add(o("ds_o2", "ds_q1", "اسلات 2", "ds_e_slot"))

        add(o("hs_o1", "hs_q1", "بله، روشن می‌شود", "hs_e_sw"))
        add(o("hs_o2", "hs_q1", "خیر، روشن نمی‌شود", "hs_e_sw"))

        // ═══════════ AUDIO ═══════════
        add(o("nsnd_o1", "nsnd_q1", "بله، با هندزفری صدا دارد", "nsnd_e_spk"))
        add(o("nsnd_o2", "nsnd_q1", "خیر، با هندزفری هم نه", "nsnd_q2"))
        add(o("nsnd_o3", "nsnd_q2", "بله، خش دارد", "nsnd_e_ic"))
        add(o("nsnd_o4", "nsnd_q2", "خیر، کاملاً ساکت", "nsnd_e_conn"))

        add(o("mi_o1", "mi_q1", "بله، در ضبط هم نه", "mi_q2"))
        add(o("mi_o2", "mi_q1", "خیر، فقط در تماس", "mi_e_ic"))
        add(o("mi_o3", "mi_q2", "بله، همه اپ‌ها", "mi_e_mic"))
        add(o("mi_o4", "mi_q2", "خیر، فقط یک اپ", "mi_e_sw"))

        add(o("spn_o1", "spn_q1", "بله، در همه صداها", "spn_e_spk"))
        add(o("spn_o2", "spn_q1", "خیر، فقط در صدای بلند", "spn_e_amp"))

        add(o("ep_o1", "ep_q1", "بله، شنیده نمی‌شود", "ep_e_spk"))
        add(o("ep_o2", "ep_q1", "خیر، فقط ضعیف است", "ep_e_conn"))

        add(o("hj_o1", "hj_q1", "بله، شناسایی می‌شود", "hj_e_clean"))
        add(o("hj_o2", "hj_q1", "خیر، شناسایی نمی‌شود", "hj_e_jack"))

        add(o("rl_o1", "rl_q1", "بله، خیلی ضعیف", "rl_e_spk"))
        add(o("rl_o2", "rl_q1", "خیر، در وسط کافی است", "rl_e_clean"))

        add(o("nac_o1", "nac_q1", "بله، هر دو طرف", "nac_e_ic"))
        add(o("nac_o2", "nac_q1", "خیر، فقط یک طرف", "nac_e_sw"))

        add(o("lc_o1", "lc_q1", "بله، در همه صداها", "lc_e_spk"))
        add(o("lc_o2", "lc_q1", "خیر، فقط در صدای بلند", "lc_e_amp"))
    }
}