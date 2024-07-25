package com.dimrnhhh.moneytopia.components.charts

import android.graphics.Typeface
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.models.groupedByDayOfMonth
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MonthlyChart(
    expenses: List<Expense>,
    month: LocalDate
) {
    val groupedExpenses = expenses.groupedByDayOfMonth()
    val numberOfDays = YearMonth.of(month.year, month.month).lengthOfMonth()+1
    fun getMonthlyEntries() = List(numberOfDays) {
        entryOf(it, groupedExpenses[it]?.total?.toFloat()?: 0f)
    }
    val daysOfWeek: MutableList<String> = mutableListOf()
        for(i in 0.. numberOfDays) {
        daysOfWeek.add(i.toString())
    }
    val chartEntryModel = entryModelOf(getMonthlyEntries().subList(1, numberOfDays))
    val bottomAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
            x, _ -> daysOfWeek[x.toInt() % daysOfWeek.size]
    }
    ProvideChartStyle(
        chartStyle = m3ChartStyle()
    ) {
        Chart(
            modifier = Modifier
                .offset(y = (-20).dp),
            chart = lineChart(
                lines = listOf(
                    LineChart.LineSpec(
                        lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            brush = Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    MaterialTheme.colorScheme.primary.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                                )
                            )
                        )
                    )
                ),
            ),
            /*chartModelProducer = chartEntryModelProducer,*/
            model = chartEntryModel,
            startAxis = rememberStartAxis(
                label = textComponent{
                    color = MaterialTheme.colorScheme.onBackground.toArgb()
                },
                itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = 5)
            ),
            bottomAxis = rememberBottomAxis(
                valueFormatter = bottomAxisValueFormatter,
                label = textComponent{
                    color = MaterialTheme.colorScheme.onBackground.toArgb()
                    typeface = Typeface.SANS_SERIF
                }
            ),
            isZoomEnabled = false,
            marker = rememberMarker()
        )
    }
}