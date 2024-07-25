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
import com.dimrnhhh.moneytopia.models.groupedByMonth
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
import java.time.Month

@Composable
fun YearlyChart(
    expenses: List<Expense>
) {
    val groupedExpenses = expenses.groupedByMonth()
    val chartEntryModel = entryModelOf(
        groupedExpenses[Month.JANUARY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.FEBRUARY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.MARCH.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.APRIL.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.MAY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.JUNE.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.JULY.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.AUGUST.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.SEPTEMBER.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.OCTOBER.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.NOVEMBER.name]?.total?.toFloat()?: 0f,
        groupedExpenses[Month.DECEMBER.name]?.total?.toFloat()?: 0f,
    )
    val monthsOfYear = listOf(
        stringResource(R.string.January),
        stringResource(R.string.February),
        stringResource(R.string.March),
        stringResource(R.string.April),
        stringResource(R.string.May),
        stringResource(R.string.June),
        stringResource(R.string.July),
        stringResource(R.string.August),
        stringResource(R.string.September),
        stringResource(R.string.October),
        stringResource(R.string.November),
        stringResource(R.string.December)
    )
    val bottomAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
            x, _ -> monthsOfYear[x.toInt() % monthsOfYear.size]
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