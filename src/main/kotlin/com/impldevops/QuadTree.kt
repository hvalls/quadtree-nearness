package com.impldevops

class QuadTree(
    private val boundary: Rectangle,
    private val capacity: Int = 3,
) {
    var people = listOf<Person>()
        private set
    private var divided = false
    private var q1: QuadTree? = null
    private var q2: QuadTree? = null
    private var q3: QuadTree? = null
    private var q4: QuadTree? = null

    fun insert(p: Person): Boolean {
        if (!boundary.contains(p.coords)) {
            return false
        }
        if (people.size < capacity) {
            people = people.plus(p)
            return true
        }
        if (!divided) {
            divide()
        }
        return (q1?.insert(p) ?: false ||
                q2?.insert(p) ?: false ||
                q3?.insert(p) ?: false ||
                q4?.insert(p) ?: false)
    }

    fun peopleNear(p: Person, radius: Int) = query(
        Rectangle(
            radius * 2, radius * 2, Coords(
                p.coords.x - radius, p.coords.y - radius
            )
        )
    )

    private fun query(range: Rectangle): List<Person> {
        val people = mutableListOf<Person>()
        queryAcc(range, people)
        return people
    }

    private fun queryAcc(range: Rectangle, acc: MutableList<Person>) {
        if (people.isEmpty() || !boundary.overlaps(range)) {
            return
        }
        acc.addAll(people.filter {
            range.contains(it.coords)
        })
        q1?.queryAcc(range, acc)
        q2?.queryAcc(range, acc)
        q3?.queryAcc(range, acc)
        q4?.queryAcc(range, acc)
    }

    private fun divide() {
        val x = boundary.origin.x
        val y = boundary.origin.y
        val width = boundary.width / 2
        val height = boundary.height / 2

        val q1Boundary = Rectangle(width, height, Coords(x, y))
        q1 = QuadTree(q1Boundary, capacity)

        val q2Boundary = Rectangle(width, height, Coords(x + width, y))
        q2 = QuadTree(q2Boundary, capacity)

        val q3Boundary = Rectangle(width, height, Coords(x, y + height))
        q3 = QuadTree(q3Boundary, capacity)

        val q4Boundary = Rectangle(width, height, Coords(x + width, y + height))
        q4 = QuadTree(q4Boundary, capacity)

        divided = true
    }

}