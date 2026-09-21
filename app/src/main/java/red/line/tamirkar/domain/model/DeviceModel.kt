package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DeviceModel(
    val id: String,
    val brandId: String,
    val name: String,
    val nameEn: String,
    val modelNumber: String,
    val slug: String = "",
    val chipset: String = "",
    val batteryCapacity: Int = 0,
    val releaseYear: Int = 0,
    val imageUrl: String? = null,
    val isPopular: Boolean = false
)