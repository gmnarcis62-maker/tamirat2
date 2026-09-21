package red.line.tamirkar.domain.repository

import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.core.result.RepairResult
import red.line.tamirkar.data.importer.ImportResult
import red.line.tamirkar.data.importer.MergeStrategy
import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.SearchHistoryEntity
import red.line.tamirkar.domain.model.*

interface RepairRepository {
    fun getBrands(): Flow<RepairResult<List<Brand>>>
    fun getModelsByBrand(brandId: String): Flow<RepairResult<List<DeviceModel>>>
    suspend fun getBrandById(brandId: String): Brand?
    suspend fun getModelById(modelId: String): DeviceModel?
    suspend fun searchModels(query: String): RepairResult<List<DeviceModel>>
    suspend fun getProblemDetail(problemId: String): ProblemDetail?
    suspend fun startDiagnosis(problemId: String, modelId: String?): RepairResult<String>
    suspend fun answerDiagnosis(sessionId: String, optionId: String): RepairResult<DiagnosisNodeEntity?>
    suspend fun backtrackDiagnosis(sessionId: String): RepairResult<DiagnosisNodeEntity?>

    // Customer methods
    suspend fun getAllCustomers(): List<Customer>
    suspend fun getCustomerById(id: String): Customer?
    suspend fun saveCustomer(customer: Customer)
    suspend fun deleteCustomer(id: String)

    // Repair Job methods
    suspend fun getAllRepairJobs(): List<RepairJob>
    suspend fun getRepairJobById(id: String): RepairJob?
    suspend fun saveRepairJob(job: RepairJob)
    suspend fun deleteRepairJob(id: String)

    // Device model list
    suspend fun getAllDeviceModels(): List<DeviceModel>

    // Inventory methods
    suspend fun getAllInventoryItems(): List<InventoryItem>
    suspend fun getInventoryItemById(id: String): InventoryItem?
    suspend fun saveInventoryItem(item: InventoryItem)
    suspend fun deleteInventoryItem(id: String)

    // Invoice methods
    suspend fun getAllInvoices(): List<Invoice>
    suspend fun getInvoiceById(id: String): Invoice?
    suspend fun saveInvoice(invoice: Invoice)
    suspend fun deleteInvoice(id: String)

    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>
    suspend fun addSearchHistory(query: String, resultCount: Int)
    suspend fun seedDatabaseIfNeeded()
    suspend fun importJson(content: String, strategy: MergeStrategy): ImportResult
}
