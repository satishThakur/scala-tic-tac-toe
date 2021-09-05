package com.satish.app.state

case class State[S, +A](run: S => (A,S)):

  /**
   *
   *Map is simple - given a state transition function, apply f on the output value.
   */
  def map[B](f: A => B): State[S,B] = State(
    s => {
      val (a,s1) = run(s)
      (f(a), s1)
    }
  )

  /**
   *
   * FlatMap is interesting. Get the A from curent STF, using f and A get STF for B
   * Pass the new state to B -> run the STF B.
   * Note that we are still producing STF - which means this is composition!!
   */
  def flatMap[B](f: A => State[S,B]): State[S,B] = State(
    s => {
      val (a,s1) = run(s)
      f(a).run(s1)
    }
  )


  /**
   *This definition is same as above. Lets try to reason it.
   * FlatMap would get A from this SFT -> Line 43.
   * now it passes A to the lambda which creates new STF using sb.
   * Now flatMap runs this new STF passing s1. Which means map or sb uses s2 line 44
   * and produces line 45.
   */
  def map2[B,C](sb: State[S,B])(f: (A,B) => C): State[S,C] =
    this.flatMap(a => sb.map(b => f(a,b)))


object State{

  //defines a State from single value. The state transition function always return same value.
  //State is ignored.
  def unit[S, A](a: A): State[S, A] = State(s => (a, s))

  //we have N state transition function for the domain A. here we compose a STF which
  //would pass state from one STF to another and keep collecting A's.
  //In other way we get a STF which when applied gets all A's and final state!!
  //Again remember this is comoposition!! we are composing a program.
  //how do we do it? map2 already takes care of combining 2 STF
  def sequence[S,A](ls: List[State[S,A]]): State[S, List[A]] =
    def loop(ss: List[State[S,A]], s: S, acc: List[A]) : (List[A], S) =
      ss match {
        case Nil => (acc.reverse, s)
        case x :: xs => {
          val (a, s1) = x.run(s)
          loop(xs, s1, a :: acc)
        }
      }
    State(s => loop(ls, s, Nil))

  /**
   *Simple combinator to produce a STF which gives the current state.
   */
  def get[S]: State[S,S] = State(s => (s,s))

  def gets[S,A](f: S => A): State[S,A] = get.map(f)


  /**
   *
   * Set arbitary state -
   */
  def set[S](s: S): State[S, Unit] = State(_ => ((), s))

  def modify[S](f: S => S): State[S,Unit] = for{
    s <- get
    _ <- set(f(s))
  } yield ()

}

