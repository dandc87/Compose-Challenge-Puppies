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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dandc87.fetch.data.Doggo
import com.dandc87.fetch.data.DoggoRepository
import com.dandc87.fetch.ui.theme.MyTheme
import kotlinx.coroutines.launch

@Composable
fun FetchApp(
    doggos: List<Doggo>,
    expandProfile: MutableState<Boolean> = remember { mutableStateOf(false) },
    showingDoggoIndex: MutableState<Int> = remember { mutableStateOf(0) },
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onLoadMoreClick: () -> Unit = {},
) {
    val transition = updateTransition(expandProfile)
    val topBarElevation = transition.animateDp {
        if (expandProfile.value) AppBarDefaults.TopAppBarElevation else 0.dp
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(showingDoggoIndex.value, expandProfile.value) {
        if (!expandProfile.value && showingDoggoIndex.value == doggos.lastIndex) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = "Last Doggo!",
                actionLabel = "Load More",
                duration = SnackbarDuration.Indefinite,
            )
            if (result == SnackbarResult.ActionPerformed) {
                onLoadMoreClick()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            FetchTopAppBar(
                elevation = topBarElevation.value,
                hasSelection = expandProfile.value,
                onBackClick = { expandProfile.value = false },
            )
        },
        content = {
            SwipeableCardLayout(
                items = doggos,
                expandCard = expandProfile,
                showingItemIndex = showingDoggoIndex,
                transition = transition,
                modifier = Modifier.fillMaxSize(),
                onSwipeRight = { doggo ->
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Application sent. It's up to ${doggo.name} now!",
                        )
                    }
                },
            ) { doggo ->
                DoggoProfile(
                    doggo = doggo,
                    includeDetails = expandProfile.value,
                )
            }
        }
    )
}

@Composable
private fun FetchTopAppBar(
    elevation: Dp = 0.dp,
    hasSelection: Boolean = false,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.zIndex(1f),
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.background,
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (hasSelection) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            } else {
                IconButton(onClick = {}) {
                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colors.primary,
                    ) {
                        Icon(Icons.Default.Pets, null)
                    }
                }
            }
        }
    )
}

private val TEST_DOGGOS = DoggoRepository.generateDoggos()

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

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview_Expanded() {
    MyTheme {
        FetchApp(
            doggos = TEST_DOGGOS,
            expandProfile = remember { mutableStateOf(true) },
        )
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview_Expanded() {
    MyTheme(darkTheme = true) {
        FetchApp(
            doggos = TEST_DOGGOS,
            expandProfile = remember { mutableStateOf(true) },
        )
    }
}
