
package Presentacion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import logica.Timbiriche;

/**
 *
 * @author brayanNicolas
 */
public class DibujoTablero {
    
    
    int Tablero[][];
    
    
    public void Dibujar(VentanaPrincipal Ventana,Timbiriche Tablero){
        
	Font fuenteNum=new Font("Impact", Font.BOLD, 25);
	Font fuenteBom=new Font("Impact", Font.BOLD, 50);
        
        Canvas lienzo = Ventana.getCanvas();
        BufferedImage dobleBuffer = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);        
        Graphics lapiz = dobleBuffer.getGraphics();
        Graphics lapizCanvas = lienzo.getGraphics();    
        //enum TableroDibujo = getMiTablero().getArreglo()[f][c];
        
        
        for(int c=0;c<Tablero.ancho;c++){
            for(int f=0;f< Tablero.altura;f++){
                int y= f*40;
                int x =c*40; 
                //NO DiBUJA LA ULTIMA FILA DEL TABLREO Y SE DAÑA
                //CUANDO SE OPRIME POR DEBAJO DE LA ULTIMA FILA
                //SE PODRIA USAR UN TRY CASTH EN CLIC
                lapiz.setColor(Color.black);
                lapiz.setFont(fuenteBom);
                lapiz.drawString(""+"*", x, y);
                lapiz.setColor(Color.red);

                switch(Tablero.ObtenerEstado(c, f)){                 
                    case 0://Blanco
                        break;
                    case 1://Horizontal
                        lapiz.fillRect(x,y,45,10);
                        break;
                    case 2://Vertical
                        lapiz.fillRect(x,y,10,45);
                        break;
                    case 3://LasDos
                        lapiz.fillRect(x,y,10,45);
                        lapiz.fillRect(x,y,45,10);
                        break;
                    case 4://BordeInferior
                        lapiz.fillRect(x,y+40,45,10);
                        break;
                    case 5://BordeDerecho
                        lapiz.fillRect(x+40,y,10,45);
                        break;
                    case 6://InferiorDerecho
                        lapiz.fillRect(x+40,y,10,45);
                        lapiz.fillRect(x,y+40,45,10);
                    case 7://VerticalInferior
                        lapiz.fillRect(x,y,10,45);
                        lapiz.fillRect(x,y+40,45,10);
                        break;
                    case 8://VerticalDerecho
                        lapiz.fillRect(x,y,10,45);
                        lapiz.fillRect(x+40,y,10,45);
                        break;
                    case 9://HorizontalDerecho
                        lapiz.fillRect(x,y,45,10);
                        lapiz.fillRect(x+40,y,10,45);
                        break;
                    case 10://HorizontalInferior
                        lapiz.fillRect(x,y,45,10);
                        lapiz.fillRect(x,y+40,45,10);
                        break;
                    case 11://LasDosDerecho
                        lapiz.fillRect(x,y,10,45);
                        lapiz.fillRect(x,y,45,10);
                        lapiz.fillRect(x+40,y,10,45);
                        break;
                    case 12://LasDosInferior
                        lapiz.fillRect(x,y,10,45);
                        lapiz.fillRect(x,y,45,10);
                        lapiz.fillRect(x,y+40,45,10);
                        break;
                    case 13:
                        lapiz.fillRect(x,y,10,45);
                        lapiz.fillRect(x,y,45,10);
                        lapiz.fillRect(x+40,y,10,45);
                        lapiz.fillRect(x,y+40,45,10);
                        lapiz.setFont(fuenteNum);
                        lapiz.setColor(Color.green);
                        lapiz.drawString(""+"x", x+20, y+30);
                        break;
                    default:
                        System.out.println("ERROR en f,c "+f+","+c);                         
                }
            }                    
        }
        if(Tablero.Resultado()==2)
            JOptionPane.showMessageDialog(Ventana,"Gano","Felicitaciones",JOptionPane.INFORMATION_MESSAGE);
        
        lapizCanvas.drawImage(dobleBuffer, 0, 0, lienzo);     
    }    
}

