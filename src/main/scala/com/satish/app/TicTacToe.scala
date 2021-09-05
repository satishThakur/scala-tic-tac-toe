package com.satish.app

import com.satish.app.domain.{Cell, Player, Piece, Game, Status, Board}
import zio.Console.{printLine, readLine}
import zio.{Console, ExitCode, Has, URIO, ZIO}
import zio.Random

import java.io.IOException

object TicTacToe extends zio.App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (for{
      p <- choosePiece
      p1 = Player.Human(p)
      p2 = Player.Computer(Piece.other(p))
      tossWinner <- toss(p1,p2)
      board = Board.empty
      game = Game(board, tossWinner, (p1,p2))
      _ <- gameLoop(game)
    } yield ()).exitCode


  def getPlayerMove(p : Player, valid: Cell => Boolean): ZIO[Has[Console], IOException, Cell] =
    for{
      _ <- printLine(s"Enter your move $p")
      rawMove <- readLine
      cell <- ZIO.fromOption(Cell.parse(rawMove)) <> (printLine(s"wrong format") *> getPlayerMove(p, valid))
      vcell <- ZIO.succeed(valid(cell)).flatMap {
        if _ then ZIO.succeed(cell) else (printLine("cell accupied") *> getPlayerMove(p, valid))
      }
    } yield vcell

  def gameLoop(g : Game): ZIO[zio.ZEnv, IOException, Unit] =
    g.status match {
      case Status.Ongoing => printLine(g.board.toString) *> playerMove(g.turn, g.emptyCells).flatMap {
        move => gameLoop(g.makeMove(move))
      }
      case _ => printGameStatus(g)
    }

  def choosePiece: ZIO[Has[Console], IOException, Piece] =
    for{
      _ <- printLine("Enter your choice O or X ?")
      raw <- readLine.orDie
      p <- ZIO.fromOption(Piece.make(raw)) <> (printLine("wrong format") *> choosePiece)
    } yield p

  def playerMove(p: Player, options: List[Cell]): ZIO[zio.ZEnv, IOException, Cell] =
    p match {
      case Player.Computer(_) => getComputerMove(options)
      case Player.Human(_) => getPlayerMove(p, options.contains(_))
    }

  def getComputerMove(freeCells: List[Cell]): ZIO[zio.ZEnv, IOException, Cell] =
    Random.nextIntBetween(0, freeCells.length)
      .map(freeCells(_))
      .tap(_ => printLine("Computer turn press enter to proceed..")) <* readLine.orDie


  def printGameStatus(g: Game): ZIO[Has[Console], IOException, Unit] =
    g.status match {
      case Status.Ongoing => printLine("Game in progress..")
      case Status.Draw => printLine("Game ended in draw")
      case Status.Win(p) => printLine(s"$p is the Winner!!")
    }

  def toss(p1: Player, p2: Player): ZIO[zio.ZEnv, IOException, Player] =
    Random.nextBoolean.map(if _ then p1 else p2)
      .tap(p => printLine(s"$p won the toss, Press ENTER to continue")) <* readLine.orDie

}

