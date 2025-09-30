package com.example.todak_ai.presentation.ui.screens

import SwipeableActionsBox
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.todak_ai.domain.model.DiaryModel
import com.example.todak_ai.presentation.viewmodel.DiaryListViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.todak_ai.presentation.ui.components.EmptyMessage
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryListScreen(navController: NavController) {

    val diaryViewModel: DiaryListViewModel = hiltViewModel()

    val diaryList by diaryViewModel.diaryList.collectAsState() // StateFlow<List<DiaryModel>>


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("DiaryAddScreen") },
                containerColor = MaterialTheme.colorScheme.primary, // FAB 색상 지정
                contentColor = MaterialTheme.colorScheme.onPrimary // FAB 아이콘/텍스트 색상 지정
            ) {
                Icon(Icons.Filled.Add, contentDescription = "일기 추가")
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (diaryList.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {
            if (diaryList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(bottom = 80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmptyMessage(modifier = Modifier.fillParentMaxSize())
                    }
                }
            } else {
                items(diaryList.size) { index ->
                    SwipeableActionsBox(endActions = {
                        Button(
                            onClick = { diaryViewModel.deleteDiary(diaryList[index]) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            ),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            Column {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "삭제",
                                    tint = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Text(
                                    text = "삭제",
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    style = MaterialTheme.typography.labelSmall
                                )

                            }
                        }
                    }, content = {
                        ExpansionTail(
                            diary = diaryList[index],
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    })
                    Spacer(modifier = Modifier.height(8.dp)) // 아이템 간 간격
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpansionTail(diary: DiaryModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    diary.isExpanded.value = !diary.isExpanded.value
                }
                .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = diary.diaryDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), // 날짜 형식 변경
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = diary.diaryTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold), // 제목 폰트 굵기
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = diary.diaryWeather.iconRes),
                        contentDescription = diary.diaryWeather.name,
                        modifier = Modifier
                            .size(28.dp)
                            .alpha(0.8f)
                            .background(Color.White, shape = MaterialTheme.shapes.extraLarge)
                    )
                    Image( // MoodType도 Image로 변경
                        painter = painterResource(id = diary.diaryMood.iconRes),
                        contentDescription = diary.diaryMood.description,
                        modifier = Modifier
                            .size(28.dp)
                            .alpha(0.8f)
                            .background(Color.White, shape = MaterialTheme.shapes.extraLarge)
                    )
                    Icon(
                        imageVector = if (diary.isExpanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (diary.isExpanded.value) "접기" else "펼치기",
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // --- 확장 영역 (일기 내용 및 AI 답변) ---
            AnimatedVisibility(
                visible = diary.isExpanded.value, // .value 사용
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
            ) {
                Column(modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp)) {
                    // 일기 내용
                    Text(
                        text = diary.diaryContent,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    // AI 답변이 있다면 Divider와 함께 표시
                    if (diary.diaryAiContent.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                        Text(
                            text = "AI의 따뜻한 공감",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = diary.diaryAiContent,
                            style = MaterialTheme.typography.bodySmall, // AI 답변은 본문보다 작은 폰트
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}