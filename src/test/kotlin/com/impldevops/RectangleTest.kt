package com.impldevops

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RectangleTest {

    @Test
    fun determinePointInside() {
        assertTrue(
            Rectangle(500, 500, Coords(0, 0))
                .contains(
                    Coords(50, 50)
                )
        )
    }

    @Test
    fun determinePointOutside() {
        assertFalse(
            Rectangle(500, 500, Coords(0, 0))
                .contains(
                    Coords(200, 501)
                )
        )
    }

    @Test
    fun overlapsTrueTest() {
        assertTrue(
            Rectangle(500, 500, Coords(0, 0))
                .overlaps(
                    Rectangle(40, 10, Coords(0, 0))
                )
        )
    }

    @Test
    fun overlapsFalseTest() {
        assertFalse(
            Rectangle(500, 500, Coords(0, 0))
                .overlaps(
                    Rectangle(200, 200, Coords(800, 50))
                )
        )
    }

}