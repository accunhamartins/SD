package Servidor;

import java.io.PrintWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import Client.*;
import Exceptions.*;

public class ThreadServerWrite extends Thread{
    private PrintWriter write_socket;
    private Condition cond;
    private ServerBuffer ms;
    private ReentrantLock lock;

    public ThreadServerWrite(PrintWriter write_socket, ServerBuffer ms){
        this.write_socket = write_socket;
        this.cond = ms.getCondition();
        this.ms = ms;
        this.lock = ms.getLock();
    }

    public void run(){
        this.lock.lock();
        try{
            String linha;
            while(true){
                while((linha = ms.getMessages())==null)
                    cond.await();
                if(linha.equals("Sair"))
                    break;
                this.write_socket.println(linha);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            this.lock.unlock();
        }
    }
}
