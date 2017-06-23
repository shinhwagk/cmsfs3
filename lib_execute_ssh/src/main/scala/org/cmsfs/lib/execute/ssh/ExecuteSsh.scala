package org.cmsfs.lib.execute.ssh

import java.io.{BufferedReader, InputStreamReader}

import com.jcraft.jsch.{ChannelExec, JSch}
import play.api.libs.json.Json

import scala.collection.mutable.ArrayBuffer

object ExecuteSsh {
  def ssh(keyPath: String, host: String, port: Int, user: String, password: Option[String], command: String): Option[String] = {
    val jsch = new JSch();
    jsch.addIdentity(keyPath);
    val session = jsch.getSession(user, host, port);
    session.setConfig("StrictHostKeyChecking", "no");
    session.connect();

    val channelExec: ChannelExec = session.openChannel("exec").asInstanceOf[ChannelExec]
    val in = channelExec.getInputStream();
    channelExec.setCommand(command);
    channelExec.connect();

    val reader = new BufferedReader(new InputStreamReader(in));

    val rs = new ArrayBuffer[String]()

    var line: Option[String] = Option(reader.readLine())

    while (line.isDefined) {
      rs += line.get
      line = Option(reader.readLine())
    }

    val exitStatus: Int = channelExec.getExitStatus();

    try {
      if (exitStatus == 0 || exitStatus == -1) {
        Some(Json.toJson(rs).toString())
      } else {
        println(s"ssh error, host:${host},  data ${Json.toJson(rs).toString()}")
        None
      }
    } finally {
      channelExec.disconnect();
      session.disconnect();
    }
  }

}
