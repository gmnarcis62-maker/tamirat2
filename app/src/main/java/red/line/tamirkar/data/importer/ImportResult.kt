package red.line.tamirkar.data.importer

data class ImportResult(
    val success: Boolean,
    val message: String,
    val brandsImported: Int = 0,
    val modelsImported: Int = 0,
    val problemsImported: Int = 0,
    val diagnosisNodesImported: Int = 0,
    val errors: List<String> = emptyList()
)