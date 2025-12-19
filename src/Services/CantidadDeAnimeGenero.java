package Services;

import Model.Anime;
import Model.Genero;
import java.util.ArrayList;

public class CantidadDeAnimeGenero implements IEstrategiaEstadistica {
    private Genero generoObjetivo;
    public CantidadDeAnimeGenero(Genero genero) {
        this.generoObjetivo = genero;
    }
    @Override
    public String crumpleCriterio(ArrayList<Anime> animes) {
        if (animes == null) return "0";
        long count = animes.stream()
                .filter(a -> a.getGeneros().contains(this.generoObjetivo))
                .count();
        return String.valueOf(count);
    }
}