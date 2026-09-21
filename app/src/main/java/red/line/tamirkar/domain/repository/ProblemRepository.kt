package red.line.tamirkar.domain.repository

import kotlinx.coroutines.flow.Flow
import red.line.tamirkar.domain.model.*

interface ProblemRepository {
    suspend fun getAllProblems(): List<Problem>
    suspend fun getProblemById(id: String): Problem?
    suspend fun getProblemsByCategory(category: ProblemCategory): List<Problem>
    suspend fun getProblemsBySeverity(severity: ProblemSeverity): List<Problem>
    suspend fun getCommonProblems(): List<Problem>
    suspend fun searchProblems(query: String): List<Problem>
    suspend fun getRelatedProblems(problemId: String): List<Problem>

    suspend fun getRepairGuide(problemId: String): RepairGuide?
    suspend fun getAllGuides(): List<RepairGuide>

    suspend fun getDiagnosisTree(): DiagnosisNode?
    suspend fun getDiagnosisNode(nodeId: String): DiagnosisNode?
    suspend fun getDiagnosisPath(startNodeId: String, answers: List<String>): List<DiagnosisNode>
}
