package com.example.registroproductoropa;

import java.util.ArrayList;

// Controlador que se encarga de manejar la lista de productos

public class ProductoController {
    // Lista dinámica que almacena todos los productos registrados
    // Encapsulada enprivate para proteger los datos
    private ArrayList<Producto> productos = new ArrayList<>();

    // CREATE: Método para agregar un nuevo producto a la lista
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
    // UPDATE: Método para editar un producto existente
    // Recibe el id del producto a editar y los nuevos datos
    public void editarProducto(int id, Producto nuevosDatos) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == id) {
                // Mantener fecha de registro original
                nuevosDatos.setFechaRegistro(productos.get(i).getFechaRegistro());
                // Reemplaza el producto viejo por el nuevo
                productos.set(i, nuevosDatos);
                return;
            }
        }
    }
    // DELETE: Método para eliminar un producto de la lista según su id
    public void eliminarProducto(int id) {
        // removeIf elimina todos los elementos que cumplan la condición
        productos.removeIf(p -> p.getId() == id);
    }
    // READ (todos): Devuelve la lista completa de productos.
    // Se usa para mostrar en tablas u otros listados.
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    // READ (uno): Busca un producto en la lista según su id.
    // Si lo encuentra, lo retorna; si no, devuelve null.
    public Producto buscarProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null; // Si no lo encuentra
    }
}