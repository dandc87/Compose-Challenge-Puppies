package com.dandc87.fetch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dandc87.fetch.data.Doggo
import com.dandc87.fetch.data.DoggoRepository

@Composable
fun DoggoProfile(
    doggo: Doggo,
    includeDetails: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: Dp = 1.dp,
) {
    Card(
        modifier = modifier,
        shape = shape,
        elevation = elevation,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Photo of ${doggo.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1f)
            )
            Surface(
                color = MaterialTheme.colors.primarySurface,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = doggo.name,
                        style = MaterialTheme.typography.h5,
                    )
                    Text(
                        text = "${doggo.breed} - ${doggo.coloration} - ${doggo.ageText}",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
            if (includeDetails) {
                Text(
                    text = doggo.bio,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp),
                )
            }
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
            shape = CutCornerShape(16.dp),
            elevation = 4.dp,
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
            shape = RectangleShape,
            elevation = 0.dp,
        )
    }
}
