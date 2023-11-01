package com.example.bmi_calculator

fun getBmi(item: String?): String {
    val parts = item?.split(", ")
    return parts?.get(0)?.split(": ")?.get(1) ?: ""
}

fun getWeight(item: String?): String {
    val parts = item?.split(", ")
    return parts?.get(1) ?: ""
}

fun getHeight(item: String?): String {
    val parts = item?.split(", ")
    return parts?.get(2) ?: ""
}

fun getDate(item: String?): String {
    val parts = item?.split(", ")
    return parts?.get(3) ?: ""
}
