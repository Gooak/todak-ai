import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableActionsBox(
    modifier: Modifier = Modifier,
    endActions: @Composable (() -> Unit),
    content: @Composable (() -> Unit)
) {
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    var actionWidth by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectHorizontalDragGestures(
                onHorizontalDrag = { change, dragAmount ->
                    val newOffsetX = offsetX.value + dragAmount
                    if (actionWidth != 0f) {
                        scope.launch {
                            offsetX.snapTo(newOffsetX.coerceIn(-actionWidth, 0f))
                        }
                    }
                    change.consume()
                },
                onDragEnd = {
                    if (actionWidth != 0f) {
                        scope.launch {
                            if (offsetX.value < -actionWidth / 2) {
                                offsetX.animateTo(-actionWidth)
                            } else {
                                offsetX.animateTo(0f)
                            }
                        }
                    }
                }
            )
        }
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier.onSizeChanged {
                    actionWidth = it.width.toFloat()
                }
            ) {
                endActions()
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        ) {
            content()
        }
    }
}