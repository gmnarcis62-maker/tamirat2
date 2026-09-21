package red.line.tamirkar.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import red.line.tamirkar.MainActivity
import red.line.tamirkar.R

class RepairWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val dataProvider = WidgetDataProvider(
            context,
            // Repository will be injected in real implementation
            throw IllegalStateException("Injected via Hilt in production")
        )

        // For widget preview/static, use default state
        val state = RepairWidgetState(
            todayRepairs = 0,
            pendingRepairs = 0,
            completedRepairs = 0,
            totalRevenue = 0.0
        )

        provideContent {
            RepairWidgetContent(state = state)
        }
    }

    companion object {
        suspend fun updateAll(context: Context) {
            val manager = GlanceAppWidgetManager(context)
            val widget = RepairWidget()
            val ids = manager.getGlanceIds(RepairWidget::class.java)
            ids.forEach { widget.update(context, it) }
        }
    }
}

@Composable
fun RepairWidgetContent(state: RepairWidgetState) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(R.color.widget_background))
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Header with icon and title
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                provider = ImageProvider(R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                modifier = GlanceModifier.size(36.dp)
            )
            Spacer(modifier = GlanceModifier.width(10.dp))
            Text(
                text = "دستیار تعمیرکار",
                style = TextStyle(
                    color = ColorProvider(R.color.widget_on_background),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            )
        }

        Spacer(modifier = GlanceModifier.height(14.dp))

        // Stats Row
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WidgetStatBox(
                value = state.todayRepairs.toString(),
                label = "امروز",
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            WidgetStatBox(
                value = state.pendingRepairs.toString(),
                label = "در انتظار",
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            WidgetStatBox(
                value = state.completedRepairs.toString(),
                label = "تکمیل",
                modifier = GlanceModifier.defaultWeight()
            )
        }

        Spacer(modifier = GlanceModifier.height(12.dp))

        // Quick Actions Row
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WidgetActionButton(
                label = "تعمیر",
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(6.dp))
            WidgetActionButton(
                label = "اسکنر",
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(6.dp))
            WidgetActionButton(
                label = "مشتری",
                modifier = GlanceModifier.defaultWeight()
            )
            Spacer(modifier = GlanceModifier.width(6.dp))
            WidgetActionButton(
                label = "فاکتور",
                modifier = GlanceModifier.defaultWeight()
            )
        }

        Spacer(modifier = GlanceModifier.height(10.dp))

        // Footer brand
        Text(
            text = "ردلاین سافت البرز",
            style = TextStyle(
                color = ColorProvider(R.color.widget_on_background_variant),
                textAlign = TextAlign.Center
            ),
            modifier = GlanceModifier.fillMaxWidth()
        )
    }
}

@Composable
fun WidgetStatBox(value: String, label: String, modifier: GlanceModifier) {
    Column(
        modifier = modifier
            .background(ColorProvider(R.color.widget_surface))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = TextStyle(
                color = ColorProvider(R.color.widget_primary),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = GlanceModifier.height(2.dp))
        Text(
            text = label,
            style = TextStyle(
                color = ColorProvider(R.color.widget_on_background_variant),
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun WidgetActionButton(label: String, modifier: GlanceModifier) {
    Text(
        text = label,
        style = TextStyle(
            color = ColorProvider(R.color.widget_on_primary),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        ),
        modifier = modifier
            .background(ColorProvider(R.color.widget_primary))
            .padding(vertical = 10.dp)
            .clickable(actionStartActivity(MainActivity::class.java))
    )
}
