package Model;

import java.util.ArrayList;
import java.util.Objects;
import java.io.Serializable;
import java.time.Year; // Import necesario para validar el año actual

public abstract class Anime implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private int anhoDeLanzamiento;
    private int calificacionDelUsuario;
    private EstadoAnime estado;
    private ArrayList<Genero> generos;
    private Estudio estudio;

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
        settitulo(titulo);
        setAnhoDeLanzamiento(anhoDeLanzamiento);
        setCalificacionDelUsuario(calificacionDelUsuario);
        setEstado(estado);
        setGeneros(generos);
        setEstudio(estudio);
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER TITULO </span></b>
     */
    public String gettitulo() {
        return titulo;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR TITULO </span></b><br>
     * verifica que el nuevo titulo no este vacio y sea mayor a 2 caracteres
     */
    public void settitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (titulo.length() < 2) {
            throw new IllegalArgumentException("El título debe tener al menos 2 caracteres.");
        }
        this.titulo = titulo;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER CALIFICACION </span></b>
     */
    public int getCalificacionDelUsuario() {
        return calificacionDelUsuario;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR CALIFICACION </span></b><br>
     * verifica que la calificacion este entre 1 y 5
     */
    public void setCalificacionDelUsuario(int calificacionDelUsuario) {
        if (calificacionDelUsuario < 1 || calificacionDelUsuario > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }
        this.calificacionDelUsuario = calificacionDelUsuario;
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
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        this.estado = estado;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> OBTENER GENEROS </span></b>
     */
    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    /**
     * <b><span style="color: #000000; background-color: #AAF0D1;"> CAMBIAR GENEROS </span></b><br>
     * verifica que el listado de generos no este vacio ni nulo
     */
    public void setGeneros(ArrayList<Genero> generos) {
        if (generos == null || generos.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos un género.");
        }
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
     * verifica que el año de lanzamiento este entre 1900 y el año actual
     */
    public void setAnhoDeLanzamiento(int anhoDeLanzamiento) {
        int anhoActual = Year.now().getValue();
        if (anhoDeLanzamiento < 1900 || anhoDeLanzamiento > anhoActual) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y " + anhoActual + ".");
        }
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
     * verifica que el estudio asignado no sea nulo
     */
    public void setEstudio(Estudio estudio) {
        if (estudio == null) {
            throw new IllegalArgumentException("El estudio no puede ser nulo.");
        }
        this.estudio = estudio;
    }

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