import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main (String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        String userInput;
        while ((userInput = in.readLine()) != null) {
           
        }
        socket.shutdownOutput();
        socket.close();
    }
}
