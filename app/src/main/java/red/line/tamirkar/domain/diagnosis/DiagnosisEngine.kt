package red.line.tamirkar.domain.diagnosis

import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.DiagnosisOptionEntity
import red.line.tamirkar.data.local.entity.DiagnosisSessionEntity

interface DiagnosisEngine {
    suspend fun createSession(problemId: String, modelId: String?): String
    suspend fun getCurrentNode(sessionId: String): DiagnosisNodeEntity?
    suspend fun getOptions(nodeId: String): List<DiagnosisOptionEntity>
    suspend fun submitAnswer(sessionId: String, optionId: String)
    suspend fun goBack(sessionId: String)
    suspend fun getSession(sessionId: String): DiagnosisSessionEntity?
}