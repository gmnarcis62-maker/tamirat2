package red.line.tamirkar.core.util

import android.content.Context
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class PdfGenerator @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun generateInvoice(
        invoiceNumber: String,
        customerName: String,
        deviceModel: String,
        problem: String,
        parts: List<Pair<String, Double>>,
        laborCost: Double,
        totalCost: Double,
        outputFile: File
    ): Boolean {
        return try {
            val writer = PdfWriter(outputFile)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)

            document.add(Paragraph("فاکتور تعمیرات موبایل").setTextAlignment(TextAlignment.CENTER).setFontSize(20f))
            document.add(Paragraph("ردلاین سافت البرز").setTextAlignment(TextAlignment.CENTER).setFontSize(12f))
            document.add(Paragraph(""))

            val infoTable = Table(floatArrayOf(150f, 250f))
            infoTable.addCell("شماره فاکتور:")
            infoTable.addCell(invoiceNumber)
            infoTable.addCell("نام مشتری:")
            infoTable.addCell(customerName)
            infoTable.addCell("مدل دستگاه:")
            infoTable.addCell(deviceModel)
            infoTable.addCell("مشکل:")
            infoTable.addCell(problem)
            document.add(infoTable)
            document.add(Paragraph(""))

            val partsTable = Table(floatArrayOf(50f, 200f, 150f))
            partsTable.addCell("ردیف")
            partsTable.addCell("نام قطعه")
            partsTable.addCell("قیمت (تومان)")
            parts.forEachIndexed { index, part ->
                partsTable.addCell((index + 1).toString())
                partsTable.addCell(part.first)
                partsTable.addCell(part.second.toString())
            }
            document.add(partsTable)
            document.add(Paragraph(""))

            document.add(Paragraph("هزینه کارگری: $laborCost تومان"))
            document.add(Paragraph("جمع کل: $totalCost تومان").setBold())

            document.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
