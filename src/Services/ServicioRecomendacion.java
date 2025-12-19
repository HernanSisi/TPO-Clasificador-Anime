package Services;

import Model.Anime;
import Model.Genero;
import Model.ListaPersonalizada;

import java.util.ArrayList;

public class ServicioRecomendacion implements IRecomendacionService {

    @Override
    public ListaPersonalizada generarRecomendaciones(String criterio, ArrayList<Anime> animes) {
        IAlgoritmoRecomendacion estrategia = null;

        try {
            if (criterio == null || criterio.isEmpty()) {
                return new ListaPersonalizada("Lista Vacía");
            }

            String[] partes = criterio.split(":");
            String clave = partes[0].toUpperCase();

            if (clave.equals("TOP_CATALOGO")) {
                if (partes.length >= 2) {
                    int cantidad = Integer.parseInt(partes[1]);
                    estrategia = new RecomendacionTopCatalogo(cantidad);
                }

            } else if (clave.equals("POR_GENERO")) {
                if (partes.length >= 3) {
                    Genero genero = Genero.valueOf(partes[1]);
                    int cantidad = Integer.parseInt(partes[2]);
                    estrategia = new RecomendacionPorGenero(genero, cantidad);
                }
            }
            if (estrategia != null) {
                return estrategia.crumpleCriterio(animes);
            }

        } catch (Exception e) {
            System.err.println("Error al interpretar el criterio de recomendación: " + criterio);
            e.printStackTrace();
        }

        return new ListaPersonalizada("Error en Recomendación");
    }
}