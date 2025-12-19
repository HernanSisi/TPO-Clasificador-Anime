package Services;

import Model.ListaPersonalizada;
import Model.Anime;

import java.util.ArrayList;

public interface IAlgoritmoRecomendacion {
    public ListaPersonalizada crumpleCriterio(ArrayList<Anime> animes);
}
