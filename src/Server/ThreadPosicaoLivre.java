package Server;

import Client.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPosicaoLivre implements Runnable{
    private ReentrantLock lock;
    private Condition cond;
    private ListUsers utilizadores;
    private ServerBuffer ms;
    private BufferedReader readSocket;
    private Socket socket;
    private int x;
    private int y;


    public ThreadPosicaoLivre (BufferedReader rs, ListUsers lu, ServerBuffer sb, String x, String y, Condition cond){
            this.readSocket = rs;
            this.ms = sb;
            this.lock = new ReentrantLock();
            this.utilizadores = lu;
            this.cond = cond;
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
    }

    public void run(){
        try {
            while (!utilizadores.estaLivre(x, y)){
                cond.await();
            }
        } catch (InterruptedException e){
        }
        ms.setMessages("A posição "+ x + " " + y + " está livre", null);
    }

}
