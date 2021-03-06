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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dandc87.fetch.data.Doggo
import com.dandc87.fetch.data.DoggoRepository

@Composable
fun DoggoProfile(
    doggo: Doggo,
    includeDetails: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = doggo.image),
            contentDescription = "Photo of ${doggo.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 0.8f)
        )
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = doggo.name,
                    maxLines = if (includeDetails) 2 else 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h5,
                )
                Text(
                    text = "${doggo.breed} - ${doggo.coloration} - ${doggo.ageText}",
                    maxLines = if (includeDetails) 2 else 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
        }
        if (includeDetails) {
            Text(
                text = doggo.bio,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp),
            )
        }
    }
}

@Composable
internal fun PreviewSurface(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.background, content = content)
    }
}

private val TEST_DOGGO = DoggoRepository.generateDoggo(1234)

@Preview
@Composable
fun Preview_DoggoProfile_as_card() {
    PreviewSurface {
        DoggoProfile(
            doggo = TEST_DOGGO,
            includeDetails = false,
            modifier = Modifier.padding(all = 16.dp),
        )
    }
}

@Preview
@Composable
fun Preview_DoggoProfile_as_detail() {
    PreviewSurface {
        DoggoProfile(
            doggo = TEST_DOGGO,
            includeDetails = true,
        )
    }
}
