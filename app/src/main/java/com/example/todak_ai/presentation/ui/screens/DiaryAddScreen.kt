package com.example.todak_ai.presentation.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todak_ai.domain.enum.MoodType
import com.example.todak_ai.domain.enum.WeatherType
import com.example.todak_ai.presentation.ui.components.LinedDiaryTextField
import com.example.todak_ai.presentation.viewmodel.DiaryAddViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryAddScreen(navController: NavHostController) {
    val diaryViewModel: DiaryAddViewModel = hiltViewModel()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val diaryDate  = LocalDate.now()
    val today = diaryDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
    var selectedWeather by remember { mutableStateOf(WeatherType.SUNNY) }
    var selectedMood by remember { mutableStateOf(MoodType.HAPPY) } // 기분 상태 추가

    val titleText : String by diaryViewModel.titleText.collectAsState()
    val contentText : String by diaryViewModel.contentText.collectAsState()

    val isLoadingAi: Boolean by diaryViewModel.isLoadingAi.collectAsState()
    val geminiResponse: String by diaryViewModel.geminiResponse.collectAsState()

    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(geminiResponse) {
        if (geminiResponse.isNotEmpty()) {
            geminiResponse.forEachIndexed { index, _ ->
                displayedText = geminiResponse.substring(0, index + 1)
                delay(50L)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (geminiResponse.isNotEmpty() && contentText.isNotEmpty())
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            diaryViewModel.insertDiary(diaryDate, geminiResponse, selectedWeather, selectedMood)
                            navController.popBackStack()
                        }
                    }
                ) {
                    Icon(Icons.Filled.Check, contentDescription = "추가")
                }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.ime),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding) // Scaffold가 제공하는 기본 패딩 사용
                .padding(horizontal = 16.dp), // 좌우 여백 추가
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기"
                    )
                }
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Text(today, style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = titleText,
                onValueChange = { diaryViewModel.onTitleChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "제목을 입력하세요",
                        style = MaterialTheme.typography.bodyLarge, // 플레이스홀더 스타일
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(5.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                        alpha = 0.3f
                    )
                )
            ) {
                Column(Modifier.padding(vertical = 8.dp)) {
                    SelectionRow(title = "오늘의 날씨") {
                        WeatherType.entries.forEach { weather ->
                            IconButton(onClick = { selectedWeather = weather }) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp) // 아이콘보다 약간 크게 만듭니다. (아이콘 36dp + 패딩)
                                        .alpha(if (selectedWeather == weather) 1f else 0.3f) // 선택 여부에 따라 투명도 조절
                                        .background(
                                            color = if (selectedWeather == weather) Color.White else Color.Transparent, // 선택 시 흰색 배경
                                            shape = MaterialTheme.shapes.extraLarge // 원형 모양 (circleShape도 가능)
                                        ),
                                    contentAlignment = Alignment.Center // 아이콘을 중앙에 배치
                                ) {
                                    Image(
                                        painter = painterResource(id = weather.iconRes),
                                        contentDescription = weather.name,
                                        modifier = Modifier.size(36.dp) // 아이콘 크기는 유지
                                    )
                                }
                            }
                        }
                    }
                    VerticalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    SelectionRow(title = "오늘의 기분") {
                        MoodType.entries.forEach { mood ->
                            IconButton(onClick = { selectedMood = mood }) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .alpha(if(selectedMood == mood) 1f else 0.3f)
                                        .background(
                                            color = if(selectedMood == mood) Color.White else Color.Transparent,
                                            shape = MaterialTheme.shapes.extraLarge
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = mood.iconRes),
                                        contentDescription = mood.name,
                                        modifier = Modifier.size(36.dp) // 아이콘 크기는 유지
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LinedDiaryTextField(
                value = contentText,
                onValueChange = { diaryViewModel.onContentChange(it) },
            )

            Spacer(modifier = Modifier.height(25.dp))

            AnimatedVisibility(visible = contentText.isNotBlank() && titleText.isNotBlank() && !isLoadingAi && geminiResponse.isEmpty()) {
                // 버튼 스타일 변경
                FilledTonalButton(
                    onClick = {
                        coroutineScope.launch {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            diaryViewModel.requestAiAnswer(selectedWeather, selectedMood)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("🤖 AI에게 공감받기", style = MaterialTheme.typography.labelLarge)
                }
            }

            if (isLoadingAi) {
                Row(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            AnimatedVisibility(
                visible = geminiResponse.isNotBlank(),
                enter = fadeIn() + slideInVertically { it / 4 },
                exit = fadeOut() + slideOutVertically { it / 4 }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "AI의 따뜻한 공감",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = displayedText,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

// 날씨/기분 선택 UI를 재사용하기 위한 작은 컴포저블
@Composable
fun SelectionRow(
    title: String,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            content()
        }
    }
}
