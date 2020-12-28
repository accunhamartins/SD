import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ListUsers{
    Map<String , Utilizador> utilizadores;
    private ReentrantLock lock;


    public ListUsers(){
        this.utilizadores = new HashMap<>();
        lock = new ReentrantLock();
    }

}