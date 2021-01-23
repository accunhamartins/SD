package Server;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Utilizador{
    private String nome;
    private String password;
    private Localizacao local;
    private int credencial;
    private boolean sick;
    private Set<Localizacao> historico;

    public Utilizador(){
        this.nome = "";
        this.password = "";
        this.credencial = 0;
        this.local = null;
        this.sick = false;
        this.historico = new HashSet<>();
    }

    public Utilizador(String nome, String password, Localizacao local, int credencial){
        this.nome = nome;
        this.password = password;
        this.local = local;
        this.sick = false;
        this.credencial = credencial;
        this.historico = new HashSet<>();
        this.historico.add(local);
    }

    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.password= u.getPassword();
        this.local = u.getLocal();
        this.sick = u.isSick();
        this.historico = u.getHistorico();
        this.credencial = u.getCredencial();
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }
    
    public Localizacao getLocal() {
        return local.clone();
    }

    public int getCredencial() {
        return credencial;
    }

    public Set<Localizacao> getHistorico(){
        return historico;
    }

    public boolean isSick() {
        return sick;
    }

    public void setLocal(Localizacao local) {
        this.local = local;
    }

    public void setCredencial(int credencial) {
        this.credencial = credencial;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setSick(boolean sick) {
        this.sick = sick;
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }

    public void addHistorico(Localizacao l){
        this.historico.add(l);
    }
}