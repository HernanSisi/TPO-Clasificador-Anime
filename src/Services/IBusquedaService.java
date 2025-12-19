package Services;

import Model.Anime;

import java.util.ArrayList;

public interface IBusquedaService {
    public ArrayList<Anime> buscar(String criterio);
    public ArrayList<Anime> filtrar(ArrayList<String> criterios);
}
