package com.example.todak_ai.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CircularChart(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    data: List<Float>,
    graphHeight: Int
) {
    val total = data.sum() // 데이터 리스트에 있는 값의 총합 구하기
    val angles = data.map { it / total * 360f } // 데이터 값의 비율을 구하고, 360도를 기준으로 한 각도로 변환하여 리스트로 저장하기
    // Canvas를 사용하여 그래프를 그리고 `graphHeight.dp`를 픽셀 단위로 변환하여 그래프의 높이를 설정
    Canvas(modifier = modifier.height(graphHeight.dp)) {
        /*
         * 그래프의 선 두께를 지정
         * `strokeWidth = 4f`로 설정하면 선이 4 픽셀 두꺼워진다.
         * 값이 커질수록 더 굵은 선이 그려지며, 작아질수록 더 얇은 선이 그려진다.
         */
        val strokeWidth = graphHeight.dp.toPx() / 4
        /*
         * 원형 그래프의 반지름을 나타내는 값으로 그래프의 크기와 위치를 결정한다.
         * 원의 중심에서 외곽선까지의 거리를 `radius`로 설정한다.
         * `radius`는 그래프 높이에서 선 두께를 빼고 2로 나눈 값으로 설정한다.
         * `radius = 50f`으로 설정하면 원의 중심에서부터 50 픽셀 떨어진 위치에 그려진다.
         */
        val radius = (graphHeight.dp.toPx() - strokeWidth) / 2
        // 그래프의 중심 좌표
        val centerX = size.width / 2f
        val centerY = radius + strokeWidth / 2

        drawGraph(angles, colors, radius, strokeWidth, centerX, centerY)
    }
}

private fun DrawScope.drawGraph(
    angles: List<Float>, // 각 데이터 항목의 중심각을 나타내며 각도는 360도로 정규화되어 있다.
    colors: List<Color>, // 각 데이터 항목에 해당하는 색상을 지정
    radius: Float, // 원형 그래프의 반지름을 나타낸다.
    strokeWidth: Float, // 그래프의 선 두께를 지정
    centerX: Float, // 그래프의 중심 좌표
    centerY: Float // 그래프의 중심 좌표
) {
    var startAngle = -90f // 원형 그래프는 12시 방향을 0도로 시작하여 반시계 방향으로 그려지므로 `-90`도를 시작 각도로 설정한다.

    // 리스트를 순회하면서 각 데이터 항목에 대한 원호를 그린다.
    angles.forEachIndexed { index, angle -> // 현재 데이터 항목의 인덱스, 현재 데이터 항목의 중심각
        val color : Color = colors[index]

        drawArc(
            color = color, // 그래프 부분의 색상을 지정
            startAngle = startAngle, // 원호의 시작 각도를 설정
            sweepAngle = angle, // 원호의 중심각을 지정
            useCenter = false,
            style = Stroke(width = strokeWidth), // 선의 두께를 설정한다.
            topLeft = Offset(centerX - radius, centerY - radius), // 원호를 그릴 사각형의 왼쪽 상단 모서리의 위치
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2) // 원호를 그릴 사각형의 크기를 설정
        )

        // 각 데이터 항목의 그리기가 완료된 후에 `startAngle`을 현재 `angle` 만큼 증가시켜 다음 데이터 항목의 시작 각도를 설정한다.
        startAngle += angle
    }
}
