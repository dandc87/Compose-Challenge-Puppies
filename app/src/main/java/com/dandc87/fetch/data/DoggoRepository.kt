package com.dandc87.fetch.data

import kotlin.random.Random

object DoggoRepository {
    private const val MAX_AGE: Int = 365 * 15

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
            .map { generateDoggo(random.nextInt()) }
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
        return 60 + (seed % MAX_AGE)
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
