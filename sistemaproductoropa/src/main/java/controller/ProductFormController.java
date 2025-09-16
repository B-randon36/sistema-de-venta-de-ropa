package controller;

import model.Product;
import view.ProductFormView;
import javafx.scene.control.Alert;

public class ProductFormController {
    private ProductFormView view;
    private Product productoSeleccionado = null;

    public ProductFormController(ProductFormView view) {
        this.view = view;
        this.view.registrarButton.setOnAction(e -> registrarProducto());
        this.view.editarButton.setOnAction(e -> editarProducto());
        this.view.eliminarButton.setOnAction(e -> eliminarProducto());

        this.view.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                productoSeleccionado = newSel;
                view.nombreField.setText(newSel.getNombre());
                view.marcaField.setText(newSel.getMarca());
                view.precioField.setText(String.valueOf(newSel.getPrecio()));
                view.stockField.setText(String.valueOf(newSel.getStock()));
            }
        });
    }

    private void registrarProducto() {
        try {
            String nombre = view.nombreField.getText();
            String marca = view.marcaField.getText();
            double precio = Double.parseDouble(view.precioField.getText());
            int stock = Integer.parseInt(view.stockField.getText());

            Product producto = new Product(nombre, marca, precio, stock);

            view.productList.add(producto);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto registrado correctamente:\n" + producto.getNombre());
            limpiarCampos();
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Datos inválidos. Por favor, verifica los campos.");
        }
    }

    private void editarProducto() {
        if (productoSeleccionado != null) {
            try {
                productoSeleccionado.setNombre(view.nombreField.getText());
                productoSeleccionado.setMarca(view.marcaField.getText());
                productoSeleccionado.setPrecio(Double.parseDouble(view.precioField.getText()));
                productoSeleccionado.setStock(Integer.parseInt(view.stockField.getText()));

                view.tableView.refresh();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto editado correctamente.");
                limpiarCampos();
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Datos inválidos. Por favor, verifica los campos.");
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un producto para editar.");
        }
    }

    private void eliminarProducto() {
        if (productoSeleccionado != null) {
            view.productList.remove(productoSeleccionado);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto eliminado correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un producto para eliminar.");
        }
    }

    private void limpiarCampos() {
        view.nombreField.clear();
        view.marcaField.clear();
        view.precioField.clear();
        view.stockField.clear();
        productoSeleccionado = null;
        view.tableView.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}