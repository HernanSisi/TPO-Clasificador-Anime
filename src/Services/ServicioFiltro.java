package Services;

import Model.Anime;
import java.util.ArrayList;

public class ServicioFiltro implements IBusquedaService {
    private IAnimeManagerService animeManager;

    public ServicioFiltro(IAnimeManagerService animeManager) {
        this.animeManager = animeManager;
    }
    @Override
    public ArrayList<Anime> buscar(String criterio) {
        ArrayList<Anime> animes = this.animeManager.getCatalogo().getAnime();
        IFiltroCriterio filtroNombre = new FiltroPorNombre(criterio);
        return filtroNombre.crumpleCriterio(animes);
    }
    @Override
    public ArrayList<Anime> filtrar(ArrayList<String> criterios) {
        ArrayList<Anime> resultado = this.animeManager.getCatalogo().getAnime();

        if (criterios == null || criterios.isEmpty()) {
            return resultado;
        }
        // Iteramos por cada criterio solicitado
        for (String criterioRaw : criterios) {
            IFiltroCriterio estrategia = null;
            String valor = "";
            if (criterioRaw.toUpperCase().startsWith("NOMBRE:")) {
                valor = criterioRaw.substring(7);
                estrategia = new FiltroPorNombre(valor);

            } else if (criterioRaw.toUpperCase().startsWith("ANHO:")) {
                valor = criterioRaw.substring(5);
                estrategia = new FiltroAnho(valor);

            } else if (criterioRaw.toUpperCase().startsWith("ESTADO:")) {
                valor = criterioRaw.substring(7);
                estrategia = new FiltroEstado(valor);
            }
            if (estrategia != null) {
                resultado = estrategia.crumpleCriterio(resultado);
            }
        }
        return resultado;
    }
}