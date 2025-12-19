package Services;

import Model.Anime;

import java.util.ArrayList;

public class FiltroAnho implements IFiltroCriterio {
    private String rangoAnios;

    public FiltroAnho(String rangoAnios) {
        this.rangoAnios = rangoAnios;
    }

    @Override
    public ArrayList<Anime> crumpleCriterio(ArrayList<Anime> animes) {
        ArrayList<Anime> animesFiltrados = new ArrayList<>();

        try {
            // Verificamos que el string no sea nulo o vacío
            if (this.rangoAnios == null || this.rangoAnios.trim().isEmpty()) {
                System.err.println("El criterio de años está vacío.");
                return animesFiltrados;
            }
            // Separamos el string por el guion "-"
            String[] partes = this.rangoAnios.split("-");

            if (partes.length != 2) {
                throw new IllegalArgumentException("El formato del año debe ser 'AAAA-AAAA'.");
            }

            int anhoInicio = Integer.parseInt(partes[0].trim());
            int anhoFin = Integer.parseInt(partes[1].trim());

            if (anhoInicio > anhoFin) {
                throw new IllegalArgumentException("El año de inicio debe ser menor o igual al año de fin.");
            }

            // Recorremos la lista y filtramos
            for (Anime anime : animes) {
                int anhoLanzamiento = anime.getAnhoDeLanzamiento();
                if (anhoLanzamiento >= anhoInicio && anhoLanzamiento <= anhoFin) {
                    animesFiltrados.add(anime);
                }
            }

        } catch (NumberFormatException e) {
            System.err.println("Error al procesar los años: Los valores deben ser numéricos.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error en el filtro de años: " + e.getMessage());
        }

        return animesFiltrados;
    }
}