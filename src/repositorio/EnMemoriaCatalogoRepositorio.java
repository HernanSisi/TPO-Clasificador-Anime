package repositorio;

import Model.Catalogo;
import java.io.*;

public class EnMemoriaCatalogoRepositorio implements ICatalogoRepositorio {
    private String ruta;

    public EnMemoriaCatalogoRepositorio(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public void guardarCatalogo(Catalogo catalogo) {
        File file = new File(this.ruta);

        // 1. Asegurar que la carpeta "AplicacionCatalogoAnimes" exista
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 2. Guardar el objeto en el archivo
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(catalogo);
            System.out.println("Catálogo guardado exitosamente en: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al guardar el catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCatalogo() {
        File file = new File(this.ruta);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Catálogo eliminado: " + this.ruta);
            } else {
                System.err.println("No se pudo eliminar el archivo.");
            }
        }
    }

    @Override
    public Catalogo cargarCatalogo() {
        File file = new File(this.ruta);

        if (!file.exists()) {
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            return (Catalogo) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el catálogo: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void actualizarCatalogo(Catalogo catalogo) {
        guardarCatalogo(catalogo);
    }

    public boolean existeCatalogo() {
        File file = new File(this.ruta);
        return file.exists();
    }
}