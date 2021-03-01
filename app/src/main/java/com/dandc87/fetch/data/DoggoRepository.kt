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
package com.dandc87.fetch.data

import com.dandc87.fetch.R
import kotlin.math.absoluteValue

object DoggoRepository {
    private const val MAX_AGE: Int = 365 * 3

    private val images = listOf(
        R.drawable.puppy_0,
        R.drawable.puppy_1,
        R.drawable.puppy_2,
        R.drawable.puppy_3,
        R.drawable.puppy_4,
        R.drawable.puppy_5,
        R.drawable.puppy_6,
        R.drawable.puppy_7,
        R.drawable.puppy_8,
        R.drawable.puppy_9,
    )

    private val names = listOf(
        "Wishbone",
        "Fido",
        "Jack",
        "Lassie",
        "Magpie",
        "Fluffy",
        "Bella",
        "Cooper",
        "Max",
        "Daisy",
        "Lola",
        "Buddy",
        "Duke",
        "Maggie",
        "Oliver",
        "Toby",
        "Scout",
        "Gus",
        "Izzy",
        "Abby",
        "Athena",
        "Professor Albus Percival Wulfric Brian Dumbledore",
    )

    private val breeds = listOf(
        "Mixed",
        "German Shepard",
        "Beagle",
        "Huskie",
        "Bulldog",
        "Poodle",
        "Terrier",
        "Rottweiler",
        "Dachhund",
        "Corgi",
        "Chihuahua",
        "Great Dane",
        "Labrador Retriever",
        "Golden Retriever",
        "Doberman",
        "Pomeranian",
        "Border Collie",
        "St. Bernard",
        "Dalmatian",
    )

    private val colorations = listOf(
        "Brown",
        "Red",
        "Black",
        "White",
        "Gold",
        "Yellow",
        "Cream",
        "Blue",
        "Gray",
        "Spotted",
        "Albino",
        "Merle",
        "Tuxedo",
        "Tricolor",
        "Saddle",
    )

    private val bioParts = listOf(
        "Likes long walks on the beach",
        "Likes kibble dinners under candlelight",
        "Likes to go hiking on weekends",
        "Good with kids",
        "I'm an extrovert, and sometimes an introvert",
        "I enjoy having fun",
        "Looking for a hooman who knows how to treat a doggo right!",
        "Must love dogs",
        "\uD83E\uDDB4 \uD83E\uDDB4 \uD83E\uDDB4",
        "\uD83E\uDD69 \uD83C\uDF56",
        "\uD83D\uDC1F \uD83C\uDF63",
        "\uD83D\uDC36 \uD83D\uDC29",
        LOREM_IPSUM.trim(),
    )

    fun generateDoggos(seed: Int = 0): List<Doggo> = (0 until 100).map {
        generateDoggo(it + seed.absoluteValue)
    }

    fun generateDoggo(seed: Int): Doggo {
        return Doggo(
            name = names.getModulo(seed),
            image = images.getModulo(seed),
            ageInDays = generateAge(seed),
            breed = breeds.getModulo(seed),
            coloration = colorations.getModulo(seed),
            bio = generateBio(seed)
        )
    }

    private fun generateAge(seed: Int): Int {
        return 10 + (seed % MAX_AGE)
    }

    private fun generateBio(seed: Int): String {
        val partCount = 1 + (seed % 5)
        return (0 until partCount)
            .map { bioParts.getModulo(seed + it) }
            .distinct()
            .joinToString(separator = "\n\n")
    }
}

private fun <E> List<E>.getModulo(index: Int): E {
    return get(index % this.size)
}

private const val LOREM_IPSUM = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tincidunt vehicula viverra. Integer consequat maximus quam non cursus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In varius fringilla lacus, sit amet condimentum tortor venenatis et. Nullam velit ligula, vulputate eget est at, tempor blandit diam. Proin vitae dui vitae massa eleifend blandit. Maecenas justo nunc, dignissim at pulvinar mattis, suscipit id enim. Sed quis tristique purus. Quisque ac dui sapien. Aenean ornare diam dapibus, imperdiet ex nec, imperdiet tortor.

Fusce blandit leo facilisis tellus consequat, porta aliquam quam pharetra. Sed pretium laoreet semper. Nullam vulputate volutpat ante. Pellentesque ornare malesuada arcu imperdiet semper. Ut cursus molestie lobortis. Nulla mollis cursus mauris ut porta. Proin vestibulum tempus ligula, eget interdum ligula maximus commodo. Nulla faucibus et odio vel tempor. Praesent tortor libero, aliquet vitae libero in, ultricies interdum est. Sed nec fringilla nulla. Ut interdum non tortor at iaculis. Nullam elementum mattis est placerat gravida. Sed porttitor enim nec nisi iaculis, et tristique odio pretium. Pellentesque at lectus arcu. Aliquam fringilla, nisi lobortis vulputate aliquet, neque risus finibus diam, a efficitur risus ex sed ipsum.
"""
