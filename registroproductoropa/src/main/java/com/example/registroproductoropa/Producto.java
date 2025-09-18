package com.example.registroproductoropa;

import java.time.LocalDateTime;              // Clase que maneja fechas y horas
import java.time.format.DateTimeFormatter;  // Clase para dar formato a fechas/horas

// Clase que representa un producto en el sistema
//constructor
public class Producto {
    // Atributos (propiedades) del producto
    private int id;                  // Identificador único del producto
    private String nombre;           // Nombre o marca del producto
    private Categoria categoria;     // Categoría del producto (ejemplo: CAMISA, PANTALÓN)
    private double precio;           // Precio del producto
    private int stock;               // Cantidad disponible en inventario
    private LocalDateTime fechaRegistro; // Fecha y hora en que se registró el producto

    // Constructor: se ejecuta cuando se crea un nuevo Producto
    //ejemplo en mi crud:
    // Producto p = new Producto(1, "Camisa", Categoria.HOMBRE, 59.90, 10);
    // Con esa línea ya tengo un objeto completo con todos sus datos y la fecha de registro.
    public Producto(int id, String nombre, Categoria categoria, double precio, int stock) {
        this.id = id;                       // Asigna el ID
        this.nombre = nombre;               // Asigna el nombre
        this.categoria = categoria;         // Asigna la categoría
        this.precio = precio;               // Asigna el precio
        this.stock = stock;                 // Asigna el stock
        this.fechaRegistro = LocalDateTime.now(); // Guarda la fecha y hora actual
    }

    // Métodos Getters y Setters
    // Sirven para aplicar encapsulación (acceder o modificar los atributos de forma controlada)

    public int getId() {
        return id; }                 // Obtener ID
    public void setId(int id) {
        this.id = id; }       // Modificar ID

    public String getNombre() {
        return nombre; }      // Obtener nombre
    public void setNombre(String nombre) { this.nombre = nombre; } // Modificar nombre

    public Categoria getCategoria() {
        return categoria; }          // Obtener categoría
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria; } // Modificar categoría

    public double getPrecio() {
        return precio; }      // Obtener precio
    public void setPrecio(double precio) {
        this.precio = precio; } // Modificar precio

    public int getStock() {
        return stock; }           // Obtener stock
    public void setStock(int stock) {
        this.stock = stock; }        // Modificar stock

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro; } // Obtener fecha de registro
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; } // Modificar fecha de registro

    // Método para mostrar la fecha y hora en un formato legible
    public String getHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formato de fecha
        return fechaRegistro.format(formatter); // Retorna la fecha con el formato aplicado
    }
}
