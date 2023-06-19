package org.mrlem.kasc

import org.junit.jupiter.api.Test
import org.mrlem.kasc.model.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParserTest {

    @Test
    fun `parser can parse a valid empty file`() {
        // GIVEN
        val parser = Parser.fromResource("/empty.asc")

        // WHEN
        val raster = parser
            .parse()

        // THEN
        assertEquals(
            expected = Raster(
                header = Header(
                    nCols = 0,
                    nRows = 0,
                    lowerLeftCellLocation = LowerLeftCellLocation.Center(
                        location = Location(
                            x = 123f,
                            y = 456f,
                        ),
                    ),
                    cellSize = 7f,
                    noDataValue = -89f,
                ),
                data = Data(emptyList()),
            ),
            actual = raster,
        )
    }

    @Test
    fun `parser can parse a valid populated file`() {
        // GIVEN
        val parser = Parser.fromResource("/sample.asc")

        // WHEN
        val raster = parser
            .parse()

        // THEN
        assertEquals(
            expected = Raster(
                header = Header(
                    nCols = 20,
                    nRows = 2,
                    lowerLeftCellLocation = LowerLeftCellLocation.Corner(
                        location = Location(
                            x = 378923f,
                            y = 4072345f,
                        ),
                    ),
                    cellSize = 30f,
                    noDataValue = -32768f,
                ),
                data = Data(listOf(
                    43f, 2f, 45f, 7f, 3f, 56f, 2f, 5f, 23f, 65f, 34f, 6f, 32f, 54f, 57f, 34f, 2f, 2f, 54f, 6f,
                    45f, 65f, 34f, 2f, 6f, 78f, 4f, 2f, 6f, 89f, 3f, 2f, 7f, 45f, 23f, 5f, 8f, 4f, 1f, 62f,
                )),
            ),
            actual = raster,
        )
    }

    @Test
    fun `parser can correctly get the value at a cell position`() {
        // GIVEN
        val parser = Parser.fromResource("/sample.asc")

        // WHEN
        val raster = parser
            .parse()
        val cellValue = raster[1, 3]

        // THEN
        assertEquals(
            expected = 2f,
            actual = cellValue,
        )
    }

    @Test
    fun `parser can correctly iterate over the cell values`() {
        // GIVEN
        val parser = Parser.fromResource("/sample.asc")

        // WHEN
        val raster = parser
            .parse()
        val iterations = mutableListOf<Triple<Int, Int, Value>>()
        raster.forEach { row, column, value ->
            iterations.add(Triple(row, column, value))
        }

        // THEN
        assertContentEquals(
            expected = listOf(
                Triple(0, 0, 43f),
                Triple(0, 1, 2f),
                Triple(0, 2, 45f),
                Triple(0, 3, 7f),
                Triple(0, 4, 3f),
                Triple(0, 5, 56f),
                Triple(0, 6, 2f),
                Triple(0, 7, 5f),
                Triple(0, 8, 23f),
                Triple(0, 9, 65f),
                Triple(0, 10, 34f),
                Triple(0, 11, 6f),
                Triple(0, 12, 32f),
                Triple(0, 13, 54f),
                Triple(0, 14, 57f),
                Triple(0, 15, 34f),
                Triple(0, 16, 2f),
                Triple(0, 17, 2f),
                Triple(0, 18, 54f),
                Triple(0, 19, 6f),

                Triple(1, 0, 45f),
                Triple(1, 1, 65f),
                Triple(1, 2, 34f),
                Triple(1, 3, 2f),
                Triple(1, 4, 6f),
                Triple(1, 5, 78f),
                Triple(1, 6, 4f),
                Triple(1, 7, 2f),
                Triple(1, 8, 6f),
                Triple(1, 9, 89f),
                Triple(1, 10, 3f),
                Triple(1, 11, 2f),
                Triple(1, 12, 7f),
                Triple(1, 13, 45f),
                Triple(1, 14, 23f),
                Triple(1, 15, 5f),
                Triple(1, 16, 8f),
                Triple(1, 17, 4f),
                Triple(1, 18, 1f),
                Triple(1, 19, 62f),
            ),
            actual = iterations,
        )
    }

    @Test
    fun `parser correctly reports a file with missing headers`() {
        assertFails {
            Parser.fromResource("/empty_no_cols.asc")
                .parse()
        }

        assertFails {
            Parser.fromResource("/empty_no_rows.asc")
                .parse()
        }

        assertFails {
            Parser.fromResource("/empty_no_location_x.asc")
                .parse()
        }

        assertFails {
            Parser.fromResource("/empty_no_location_y.asc")
                .parse()
        }

        assertFails {
            Parser.fromResource("/empty_no_cellsize.asc")
                .parse()
        }

        assertFails {
            Parser.fromResource("/empty_no_nodata_value.asc")
                .parse()
        }
    }

    @Test
    fun `parser correctly reports a file with missing data`() {
        assertFails {
            Parser.fromResource("/empty_no_data.asc")
                .parse()
        }
    }

    @Test
    fun `parser correctly reports a file with too much data`() {
        assertFails {
            Parser.fromResource("/empty_too_much_data.asc")
                .parse()
        }
    }

}