package org.monitor.cmsfs.util

import java.io.{ File, FileInputStream }
import java.util.Properties

object PropertiesLoader {
  def from(file: String): Properties = {
    val properties = new Properties()
    val is = {
      getClass.getResourceAsStream(file) match {
        case null =>
          val f = new File(file)
          if (f.isFile) {
            new FileInputStream(f)
          } else {
            throw new IllegalArgumentException(s"File $file not found as classpath resource or on the filesystem")
          }
        case found => found
      }
    }

    try {
      properties.load(is)
      properties
    } finally {
      is.close()
    }
  }
}