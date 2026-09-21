package red.line.tamirkar.domain.diagnosis

import red.line.tamirkar.data.local.dao.DiagnosisAnswerDao
import red.line.tamirkar.data.local.dao.DiagnosisNodeDao
import red.line.tamirkar.data.local.dao.DiagnosisOptionDao
import red.line.tamirkar.data.local.dao.DiagnosisSessionDao
import red.line.tamirkar.data.local.entity.DiagnosisAnswerEntity
import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.DiagnosisOptionEntity
import red.line.tamirkar.data.local.entity.DiagnosisSessionEntity
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiagnosisEngineImpl @Inject constructor(
    private val sessionDao: DiagnosisSessionDao,
    private val answerDao: DiagnosisAnswerDao,
    private val nodeDao: DiagnosisNodeDao,
    private val optionDao: DiagnosisOptionDao
) : DiagnosisEngine {

    override suspend fun createSession(problemId: String, modelId: String?): String {
        val sessionId = UUID.randomUUID().toString()
        val startNode = nodeDao.getStartNode()

        val session = DiagnosisSessionEntity(
            id = sessionId,
            repairCaseId = null,
            modelId = modelId,
            problemId = problemId,
            startedAt = System.currentTimeMillis(),
            finishedAt = null,
            currentNodeId = startNode?.id,
            status = if (startNode == null) "COMPLETED" else "IN_PROGRESS"
        )
        sessionDao.insert(session)
        return sessionId
    }

    override suspend fun getCurrentNode(sessionId: String): DiagnosisNodeEntity? {
        val session = sessionDao.getById(sessionId) ?: return null
        if (session.status == "COMPLETED") return null
        val nodeId = session.currentNodeId ?: return null
        return nodeDao.getById(nodeId)
    }

    override suspend fun getOptions(nodeId: String): List<DiagnosisOptionEntity> {
        return optionDao.getByNodeId(nodeId)
    }

    override suspend fun submitAnswer(sessionId: String, optionId: String) {
        val session = sessionDao.getById(sessionId) ?: return
        val option = optionDao.getById(optionId) ?: return
        val currentNodeId = session.currentNodeId ?: return

        // ۱. ثبت پاسخ در دیتابیس
        val answer = DiagnosisAnswerEntity(
            id = UUID.randomUUID().toString(),
            sessionId = sessionId,
            nodeId = currentNodeId,
            optionId = optionId,
            value = option.value,
            timestamp = System.currentTimeMillis()
        )
        answerDao.insert(answer)

        // ۲. تعیین نود بعدی
        val nextNodeId = option.nextNodeId
        val nextNode = nextNodeId?.let { nodeDao.getById(it) }

        val updated = if (nextNode == null || nextNode.isEndNode) {
            session.copy(
                currentNodeId = null,
                status = "COMPLETED",
                finishedAt = System.currentTimeMillis()
            )
        } else {
            session.copy(currentNodeId = nextNode.id)
        }
        sessionDao.update(updated)
    }

    override suspend fun goBack(sessionId: String) {
        val session = sessionDao.getById(sessionId) ?: return
        val answers = answerDao.getBySessionSync(sessionId)
        if (answers.isEmpty()) return

        val lastAnswer = answers.last()
        answerDao.deleteById(lastAnswer.id)

        val updated = session.copy(
            currentNodeId = lastAnswer.nodeId,
            status = "IN_PROGRESS",
            finishedAt = null
        )
        sessionDao.update(updated)
    }

    override suspend fun getSession(sessionId: String): DiagnosisSessionEntity? {
        return sessionDao.getById(sessionId)
    }
}