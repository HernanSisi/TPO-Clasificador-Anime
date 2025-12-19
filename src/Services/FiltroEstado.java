package Services;

import Model.Anime;
import Model.EstadoAnime;

import java.util.ArrayList;

public class FiltroEstado implements IFiltroCriterio {
    private String estadoBusqueda;
    public FiltroEstado(String estadoBusqueda) {
        this.estadoBusqueda = estadoBusqueda;
    }
    @Override
    public ArrayList<Anime> crumpleCriterio(ArrayList<Anime> animes) {
        ArrayList<Anime> resultado = new ArrayList<>();
        if (this.estadoBusqueda == null || this.estadoBusqueda.trim().isEmpty()) {
            System.err.println("El criterio de estado está vacío.");
            return resultado;
        }

        try {
            // Convertir el String al Enum.
            String valorNormalizado = this.estadoBusqueda.trim().toUpperCase().replace(" ", "_");
            EstadoAnime estadoRequerido = EstadoAnime.valueOf(valorNormalizado);
            // Filtramos la lista comparando con el Enum obtenido
            for (Anime anime : animes) {
                if (anime.getEstado() == estadoRequerido) {
                    resultado.add(anime);
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error: El estado '" + this.estadoBusqueda + "' no es válido.");
            System.err.println("Valores permitidos: POR_VER, VIENDO, FINALIZADO, ABANDONADO.");
        }

        return resultado;
    }
}