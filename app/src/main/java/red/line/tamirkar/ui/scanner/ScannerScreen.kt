package red.line.tamirkar.ui.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.*
import red.line.tamirkar.domain.model.BarcodeFormat
import red.line.tamirkar.domain.model.ScanResult
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen(
    onBackClick: () -> Unit,
    viewModel: ScannerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()
    val scannerMode by viewModel.scannerMode.collectAsState()
    val flashEnabled by viewModel.flashEnabled.collectAsState()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("اسکنر") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.7f),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (hasCameraPermission) {
                CameraPreviewWithOverlay(
                    viewModel = viewModel,
                    lifecycleOwner = lifecycleOwner,
                    flashEnabled = flashEnabled,
                    scannerMode = scannerMode
                )

                // Mode selector at bottom
                ScannerModeSelector(
                    selectedMode = scannerMode,
                    onModeSelected = { viewModel.setMode(it) },
                    flashEnabled = flashEnabled,
                    onFlashToggle = { viewModel.toggleFlash() },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            } else {
                CameraPermissionDenied(
                    onRequestPermission = { permissionLauncher.launch(Manifest.permission.CAMERA) }
                )
            }

            // Result Bottom Sheet
            AnimatedVisibility(
                visible = uiState is ScannerUiState.BarcodeDetected || uiState is ScannerUiState.TextDetected,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                when (val state = uiState) {
                    is ScannerUiState.BarcodeDetected -> {
                        BarcodeResultSheet(
                            result = state.result,
                            onDismiss = { viewModel.reset() },
                            viewModel = viewModel
                        )
                    }
                    is ScannerUiState.TextDetected -> {
                        TextResultSheet(
                            result = state.result,
                            onDismiss = { viewModel.reset() },
                            viewModel = viewModel
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun CameraPreviewWithOverlay(
    viewModel: ScannerViewModel,
    lifecycleOwner: LifecycleOwner,
    flashEnabled: Boolean,
    scannerMode: ScannerMode
) {
    val context = LocalContext.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var previewUseCase by remember { mutableStateOf<Preview?>(null) }
    var camera by remember { mutableStateOf<Camera?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder()
                        .setTargetResolution(Size(1280, 720))
                        .build()
                        .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                    previewUseCase = preview

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val image = InputImage.fromMediaImage(
                                mediaImage,
                                imageProxy.imageInfo.rotationDegrees
                            )

                            when (scannerMode) {
                                ScannerMode.BARCODE -> {
                                    val scanner = BarcodeScanning.getClient()
                                    scanner.process(image)
                                        .addOnSuccessListener { barcodes ->
                                            barcodes.firstOrNull()?.rawValue?.let { rawValue ->
                                                val format = when (barcodes.firstOrNull()?.format) {
                                                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE -> BarcodeFormat.QR_CODE
                                                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_AZTEC -> BarcodeFormat.AZTEC
                                                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
                                                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_UPC_A -> BarcodeFormat.UPC_A
                                                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_EAN_13 -> BarcodeFormat.EAN_13
                                                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_CODE_128 -> BarcodeFormat.CODE_128
                                                    else -> BarcodeFormat.UNKNOWN
                                                }
                                                viewModel.onBarcodeDetected(rawValue, format)
                                            }
                                        }
                                        .addOnCompleteListener {
                                            imageProxy.close()
                                        }
                                }
                                ScannerMode.OCR -> {
                                    // OCR with ML Kit Text Recognition
                                    val recognizer = com.google.mlkit.vision.text.TextRecognition.getClient(
                                        com.google.mlkit.vision.text.latin.TextRecognizerOptions.Builder().build()
                                    )
                                    recognizer.process(image)
                                        .addOnSuccessListener { visionText ->
                                            if (visionText.text.isNotBlank()) {
                                                val blocks = visionText.textBlocks.map { it.text }
                                                viewModel.onTextDetected(visionText.text, blocks)
                                            }
                                        }
                                        .addOnCompleteListener {
                                            imageProxy.close()
                                        }
                                }
                            }
                        } else {
                            imageProxy.close()
                        }
                    }

                    try {
                        cameraProvider.unbindAll()
                        camera = cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalysis
                        )
                        camera?.cameraControl?.enableTorch(flashEnabled)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay with viewfinder
        ScannerOverlay()

        // Flash effect on detection
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500, easing = EaseInOutCubic),
                repeatMode = RepeatMode.Reverse
            ),
            label = "overlay_pulse"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan.copy(alpha = alpha))
        )
    }

    LaunchedEffect(flashEnabled) {
        camera?.cameraControl?.enableTorch(flashEnabled)
    }
}

@Composable
fun ScannerOverlay() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Dark overlay with transparent cutout
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        // Transparent center cutout
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent)
            ) {
                // Corner markers
                ScannerCorners(modifier = Modifier.fillMaxSize())
            }
        }

        // Guide text
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(180.dp))
                Text(
                    text = "بارکد یا QR را درون قاب قرار دهید",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ScannerCorners(modifier: Modifier = Modifier) {
    val cornerColor = MaterialTheme.colorScheme.primary
    val cornerSize = 30.dp
    val strokeWidth = 4.dp

    Box(modifier = modifier) {
        // Top-left corner
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.TopStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(strokeWidth)
                    .background(cornerColor)
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier
                    .width(strokeWidth)
                    .fillMaxHeight()
                    .background(cornerColor)
                    .align(Alignment.TopStart)
            )
        }

        // Top-right corner
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(strokeWidth)
                    .background(cornerColor)
                    .align(Alignment.TopEnd)
            )
            Box(
                modifier = Modifier
                    .width(strokeWidth)
                    .fillMaxHeight()
                    .background(cornerColor)
                    .align(Alignment.TopEnd)
            )
        }

        // Bottom-left corner
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(strokeWidth)
                    .background(cornerColor)
                    .align(Alignment.BottomStart)
            )
            Box(
                modifier = Modifier
                    .width(strokeWidth)
                    .fillMaxHeight()
                    .background(cornerColor)
                    .align(Alignment.BottomStart)
            )
        }

        // Bottom-right corner
        Box(
            modifier = Modifier
                .size(cornerSize)
                .align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(strokeWidth)
                    .background(cornerColor)
                    .align(Alignment.BottomEnd)
            )
            Box(
                modifier = Modifier
                    .width(strokeWidth)
                    .fillMaxHeight()
                    .background(cornerColor)
                    .align(Alignment.BottomEnd)
            )
        }

        // Scanning line animation
        val infiniteTransition = rememberInfiniteTransition(label = "scan_line")
        val offsetY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scan_line_anim"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                .align(Alignment.TopCenter)
                .offset(y = (offsetY * 280).dp)
        )
    }
}

@Composable
fun ScannerModeSelector(
    selectedMode: ScannerMode,
    onModeSelected: (ScannerMode) -> Unit,
    flashEnabled: Boolean,
    onFlashToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Flash toggle
        IconButton(
            onClick = onFlashToggle,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Icon(
                imageVector = if (flashEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                contentDescription = "فلش",
                tint = if (flashEnabled) MaterialTheme.colorScheme.primary else Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mode selector
        SingleChoiceSegmentedButtonRow {
            ScannerMode.entries.forEachIndexed { index, mode ->
                SegmentedButton(
                    selected = selectedMode == mode,
                    onClick = { onModeSelected(mode) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = ScannerMode.entries.size
                    ),
                    icon = {
                        if (selectedMode == mode) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                ) {
                    Text(mode.label)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CameraPermissionDenied(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "نیاز به دسترسی دوربین",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "برای اسکن بارکد و تشخیص متن، دسترسی به دوربین ضروری است.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRequestPermission) {
            Icon(Icons.Default.CameraAlt, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("اعطای دسترسی")
        }
    }
}
