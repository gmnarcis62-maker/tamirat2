package red.line.tamirkar.data.repository

import kotlinx.coroutines.flow.*
import red.line.tamirkar.data.local.dao.DiagnosisNodeDao
import red.line.tamirkar.data.local.dao.ProblemDao
import red.line.tamirkar.data.local.dao.RepairGuideDao
import red.line.tamirkar.data.local.entity.DiagnosisNodeEntity
import red.line.tamirkar.data.local.entity.ProblemEntity
import red.line.tamirkar.data.local.entity.RepairGuideEntity
import red.line.tamirkar.domain.model.*
import red.line.tamirkar.domain.repository.ProblemRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProblemRepositoryImpl @Inject constructor(
    private val problemDao: ProblemDao,
    private val repairGuideDao: RepairGuideDao,
    private val diagnosisNodeDao: DiagnosisNodeDao
) : ProblemRepository {

    override suspend fun getAllProblems(): List<Problem> {
        return problemDao.getAll().first().map { it.toDomain() }
    }

    override suspend fun getProblemById(id: String): Problem? {
        return problemDao.getById(id)?.toDomain()
    }

    override suspend fun getProblemsByCategory(category: ProblemCategory): List<Problem> {
        return problemDao.getByCategory(category).first().map { it.toDomain() }
    }

    override suspend fun getProblemsBySeverity(severity: ProblemSeverity): List<Problem> {
        return problemDao.getBySeverity(severity).first().map { it.toDomain() }
    }

    override suspend fun getCommonProblems(): List<Problem> {
        return problemDao.getCommon().first().map { it.toDomain() }
    }

    override suspend fun searchProblems(query: String): List<Problem> {
        return problemDao.search(query).first().map { it.toDomain() }
    }

    override suspend fun getRelatedProblems(problemId: String): List<Problem> {
        val problem = getProblemById(problemId) ?: return emptyList()
        return problem.relatedProblems.mapNotNull { getProblemById(it) }
    }

    override suspend fun getRepairGuide(problemId: String): RepairGuide? {
        return repairGuideDao.getByProblemId(problemId)?.toDomain()
    }

    override suspend fun getAllGuides(): List<RepairGuide> {
        return repairGuideDao.getAll().first().map { it.toDomain() }
    }

    override suspend fun getDiagnosisTree(): DiagnosisNode? {
        return diagnosisNodeDao.getStartNode()?.toDomain()
    }

    override suspend fun getDiagnosisNode(nodeId: String): DiagnosisNode? {
        return diagnosisNodeDao.getById(nodeId)?.toDomain()
    }

    override suspend fun getDiagnosisPath(startNodeId: String, answers: List<String>): List<DiagnosisNode> {
        val path = mutableListOf<DiagnosisNode>()
        var currentNode = getDiagnosisNode(startNodeId) ?: return path
        path.add(currentNode)

        for (answer in answers) {
            val option = currentNode.options.find { it.label == answer } ?: break
            val nextId = option.nextNodeId ?: break
            currentNode = getDiagnosisNode(nextId) ?: break
            path.add(currentNode)
            if (currentNode.isEndNode) break
        }
        return path
    }

    // Mappers
    private fun ProblemEntity.toDomain() = Problem(
        id = id, title = title, description = description,
        category = category, severity = severity,
        symptoms = symptoms, commonCauses = commonCauses,
        estimatedFixTime = estimatedFixTime, estimatedCost = estimatedCost,
        difficulty = difficulty, requiredTools = requiredTools,
        requiredParts = requiredParts, warningNotes = warningNotes,
        successRate = successRate, isCommon = isCommon,
        relatedProblems = relatedProblems
    )

    private fun RepairGuideEntity.toDomain() = RepairGuide(
        id = id, problemId = problemId, title = title,
        description = description, steps = steps,
        warnings = warnings, tips = tips, videoUrl = videoUrl,
        imageUrls = imageUrls, estimatedTime = estimatedTime,
        difficulty = difficulty, requiredTools = requiredTools,
        requiredParts = requiredParts, prerequisites = prerequisites,
        testSteps = testSteps
    )

    private fun DiagnosisNodeEntity.toDomain() = DiagnosisNode(
        id = id, question = question, description = description,
        options = options, isStartNode = isStartNode,
        isEndNode = isEndNode, problemId = problemId,
        guideId = guideId, severity = severity
    )
}
