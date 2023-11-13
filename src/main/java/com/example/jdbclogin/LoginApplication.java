package com.example.jdbclogin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

/**
 * Clase principal de la aplicación que extiende de Application.
 * Inicia la aplicación JavaFX y proporciona métodos para cargar las pantallas de la aplicación.
 *
 * @author edupime
 */
public class LoginApplication extends Application {

    /**
     * Variable estática para almacenar la conexión a la base de datos.
     */
    private static Connection databaseConnection;

    /**
     * Variable estática para almacenar el nombre de la tabla seleccionada.
     */
    private static String selectedTable;

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param stage El objeto Stage principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Carga y agrega el icono de la aplicación
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.jpg")));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Icon not found");
        }

        // Carga la vista de inicio de sesión desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Configura la escena y muestra la ventana principal
        stage.setTitle("NorthWind Database Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Getter para obtener la conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     */
    public static Connection getDatabaseConnection() {
        return databaseConnection;
    }

    /**
     * Setter para establecer la conexión a la base de datos.
     *
     * @param connection La nueva conexión a la base de datos.
     */
    public static void setDatabaseConnection(Connection connection) {
        databaseConnection = connection;
    }

    /**
     * Método para cargar la pantalla de selección de tabla.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public static void loadTableSelectionScreen() throws IOException {
        // Carga la vista de selección de tabla desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("tableSelection-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage = new Stage();

        try {
            // Intenta cargar y agregar el icono de la aplicación
            Image icon = new Image(Objects.requireNonNull(LoginApplication.class.getResourceAsStream("/icon.jpg")));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Icon not found");
        }

        // Configura la escena y muestra la ventana de selección de tabla
        stage.setTitle("NorthWind Database");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para cargar la pantalla de vista de tabla.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public static void loadTableViewScreen() throws IOException {
        // Carga la vista de tabla desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("tableView-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();

        try {
            // Intenta cargar y agregar el icono de la aplicación
            Image icon = new Image(Objects.requireNonNull(LoginApplication.class.getResourceAsStream("/icon.jpg")));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Icon not found");
        }

        // Configura la escena y muestra la ventana de vista de tabla
        stage.setTitle("NorthWind Database - " + selectedTable);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Getter para obtener la tabla seleccionada.
     *
     * @return El nombre de la tabla seleccionada.
     */
    public static String getSelectedTable() {
        return selectedTable;
    }

    /**
     * Setter para establecer la tabla seleccionada.
     *
     * @param table El nombre de la nueva tabla seleccionada.
     */
    public static void setSelectedTable(String table) {
        selectedTable = table;
    }
}
