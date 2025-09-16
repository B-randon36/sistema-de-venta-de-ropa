package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;

public class ProductFormView {
    public TextField nombreField = new TextField();
    public TextField marcaField = new TextField();
    public TextField precioField = new TextField();
    public TextField stockField = new TextField();
    public Button registrarButton = new Button("Registrar");
    public Button editarButton = new Button("Editar");
    public Button eliminarButton = new Button("Eliminar");

    public TableView<Product> tableView = new TableView<>();
    public ObservableList<Product> productList = FXCollections.observableArrayList();

    public void show(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);

        grid.add(new Label("Marca:"), 0, 1);
        grid.add(marcaField, 1, 1);

        grid.add(new Label("Precio:"), 0, 2);
        grid.add(precioField, 1, 2);

        grid.add(new Label("Stock:"), 0, 3);
        grid.add(stockField, 1, 3);

        HBox buttonBox = new HBox(10, registrarButton, editarButton, eliminarButton);
        grid.add(buttonBox, 1, 4);

        // Configura la tabla
        TableColumn<Product, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));

        TableColumn<Product, String> marcaCol = new TableColumn<>("Marca");
        marcaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));

        TableColumn<Product, Number> precioCol = new TableColumn<>("Precio");
        precioCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrecio()));

        TableColumn<Product, Number> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStock()));

        tableView.getColumns().addAll(nombreCol, marcaCol, precioCol, stockCol);
        tableView.setItems(productList);
        tableView.setPrefHeight(200);

        VBox vbox = new VBox(10, grid, tableView);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 450, 470);
        stage.setTitle("Registro de Producto");
        stage.setScene(scene);
        stage.show();
    }
}