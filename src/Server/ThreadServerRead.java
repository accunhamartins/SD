package Server;


import java.io.BufferedReader;


public class ThreadServerRead implements Runnable{
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

    public void run(){
        try{
            String input;
            while((input = readSocket.readLine()) != null) {
                if(input.equals("1")){
                    String username, password;
                    username = readSocket.readLine();
                    password = readSocket.readLine();
                    try{
                        this.user = listUsers.loginUser(username, password, ms);
                        ms.setMessages("Sessão iniciada!", null);
                    } catch (Exception e){
                        ms.setMessages(e.getMessage() ,null);
                    }
                }
                else if(input.equals("2")){
                    String user,pass, x, y;
                    user = readSocket.readLine();
                    pass = readSocket.readLine();
                    x = readSocket.readLine();
                    y = readSocket.readLine();
                    try{
                        listUsers.registerUser(user,pass,x,y,ms);
                        ms.setMessages("Registado",null);
                    }
                    catch(Exception e){
                        ms.setMessages(e.getMessage(),null);
                    }
                }
                else if(input.equals("1.2")){
                    String x,y;
                    x = readSocket.readLine();
                    y = readSocket.readLine();
                    try{
                        this.listUsers.validaLocalizacao(user.getNome(),x,y,ms);
                        ms.setMessages("Localizacao Atualizada",null);
                    }
                    catch (Exception e){
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
