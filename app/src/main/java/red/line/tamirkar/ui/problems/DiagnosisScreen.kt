package red.line.tamirkar.ui.problems

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import red.line.tamirkar.domain.model.DiagnosisNode
import red.line.tamirkar.domain.model.ProblemSeverity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisScreen(
    onBackClick: () -> Unit,
    onProblemFound: (String) -> Unit,
    viewModel: DiagnosisViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val path by viewModel.path.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("عیب‌یابی هوشمند") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                actions = {
                    if (path.isNotEmpty()) {
                        IconButton(onClick = { viewModel.reset() }) {
                            Icon(Icons.Default.Replay, contentDescription = "شروع مجدد")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
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

            // Path breadcrumb
            if (path.isNotEmpty()) {
                PathBreadcrumb(path = path, onBackStep = { viewModel.goBack() })
                Spacer(modifier = Modifier.height(16.dp))
            }

            when (val currentState = state) {
                is DiagnosisState.Loading -> {
                    DiagnosisLoading()
                }
                is DiagnosisState.Question -> {
                    QuestionCard(
                        node = currentState.node,
                        onAnswerSelected = { answer ->
                            viewModel.onAnswerSelected(currentState.node, answer)
                        }
                    )
                }
                is DiagnosisState.Result -> {
                    DiagnosisResultCard(
                        problem = currentState.problem,
                        guideId = currentState.guideId,
                        onProblemClick = { problemId ->
                            onProblemFound(problemId)
                        },
                        onRestart = { viewModel.reset() }
                    )
                }
                is DiagnosisState.Error -> {
                    ErrorCard(message = currentState.message, onRetry = { viewModel.reset() })
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PathBreadcrumb(path: List<DiagnosisNode>, onBackStep: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "مسیر عیب‌یابی",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(10.dp))
            path.forEachIndexed { index, node ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = node.question,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (index < path.size - 1) {
                    Box(
                        modifier = Modifier
                            .padding(start = 13.dp)
                            .width(2.dp)
                            .height(16.dp)
                            .background(
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                    )
                }
            }
            if (path.size > 1) {
                Spacer(modifier = Modifier.height(10.dp))
                TextButton(onClick = onBackStep) {
                    Icon(Icons.Default.Undo, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("گام قبلی")
                }
            }
        }
    }
}

@Composable
fun QuestionCard(node: DiagnosisNode, onAnswerSelected: (String) -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(28.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.AccountTree,
                            null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = "سؤال عیب‌یابی",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = node.question,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            node.description?.let { desc ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            node.options.forEach { option ->
                val severityColor = when (option.severity) {
                    ProblemSeverity.LOW -> Color(0xFF4CAF50)
                    ProblemSeverity.MEDIUM -> Color(0xFFFF9800)
                    ProblemSeverity.HIGH -> Color(0xFFF44336)
                    ProblemSeverity.CRITICAL -> Color(0xFFB71C1C)
                    null -> MaterialTheme.colorScheme.primary
                }

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onAnswerSelected(option.label) },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = severityColor.copy(alpha = 0.12f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.ChevronLeft,
                                    null,
                                    tint = severityColor,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(18.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = option.label,
                                style = MaterialTheme.typography.titleMedium
                            )
                            option.advice?.let { advice ->
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = advice,
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
}

@Composable
fun DiagnosisResultCard(
    problem: red.line.tamirkar.domain.model.Problem?,
    guideId: String?,
    onProblemClick: (String) -> Unit,
    onRestart: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (problem != null) Color(0xFF4CAF50).copy(alpha = 0.12f)
                else MaterialTheme.colorScheme.errorContainer
            ) {
                Box(
                    modifier = Modifier.size(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (problem != null) Icons.Default.CheckCircle else Icons.Default.Error,
                        null,
                        modifier = Modifier.size(56.dp),
                        tint = if (problem != null) Color(0xFF4CAF50)
                        else MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (problem != null) "مشکل شناسایی شد" else "مشکلی یافت نشد",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (problem != null) {
                Text(
                    text = problem.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = problem.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onProblemClick(problem.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Build, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("مشاهده جزئیات و راهنمای تعمیر")
                }
            } else {
                Text(
                    text = "با توجه به پاسخ‌های شما، مشکل قابل شناسایی نبود. لطفاً از لیست مشکلات استفاده کنید.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onRestart,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Replay, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("شروع مجدد عیب‌یابی")
            }
        }
    }
}

@Composable
fun DiagnosisLoading() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("در حال بارگذاری درخت عیب‌یابی...")
        }
    }
}

@Composable
fun ErrorCard(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Error, null, tint = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Text(message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("تلاش مجدد")
        }
    }
}
