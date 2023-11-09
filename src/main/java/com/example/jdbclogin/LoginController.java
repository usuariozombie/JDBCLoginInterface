package com.example.jdbclogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Controlador para la vista de inicio de sesión.
 * Muestra un formulario para ingresar el nombre de usuario y la contraseña.
 *
 * @author edupime
 */
public class LoginController {

    /**
     * Campo de texto para el nombre de usuario
     */
    @FXML
    private TextField usernameField;

    /**
     * Campo de texto para la contraseña
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Maneja el evento de clic en el botón de inicio de sesión.
     *
     * @param event El evento de clic en el botón.
     */
    public void onLoginButtonClick(ActionEvent event) {
        // Obtiene el nombre de usuario y la contraseña ingresados
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Verifica si el nombre de usuario o la contraseña están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            // Muestra una ventana de alerta en caso de campos vacíos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Username and password cannot be empty");
            alert.showAndWait();
            return;
        }

        // Establece la conexión a la base de datos usando el nombre de usuario y la contraseña
        Connection connection = establishDatabaseConnection(username, password);

        if (connection != null) {
            // Si la conexión es exitosa, la establece en la aplicación principal y carga la pantalla de selección de tabla
            LoginApplication.setDatabaseConnection(connection);
            try {
                LoginApplication.loadTableSelectionScreen();
                closeLoginWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Muestra una ventana de alerta si el nombre de usuario o la contraseña son incorrectos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Username or password is incorrect");
            alert.showAndWait();
        }
    }

    /**
     * Establece la conexión a la base de datos utilizando el nombre de usuario y la contraseña proporcionados.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return La conexión a la base de datos, o null si hay un error.
     */
    private Connection establishDatabaseConnection(String username, String password) {
        try {
            // Configura la URL de conexión a la base de datos MySQL
            String url = "jdbc:mysql://localhost/northwind";
            url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Intenta establecer la conexión y la devuelve
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException ex) {
            // En caso de error, devuelve null
            return null;
        }
    }

    /**
     * Cierra la ventana de inicio de sesión.
     */
    private void closeLoginWindow() {
        // Obtiene la referencia a la ventana actual y la cierra
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
