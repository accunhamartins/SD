public class Utilizador{
    private String nome;
    private String password;
    private Localizacao local;
    private boolean sick;

    public Utilizador(){
        this.nome = "";
        this.password = "";
        this.local = null;
        this.sick = false;
    }

    public Utilizador(String nome, String password, Localizacao local, boolean sick){
        this.nome = nome;
        this.password = password;
        this.local = local;
        this.sick = sick;
    }

    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.password= u.getPassword();
        this.local = u.getLocal();
        this.sick = u.isSick();
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

    public Utilizador clone(){
        return new Utilizador(this);
    }


}