package Services;

import Model.Anime;
import Model.EstadoAnime;
import java.util.ArrayList;

public class CantidadAnimesPorEstado implements IEstrategiaEstadistica {
    private EstadoAnime estadoObjetivo;

    public CantidadAnimesPorEstado(EstadoAnime estado) {
        this.estadoObjetivo = estado;
    }

    @Override
    public String crumpleCriterio(ArrayList<Anime> animes) {
        if (animes == null) return "0";
        long count = animes.stream()
                .filter(a -> a.getEstado() == this.estadoObjetivo)
                .count();
        return String.valueOf(count);
    }
}