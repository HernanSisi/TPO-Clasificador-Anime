package Services;

import Model.Anime;
import Model.Genero;
import Model.ListaPersonalizada;

import java.util.ArrayList;

public class ServicioRecomendacion implements IRecomendacionService {
    private IAnimeManagerService animeManager;

    public ServicioRecomendacion(IAnimeManagerService animeManager) {
        this.animeManager = animeManager;
    }
    @Override
    public ListaPersonalizada generarRecomendaciones(String criterio) {
        ArrayList<Anime> animes = this.animeManager.getCatalogo().getAnime();
        IAlgoritmoRecomendacion estrategia = null;
        try {
            if (criterio == null || criterio.isEmpty()) {
                return new ListaPersonalizada("Recomendación Vacía");
            }

            String[] partes = criterio.split(":");
            String tipo = partes[0].toUpperCase();

            if (tipo.equals("TOP_GLOBAL") && partes.length >= 2) {
                int cantidad = Integer.parseInt(partes[1]);
                estrategia = new RecomendacionTopCatalogo(cantidad);
            } else if (tipo.equals("POR_GENERO") && partes.length >= 3) {
                String generoStr = partes[1].toUpperCase();
                int cantidad = Integer.parseInt(partes[2]);
                Genero genero = Genero.valueOf(generoStr);
                estrategia = new RecomendacionPorGenero(cantidad, genero);
            }

            if (estrategia != null) {
                return estrategia.crumpleCriterio(animes);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error al parsear el criterio de recomendación: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Género no válido: " + e.getMessage());
        }

        return new ListaPersonalizada("Error en recomendación");
    }
}