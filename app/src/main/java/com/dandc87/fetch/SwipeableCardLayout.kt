/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dandc87.fetch

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.android.material.math.MathUtils.lerp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeableCardLayout(
    items: List<T>,
    modifier: Modifier = Modifier,
    baseElevation: Dp = 4.dp,
    basePadding: Dp = 16.dp,
    baseShapeCorner: Dp = 16.dp,
    expandCard: MutableState<Boolean> = remember { mutableStateOf(false) },
    showingItemIndex: MutableState<Int> = remember { mutableStateOf(0) },
    onItemClick: (T) -> Unit = { expandCard.value = true },
    content: @Composable (T) -> Unit
) {
    val item = items[showingItemIndex.value]
    val nextItem = items.getOrNull(showingItemIndex.value + 1)

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val swipeState = remember(item) { SwipeableState(initialValue = CardSwipeState.NONE) }
        DisposableEffect(swipeState.currentValue) {
            if (swipeState.currentValue != CardSwipeState.NONE) {
                showingItemIndex.value += 1
            }
            onDispose { }
        }

        if (nextItem != null) {
            val scale = lerp(0.9f, 1f, (swipeState.offset.value.absoluteValue / constraints.maxWidth))
            SwipeableCard(
                item = nextItem,
                shape = CutCornerShape(size = baseShapeCorner),
                elevation = baseElevation,
                padding = basePadding,
                isSelectedItem = false,
                swipeState = rememberSwipeableState(initialValue = CardSwipeState.NONE),
                onItemClick = { },
                scale = scale,
            ) {
                content(it)
            }
        } else {
            Text(
                text = "Last doggo!",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            )
        }

        val transition = updateTransition(expandCard)
        val paddingDp by transition.animateDp { if (it.value) 0.dp else basePadding }
        val shapeDp by transition.animateDp { if (it.value) 0.dp else baseShapeCorner }
        val elevationDp by transition.animateDp { if (it.value) 0.dp else baseElevation }

        SwipeableCard(
            item = item,
            shape = CutCornerShape(size = shapeDp),
            elevation = elevationDp,
            padding = paddingDp,
            isSelectedItem = expandCard.value,
            swipeState = swipeState,
            onItemClick = onItemClick,
            swipeable = nextItem != null,
        ) {
            content(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun <T> BoxWithConstraintsScope.SwipeableCard(
    item: T,
    shape: Shape,
    elevation: Dp,
    padding: Dp,
    isSelectedItem: Boolean,
    swipeState: SwipeableState<CardSwipeState>,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    swipeable: Boolean = true,
    scale: Float = 1f,
    content: @Composable() (T) -> Unit,
) {
    val boxMaxWidth = this.constraints.maxWidth.toFloat()
    val swipeAnchors: Map<Float, CardSwipeState> = mapOf(
        -boxMaxWidth to CardSwipeState.SWIPED_LEFT,
        0f to CardSwipeState.NONE,
        boxMaxWidth to CardSwipeState.SWIPED_RIGHT,
    )
    Card(
        shape = shape,
        elevation = elevation,
        modifier = modifier
            .fillMaxWidth()
            .let {
                if (isSelectedItem) {
                    it.height(this.maxHeight)
                } else {
                    it.wrapContentHeight()
                }
            }
            .animateContentSize()
            .scale(scale = scale)
            .swipeable(
                enabled = swipeable && !isSelectedItem,
                state = swipeState,
                anchors = swipeAnchors,
                orientation = Orientation.Horizontal,
            )
            .offset {
                IntOffset(swipeState.offset.value.roundToInt(), 0)
            }
            .padding(all = padding),
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(enabled = isSelectedItem, state = rememberScrollState())
                .clickable(enabled = !isSelectedItem) { onItemClick(item) },
        ) {
            content(item)
        }
    }
}

private enum class CardSwipeState {
    SWIPED_LEFT,
    NONE,
    SWIPED_RIGHT,
}
