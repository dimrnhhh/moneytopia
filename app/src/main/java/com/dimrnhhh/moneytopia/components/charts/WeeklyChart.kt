package com.dimrnhhh.moneytopia.components.charts

import android.graphics.Typeface
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.models.groupedByDayOfWeek
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import java.time.DayOfWeek
import com.patrykandpatrick.vico.core.chart.line.LineChart.LineSpec
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun WeeklyChart(
    expenses: List<Expense>,
) {
    val groupedExpenses = expenses.groupedByDayOfWeek()
    val chartEntryModel = entryModelOf(
        groupedExpenses[DayOfWeek.MONDAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[DayOfWeek.TUESDAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[DayOfWeek.WEDNESDAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[DayOfWeek.THURSDAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[DayOfWeek.FRIDAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[DayOfWeek.SATURDAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[DayOfWeek.SUNDAY.name]?.total?.toFloat()?: 0f
    )
    val daysOfWeek = listOf(
        stringResource(R.string.Monday),
        stringResource(R.string.Tuesday),
        stringResource(R.string.Wednesday),
        stringResource(R.string.Thursday),
        stringResource(R.string.Friday),
        stringResource(R.string.Saturday),
        stringResource(R.string.Sunday),
    )
    val bottomAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
            x, _ -> daysOfWeek[x.toInt() % daysOfWeek.size]
    }
    ProvideChartStyle(
        chartStyle = m3ChartStyle(),
    ) {
        Chart(
            modifier = Modifier
                .offset(y = (-20).dp),
            chart = lineChart(
                lines = listOf(
                    LineSpec(
                        lineColor = MaterialTheme.colorScheme.primary.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            brush = Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    MaterialTheme.colorScheme.primary.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                                )
                            )
                        )
                    )
                )
            ),
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