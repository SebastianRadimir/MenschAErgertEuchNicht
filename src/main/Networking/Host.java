package Networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Host {
    private int port;

    private ServerSocket serverSocket;
    private Socket connectionToClientSocket;

    public Host(int port_p){
        port = port_p;
        while(connectionToClientSocket == null){
            openHostConnection();
            letClientConnect(); //this method waits until somebody connects
        }

        Thread t2 = new Thread(() ->{
            while(true) {
                clientListener();
            }
        });
        t2.start();

    }

    private void sendErrorToGame(String s){
        //send the error message to the game so it can display the problem to the user
        //something like "failed to send a message"
    }

    private void sendMessageToGame(String s){
        //send a message to the Game to update the game,position etc.
    }
    private boolean letClientConnect(){
        try {
            connectionToClientSocket = serverSocket.accept();
            return true;
        } catch (IOException e) {
            sendErrorToGame(e.toString());
            return false;
        }
    }
    private boolean openHostConnection(){
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(60000);
            return true;
        } catch (IOException e) {
            sendErrorToGame(e.toString());
            return false;
        }
    }
    public void sendDataToClient(String dataString){
        Thread t1 = new Thread(() ->{
            boolean sendStatus = false;
            int maxAttemts = 5;
            int attemts = 0;
            while(!sendStatus && attemts != maxAttemts){
                try {
                    DataOutputStream out = new DataOutputStream(connectionToClientSocket.getOutputStream());
                    out.writeUTF(dataString);
                    attemts += 1;
                    sendStatus = true;
                } catch (IOException e) {
                    sendStatus = false;
                    sendErrorToGame(e.toString());
                }
            }
        });
        t1.start();
    }

    private void clientListener(){
        try {
            InputStream inFromServer = connectionToClientSocket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            sendMessageToGame(in.readUTF());
        } catch (IOException e) {
            //sendErrorToGame("Failed to listen to the Server, Error: " + e);
        }
    }
    public void closeServer(){
        try {
            serverSocket.close();
            connectionToClientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
