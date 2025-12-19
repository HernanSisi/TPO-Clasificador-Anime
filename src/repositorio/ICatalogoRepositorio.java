package repositorio;

import Model.Catalogo;

public interface ICatalogoRepositorio {
    public void guardarCatalogo(Catalogo catalogo, String ruta);
    public void eliminarCatalogo(String ruta);
    public Catalogo cargarCatalogo(String ruta);
    public void actualizarCatalogo(Catalogo catalogo, String ruta);

}
