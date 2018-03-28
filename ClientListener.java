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
   * Gets message from server and diplays it to the user.
   */
  public void run() {

    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));

      String initialText = serverInput.readLine();
      System.out.println(initialText);

      while (true) {

        // Get data sent from the server
        String serverText = serverInput.readLine();

        System.out.println();

        if (serverInput != null) {
          if (serverText.substring(0, 3).equals("Tie")) {
            System.out.println(serverText);
          }else if (serverText.substring(0,3).equals("Nop")) {
            System.out.println("You can't play there. That space has already been taken");
          }else if (serverText.substring(0, 3).equals("pla")) {
            System.out.println(serverText + " has won the game!");
            System.out.println("Thank you for playing tic tac toe!");
            connectionSock.close();
            System.exit(0);
            break;
          }else {
            int i = 0;
            while (i < 7) {
              for (int j = i; j < i + 3; ++j)
              {
                System.out.print(serverText.charAt(j));
              }
              i += 3;
              System.out.println();
              }
            }
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("The server terminated connection or the game was over.");
      System.out.println("Please press control+C");
    }
  }
} // ClientListener for MtClient
