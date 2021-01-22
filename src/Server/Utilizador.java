package Server;

import java.util.Set;
import java.util.TreeSet;

public class Utilizador{
    private String nome;
    private String password;
    private Localizacao local;
    private boolean sick;
    private Set<Localizacao> historico;

    public Utilizador(){
        this.nome = "";
        this.password = "";
        this.local = null;
        this.sick = false;
        this.historico = new TreeSet<>();
    }

    public Utilizador(String nome, String password, Localizacao local, Set<Localizacao> h){
        this.nome = nome;
        this.password = password;
        this.local = local;
        this.sick = false;
        this.setHistorico(h);
    }

    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.password= u.getPassword();
        this.local = u.getLocal();
        this.sick = u.isSick();
        this.historico = u.getHistorico();
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

    public Set<Localizacao> getHistorico(){
        return historico;
    }

    public boolean isSick() {
        return sick;
    }

    public void setLocal(Localizacao local) {
        this.local = local;
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

    public void setHistorico(Set<Localizacao> historico) {
        this.historico = historico;
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }


}