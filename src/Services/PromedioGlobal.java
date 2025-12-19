package Services;

import Model.Anime;
import java.util.ArrayList;

public class PromedioGlobal implements IEstrategiaEstadistica {
    @Override
    public String crumpleCriterio(ArrayList<Anime> animes) {
        if (animes == null || animes.isEmpty()) return "0.00";
        double suma = 0;
        for (Anime a : animes) {
            suma += a.getCalificacionDelUsuario();
        }
        double promedio = suma / animes.size();
        return String.format("%.2f", promedio);
    }
}