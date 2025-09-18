package com.example.registroproductoropa;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Clase principal que extiende de Application para usar JavaFX
public class Main extends Application {
    // Controlador donde se gestionan los productos
    ProductoController controller = new ProductoController();
    // Lista observable para que la tabla se actualice automáticamente
    ObservableList<Producto> productosObs = FXCollections.observableArrayList();

    private BorderPane root;  // Contenedor principal
    private Scene scene;      // Escena de la ventana

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        scene = new Scene(root, 700, 600);

        // --- Barra de menú superior ---
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Opciones");
        MenuItem registrarItem = new MenuItem("Registrar producto");
        MenuItem verItem = new MenuItem("Ver productos");
        MenuItem salirItem = new MenuItem("Salir");

        // Agregamos los ítems al menú
        menu.getItems().addAll(registrarItem, verItem, new SeparatorMenuItem(), salirItem);
        menuBar.getMenus().add(menu);
        root.setTop(menuBar);

        // Creamos los paneles para registro y para la tabla
        VBox panelRegistro = crearPanelRegistro();
        VBox panelTabla = crearPanelTabla();

        // --- Acciones de los botones del menú ---
        registrarItem.setOnAction(e -> root.setCenter(panelRegistro));
        verItem.setOnAction(e -> {
            actualizarTabla(); // refresca la tabla
            root.setCenter(panelTabla);
        });
        salirItem.setOnAction(e -> primaryStage.close());

        // Mostramos el panel de registro como inicio
        root.setCenter(panelRegistro);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestión de Ropa");
        primaryStage.show();
    }

    // --- Panel para registrar un nuevo producto ---
    private VBox crearPanelRegistro() {
        // Campos de entrada
        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField marcaField = new TextField();
        marcaField.setPromptText("Nombre");

        ComboBox<Categoria> categoriaBox = new ComboBox<>();
        categoriaBox.getItems().setAll(Categoria.values());
        categoriaBox.setPromptText("Categoría");

        TextField precioField = new TextField();
        precioField.setPromptText("Precio");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        // Botón para registrar
        Button btnAgregar = new Button("Agregar producto");

        // Acción del botón
        btnAgregar.setOnAction(e -> {
            try {
                // Se obtienen los datos ingresados
                int id = Integer.parseInt(idField.getText());
                String marca = marcaField.getText();
                Categoria categoria = categoriaBox.getValue();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());

                // Validación de campos
                if (categoria == null || marca.isEmpty()) throw new Exception();

                // Se crea un nuevo producto y se agrega al controlador
                Producto p = new Producto(id, marca, categoria, precio, stock);
                controller.agregarProducto(p);

                // Se limpian los campos después de registrar
                limpiarCampos(idField, marcaField, categoriaBox, precioField, stockField);

                // Mensaje de éxito
                showAlert("Éxito", "Producto registrado correctamente", Alert.AlertType.INFORMATION);

            } catch (Exception ex) {
                // Si hay error en los datos
                showAlert("Error", "Datos inválidos, revisa que los datos estén llenos", Alert.AlertType.ERROR);
            }
        });

        // Estructura del formulario
        VBox vbox = new VBox(10,
                new Label("Registrar producto"),
                new Label("ID:"), idField,
                new Label("Marca:"), marcaField,
                new Label("Categoría:"), categoriaBox,
                new Label("Precio:"), precioField,
                new Label("Stock:"), stockField,
                btnAgregar
        );
        vbox.setPadding(new Insets(25));
        return vbox;
    }

    // --- Panel donde se muestra la tabla de productos ---
    private VBox crearPanelTabla() {
        TableView<Producto> tableView = new TableView<>(productosObs);

        // Definición de columnas
        TableColumn<Producto, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Marca");
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().toString()));

        TableColumn<Producto, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));

        TableColumn<Producto, String> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));

        TableColumn<Producto, String> colHora = new TableColumn<>("Hora de Registro");
        colHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraFormateada()));

        // Agregar columnas a la tabla
        tableView.getColumns().addAll(colId, colNombre, colCategoria, colPrecio, colStock, colHora);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Botones para editar y eliminar
        Button btnEditar = new Button("Editar seleccionado");
        Button btnEliminar = new Button("Eliminar seleccionado");

        // Acción de editar
        btnEditar.setOnAction(e -> {
            Producto sel = tableView.getSelectionModel().getSelectedItem();
            if (sel == null) {
                showAlert("Advertencia", "Selecciona un producto para editar.", Alert.AlertType.WARNING);
                return;
            }
            mostrarDialogoEdicion(sel);
            actualizarTabla();
        });

        // Acción de eliminar
        btnEliminar.setOnAction(e -> {
            Producto sel = tableView.getSelectionModel().getSelectedItem();
            if (sel == null) {
                showAlert("Advertencia", "Selecciona un producto para eliminar.", Alert.AlertType.WARNING);
                return;
            }
            controller.eliminarProducto(sel.getId());
            actualizarTabla();
        });

        VBox vbox = new VBox(12, new Label("Productos registrados:"), tableView, btnEditar, btnEliminar);
        vbox.setPadding(new Insets(25));
        return vbox;
    }

    // --- Diálogo para editar productos ---
    private void mostrarDialogoEdicion(Producto producto) {
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Editar producto");

        // Campos con los valores actuales
        TextField nombreField = new TextField(producto.getNombre());
        ComboBox<Categoria> categoriaBox = new ComboBox<>(FXCollections.observableArrayList(Categoria.values()));
        categoriaBox.setValue(producto.getCategoria());
        TextField precioField = new TextField(String.valueOf(producto.getPrecio()));
        TextField stockField = new TextField(String.valueOf(producto.getStock()));

        // Diseño del formulario
        VBox box = new VBox(10,
                new Label("Marca:"), nombreField,
                new Label("Categoría:"), categoriaBox,
                new Label("Precio:"), precioField,
                new Label("Stock:"), stockField
        );
        dialog.getDialogPane().setContent(box);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Acción cuando el usuario confirma la edición
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    String nombre = nombreField.getText();
                    Categoria categoria = categoriaBox.getValue();
                    double precio = Double.parseDouble(precioField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    // Creamos un nuevo producto con los datos modificados
                    Producto nuevo = new Producto(producto.getId(), nombre, categoria, precio, stock);
                    controller.editarProducto(producto.getId(), nuevo);
                    return nuevo;
                } catch (Exception ex) {
                    showAlert("Error", "Datos inválidos", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // --- Método para actualizar la tabla con los productos del controlador ---
    private void actualizarTabla() {
        productosObs.setAll(controller.getProductos());
    }

    // --- Método para limpiar los campos después de registrar ---
    private void limpiarCampos(TextField idField, TextField nombreField, ComboBox<Categoria> categoriaBox, TextField precioField, TextField stockField) {
        idField.clear();
        nombreField.clear();
        categoriaBox.setValue(null);
        precioField.clear();
        stockField.clear();
    }

    // --- Método para mostrar mensajes (éxito, error, advertencia) ---
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método main que lanza la aplicación
    public static void main(String[] args) {
        launch(args);
    }
}
