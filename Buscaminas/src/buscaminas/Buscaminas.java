/** Fil principal del programa, encarregat d'inicialitzar les distintes classes.
 */
package buscaminas;

import Controlador.Controlador;
import Vista.Vista;

/**
 *
 * @author Jiim
 */
public class Buscaminas {
    
    Controlador controlador;
    Vista vista;
    
    public void start(){
        this.controlador = new Controlador(10, 10);
        
        this.vista = new Vista("Buscaminas", this.controlador);
        this.vista.setVisible(true); 
        this.controlador.setVista(vista);
    }    
            
    public static void main(String[] args) {
        new Buscaminas().start();
    }
    
}
