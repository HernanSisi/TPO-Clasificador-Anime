package Services;

import Model.Anime;
import Model.ListaPersonalizada;

import java.util.ArrayList;

public interface IRecomendacionService {
    public ListaPersonalizada generarRecomendaciones(String criterio, ArrayList<Anime> animes);
}
