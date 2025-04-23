package com.hfut.mihealth.common.recordDetail.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfut.mihealth.R
import com.hfut.mihealth.common.recordDetail.viewmodel.RecordViewModel
import com.hfut.mihealth.ui.theme.Green
import com.hfut.mihealth.ui.theme.ThemeWhite
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordDate(viewModel: RecordViewModel) {

    val currentMonth by viewModel.currentMonth.collectAsState()
    // 明确声明依赖于 currentMonth
    val startMonth = remember(currentMonth) { currentMonth.minusMonths(100) }
    val endMonth = remember(currentMonth) { currentMonth.plusMonths(100) }

    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        val selectedDay by viewModel.selectedDay.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        SimpleCalendarTitle(
            modifier = Modifier.padding(horizontal = 8.dp),
            currentMonth = state.firstVisibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        DaysOfWeekTitle(daysOfWeek = daysOfWeek)
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalCalendar(
            calendarScrollPaged = true,
            monthBody = { _, content ->
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .border(
                            color = Color.Black,
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = ThemeWhite
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .border(
                                color = Color.Black,
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        content()
                    }
                }
            },
            state = state,
            dayContent = { day ->
                Day(day, isSelected = day == selectedDay) {
                    viewModel.updateSelectedDay(day)
                    viewModel.updateDate(day.date.toString())
                }
            },
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .testTag("MonthDay")
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Green else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> if (isSelected) ThemeWhite else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> colorResource(R.color.purple_200)
        }
        if (day.position == DayPosition.MonthDate) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = textColor,
                fontSize = 14.sp,
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(
                    java.time.format.TextStyle.SHORT,
                    java.util.Locale.getDefault()
                ),
            )
        }
    }
}