package tictactoe

import java.lang.NumberFormatException
import kotlin.math.abs

const val GRID_SIZE = 3
const val EMPTY_CELL = " "
const val X_PLAYER = "X"
const val O_PLAYER = "O"

fun main() {
    val grid = List(GRID_SIZE) { mutableListOf(EMPTY_CELL, EMPTY_CELL, EMPTY_CELL) }
    printGrid(grid)

    var player = X_PLAYER
    while (!isGameEnd(grid)) {
        var coordinates = readln().split(" ")
        while (!validateCoordinates(coordinates, grid)) {
            coordinates = readln().split(" ")
        }
        val x = coordinates[0].toInt()
        val y = coordinates[1].toInt()
        grid[x - 1][y - 1] = player
        printGrid(grid)
        player = if (player == X_PLAYER) O_PLAYER else X_PLAYER
    }
}

fun isGameEnd(grid: List<MutableList<String>>): Boolean {
    val differenceBetweenAmountOfCells = abs( countCells(X_PLAYER, grid) - countCells(O_PLAYER, grid))
    val isXWin = checkRowWin(X_PLAYER, grid) || checkColumnWin(X_PLAYER, grid) || checkDiagonalWin(X_PLAYER, grid)
    val isOWin = checkRowWin(O_PLAYER, grid) || checkColumnWin(O_PLAYER, grid) || checkDiagonalWin(O_PLAYER, grid)
    val hasEmptyCells = hasEmptyCells(grid)

    when {
        isXWin && isOWin || differenceBetweenAmountOfCells >= 2 -> {
            println("Impossible")
            return false
        }

        isXWin -> {
            println("X wins")
            return true
        }

        isOWin -> {
            println("O wins")
            return true
        }

        hasEmptyCells -> return false

        else -> {
            println("Draw")
            return true
        }
    }
}

fun validateCoordinates(coordinates: List<String>, grid: List<MutableList<String>>): Boolean {
    if (coordinates.size < 2) {
        println("You should enter numbers!")
        return false
    }

    val x: Int
    val y: Int
    try {
        x = coordinates[0].toInt()
        y = coordinates[1].toInt()
    } catch (e: NumberFormatException) {
        println("You should enter numbers!")
        return false
    }

    if (x !in 1..GRID_SIZE || y !in 1..GRID_SIZE) {
        println("Coordinates should be from 1 to 3!")
        return false
    }

    if (grid[x - 1][y - 1] != EMPTY_CELL) {
        println("This cell is occupied! Choose another one!")
        return false
    }

    return true
}

fun countCells(cellValue: String, grid: List<MutableList<String>>): Int {
    var count = 0
    grid.forEach { row ->
        row.forEach { col -> if (col == cellValue) count++ }
    }
    return count
}

fun hasEmptyCells(grid: List<List<String>>): Boolean {
    grid.forEach { row ->
        row.forEach { col ->
            if (col == EMPTY_CELL) return true
        }
    }
    return false
}

fun checkDiagonalWin(cellValue: String, grid: List<List<String>>): Boolean {
    var firstDiagonal = 0
    var secondDiagonal = 0
    for (i in grid.indices) {
        if (grid[i][i] == cellValue) firstDiagonal++
        if (grid[i][grid.size - i - 1] == cellValue) secondDiagonal++
    }

    return firstDiagonal == GRID_SIZE || secondDiagonal == GRID_SIZE
}

fun checkColumnWin(cellValue: String, grid: List<List<String>>): Boolean {
    for (colIndex in 0 until grid[0].size) {
        var count = 0
        for (row in grid) {
            if (row[colIndex] == cellValue) count++
        }
        if (count == GRID_SIZE) return true
    }
    return false
}

fun checkRowWin(cellValue: String, grid: List<List<String>>): Boolean {
    grid.forEach { row ->
        val amount = row.count { it == cellValue }
        if (amount == GRID_SIZE) return true
    }
    return false
}

fun printGrid(grid: List<MutableList<String>>) {
    println("""
        |---------
        || ${grid[0].joinToString(" ")} |
        || ${grid[1].joinToString(" ")} |
        || ${grid[2].joinToString(" ")} |
        |---------
    """.trimMargin())
}