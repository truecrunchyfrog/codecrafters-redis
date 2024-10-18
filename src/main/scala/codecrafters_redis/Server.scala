package codecrafters_redis

import java.net.{InetSocketAddress, ServerSocket}
import scala.annotation.tailrec

object Server {
  def main(args: Array[String]): Unit = {
    val serverSocket = new ServerSocket
    serverSocket.bind(new InetSocketAddress("localhost", 6379))
    val clientSocket = serverSocket.accept() // wait for client

    @tailrec def stream(): Unit = {
      clientSocket.getInputStream.readAllBytes() match {
        case "*1\r\n$4\r\nPING\r\n" =>
          clientSocket.getOutputStream.write("+PONG\r\n".toArray.map(_.toByte))
      }
      stream()
    }

    stream()
  }
}
