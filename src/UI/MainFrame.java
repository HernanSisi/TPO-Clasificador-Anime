package UI;

import Services.IAnimeManagerService;
import Services.IBusquedaService;
import Services.IEstadisticasService;
import Services.IRecomendacionService;
import repositorio.EnMemoriaCatalogoRepositorio;

import java.io.File;

public class MainFrame {
    IAnimeManagerService servicioManagement;
    IBusquedaService busqueda;
    IEstadisticasService estadisticas;
    IRecomendacionService recomendacion;
    public static void main(String[] args) {
        String ruta = System.getProperty("user.home") + File.separator+"Documents" + File.separator + "AplicacionCatalogoAnimes" + File.separator + "catalogo.anime";
        EnMemoriaCatalogoRepositorio catalogoRepositorio = new EnMemoriaCatalogoRepositorio(ruta);
        new VentanaInicio(catalogoRepositorio);
        System.out.println(catalogoRepositorio.existeCatalogo());
    }
    public void Iniciar(){}
    public void mostrarMainFrame(){}
}
