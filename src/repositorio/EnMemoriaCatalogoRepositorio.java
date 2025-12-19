package repositorio;

import Model.Catalogo;

import javax.swing.*;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class EnMemoriaCatalogoRepositorio implements ICatalogoRepositorio {
    private String ruta;
    private RandomAccessFile raf;
    private FileChannel channel;
    private FileLock lock;

    public EnMemoriaCatalogoRepositorio(String ruta) {
        this.ruta = ruta;
        // Intentamos bloquear el archivo nada más instanciar el repositorio
        if (existeCatalogo()) {
            bloquearArchivo();
        }
    }

    @Override
    public void guardarCatalogo(Catalogo catalogo) {
        File file = new File(this.ruta);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            //Si no tenemos el archivo abierto/bloqueado lo abrimos.
            if (this.raf == null || !file.exists()) {
                if (!file.exists()) {
                    file.createNewFile();
                }
                bloquearArchivo();
            }
            if (channel != null && channel.isOpen()) {
                channel.truncate(0);
                channel.position(0);
                OutputStream os = Channels.newOutputStream(channel);
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(catalogo);
                oos.flush();
                System.out.println("Catálogo guardado exitosamente en modo seguro: " + file.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Error al guardar el catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Catalogo cargarCatalogo() {
        // Aseguramos que esté abierto y bloqueado antes de leer
        if (this.raf == null && existeCatalogo()) {
            bloquearArchivo();
        }

        try {
            if (channel != null && channel.isOpen()) {
                channel.position(0);
                InputStream is = Channels.newInputStream(channel);
                ObjectInputStream ois = new ObjectInputStream(is);
                return (Catalogo) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el catálogo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void eliminarCatalogo() {
        //liberar el bloqueo, de lo contrario el SO no dejará borrar el archivo
        liberarRecursos();
        //borrar el archivo
        File file = new File(this.ruta);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Catálogo eliminado: " + this.ruta);
            } else {
                System.err.println("No se pudo eliminar el archivo (verifique permisos).");
            }
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
    private void bloquearArchivo() {
        try {
            File file = new File(this.ruta);
            // se abre en modo lextura y escritura
            this.raf = new RandomAccessFile(file, "rw");
            this.channel = raf.getChannel();
            this.lock = channel.tryLock();

            if (this.lock != null) {
                System.out.println("Archivo bloqueado exclusivamente por la aplicación.");
            }

        } catch (IOException e) {
            System.err.println("No se pudo bloquear el archivo (posiblemente ya esté en uso): " + e.getMessage());
            mostrarErrorYSalir();
        }
    }
    private void mostrarErrorYSalir() {
        JOptionPane.showMessageDialog(null,
                "Aplicación ya abierta, no se puede abrir otra instancia",
                "Error",
                JOptionPane.ERROR_MESSAGE);

        // Cierra la aplicación forzosamente
        System.exit(0);
    }

    private void liberarRecursos() {
        try {
            if (lock != null) { lock.release(); lock = null; }
            if (channel != null) { channel.close(); channel = null; }
            if (raf != null) { raf.close(); raf = null; }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
