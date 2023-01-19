package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = TODO()


open class SquareBoardImpl(override val width: Int) : SquareBoard {

    /*
    private var cells: Array<Array<Cell>> = Array(width) { r ->
        Array(width) { c ->
            Cell(r + 1, c + 1)
        }
    }
     */
    private var cells: List<Cell> = (1..width).flatMap { i ->
        (1..width).map { j ->
            Cell(i, j)
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        cells.firstOrNull{ cell -> cell.i == i && cell.j == j  }

    override fun getCell(i: Int, j: Int): Cell =
        this.getCellOrNull(i, j) ?: throw IllegalArgumentException("Out of Bounds")

    override fun getAllCells(): Collection<Cell> = cells

    /**
     * Progression: It has start and end points. and It can set a step.
     */
    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.mapNotNull { j ->
            getCellOrNull(i, j)
        }


    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.mapNotNull { i ->
            getCellOrNull(i, j)
        }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction){
            UP -> getCellOrNull(this.i-1, this.j)
            DOWN -> getCellOrNull(this.i+1, this.j)
            LEFT -> getCellOrNull(this.i, this.j-1)
            RIGHT -> getCellOrNull(this.i, this.j+1)
        }
    }

}

