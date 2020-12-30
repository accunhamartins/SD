

import java.io.BufferedReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadClientOutput extends Thread{
    private BufferedReader readSocket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition cond;

    public ThreadClientOutput(BufferedReader rs, Menu m, ReentrantLock l, Condition c){
        this.readSocket = rs;
        this.menu = m;
        this.lock=l;
        this.cond=c;
    }

    public void run() {
        try{
            String line;

            while((line = readSocket.readLine()) != null) {
                if(line.equals("Sessão iniciada!")){
                    menu.setOpcao(1);
                    System.out.println("Sessão iniciada!");
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }

                else if(line.equals("0")){
                    menu.setOpcao(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }

                else if(line.equals("Registado") || line.equals("Terminou sessão") || line.equals("Nome de utilizador não existe!")
                        || line.equals("A password está incorreta!") || line.equals("Nome de utilizador já em uso!")){
                    menu.setOpcao(0);
                    System.out.println("\n"+line+"\n");
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
