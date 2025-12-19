package Services;

import Model.Anime;
import Model.Genero;
import java.util.ArrayList;

public class PromedioPorGenero implements IEstrategiaEstadistica {
    private Genero generoObjetivo;
    public PromedioPorGenero(Genero genero) {
        this.generoObjetivo = genero;
    }
    @Override
    public String crumpleCriterio(ArrayList<Anime> animes) {
        if (animes == null || animes.isEmpty()) return "0.00";
        double suma = 0;
        int cantidad = 0;
        for (Anime a : animes) {
            if (a.getGeneros().contains(this.generoObjetivo)) {
                suma += a.getCalificacionDelUsuario();
                cantidad++;
            }
        }
        if (cantidad == 0) return "0.00";
        double promedio = suma / cantidad;
        return String.format("%.2f", promedio);
    }
}