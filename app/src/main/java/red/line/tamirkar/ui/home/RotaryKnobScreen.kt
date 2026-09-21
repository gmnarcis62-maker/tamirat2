package red.line.tamirkar.ui.home

import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.unit.width
import androidx.compose.ui.unit.height
import android.view.HapticFeedbackConstants
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import red.line.tamirkar.R
import kotlin.math.*

@Composable
fun RotaryKnobScreen(
    onMenuItemClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel
) {
    val rotationAngle by viewModel.rotationAngle.collectAsState()
    val selectedIndex by viewModel.selectedIndex.collectAsState()
    val isDragging by viewModel.isDragging.collectAsState()
    val selectedItem by viewModel.selectedItem.collectAsState()
    val menuItems = viewModel.menuItems

    val context = LocalContext.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()

    val animatedRotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "rotation"
    )

    LaunchedEffect(selectedIndex) {
        view.performHapticFeedback(HapticFeedbackConstants.TEXT_HANDLE_MOVE)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF0D1B2A).copy(alpha = 0.75f),
                            Color(0xFF000000).copy(alpha = 0.92f)
                        )
                    )
                )
        )

        // دکمه تنظیمات در بالای صفحه دایره‌ای
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 40.dp, end = 24.dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "تنظیمات", tint = Color.White)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            InfoPanel(
                selectedItem = selectedItem,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(380.dp)) {
                    drawCircle(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color(0xFF00E5FF).copy(alpha = 0.3f),
                                Color(0xFFFFD740).copy(alpha = 0.3f),
                                Color(0xFF69F0AE).copy(alpha = 0.3f),
                                Color(0xFFFF4081).copy(alpha = 0.3f),
                                Color(0xFFE040FB).copy(alpha = 0.3f),
                                Color(0xFFFFAB40).copy(alpha = 0.3f),
                                Color(0xFF00E5FF).copy(alpha = 0.3f)
                            ),
                            center = center
                        ),
                        radius = size.minDimension / 2 - 10f,
                        style = Stroke(width = 2f)
                    )

                    val tickCount = 60
                    val outerRadius = size.minDimension / 2 - 24f
                    val innerRadius = size.minDimension / 2 - 36f
                    for (i in 0 until tickCount) {
                        val angle = Math.toRadians((i * 6.0) - 90.0)
                        val isMainTick = i % 5 == 0
                        val startR = if (isMainTick) innerRadius - 8 else innerRadius
                        val endR = outerRadius
                        val tickWidth = if (isMainTick) 2.5f else 1f
                        val tickAlpha = if (isMainTick) 0.7f else 0.3f
                        drawLine(
                            color = Color(0xFF00E5FF).copy(alpha = tickAlpha),
                            start = Offset(
                                center.x + cos(angle).toFloat() * startR,
                                center.y + sin(angle).toFloat() * startR
                            ),
                            end = Offset(
                                center.x + cos(angle).toFloat() * endR,
                                center.y + sin(angle).toFloat() * endR
                            ),
                            strokeWidth = tickWidth
                        )
                    }
                }

                RadialMenuItems(
                    menuItems = menuItems,
                    selectedIndex = selectedIndex,
                    isDragging = isDragging,
                    rotationAngle = animatedRotation,
                    onItemClick = { index ->
                        if (selectedIndex == index) {
                            val route = menuItems[index].route
                            onMenuItemClick(route)
                        } else {
                            viewModel.selectItem(index)
                        }
                    }
                )

                RotaryKnob(
                    rotationAngle = animatedRotation,
                    selectedItem = selectedItem,
                    isDragging = isDragging,
                    onRotationDelta = { delta -> viewModel.onRotationDelta(delta) },
                    onDragStart = { viewModel.onDragStart() },
                    onDragEnd = { viewModel.onDragEnd() },
                    onClick = {
                        selectedItem?.let { onMenuItemClick(it.route) }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(id = R.string.hint_rotate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun InfoPanel(selectedItem: red.line.tamirkar.domain.model.RotaryMenuItem?, modifier: Modifier = Modifier) {
    AnimatedContent(
        targetState = selectedItem,
        transitionSpec = {
            fadeIn(tween(250)) + slideInVertically { -it / 4 } togetherWith
            fadeOut(tween(200)) + slideOutVertically { -it / 4 }
        },
        label = "info_panel"
    ) { item ->
        Card(
            modifier = modifier.fillMaxWidth().heightIn(min = 110.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1B2A).copy(alpha = 0.72f)),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF00E5FF).copy(alpha = 0.5f), Color(0xFFE040FB).copy(alpha = 0.3f))
                )
            )
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val color = item?.color ?: Color(0xFF00E5FF)
                    Box(
                        modifier = Modifier.size(10.dp).background(color.copy(alpha = 0.9f), CircleShape).shadow(8.dp, CircleShape, spotColor = color)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = item?.label ?: stringResource(id = R.string.app_subtitle),
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontSize = 22.sp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                val description = when (item?.id) {
                    "troubleshoot" -> "عیب‌یابی هوشمند با درخت تصمیم برای تشخیص سریع مشکل"
                    "schematics" -> "مشاهده نقشه‌های شماتیک برد و مسیرهای تغذیه"
                    "power_diag" -> "تشخیص جریان‌کشی و تست نقاط تغذیه با راهنما"
                    "secret_codes" -> "کدهای مخفی سرویس و تست سخت‌افزاری"
                    "brands" -> "لیست برندها و مدل‌های گوشی‌های موجود"
                    "backup" -> "تهیه نسخه پشتیبان از اطلاعات برنامه"
                    "customer" -> "مدیریت اطلاعات مشتریان و تعمیرات"
                    "models" -> "جستجو و انتخاب مدل گوشی مورد نظر"
                    else -> stringResource(id = R.string.hint_center)
                }

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun RadialMenuItems(
    menuItems: List<red.line.tamirkar.domain.model.RotaryMenuItem>,
    selectedIndex: Int,
    isDragging: Boolean,
    rotationAngle: Float,
    onItemClick: (Int) -> Unit
) {
    val radius = 145.dp

    Box(modifier = Modifier.size(380.dp), contentAlignment = Alignment.Center) {
        menuItems.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            val angleRad = Math.toRadians((item.angleDegrees - rotationAngle).toDouble() - 90.0)
            val xOffset = (cos(angleRad) * radius.value).dp
            val yOffset = (sin(angleRad) * radius.value).dp

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.18f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "scale_$index"
            )

            val glowAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0.35f,
                animationSpec = tween(250),
                label = "glow_$index"
            )

            Box(
                modifier = Modifier
                    .offset(x = xOffset, y = yOffset)
                    .size(64.dp)
                    .scale(scale)
                    .clickable { onItemClick(index) }
                    .graphicsLayer { this.alpha = if (isDragging || isSelected) 1f else 0.85f },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        brush = Brush.radialGradient(
                            colors = listOf(item.color.copy(alpha = glowAlpha * 0.5f), item.color.copy(alpha = 0f))
                        ),
                        shape = CircleShape
                    )
                )

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(item.color.copy(alpha = 0.25f), Color(0xFF0A1929).copy(alpha = 0.9f))
                            ),
                            shape = CircleShape
                        )
                        .border(
                            width = if (isSelected) 2.5.dp else 1.2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(item.color.copy(alpha = glowAlpha), item.color.copy(alpha = glowAlpha * 0.5f))
                            ),
                            shape = CircleShape
                        )
                        .shadow(elevation = if (isSelected) 12.dp else 4.dp, shape = CircleShape, spotColor = item.color),
                    contentAlignment = Alignment.Center
                ) {
                    val icon = when (item.id) {
                        "troubleshoot" -> Icons.Default.Build
                        "schematics" -> Icons.Default.Map
                        "power_diag" -> Icons.Default.Bolt
                        "secret_codes" -> Icons.Default.Code
                        "brands" -> Icons.Default.Storefront
                        "backup" -> Icons.Default.Backup
                        "customer" -> Icons.Default.People
                        "models" -> Icons.Default.PhoneAndroid
                        else -> Icons.Default.Circle
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = item.label,
                        tint = item.color.copy(alpha = glowAlpha * 0.9f + 0.1f),
                        modifier = Modifier.size(26.dp)
                    )
                }

                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isSelected) item.color.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.6f),
                        fontSize = if (isSelected) 11.sp else 10.sp
                    ),
                    modifier = Modifier.offset(y = 42.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun RotaryKnob(
    rotationAngle: Float,
    selectedItem: red.line.tamirkar.domain.model.RotaryMenuItem?,
    isDragging: Boolean,
    onRotationDelta: (Float) -> Unit,
    onDragStart: () -> Unit,
    onDragEnd: () -> Unit,
    onClick: () -> Unit
) {
    val view = LocalView.current
    var center by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .size(180.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragEnd() }
                ) { change, dragAmount ->
                    change.consume()
                    val touchX = change.position.x
                    val touchY = change.position.y
                    val centerX = center.x
                    val centerY = center.y

                    val startAngle = atan2(touchY - centerY, touchX - centerX)
                    val endAngle = atan2(touchY + dragAmount.y - centerY, touchX + dragAmount.x - centerX)
                    var delta = Math.toDegrees((endAngle - startAngle).toDouble()).toFloat()

                    if (delta > 180) delta -= 360
                    if (delta < -180) delta += 360

                    onRotationDelta(delta)
                }
            }
            .onGloballyPositioned { coordinates ->
                val pos = coordinates.boundsInWindow()
                center = Offset(pos.size.width / 2f, pos.size.height / 2f)
            }
            .clickable(onClick = onClick)
            .graphicsLayer { rotationZ = rotationAngle },
        contentAlignment = Alignment.Center
    ) {
        val glowColor = selectedItem?.color ?: Color(0xFF00E5FF)
        val glowAlpha by animateFloatAsState(
            targetValue = if (isDragging) 0.7f else 0.4f,
            animationSpec = tween(400),
            label = "knob_glow"
        )

        Box(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.radialGradient(colors = listOf(glowColor.copy(alpha = glowAlpha * 0.35f), glowColor.copy(alpha = 0f))),
                shape = CircleShape
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF1B3A4B).copy(alpha = 0.95f), Color(0xFF0D1B2A).copy(alpha = 0.98f), Color(0xFF000000).copy(alpha = 0.99f))
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 2.5.dp,
                    brush = Brush.sweepGradient(colors = listOf(glowColor.copy(alpha = glowAlpha), glowColor.copy(alpha = glowAlpha * 0.4f), glowColor.copy(alpha = glowAlpha))),
                    shape = CircleShape
                )
                .shadow(elevation = 20.dp, shape = CircleShape, spotColor = glowColor),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(
                        brush = Brush.radialGradient(colors = listOf(Color(0xFF00E5FF).copy(alpha = 0.08f), Color.Transparent)),
                        shape = CircleShape
                    )
                    .border(width = 1.dp, color = glowColor.copy(alpha = 0.3f), shape = CircleShape)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .size(4.dp, 14.dp)
                        .background(glowColor.copy(alpha = 0.9f), RoundedCornerShape(2.dp))
                        .offset(y = (-55).dp)
                )

                val labelScale by animateFloatAsState(
                    targetValue = if (isDragging) 0.92f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "label_scale"
                )

                Text(
                    text = selectedItem?.label ?: stringResource(id = R.string.hint_select),
                    style = MaterialTheme.typography.titleMedium.copy(color = glowColor.copy(alpha = 0.95f), fontSize = 18.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.scale(labelScale)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    repeat(3) { i ->
                        val dotAlpha = if (i == 1) 1f else 0.4f
                        Box(modifier = Modifier.size(5.dp).background(glowColor.copy(alpha = dotAlpha), CircleShape))
                    }
                }
            }
        }
    }
}