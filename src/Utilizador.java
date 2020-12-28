public class Utilizador{
    private String nome;
    private String password;
    private Localizacao local;

    public Utilizador(){
        this.nome = "";
        this.password = "";
        this.local = null;
    }

    public Utilizador(String nome, String password, Localizacao local){
        this.nome = nome;
        this.password = password;
        this.local = local;
    }

    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.password= u.getPassword();
        this.local = u.getLocal();
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

    public void setLocal(Localizacao local) {
        this.local = local;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }


}