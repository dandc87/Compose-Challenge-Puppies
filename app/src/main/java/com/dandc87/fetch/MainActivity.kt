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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.dandc87.fetch.data.DoggoRepository
import com.dandc87.fetch.ui.theme.MyTheme
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val expandProfile = mutableStateOf(false)
    private val showingDoggoIndex = mutableStateOf(0)
    private val doggos = mutableStateOf(DoggoRepository.generateDoggos(Random.nextInt()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                FetchApp(
                    doggos = doggos.value,
                    expandProfile = expandProfile,
                    showingDoggoIndex = showingDoggoIndex,
                    onLoadMoreClick = { loadMoreDoggos() },
                )
            }
        }
    }

    private fun loadMoreDoggos() {
        val doggosToKeep = doggos.value.subList(showingDoggoIndex.value, doggos.value.size)
        val newDoggos = DoggoRepository.generateDoggos(Random.nextInt())
        doggos.value = doggosToKeep + newDoggos
        showingDoggoIndex.value = 0
    }

    override fun onBackPressed() {
        if (!expandProfile.value) {
            super.onBackPressed()
        } else {
            expandProfile.value = false
        }
    }
}
