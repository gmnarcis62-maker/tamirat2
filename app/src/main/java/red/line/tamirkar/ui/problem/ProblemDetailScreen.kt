package red.line.tamirkar.ui.problem

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import red.line.tamirkar.domain.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemDetailScreen(
    onBackClick: () -> Unit,
    onNavigateToGuide: (String) -> Unit,
    viewModel: ProblemDetailViewModel = hiltViewModel()
) {
    val problem by viewModel.problem.collectAsState()
    val expandedSections by viewModel.expandedSections.collectAsState()
    val scrollState = rememberScrollState()

    if (problem == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val p = problem!!
    val severityColor = Color(android.graphics.Color.parseColor(p.severity.color))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(p.title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = severityColor.copy(alpha = 0.15f),
                    titleContentColor = severityColor
                )
            )
        },
        floatingActionButton = {
            if (p.repairGuide != null) {
                ExtendedFloatingActionButton(
                    onClick = { onNavigateToGuide(p.id) },
                    icon = { Icon(Icons.Default.Build, contentDescription = null) },
                    text = { Text("راهنمای تعمیر") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header Card
            ProblemHeaderCard(problem = p, severityColor = severityColor)

            Spacer(modifier = Modifier.height(16.dp))

            // Symptoms Section
            ExpandableSection(
                title = "علائم و نشانه‌ها",
                icon = Icons.Default.Medication,
                sectionKey = "symptoms",
                expandedSections = expandedSections,
                onToggle = { viewModel.toggleSection("symptoms") }
            ) {
                SymptomsList(symptoms = p.symptoms)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Causes Section
            ExpandableSection(
                title = "دلایل خرابی (${p.causes.size})",
                icon = Icons.Default.Search,
                sectionKey = "causes",
                expandedSections = expandedSections,
                onToggle = { viewModel.toggleSection("causes") }
            ) {
                CausesList(
                    causes = p.causes,
                    onCauseClick = { viewModel.selectCause(it) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Solutions Section
            ExpandableSection(
                title = "راه‌حل‌های پیشنهادی (${p.solutions.size})",
                icon = Icons.Default.Handyman,
                sectionKey = "solutions",
                expandedSections = expandedSections,
                onToggle = { viewModel.toggleSection("solutions") }
            ) {
                SolutionsList(
                    solutions = p.solutions,
                    onSolutionClick = { viewModel.selectSolution(it) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Info Section
            ExpandableSection(
                title = "اطلاعات تکمیلی",
                icon = Icons.Default.Info,
                sectionKey = "info",
                expandedSections = expandedSections,
                onToggle = { viewModel.toggleSection("info") }
            ) {
                ProblemInfoCard(problem = p)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Warnings Section
            if (p.warningNotes.isNotEmpty()) {
                ExpandableSection(
                    title = "هشدارهای ایمنی",
                    icon = Icons.Default.Warning,
                    sectionKey = "warnings",
                    expandedSections = expandedSections,
                    onToggle = { viewModel.toggleSection("warnings") }
                ) {
                    WarningsList(warnings = p.warningNotes)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Tools & Parts Section
            if (p.requiredTools.isNotEmpty() || p.requiredParts.isNotEmpty()) {
                ExpandableSection(
                    title = "ابزار و قطعات مورد نیاز",
                    icon = Icons.Default.Construction,
                    sectionKey = "tools",
                    expandedSections = expandedSections,
                    onToggle = { viewModel.toggleSection("tools") }
                ) {
                    ToolsAndPartsCard(
                        tools = p.requiredTools,
                        parts = p.requiredParts
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ProblemHeaderCard(problem: ProblemDetail, severityColor: Color) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = problem.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = severityColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = problem.severity.label,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = severityColor
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = problem.category.label,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                if (problem.isCommon) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = problem.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.Timer,
                    value = problem.estimatedTime,
                    label = "زمان"
                )
                StatItem(
                    icon = Icons.Default.Savings,
                    value = problem.estimatedCost?.let { "${it.min.toInt()}-${it.max.toInt()}" } ?: "-",
                    label = "هزینه (تومان)"
                )
                StatItem(
                    icon = Icons.Default.Grade,
                    value = "${problem.successRate}%",
                    label = "نرخ موفقیت"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Difficulty
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "سطح دشواری: ",
                    style = MaterialTheme.typography.bodyMedium
                )
                DifficultyStars(stars = problem.difficulty.stars, label = problem.difficulty.label)
            }
        }
    }
}

@Composable
fun DifficultyStars(stars: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(4) { index ->
            Icon(
                imageVector = if (index < stars) Icons.Default.Star else Icons.Default.StarOutline,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (index < stars) Color(0xFFFFB300) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun StatItem(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
fun ExpandableSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    sectionKey: String,
    expandedSections: Set<String>,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    val isExpanded = expandedSections.contains(sectionKey)

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                IconButton(onClick = onToggle) {
                    Icon(
                        if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "بستن" else "باز کردن"
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun SymptomsList(symptoms: List<String>) {
    Column {
        symptoms.forEachIndexed { index, symptom ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = symptom,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CausesList(causes: List<ProblemCause>, onCauseClick: (ProblemCause) -> Unit) {
    Column {
        causes.forEach { cause ->
            val probabilityColor = when {
                cause.probability >= 70 -> Color(0xFFF44336)
                cause.probability >= 40 -> Color(0xFFFF9800)
                else -> Color(0xFF4CAF50)
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = { onCauseClick(cause) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = cause.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = probabilityColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${cause.probability}%",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = probabilityColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    if (cause.testMethod.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                Icons.Default.Science,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = cause.testMethod,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SolutionsList(solutions: List<ProblemSolution>, onSolutionClick: (ProblemSolution) -> Unit) {
    Column {
        solutions.forEach { solution ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = { onSolutionClick(solution) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = solution.description,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                        if (solution.requiresProfessional) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.errorContainer
                            ) {
                                Text(
                                    text = "تخصصی",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        solution.estimatedCost?.let {
                            InfoChipSmall(icon = Icons.Default.AttachMoney, text = "${it.min.toInt()}-${it.max.toInt()}")
                        }
                        InfoChipSmall(icon = Icons.Default.Timer, text = solution.timeRequired)
                        InfoChipSmall(icon = Icons.Default.TrendingUp, text = "${solution.successRate}% موفقیت")
                    }

                    if (solution.steps.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        solution.steps.take(3).forEachIndexed { index, step ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "${index + 1}.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        if (solution.steps.size > 3) {
                            Text(
                                text = "+ ${solution.steps.size - 3} مرحله دیگر...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 4.dp, start = 18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChipSmall(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
fun ProblemInfoCard(problem: ProblemDetail) {
    Column {
        InfoRow(label = "دسته‌بندی", value = problem.category.label)
        InfoRow(label = "سطح شدت", value = problem.severity.label)
        InfoRow(label = "سطح دشواری", value = problem.difficulty.label)
        InfoRow(label = "زمان تقریبی", value = problem.estimatedTime)
        problem.estimatedCost?.let {
            InfoRow(label = "هزینه تقریبی", value = "${it.min.toInt()} - ${it.max.toInt()} ${it.currency}")
        }
        InfoRow(label = "نرخ موفقیت", value = "${problem.successRate}%")
        if (problem.isCommon) {
            InfoRow(label = "مشکل رایج", value = "بله")
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun WarningsList(warnings: List<String>) {
    Column {
        warnings.forEach { warning ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = warning,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

@Composable
fun ToolsAndPartsCard(tools: List<String>, parts: List<String>) {
    Column {
        if (tools.isNotEmpty()) {
            Text(
                text = "ابزارهای مورد نیاز:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow {
                tools.forEach { tool ->
                    AssistChip(
                        onClick = {},
                        label = { Text(tool) },
                        leadingIcon = {
                            Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(18.dp))
                        }
                    )
                }
            }
        }
        if (parts.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "قطعات مورد نیاز:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow {
                parts.forEach { part ->
                    AssistChip(
                        onClick = {},
                        label = { Text(part) },
                        leadingIcon = {
                            Icon(Icons.Default.Memory, contentDescription = null, modifier = Modifier.size(18.dp))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val hGapPx = 8.dp.roundToPx()
        val vGapPx = 8.dp.roundToPx()
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        val rowWidths = mutableListOf<Int>()
        val rowHeights = mutableListOf<Int>()

        var row = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var rowWidth = 0
        var rowHeight = 0

        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)
            if (rowWidth + placeable.width + if (row.isEmpty()) 0 else hGapPx > constraints.maxWidth) {
                rows.add(row)
                rowWidths.add(rowWidth)
                rowHeights.add(rowHeight)
                row = mutableListOf()
                rowWidth = 0
                rowHeight = 0
            }
            row.add(placeable)
            rowWidth += placeable.width + if (row.size > 1) hGapPx else 0
            rowHeight = maxOf(rowHeight, placeable.height)
        }
        if (row.isNotEmpty()) {
            rows.add(row)
            rowWidths.add(rowWidth)
            rowHeights.add(rowHeight)
        }

        val height = rowHeights.sum() + (rows.size - 1).coerceAtLeast(0) * vGapPx
        layout(constraints.maxWidth, height) {
            var y = 0
            rows.forEachIndexed { rowIndex, rowPlaceables ->
                var x = when (horizontalArrangement) {
                    Arrangement.End -> constraints.maxWidth - rowWidths[rowIndex]
                    Arrangement.Center -> (constraints.maxWidth - rowWidths[rowIndex]) / 2
                    else -> 0
                }
                rowPlaceables.forEach { placeable ->
                    placeable.placeRelative(x, y)
                    x += placeable.width + hGapPx
                }
                y += rowHeights[rowIndex] + vGapPx
            }
        }
    }
}
