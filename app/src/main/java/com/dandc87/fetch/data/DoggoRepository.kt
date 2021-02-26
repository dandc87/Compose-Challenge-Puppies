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

import kotlin.math.absoluteValue
import kotlin.random.Random

object DoggoRepository {
    private const val MAX_AGE: Int = 365 * 3

    private val names = listOf(
        "Wishbone",
        "Fido",
        "Jack",
        "Lassie",
        "Magpie",
        "Fluffy",
    )
    private val breeds = listOf("Mixed", "German Shepard", "Beagle", "Huskie")
    private val colorations = listOf("Black", "Brown", "White", "Gray", "Spotted")
    private val bioParts = listOf(
        "Likes long walks on the beach",
        "Likes kibble dinners under candlelight",
        "Likes to go hiking on weekends",
        "Good with kids",
        "Vegan",
        "I'm an extrovert, and sometimes an introvert",
        "I enjoy having fun",
        "Looking for a hooman who knows how to treat a doggo right!",
        "Must love dogs",
    )

    fun generateDoggos(random: Random = Random): List<Doggo> {
        return (0 until 10 + random.nextInt(100))
            .map { generateDoggo(random.nextInt().absoluteValue) }
    }

    fun generateDoggo(seed: Int): Doggo {
        return Doggo(
            name = names.getModulo(seed),
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
        val partCount = 1 + (seed % bioParts.size)
        return (0 until partCount).joinToString(separator = "\n\n") {
            bioParts.getModulo(seed + it)
        }
    }
}

private fun <E> List<E>.getModulo(index: Int): E {
    return get(index % this.size)
}
