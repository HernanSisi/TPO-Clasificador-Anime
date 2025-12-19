package Services;

import Model.Anime;
import Model.Genero;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TopNGenerosMasFrecuente implements IEstrategiaEstadistica {
    private int n;

    public TopNGenerosMasFrecuente(int n) {
        this.n = n;
    }

    @Override
    public String crumpleCriterio(ArrayList<Anime> animes) {
        if (animes == null || animes.isEmpty()) return "Sin datos";
        Map<Genero, Integer> conteo = new HashMap<>();
        // Contar ocurrencias
        for (Anime a : animes) {
            for (Genero g : a.getGeneros()) {
                conteo.put(g, conteo.getOrDefault(g, 0) + 1);
            }
        }
        // Ordenar y limitar
        return conteo.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(n)
                .map(e -> e.getKey().name() + " (" + e.getValue() + ")")
                .collect(Collectors.joining(", "));
    }
}