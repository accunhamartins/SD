import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ListUsers{
    private Map<String,Utilizador> utilizadores; //Key is the username
    private Map<String, ServerBuffer> messages;

    public ListUsers(){
        this.utilizadores = new HashMap<>();
        this.messages = new HashMap<>();
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

    public void registerUser (String username, String password, ServerBuffer ms) throws InvalidRegistrationException {
        synchronized (this.utilizadores) {
            if(this.utilizadores.containsKey(username)){
                throw new InvalidRegistrationException("Nome de utilizador já em uso!");
            } else {
                Utilizador user = new Utilizador(username,password);
                this.utilizadores.put(username, user);
                synchronized(this.messages){
                    this.messages.put(username,ms);
                }
            }
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
     * @throws InvalidLoginExcpetion - thrown if username does not exist or if the password doesn't match the registered one.
     */

    public Utilizador loginUser (String username, String password, ServerBuffer ms) throws InvalidLoginExcpetion {
        Utilizador u;

        synchronized (this.utilizadores){
            if(!(this.utilizadores.containsKey(username))) {
                throw new InvalidLoginExcpetion("Nome de utilizador não existe!");
            } else if (!(this.utilizadores.get(username).getPassword().equals(password))){
                throw new InvalidLoginExcpetion("A password está incorreta!");
            }
            u = this.utilizadores.get(username);
        }

        synchronized(this.messages){
            if(this.messages.containsKey(username)){
                ServerBuffer m = this.messages.get(username);
                String linha;
                while((linha = m.getMessages())!=null){
                    ms.setMessages(linha,null);
                }
                this.messages.put(username,ms);
            }
        }

        return u;
    }

}