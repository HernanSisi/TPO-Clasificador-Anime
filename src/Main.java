import Model.Catalogo;
import UI.VentanaInicio;
import repositorio.EnMemoriaCatalogoRepositorio;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        new VentanaInicio();
        String userHome = System.getProperty("user.home");
        String ruta = userHome + File.separator+"Documents" + File.separator + "AplicacionCatalogoAnimes" + File.separator + "catalogo.anime";
        System.out.println(ruta);
        EnMemoriaCatalogoRepositorio catalogoRepositorio = new EnMemoriaCatalogoRepositorio(ruta);
        Catalogo catalogo = new Catalogo("abc");
        catalogoRepositorio.eliminarCatalogo();
    }
}