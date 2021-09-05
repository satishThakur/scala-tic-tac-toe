## Tic-Tac-Toe Scala-3 & ZIO

### Usage
1. In current directory open sbt console.
2. Use `compile` to compile to source code.
3. Use `run` to run the application.

### TODO
* Add Unit tests both property based and munit based.
* play around to see if we can create our own IO Monad with basic combinator functions and use that instead of ZIO.
* Few more enhancements to game:
    * Make computer bit smart to use better strategy.
    * How can we design this game so that it can be played by multiple players over internet.
    * Think how to extend when multiple players are playing (each having session etc).
    * What if we have save the game status.
    * What if we have to store games and have a way to replay already played game.
    * Can computer learn from already played games to have better strategy!!
    