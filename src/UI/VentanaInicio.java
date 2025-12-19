package UI;

import javax.swing.*;
import java.awt.*;

public class VentanaInicio extends JFrame {
    public VentanaInicio() {
        setTitle("Clasificador de anime");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Clasificador de anime");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 35));

        JPanel panelSuperior = new JPanel();
//        panelSuperior.setBackground(Color.CYAN);
        panelSuperior.setPreferredSize(new Dimension(800, 120));
        panelSuperior.setLayout(new GridBagLayout());
        panelSuperior.add(titulo);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelInferior = new JPanel();
//        panelInferior.setBackground(Color.orange);
        panelInferior.setPreferredSize(new Dimension(800, 480));
        panelInferior.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        generarBotones(gbc, panelInferior);
        add(panelInferior, BorderLayout.CENTER);

    }
    public void generarBotones(GridBagConstraints gbc, JPanel panelInferior){
        Boton botonTemplatePrincipal = new Boton(50,300,Color.LIGHT_GRAY,new Font("Arial", Font.BOLD, 18), Color.GRAY);
        JButton botonCargar = botonTemplatePrincipal.getBoton("Cargar Catalogo");
        gbc.gridy = 0;
        panelInferior.add(botonCargar, gbc);
        JButton botonNuevo = botonTemplatePrincipal.getBoton("Nuevo Catalogo");
        gbc.gridy = 1;
        panelInferior.add(botonNuevo, gbc);
        JButton botonEliminar = botonTemplatePrincipal.getBoton("Eliminar Catalogo");
        gbc.gridy = 2;
        panelInferior.add(botonEliminar, gbc);

        Boton botonTemplateSalir = new Boton(50,300,new Color(255, 105, 97),new Font("Arial", Font.BOLD, 18), new Color(204, 45, 39));
        JButton botonSalir = botonTemplateSalir.getBoton("Salir");
        botonSalir.addActionListener(e -> {
            System.exit(0);
        });
        gbc.gridy = 3;
        panelInferior.add(botonSalir, gbc);
    }
}
