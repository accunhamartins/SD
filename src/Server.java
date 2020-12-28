import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ServerWorker implements Runnable {
    private Socket socket;


    public ServerWorker (Socket socket) {
        this.socket = socket;
        
    }

    
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
            
            socket.shutdownInput();
            socket.close();

            System.out.println("Connection closed...");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}



public class Server {
    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        

        while (true) {
            Socket socket = serverSocket.accept();
            Thread worker = new Thread(new ServerWorker(socket));
            worker.start();
        }
    }
}
