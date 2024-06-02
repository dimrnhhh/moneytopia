package com.dimrnhhh.moneytopia.models

sealed class Recurrence(val name: String, val target: String) {
    data object None: Recurrence("None", "None")
    data object Daily: Recurrence("Daily", "Daily")
    data object Weekly: Recurrence("Weekly", "Weekly")
    data object Monthly: Recurrence("Monthly", "Monthly")
    data object Yearly: Recurrence("Yearly", "Yearly")
}

fun String.toRecurrence(): Recurrence {
    return when (this) {
        "None" -> Recurrence.None
        "Daily" -> Recurrence.Daily
        "Weekly" -> Recurrence.Weekly
        "Monthly" -> Recurrence.Monthly
        "Yearly" -> Recurrence.Yearly
        else -> Recurrence.None
    }
}