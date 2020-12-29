import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    public static void main (String[] args) throws IOException {
        String input = null;
        Utilizador user = null;
        Socket socket = null;
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();

        try{
            socket = new Socket("localhost",1234);

            BufferedReader ler_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Menu menu = new Menu();
            ThreadClientInput tci = new ThreadClientInput(socket,menu,lock, cond);
            ThreadClientOutput tco = new ThreadClientOutput(ler_socket,menu,lock, cond);

            tci.start();
            tco.start();

            tci.join();
            tco.join();

            ler_socket.close();

            System.out.println("Até uma próxima!\n");
            socket.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
