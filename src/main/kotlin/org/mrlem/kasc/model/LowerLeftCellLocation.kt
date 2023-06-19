package org.mrlem.kasc.model

sealed interface LowerLeftCellLocation {

    data class Center(val location: Location) : LowerLeftCellLocation
    data class Corner(val location: Location) : LowerLeftCellLocation

}