
package Vista;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jiim
 */
public class Vista extends JFrame {
    private PanellDibuix p;
    private Controlador controlador;
    
    private final int tamanyTaulerX = 850;
    private final int tamanyTaulerY = tamanyTaulerX;
    
    public Vista(String nom, Controlador c){
        super(nom);
        this.p = new PanellDibuix(c);
        initComponents();
    }
    
    public void initComponents(){
        this.setSize(tamanyTaulerX, tamanyTaulerY);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 400));
        
        //BorderLayout predeterminat
        this.getContentPane().add(this.p, BorderLayout.CENTER);
    }
    
    /**
     * Acaba l'execució del videojoc depenent del valor 'type'. 
     * @param type 
     */
    public void acaba(FinalType type) {
        JFrame frame = new JFrame(" ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.repaint();
        switch(type){
            case GUANYAT:
                JOptionPane.showMessageDialog(frame, "Has guanyat!");
                break;
            case PERDUT:
                JOptionPane.showMessageDialog(frame, "Has perdut!");
                break;
        }
        System.exit(0);
    }
    
    public void setControlador(Controlador c){
        controlador = c;
        p.setControlador(c);
    }
}
