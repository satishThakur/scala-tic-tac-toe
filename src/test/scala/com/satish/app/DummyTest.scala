package com.satish.app

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

object DummyTest extends Properties("dummy") {
  property("myProp1") = forAll{ (n: Int) => n == n}

}
