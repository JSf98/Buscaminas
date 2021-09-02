package Controlador;

import Model.Tauler;
import Vista.FinalType;
import Vista.Vista;
import java.util.ArrayList;

/**
 *
 * @author Jiim
 */
public class Controlador implements MousePressListener{
    
    private Tauler tauler;
    private Vista vista;
    
    /**
     * Cosntructor. 
     */
    public Controlador(int dimensio, int nmines){
        tauler = new Tauler(dimensio, nmines);
        //tauler.printInformacio();
    }
    
    /**
     * Getter. 
     * @return El tauler associat. 
     */
    public Tauler getTauler(){
        return tauler;
    }

    /**
     * Setter.
     * @param vista 
     */
    public void setVista(Vista vista) {
        this.vista = vista;
    }
    
    /**
     * Destapa les caselles corresponents a partir d'una posició en concret. 
     * @param x: Coordenada x.
     * @param y: Coordenada y.
     */
    public void destapaPosicio(int x, int y) {
        if (!tauler.isDestapada(x, y) && !tauler.teBandera(x, y)) {
            tauler.destapa(x, y);
            if (!tauler.teBomba(x, y)) {
                ArrayList<Coordenada> coa = getVeinsValids(x, y);

                while (!coa.isEmpty()) {
                    Coordenada cor = coa.get(0); //Desencoam
                    coa.remove(0);

                    ArrayList<Coordenada> aux = getVeinsValids(cor.x, cor.y);
                    if (!aux.isEmpty()) {
                        coa.addAll(aux);
                    }
                }
                
                if (this.tauler.comprova()) {
                    this.vista.acaba(FinalType.GUANYAT);
                }
            } else {
                //Ha perdut
                tauler.destapa(x, y);
                this.vista.acaba(FinalType.PERDUT);
            }
        }
    }
    
    /**
     * Donada una posició, retorna les adjacents vàlides. En el cas de que
     * un adjacent tingui bomba, retorna array buida. 
     * @param x: Coordenada x.
     * @param y: Coordenada y.
     * @return ArrayList<Coordenada>
     */
    private ArrayList<Coordenada> getVeinsValids(int x, int y) {
        int array[] = {-1, 0, 1};
        ArrayList<Coordenada> res = new ArrayList<>();

        for (int i : array) {
            for (int j : array) {
                int newX = (x + i);
                int newY = (y + j);

                if (tauler.isPosicioValida(newX, newY)) {
                    if (!tauler.isDestapada(newX, newY) && !tauler.teBandera(newX, newY)) {
                        if (tauler.teBomba(newX, newY)) {
                            return new ArrayList<>(); // Aturam i retornam buid
                        } else {
                            res.add(new Coordenada(newX, newY));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < res.size(); i++) {
            Coordenada c = res.get(i);
            tauler.destapa(c.x, c.y);
        }
        
        return res;
    }

    @Override
    /**
     * *
     * Duu a terme l'acció corresponent depenent del valor 'type'.
     */
    public void onCellClick(int x, int y, ButtonType type) {
        if (tauler.isPosicioValida(x, y)) {
            switch (type) {
                case ESQUERRA:
                    destapaPosicio(x, y);
                    break;
                case DRETA:
                    if (!tauler.isDestapada(x, y)) {
                        tauler.toggleBandera(x, y);
                        if (this.tauler.comprova()) {
                            this.vista.acaba(FinalType.GUANYAT);
                        }
                    }
                    break;
                default:
            }

        }
    }
    
    class Coordenada{
        private int x;
        private int y;
        
        public Coordenada(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
