package Networking;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private Socket temporaryConnectionSocket;

    public Client(InetAddress serverIP_p, int port_p) {
        serverIP = serverIP_p;
        serverPort = port_p;
        Thread t1 = new Thread(() ->{
            while(temporaryConnectionSocket == null){
                tryToConnect();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(() ->{
            while(true) {
                serverListener();
            }
        });
        t2.start();
    }
    private boolean tryToConnect(){
        try {
            temporaryConnectionSocket = new Socket(serverIP, serverPort);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void sendData(String dataString){
        Thread t1 = new Thread(() ->{
            boolean sendStatus = false;
            int maxAttemts = 5;
            int attemts = 0;
            while(!sendStatus && attemts != maxAttemts){
                try {
                    OutputStream outToServer = temporaryConnectionSocket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF(dataString);
                    attemts += 1;
                    sendStatus = true;
                } catch (IOException e) {
                    sendStatus = false;
                    sendErrorToGame("something went wrong, please try again");
                }
            }
        });
        t1.start();
    }

    private void sendErrorToGame(String s){
        //send the error message to the game so it can display the problem to the user
        //something like "failed to send a message"
    }

    private void sendMessageToGame(String s){
        //send a message to the Game to update the game,position etc.
    }

    private void serverListener(){
        try {
            InputStream inFromServer = temporaryConnectionSocket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            sendMessageToGame(in.readUTF());
        } catch (IOException e) {
            sendErrorToGame("Failed to listen to the Server, Error: " + e);
        }
    }

    public void closeConnection(){
        try {
            temporaryConnectionSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
