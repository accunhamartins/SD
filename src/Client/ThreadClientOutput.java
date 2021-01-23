package Client;

import Client.Menu;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadClientOutput implements Runnable{
    private DataInputStream readSocket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition cond;

    public ThreadClientOutput(DataInputStream rs, Menu m, ReentrantLock l, Condition c){
        this.readSocket = rs;
        this.menu = m;
        this.lock=l;
        this.cond=c;
    }

    public void run() {
        int count = 0;
        try{
            String line;

            while((line = readSocket.readUTF()) != null) {
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

                else if(line.equals("Sessão iniciada Saúde!")){
                    menu.setOpcao(2);
                    System.out.println("Sessão iniciada!");
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }

                else if(line.equals("Registado") || line.equals("Terminou sessão") || line.equals("Nome de utilizador não existe!")
                        || line.equals("A password está incorreta!") || line.equals("Nome de utilizador já em uso!")
                        || line.equals("Localização inválida! Efetue novamente o registo!")){
                    menu.setOpcao(0);
                    System.out.println("\n"+line+"\n");
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(line.equals("Localizacao Atualizada") || line.equals("Localização inválida!")
                        || line.contains("Numero de pessoas = ") || line.contains("A posição ")
                        || line.equals("Essa é a sua localização!")
                        ){
                    menu.setOpcao(1);
                    System.out.println("\n"+line+"\n");
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }

                else if(line.equals("Não se encontra livre. Será avisado assim que estiver")){
                    menu.setOpcao(1);
                    if(count == 0) System.out.println("\n"+line+"\n");
                    count++;
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }

                else if(line.equals("Utilizador Doente")){
                    menu.setOpcao(3);
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
