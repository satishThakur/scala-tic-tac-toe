package com.satish.app.domain

sealed trait Piece

object Piece:
  case object O extends Piece
  case object X extends Piece

  def make(s: String): Option[Piece] =
    s.toLowerCase match {
      case "o" => Some(O)
      case "x" => Some(X)
      case _ => None
    }

  def other(p: Piece): Piece = if p == O then X else O