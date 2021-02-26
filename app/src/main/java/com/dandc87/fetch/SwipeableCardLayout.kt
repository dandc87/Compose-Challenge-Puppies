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

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> SwipeableCardLayout(
    items: List<T>,
    modifier: Modifier = Modifier,
    selectedItem: MutableState<T?> = remember { mutableStateOf(null) },
    onItemClick: (T) -> Unit = { selectedItem.value = it },
    content: @Composable (T, Boolean) -> Unit
) {
    val transition = updateTransition(selectedItem)
    LazyColumn(modifier = modifier) {
        items(items) { item ->
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

            Card(
                shape = CutCornerShape(size = shapeDp),
                elevation = elevationDp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = paddingDp)
            ) {
                Box(
                    modifier = Modifier.clickable(enabled = !isSelectedItem) { onItemClick(item) },
                ) {
                    content(item, isSelectedItem)
                }
            }
        }
    }
}
