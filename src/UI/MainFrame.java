package UI;

import Model.Catalogo;
import Services.*;
import repositorio.EnMemoriaCatalogoRepositorio;
import repositorio.ICatalogoRepositorio;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {

    // Servicios
    private IAnimeManagerService servicioManagement;
    private IBusquedaService busqueda;
    private IEstadisticasService estadisticas;
    private IRecomendacionService recomendacion;

    // Repositorio
    private ICatalogoRepositorio repositorio;

    public MainFrame(ICatalogoRepositorio repositorio) {
        this.repositorio = repositorio;
        setTitle("Clasificador de Anime - Panel Principal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Iniciar();
        inicializarComponentes();
    }

    // Inicializa los servicios del sistema.
    public void Iniciar() {
        // Inicializamos el servicio de manejo de archivos principal
        this.servicioManagement = new ServicioManagment(this.repositorio);
        // Inicializamos los otros servicios
        this.busqueda = new ServicioFiltro(this.servicioManagement);
        this.estadisticas = new ServicioEstadistica(this.servicioManagement);
        this.recomendacion = new ServicioRecomendacion();
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(60, 63, 65));
        Catalogo catalogo = repositorio.cargarCatalogo();
        JLabel titulo = new JLabel("Panel de Control - " + catalogo.getNombre());
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelSuperior.add(titulo);
        add(panelSuperior, BorderLayout.NORTH);
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: Mis Animes
        PanelGestionAnimes panelGestion = new PanelGestionAnimes(this.servicioManagement);
        tabbedPane.addTab("Mis Animes", panelGestion);

        // Pestaña 2: Búsqueda y Filtros
        PanelBusqueda panelBusqueda = new PanelBusqueda(this.busqueda, this.servicioManagement);
        tabbedPane.addTab("Buscar", panelBusqueda);

        // Pestaña 3: Estadísticas
        PanelEstadisticas panelEstadisticas = new PanelEstadisticas(this.estadisticas);
        tabbedPane.addTab("Estadísticas", panelEstadisticas);

        // Pestaña 4: Listas Personalizadas
        PanelListasPersonalizada panelListas = new PanelListasPersonalizada(servicioManagement,recomendacion);
        tabbedPane.addTab("Listas Personalizadas", panelListas);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void mostrarMainFrame() {
        this.setVisible(true);
    }

    public static void main(String[] args) {
        String ruta = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "AplicacionCatalogoAnimes" + File.separator + "catalogo.anime";
        EnMemoriaCatalogoRepositorio catalogoRepositorio = new EnMemoriaCatalogoRepositorio(ruta);
        new VentanaInicio(catalogoRepositorio);

        System.out.println("Sistema iniciado. Repositorio: " + (catalogoRepositorio.existeCatalogo() ? "Detectado" : "No encontrado"));
    }
    private void pestanhGestion() {
        JPanel panelGestion = new JPanel();
    }
}