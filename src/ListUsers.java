
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ListUsers{
    private final int size = 5;
    private Map<String,Utilizador> utilizadores; //Key is the username
    private Map<String, ServerBuffer> messages;
    private int[][] map = new int[size][size];
    private Lock userLock;

    public ListUsers(){
        this.utilizadores = new HashMap<>();
        this.messages = new HashMap<>();
        this.userLock = new ReentrantLock(); 
       }

    /**
     * Method that will be used to register a user into the system.
     * Registration fails if the given username for registry has already been taken.
     *
     * @param username - new user's username
     * @param password - new user's password
     * @param ms - Buffer used to communicate between server and client
     * @throws InvalidRegistrationException - Thrown if username has already been taken
     */

    public void registerUser (String username, String password, String x1, String y1, ServerBuffer ms) throws InvalidRegistrationException, InvalidLocationException {
        this.userLock.lock();
        try {
            int x = Integer.parseInt(x1);
            int y = Integer.parseInt(y1);
            if(this.utilizadores.containsKey(username)){
                throw new InvalidRegistrationException("Nome de utilizador já em uso!");
            }
             else if(x < 0 || x > 5 || y < 0 || y > 5){
                throw new InvalidLocationException("Localização inválida!");
             }
             else {
                Utilizador user = new Utilizador(username,password, new Localizacao(x, y));
                this.utilizadores.put(username, user);
                this.messages.put(username,ms);
            }
        } finally {
            this.userLock.unlock();
        }
    }

    /**
     * Method that will allow for user authentication.
     * Login fails if username does not exist in the system or if the password doesn't match the registered one.
     *
     * @param username - Given username
     * @param password - Given password
     * @param ms - Buffer used to communicate between server and client
     * @return - Returns an instance of the user that has just been authenticated
     * @throws InvalidLoginException - thrown if username does not exist or if the password doesn't match the registered one.
     */

    public Utilizador loginUser (String username, String password, ServerBuffer ms) throws InvalidLoginException {
        Utilizador u;
        this.userLock.lock();
        try{
            if(!(this.utilizadores.containsKey(username))) {
                throw new InvalidLoginException("Nome de utilizador não existe!");
            } else if (!(this.utilizadores.get(username).getPassword().equals(password))){
                throw new InvalidLoginException("A password está incorreta!");
            }
            u = this.utilizadores.get(username);
        
            if(this.messages.containsKey(username)){
                ServerBuffer m = this.messages.get(username);
                String linha;
                while((linha = m.getMessages())!=null){
                    ms.setMessages(linha,null);
                }
                this.messages.put(username,ms);
            }
        } finally {
            this.userLock.unlock();
        }

        return u;
    }

}