package Model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serializable;
public abstract class Anime implements  Serializable {
    private String titulo;
    private int anhoDeLanzamiento;
    private int calificacionDelUsuario;
    private EstadoAnime estado;
    private ArrayList<Genero> generos;
    private Estudio estudio;
    private static final long serialVersionUID = 1L;

    /**
     * <b><span style="color: #000000; background-color: #FFFACD;"> METODOS </span></b><br><br>
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CONSTRUCTOR </span></b>
     */
    public Anime(String titulo,
                 int anhoDeLanzamiento,
                 int calificacionDelUsuario,
                 EstadoAnime estado,
                 ArrayList<Genero> generos,
                 Estudio estudio) {
        this.titulo = titulo;
        this.anhoDeLanzamiento = anhoDeLanzamiento;
        this.calificacionDelUsuario = calificacionDelUsuario;
        // AGREGAR VERIFICACION QUE ESTE ENTRE 1 Y 5 O DEVOLVER UN THROW
        this.estado = estado;
        this.generos = generos;
        this.estudio = estudio;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER TITULO </span></b>
     */
    public String gettitulo() {
        return titulo;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR TITULO </span></b><br>
     *  verifica que el neuvo titulo no este vacio y sea mayor a x caracteres
     */
    public void settitulo(String titulo) {
        this.titulo = titulo;
        //TODO falta verificar que el titulo no se null y/o sea mayor q x caracteres
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER CALIFICACION </span></b>
     */
    public int getCalificacionDelUsuario() {
        return calificacionDelUsuario;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR CALIFICACION </span></b><br>
     *  verifica que la calificacion este entre 1 y 5, si no lo esta la calificacion no cambia
     */
    public void setCalificacionDelUsuario(int calificacionDelUsuario) {
        this.calificacionDelUsuario = calificacionDelUsuario;
        // TODO AGREGAR VERIFICACION QUE ESTE ENTRE 1 Y 5 O DEVOLVER UN THROW
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER ESTADO </span></b>
     */
    public EstadoAnime getEstado() {
        return estado;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR ESTADO </span></b><br>
     * verifica que el nuevo estado no este vacio
     */
    public void setEstado(EstadoAnime estado) {
        this.estado = estado;
        //TODO verificar que el nuevo estado no este vacio
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER GENEROS </span></b>
     */
    public ArrayList<Genero> getGeneros() {
        return generos;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR GENEROS </span></b><br>
     *  verifica que el listado de generos no este vacio
     */
    public void setGeneros(ArrayList<Genero> generos) {
        this.generos = generos;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER AÑO DE LANZAMIENTO </span></b>
     */
    public int getAnhoDeLanzamiento() {
        return anhoDeLanzamiento;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR AÑO DE LANZAMIENTO </span></b><br>
     *  verifica que el año de lanzamiento este entre 1900 y el año actual
     */
    public void setAnhoDeLanzamiento(int anhoDeLanzamiento) {
        this.anhoDeLanzamiento = anhoDeLanzamiento;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER ESTUDIO </span></b>
     */
    public Estudio getEstudio() {
        return estudio;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR ESTUDIO </span></b><br>
     *  verifica que el estudio asignado no sea nulo y este en el enum
     */
    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }
    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> EQUALS </span></b><br>
     *  verifica dos objetos del tipo anime son iguales
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return anhoDeLanzamiento == anime.anhoDeLanzamiento && Objects.equals(titulo, anime.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, anhoDeLanzamiento);
    }
}
