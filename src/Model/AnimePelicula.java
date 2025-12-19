package Model;

import java.util.ArrayList;

public class AnimePelicula extends Anime {
    private int duracion;

    /**
     * <b><span style="color: #000000; background-color: #FFFACD;"> METODOS </span></b><br><br>
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CONSTRUCTOR </span></b>
     */
    public AnimePelicula(String titulo, int anhoDeLanzamiento, int calificacionDelUsuario, EstadoAnime estado, ArrayList<Genero> generos, Estudio estudio, int duracion) {
        super(titulo, anhoDeLanzamiento, calificacionDelUsuario, estado, generos, estudio);
        setDuracion(duracion);
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER DURACION </span></b>
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR DURACION </span></b> <br>
     * verifica que la duracion sea positiva diferente de 0
     */
    public void setDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos.");
        }
        this.duracion = duracion;
    }
}