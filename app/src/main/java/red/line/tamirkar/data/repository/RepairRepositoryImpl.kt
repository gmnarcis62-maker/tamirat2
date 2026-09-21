package red.line.tamirkar.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import red.line.tamirkar.core.result.RepairResult
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.SearchHistoryEntity
import red.line.tamirkar.domain.model.Brand
import red.line.tamirkar.domain.model.Customer
import red.line.tamirkar.domain.model.DeviceModel
import red.line.tamirkar.domain.model.InventoryItem
import red.line.tamirkar.domain.model.Invoice
import red.line.tamirkar.domain.model.ProblemDetail
import red.line.tamirkar.domain.model.RepairJob
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepairRepositoryImpl @Inject constructor(
    private val db: RepairDatabase
) : RepairRepository {

    override fun getBrands(): Flow<RepairResult<List<Brand>>> {
        return flowOf(RepairResult.Success(emptyList()))
    }

    override fun getModelsByBrand(brandId: String): Flow<RepairResult<List<DeviceModel>>> {
        return flowOf(RepairResult.Success(emptyList()))
    }

    override suspend fun getBrandById(brandId: String): Brand? = null

    override suspend fun getModelById(modelId: String): DeviceModel? = null

    override suspend fun searchModels(query: String): RepairResult<List<DeviceModel>> {
        return RepairResult.Success(emptyList())
    }

    override suspend fun getProblemDetail(problemId: String): ProblemDetail? = null

    override suspend fun startDiagnosis(problemId: String, modelId: String?): RepairResult<String> {
        return RepairResult.Success("session_id")
    }

    override suspend fun answerDiagnosis(
        sessionId: String,
        optionId: String
    ): RepairResult<DiagnosisNodeEntity?> = RepairResult.Success(null)

    override suspend fun backtrackDiagnosis(sessionId: String): RepairResult<DiagnosisNodeEntity?> =
        RepairResult.Success(null)

    override suspend fun getAllCustomers(): List<Customer> = emptyList()

    override suspend fun getCustomerById(id: String): Customer? = null

    override suspend fun saveCustomer(customer: Customer) {}

    override suspend fun deleteCustomer(id: String) {}

    override suspend fun getAllRepairJobs(): List<RepairJob> = emptyList()

    override suspend fun getRepairJobById(id: String): RepairJob? = null

    override suspend fun saveRepairJob(job: RepairJob) {}

    override suspend fun deleteRepairJob(id: String) {}

    override suspend fun getAllDeviceModels(): List<DeviceModel> = emptyList()

    override suspend fun getAllInventoryItems(): List<InventoryItem> = emptyList()

    override suspend fun getInventoryItemById(id: String): InventoryItem? = null

    override suspend fun saveInventoryItem(item: InventoryItem) {}

    override suspend fun deleteInventoryItem(id: String) {}

    override suspend fun getAllInvoices(): List<Invoice> = emptyList()

    override suspend fun getInvoiceById(id: String): Invoice? = null

    override suspend fun saveInvoice(invoice: Invoice) {}

    override suspend fun deleteInvoice(id: String) {}

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> = flowOf(emptyList())

    override suspend fun addSearchHistory(query: String, resultCount: Int) {}

    override suspend fun seedDatabaseIfNeeded() {}
}