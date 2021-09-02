package Model;

import java.util.Random;

/**
 *
 * @author Jiim
 */
public class Tauler {
    private Casella informacio[][];
    private int nmines;
    
    /**
     * Constructor.
     * @param dimensio: Dimensio que volem que tingui el tauler.
     * @param nmines: Nombre de mines que volem que tingui el tauler. 
     */
    public Tauler(int dimensio, int nmines){
        this.nmines = nmines;
        if (nmines > (dimensio * dimensio)) {
            //Error, hi ha més mines que posicions
            initTauler(dimensio);
            rellenaTauler(dimensio - 1);
        }else{
            initTauler(dimensio);
            rellenaTauler(nmines);
        }
    }
    
    /** 
     * Omple el tauler amb tot false (sense mines). 
     * @param dimensio: Dimensio del tauler.
     */
    private void initTauler(int dimensio){
        informacio = new Casella[dimensio][dimensio];
        
        for (int i = 0; i < informacio.length; i++) {           //files
            for (int j = 0; j < informacio[i].length; j++) {    //columnes
                informacio[i][j] = new Casella();
                informacio[i][j].nombre = 0;
            }
        }
    }
    
    /**
     * Donat un nombre determinat de mines, omple aleatoriament amb 'n' mines.
     * Simultaniament crea la informació respectiva al tauler. 
     * @param nmines: Nombre de mines que volem que tingui el tauler. 
     */
    private void rellenaTauler(int nmines) {
        Random rnd = new Random();
        
        for (int i = 0; i < nmines; i++) {
            int x = rnd.nextInt(informacio.length);
            int y = rnd.nextInt(informacio[0].length);
        
            //Significa que no hi ha cap mina anteriorment
            if (informacio[x][y].nombre != -1) {
                informacio[x][y].nombre = -1; 
                actualitzaAdjacents(x, y);
            }else{
                i--;
            }
        }
    }
    
    /**
     * Donada una posició on suposadament hi ha una mina, actualitza els seus 
     * adjacents. 
     * @param x: Coordenada x.
     * @param y: Coordenada y. 
     */
    private void actualitzaAdjacents(int x, int y){
        int array[] = {-1, 0, 1};
        //System.out.println("(" + x + "," + y + ")");
        
        for (int i: array) {
            for (int j: array){
                int newX = (x + i);
                int newY = (y + j);
                //System.out.println("\t(" + newX + "," + newY + ")");
                
                if (isPosicioValida(newX, newY)){
                    if (informacio[newX][newY].nombre != -1){
                        informacio[newX][newY].nombre++;
                    }
                }
            }
        }
    }
    
    /**
     * Donada una posició, comprova que és una posició vàlida i per tant, no 
     * surt de la matriu. 
     * @param x: Coordenada x. 
     * @param y: Coordenada y.
     * @return 'true' si la posició no surt del tauler. 'false' en el cas 
     * contrari. 
     */
    public boolean isPosicioValida(int x, int y){
        return ((x != -1) && (y != -1) && (x < informacio.length) 
                && (y < informacio[0].length));
    }
    
    /**
     * Comprova si el tauler esta completat o no. 
     * @return 'true' si el tauler ja esta completat satisfactoriament. 'false'
     * en el cas contrari. 
     */
    public boolean comprova(){
        int nminesCorrectes = 0;
        int nminesIncorrectes = 0;
        boolean completat = true;
        
        for (int i = 0; i < informacio.length; i++) {
            for (int j = 0; j < informacio[i].length; j++) {
                // Requeriment: Totes les caselles han d'estar destapades
                if (!informacio[i][j].bandera && !informacio[i][j].destapada) {
                    completat = false;
                }
                
                if (informacio[i][j].bandera) {
                    if (informacio[i][j].nombre == -1) {
                        nminesCorrectes++;
                    }else{
                        nminesIncorrectes++;
                    }
                }
            }
        }
        
        if (nminesCorrectes == this.nmines && nminesIncorrectes == 0 && completat) {
            return true;
        }
        return false;
    }
    
    /** 
     * Pinta la informacio del tauler per consola. 
     */
    public void printInformacio(){
        for (int i = 0; i < informacio.length; i++) {           //files
            for (int j = 0; j < informacio[i].length; j++) {    //columnes
                if (informacio[i][j].nombre == -1) {
                    System.out.print("@ ");
                }else{
                    System.out.print(informacio[i][j].nombre + " ");
                }
                
            }
            System.out.println("");
        }
        System.out.println("\n");
    }
    
    /**
     * Getter.
     * @return dimensio del tauler. 
     */
    public int getDimensio(){
        return informacio.length;
    }
    
    /**
     * Estableix com a destapada una posició en concret. 
     * @param x: Coordenada x.
     * @param y: Coordenada y.
     */
    public void destapa(int x, int y){
        informacio[x][y].destapada = true;
    }
  
    /**
     * Donada una casella, commuta l'estat de la seva bandera. 
     * @param x: Coordenada x.
     * @param y: Coordenada y.
     */
    public void toggleBandera(int x, int y) {
        if (informacio[x][y].bandera) {
            informacio[x][y].bandera = false;
        }else {
            informacio[x][y].bandera = true;
        }
    }
    
    
    //GETTERS
    
    public int getNombre(int x, int y){
        return informacio[x][y].nombre;
    }
    
    public boolean isDestapada(int x, int y){
        return informacio[x][y].destapada;
    }
    
    public boolean teBomba(int x, int y){
        return informacio[x][y].nombre == -1;
    }
    
    public boolean teBandera(int x, int y){
        return informacio[x][y].bandera;
    }
    
    class Casella{
        private boolean destapada;
        private int nombre;
        private boolean bandera;
        
        /**
         * Constructor
         */
        public Casella() {
            destapada = false;
            bandera = false;
            nombre = 0;
        }
    }
}
