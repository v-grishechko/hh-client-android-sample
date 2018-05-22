package com.vgrishechco.hh.feature.vacancies

import android.view.View
import com.agoda.kakao.KRecyclerItem
import com.agoda.kakao.KRecyclerView
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen
import com.vgrishechco.hh.R
import org.hamcrest.Matcher

class VacanciesScreen : Screen<VacanciesScreen>() {

    val vacancies = KRecyclerView(
            builder = { withId(R.id.vacancies) },
            itemTypeBuilder = { itemType(::VacancyItem) }
    )

    class VacancyItem(parent: Matcher<View>) : KRecyclerItem<VacancyItem>(parent) {
        val name: KTextView = KTextView(parent) { withId(R.id.name) }
        val price: KTextView = KTextView(parent) { withId(R.id.price) }
    }
}