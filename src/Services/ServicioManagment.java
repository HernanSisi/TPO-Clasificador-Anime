package Services;

import Model.Anime;
import Model.Catalogo;
import repositorio.ICatalogoRepositorio;

public class ServicioManagment implements IAnimeManagerService {
    private ICatalogoRepositorio repositorio;
    private Catalogo catalogo;

    public ServicioManagment(ICatalogoRepositorio repositorio) {
        this.repositorio = repositorio;
        this.catalogo = repositorio.cargarCatalogo();
    }

    @Override
    public void agregarAnime(Anime a) {
        garantizarCatalogo();
        if (catalogo != null) {
            catalogo.addAnime(a);
            repositorio.actualizarCatalogo(catalogo);
        }
    }

    @Override
    public void actualizarAnime(Anime nuevo, Anime viejo) {
        garantizarCatalogo();
        if (catalogo != null) {
            catalogo.removeAnime(viejo);
            catalogo.addAnime(nuevo);
            repositorio.actualizarCatalogo(catalogo);
        }
    }

    @Override
    public void eliminarAnime(Anime a) {
        garantizarCatalogo();
        if (catalogo != null) {
            catalogo.removeAnime(a);
            repositorio.actualizarCatalogo(catalogo);
        }
    }

    @Override
    public Catalogo getCatalogo() {
        garantizarCatalogo();
        return catalogo;
    }

    @Override
    public void guardarCambios() {
        if (catalogo != null) {
            repositorio.actualizarCatalogo(catalogo);
        }
    }

    private void garantizarCatalogo() {
        if (catalogo == null) {
            catalogo = repositorio.cargarCatalogo();
        }
    }
}