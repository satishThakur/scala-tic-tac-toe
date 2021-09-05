package com.satish.app.domain

trait Board:
  /**
   * Update the board with piece p placed at cell.
   * This is immutable and hence returns new updated board.
   */
  def update(p: Piece, cell: Cell): Board

  /**
   * Currently unaccupied cells in the board.
   */
  def emptyCells: List[Cell]

  /**
   *
   * Return the peice accupying the given Cell.
   * If cell is empty return None.
   */
  def getPiece(cell: Cell): Option[Piece]

  /**
   *Get all combinations of wins.
   */
  def winnerCombinations: List[List[Cell]]




object Board:

  private case class BoardImpl(pieces: Map[Cell, Piece]) extends Board:

    override def winnerCombinations: List[List[Cell]] = winningPositions

    def update (piece: Piece, cell : Cell): Board = {
      BoardImpl(pieces + (cell -> piece))
    }

    def getPiece(pos: Cell): Option[Piece] = pieces.get(pos)

    def emptyCells: List[Cell] = Cell.all.filterNot(c => pieces.contains(c))

    def emptyCell(cell: Cell): Boolean = !pieces.contains(cell)

    override def toString: String =
      val line = "═══╬═══╬═══"

      val cells: List[(Cell,Option[Piece])] = Cell.all.map(c => (c, pieces.get(c)))
      val b = cells.map{
        case (_, Some(Piece.X)) => "X"
        case (_, Some(Piece.O)) => "O"
        case (c, None) => c.value.toString
      }
      val values = b.sliding(3,3).toList
      val lines: List[String] = values.flatMap(l => line :: l.mkString(" ",  " ║ ", "") :: Nil)
      lines.mkString("\n")

  end BoardImpl

  def empty: Board = BoardImpl(Map.empty)

  val winningPositions: List[List[Cell]] = List(
    List(Cell.R1C1,Cell.R1C2,Cell.R1C3),
    List(Cell.R2C1,Cell.R2C2,Cell.R2C3),
    List(Cell.R3C1,Cell.R3C2,Cell.R3C3),
    List(Cell.R1C1,Cell.R2C1,Cell.R3C1),
    List(Cell.R1C2,Cell.R2C2,Cell.R3C2),
    List(Cell.R1C3,Cell.R2C3,Cell.R3C3),
    List(Cell.R1C1,Cell.R2C2,Cell.R3C3),
    List(Cell.R1C3,Cell.R2C2,Cell.R3C1),
  )





