package org.mrlem.kasc.model

typealias Value = Float

fun String?.toValueOrNull() = this?.toFloatOrNull()