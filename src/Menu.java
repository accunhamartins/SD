

public class Menu {
    private String menu;
    private int opcao;

    public void showMenu() {
        switch(opcao){
            case 0: System.out.println("************* MENU ****************\n"+
                                       "* 1 - Iniciar Sessao              *\n"+
                                       "* 2 - Registar                    *\n"+
                                       "* 0 - Sair                        *\n"+
                                       "***********************************\n");
                    break;
            case 1: System.out.println("************** ÁREA CLIENTE **************\n"+
                                       "* 1 - Número de pessoas por localização  *\n"+
                                       "* 2 - Alterar localização                *\n"+
                                       "* 3 - Localizações livres                *\n"+
                                       "* 4 - Comunicar Infeção                  *\n"+
                                       "* 0 - Terminar Sessao                    *\n"+
                                       "******************************************\n");
                    break;
            case 2: 
                    break;     
            case 3: 
                    break;
                    
        }
    }

    public int getOpcao(){
        return this.opcao;
    }

    public void setOpcao(int n){
        this.opcao=n;
    }

}