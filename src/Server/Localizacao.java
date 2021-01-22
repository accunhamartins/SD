package Server;

public class Localizacao{
    private int x;
    private int y;

    public Localizacao(){
        this.x = 0;
        this.y = 0;
    }

    public Localizacao(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Localizacao(Localizacao local){
        this.x = local.getX();
        this.y = local.getY();
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Localizacao clone(){
        return new Localizacao(this);
    }


}