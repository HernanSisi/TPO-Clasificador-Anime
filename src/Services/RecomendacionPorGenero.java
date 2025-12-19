package Services;

import Model.Anime;
import Model.Genero;
import Model.ListaPersonalizada;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RecomendacionPorGenero implements IAlgoritmoRecomendacion {
    private int cantidad;
    private Genero genero;

    public RecomendacionPorGenero(int cantidad, Genero genero) {
        this.cantidad = cantidad;
        this.genero = genero;
    }

    @Override
    public ListaPersonalizada crumpleCriterio(ArrayList<Anime> animes) {
        String nombreLista = "Top " + cantidad + " animes de " + genero.name();
        ListaPersonalizada lista = new ListaPersonalizada(nombreLista);

        if (animes == null || animes.isEmpty()) {
            return lista;
        }

        java.util.List<Anime> resultado = animes.stream()
                .filter(a -> a.getGeneros().contains(this.genero))
                .sorted(Comparator.comparingInt(Anime::getCalificacionDelUsuario).reversed())
                .limit(this.cantidad)
                .collect(Collectors.toList());
        for (Anime a : resultado) {
            lista.agregarAnime(a);
        }

        return lista;
    }
}