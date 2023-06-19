package org.mrlem.kasc.internal

import org.mrlem.kasc.model.LowerLeftCellLocation
import org.mrlem.kasc.model.Location
import org.mrlem.kasc.model.Value

internal data class MutableLowerLeftCellLocation(
    var type: Type? = null,
    var x: Value? = null,
    var y: Value? = null,
) {

    fun toCellLocation(): LowerLeftCellLocation? {
        return when (type) {
            Type.CENTER -> LowerLeftCellLocation.Center(
                location = Location(
                    x ?: return null,
                    y ?: return null,
                ),
            )
            Type.CORNER -> LowerLeftCellLocation.Corner(
                location = Location(
                    x ?: return null,
                    y ?: return null,
                ),
            )
            null -> null
        }
    }
}

enum class Type {
    CENTER,
    CORNER,
}