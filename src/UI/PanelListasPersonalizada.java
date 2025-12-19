package UI;

import Model.*;
import Services.IAnimeManagerService;
import Services.IRecomendacionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PanelListasPersonalizada extends JPanel {

    private IAnimeManagerService managementService;
    private IRecomendacionService recomendacionService;
    private JTable tablaListas;
    private DefaultTableModel modeloTabla;

    // Estilos
    private Boton estiloBotonAccion;
    private Boton estiloBotonGeneral;

    public PanelListasPersonalizada(IAnimeManagerService managementService, IRecomendacionService recomendacionService) {
        this.managementService = managementService;
        this.recomendacionService = recomendacionService;

        this.estiloBotonAccion = new Boton(40, 140, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 14), Color.GRAY);
        this.estiloBotonGeneral = new Boton(40, 200, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 14), Color.GRAY);

        setLayout(new BorderLayout());
        inicializarUI();
        cargarDatosTabla();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                cargarDatosTabla();
            }
        });
    }

    private void inicializarUI() {
        // --- PANEL SUPERIOR ---
        JLabel lblTitulo = new JLabel("Mis Listas Personalizadas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // --- PANEL CENTRAL ---
        String[] columnas = {"Nombre de la Lista", "Cantidad de Animes"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaListas = new JTable(modeloTabla);
        tablaListas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaListas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaListas.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tablaListas);
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        JButton btnCrear = estiloBotonGeneral.getBoton("Crear Nueva Lista");
        JButton btnRecomendar = estiloBotonGeneral.getBoton("Generar Recomendación");
        JButton btnEliminar = estiloBotonAccion.getBoton("Eliminar Lista");
        JButton btnModificar = estiloBotonAccion.getBoton("Modificar Lista");
        JButton btnVerContenido = estiloBotonAccion.getBoton("Ver Contenido");

        btnCrear.addActionListener(e -> crearNuevaLista());
        btnRecomendar.addActionListener(e -> abrirDialogoRecomendacion());
        btnEliminar.addActionListener(e -> eliminarListaSeleccionada());
        btnModificar.addActionListener(e -> abrirDialogoModificar(true));
        btnVerContenido.addActionListener(e -> abrirDialogoModificar(false));

        panelBotones.add(btnCrear);
        panelBotones.add(btnRecomendar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnVerContenido);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        Catalogo catalogo = managementService.getCatalogo();
        if (catalogo != null && catalogo.getListasPersonalizadas() != null) {
            for (ListaPersonalizada lista : catalogo.getListasPersonalizadas()) {
                modeloTabla.addRow(new Object[]{lista.getNombre(), lista.getCantidadDeAnimes()});
            }
        }
    }

    private void crearNuevaLista() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre de la nueva lista:", "Nueva Lista", JOptionPane.QUESTION_MESSAGE);
        if (nombre != null && !nombre.trim().isEmpty()) {
            ListaPersonalizada nueva = new ListaPersonalizada(nombre.trim());
            managementService.getCatalogo().addListaPersonalizada(nueva);
            managementService.guardarCambios();
            cargarDatosTabla();
        }
    }

    private void eliminarListaSeleccionada() {
        int fila = tablaListas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una lista para eliminar.");
            return;
        }
        Catalogo catalogo = managementService.getCatalogo();
        ListaPersonalizada lista = catalogo.getListaPersonalizada(fila);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar '" + lista.getNombre() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            catalogo.removeListaPersonalizada(lista);
            managementService.guardarCambios();
            cargarDatosTabla();
        }
    }

    // --- LOGICA MODIFICADA PARA CONSTRUIR EL STRING DE CRITERIO ---
    private void abrirDialogoRecomendacion() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Generar Recomendación", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Selección de Tipo
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Tipo de Recomendación:"), gbc);
        String[] criterios = {"Top Catalogo", "Por Género"};
        JComboBox<String> cmbCriterio = new JComboBox<>(criterios);
        gbc.gridx = 1;
        dialog.add(cmbCriterio, gbc);

        // Selección de Género
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Género:"), gbc);
        JComboBox<Genero> cmbGenero = new JComboBox<>(Genero.values());
        cmbGenero.setEnabled(false);
        gbc.gridx = 1;
        dialog.add(cmbGenero, gbc);

        cmbCriterio.addActionListener(e -> {
            boolean esPorGenero = "Por Género".equals(cmbCriterio.getSelectedItem());
            cmbGenero.setEnabled(esPorGenero);
        });

        // Selección de Cantidad
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Cantidad de Animes:"), gbc);
        JSpinner spinCantidad = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
        gbc.gridx = 1;
        dialog.add(spinCantidad, gbc);

        JButton btnGenerar = new JButton("Generar Lista");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(btnGenerar, gbc);

        btnGenerar.addActionListener(e -> {
            int cantidad = (int) spinCantidad.getValue();
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(dialog, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Construcción del String Criterio
            String tipoSeleccionado = (String) cmbCriterio.getSelectedItem();
            StringBuilder criterioString = new StringBuilder();

            if ("Por Género".equals(tipoSeleccionado)) {
                Genero generoSeleccionado = (Genero) cmbGenero.getSelectedItem();
                // Construimos "POR_GENERO:SHONEN:5"
                criterioString.append("POR_GENERO:")
                        .append(generoSeleccionado.name())
                        .append(":")
                        .append(cantidad);
            } else {
                // Construimos "TOP_CATALOGO:5"
                criterioString.append("TOP_CATALOGO:")
                        .append(cantidad);
            }

            // Llamada al servicio con la nueva firma
            ListaPersonalizada nuevaLista = recomendacionService.generarRecomendaciones(
                    criterioString.toString(),
                    managementService.getCatalogo().getAnime()
            );

            if (nuevaLista.getCantidadDeAnimes() == 0) {
                JOptionPane.showMessageDialog(dialog, "No se encontraron animes para ese criterio.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                managementService.getCatalogo().addListaPersonalizada(nuevaLista);
                managementService.guardarCambios();
                cargarDatosTabla();
                JOptionPane.showMessageDialog(dialog, "Lista generada exitosamente con " + nuevaLista.getCantidadDeAnimes() + " animes.");
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    // ... (El resto de métodos: abrirDialogoModificar y la clase DialogoDetalleLista se mantienen iguales)
    private void abrirDialogoModificar(boolean esModificacion) {
        int fila = tablaListas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una lista primero.");
            return;
        }
        ListaPersonalizada lista = managementService.getCatalogo().getListaPersonalizada(fila);
        new DialogoDetalleLista(lista, esModificacion).setVisible(true);
    }

    private class DialogoDetalleLista extends JDialog {
        private ListaPersonalizada lista;
        private DefaultTableModel modeloAnimesLista;
        private JTable tablaAnimesLista;
        private JTextField txtNombreLista;

        public DialogoDetalleLista(ListaPersonalizada lista, boolean editable) {
            super((Frame) SwingUtilities.getWindowAncestor(PanelListasPersonalizada.this),
                    editable ? "Modificar Lista" : "Ver Contenido de Lista", true);
            this.lista = lista;
            setSize(900, 500);
            setLocationRelativeTo(PanelListasPersonalizada.this);
            setLayout(new BorderLayout());

            JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelNombre.add(new JLabel("Nombre de la Lista: "));
            txtNombreLista = new JTextField(lista.getNombre(), 20);
            txtNombreLista.setEditable(editable);
            panelNombre.add(txtNombreLista);

            if (editable) {
                JButton btnRenombrar = new JButton("Renombrar");
                btnRenombrar.addActionListener(e -> {
                    String nuevoNombre = txtNombreLista.getText().trim();
                    if (!nuevoNombre.isEmpty()) {
                        lista.setNombre(nuevoNombre);
                        managementService.guardarCambios();
                        cargarDatosTabla();
                        JOptionPane.showMessageDialog(this, "Nombre actualizado.");
                    }
                });
                panelNombre.add(btnRenombrar);
            }
            add(panelNombre, BorderLayout.NORTH);

            String[] cols = {"Título", "Año", "Estudio", "Géneros", "Estado", "Calif.", "Detalle"};
            modeloAnimesLista = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int row, int col) { return false; }
            };
            tablaAnimesLista = new JTable(modeloAnimesLista);
            tablaAnimesLista.getColumnModel().getColumn(0).setPreferredWidth(150);
            tablaAnimesLista.getColumnModel().getColumn(3).setPreferredWidth(200);
            tablaAnimesLista.getColumnModel().getColumn(5).setPreferredWidth(40);
            add(new JScrollPane(tablaAnimesLista), BorderLayout.CENTER);
            cargarAnimesDeLista();

            if (editable) {
                JPanel panelAcciones = new JPanel(new FlowLayout());
                Boton estiloBtn = new Boton(35, 180, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 12), Color.GRAY);
                JButton btnAgregar = estiloBtn.getBoton("Agregar del Catálogo");
                JButton btnQuitar = estiloBtn.getBoton("Quitar de la Lista");
                btnQuitar.addActionListener(e -> quitarAnimeDeLista());
                btnAgregar.addActionListener(e -> agregarAnimeDelCatalogo());
                panelAcciones.add(btnAgregar);
                panelAcciones.add(btnQuitar);
                add(panelAcciones, BorderLayout.SOUTH);
            }
        }
        private void cargarAnimesDeLista() {
            modeloAnimesLista.setRowCount(0);
            for (Anime a : lista.getAnime()) {
                String categoriasStr = a.getGeneros().stream().map(Enum::name).collect(Collectors.joining(", "));
                String detalle = (a instanceof AnimeSerie) ? ((AnimeSerie) a).getCantidadCapitulos() + " caps" : ((AnimePelicula) a).getDuracion() + " min";
                modeloAnimesLista.addRow(new Object[]{a.gettitulo(), a.getAnhoDeLanzamiento(), a.getEstudio(), categoriasStr, a.getEstado(), a.getCalificacionDelUsuario(), detalle});
            }
        }
        private void quitarAnimeDeLista() {
            int fila = tablaAnimesLista.getSelectedRow();
            if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un anime."); return; }
            lista.removeAnime(lista.getAnime(fila));
            managementService.guardarCambios();
            cargarAnimesDeLista();
            cargarDatosTabla();
        }
        private void agregarAnimeDelCatalogo() {
            Catalogo catalogoGeneral = managementService.getCatalogo();
            ArrayList<Anime> disponibles = new ArrayList<>();
            for (Anime a : catalogoGeneral.getAnime()) if (!lista.getAnime().contains(a)) disponibles.add(a);
            if (disponibles.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay más animes para agregar."); return; }
            JComboBox<String> combo = new JComboBox<>();
            for (Anime a : disponibles) combo.addItem(a.gettitulo() + " (" + a.getAnhoDeLanzamiento() + ")");
            int result = JOptionPane.showConfirmDialog(this, combo, "Agregar Anime", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                lista.agregarAnime(disponibles.get(combo.getSelectedIndex()));
                managementService.guardarCambios();
                cargarAnimesDeLista();
                cargarDatosTabla();
            }
        }
    }
}