package Services;

import Model.Anime;

import java.util.ArrayList;

public class FiltroPorNombre implements IFiltroCriterio {
    private String textoBusqueda;
    public FiltroPorNombre(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    @Override
    public ArrayList<Anime> crumpleCriterio(ArrayList<Anime> animes) {
        ArrayList<Anime> animesFiltrados = new ArrayList<>();
        // validamos si el texto de búsqueda es nulo o vacío
        if (this.textoBusqueda == null || this.textoBusqueda.trim().isEmpty()) {
            System.err.println("El criterio de nombre está vacío.");
            return animesFiltrados;
        }
        String busquedaNormalizada = this.textoBusqueda.trim().toLowerCase();
        for (Anime anime : animes) {
            if (anime.gettitulo() != null) {
                if (anime.gettitulo().toLowerCase().contains(busquedaNormalizada)) {
                    animesFiltrados.add(anime);
                }
            }
        }
        return animesFiltrados;
    }
}