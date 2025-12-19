package Services;

import Model.Anime;
import Model.ListaPersonalizada;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RecomendacionTopCatalogo implements IAlgoritmoRecomendacion {
    private int cantidad;

    public RecomendacionTopCatalogo(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public ListaPersonalizada crumpleCriterio(ArrayList<Anime> animes) {
        String nombreLista = "Top " + cantidad + " Global del Catálogo";
        ListaPersonalizada lista = new ListaPersonalizada(nombreLista);

        if (animes == null || animes.isEmpty()) {
            return lista;
        }
        // ordenados y limitamos
        java.util.List<Anime> resultado = animes.stream()
                .sorted(Comparator.comparingInt(Anime::getCalificacionDelUsuario).reversed())
                .limit(this.cantidad)
                .collect(Collectors.toList());

        for (Anime a : resultado) {
            lista.agregarAnime(a);
        }

        return lista;
    }
}