package org.cmsfs.execute.script

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

@RunWith(classOf[JUnitRunner])
class ScriptExecuteText extends FlatSpec with Matchers {

  import ScriptExecute._

  val testScriptForLinux = Source.fromResource("test_script.sh")
  val testScriptForWindows = Source.fromResource("test_script.bat")

  "A Stack" should "pop values in last-in-first-out order" in {
    testScriptForLinux.mkString should be ("""echo "test"""")
  }


}