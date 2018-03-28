/**
 * ClientListener.java
 *
 * This class runs on the client end and just
 * displays any text received from the server.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientListener implements Runnable {
  private Socket connectionSock = null;

  ClientListener(Socket sock) {
    this.connectionSock = sock;
  }

  /**
   * Gets message from server and dsiplays it to the user.
   */
  public void run() {

    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));

      while (true) {

        // Get data sent from the server
        String serverText = serverInput.readLine();

        System.out.println();

        if (serverInput != null) {
          if (!serverText.substring(0, 3).equals("Tie")) {
            if (!serverText.substring(0, 6).equals("player")) {
              int i = 0;
              while (i < 7){
                for (int j = i; j < i+3; ++j)
                {
                  System.out.print(serverText.charAt(j));
                }
                i+=3;
                System.out.println();
              }
            }
            else {
              System.out.println(serverText + "has won the game");
            }
          }
          else {
            System.out.println(serverText);
            //connectionSock.close();
            //break;
          }
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }
} // ClientListener for MtClient
