package Server;

import Exceptions.InvalidLocationException;
import Exceptions.InvalidLoginException;
import Exceptions.InvalidRegistrationException;
import Exceptions.UserInfetadoException;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ListUsers{
    private final int size = 5;
    private Map<String,Utilizador> utilizadores; //Key is the username
    private Map<String, ServerBuffer> messages;
    private int[][] map = new int[size][size];
    private List<List<Set <String>>> hist; //Matriz de users -> Java não permite criar matrizes com tipos não básicos
    private Lock userLock;
    private Lock posicaoLock;
    private Condition cond;
    private Set<Condition> queue;

    public ListUsers(){
        this.utilizadores = new HashMap<>();
        this.messages = new HashMap<>();
        this.userLock = new ReentrantLock();
        this.posicaoLock = new ReentrantLock();
        this.hist = new ArrayList<>(size);
        this.queue = new HashSet<>();
        this.cond = posicaoLock.newCondition();
        for(int i = 0; i < size; i++){
            hist.add(i, new ArrayList<>(size));
            for(int j = 0; j < size; j++){
                hist.get(i).add(j,new TreeSet<>());
            }
        } //Cria a matriz e em cada posição põe um set
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

    public void registerUser (String username, String password, String x1, String y1, String c,ServerBuffer ms) throws InvalidRegistrationException, InvalidLocationException {
        this.userLock.lock();
        try {
            int x = Integer.parseInt(x1);
            int y = Integer.parseInt(y1);
            int credencial = Integer.parseInt(c);
            if(this.utilizadores.containsKey(username)){
                throw new InvalidRegistrationException("Nome de utilizador já em uso!");
            }
             else if(x < 0 || x >= size || y < 0 || y >= size){
                throw new InvalidLocationException("Localização inválida! Efetue novamente o registo!");
             }
             else {
                 Localizacao ln = new Localizacao(x,y);
                 System.out.println("1");
                Utilizador user = new Utilizador(username,password, ln, credencial);
                System.out.println("2");
                System.out.println(user.getCredencial());
                this.utilizadores.put(username, user);
                this.map[x][y]++;
                this.hist.get(x).get(y).add(username);
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

    public Utilizador loginUser (String username, String password, ServerBuffer ms) throws InvalidLoginException, UserInfetadoException {
        Utilizador u;
        this.userLock.lock();
        try{
            if(!(this.utilizadores.containsKey(username))) {
                throw new InvalidLoginException("Nome de utilizador não existe!");
            } else if (!(this.utilizadores.get(username).getPassword().equals(password))){
                throw new InvalidLoginException("A password está incorreta!");
            } else if(this.utilizadores.get(username).isSick()){
                throw new UserInfetadoException("Utilizador Doente");
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

    public void validaLocalizacao (String username, String xs, String ys, ServerBuffer ms) throws InvalidLocationException, InterruptedException {
        this.userLock.lock();
        this.posicaoLock.lock();
        Localizacao l;
        try{
            int x = Integer.parseInt(xs);
            int y = Integer.parseInt(ys);
            if(x < 0 || x >= size || y < 0 || y >= size){
                throw new InvalidLocationException("Localização inválida!");
            }
            else {
                l = new Localizacao(x,y); //Nova localização
                //Antiga posição do user
                int xo = utilizadores.get(username).getLocal().getX();
                int yo = utilizadores.get(username).getLocal().getY();
                map[xo][yo]--; //Como user saiu da antiga posição, deixa de constar nessa mesma posição no mapa
                Utilizador u = utilizadores.get(username); //Alteramos a sua localização para a atual
                u.setLocal(l);
                u.addHistorico(l);
                utilizadores.put(u.getNome(),u);
                map[x][y]++; //Tem de constar na sua nova posição no mapa
                hist.get(x).get(y).add(username); //Colocamos já o User no historico de todos os users que estiveram nesta posição
                this.messages.put(username,ms);
                for(Condition c: queue){
                    c.signal();
                    queue.remove(c);
                }
            }
        } finally {
            this.userLock.unlock();
            this.posicaoLock.unlock();
        }
    }

    public int numeroPorLocalizacao(String xs, String ys, ServerBuffer ms) throws InvalidLocationException {
            int x = Integer.parseInt(xs);
            int y = Integer.parseInt(ys);
            if(x < 0 || x >= size || y < 0 || y >= size){
                throw new InvalidLocationException("Localização inválida!");
            }

            return map[x][y];
    }

    public void estaLivre(String xs, String ys, ServerBuffer ms, String nome) throws InterruptedException, InvalidLocationException{
        this.userLock.lock();
        int x = Integer.parseInt(xs);
        int y = Integer.parseInt(ys);
        try {
            Utilizador u = this.utilizadores.get(nome);
            if (x < 0 || x >= size || y < 0 || y >= size) {
                throw new InvalidLocationException("Localização inválida!");
            } else if (u.getLocal().getX() == x && u.getLocal().getY() == y) {
                throw new InvalidLocationException("Essa é a sua localização!");
            }
        } finally {
            userLock.unlock();
        }
            while (map[x][y] > 0) {
                ms.setMessages("A posição não se encontra livre. Será avisado assim que estiver", null);
                this.posicaoLock.lock();
                Condition condAux = posicaoLock.newCondition();
                queue.add(condAux);
                condAux.await();
                this.posicaoLock.unlock();
            }
            ms.setMessages("A posição " + x + " " + y + " está livre", null);

    }

    public void comunicaInfecao(String username, ServerBuffer ms){
        this.userLock.lock();
        try{
            Utilizador u = this.utilizadores.get(username);
            u.setSick(true);
            this.utilizadores.put(username, u);
            map[u.getLocal().getX()][u.getLocal().getY()]--;
        } finally {
            this.userLock.unlock();
        }

        ms.setMessages("Utilizador Doente", null);

    }

    public String mapToString() {

        this.userLock.lock();
        try {
            int[][] mapSick = new int[size][size];
            Set<String> s = new HashSet<>();
            for (String s1 : this.utilizadores.keySet()) {
                if (this.utilizadores.get(s1).isSick()) s.add(s1);
            }
            for (String s2 : s) {
                Utilizador u = this.utilizadores.get(s2);
                Set<Localizacao> h = u.getHistorico();
                for (Localizacao l : h) {
                    mapSick[l.getX()][l.getY()]++;
                }
            }

            StringBuilder sb = new StringBuilder();

            sb.append("Mapa dos utilizadores doentes por utilizadores totais numa localização\n  |  ");
            for (int k = 0; k < size; k++) {
                sb.append(k);
                if (k + 1 != size) sb.append("  |  ");
            }
            sb.append("\n");

            for (int i = 0; i < size; i++) {
                sb.append(i + " | ");
                for (int j = 0; j < size; j++) {
                    int total = this.map[i][j] + mapSick[i][j];
                    sb.append(mapSick[i][j] + "/" + total);
                    if (j + 1 != size) sb.append(" | ");
                }
                sb.append("\n");
            }

            return sb.toString();
        } finally {
            this.userLock.unlock();
        }
    }


}