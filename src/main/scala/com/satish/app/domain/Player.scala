package com.satish.app.domain

trait Player:
  def piece: Piece

object Player:
  case class Computer(piece: Piece) extends Player
  case class Human(piece: Piece) extends Player