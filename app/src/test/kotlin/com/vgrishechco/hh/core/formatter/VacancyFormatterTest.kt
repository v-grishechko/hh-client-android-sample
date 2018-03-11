package com.vgrishechco.hh.core.formatter

import android.content.Context
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.vgrishechco.hh.core.backend.entity.Salary
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class VacancyFormatterTest {

    @Test
    fun salaryFormatTest() {
        val context = mockContex()
        var salary = stubSalary(from = null, to = null)
        var text = VacancyFormatter.formatSalary(context, salary)

        assertThat(text).isNullOrEmpty()

        salary = stubSalary(from = 1000.0)
        text = VacancyFormatter.formatSalary(mockContex(), salary)
        assertThat(text).isEqualTo("от 1000 BYN")

        salary = stubSalary(to = 1000.0)
        text = VacancyFormatter.formatSalary(context, salary)
        assertThat(text).isEqualTo(" до 1000 BYN")

        salary = stubSalary(from = 1000.0, to = 1000.0)
        text = VacancyFormatter.formatSalary(context, salary)
        assertThat(text).isEqualTo("1000 — 1000 BYN")
    }

    fun stubSalary(currency: String = "BYN", from: Double? = null, to: Double? = null) =
            Salary(to, from, currency)

    fun mockContex(): Context {
        val context: Context = mock()
        whenever(context.getString(any())).thenReturn("Договорная")
        return context
    }

}