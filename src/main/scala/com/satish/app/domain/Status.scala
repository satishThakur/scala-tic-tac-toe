package com.satish.app.domain

trait Status
object Status:
  case object Ongoing extends Status
  case object Draw extends Status
  case class Win(player: Player) extends Status
