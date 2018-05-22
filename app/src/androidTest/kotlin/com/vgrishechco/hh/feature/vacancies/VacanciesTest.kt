package com.vgrishechco.hh.feature.vacancies

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VacanciesTest {


    @JvmField
    @Rule
    val screenRule = ActivityTestRule(VacanciesActivity::class.java)

    @Test
    fun exampleTest() {
        val screen = VacanciesScreen()

        Thread.sleep(5000)
        screen {
            vacancies {
                firstChild<VacanciesScreen.VacancyItem> {
                    isVisible()
                    name {
                        hasAnyText()
                    }
                }
            }
        }
    }
}