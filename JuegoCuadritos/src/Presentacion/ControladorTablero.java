package Presentacion;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



public class ControladorTablero implements MouseListener  {
    private final VentanaPrincipal ventana;
    private Modelo model;

    public ControladorTablero (VentanaPrincipal aThis) {
        ventana = aThis;
        model = ventana.getModelo();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        
        int x = e.getX();
        int y = e.getY();
        System.out.println("Desde el controlador de eventos");
        System.out.println("X = " + x + ", Y = " + y );        
        if(e.getButton()== MouseEvent.BUTTON1)
            model.Raya(x,y);
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    @Override
    public void mouseExited(MouseEvent e){
        
    }
}