package com.impldevops

class Rectangle(
    val width: Int,
    val height: Int,
    val origin: Coords = Coords(0, 0),
) {

    fun contains(p: Coords) = (p.x >= origin.x &&
            p.x <= origin.x + width &&
            p.y >= origin.y &&
            p.y <= origin.y + height)

    fun overlaps(range: Rectangle) = !(range.origin.x - range.width > origin.x + width ||
            range.origin.x + range.width < origin.x - width ||
            range.origin.y - range.height > origin.y + height ||
            range.origin.y + range.height < origin.y - height)

}