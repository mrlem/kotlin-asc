package org.mrlem.kasc.internal

import org.mrlem.kasc.model.Data
import org.mrlem.kasc.model.Raster
import org.mrlem.kasc.model.Value

internal data class MutableRaster(
    var header: MutableHeader = MutableHeader(),
    var data: MutableList<Value> = mutableListOf(),
) {

    fun toRaster(): Raster? {
        val header = header.toHeader() ?: return null

        if (header.nCols * header.nRows != data.size) return null

        return Raster(
            header = header,
            data = Data(ArrayList(data))
        )
    }

}