package UI;

import Model.*;
import Services.IAnimeManagerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PanelGestionAnimes extends JPanel {
    private IAnimeManagerService servicioManagement;
    private JTable tablaAnimes;
    private DefaultTableModel modeloTabla;

    public PanelGestionAnimes(IAnimeManagerService servicioManagement) {
        this.servicioManagement = servicioManagement;
        setLayout(new BorderLayout());
        inicializarUI();
        cargarDatosTabla();
    }

    private void inicializarUI() {
        // Panel tabla principal
        String[] columnas = {"Título", "Año", "Estudio", "Categoría", "Estado", "Calif.", "Detalle (Cap/Dur)"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaAnimes = new JTable(modeloTabla);
        tablaAnimes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaAnimes.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaAnimes.getColumnModel().getColumn(3).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tablaAnimes);
        add(scrollPane, BorderLayout.CENTER);

        // botonera- nuevo modificar y elimar
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        Boton templateBoton = new Boton(40, 120, Color.LIGHT_GRAY, new Font("Arial", Font.BOLD, 14), Color.GRAY);

        JButton btnAgregar = templateBoton.getBoton("Agregar");
        JButton btnEditar = templateBoton.getBoton("Editar");
        JButton btnEliminar = templateBoton.getBoton("Eliminar");

        btnAgregar.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarSeleccionado());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        Catalogo catalogo = servicioManagement.getCatalogo();
        if (catalogo != null && catalogo.getAnime() != null) {
            for (Anime a : catalogo.getAnime()) {
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

    private void eliminarSeleccionado() {
        int fila = tablaAnimes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un anime para eliminar.");
            return;
        }

        Catalogo catalogo = servicioManagement.getCatalogo();
        Anime animeAElminiar = catalogo.getAnime(fila);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar '" + animeAElminiar.gettitulo() + "'?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            servicioManagement.eliminarAnime(animeAElminiar);
            cargarDatosTabla();
        }
    }

    private void editarSeleccionado() {
        int fila = tablaAnimes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un anime para editar.");
            return;
        }
        Catalogo catalogo = servicioManagement.getCatalogo();
        Anime animeAEditar = catalogo.getAnime(fila);
        abrirFormulario(animeAEditar);
    }

    private void abrirFormulario(Anime animeEditar) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                (animeEditar == null) ? "Gestión de Anime" : "Editar Anime", true);
        dialog.setSize(500, 650);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // Más espaciado
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //TÍTULO SUPERIOR
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa las dos columnas
        JLabel lblTituloHeader = new JLabel((animeEditar == null) ? "Agregar Anime" : "Editar Anime");
        lblTituloHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloHeader.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(lblTituloHeader, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;

        //Campos del Formulario
        JTextField txtTitulo = new JTextField(20);
        int currentYear = Year.now().getValue();
        JSpinner spinAnho = new JSpinner(new SpinnerNumberModel(currentYear, 1900, Year.now().getValue(), 1));

        JComboBox<Estudio> cmbEstudio = new JComboBox<>(Estudio.values());
        JComboBox<EstadoAnime> cmbEstado = new JComboBox<>(EstadoAnime.values());
        JSpinner spinCalif = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        //SELECCIÓN DE GÉNEROS
        JPanel panelCheckBoxes = new JPanel();
        panelCheckBoxes.setLayout(new GridLayout(0, 2, 5, 5));
        panelCheckBoxes.setBackground(Color.WHITE);
        List<JCheckBox> listaChecks = new ArrayList<>();
        for (Genero g : Genero.values()) {
            JCheckBox cb = new JCheckBox(g.name());
            cb.setBackground(Color.WHITE);
            listaChecks.add(cb);
            panelCheckBoxes.add(cb);
        }
        JScrollPane scrollGeneros = new JScrollPane(panelCheckBoxes);
        scrollGeneros.setPreferredSize(new Dimension(250, 120));
        scrollGeneros.setBorder(BorderFactory.createTitledBorder("Seleccione Categorías"));

        //TIPO DE ANIME O SERIE O PELICULA
        JRadioButton rbtnSerie = new JRadioButton("Serie", true);
        JRadioButton rbtnPelicula = new JRadioButton("Película");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbtnSerie);
        grupoTipo.add(rbtnPelicula);
        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTipo.add(rbtnSerie);
        panelTipo.add(rbtnPelicula);

        //CANTIDAD DE CAPITULOS O DURACION EN MINUTOS DE LA PELICULA EN CUESTION
        JLabel lblExtra = new JLabel("Capítulos:");
        JSpinner spinExtra = new JSpinner(new SpinnerNumberModel(12, 1, 10000, 1));

        rbtnSerie.addActionListener(e -> lblExtra.setText("Capítulos:"));
        rbtnPelicula.addActionListener(e -> lblExtra.setText("Duración (min):"));

        //COMPLETAMOS LOS DATOS SI HAY Q EDITAR UN ANIME
        if (animeEditar != null) {
            txtTitulo.setText(animeEditar.gettitulo());
            spinAnho.setValue(animeEditar.getAnhoDeLanzamiento());
            cmbEstudio.setSelectedItem(animeEditar.getEstudio());
            cmbEstado.setSelectedItem(animeEditar.getEstado());
            spinCalif.setValue(animeEditar.getCalificacionDelUsuario());

            // MARCA LOS GENEROS QUE CORRESPONDEN
            ArrayList<Genero> generosActuales = animeEditar.getGeneros();
            for (JCheckBox cb : listaChecks) {
                try {
                    Genero gCbox = Genero.valueOf(cb.getText());
                    if (generosActuales.contains(gCbox)) {
                        cb.setSelected(true);
                    }
                } catch (IllegalArgumentException ignored) {}
            }
            if (animeEditar instanceof AnimeSerie) {
                rbtnSerie.setSelected(true);
                lblExtra.setText("Capítulos:");
                spinExtra.setValue(((AnimeSerie) animeEditar).getCantidadCapitulos());
            } else if (animeEditar instanceof AnimePelicula) {
                rbtnPelicula.setSelected(true);
                lblExtra.setText("Duración (min):");
                spinExtra.setValue(((AnimePelicula) animeEditar).getDuracion());
            }
            rbtnSerie.setEnabled(false);
            rbtnPelicula.setEnabled(false);
        }

        // AGREGAMOS LOS COMPONENTES A LA GRILLA PARA MOSTRARLOS
        //Titulo
        gbc.gridx = 0; dialog.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; dialog.add(txtTitulo, gbc);

        // Año
        gbc.gridy++; gbc.gridx = 0; dialog.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1; dialog.add(spinAnho, gbc);

        // Estudio
        gbc.gridy++; gbc.gridx = 0; dialog.add(new JLabel("Estudio:"), gbc);
        gbc.gridx = 1; dialog.add(cmbEstudio, gbc);

        // Estado
        gbc.gridy++; gbc.gridx = 0; dialog.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; dialog.add(cmbEstado, gbc);

        // Calificacion
        gbc.gridy++; gbc.gridx = 0; dialog.add(new JLabel("Calificación (1-5):"), gbc);
        gbc.gridx = 1; dialog.add(spinCalif, gbc);

        // Categorias (Checkboxes) - Ocupa 2 filas de altura visualmente si es necesario
        gbc.gridy++; gbc.gridx = 0;
        gbc.gridwidth = 2; // Ocupa todo el ancho
        dialog.add(scrollGeneros, gbc);
        gbc.gridwidth = 1; // Restauramos

        // Tipo
        gbc.gridy++; gbc.gridx = 0; dialog.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; dialog.add(panelTipo, gbc);

        // Extra
        gbc.gridy++; gbc.gridx = 0; dialog.add(lblExtra, gbc);
        gbc.gridx = 1; dialog.add(spinExtra, gbc);

        // Botón Guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(100, 149, 237)); // Un color azul suave
        btnGuardar.setForeground(Color.WHITE);

        gbc.gridy++; gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        dialog.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            // VALIDAMOS LOS DATOS DEL FORMULARIO
            if (txtTitulo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El título es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //VALIDACION DE LOS GENEROS
            ArrayList<Genero> generosSeleccionados = new ArrayList<>();
            for (JCheckBox cb : listaChecks) {
                if (cb.isSelected()) {
                    generosSeleccionados.add(Genero.valueOf(cb.getText()));
                }
            }
            if (generosSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Debe seleccionar al menos una categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int anhoIngresado = (int) spinAnho.getValue();
            /*
            //VALIDACION DEL AÑO
            int anhoActualSistema = Year.now().getValue();
            if (anhoIngresado > anhoActualSistema) {
                JOptionPane.showMessageDialog(dialog, "El año no puede ser futuro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (anhoIngresado < 1900) {
                JOptionPane.showMessageDialog(dialog, "El año no puede ser anterior a 1900.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            */
            // Datos finales
            String titulo = txtTitulo.getText();
            Estudio estudio = (Estudio) cmbEstudio.getSelectedItem();
            EstadoAnime estado = (EstadoAnime) cmbEstado.getSelectedItem();
            int calif = (int) spinCalif.getValue();
            int extra = (int) spinExtra.getValue();

            Anime animeNuevo;
            if (rbtnSerie.isSelected()) {
                animeNuevo = new AnimeSerie(titulo, anhoIngresado, calif, estado, generosSeleccionados, estudio, extra);
            } else {
                animeNuevo = new AnimePelicula(titulo, anhoIngresado, calif, estado, generosSeleccionados, estudio, extra);
            }

            if (animeEditar == null) {
                servicioManagement.agregarAnime(animeNuevo);
            } else {
                servicioManagement.actualizarAnime(animeNuevo, animeEditar);
            }

            cargarDatosTabla();
            dialog.dispose();
        });

        dialog.setVisible(true);
    }
}