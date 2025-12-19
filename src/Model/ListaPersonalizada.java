package Model;

import java.util.ArrayList;
import java.io.Serializable; // Import necesario

// AGREGAR "implements Serializable" AQUÍ
public class ListaPersonalizada implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private ArrayList<Anime> animes;

    public ListaPersonalizada(String nombre) {
        this.nombre = nombre;
        this.animes = new ArrayList<>();
    }
    // ... resto de los métodos igual (getters, setters, add, remove) ...
    public void agregarAnime(Anime a){
        animes.add(a);
    }
    public Anime getAnime(int i){
        return animes.get(i);
    }
    public ArrayList<Anime> getAnime(){
        return animes;
    }
    public int getCantidadDeAnimes(){
        return animes.size();
    }
    public void removeAnime(Anime a){
        animes.remove(a);
    }
    public void removeAnime(int i){
        animes.remove(i);
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}