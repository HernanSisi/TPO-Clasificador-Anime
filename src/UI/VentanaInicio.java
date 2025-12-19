package UI;

import Model.Catalogo;
import repositorio.EnMemoriaCatalogoRepositorio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaInicio extends JFrame {
    EnMemoriaCatalogoRepositorio repositorio;

    public VentanaInicio(EnMemoriaCatalogoRepositorio repositorio) {
        this.repositorio = repositorio;
        setTitle("Clasificador de anime");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel();
        panelSuperior.setPreferredSize(new Dimension(800, 120));
        panelSuperior.setLayout(new GridBagLayout());
        JLabel titulo = new JLabel("Clasificador de anime");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 35));
        panelSuperior.add(titulo);
        add(panelSuperior, BorderLayout.NORTH);

        // PANEL INFERIOR
        JPanel panelInferior = new JPanel();
        panelInferior.setPreferredSize(new Dimension(800, 480));
        panelInferior.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        generarBotones(gbc, panelInferior);
        add(panelInferior, BorderLayout.CENTER);

        setVisible(true);
    }

    public void generarBotones(GridBagConstraints gbc, JPanel panelInferior){
        Boton botonTemplatePrincipal = new Boton(50,300,Color.LIGHT_GRAY,new Font("Arial", Font.BOLD, 18), Color.GRAY);

        if (repositorio.existeCatalogo()) {
            JButton botonCargar = botonTemplatePrincipal.getBoton("Cargar Catalogo");
            botonCargar.addActionListener(e -> abrirMainFrame());
            gbc.gridy = 0;
            panelInferior.add(botonCargar, gbc);

            // BOTON DE ELIMINAR CATALOGO
            JButton botonEliminar = botonTemplatePrincipal.getBoton("Eliminar Catalogo");
            botonEliminar.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Estás seguro de que deseas eliminar el catálogo existente?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    repositorio.eliminarCatalogo();
                    // Recargamos la ventana actual para que muestre la opción de "Nuevo Catalogo"
                    this.dispose();
                    new VentanaInicio(repositorio);
                }
            });

            gbc.gridy = 2;
            panelInferior.add(botonEliminar, gbc);

        } else {
            JTextField txtInput = new JTextField();
            JLabel lblError = new JLabel();
            txtInput.setBorder(BorderFactory.createLineBorder( Color.LIGHT_GRAY, 2));
            txtInput.setPreferredSize(new Dimension(300, 50));
            lblError.setForeground( new Color(255, 105, 97));
            lblError.setFont(new Font("Arial", Font.BOLD, 12));

            // Validación visual del input
            txtInput.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    validarInput(txtInput, lblError);
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

            botonNuevo.addActionListener(e -> {
                if (validarInput(txtInput, lblError)) {
                    String nombre = txtInput.getText();
                    Catalogo nuevoCatalogo = new Catalogo(nombre);

                    // Guardamos el nuevo catálogo en disco
                    repositorio.guardarCatalogo(nuevoCatalogo);

                    // Abrimos la aplicación principal
                    abrirMainFrame();
                } else {
                    JOptionPane.showMessageDialog(this, "Por favor revise el nombre del catálogo.");
                }
            });

            gbc.gridy = 3;
            panelInferior.add(botonNuevo, gbc);
        }

        // BOTON SALIR
        Boton botonTemplateSalir = new Boton(50,300,new Color(255, 105, 97),new Font("Arial", Font.BOLD, 18), new Color(204, 45, 39));
        JButton botonSalir = botonTemplateSalir.getBoton("Salir");
        botonSalir.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        panelInferior.add(botonSalir, gbc);
    }

    /**
     * Método auxiliar para validar el nombre del catálogo
     */
    private boolean validarInput(JTextField txtInput, JLabel lblError) {
        String text = txtInput.getText();
        int length = text.length();
        if (length < 5 || length > 30) {
            txtInput.setBorder(BorderFactory.createLineBorder( new Color(255, 105, 97), 2));
            lblError.setText("Error: El nombre del catalogo debe estar entre 5 y 30 caracteres");
            return false;
        } else {
            txtInput.setBorder(BorderFactory.createLineBorder( Color.LIGHT_GRAY, 2));
            lblError.setText("");
            return true;
        }
    }

    /**
     * Método que realiza la transición al MainFrame
     */
    private void abrirMainFrame() {
        // Instanciamos el MainFrame pasando el repositorio ya configurado
        MainFrame mainFrame = new MainFrame(repositorio);
        mainFrame.mostrarMainFrame();

        // Cerramos la ventana de inicio
        this.dispose();
    }
}