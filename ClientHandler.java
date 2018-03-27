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

  public static String[][] board_string = new String[][] {{"0", "0", "0"}, {"0", "0", "0"}, {"0", "0", "0"}};
  public static int count = 0;

  //check to see if the board is fully filled out
  public boolean checkComplete() {
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        if ((board_string[i][j] != "1") && (board_string[i][j] != "2")) {
          return false;
        }
      }
    }
    return true;
  }

  //win check here
  public boolean checkWin(String player) {
    //check diagonals
    if ((board_string[0][0] == player) && (board_string[1][1] == player) && (board_string[2][2] == player)) {
      return true;
    }
    else if ((board_string[0][2] == player) && (board_string[1][1] == player) && (board_string[2][0] == player)) {
      return true;
    }
    //check verticals and horizontals
    for (int i = 0; i < 3; ++i)
    {
      if ((board_string[i][0] == player) && (board_string[i][1] == player) && (board_string[i][2] == player)){
        return true;
      }
      else if ((board_string[0][i] == player) && (board_string[1][i] == player) && (board_string[2][i] == player)) {
        return true;
      }
    }
    return false;
  }

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

        char char_row = clientText.charAt(0);
        char char_col = clientText.charAt(1);

        int row = Character.getNumericValue(char_row);
        int col = Character.getNumericValue(char_col);

        if (count == 0) {
          board_string[row-1][col-1] = "1";
          count = 1;
        }
        else if (count == 1) {
          board_string[row-1][col-1] = "2";
          count = 0;
        }

        String sendData = "nothing";

        if (checkWin("2") == true) {
          sendData = "player 2";
        }
        else if (checkWin("1") == true) {
          sendData = "player 1";
        }
        else if ((checkWin("1") == false) && (checkWin("2") == false) && (checkComplete() == true)) {
          sendData = "Tie";
        }

        if (clientText != null) {
          System.out.println("Received: " + clientText);
          for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
              System.out.print(board_string[i][j]);
            }
            System.out.println();
          }
          System.out.println();

          // Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          for (Socket s : socketList) {
              DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
              if ((!sendData.substring(0,1).equals("p")) && (!sendData.substring(0,1).equals("T"))) {
                sendData = "";
                for (int i = 0; i < 3; ++i) {
                  for (int j = 0; j < 3; ++j) {
                    sendData += board_string[i][j];
                  }

                }
              }
              clientOutput.writeBytes(sendData + "\n");
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
