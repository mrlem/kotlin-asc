package org.mrlem.kasc

import org.mrlem.kasc.internal.MutableRaster
import org.mrlem.kasc.internal.Type
import org.mrlem.kasc.model.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.text.ParseException
import kotlin.jvm.Throws

class Parser private constructor(
    private val source: BufferedReader,
) {

    companion object {

        @Throws(IOException::class)
        fun fromResource(resourceName: String): Parser =
            resourceName
                .let { Parser::class.java.getResourceAsStream(resourceName) ?: throw IOException("resource not found '$resourceName'") }
                .bufferedReader(Charsets.US_ASCII)
                .let { Parser(it) }

        fun fromFile(fileName: String): Parser =
            File(fileName)
                .bufferedReader(Charsets.US_ASCII)
                .let { Parser(it) }

    }

    fun parse(): Raster {
        val mutableRaster = MutableRaster()

        source.forEachLine { line ->
            val parts = line.split(" ")
            val key = parts.getOrNull(0)?.lowercase()
            val valueString = parts.getOrNull(1)

            when (key) {
                "ncols" -> mutableRaster.header.nCols = valueString?.toIntOrNull() ?: throw ParseException(line, 0)
                "nrows" -> mutableRaster.header.nRows = valueString?.toIntOrNull() ?: throw ParseException(line, 0)
                "xllcenter" -> {
                    mutableRaster.header.lowerLeftCellLocation.run {
                        type = Type.CENTER
                        x = valueString.toValueOrNull() ?: throw ParseException(line, 0)
                    }
                }
                "yllcenter" -> {
                    mutableRaster.header.lowerLeftCellLocation.run {
                        type = Type.CENTER
                        y = valueString.toValueOrNull() ?: throw ParseException(line, 0)
                    }
                }
                "xllcorner" -> {
                    mutableRaster.header.lowerLeftCellLocation.run {
                        type = Type.CORNER
                        x = valueString.toValueOrNull() ?: throw ParseException(line, 0)
                    }
                }
                "yllcorner" -> {
                    mutableRaster.header.lowerLeftCellLocation.run {
                        type = Type.CORNER
                        y = valueString.toValueOrNull() ?: throw ParseException(line, 0)
                    }
                }
                "cellsize" -> mutableRaster.header.cellSize = valueString.toValueOrNull() ?: throw ParseException(line, 0)
                "nodata_value" -> mutableRaster.header.noDataValue = valueString.toValueOrNull() ?: throw ParseException(line, 0)
                else -> parts
                    .map { it.toValueOrNull() ?: throw ParseException(line, 0) }
                    .let { mutableRaster.data.addAll(it) }
            }
        }

        return mutableRaster.toRaster() ?: throw Exception("Invalid file contents")
    }

}