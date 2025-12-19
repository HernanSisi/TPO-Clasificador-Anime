package Services;

import Model.Anime;
import Model.Catalogo;

import java.util.ArrayList;

public interface IAnimeManagerService {
    public void agregarAnime(Anime a);
    public void actualizarAnime(Anime nuevo, Anime viejo);
    public void eliminarAnime(Anime a);
    public Catalogo getCatalogo();
    public void guardarCambios();
}
