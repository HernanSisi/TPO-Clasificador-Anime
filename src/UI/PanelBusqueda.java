package UI;

import Model.*;
import Services.IAnimeManagerService;
import Services.IBusquedaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelBusqueda extends JPanel {

    private IBusquedaService busquedaService;
    private IAnimeManagerService managementService;
    private ArrayList<Anime> animesEncontrados;

    // Componentes UI
    private JTextField txtBusquedaNombre;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar;
    private JLabel lblFiltrosActivos;

    // Variables para filtros activos
    private String filtroRangoAnios = "";
    private List<EstadoAnime> filtrosEstados = new ArrayList<>();

    public PanelBusqueda(IBusquedaService busquedaService, IAnimeManagerService managementService) {
        this.busquedaService = busquedaService;
        this.managementService = managementService;
        this.animesEncontrados = new ArrayList<>();

        setLayout(new BorderLayout());
        inicializarUI();
    }

    private void inicializarUI() {
        Boton estiloBotonesSuperiores = new Boton(40, 160, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 14), Color.GRAY);
        Boton estiloBotonGuardar = new Boton(40, 260, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 14), Color.GRAY);

        //PANEL SUPERIOR
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        //Buscador Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14)); // Unificamos fuente de labels también
        panelSuperior.add(lblNombre, gbc);

        txtBusquedaNombre = new JTextField(15);
        txtBusquedaNombre.setPreferredSize(new Dimension(150, 30)); // Altura acorde a los botones
        gbc.gridx = 1;
        panelSuperior.add(txtBusquedaNombre, gbc);

        //Botón Configurar Filtros
        JButton btnFiltros = estiloBotonesSuperiores.getBoton("Configurar Filtros");
        gbc.gridx = 2;
        panelSuperior.add(btnFiltros, gbc);

        //Botón Buscar y Filtrar
        JButton btnBuscar = estiloBotonesSuperiores.getBoton("Buscar y Filtrar");
        gbc.gridx = 3;
        panelSuperior.add(btnBuscar, gbc);
        lblFiltrosActivos = new JLabel("Sin filtros aplicados");
        lblFiltrosActivos.setForeground(Color.GRAY);
        lblFiltrosActivos.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 0, 5);
        panelSuperior.add(lblFiltrosActivos, gbc);

        add(panelSuperior, BorderLayout.NORTH);

        // PANEL Tabla
        String[] columnas = {"Título", "Año", "Estudio", "Categoría", "Estado", "Calif.", "Detalle"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1 || columnIndex == 5) {
                    return Integer.class; // Para ordenamiento numérico
                }
                return Object.class;
            }
        };

        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setAutoCreateRowSorter(true);
        tablaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        // Ajuste visual de anchos
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(150); // Título
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(150); // Categoría
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(40);  // Calif

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        add(scrollPane, BorderLayout.CENTER);

        // PANEL INFERIOR
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Botón Guardar
        btnGuardar = estiloBotonGuardar.getBoton("Guardar Búsqueda en Lista");
        btnGuardar.setEnabled(false); // Gris/Bloqueado por defecto

        panelInferior.add(btnGuardar);
        add(panelInferior, BorderLayout.SOUTH);

        // LISTENERS
        btnFiltros.addActionListener(e -> abrirVentanaFiltros());
        btnBuscar.addActionListener(e -> ejecutarBusqueda());
        btnGuardar.addActionListener(e -> guardarListaPersonalizada());
    }

    private void abrirVentanaFiltros() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Configurar Filtros Avanzados", true);
        dialog.setSize(400, 380); // Un poco más alto para que entren bien los botones
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // SECCIÓN AÑOS
        JLabel lblAnios = new JLabel("Rango de Años:");
        lblAnios.setFont(new Font("Arial", Font.BOLD, 12));
        dialog.add(lblAnios, gbc);
        JPanel panelAnios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        int anioActual = Year.now().getValue();
        JSpinner spinInicio = new JSpinner(new SpinnerNumberModel(2000, 1900, anioActual, 1));
        JSpinner spinFin = new JSpinner(new SpinnerNumberModel(anioActual, 1900, anioActual, 1));
        panelAnios.add(new JLabel("Desde:"));
        panelAnios.add(spinInicio);
        panelAnios.add(new JLabel("Hasta:"));
        panelAnios.add(spinFin);
        gbc.gridy++;
        dialog.add(panelAnios, gbc);

        //SECCIÓN ESTADOS
        gbc.gridy++;
        JLabel lblEstados = new JLabel("Estados (Seleccione uno o varios):");
        lblEstados.setFont(new Font("Arial", Font.BOLD, 12));
        dialog.add(lblEstados, gbc);
        JPanel panelEstados = new JPanel(new GridLayout(0, 2, 5, 5));
        panelEstados.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        List<JCheckBox> checksEstados = new ArrayList<>();
        for (EstadoAnime est : EstadoAnime.values()) {
            JCheckBox cb = new JCheckBox(est.name());
            cb.setFont(new Font("Arial", Font.PLAIN, 12));
            if (filtrosEstados.contains(est)) {
                cb.setSelected(true);
            }
            checksEstados.add(cb);
            panelEstados.add(cb);
        }
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        dialog.add(new JScrollPane(panelEstados), gbc);
        Boton estiloDialogo = new Boton(40, 200, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 14), Color.GRAY);
        JButton btnAplicar = estiloDialogo.getBoton("Aplicar Filtros");
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; // Centrado
        gbc.insets = new Insets(15, 10, 15, 10);
        dialog.add(btnAplicar, gbc);

        //Guardar filtros seleccionados
        btnAplicar.addActionListener(e -> {
            int inicio = (int) spinInicio.getValue();
            int fin = (int) spinFin.getValue();
            if (inicio >= fin) {
                JOptionPane.showMessageDialog(dialog,
                        "El año de inicio debe ser menor al año de fin.",
                        "Error en Años", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.filtroRangoAnios = inicio + "-" + fin;
            this.filtrosEstados.clear();
            for (JCheckBox cb : checksEstados) {
                if (cb.isSelected()) {
                    this.filtrosEstados.add(EstadoAnime.valueOf(cb.getText()));
                }
            }
            actualizarLabelInfo();
            dialog.dispose();
        });
        dialog.setVisible(true);
    }

    private void actualizarLabelInfo() {
        StringBuilder info = new StringBuilder("Filtros: ");
        boolean hayFiltro = false;
        if (!filtroRangoAnios.isEmpty()) {
            info.append("[Años: ").append(filtroRangoAnios).append("] ");
            hayFiltro = true;
        }
        if (!filtrosEstados.isEmpty()) {
            info.append("[Estados: ");
            for (EstadoAnime e : filtrosEstados) info.append(e.name()).append(" ");
            info.append("] ");
            hayFiltro = true;
        }
        lblFiltrosActivos.setText(hayFiltro ? info.toString() : "Sin filtros configurados");
    }

    private void ejecutarBusqueda() {
        ArrayList<String> criterios = new ArrayList<>();
        String nombre = txtBusquedaNombre.getText().trim();
        if (!nombre.isEmpty()) {
            criterios.add("NOMBRE:" + nombre);
        }
        if (filtroRangoAnios != null && !filtroRangoAnios.isEmpty()) {
            criterios.add("ANHO:" + filtroRangoAnios);
        }
        if (!filtrosEstados.isEmpty()) {
            String estadosStr = filtrosEstados.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            criterios.add("ESTADO:" + estadosStr);
        }
        animesEncontrados = busquedaService.filtrar(criterios);
        actualizarTablaResultados();

        btnGuardar.setEnabled(animesEncontrados != null && !animesEncontrados.isEmpty());

        if (animesEncontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.");
        }
    }

    private void actualizarTablaResultados() {
        modeloTabla.setRowCount(0);
        if (animesEncontrados != null) {
            for (Anime a : animesEncontrados) {
                String categoriasStr = a.getGeneros().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));

                String detalle = (a instanceof AnimeSerie)
                        ? ((AnimeSerie) a).getCantidadCapitulos() + " caps"
                        : ((AnimePelicula) a).getDuracion() + " min";

                Object[] fila = {
                        a.gettitulo(),
                        a.getAnhoDeLanzamiento(),
                        a.getEstudio(),
                        categoriasStr,
                        a.getEstado(),
                        a.getCalificacionDelUsuario(),
                        detalle
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void guardarListaPersonalizada() {
        if (animesEncontrados == null || animesEncontrados.isEmpty()) return;

        String nombreLista = JOptionPane.showInputDialog(this,
                "Nombre para la nueva lista personalizada:",
                "Guardar Resultados", JOptionPane.QUESTION_MESSAGE);

        if (nombreLista != null && !nombreLista.trim().isEmpty()) {
            ListaPersonalizada nuevaLista = new ListaPersonalizada(nombreLista.trim());
            for (Anime a : animesEncontrados) {
                nuevaLista.agregarAnime(a);
            }
            managementService.getCatalogo().addListaPersonalizada(nuevaLista);
            JOptionPane.showMessageDialog(this, "Lista guardada exitosamente.");
        }
    }
}