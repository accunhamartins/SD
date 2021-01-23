package Client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadClientInput implements Runnable{
    private BufferedReader tecladoIn;
    private DataOutputStream writeSocket;
    private Socket socket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition cond;

    public ThreadClientInput (Socket s, Menu m, ReentrantLock l, Condition c){
        try {
            this.tecladoIn = new BufferedReader(new InputStreamReader(System.in));
            this.writeSocket = new DataOutputStream(s.getOutputStream());
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
                        writeSocket.writeUTF("1");
                        writeSocket.flush();
                        System.out.println("Username: ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Password: ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input= "1";
                    }
                    else if (input.equals("2")){                //Register
                        writeSocket.writeUTF("2");
                        writeSocket.flush();
                        System.out.println("Username: ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Password: ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Localização (X) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Localização (Y) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Credencial de Delegado de Saúde (0 se não for): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input= "2" ;
                    }
                    else if(input.equals("0")){//Exit
                        break;
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("0")) menu.showMenu();
                    else System.out.println("Opção Inválida");
                }
                else if(menu.getOpcao() == 1){
                    if(input.equals("0")) {
                        break;
                    }
                    else if (input.equals("1")){
                        writeSocket.writeUTF("1.1");
                        writeSocket.flush();
                        System.out.println("Localização (X) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Localização (Y) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input="1";
                    }
                    else if(input.equals("2")){ //Alterar localização

                        writeSocket.writeUTF("1.2");
                        writeSocket.flush();
                        System.out.println("Localização (X) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Localização (Y) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input="2";
                    }

                    else if(input.equals("3")){

                        writeSocket.writeUTF("1.3");
                        writeSocket.flush();
                        System.out.println("Localização (X) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        System.out.println("Localização (Y) (0 a 4): ");
                        input = tecladoIn.readLine();
                        writeSocket.writeUTF(input);
                        writeSocket.flush();

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input="3";
                    }

                    else if(input.equals("4")){
                        writeSocket.writeUTF("1.4");
                        writeSocket.flush();

                        this.lock.lock();
                        cond.await();
                        this.lock.unlock();

                        input="4";
                    }


                    if(input.equals("1") ||input.equals("2") || input.equals("3") || input.equals("0")) menu.showMenu();
                    else if(input.equals("4")) menu.showMenu();
                    else System.out.println("Opção Inválida");
                }
                else if(menu.getOpcao() == 3){
                    if(input.equals("0")){
                        break;
                    }
                    else { System.out.println("Opção Inválida");}
                }

            }

            writeSocket.close();
            socket.shutdownOutput();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
