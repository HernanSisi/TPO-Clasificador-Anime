package UI;

import Model.EstadoAnime;
import Model.Genero;
import Services.IEstadisticasService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PanelEstadisticas extends JPanel {

    private IEstadisticasService estadisticasService;

    // Componentes para actualizar
    private JLabel lblPromedioGlobal;
    private JLabel lblTopGeneros;
    private JTable tablaGeneros;
    private DefaultTableModel modeloTablaGeneros;
    private JPanel panelEstados; // Para barras o etiquetas de estados

    public PanelEstadisticas(IEstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
        setLayout(new BorderLayout());

        inicializarUI();

        // Listener para actualizar datos cuando la pestaña gana el foco
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarEstadisticas();
            }
        });
    }

    private void inicializarUI() {
        // --- TÍTULO ---
        JLabel titulo = new JLabel("Dashboard de Estadísticas");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // --- CONTENIDO CENTRAL (GRIDBAG) ---
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // 1. TARJETA PROMEDIO GLOBAL
        JPanel cardGlobal = crearTarjeta("Promedio Calificación Global");
        lblPromedioGlobal = new JLabel("-");
        lblPromedioGlobal.setFont(new Font("Arial", Font.BOLD, 36));
        lblPromedioGlobal.setForeground(new Color(100, 149, 237)); // Azul bonito
        lblPromedioGlobal.setHorizontalAlignment(SwingConstants.CENTER);
        cardGlobal.add(lblPromedioGlobal, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.5; gbc.weighty = 0.2;
        panelCentral.add(cardGlobal, gbc);

        // 2. TARJETA TOP GÉNEROS
        JPanel cardTop = crearTarjeta("Top 3 Géneros Más Frecuentes");
        lblTopGeneros = new JLabel("-");
        lblTopGeneros.setFont(new Font("Arial", Font.ITALIC, 16));
        lblTopGeneros.setHorizontalAlignment(SwingConstants.CENTER);
        cardTop.add(lblTopGeneros, BorderLayout.CENTER);

        gbc.gridx = 1; gbc.gridy = 0;
        panelCentral.add(cardTop, gbc);

        // 3. TABLA DE DETALLE POR GÉNERO (Promedio y Cantidad)
        JPanel cardTabla = crearTarjeta("Desglose por Género");
        String[] cols = {"Género", "Cantidad Animes", "Promedio Calif."};
        modeloTablaGeneros = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaGeneros = new JTable(modeloTablaGeneros);
        tablaGeneros.setFillsViewportHeight(true);
        cardTabla.add(new JScrollPane(tablaGeneros), BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.weighty = 0.5;
        panelCentral.add(cardTabla, gbc);

        // 4. ESTADÍSTICAS POR ESTADO
        JPanel cardEstados = crearTarjeta("Cantidad por Estado");
        panelEstados = new JPanel(new GridLayout(1, 4, 10, 0)); // Una fila, 4 col
        cardEstados.add(panelEstados, BorderLayout.CENTER);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weighty = 0.2;
        panelCentral.add(cardEstados, gbc);

        add(panelCentral, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                titulo,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));
        p.setBackground(Color.WHITE);
        return p;
    }

    private void cargarEstadisticas() {
        // 1. Promedio Global
        String promGlobal = estadisticasService.obtenerEstadistica("GLOBAL_PROMEDIO");
        lblPromedioGlobal.setText(promGlobal);

        // 2. Top Géneros
        String topGen = estadisticasService.obtenerEstadistica("TOP_GENEROS:3");
        lblTopGeneros.setText("<html><div style='text-align: center;'>" + topGen + "</div></html>");

        // 3. Tabla Por Género
        modeloTablaGeneros.setRowCount(0);
        for (Genero g : Genero.values()) {
            String cant = estadisticasService.obtenerEstadistica("GENERO_CANTIDAD:" + g.name());
            String prom = estadisticasService.obtenerEstadistica("GENERO_PROMEDIO:" + g.name());
            modeloTablaGeneros.addRow(new Object[]{g.name(), cant, prom});
        }

        // 4. Estados (Reconstruimos los labels para actualizar valores)
        panelEstados.removeAll();
        for (EstadoAnime e : EstadoAnime.values()) {
            String cant = estadisticasService.obtenerEstadistica("ESTADO_CANTIDAD:" + e.name());

            JPanel pEstado = new JPanel(new BorderLayout());
            pEstado.setBackground(new Color(245, 245, 245));
            JLabel lblVal = new JLabel(cant);
            lblVal.setFont(new Font("Arial", Font.BOLD, 20));
            lblVal.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel lblNom = new JLabel(e.name());
            lblNom.setFont(new Font("Arial", Font.PLAIN, 10));
            lblNom.setHorizontalAlignment(SwingConstants.CENTER);

            pEstado.add(lblVal, BorderLayout.CENTER);
            pEstado.add(lblNom, BorderLayout.SOUTH);
            panelEstados.add(pEstado);
        }
        panelEstados.revalidate();
        panelEstados.repaint();
    }
}