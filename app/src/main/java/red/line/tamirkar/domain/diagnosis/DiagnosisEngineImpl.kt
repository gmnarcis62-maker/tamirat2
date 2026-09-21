package red.line.tamirkar.domain.diagnosis

import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.DiagnosisOptionEntity
import red.line.tamirkar.data.local.entity.DiagnosisSessionEntity
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiagnosisEngineImpl @Inject constructor() : DiagnosisEngine {

    private data class Session(
        val id: String,
        val problemId: String,
        val modelId: String?,
        val startedAt: Long,
        var finishedAt: Long?,
        var currentNodeId: String?,
        var status: String,
        val answeredOptions: MutableList<String>
    )

    private val sessions = mutableMapOf<String, Session>()

    // داده‌های نمونه برای درخت تشخیصی
    private val nodes = mutableMapOf<String, DiagnosisNodeEntity>()

    init {
        // نود شروع
        nodes["start"] = DiagnosisNodeEntity(
            id = "start",
            question = "آیا دستگاه روشن می‌شود؟",
            description = "ابتدا دستگاه را با شارژر به برق وصل کنید و کلید Power را ۳ ثانیه نگه دارید.",
            options = emptyList(),
            isStartNode = true,
            isEndNode = false,
            problemId = null,
            guideId = null,
            severity = null
        )
        nodes["node_2"] = DiagnosisNodeEntity(
            id = "node_2",
            question = "آیا نشانه‌های نفوذ آب یا خوردگی روی برد دیده می‌شود؟",
            description = "قاب پشت را باز کنید و با ذره‌بین برد را بررسی کنید.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = false,
            problemId = null,
            guideId = null,
            severity = null
        )
        nodes["node_3"] = DiagnosisNodeEntity(
            id = "node_3",
            question = "آیا با منبع تغذیه، جریان‌کشی مشاهده می‌شود؟",
            description = "منبع تغذیه را روی 4.2V و 1A تنظیم کنید.",
            options = emptyList(),
            isStartNode = false,
            isEndNode = false,
            problemId = null,
            guideId = null,
            severity = null
        )
    }

    override suspend fun createSession(problemId: String, modelId: String?): String {
        val id = UUID.randomUUID().toString()
        val session = Session(
            id = id,
            problemId = problemId,
            modelId = modelId,
            startedAt = System.currentTimeMillis(),
            finishedAt = null,
            currentNodeId = "start",
            status = "IN_PROGRESS",
            answeredOptions = mutableListOf()
        )
        sessions[id] = session
        return id
    }

    override suspend fun getCurrentNode(sessionId: String): DiagnosisNodeEntity? {
        val session = sessions[sessionId] ?: return null
        if (session.status == "COMPLETED") return null
        val nodeId = session.currentNodeId ?: return null
        return nodes[nodeId]
    }

    override suspend fun getOptions(nodeId: String): List<DiagnosisOptionEntity> {
        // برای هر نود، دو گزینه بله/خیر برمی‌گردونیم
        return listOf(
            DiagnosisOptionEntity(
                id = "${nodeId}_yes",
                nodeId = nodeId,
                title = "بله",
                value = "yes",
                nextNodeId = null,
                condition = null
            ),
            DiagnosisOptionEntity(
                id = "${nodeId}_no",
                nodeId = nodeId,
                title = "خیر",
                value = "no",
                nextNodeId = null,
                condition = null
            )
        )
    }

    override suspend fun submitAnswer(sessionId: String, optionId: String) {
        val session = sessions[sessionId] ?: return
        session.answeredOptions.add(optionId)

        // اگر ۳ سؤال جواب داده شده، نتیجه نهایی رو اعلام کن
        if (session.answeredOptions.size >= 3) {
            session.status = "COMPLETED"
            session.currentNodeId = null
            session.finishedAt = System.currentTimeMillis()
        } else {
            session.currentNodeId = "node_${session.answeredOptions.size + 1}"
        }
    }

    override suspend fun goBack(sessionId: String) {
        val session = sessions[sessionId] ?: return
        if (session.answeredOptions.isEmpty()) return

        session.answeredOptions.removeAt(session.answeredOptions.size - 1)
        session.status = "IN_PROGRESS"
        session.finishedAt = null
        session.currentNodeId = if (session.answeredOptions.isEmpty()) {
            "start"
        } else {
            "node_${session.answeredOptions.size + 1}"
        }
    }

    override suspend fun getSession(sessionId: String): DiagnosisSessionEntity? {
        val session = sessions[sessionId] ?: return null
        return DiagnosisSessionEntity(
            id = session.id,
            repairCaseId = null,
            modelId = session.modelId,
            problemId = session.problemId,
            startedAt = session.startedAt,
            finishedAt = session.finishedAt,
            currentNodeId = session.currentNodeId,
            status = session.status
        )
    }
}