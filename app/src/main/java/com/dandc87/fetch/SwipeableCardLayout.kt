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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeableCardLayout(
    items: List<T>,
    modifier: Modifier = Modifier,
    selectedItem: MutableState<T?> = remember { mutableStateOf(null) },
    showingItemIndex: MutableState<Int> = remember { mutableStateOf(0) },
    onItemClick: (T) -> Unit = { selectedItem.value = it },
    content: @Composable (T) -> Unit
) {
    val transition = updateTransition(selectedItem)

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val item = items[showingItemIndex.value]

        val nextItem = items.getOrNull(showingItemIndex.value + 1)

        if (nextItem != null) {
            SwipeableCard(
                item = nextItem,
                shape = CutCornerShape(size = 16.dp),
                elevation = 1.dp,
                isSelectedItem = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateContentSize()
                    .padding(all = 32.dp),
                onItemClick = { }
            ) {
                content(it)
            }
        }

        val paddingDp by transition.animateDp {
            when (it.value) {
                item -> 0.dp
                else -> 16.dp
            }
        }
        val shapeDp by transition.animateDp {
            when (it.value) {
                item -> 0.dp
                else -> 16.dp
            }
        }
        val elevationDp by transition.animateDp {
            when (it.value) {
                item -> 0.dp
                else -> 4.dp
            }
        }

        val isSelectedItem = selectedItem.value == item

        SwipeableCard(
            item = item,
            shape = CutCornerShape(size = shapeDp),
            elevation = elevationDp,
            onItemClick = onItemClick,
            isSelectedItem = isSelectedItem,
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (isSelectedItem) {
                        it.height(this.maxHeight)
                    } else {
                        it.wrapContentHeight()
                    }
                }
                .animateContentSize()
                .padding(all = paddingDp),
        ) {
            content(it)
        }
    }
}

@Composable
private fun <T> BoxWithConstraintsScope.SwipeableCard(
    item: T,
    shape: Shape,
    elevation: Dp,
    isSelectedItem: Boolean,
    onItemClick: (T) -> Unit,
    modifier: Modifier,
    content: @Composable() (T) -> Unit,
) {
    Card(
        shape = shape,
        elevation = elevation,
        modifier = modifier,
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
