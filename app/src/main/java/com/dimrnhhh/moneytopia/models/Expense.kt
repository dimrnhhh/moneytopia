package com.dimrnhhh.moneytopia.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import java.time.LocalDateTime

class Expense(): RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var amount: Double = 0.0
    var category: String = ""
    private var _dateValue: String = LocalDateTime.now().toString()
    val date: LocalDateTime
        get() {
            return LocalDateTime.parse(_dateValue)
        }
    private var _recurrenceName: String = "None"
    val recurrence: Recurrence
        get() {
            return _recurrenceName.toRecurrence()
        }
    var note: String = ""

    constructor(
        amount: Double,
        category: String,
        date: LocalDateTime,
        recurrence: Recurrence,
        note: String
    ): this() {
        this.amount = amount
        this.category = category
        this._dateValue = date.toString()
        this._recurrenceName
        this.note = note
    }
}

class DayExpenses(
    val expenses: MutableList<Expense>,
    var total: Double
)

fun List<Expense>.groupedByDay(): Map<LocalDate, DayExpenses> {
    val dataMap: MutableMap<LocalDate, DayExpenses> = mutableMapOf()

    this.forEach {
        val date = it.date.toLocalDate()

        if(dataMap[date] == null) {
            dataMap[date] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[date]!!.expenses.add(it)
        dataMap[date]!!.total = dataMap[date]!!.total.plus(it.amount)
    }

    dataMap.values.forEach {
        it.expenses.sortBy { expense -> expense.date }
    }

    return dataMap.toSortedMap(compareByDescending { it })
}

fun List<Expense>.groupedByDayOfWeek(): Map<String, DayExpenses> {
    val dataMap: MutableMap<String, DayExpenses> = mutableMapOf()

    this.forEach {
        val dayOfWeek = it.date.toLocalDate().dayOfWeek

        if (dataMap[dayOfWeek.name] == null) {
            dataMap[dayOfWeek.name] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[dayOfWeek.name]!!.expenses.add(it)
        dataMap[dayOfWeek.name]!!.total = dataMap[dayOfWeek.name]!!.total.plus(it.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}

fun List<Expense>.groupedByDayOfMonth(): Map<Int, DayExpenses> {
    val dataMap: MutableMap<Int, DayExpenses> = mutableMapOf()

    this.forEach {
        val dayOfMonth = it.date.toLocalDate().dayOfMonth

        if (dataMap[dayOfMonth] == null) {
            dataMap[dayOfMonth] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[dayOfMonth]!!.expenses.add(it)
        dataMap[dayOfMonth]!!.total = dataMap[dayOfMonth]!!.total.plus(it.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}

fun List<Expense>.groupedByMonth(): Map<String, DayExpenses> {
    val dataMap: MutableMap<String, DayExpenses> = mutableMapOf()

    this.forEach {
        val month = it.date.toLocalDate().month

        if (dataMap[month.name] == null) {
            dataMap[month.name] = DayExpenses(
                expenses = mutableListOf(),
                total = 0.0
            )
        }

        dataMap[month.name]!!.expenses.add(it)
        dataMap[month.name]!!.total = dataMap[month.name]!!.total.plus(it.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}