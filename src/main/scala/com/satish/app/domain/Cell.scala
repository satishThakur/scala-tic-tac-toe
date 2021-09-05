package com.satish.app.domain

abstract case class Cell(value: Int)

object Cell:
  object R1C1 extends Cell(1)
  object R1C2 extends Cell(2)
  object R1C3 extends Cell(3)
  object R2C1 extends Cell(4)
  object R2C2 extends Cell(5)
  object R2C3 extends Cell(6)
  object R3C1 extends Cell(7)
  object R3C2 extends Cell(8)
  object R3C3 extends Cell(9)

  val all: List[Cell] = List(
    R1C1, R1C2, R1C3,
    R2C1, R2C2, R2C3,
    R3C1, R3C2, R3C3
  )

  def apply(value: Int): Cell = all(value - 1)

  def parse(raw: String): Option[Cell] = raw.trim match{
    case "1" => Some(R1C1)
    case "2" => Some(R1C2)
    case "3" => Some(R1C3)
    case "4" => Some(R2C1)
    case "5" => Some(R2C2)
    case "6" => Some(R2C3)
    case "7" => Some(R3C1)
    case "8" => Some(R3C2)
    case "9" => Some(R3C3)
    case _ => None
  }