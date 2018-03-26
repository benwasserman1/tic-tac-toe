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

public static int[][] board = new int[3][3];

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

        //Still need to identify the player
        char char_row = serverText.charAt(0);
        char char_col = serverText.charAt(1);

        int row = Character.getNumericValue(char_row);
        int col = Character.getNumericValue(char_col);

        board[row-1][col-1] = 1;

        //System.out.println("From client listener: " + serverText);
        if (serverInput != null) {
          for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
              System.out.print(board[i][j]);
            }
            System.out.println();
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
