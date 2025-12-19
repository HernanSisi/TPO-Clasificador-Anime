package Services;

import Model.Anime;
import Model.EstadoAnime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FiltroEstado implements IFiltroCriterio {
    private String estadoBusqueda;

    public FiltroEstado(String estadoBusqueda) {
        this.estadoBusqueda = estadoBusqueda;
    }

    @Override
    public ArrayList<Anime> crumpleCriterio(ArrayList<Anime> animes) {
        ArrayList<Anime> resultado = new ArrayList<>();
        if (this.estadoBusqueda == null || this.estadoBusqueda.trim().isEmpty()) {
            return resultado;
        }

        try {
            Set<EstadoAnime> estadosRequeridos = new HashSet<>();
            String[] partes = this.estadoBusqueda.split(",");

            for (String p : partes) {
                if (!p.trim().isEmpty()) {
                    String valorNormalizado = p.trim().toUpperCase().replace(" ", "_");
                    estadosRequeridos.add(EstadoAnime.valueOf(valorNormalizado));
                }
            }

            if (estadosRequeridos.isEmpty()) {
                return resultado;
            }
            for (Anime anime : animes) {
                if (estadosRequeridos.contains(anime.getEstado())) {
                    resultado.add(anime);
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error al procesar estados: " + e.getMessage());
        }

        return resultado;
    }
}