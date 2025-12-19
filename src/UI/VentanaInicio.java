package UI;

import repositorio.EnMemoriaCatalogoRepositorio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.Border;

public class VentanaInicio extends JFrame {
    EnMemoriaCatalogoRepositorio repositorio;
    public VentanaInicio(EnMemoriaCatalogoRepositorio repositorio) {
        this.repositorio = repositorio;
        setTitle("Clasificador de anime");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //TODO GENERAR EL PANEL SUPERIOR
        JPanel panelSuperior = new JPanel();
        panelSuperior.setPreferredSize(new Dimension(800, 120));
        panelSuperior.setLayout(new GridBagLayout());
        JLabel titulo = new JLabel("Clasificador de anime");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 35));
        panelSuperior.add(titulo);
        add(panelSuperior, BorderLayout.NORTH);

        //TODO GENERAR EL PANEL INFERIOR
        JPanel panelInferior = new JPanel();
        panelInferior.setPreferredSize(new Dimension(800, 480));
        panelInferior.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        //TODO GENERAR OPCIONES DEL MENU PRINCIPAL
        generarBotones(gbc, panelInferior);
        add(panelInferior, BorderLayout.CENTER);

    }
    public void generarBotones(GridBagConstraints gbc, JPanel panelInferior){
        //TODO GENERARADOR DE BOTONES
        Boton botonTemplatePrincipal = new Boton(50,300,Color.LIGHT_GRAY,new Font("Arial", Font.BOLD, 18), Color.GRAY);
        if (repositorio.existeCatalogo()) {
            //todo BOTON DE CARGAR CATALOGO
            JButton botonCargar = botonTemplatePrincipal.getBoton("Cargar Catalogo");
            gbc.gridy = 0;
            panelInferior.add(botonCargar, gbc);
            //todo BOTON DE ELIMINAR CATALOGO
            JButton botonEliminar = botonTemplatePrincipal.getBoton("Eliminar Catalogo");
            gbc.gridy = 2;
            panelInferior.add(botonEliminar, gbc);
        } else {
            //todo CREA EL TEXT IMPUT Y UN LABEL PARA MOSTRAR EL ERROR
            JTextField txtInput = new JTextField();
            JLabel lblError = new JLabel();
            txtInput.setBorder(BorderFactory.createLineBorder( Color.LIGHT_GRAY, 2));
            txtInput.setPreferredSize(new Dimension(300, 50));
            lblError.setForeground( new Color(255, 105, 97));
            lblError.setFont(new Font("Arial", Font.BOLD, 12));
            txtInput.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    String text = txtInput.getText();
                    int length = text.length();
                    if (length < 5 || length > 30) {
                        txtInput.setBorder(BorderFactory.createLineBorder( new Color(255, 105, 97), 2));
                        lblError.setText("Error: El nombre del catalogo debe estar entre 5 y 30 caracteres");
                    } else {
                        txtInput.setBorder(BorderFactory.createLineBorder( Color.LIGHT_GRAY, 2));
                        lblError.setText("");
                    }
                }
            });
            JLabel lblInfo = new JLabel("Nombre del catalogo:");
            lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
            gbc.gridy = 0;
            panelInferior.add(lblInfo, gbc);
            gbc.gridy = 1;
            panelInferior.add(txtInput, gbc);
            gbc.gridy = 2;
            panelInferior.add(lblError, gbc);
            JButton botonNuevo = botonTemplatePrincipal.getBoton("Nuevo Catalogo");
            gbc.gridy = 3;
            panelInferior.add(botonNuevo, gbc);

        }

        Boton botonTemplateSalir = new Boton(50,300,new Color(255, 105, 97),new Font("Arial", Font.BOLD, 18), new Color(204, 45, 39));
        JButton botonSalir = botonTemplateSalir.getBoton("Salir");
        botonSalir.addActionListener(e -> {
            System.exit(0);
        });
        gbc.gridy = 4;
        panelInferior.add(botonSalir, gbc);
    }

}
