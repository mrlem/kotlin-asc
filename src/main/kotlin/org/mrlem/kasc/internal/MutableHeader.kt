package org.mrlem.kasc.internal

import org.mrlem.kasc.model.Header
import org.mrlem.kasc.model.Value

internal data class MutableHeader(
    var nCols: Int? = null,
    var nRows: Int? = null,
    var lowerLeftCellLocation: MutableLowerLeftCellLocation = MutableLowerLeftCellLocation(),
    var cellSize: Value? = null,
    var noDataValue: Value? = null,
) {

    fun toHeader(): Header? {
        return Header(
            nCols = nCols ?: return null,
            nRows = nRows ?: return null,
            lowerLeftCellLocation = lowerLeftCellLocation.toCellLocation() ?: return null,
            cellSize = cellSize ?: return null,
            noDataValue = noDataValue ?: return null,
        )
    }

}