package red.line.tamirkar.ui.problems

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import red.line.tamirkar.domain.model.ProblemDetail
import red.line.tamirkar.domain.model.RepairStepItem
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Inject

sealed class RepairGuideUiState {
    object Loading : RepairGuideUiState()
    data class Success(
        val problemTitle: String,
        val steps: List<RepairStepItem>,
        val currentStep: Int,
        val isCompleted: Boolean
    ) : RepairGuideUiState()
    data class Error(val message: String) : RepairGuideUiState()
}

@HiltViewModel
class RepairGuideViewModel @Inject constructor(
    private val repository: RepairRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val problemId: String = savedStateHandle["problemId"] ?: ""

    private val _uiState = MutableStateFlow<RepairGuideUiState>(RepairGuideUiState.Loading)
    val uiState: StateFlow<RepairGuideUiState> = _uiState.asStateFlow()

    private var problemDetail: ProblemDetail? = null

    init {
        loadGuide()
    }

    private fun loadGuide() {
        // In a real implementation, fetch from repository
        // For now, create sample data
        val sampleSteps = listOf(
            RepairStepItem(
                stepNumber = 1,
                title = "بررسی اولیه دستگاه",
                description = "ابتدا دستگاه را از نظر ظاهری بررسی کنید. آیا نشانه‌های خوردگی، ضربه یا نفوذ آب وجود دارد؟ شیشه پشت یا قاب را بررسی کنید. اگر دستگاه قبلاً باز شده، چسب‌های اصلی وجود دارند یا خیر.",
                warning = "قبل از هر کاری، باتری را از دستگاه جدا کنید یا حداقل کانکتور آن را باز کنید تا از کوتاهی احتمالی جلوگیری شود.",
                estimatedTime = 5
            ),
            RepairStepItem(
                stepNumber = 2,
                title = "تست با منبع تغذیه",
                description = "دستگاه را به منبع تغذیه DC وصل کنید. ولتاژ را روی 4.2V و جریان محدود را روی 1A تنظیم کنید. کلید Power را فشار دهید و جریان‌کشی را مشاهده کنید.

- اگر جریان صفر است: مشکل از مسیر تغذیه یا کلید Power است.
- اگر جریان لحظه‌ای دارد: CPU و RAM در حال تلاش برای بوت هستند.
- اگر جریان ثابت می‌کشد: احتمالاً کوتاهی در برد وجود دارد.",
                estimatedTime = 10
            ),
            RepairStepItem(
                stepNumber = 3,
                title = "بررسی ولتاژ باتری (VBAT)",
                description = "مولتی‌متر را روی حالت DC Voltage قرار دهید. پروب مشکی را به GND برد و پروب قرمز را به پد VBAT وصل کنید.

ولتاژ نرمال: 3.7V تا 4.4V

اگر ولتاژ کمتر از 3.5V است، باتری خالی یا خراب است. اگر لتاژ صفر است، مسیر VBAT قطع یا کوتاه است.",
                warning = "از اتصال پروب به پدهای ریز دقت کنید. لحیم کاری نادرست می‌تواند باعث آسیب به برد شود.",
                estimatedTime = 5
            ),
            RepairStepItem(
                stepNumber = 4,
                title = "بررسی کلید Power و مسیر آن",
                description = "کلید Power را از نظر فیزیکی بررسی کنید. با مولتی‌متر حالت Buzzer، دو سر کلید را در حالت فشار داده و رها شده تست کنید.

سپس مسیر کلید Power را از کلید تا PMIC ردگیری کنید. معمولاً کلید Power به یک پین از PMIC وصل است. مقاومت‌های Pull-up و خازن‌های تمیزکننده سیگنال را بررسی کنید.",
                estimatedTime = 15
            ),
            RepairStepItem(
                stepNumber = 5,
                title = "بررسی PMIC و کویل‌های تغذیه",
                description = "PMIC (Power Management IC) را شناسایی کنید. معمولاً بزرگترین IC در اطراف باتری است.

۱. ولتاژ ورودی PMIC (VPH_PWR) را بررسی کنید.
۲. ولتاژ خروجی‌های PMIC را اندازه‌گیری کنید:
   - VREG_BOB: 3.8V - 4.3V
   - S1-S13: 0.5V - 1.8V (بسته به نیاز CPU)
   - LDOها: 1.2V, 1.8V, 2.8V, 3.0V

اگر ورودی PMIC وجود دارد اما خروجی ندارد، PMIC خراب است.",
                warning = "PMICهای جدید Underfill دارند و تعویض آنها نیاز به مهارت بالا و میکروسکوپ دارد.",
                estimatedTime = 20
            ),
            RepairStepItem(
                stepNumber = 6,
                title = "تست حرارتی و شناسایی کوتاهی",
                description = "دستگاه را به منبع تغذیه وصل کنید و ولتاژ را به آرامی افزایش دهید. با دست یا دوربین حرارتی، نقاط داغ را شناسایی کنید.

نقاط داغ احتمالی:
- PMIC: خرابی PMIC یا کوتاهی خروجی آن
- CPU: خرابی CPU یا کوتاهی داخلی
- کویل‌ها: خرابی MOSFET یا دیود همسو
- خازن‌ها: کوتاهی سرامیکی

با تزریق ولتاژ 1V و محدودیت جریان 1A، IC داغ شده را شناسایی کنید.",
                warning = "تست حرارتی باید با احتیاط انجام شود. دمای بیش از 80 درجه می‌تواند برد را تخریب کند.",
                estimatedTime = 15
            ),
            RepairStepItem(
                stepNumber = 7,
                title = "تعویض قطعه مشکل‌دار",
                description = "پس از شناسایی قطعه خراب:

۱. برد را با هیتر از پیش گرم کنید (150-180 درجه)
۲. فلاکس مناسب بزنید
۳. با هیتر و قلع مکشی، IC قدیمی را بردارید
۴. پدها را تمیز کنید
۵. IC جدید را با alignment دقیق قرار دهید
۶. با هیتر لحیم کنید
۷. Underfill جدید بزنید (در صورت نیاز)

پس از تعویض، تست برق بگیرید.",
                warning = "تعویض CPU و RAM نیاز به Reballing دارد و باید با BGA Rework Station انجام شود.",
                estimatedTime = 30
            ),
            RepairStepItem(
                stepNumber = 8,
                title = "تست نهایی و تحویل",
                description = "پس از تعمیر:

۱. دستگاه را روشن کنید و تا بوت کامل صبر کنید
۲. تست LCD و تاچ
۳. تست دوربین، صدا، آنتن، WiFi
۴. تست شارژ
۵. دستگاه را 30 دقیقه در حالت Standby قرار دهید

اگر همه موارد OK بود، دستگاه را تحویل دهید و فاکتور صادر کنید.",
                estimatedTime = 10
            )
        )

        _uiState.value = RepairGuideUiState.Success(
            problemTitle = "گوشی روشن نمی‌شود",
            steps = sampleSteps,
            currentStep = 0,
            isCompleted = false
        )
    }

    fun nextStep() {
        val current = _uiState.value as? RepairGuideUiState.Success ?: return
        if (current.currentStep < current.steps.size - 1) {
            _uiState.value = current.copy(currentStep = current.currentStep + 1)
        } else {
            _uiState.value = current.copy(isCompleted = true)
        }
    }

    fun previousStep() {
        val current = _uiState.value as? RepairGuideUiState.Success ?: return
        if (current.currentStep > 0) {
            _uiState.value = current.copy(
                currentStep = current.currentStep - 1,
                isCompleted = false
            )
        }
    }

    fun goToStep(stepIndex: Int) {
        val current = _uiState.value as? RepairGuideUiState.Success ?: return
        if (stepIndex in current.steps.indices) {
            _uiState.value = current.copy(
                currentStep = stepIndex,
                isCompleted = stepIndex == current.steps.size - 1
            )
        }
    }
}
