package Servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import Client.*;
import Exceptions.*;

class ServerWorker implements Runnable {
    private Socket socket;


    public ServerWorker (Socket socket) {
        this.socket = socket;
        
    }

    
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
            
            socket.shutdownInput();
            socket.close();

            System.out.println("Connection closed...");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}



public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocket ss;
        Socket s = null;
        int i = 0;
        ListUsers l = new ListUsers();
        ReentrantLock lock = new ReentrantLock();

        try {
            ss = new ServerSocket(1234);

            while((s = ss.accept()) != null){
                BufferedReader readSocket = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter writeSocket = new PrintWriter(s.getOutputStream(),true);

                Condition cond = lock.newCondition();
                ServerBuffer ms = new ServerBuffer(cond,lock);

                ThreadServerRead tsr = new ThreadServerRead(readSocket,l,ms);
                ThreadServerWrite tsw = new ThreadServerWrite(writeSocket,ms);
                tsr.start();
                tsw.start();
            }
            ss.close();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
