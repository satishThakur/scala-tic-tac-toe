package com.satish.app.domain
import com.satish.app.domain.Status.{Draw, Ongoing, Win}
import com.satish.app.state.State

/**
 * Game maintains the current state of the Game. It contains the current
 * state of the board, which player turn is next as well as players of the
 * game.
 *
 */
case class Game(board: Board, turn: Player, players: (Player, Player)):
  import Game.transition
  /**
   * MakeMove takes the move of the player and retuns the the new Game
   */
  def makeMove(cell: Cell): Game =
    val move: State[Game, Status] = transition(turn, cell)
    move.run(this)(1)

  def status: Status = {
    if isWinner then Win(next)
    else if board.emptyCells.isEmpty then Draw else Ongoing
  }

  def next: Player = if turn == players(0) then players(1) else players(0)

  def isValidMove(cell: Cell): Boolean = board.getPiece(cell).isEmpty

  def emptyCells: List[Cell] = board.emptyCells

  def isWinner: Boolean =
    board.winnerCombinations.exists(
      winComb => winComb.forall(
        cell => board.getPiece(cell).map(_ == next.piece).getOrElse(false))
    )


object Game:
  /**
   * This function takea a player and his move and return a
   * function from Game -> Game.
   */
  def singleMove(p: Player, cell: Cell)(g: Game): Game =
    Game(g.board.update(p.piece, cell), g.next, g.players)

  def transition(p: Player, cell: Cell): State[Game,Status] =
    for{
      _ <- State.modify(singleMove(p, cell))
      s <- State.get
    } yield s.status
