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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dandc87.fetch.data.Doggo
import com.dandc87.fetch.data.DoggoRepository
import com.dandc87.fetch.ui.theme.MyTheme
import kotlin.random.Random

@Composable
fun FetchApp(
    doggos: List<Doggo>,
    selectedDoggo: MutableState<Doggo?> = remember { mutableStateOf(null) }
) {
    val detailScrollState = rememberScrollState()
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = if (selectedDoggo.value != null) {
                    { ->
                        IconButton(
                            onClick = {
                                selectedDoggo.value = null
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                } else null,
            )
            when (val selected = selectedDoggo.value) {
                null -> LazyColumn {
                    items(doggos) { doggo ->
                        DoggoProfile(
                            doggo = doggo,
                            includeDetails = false,
                            shape = CutCornerShape(size = 16.dp),
                            elevation = 4.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp),
                            onClick = {
                                selectedDoggo.value = doggo
                            }
                        )
                    }
                }
                else -> {
                    DoggoProfile(
                        doggo = selected,
                        includeDetails = true,
                        shape = RectangleShape,
                        elevation = 0.dp,
                        modifier = Modifier.verticalScroll(detailScrollState),
                    )
                }
            }
        }
    }
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
