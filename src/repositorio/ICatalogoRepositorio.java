package repositorio;

import Model.Catalogo;

public interface ICatalogoRepositorio {
    public void guardarCatalogo(Catalogo catalogo);
    public void eliminarCatalogo();
    public Catalogo cargarCatalogo();
    public void actualizarCatalogo(Catalogo catalogo);
}
