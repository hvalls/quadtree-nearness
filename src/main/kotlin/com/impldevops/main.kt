package com.impldevops

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import kotlin.random.Random

const val ME_BOUNDARY_RADIUS = 50
const val MAP_SIDE = 500

lateinit var me: Person
val map = QuadTree(Rectangle(MAP_SIDE, MAP_SIDE))
val people = mutableListOf<Person>()

val container = initFrame()
val graphics = container.graphics!!

fun main() {
    initMe(randomCoords())
    initPeople()
    highlightPeopleNearMe()
    container.addKeyListener(object : KeyListener {
        override fun keyTyped(e: KeyEvent?) {

        }

        override fun keyPressed(e: KeyEvent) {
            if (!arrayOf(37, 38, 39, 40).contains(e.keyCode)) {
                return
            }
            onArrowPressed(e.keyCode)
        }

        override fun keyReleased(e: KeyEvent?) {

        }
    })
}

private fun onArrowPressed(keyCode: Int) {
    graphics.clearAll()
    when (keyCode) {
        37 -> { // Left
            initMe(Coords(me.coords.x - 10, me.coords.y))
        }
        38 -> { // Top
            initMe(Coords(me.coords.x, me.coords.y - 10))
        }
        39 -> { // Right
            initMe(Coords(me.coords.x + 10, me.coords.y))
        }
        40 -> { // Bottom
            initMe(Coords(me.coords.x, me.coords.y + 10))
        }
    }
    graphics.paint(people)
    highlightPeopleNearMe()
}

private fun initPeople() = listOf(
    "Mar", "Miguel", "María", "Angel", "Jose María", "Olga",
    "Nick", "Bea", "José Manuel", "Elena"
).forEach {
    Person(it, randomCoords()).apply {
        people.add(this)
        map.insert(this)
        graphics.paint(this)
    }
}

private fun highlightPeopleNearMe() {
    map.peopleNear(me, ME_BOUNDARY_RADIUS).forEach {
        graphics.highlight(it)
    }
}

private fun initFrame(): JFrame {
    val frame = JFrame()
    frame.preferredSize = Dimension(700, 700)
    frame.isResizable = false
    frame.setLocationRelativeTo(null)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
    frame.pack()
    frame.layout = null
    frame.isVisible = true
    return frame
}

fun initMe(coords: Coords) {
    me = Person("Yo", coords).apply {
        graphics.paint(this, Color.RED)
        graphics.paintBoundary(this)
    }
}

fun Graphics.paint(p: Person, c: Color = Color.BLACK) {
    color = c
    drawString(p.name, p.coords.x - 8, p.coords.y)
    fillOval(p.coords.x, p.coords.y, 8, 8)
}

fun Graphics.paint(people: List<Person>) = people.forEach { paint(it) }

fun Graphics.paintBoundary(p: Person) {
    color = Color.BLUE
    drawRect(
        p.coords.x - ME_BOUNDARY_RADIUS,
        p.coords.y - ME_BOUNDARY_RADIUS,
        ME_BOUNDARY_RADIUS * 2,
        ME_BOUNDARY_RADIUS * 2
    )
}

fun Graphics.highlight(p: Person) {
    color = Color.GREEN
    fillOval(p.coords.x, p.coords.y, 8, 8)
}

fun Graphics.clearAll() {
    color = Color.WHITE
    fillRect(0, 0, 1000, 1000)
}

fun randomCoords() = Coords(Random.nextInt(MAP_SIDE), Random.nextInt(MAP_SIDE))
