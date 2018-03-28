/**
 * MTClient.java
 *
 * This program implements a simple multithreaded chat client.  It connects to the
 * server (assumed to be localhost on port 7654) and starts two threads:
 * one for listening for data sent from the server, and another that waits
 * for the user to type something in that will be sent to the server.
 * Anything sent to the server is broadcast to all clients.
 *
 * The MTClient uses a ClientListener whose code is in a separate file.
 * The ClientListener runs in a separate thread, recieves messages form the server,
 * and displays them on the screen.
 *
 * Data received is sent to the output screen, so it is possible that as
 * a user is typing in information a message from the server will be
 * inserted.
 *
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

import java.util.Scanner;

public class MtClient {
  /**
   * main method.
   * @params not used.
   */

  public static void main(String[] args) {
    try {
      String hostname = "localhost";
      int port = 7654;

      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port);

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

      System.out.println("Connection made.\n");

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      System.out.println("***** Let's play some Tic-Tac-Toe *****\n");
      System.out.print("***** Here is your game board. Indicate the row"); 
      System.out.print("and column that you would like to mark *****\n");

      int[][] board = new int[3][3];

      for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
          System.out.print(board[i][j]);
        }
        System.out.println();
      }
      System.out.println();

      // Read input from the keyboard and send it to everyone else.
      // The only way to quit is to hit control-c, but a quit command
      // could easily be added.
      Scanner keyboard = new Scanner(System.in);
      while (true) {
        String data = keyboard.nextLine();
        char charRow = data.charAt(0);
        char charCol = data.charAt(1);
        if ((Character.getNumericValue(charRow) > 3) || (Character.getNumericValue(charCol) > 3)) {
          System.out.println("The row or column input was larger than the bounds of the board");
        } else if ((Character.getNumericValue(charRow) < 1) 
            || (Character.getNumericValue(charCol) < 1)) {
          System.out.println("The row or column input was less than the bounds of the board");
        } else {
          serverOutput.writeBytes(data + "\n");
        }

      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
} // MtClient
