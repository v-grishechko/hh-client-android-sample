package com.vgrishechco.hh.core.formatter

import android.content.Context
import android.text.Html
import android.text.Spanned
import com.vgrishechco.hh.R
import com.vgrishechco.hh.core.backend.entity.Salary
import com.vgrishechco.hh.utils.Locales
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

object VacancyFormatter {

    private val DECIMAL_FORMATTER = createDecimalFormatter()

    private fun createDecimalFormatter(): Format {
        val decimalFormatter = DecimalFormat("##.##")

        decimalFormatter.roundingMode = RoundingMode.DOWN
        decimalFormatter.isGroupingUsed = false

        val decimalSymbols = decimalFormatter.decimalFormatSymbols
        decimalSymbols.decimalSeparator = '.'

        decimalFormatter.decimalFormatSymbols = decimalSymbols

        return decimalFormatter
    }

    private fun formatNumber(number: Double): String {
        return DECIMAL_FORMATTER.format(number)
    }

    fun formatSalary(context: Context, salary: Salary?): String? {
        if (salary == null || (salary.from == null && salary.to == null)) {
            return context.getString(R.string.negotiable)
        }

        val salaryFormattedText = StringBuilder()

        if(salary.from != null && salary.to != null) {
            salaryFormattedText.apply {
                append(formatNumber(salary.from))
                append(" — ")
                append(formatNumber(salary.to))
            }
        } else if(salary.from != null && salary.to == null) {
            salaryFormattedText.apply {
                append("от ")
                append(salary.from)
            }
        } else if(salary.from == null && salary.to != null) {
            salaryFormattedText.apply {
                append("до ")
                append(salary.to)
            }
        }

        salaryFormattedText.apply {
            append(" ")
            append(salary.currency)
        }

        return salaryFormattedText.toString()
    }

    private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd", Locales.DEFAULT)

    fun formatDate(date: Date?): String? {
        if(date == null) {
            return null
        }

        return DATE_FORMATTER.format(date)
    }

    fun formateDescription(description: String?): Spanned? {
        if(description.isNullOrEmpty()) {
            return null
        }

        @Suppress("DEPRECATION")
        return Html.fromHtml(description)
    }
}