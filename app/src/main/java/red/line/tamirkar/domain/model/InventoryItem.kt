package red.line.tamirkar.domain.model

data class InventoryItem(
    val id: String,
    val partNumber: String? = null,
    val name: String,
    val category: String? = null,
    val quantity: Int = 0,
    val minQuantity: Int = 0,
    val purchasePrice: Double? = null,
    val salePrice: Double? = null,
    val supplier: String? = null,
    val location: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val isLowStock: Boolean
        get() = quantity <= minQuantity && minQuantity > 0

    val profit: Double?
        get() = if (purchasePrice != null && salePrice != null) salePrice - purchasePrice else null

    val profitPercent: Double?
        get() = if (purchasePrice != null && purchasePrice > 0 && salePrice != null) {
            ((salePrice - purchasePrice) / purchasePrice) * 100
        } else null
}

enum class InventoryCategory(val label: String) {
    BATTERY("باتری"),
    SCREEN("ال‌سی‌دی و تاچ"),
    CHARGING_IC("آی‌سی شارژ"),
    POWER_IC("آی‌سی تغذیه"),
    CONNECTOR("کانکتور"),
    COIL("سیم‌پیچ / کویل"),
    CAPACITOR("خازن"),
    DIODE("دیود"),
    BACKLIGHT("بک‌لایت"),
    CAMERA("دوربین"),
    SPEAKER("بلندگو"),
    MICROPHONE("میکروفن"),
    FLEX("فلکس"),
    FRAME("قاب و شاسی"),
    OTHER("سایر")
}
