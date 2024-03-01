package view;

import controller.CtrlUsuario;
import model.Log;
import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistroUsuario {
    private JPanel RegistroUsuariopanel;
    private JTextField nombretext;
    private JTextField apellidoPaternotext;
    private JTextField apellidoMaternotext;
    private JTextField telefonotext;
    private JTextField correotext;
    private JComboBox genero;
    private JPasswordField contraseña;
    private JComboBox ciudades;
    private JButton siguienteButton;

    private CtrlUsuario ctrlUsuario;



    public RegistroUsuario(){
        ctrlUsuario = new CtrlUsuario();


        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombretext.getText();
                String password = new String(contraseña.getPassword());
                String apellidoMaterno = apellidoMaternotext.getText();
                String apellidoPaterno = apellidoPaternotext.getText();
                String telefono = telefonotext.getText();
                String correo = correotext.getText();
                int idGenero = obtenerIdGenero((String) genero.getSelectedItem());
                int idCiudad = obtenerIdCiudad((String) ciudades.getSelectedItem());

                Usuario nuevoUsuario = new Usuario(nombre,password,apellidoPaterno,apellidoMaterno,telefono,correo,idGenero,idCiudad);

                try {
                    boolean registroExitoso= Usuario.registrar(nuevoUsuario);
                    if (registroExitoso){

                        JOptionPane.showMessageDialog(null,"Registro Exitoso");
                        Log.info("");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error en el registro");
                    }

                }catch ( SQLException ex){
                    ex.printStackTrace();
                }


            }
        });
    }

    public void mostrarRegistro() {
        JFrame frame = new JFrame("RegistroUsuario");
        frame.setContentPane(new RegistroUsuario().RegistroUsuariopanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        Log.info("Se carga interfaz de registro de usuario");
    }


    private int obtenerIdGenero(String genero) {
         if ("Masculino".equals(genero)) {
            return 1;
        } else if ("Femenino".equals(genero)) {
            return 2;
        } else {
            return 0;
        }
    }

    private int obtenerIdCiudad(String ciudades){
        if ("Puebla".equals(ciudades)) {
            return 20;
        } else if ("México".equals(ciudades)) {
            return 14;
        } else if ("Veracruz".equals(ciudades)) {
            return 29;            
        } else if ("Nayarit".equals(ciudades)) {
            return 17;
        }else {
            return 0;
        }
    }
}
