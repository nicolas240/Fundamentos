package Presentacion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logica.*;

public class Modelo implements Runnable{

    private SistemaServer miSistemaServer;
    private Sistema miSistema;
    private Cliente miCliente;
    private Timbiriche Juego;
            
    private VentanaPrincipal vistaPrincipal;
    private Inicio VistaInicial;
    
    private int Puerto;
    private String Estado;
    private String Host;
    private Thread hiloDibujo;
    private Thread Mensajes;
    
    private boolean conectado;
    
    public Sistema getMiCliente() {
        if(miSistema == null){
            miSistema = new Sistema();
        }
        return miSistema;
    }
    
    public SistemaServer getMiServer() {
        if(miSistemaServer == null){
            miSistemaServer = new SistemaServer();
        }
        return miSistemaServer;
    }

    public VentanaPrincipal getVentanaPrincipal() {
        if(vistaPrincipal == null){
            vistaPrincipal = new VentanaPrincipal(this);
        }
        return vistaPrincipal;
    }

    public Timbiriche getMiTablero() {
        if(Juego == null){
            Juego = new Timbiriche();
        }
        return Juego;
    }       
    
    public Inicio getVentanaInicio() {
        if(VistaInicial == null){
            VistaInicial = new Inicio(this);
        }
        return VistaInicial;
    }
    //************representacion de cU
    public void iniciar() {
        getVentanaInicio().setSize(480, 360);
        getVentanaInicio().setVisible(true);
        Estado ="No tiene";
    }
    
    public void iniciarServidor(int PuertoEntra, String HostEntra){
        getVentanaPrincipal().setSize(660,530);
        getVentanaPrincipal().setVisible(true);
        getVentanaInicio().setVisible(false);
        getVentanaPrincipal().getLblEnunciado().setText("CreandoPartida");
        getVentanaPrincipal().getTxaMensajes().setEnabled(false);
        getVentanaPrincipal().getTxtMensaje().setEnabled(false);
        getVentanaPrincipal().getBtnEnviar().setEnabled(false);
        getVentanaPrincipal().getTxaMensajes().setEditable(false);
        conectado = false;
        Puerto = PuertoEntra;
        Host = HostEntra;
        Estado = "Servidor";
        esperarConexion();
    }
    
    public void iniciarCliente(int PuertoEntra, String HostEntra){
        getVentanaPrincipal().setSize(660,530);
        getVentanaPrincipal().setVisible(true);        
        getVentanaInicio().setVisible(false);
        conectado = false;
        getVentanaPrincipal().getLblEnunciado().setText("Buscando Partida");
        getVentanaPrincipal().getTxaMensajes().setEnabled(false);
        getVentanaPrincipal().getTxtMensaje().setEnabled(false);
        getVentanaPrincipal().getBtnEnviar().setEnabled(false);
        getVentanaPrincipal().getTxaMensajes().setEditable(false);
        Puerto = PuertoEntra;
        Host = HostEntra;
        Estado = "Cliente";
        
    }
    
    public void esperarConexion(){                
        try {
            getMiServer().activarEsperaConexiones();
            getVentanaPrincipal().getLblEnunciado().setText("Buscando partida...");
            Mensajes = new Thread(this);
            hiloDibujo = new Thread(this);
            
            hiloDibujo.start();
            Mensajes.start();
            //getVentanaPrincipal().getBtnEscuchar().setText("Detener");
            //getVentanaPrincipal().getTxtPuerto().setEnabled(false);
        }catch (IOException ex) {
            mostrarError(ex.getMessage());
        }  
        getVentanaPrincipal().getLblEnunciado().setText("Conectado...");
    }
    
    void terminarConexion() {
        try {
            getMiServer().setEsperandoConexiones(false);
            getMiServer().detenerConexiones();           
            //getVentanaPrincipal().getTxtPuerto().setEnabled(true);
        } catch (IOException ex) {
        }
    }
 
    public void conectar(){
            try {    
                getMiCliente().conectar(Host, Puerto);
                conectado = true;
                Mensajes = new Thread(this);
                hiloDibujo = new Thread(this);

                //getVentanaInicial().getTxtHost().setEnabled(false);
                //getVentanaInicial().getTxtPuerto().setEnabled(false);
                //getVentanaInicial().getBtnConectar().setText("Desconectar");
                getVentanaPrincipal().getLblEnunciado().setText("Conectado...");
                getVentanaPrincipal().getTxaMensajes().setEnabled(true);
                getVentanaPrincipal().getTxtMensaje().setEnabled(true);
                getVentanaPrincipal().getBtnEnviar().setEnabled(true);
                
                
                getMiTablero().crearTablero();
                hiloDibujo.start();
                Mensajes.start();
                
            } catch (IOException ex) {
                mostrarError(ex.getMessage());
            }  
            
    }
    
    public void desconectar(){
        try {
            getMiCliente().desconectar();
            conectado = false;
            hiloDibujo = null;
        } catch (IOException ex) {
            mostrarError(ex.getMessage());            
        }
        getVentanaPrincipal().getTxaMensajes().setEnabled(false);
        getVentanaPrincipal().getTxtMensaje().setEnabled(false);
        getVentanaPrincipal().getBtnEnviar().setEnabled(false);
        getVentanaPrincipal().getTxaMensajes().setEditable(false);
    }
    
    public void recibirMensajes(){
        getVentanaPrincipal().getTxaMensajes().setText(getMiServer().getSbMensajes().toString());
    }
 
     private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(vistaPrincipal, msg, "Error de datos", JOptionPane.ERROR_MESSAGE);
    }
    
   public void enviarMensaje(){
        String mensaje;

        mensaje = getVentanaPrincipal().getTxtMensaje().getText();
        if (mensaje.isEmpty()) {
            mostrarError("El campo de mensaje no debe estar vacio");
            return;
        }
        try {
            if(Estado == "Cliente")
                getMiCliente().enviarMensaje(mensaje);
            else
                getMiServer().enviarMensaje(mensaje);
        } catch (IOException ex) {
            mostrarError(ex.getMessage());
        }
    }
    
    public void mostrarMensajes(){
            getVentanaPrincipal().getTxaMensajes().setText(getMiCliente().getMensajes().toString());
    }
    
    public void Raya(int a, int b){
        getMiTablero().Clic(a,b);
    }
    
    public void Dibujo(){
        
	Font fuenteNum=new Font("Impact", Font.BOLD, 25);
	Font fuenteBom=new Font("Impact", Font.BOLD, 50);
        
        Canvas lienzo = getVentanaPrincipal().getCanvas();
        BufferedImage dobleBuffer = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);        
        Graphics lapiz = dobleBuffer.getGraphics();
        Graphics lapizCanvas = lienzo.getGraphics();    
        //enum TableroDibujo = getMiTablero().getArreglo()[f][c];
        
        
        for(int c=0;c<getMiTablero().ancho;c++){
            for(int f=0;f< getMiTablero().altura;f++){
                int y= f*40;
                int x =c*40; 
                //NO DiBUJA LA ULTIMA FILA DEL TABLREO Y SE DAÑA
                //CUANDO SE OPRIME POR DEBAJO DE LA ULTIMA FILA
                //SE PODRIA USAR UN TRY CASTH EN CLIC
                lapiz.setColor(Color.black);
                lapiz.setFont(fuenteBom);
                lapiz.drawString(""+"*", x, y);
                lapiz.setColor(Color.red);

                switch(getMiTablero().ObtenerEstado(c, f)){                 
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
        lapizCanvas.drawImage(dobleBuffer, 0, 0, lienzo);
        if(getMiTablero().Resultado()==2)
            JOptionPane.showMessageDialog(getVentanaPrincipal(),"Gano","Felicitaciones",JOptionPane.INFORMATION_MESSAGE);    
       //Dibujar(getVentanaInicial(),getMiTablero()); 
    }
    
    @Override    
    public void run(){
        
        Thread ct = Thread.currentThread();
        
        /*while(ct == hiloDibujo){
            while(true){
                try {            
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                }
                Dibujo();
            }            
        }*/
        while (ct == Mensajes){
            while(true){
                try{
                    Thread.sleep(30);
                    
                }catch(InterruptedException ex){
                    
                }
                mostrarMensajes();
                recibirMensajes();
            }
        }
    }
}