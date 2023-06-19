package org.mrlem.kasc.model

data class Raster(
    val header: Header,
    val data: Data,
) {

    operator fun get(row: Int, column: Int) = data.rawData[header.nCols * row + column]

    fun forEach(block: (row: Int, column: Int, value: Value) -> Unit) {
        data.rawData.forEachIndexed { index, value ->
            block(
                index / header.nCols,
                index % header.nCols,
                value,
            )
        }
    }

}