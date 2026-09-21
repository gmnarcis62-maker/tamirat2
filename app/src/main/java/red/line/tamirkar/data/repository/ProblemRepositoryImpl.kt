package red.line.tamirkar.data.repository

import red.line.tamirkar.data.local.seed.ProblemSeedData
import red.line.tamirkar.domain.model.DiagnosisNode
import red.line.tamirkar.domain.model.Problem
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity
import red.line.tamirkar.domain.model.RepairGuide
import red.line.tamirkar.domain.repository.ProblemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProblemRepositoryImpl @Inject constructor() : ProblemRepository {

    private val allProblems: List<Problem> by lazy {
        ProblemSeedData.getAllProblems()
    }

    override suspend fun getAllProblems(): List<Problem> = allProblems

    override suspend fun getProblemById(id: String): Problem? =
        allProblems.find { it.id == id }

    override suspend fun getProblemsByCategory(category: ProblemCategory): List<Problem> =
        allProblems.filter { it.category == category }

    override suspend fun getProblemsBySeverity(severity: ProblemSeverity): List<Problem> =
        allProblems.filter { it.severity == severity }

    override suspend fun getCommonProblems(): List<Problem> =
        allProblems.filter { it.isCommon }

    override suspend fun searchProblems(query: String): List<Problem> {
        val q = query.trim()
        if (q.isBlank()) return allProblems
        return allProblems.filter { problem ->
            problem.title.contains(q, ignoreCase = true) ||
            problem.description.contains(q, ignoreCase = true) ||
            problem.symptoms.any { it.contains(q, ignoreCase = true) } ||
            problem.commonCauses.any { it.contains(q, ignoreCase = true) }
        }
    }

    override suspend fun getRelatedProblems(problemId: String): List<Problem> {
        val problem = getProblemById(problemId) ?: return emptyList()
        return problem.relatedProblems.mapNotNull { getProblemById(it) }
    }

    override suspend fun getRepairGuide(problemId: String): RepairGuide? = null

    override suspend fun getAllGuides(): List<RepairGuide> = emptyList()

    override suspend fun getDiagnosisTree(): DiagnosisNode? = null

    override suspend fun getDiagnosisNode(nodeId: String): DiagnosisNode? = null

    override suspend fun getDiagnosisPath(
        startNodeId: String,
        answers: List<String>
    ): List<DiagnosisNode> = emptyList()
}