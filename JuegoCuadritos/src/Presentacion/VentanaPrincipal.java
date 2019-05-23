/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Canvas;
import javax.swing.JLabel;



/**
 *
 * @author Estudiantes
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private final Modelo modelo;
    private ControladorMensaje control;
    private ControladorTablero control2;
    
    public VentanaPrincipal(Modelo aThis) {
        modelo = aThis;
        initComponents();
        capturarEventos();
        TableroDibujo.addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent arg1) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseClicked(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaMensajes = new javax.swing.JTextArea();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        TableroDibujo = new java.awt.Canvas();
        lblConectado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Titulo");
        getContentPane().setLayout(null);

        txaMensajes.setColumns(20);
        txaMensajes.setRows(5);
        jScrollPane1.setViewportView(txaMensajes);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(460, 90, 190, 290);

        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });
        getContentPane().add(txtMensaje);
        txtMensaje.setBounds(460, 390, 120, 30);

        btnEnviar.setText("Enviar");
        getContentPane().add(btnEnviar);
        btnEnviar.setBounds(580, 390, 70, 30);
        getContentPane().add(TableroDibujo);
        TableroDibujo.setBounds(10, 90, 420, 330);

        lblConectado.setBackground(new java.awt.Color(51, 204, 0));
        lblConectado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblConectado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConectado.setText("Esperando conectarse");
        lblConectado.setOpaque(true);
        getContentPane().add(lblConectado);
        lblConectado.setBounds(110, 20, 460, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed

    }//GEN-LAST:event_txtMensajeActionPerformed

    private void capturarEventos(){
        TableroDibujo.addMouseListener(getControl2());//eventos del click en el canvas
        btnEnviar.addActionListener(getControl());
    }

    public Modelo getModelo() {
        return modelo;
    }

    public ControladorMensaje getControl() {
        if(control == null){
            control = new ControladorMensaje(this);
        }
        return control;
    }

    public ControladorTablero getControl2() {
        if(control2 == null){
            control2 = new ControladorTablero(this);
        }
        //segundo controlador para el tablero del juego
        return control2;
    }    
    
    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public JTextArea getTxaMensajes() {
        return txaMensajes;
    }

    public JTextField getTxtMensaje() {
        return txtMensaje;
    }
    
    public Canvas getCanvas(){
        return TableroDibujo;
    }
    
    public JLabel getLblEnunciado(){
        return lblConectado;
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas TableroDibujo;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConectado;
    private javax.swing.JTextArea txaMensajes;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
