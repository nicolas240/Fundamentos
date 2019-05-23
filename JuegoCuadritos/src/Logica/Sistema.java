
package logica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import logica.Timbiriche.Estado;


public class Sistema implements Runnable{
    
    private String nombreHost;
    private int puerto;
    private Socket servidor;
    private StringBuffer mensajes;
    private int altura;
    private int ancho;
    
    private Estado TableroServidor[][];
    private boolean conectado;
    
    private DataInputStream datosEntrada;
    private DataOutputStream datosSalida;
    ByteArrayInputStream TableroEntrada;//Input para el arreglo 
    ByteArrayOutputStream TableroSalida;
    
    private boolean recibeMensaje;
    private Thread hiloLectura;
    
    public Sistema() {
        conectado = false;
        mensajes = new StringBuffer();
        TableroServidor = new Estado[altura][ancho];
    }

    public String getNombreHost() {
        return nombreHost;
    }

    public int getPuerto() {
        return puerto;
    }

    public StringBuffer getMensajes() {
        return mensajes;
    }
    
    public Estado[][] getTablero(){
        return TableroServidor;
    }

    public boolean isConectado() {
        return conectado;
    }
    
    
    public void conectar(String host, int puerto) throws IOException{
        // 1. Establecer contacto
        servidor = new Socket(host, puerto);
        
        //2. Capturar flujos(stream)
        datosEntrada = new DataInputStream(servidor.getInputStream());
        //TableroEntrada Falta mirar los bytes  arreglo
        datosSalida = new DataOutputStream(servidor.getOutputStream());
        
        conectado = true;
        hiloLectura = new Thread(this);
        hiloLectura.start();
    }
    
    public void enviarMensaje(String msg) throws IOException{
        datosSalida.write(msg.getBytes());
        //Emvia posicion x y y
        mensajes.append(">: " + msg + "\n");
        System.out.println(">: " + msg + "\n");
    }

    @Override
    public void run() {
        
        byte buffer[] = new byte[256];
        String msg;
        
        while(conectado){
            try {
                datosEntrada.read(buffer);
                // si llego aqui, es porque algo llego
                msg = new String(buffer);
                mensajes.append("<: " + msg + "\n");
                recibeMensaje = true;
            } catch (IOException ex) {                
            }            
        }
    }

    public void desconectar() throws IOException {
        conectado = false;
        datosEntrada.close();
        datosSalida.close();
        servidor.close();        
    }

    public boolean isRecibeMensaje() {
        return recibeMensaje;
    }

    public void setRecibeMensaje(boolean recibeMensaje) {
        this.recibeMensaje = recibeMensaje;
    }
    
    
    
}
