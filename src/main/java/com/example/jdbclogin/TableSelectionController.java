package com.example.jdbclogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Controlador para la vista de selección de tabla.
 * Muestra un ComboBox con las tablas disponibles en la base de datos.
 */
public class TableSelectionController {

    /**
     * ComboBox que muestra las tablas disponibles en la base de datos.
     */
    @FXML
    private ComboBox<String> tableComboBox;

    /**
     * Método llamado automáticamente cuando se inicializa el controlador.
     * Llama a {@link #populateTableComboBox()} para poblar el ComboBox con las tablas disponibles al inicio.
     */
    public void initialize() {
        populateTableComboBox();
    }

    /**
     * Método llamado cuando se selecciona una tabla en el ComboBox y se hace clic en el botón.
     * Verifica si se ha seleccionado una tabla, establece la tabla seleccionada en la aplicación principal
     * y carga la pantalla de vista de tabla.
     * Muestra una alerta de error si no se ha seleccionado ninguna tabla.
     *
     * @param event Evento de clic en el botón.
     */
    public void onTableSelection(ActionEvent event) {
        String selectedTable = tableComboBox.getValue();

        if (selectedTable != null) {
            LoginApplication.setSelectedTable(selectedTable);

            try {
                LoginApplication.loadTableViewScreen();
                closeTableSelectionWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a table");
            alert.showAndWait();
        }
    }

    /**
     * Método para poblar el ComboBox con los nombres de las tablas disponibles en la base de datos.
     * Obtiene la conexión a la base de datos desde LoginApplication y agrega los nombres de las tablas al ComboBox.
     * Muestra un error en la consola si hay un problema con la base de datos.
     */
    public void populateTableComboBox() {
        Connection connection = LoginApplication.getDatabaseConnection();

        if (connection != null) {
            List<String> tableNames = new ArrayList<>();

            try {
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables("northwind", null, "%", new String[] { "TABLE" });

                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    tableNames.add(tableName);
                }

                tableComboBox.getItems().addAll(tableNames);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para cerrar la ventana de selección de tabla.
     * Oculta la ventana actual del ComboBox.
     */
    private void closeTableSelectionWindow() {
        tableComboBox.getScene().getWindow().hide();
    }
}
