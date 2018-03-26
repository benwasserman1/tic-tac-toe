/**
 * ClientHandler.java
 *
 * This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;


public class ClientHandler implements Runnable {
  private Socket connectionSock = null;
  private ArrayList<Socket> socketList;

  ClientHandler(Socket sock, ArrayList<Socket> socketList) {
    this.connectionSock = sock;
    this.socketList = socketList;  // Keep reference to master list
  }

  public static int[][] board = new int[3][3];

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {

    try {
      System.out.println("Connection made with socket " + connectionSock);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {
        // Get data sent from a client
        String clientText = clientInput.readLine();

        //Still need to identify the player
        char char_row = clientText.charAt(0);
        char char_col = clientText.charAt(1);

        int row = Character.getNumericValue(char_row);
        int col = Character.getNumericValue(char_col);

        board[row-1][col-1] = 1;

        if (clientText != null) {
          System.out.println("Received: " + clientText);
          for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
              System.out.print(board[i][j]);
            }
            System.out.println();
          }
          System.out.println();

          // Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          for (Socket s : socketList) {
            if (s != connectionSock) {
              DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
              clientOutput.writeBytes(clientText + "\n");
            }
          }
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          // Remove from arraylist
          socketList.remove(connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      socketList.remove(connectionSock);
    }
  }
} // ClientHandler for MtServer.java
