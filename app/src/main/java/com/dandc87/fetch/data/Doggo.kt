package com.dandc87.fetch.data

private const val YEAR_AS_DAYS = 365
private const val MONTH_AS_DAYS = 30
private const val WEEK_AS_DAYS = 7

class Doggo(
    val name: String,
    val ageInDays: Int,
    val breed: String,
    val coloration: String,
    val bio: String,
) {
    val ageText = when {
        ageInDays > YEAR_AS_DAYS -> "${(ageInDays / YEAR_AS_DAYS)} years"
        ageInDays > (MONTH_AS_DAYS * 2) -> "${(ageInDays / MONTH_AS_DAYS)} months"
        else -> "${(ageInDays / WEEK_AS_DAYS)} weeks"
    } + " old"
}
