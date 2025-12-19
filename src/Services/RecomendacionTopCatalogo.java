package Services;

import Model.Anime;
import Model.ListaPersonalizada;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionTopCatalogo implements IAlgoritmoRecomendacion {
    private int cantidad;
    public RecomendacionTopCatalogo(int cantidad) {
        this.cantidad = cantidad;
    }
    @Override
    public ListaPersonalizada crumpleCriterio(ArrayList<Anime> animes) {
        ListaPersonalizada lista = new ListaPersonalizada("Top " + cantidad + " Mejores Animes");
        if (animes != null && !animes.isEmpty()) {
            List<Anime> topAnimes = animes.stream()
                    // Ordenar por calificacion (Descendente)
                    .sorted(Comparator.comparingInt(Anime::getCalificacionDelUsuario).reversed())
                    // Limitar a la cantidad solicitada
                    .limit(this.cantidad)
                    .collect(Collectors.toList());
            for (Anime a : topAnimes) {
                lista.agregarAnime(a);
            }
        }
        return lista;
    }
}