package red.line.tamirkar.domain.model

data class Customer(
    val id: String,
    val name: String,
    val phone: String,
    val email: String? = null,
    val address: String? = null,
    val notes: String? = null,
    val repairCount: Int = 0,
    val totalSpent: Double = 0.0
)
