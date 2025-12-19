package Services;

import Model.Anime;
import Model.Genero;
import Model.ListaPersonalizada;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionPorGenero implements IAlgoritmoRecomendacion {
    private Genero genero;
    private int cantidad;
    public RecomendacionPorGenero(Genero genero, int cantidad) {
        this.genero = genero;
        this.cantidad = cantidad;
    }
    @Override
    public ListaPersonalizada crumpleCriterio(ArrayList<Anime> animes) {
        ListaPersonalizada lista = new ListaPersonalizada("Recomendación: " + genero.name());

        if (animes != null && !animes.isEmpty()) {
            List<Anime> animesFiltrados = animes.stream()
                    // Filtrar por el genero seleccionado
                    .filter(a -> a.getGeneros().contains(this.genero))
                    // Ordenar por calificación (los mejores primero)
                    .sorted(Comparator.comparingInt(Anime::getCalificacionDelUsuario).reversed())
                    .limit(this.cantidad)
                    .collect(Collectors.toList());
            for (Anime a : animesFiltrados) {
                lista.agregarAnime(a);
            }
        }
        return lista;
    }
}