package com.dimrnhhh.moneytopia.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.models.Recurrence
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun LocalDate.formatDay(): String{
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return when {
        this.isEqual(today) -> stringResource(R.string.today)
        this.isEqual(yesterday) -> stringResource(R.string.yesterday)
        this.year != today.year -> this.format(DateTimeFormatter.ofPattern("ddd, dd MMM yyyy"))
        else ->this.format(DateTimeFormatter.ofPattern("E, dd, MMM"))
    }
}

fun LocalDateTime.formatDayForRange(): String {
    val today = LocalDateTime.now()
    val yesterday = today.minusDays(1)

    return when {
        this.year != today.year -> this.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        else -> this.format(DateTimeFormatter.ofPattern("dd MMM"))
    }
}

data class DateRangeData(
    val start: LocalDate,
    val end: LocalDate,
    val daysInRange: Int
)

fun calculateDateRange(recurrence: Recurrence, page: Int): DateRangeData {
    val today = LocalDate.now()
    lateinit var start: LocalDate
    lateinit var end: LocalDate
    var daysInRange = 7

    when (recurrence) {
        Recurrence.Daily -> {
            start = LocalDate.now().minusDays(page.toLong())
            end = start
        }

        Recurrence.Weekly -> {
            start =
                LocalDate.now().minusDays(today.dayOfWeek.value.toLong() - 1)
                    .minusDays((page * 7).toLong())
            end = start.plusDays(6)
            daysInRange = 7
        }

        Recurrence.Monthly -> {
            start =
                LocalDate.of(today.year, today.month, 1)
                    .minusMonths(page.toLong())
            val numberOfDays =
                YearMonth.of(start.year, start.month).lengthOfMonth()
            end = start.plusDays(numberOfDays.toLong())
            daysInRange = numberOfDays
        }

        Recurrence.Yearly -> {
            start = LocalDate.of(today.year, 1, 1)
            end = LocalDate.of(today.year, 12, 31)
            daysInRange = 365
        }

        else -> Unit
    }

    return DateRangeData(
        start = start,
        end = end,
        daysInRange = daysInRange
    )
}

