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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dandc87.fetch.data.Doggo
import com.dandc87.fetch.data.DoggoRepository
import com.dandc87.fetch.ui.theme.MyTheme
import kotlin.random.Random

@Composable
fun FetchApp(
    doggos: List<Doggo>,
    selectedDoggo: MutableState<Doggo?> = remember { mutableStateOf(null) }
) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            FetchTopAppBar(
                hasSelection = selectedDoggo.value != null,
                onBackClick = { selectedDoggo.value = null },
            )
            SwipeableCardLayout(
                items = doggos,
                selectedItem = selectedDoggo,
                modifier = Modifier.fillMaxSize(),
            ) {
                DoggoProfile(
                    doggo = it,
                    includeDetails = selectedDoggo.value == it,
                )
            }
        }
    }
}

@Composable
private fun FetchTopAppBar(
    hasSelection: Boolean = false,
    onBackClick: () -> Unit,
) {
    val backIcon: @Composable () -> Unit = {
    }
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (hasSelection) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            } else {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Pets, null)
                }
            }
        }
    )
}

private val TEST_DOGGOS = DoggoRepository.generateDoggos(Random(1234))

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        FetchApp(doggos = TEST_DOGGOS)
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        FetchApp(doggos = TEST_DOGGOS)
    }
}
