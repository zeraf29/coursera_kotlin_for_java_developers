package games.gameOfFifteen

import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameImpl(initializer)


class GameImpl(private val initializer: GameOfFifteenInitializer): Game{

    private val board = createGameBoard<Int?>(4)
    private val solutionBoard = createGameBoard<Int?>(4)


    override fun initialize() {
        board.filter {
            it == null
        }.forEachIndexed { idx, cell ->
            board[cell] = initializer.initialPermutation.getOrNull(idx)
            solutionBoard[cell] = (1..15).toList().getOrNull(idx)
        }
    }

    override fun canMove(): Boolean = board.any { it == null }

    override fun hasWon(): Boolean = board.getAllCells().all{
        board[it] == solutionBoard[it]
    }

    override fun processMove(direction: Direction) {
        val toMoveCell = board.find { it == null }
        val fromMovelCell = with(board){
            toMoveCell?.getNeighbour(direction.reversed())
        }
        fromMovelCell?.let {
            board[toMoveCell!!] = board[fromMovelCell]
            board[fromMovelCell] = null
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

