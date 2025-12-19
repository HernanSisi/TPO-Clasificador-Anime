package Model;

import java.util.ArrayList;

public class AnimeSerie extends Anime{
    private int cantidadCapitulos;
    /**
     * <b><span style="color: #000000; background-color: #FFFACD;"> METODOS </span></b><br><br>
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CONSTRUCTOR </span></b>
     */
    public AnimeSerie(String titulo, int anhoDeLanzamiento, int calificacionDelUsuario, EstadoAnime estado, ArrayList<Genero> generos, Estudio estudio, int cantidadCapitulos) {
        super(titulo, anhoDeLanzamiento, calificacionDelUsuario, estado, generos, estudio);
        this.cantidadCapitulos = cantidadCapitulos;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER CANTIDAD DE CAPITULOS </span></b>
     */
    public int getCantidadCapitulos() {
        return cantidadCapitulos;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> MODIFICAR CANTIDAD DE CAPITULOS </span></b> <br>
     * verifica que la cantidad de capitulos sea un numero no negativo mayor que cero
     */
    public void setCantidadCapitulos(int cantidadCapitulos) {
        this.cantidadCapitulos = cantidadCapitulos;
    }
}
