package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocket ss;
        Socket s = null;
        int i = 0;
        ListUsers l = new ListUsers();
        ReentrantLock lock = new ReentrantLock();

        try {
            ss = new ServerSocket(12345);

            while((s = ss.accept()) != null){
                BufferedReader readSocket = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter writeSocket = new PrintWriter(s.getOutputStream(),true);

                Condition cond = lock.newCondition();
                ServerBuffer ms = new ServerBuffer(cond,lock);

                Thread tsr = new Thread(new ThreadServerRead(readSocket,l,ms));
                Thread tsw = new Thread (new ThreadServerWrite(writeSocket,ms));
                tsr.start();
                tsw.start();
            }
            ss.close();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
