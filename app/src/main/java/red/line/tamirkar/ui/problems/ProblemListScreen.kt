package red.line.tamirkar.ui.problems

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ScreenLockPortrait
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import red.line.tamirkar.domain.model.Problem
import red.line.tamirkar.domain.model.ProblemCategory
import red.line.tamirkar.domain.model.ProblemSeverity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemListScreen(
    onBackClick: () -> Unit,
    onProblemClick: (String) -> Unit,
    viewModel: ProblemListViewModel = hiltViewModel()
) {
    val problems by viewModel.problems.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "مشکلات و راه‌حل‌ها",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${problems.size} مورد",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            CategoryChips(
                selected = selectedCategory,
                onSelect = viewModel::onCategorySelected,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (isLoading) {
                LoadingState()
            } else if (problems.isEmpty()) {
                EmptyState(searchQuery.isNotBlank())
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 24.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val commonProblems = problems.filter { it.isCommon }.take(3)
                    val otherProblems = problems.filter { !it.isCommon }

                    if (commonProblems.isNotEmpty() && searchQuery.isBlank() && selectedCategory == null) {
                        item {
                            SectionHeader(
                                title = "مشکلات رایج",
                                icon = Icons.Default.TrendingUp,
                                count = commonProblems.size
                            )
                        }
                        items(commonProblems, key = { "common_${it.id}" }) { problem ->
                            ProblemCard(problem, onClick = { onProblemClick(problem.id) })
                        }
                        if (otherProblems.isNotEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                SectionHeader(
                                    title = "همه مشکلات",
                                    icon = Icons.Default.Build,
                                    count = otherProblems.size
                                )
                            }
                        }
                    }

                    val displayList = if (commonProblems.isNotEmpty() &&
                        searchQuery.isBlank() &&
                        selectedCategory == null) {
                        otherProblems
                    } else {
                        problems
                    }

                    items(displayList, key = { it.id }) { problem ->
                        ProblemCard(problem, onClick = { onProblemClick(problem.id) })
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════
//  SEARCH BAR
// ═══════════════════════════════════════════════════════════

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "جستجوی مشکل، علامت یا راه‌حل...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyMedium
            )
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "پاک کردن",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════
//  CATEGORY CHIPS
// ═══════════════════════════════════════════════════════════

@Composable
fun CategoryChips(
    selected: ProblemCategory?,
    onSelect: (ProblemCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val categories: List<ProblemCategory?> = listOf(null) + ProblemCategory.entries

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryChip(
                label = category?.label ?: "همه",
                icon = category?.let { getCategoryIcon(it) },
                isSelected = category == selected,
                onClick = { onSelect(category) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    label: String,
    icon: ImageVector?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant,
        label = "chip_bg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "chip_content"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        tonalElevation = if (isSelected) 0.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
//  SECTION HEADER
// ═══════════════════════════════════════════════════════════

@Composable
fun SectionHeader(
    title: String,
    icon: ImageVector,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
//  PROBLEM CARD
// ═══════════════════════════════════════════════════════════

@Composable
fun ProblemCard(
    problem: Problem,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        label = "card_scale"
    )

    Surface(
        onClick = {
            pressed = true
            onClick()
        },
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = getCategoryColor(problem.category).copy(alpha = 0.15f),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            getCategoryIcon(problem.category),
                            contentDescription = null,
                            tint = getCategoryColor(problem.category),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = problem.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = problem.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                SeverityPill(problem.severity)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoBadge(
                    icon = Icons.Default.Schedule,
                    text = problem.estimatedFixTime,
                    modifier = Modifier.weight(1f)
                )
                InfoBadge(
                    icon = Icons.Default.Build,
                    text = problem.difficulty.label,
                    modifier = Modifier.weight(1f)
                )
                InfoBadge(
                    icon = Icons.Default.Check,
                    text = "${problem.successRate}%",
                    accent = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun InfoBadge(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    accent: Boolean = false
) {
    val tint = if (accent) Color(0xFF10B981) else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = tint,
            maxLines = 1,
            fontWeight = if (accent) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun SeverityPill(severity: ProblemSeverity) {
    val color = parseColor(severity.color)
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = severity.label,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
//  STATES
// ═══════════════════════════════════════════════════════════

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "در حال بارگذاری...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyState(isSearch: Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        if (isSearch) Icons.Default.Search else Icons.Default.Build,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (isSearch) "نتیجه‌ای یافت نشد" else "مشکلی وجود ندارد",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isSearch) "عبارت دیگری را جستجو کنید"
                else "مشکلات به‌زودی اضافه می‌شوند",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════
//  HELPERS
// ═══════════════════════════════════════════════════════════

private fun getCategoryIcon(category: ProblemCategory): ImageVector = when (category) {
    ProblemCategory.POWER -> Icons.Default.Power
    ProblemCategory.CHARGING -> Icons.Default.BatteryChargingFull
    ProblemCategory.DISPLAY -> Icons.Default.ScreenLockPortrait
    ProblemCategory.NETWORK -> Icons.Default.NetworkCheck
    ProblemCategory.AUDIO -> Icons.Default.VolumeUp
    ProblemCategory.SOFTWARE -> Icons.Default.SystemUpdate
    ProblemCategory.CAMERA -> Icons.Default.CameraAlt
    ProblemCategory.CONNECTIVITY -> Icons.Default.NetworkCheck
    ProblemCategory.HARDWARE -> Icons.Default.Memory
    ProblemCategory.WATER -> Icons.Default.WaterDrop
    ProblemCategory.SENSOR -> Icons.Default.ScreenLockPortrait
}

private fun getCategoryColor(category: ProblemCategory): Color = when (category) {
    ProblemCategory.POWER -> Color(0xFFE53935)
    ProblemCategory.CHARGING -> Color(0xFF10B981)
    ProblemCategory.DISPLAY -> Color(0xFF3B82F6)
    ProblemCategory.NETWORK -> Color(0xFF8B5CF6)
    ProblemCategory.AUDIO -> Color(0xFFF59E0B)
    ProblemCategory.SOFTWARE -> Color(0xFF06B6D4)
    ProblemCategory.CAMERA -> Color(0xFFEC4899)
    ProblemCategory.CONNECTIVITY -> Color(0xFF06B6D4)
    ProblemCategory.HARDWARE -> Color(0xFF64748B)
    ProblemCategory.WATER -> Color(0xFF0EA5E9)
    ProblemCategory.SENSOR -> Color(0xFF8B5CF6)
}