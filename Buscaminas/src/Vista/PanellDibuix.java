
package Vista;

import Controlador.ButtonType;
import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Jiim
 */
public class PanellDibuix extends JPanel implements MouseListener{
    Controlador controlador;
    
    public PanellDibuix(Controlador c) {
        this.addMouseListener(this);
        controlador = c;
    }
    
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }
    
    public void paint(Graphics g) {
        Dimension d = this.getSize();

        int dimensio = this.controlador.getTauler().getDimensio();
        int min = d.width;
        if (d.height < d.width) {
            min = d.height;
        }
        
        int costatX = min / dimensio;
        int costatY = min / dimensio;

        int y = 0;
        for (int i = 0; i < dimensio; i++) {
            int x = 0;
            for (int j = 0; j < dimensio; j++) {
                Rectangle2D.Float r = new Rectangle2D.Float(x, y, costatX, 
                        costatY);
                Graphics2D g2d = (Graphics2D) g;

                if (this.controlador.getTauler().teBandera(i, j)) {
                    g2d.setColor(Color.GREEN);
                    g2d.fill(r);
                } else {
                    if (this.controlador.getTauler().isDestapada(i, j)) {
                        String nombre = Integer.toString(
                                this.controlador.getTauler().getNombre(i, j));

                        // Background de la casella
                        if (nombre.equals("-1")) {
                            g2d.setColor(Color.RED);
                        } else {
                            g2d.setColor(Color.WHITE);
                        }
                        g2d.fill(r);

                        // Color del nombre de la casella
                        if (!nombre.equals("0")) {
                            g2d.setColor(Color.BLUE);
                            //FontMetrics
                            g2d.drawString((nombre),
                                    (r.x + (r.width / 2)),
                                    (r.y + (r.height / 2)));
                        }
                    } else {
                        g2d.setColor(Color.LIGHT_GRAY);
                        g2d.fill(r);
                    }
                }

                // Contorns
                g2d.setColor(Color.BLACK);
                g2d.draw(r);
                x += costatX;
            }
            y += costatY;
        }

    }
    
    public void setControlador(Controlador c){
        controlador = c;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Dimension d = this.getSize();
        int dimensio = this.controlador.getTauler().getDimensio();
        int min = d.width;
        if (d.height < d.width) {
            min = d.height;
        }
        int costatX = min / dimensio;
        int costatY = min / dimensio;
        int x = (me.getX()) / costatX;
        int y = (me.getY()) / costatY;
        
        this.controlador.onCellClick(y, x, me.getButton() == MouseEvent.BUTTON1? 
                ButtonType.ESQUERRA:ButtonType.DRETA);
        
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
