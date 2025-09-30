import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.today_diary_ai.presentation.ui.components.CircularChart
import com.example.today_diary_ai.presentation.ui.components.EmptyMessage
import com.example.today_diary_ai.presentation.viewmodel.StatisticsViewModel

@Composable
fun StatisticsScreen() {

    val viewModel: StatisticsViewModel = hiltViewModel()

    val moodData by viewModel.moodData.collectAsStateWithLifecycle()
    val moodColors by viewModel.moodColors.collectAsStateWithLifecycle()
    val moodLegend by viewModel.moodLegend.collectAsStateWithLifecycle()

    val weatherData by viewModel.weatherData.collectAsStateWithLifecycle()
    val weatherColors by viewModel.weatherColors.collectAsStateWithLifecycle()
    val weatherLegend by viewModel.weatherLegend.collectAsStateWithLifecycle()

    val totalDiaryCount by viewModel.totalDiaryCount.collectAsStateWithLifecycle()

    Scaffold {
        innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (totalDiaryCount == 0) {
                item {
                    EmptyMessage(modifier = Modifier.fillParentMaxSize())
                }
            } else {
                item {
                    ChartCard(
                        title = "나의 기분 통계",
                        colors = moodColors,
                        data = moodData,
                        legendItems = moodLegend
                    )
                }
                item {
                    ChartCard(
                        title = "경험한 날씨 통계",
                        colors = weatherColors,
                        data = weatherData,
                        legendItems = weatherLegend
                    )
                }
            }
        }
    }
}

// 차트와 범례를 보여주는 카드
@Composable
private fun ChartCard(
    title: String,
    colors: List<Color>,
    data: List<Float>,
    legendItems: List<Triple<Color, String, Int>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularChart(
                    modifier = Modifier.size(180.dp),
                    colors = colors,
                    data = data,
                    graphHeight = 180
                )
                ChartLegendColumn(items = legendItems)
            }
        }
    }
}

// 범례 UI
@Composable
private fun ChartLegendColumn(items: List<Triple<Color, String, Int>>) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        items.forEach { item -> // 여기서 item의 타입은 Triple<Color, String, Int>
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(item.first, CircleShape) // item.color -> item.first
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${item.second} (${item.third}개)", // item.text -> item.second, item.count -> item.third
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}