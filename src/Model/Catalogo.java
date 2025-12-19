package Model;

import java.util.ArrayList;

public class Catalogo {
    private ArrayList<Anime> animes;
    private String nombre;
    private ArrayList<ListaPersonalizada> listasPersonalizadas;
    public Catalogo(String nombre) {
        this.nombre = nombre;
        this.animes = new ArrayList<>();
        this.listasPersonalizadas = new ArrayList<>();
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public ArrayList<Anime> getAnime() {
        return animes;
    }
    public Anime getAnime(int i) {
        return animes.get(i);
    }
    public void addAnime(Anime a) {
        this.animes.add(a);
    }
    public void removeAnime(Anime a) {
        this.animes.remove(a);
    }
    public void removeAnime(int i) {
        this.animes.remove(i);
    }
    public void updateAnime(Anime a) {
        this.animes.remove(a);
        this.animes.add(a);
    }
    public ArrayList<ListaPersonalizada> getListasPersonalizadas() {
        return listasPersonalizadas;
    }
    public ListaPersonalizada getListaPersonalizada(int i) {
        return listasPersonalizadas.get(i);
    }
    public void addListaPersonalizada(ListaPersonalizada l) {
        this.listasPersonalizadas.add(l);
    }
    public void removeListaPersonalizada(ListaPersonalizada l) {
        this.listasPersonalizadas.remove(l);
    }
    public void removeListaPersonalizada(int i) {
        this.listasPersonalizadas.remove(i);
    }

}
