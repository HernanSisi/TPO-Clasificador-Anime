package Services;

import Model.Anime;

import java.util.ArrayList;

public interface IRecomendacionService {
    public ArrayList<Anime> generarRecomendaciones(String criterio);
}
