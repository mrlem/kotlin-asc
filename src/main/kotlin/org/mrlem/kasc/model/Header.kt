package org.mrlem.kasc.model

data class Header(
    val nCols: Int,
    val nRows: Int,
    val lowerLeftCellLocation: LowerLeftCellLocation,
    val cellSize: Value,
    val noDataValue: Value,
)