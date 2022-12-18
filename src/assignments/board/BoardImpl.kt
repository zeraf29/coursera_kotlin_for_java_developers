package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = TODO()


open class SquareBoardImpl(override val width: Int) : SquareBoard {

    private var cells: Array<Array<Cell>> = Array(width) { r ->
        Array(width) { c ->
            Cell(r + 1, c + 1)
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.getOrNull(i-1)?.getOrNull(j-1)

    override fun getCell(i: Int, j: Int): Cell =
        this.getCellOrNull(i, j) ?: throw IllegalArgumentException("Out of Bounds")

    override fun getAllCells(): Collection<Cell> {
        TODO("Not yet implemented")
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        TODO("Not yet implemented")
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        TODO("Not yet implemented")
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction){
            UP -> cells.getOrNull(this.i-1)?.getOrNull(this.j)
            DOWN -> cells.getOrNull(this.i+1)?.getOrNull(this.j)
            LEFT -> cells.getOrNull(this.i)?.getOrNull(this.j-1)
            RIGHT -> cells.getOrNull(this.i)?.getOrNull(this.j+1)
        }
    }

}