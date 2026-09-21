package red.line.tamirkar.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import red.line.tamirkar.core.normalization.PersianNormalizer
import red.line.tamirkar.core.result.RepairResult
import red.line.tamirkar.data.importer.JsonImporter
import red.line.tamirkar.data.importer.MergeStrategy
import red.line.tamirkar.data.importer.ImportResult
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.entity.*
import red.line.tamirkar.data.local.seed.SeedData
import red.line.tamirkar.data.repository.mapper.toDomainList
import red.line.tamirkar.data.repository.mapper.toDomain
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.RepairRepository
import java.util.UUID
import javax.inject.Inject

class RepairRepositoryImpl @Inject constructor(
    private val db: RepairDatabase,
    private val seedData: SeedData
) : RepairRepository {

    override fun getBrands(): Flow<RepairResult<List<Brand>>> = flow {
        emit(RepairResult.Loading)
        try {
            db.brandDao().getAll().map { list ->
                RepairResult.Success(list.toDomainList())
            }.collect { emit(it) }
        } catch (e: Exception) {
            emit(RepairResult.Error("خطا در دریافت برندها", e))
        }
    }

    override fun getModelsByBrand(brandId: String): Flow<RepairResult<List<DeviceModel>>> = flow {
        emit(RepairResult.Loading)
        try {
            db.deviceModelDao().getByBrand(brandId).map { list ->
                RepairResult.Success(list.toDomainList())
            }.collect { emit(it) }
        } catch (e: Exception) {
            emit(RepairResult.Error("خطا در دریافت مدل‌ها", e))
        }
    }

    override suspend fun getBrandById(brandId: String): Brand? {
        return db.brandDao().getById(brandId)?.toDomain()
    }

    override suspend fun getModelById(modelId: String): DeviceModel? {
        return db.deviceModelDao().getById(modelId)?.toDomain()
    }

    override suspend fun searchModels(query: String): RepairResult<List<DeviceModel>> {
        return try {
            val normalized = PersianNormalizer.normalize(query)
            RepairResult.Success(db.deviceModelDao().search(normalized).toDomainList())
        } catch (e: Exception) {
            RepairResult.Error("خطا در جستجو", e)
        }
    }

    override suspend fun getAllDeviceModels(): List<DeviceModel> {
        return try {
            db.deviceModelDao().getAllSync().toDomainList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Customer methods
    override suspend fun getAllCustomers(): List<Customer> {
        return db.customerDao().getAllSync().map { it.toDomain() }
    }

    override suspend fun getCustomerById(id: String): Customer? {
        return db.customerDao().getById(id)?.toDomain()
    }

    override suspend fun saveCustomer(customer: Customer) {
        db.customerDao().insert(customer.toEntity())
    }

    override suspend fun deleteCustomer(id: String) {
        db.customerDao().deleteById(id)
    }

    // Repair Job methods
    override suspend fun getAllRepairJobs(): List<RepairJob> {
        return db.repairCaseDao().getAllSync().map { it.toDomain() }
    }

    override suspend fun getRepairJobById(id: String): RepairJob? {
        return db.repairCaseDao().getById(id)?.toDomain()
    }

    override suspend fun saveRepairJob(job: RepairJob) {
        db.repairCaseDao().insert(job.toEntity())
    }

    override suspend fun deleteRepairJob(id: String) {
        db.repairCaseDao().deleteById(id)
    }

    // Inventory methods
    override suspend fun getAllInventoryItems(): List<InventoryItem> {
        return db.inventoryItemDao().getAllSync().map { it.toDomain() }
    }

    override suspend fun getInventoryItemById(id: String): InventoryItem? {
        return db.inventoryItemDao().getById(id)?.toDomain()
    }

    override suspend fun saveInventoryItem(item: InventoryItem) {
        db.inventoryItemDao().insert(item.toEntity())
    }

    override suspend fun deleteInventoryItem(id: String) {
        db.inventoryItemDao().deleteById(id)
    }

    // Invoice methods
    override suspend fun getAllInvoices(): List<Invoice> {
        return db.invoiceDao().getAllSync().map { it.toDomain() }
    }

    override suspend fun getInvoiceById(id: String): Invoice? {
        return db.invoiceDao().getById(id)?.toDomain()
    }

    override suspend fun saveInvoice(invoice: Invoice) {
        db.invoiceDao().insert(invoice.toEntity())
    }

    override suspend fun deleteInvoice(id: String) {
        db.invoiceDao().deleteById(id)
    }

    override suspend fun getProblemDetail(problemId: String): ProblemDetail? {
        return try {
            val entity = db.problemDao().getById(problemId) ?: return null
            val category = db.problemCategoryDao().getById(entity.categoryId)

            ProblemDetail(
                id = entity.id,
                title = entity.title,
                titleEn = entity.titleEn,
                description = buildProblemDescription(entity),
                severity = mapSeverity(entity.severity),
                difficulty = mapDifficulty(entity.difficulty),
                estimatedRepairTime = entity.estimatedRepairTime ?: 30,
                requiresDisassembly = entity.requiresDisassembly,
                requiresMicroscope = entity.requiresMicroscope,
                requiresPowerSupply = entity.requiresPowerSupply,
                requiresMultimeter = entity.requiresMultimeter,
                requiresOscilloscope = entity.requiresOscilloscope,
                isCommon = entity.isCommon,
                symptoms = getSymptomsForProblem(entity.id),
                possibleCauses = getPossibleCauses(entity.id),
                requiredTools = getRequiredTools(entity),
                repairSteps = emptyList(),
                warnings = getWarnings(entity),
                relatedProblems = getRelatedProblems(entity.id),
                errorCodes = getErrorCodes(entity.id),
                categoryTitle = category?.title ?: ""
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun buildProblemDescription(entity: ProblemEntity): String {
        return when (entity.id) {
            "problem_no_power" -> "دستگاه هیچ واکنشی به شارژر یا کلید Power نشان نمی‌دهد. صفحه کاملاً تاریک است و هیچ ویبره یا صدایی شنیده نمی‌شود. این مشکل می‌تواند ناشی از خرابی باتری، PMIC، CPU یا کوتاهی در خطوط تغذیه باشد."
            "problem_no_charge" -> "دستگاه روشن می‌شود اما با شارژر وصل شده، درصد باتری افزایش نمی‌یابد یا پیام شارژ نشان داده نمی‌شود."
            "problem_bootloop" -> "دستگاه به طور مداوم در لوگوی اولیه راه‌اندازی مجدد می‌شود و به سیستم‌عامل نمی‌رسد."
            "problem_touch_issue" -> "صفحه نمایش روشن است اما تاچ پاسخ نمی‌دهد یا لمس‌ها در جای اشتباه ثبت می‌شوند."
            "problem_no_signal" -> "دستگاه SIM را تشخیص می‌دهد اما آنتن ندارد یا دائماً در حال جستجو است."
            else -> entity.description ?: "توضیحاتی برای این مشکل ثبت نشده است."
        }
    }

    private fun mapSeverity(severity: String?): ProblemSeverity {
        return when (severity?.lowercase()) {
            "low" -> ProblemSeverity.LOW
            "medium" -> ProblemSeverity.MEDIUM
            "high" -> ProblemSeverity.HIGH
            "critical" -> ProblemSeverity.CRITICAL
            else -> ProblemSeverity.MEDIUM
        }
    }

    private fun mapDifficulty(difficulty: String?): ProblemDifficulty {
        return when (difficulty?.lowercase()) {
            "beginner" -> ProblemDifficulty.BEGINNER
            "intermediate" -> ProblemDifficulty.INTERMEDIATE
            "advanced" -> ProblemDifficulty.ADVANCED
            "expert" -> ProblemDifficulty.EXPERT
            else -> ProblemDifficulty.INTERMEDIATE
        }
    }

    private fun getSymptomsForProblem(problemId: String): List<SymptomItem> {
        return when (problemId) {
            "problem_no_power" -> listOf(
                SymptomItem("1", "هیچ واکنشی به کلید Power", "ویبره، صدا یا LED روشن نمی‌شود"),
                SymptomItem("2", "عدم نمایش شارژ", "با شارژر وصل شده هیچ آیکون شارژ نمایش داده نمی‌شود"),
                SymptomItem("3", "جریان صفر روی منبع تغذیه", "با اتصال به DC Power Supply هیچ جریانی نمی‌کشد"),
                SymptomItem("4", "داغ شدن ناحیه PMIC", "بخش بالایی برد در حالت خاموش داغ می‌شود")
            )
            "problem_no_charge" -> listOf(
                SymptomItem("1", "عدم افزایش درصد باتری", "با وجود اتصال شارژر، باتری شارژ نمی‌شود"),
                SymptomItem("2", "قطع و وصل شارژ", "کابل را که تکان می‌دهید، شارژ قطع می‌شود"),
                SymptomItem("3", "پیام 'شارژ نامناسب'", "با شارژر اصلی نیز این پیام نمایش داده می‌شود")
            )
            "problem_bootloop" -> listOf(
                SymptomItem("1", "ری‌استارت مداوم", "دستگاه در لوگوی برند ریبوت می‌شود"),
                SymptomItem("2", "ورود به Recovery", "گاهی به صفحه Recovery می‌رود"),
                SymptomItem("3", "پس از آپدیت", "مشکل پس از بروزرسانی نرم‌افزاری شروع شده")
            )
            "problem_touch_issue" -> listOf(
                SymptomItem("1", "عدم پاسخ تاچ", "لمس صفحه هیچ اثری ندارد"),
                SymptomItem("2", "تاچ خودکار (Ghost Touch)", "صفحه بدون لمس کاربر عمل می‌کند"),
                SymptomItem("3", "عدم دقت تاچ", "لمس در جای دیگری ثبت می‌شود")
            )
            "problem_no_signal" -> listOf(
                SymptomItem("1", "آنتن صفر", "هیچ آنتنی نمایش داده نمی‌شود"),
                SymptomItem("2", "جستجوی دائمی", "دستگاه دائماً در حال جستجوی شبکه است"),
                SymptomItem("3", "IMEI صفر یا نامعتبر", "با کد *#06# IMEI نمایش داده نمی‌شود")
            )
            else -> emptyList()
        }
    }

    private fun getPossibleCauses(problemId: String): List<String> {
        return when (problemId) {
            "problem_no_power" -> listOf(
                "باتری خالی یا خراب (ولتاژ کمتر از 3.5V)",
                "خرابی PMIC (Power Management IC)",
                "خرابی CPU یا RAM (UFS/eMMC)",
                "کوتاهی در خط VBAT یا VPH_PWR",
                "خرابی کلید Power یا مسیر آن",
                "آب‌خوردگی یا خوردگی در مسیر تغذیه",
                "خرابی کویل‌های Boost/Buck",
                "قطع شدن خطوط کلاک اصلی (Clock Lines)"
            )
            "problem_no_charge" -> listOf(
                "خرابی IC شارژ (Charging IC)",
                "خرابی کانکتور شارژ",
                "قطع شدن خط D+/D- یا CC (Type-C)",
                "خرابی باتری (high internal resistance)",
                "نرم‌افزاری: خرابی Battery Profile"
            )
            "problem_bootloop" -> listOf(
                "خرابی نرم‌افزار (Corrupted Boot Image)",
                "خرابی Storage (UFS/eMMC)",
                "ناسازگاری رام نصب شده",
                "خرابی CPU (بخصوص در اثر داغ شدن)",
                "تداخل در Magisk Module یا Root"
            )
            "problem_touch_issue" -> listOf(
                "خرابی تاچ IC (Touch Controller)",
                "قطع شدن خطوط MIPI DSI",
                "خرابی دیجیتایزر (Digitizer)",
                "نرم‌افزاری: خرابی درایور تاچ",
                "آب‌خوردگی در مسیر تاچ"
            )
            "problem_no_signal" -> listOf(
                "خرابی IC آنتن (Transceiver / RF IC)",
                "قطع شدن خطوط آنتن (Antenna Matching)",
                "خرابی Baseband",
                "NVRAM corrupted (IMEI صفر)",
                "خرابی SIM Reader"
            )
            else -> emptyList()
        }
    }

    private fun getRequiredTools(entity: ProblemEntity): List<String> {
        val tools = mutableListOf<String>()
        tools.add("مولتی‌متر دیجیتال")
        if (entity.requiresPowerSupply) tools.add("منبع تغذیه DC")
        if (entity.requiresMicroscope) tools.add("میکروسکوپ تعمیراتی")
        if (entity.requiresOscilloscope) tools.add("اسیلوسکوپ")
        tools.add("هیتر و ایستگاه لحیم‌کاری")
        tools.add("قلم مکانیکی و ابزار باز کردن")
        return tools
    }

    private fun getWarnings(entity: ProblemEntity): List<String> {
        return when (entity.id) {
            "problem_no_power" -> listOf(
                "قبل از هر اقدامی، باتری را جدا کنید تا از کوتاهی و آسیب بیشتر جلوگیری شود.",
                "تست حرارتی با احتیاط انجام شود. دمای بیش از 80 درجه می‌تواند برد را تخریب کند.",
                "تعویض PMIC نیاز به Underfill جدید و مهارت بالا دارد."
            )
            "problem_no_charge" -> listOf(
                "از شارژرهای غیراستاندارد استفاده نکنید.",
                "قبل از تعویض IC شارژ، کانکتور و کابل را بررسی کنید."
            )
            "problem_bootloop" -> listOf(
                "قبل از فلش کردن، بکاپ NVRAM و IMEI را بگیرید.",
                "از رام رسمی و متناسب با مدل دقیق استفاده کنید."
            )
            "problem_touch_issue" -> listOf(
                "در صورت خرابی تاچ IC، تعویض آن نیاز به Reballing دارد.",
                "خطوط MIPI DSI بسیار حساس هستند و لحیم‌کاری نادرست باعث خرابی دائمی می‌شود."
            )
            "problem_no_signal" -> listOf(
                "تعمیر Baseband بسیار تخصصی است و نیاز به ابزارهای پیشرفته دارد.",
                "NVRAM backup قبل از هر اقدامی ضروری است."
            )
            else -> listOf("همیشه از ابزارهای مناسب و ESD-safe استفاده کنید.")
        }
    }

    private fun getRelatedProblems(problemId: String): List<RelatedProblemItem> {
        return when (problemId) {
            "problem_no_power" -> listOf(
                RelatedProblemItem("problem_no_charge", "شارژ نمی‌شود", "شارژ"),
                RelatedProblemItem("problem_bootloop", "بوت‌لوپ", "نرم‌افزار")
            )
            "problem_no_charge" -> listOf(
                RelatedProblemItem("problem_no_power", "گوشی روشن نمی‌شود", "تغذیه")
            )
            "problem_bootloop" -> listOf(
                RelatedProblemItem("problem_no_power", "گوشی روشن نمی‌شود", "تغذیه")
            )
            else -> emptyList()
        }
    }

    private fun getErrorCodes(problemId: String): List<ErrorCodeItem> {
        return when (problemId) {
            "problem_no_power" -> listOf(
                ErrorCodeItem("0xE8000015", "USB Communication Error - ممکن است نشانه خرابی PMIC یا CPU باشد", "high"),
                ErrorCodeItem("4013", "iTunes Error - مشکل در ارتباط با CPU یا NAND", "high")
            )
            "problem_bootloop" -> listOf(
                ErrorCodeItem("9006", "iTunes Restore Error - مشکل در ارتباط با Storage", "medium"),
                ErrorCodeItem("0x80070057", "Parameter Error - خرابی پارتیشن یا Boot", "medium")
            )
            else -> emptyList()
        }
    }

    override suspend fun startDiagnosis(problemId: String, modelId: String?): RepairResult<String> {
        return try {
            val tree = db.diagnosisTreeDao().getTreeForProblem(problemId, modelId)
                ?: return RepairResult.Error("درخت عیب‌یابی یافت نشد")
            val root = db.diagnosisNodeDao().getRootNode(tree.id)
                ?: return RepairResult.Error("گره شروع یافت نشد")
            val sessionId = UUID.randomUUID().toString()
            db.diagnosisSessionDao().insert(
                DiagnosisSessionEntity(
                    id = sessionId,
                    problemId = problemId,
                    modelId = modelId,
                    currentNodeId = root.id
                )
            )
            RepairResult.Success(sessionId)
        } catch (e: Exception) {
            RepairResult.Error("خطا در شروع عیب‌یابی", e)
        }
    }

    override suspend fun answerDiagnosis(sessionId: String, optionId: String): RepairResult<DiagnosisNodeEntity?> {
        return try {
            val option = db.diagnosisOptionDao().getById(optionId) ?: return RepairResult.Success(null)
            val session = db.diagnosisSessionDao().getById(sessionId) ?: return RepairResult.Error("نشست یافت نشد")
            db.diagnosisAnswerDao().insert(
                DiagnosisAnswerEntity(
                    id = UUID.randomUUID().toString(),
                    sessionId = sessionId,
                    nodeId = option.nodeId,
                    optionId = optionId
                )
            )
            val nextNode = option.nextNodeId?.let { db.diagnosisNodeDao().getById(it) }
            db.diagnosisSessionDao().update(
                session.copy(
                    currentNodeId = nextNode?.id,
                    status = if (nextNode == null) "COMPLETED" else "IN_PROGRESS",
                    finishedAt = if (nextNode == null) System.currentTimeMillis() else null
                )
            )
            RepairResult.Success(nextNode)
        } catch (e: Exception) {
            RepairResult.Error("خطا در ثبت پاسخ", e)
        }
    }

    override suspend fun backtrackDiagnosis(sessionId: String): RepairResult<DiagnosisNodeEntity?> {
        return try {
            val answers = db.diagnosisAnswerDao().getBySessionSync(sessionId)
            if (answers.isEmpty()) {
                val session = db.diagnosisSessionDao().getById(sessionId)
                val node = session?.currentNodeId?.let { db.diagnosisNodeDao().getById(it) }
                return RepairResult.Success(node)
            }
            val last = answers.last()
            db.diagnosisAnswerDao().deleteById(last.id)
            val remaining = db.diagnosisAnswerDao().getBySessionSync(sessionId)
            val node = if (remaining.isNotEmpty()) {
                db.diagnosisNodeDao().getById(remaining.last().nodeId)
            } else {
                val session = db.diagnosisSessionDao().getById(sessionId)
                session?.currentNodeId?.let { db.diagnosisNodeDao().getById(it) }
            }
            val session = db.diagnosisSessionDao().getById(sessionId)
            if (session != null) {
                db.diagnosisSessionDao().update(session.copy(status = "IN_PROGRESS", finishedAt = null, currentNodeId = node?.id))
            }
            RepairResult.Success(node)
        } catch (e: Exception) {
            RepairResult.Error("خطا در بازگشت", e)
        }
    }

    override fun getSearchHistory(): Flow<List<SearchHistoryEntity>> = db.searchHistoryDao().getRecent()

    override suspend fun addSearchHistory(query: String, resultCount: Int) {
        db.searchHistoryDao().insert(
            SearchHistoryEntity(
                id = UUID.randomUUID().toString(),
                query = query,
                normalizedQuery = PersianNormalizer.normalize(query),
                resultCount = resultCount
            )
        )
        db.searchHistoryDao().pruneOld()
    }

    override suspend fun seedDatabaseIfNeeded() {
        if (db.brandDao().count() == 0) {
            db.brandDao().insertAll(seedData.brands())
        }
        if (db.problemCategoryDao().count() == 0) {
            db.problemCategoryDao().insertAll(seedData.categories())
        }
        if (db.problemDao().count() == 0) {
            db.problemDao().insertAll(seedData.problems())
        }
        if (db.deviceModelDao().count() == 0) {
            db.deviceModelDao().insertAll(seedData.models())
        }
        if (db.diagnosisTreeDao().count() == 0) {
            db.diagnosisTreeDao().insertAll(seedData.diagnosisTrees())
        }
        if (db.diagnosisNodeDao().count() == 0) {
            db.diagnosisNodeDao().insertAll(seedData.diagnosisNodes())
        }
        if (db.diagnosisOptionDao().count() == 0) {
            db.diagnosisOptionDao().insertAll(seedData.diagnosisOptions())
        }
    }

    override suspend fun importJson(content: String, strategy: MergeStrategy): ImportResult {
        return JsonImporter(db).import(content, strategy)
    }

    // Mappers
    private fun CustomerEntity.toDomain(): Customer = Customer(
        id = id, name = name, phone = phone, email = email, address = address, notes = notes
    )

    private fun Customer.toEntity(): CustomerEntity = CustomerEntity(
        id = id, name = name, phone = phone, email = email, address = address, notes = notes
    )

    private fun RepairCaseEntity.toDomain(): RepairJob = RepairJob(
        id = id, customerId = customerId, customerName = "", customerPhone = "",
        deviceModelId = deviceModelId, deviceModelName = null, customerDeviceId = customerDeviceId,
        reportedProblem = reportedProblem, technicianDiagnosis = technicianDiagnosis,
        status = try { RepairStatus.valueOf(status) } catch (_: Exception) { RepairStatus.RECEIVED },
        totalCost = totalCost, partsCost = partsCost, laborCost = laborCost,
        receivedAt = receivedAt, startedAt = startedAt, completedAt = completedAt,
        deliveredAt = deliveredAt, notes = notes
    )

    private fun RepairJob.toEntity(): RepairCaseEntity = RepairCaseEntity(
        id = id, customerId = customerId, deviceModelId = deviceModelId,
        customerDeviceId = customerDeviceId, reportedProblem = reportedProblem,
        technicianDiagnosis = technicianDiagnosis, status = status.name,
        totalCost = totalCost, partsCost = partsCost, laborCost = laborCost,
        receivedAt = receivedAt, startedAt = startedAt, completedAt = completedAt,
        deliveredAt = deliveredAt, notes = notes
    )

    private fun InventoryItemEntity.toDomain(): InventoryItem = InventoryItem(
        id = id, partNumber = partNumber, name = name, category = category,
        quantity = quantity, minQuantity = minQuantity,
        purchasePrice = purchasePrice, salePrice = salePrice,
        supplier = supplier, location = location, notes = notes,
        createdAt = createdAt, updatedAt = updatedAt
    )

    private fun InventoryItem.toEntity(): InventoryItemEntity = InventoryItemEntity(
        id = id, partNumber = partNumber, name = name, category = category,
        quantity = quantity, minQuantity = minQuantity,
        purchasePrice = purchasePrice, salePrice = salePrice,
        supplier = supplier, location = location, notes = notes,
        createdAt = createdAt, updatedAt = updatedAt
    )

    private fun InvoiceEntity.toDomain(): Invoice {
        return Invoice(
            id = id, repairCaseId = repairCaseId,
            customerName = "", customerPhone = "", deviceModel = null,
            invoiceNumber = invoiceNumber, items = emptyList(),
            laborCost = 0.0, discount = discount ?: 0.0,
            totalAmount = totalAmount, finalAmount = finalAmount,
            isPaid = isPaid, paymentMethod = paymentMethod?.let {
                try { PaymentMethod.valueOf(it) } catch (_: Exception) { null }
            },
            pdfPath = pdfPath, createdAt = createdAt
        )
    }

    private fun Invoice.toEntity(): InvoiceEntity = InvoiceEntity(
        id = id, repairCaseId = repairCaseId ?: "",
        invoiceNumber = invoiceNumber, totalAmount = totalAmount,
        discount = discount, finalAmount = finalAmount,
        isPaid = isPaid, paymentMethod = paymentMethod?.name,
        pdfPath = pdfPath, createdAt = createdAt
    )
}
