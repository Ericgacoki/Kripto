package com.ericg.kripto.util

import kotlin.random.Random

@Deprecated(message = "Useless Function!", ReplaceWith("true"))
fun Boolean.random(): Boolean {
    val int = Random(5).nextInt(0, 100)
    return this && (int % 2 == 0)
}

operator fun Boolean.component1(): Boolean = false
operator fun Boolean.component2(): Boolean = false
