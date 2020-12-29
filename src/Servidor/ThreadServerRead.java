package Servidor;


import java.io.BufferedReader;
import Client.*;
import Exceptions.*;

public class ThreadServerRead extends Thread {
    private BufferedReader readSocket;
    private ListUsers listUsers;
    private Utilizador user;
    private ServerBuffer ms;

    public ThreadServerRead (BufferedReader rs, ListUsers lu, ServerBuffer sb) {
        this.readSocket = rs;
        this.listUsers = lu;
        this.user = null;
        this.ms = sb;
    }

    public void run() {
        try{
            String input;
            while((input = readSocket.readLine()) != null) {
                if(input.equals("1")){
                    String username, password;
                    username = readSocket.readLine();
                    password = readSocket.readLine();
                    try{
                        this.user = listUsers.loginUser(username, password, ms);
                        ms.setMessages("Sessão iniciada!",null);
                    } catch (InvalidLoginException e){
                        ms.setMessages("Nome de utilizador não existe!",null);
                    }
                }
                else if(input.equals("2")){
                    String user,pass;
                    user = readSocket.readLine();
                    pass = readSocket.readLine();

                    try{
                        listUsers.registerUser(user,pass,ms);
                        ms.setMessages("Registado",null);
                    }
                    catch(InvalidRegistrationException e){
                        ms.setMessages(e.getMessage(),null);
                    }
                }
            }
            readSocket.close();
            ms.setMessages("Sair",null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
