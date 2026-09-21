package red.line.tamirkar.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    val id: String,
    val name: String,
    val nameEn: String,
    val slug: String,
    val logoUrl: String? = null,
    val country: String = "",
    val deviceCount: Int = 0
)