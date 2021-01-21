

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadClientInput extends Thread{
    private BufferedReader tecladoIn;
    private PrintWriter writeSocket;
    private Socket socket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition cond;

    public ThreadClientInput (Socket s, Menu m, ReentrantLock l, Condition c){
        try {
            this.tecladoIn = new BufferedReader(new InputStreamReader(System.in));
            this.writeSocket = new PrintWriter(s.getOutputStream(),true);
            this.socket = s;
            this.menu = m;
            this.lock = l;
            this.cond = c;
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        String input;

        try {
            menu.showMenu();
            while((input = tecladoIn.readLine()) != null){  
                if(menu.getOpcao() == 0){
                    if(input.equals("1")){                      //Login
                        writeSocket.println("1");
                        System.out.println("Username: ");
                        input = tecladoIn.readLine();
                        writeSocket.println(input);

                        System.out.println("Password: ");
                        input = tecladoIn.readLine();
                        writeSocket.println(input);

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input= "1";
                    }
                    else if (input.equals("2")){                //Register
                        writeSocket.println("2");
                        System.out.println("Username: ");
                        input = tecladoIn.readLine();
                        writeSocket.println(input);

                        System.out.println("Password: ");
                        input = tecladoIn.readLine();
                        writeSocket.println(input);

                        System.out.println("Localização (X): ");
                        input = tecladoIn.readLine();
                        writeSocket.println(input);

                        System.out.println("Localização (Y): ");
                        input = tecladoIn.readLine();
                        writeSocket.println(input);

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input= "2" ;
                    }
                    else if(input.equals("0")){                 //Exit
                        break;
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("0")) menu.showMenu();
                    else System.out.println("Opção Inválida");
                }
                else if(menu.getOpcao() == 1){
                    if(input.equals("0")) {
                        break;
                    }
                    else if(input.equals("2")){ //Alterar localização
                        boolean valid = false;
                        while(!valid){
                            writeSocket.println("1.2");
                            System.out.println("Localização (X): ");
                            input = tecladoIn.readLine();
                            writeSocket.println(input);

                            System.out.println("Localização (Y): ");
                            input = tecladoIn.readLine();
                            writeSocket.println(input);

                            this.lock.lock();
                            cond.await();
                            this.lock.unlock();

                            if(!valid){ //Caso de não ser válida
                                System.out.println("A posição que inseriu é inválida. Tente novamente.");
                            }
                        }
                        input="2";
                    }
                    if(input.equals("2") || input.equals("0")) menu.showMenu();
                    else System.out.println("Opção Inválida");
                }

            }
            
            socket.shutdownOutput();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
