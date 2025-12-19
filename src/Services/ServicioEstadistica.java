package Services;

import Model.Anime;
import Model.EstadoAnime;
import Model.Genero;

import java.util.ArrayList;

public class ServicioEstadistica implements IEstadisticasService {
    private IAnimeManagerService animeManager;

    public ServicioEstadistica(IAnimeManagerService animeManager) {
        this.animeManager = animeManager;
    }

    @Override
    public String obtenerEstadistica(String criterio) {
        if (animeManager == null) return "Error: Servicio no inicializado";

        ArrayList<Anime> animes = animeManager.getCatalogo().getAnime();
        IEstrategiaEstadistica estrategia = null;

        try {
            String[] partes = criterio.split(":");
            String tipo = partes[0].toUpperCase();
            switch (tipo) {
                case "GLOBAL_PROMEDIO":
                    estrategia = new PromedioGlobal();
                    break;
                case "GENERO_PROMEDIO":
                    if (partes.length > 1) {
                        Genero g = Genero.valueOf(partes[1]);
                        estrategia = new PromedioPorGenero(g);
                    }
                    break;
                case "GENERO_CANTIDAD":
                    if (partes.length > 1) {
                        Genero g = Genero.valueOf(partes[1]);
                        estrategia = new CantidadDeAnimeGenero(g);
                    }
                    break;
                case "ESTADO_CANTIDAD":
                    if (partes.length > 1) {
                        EstadoAnime e = EstadoAnime.valueOf(partes[1]);
                        estrategia = new CantidadAnimesPorEstado(e);
                    }
                    break;
                case "TOP_GENEROS":
                    int n = partes.length > 1 ? Integer.parseInt(partes[1]) : 3;
                    estrategia = new TopNGenerosMasFrecuente(n);
                    break;
                default:
                    return "Criterio desconocido";
            }
            if (estrategia != null) {
                return estrategia.crumpleCriterio(animes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al calcular estadística";
        }
        return "N/A";
    }
}