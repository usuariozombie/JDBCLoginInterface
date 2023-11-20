package com.example.jdbclogin;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Controlador para la vista de la tabla.
 * Muestra los datos de la tabla seleccionada en un TableView.
 */
public class TableViewController {

    /**
     * TableView que muestra los datos de la tabla seleccionada.
     */
    @FXML
    private TableView<ObservableList<String>> tableData;

    /**
     * Método llamado automáticamente cuando se carga la vista asociada a este controlador.
     * Obtiene la conexión a la base de datos y la tabla seleccionada desde la aplicación de inicio de sesión.
     * Consulta todos los datos de la tabla seleccionada y genera dinámicamente las columnas y carga los datos en la TableView.
     * Muestra un error en la consola si hay un problema con la base de datos.
     */
    public void initialize() {
        Connection connection = LoginApplication.getDatabaseConnection();
        String selectedTable = LoginApplication.getSelectedTable();

        if (connection != null && selectedTable != null) {
            try {
                String query = "SELECT * FROM " + selectedTable;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                tableData.getColumns().clear();

                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    final int j = i - 1;
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                    column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().get(j)));
                    tableData.getColumns().add(column);
                }

                ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }
                tableData.setItems(data);

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método llamado cuando se presiona el botón de retroceso.
     * Navega de nuevo a la pantalla de selección de tabla.
     * Muestra un error en la consola si hay un problema al cargar la pantalla.
     */
    public void onBackButtonClick() {
        try {
            LoginApplication.loadTableSelectionScreen();
            closeTableViewWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cerrar la ventana de la tabla actual.
     * Oculta la ventana actual de la TableView.
     */
    private void closeTableViewWindow() {
        tableData.getScene().getWindow().hide();
    }

    /**
     * Método llamado al hacer clic en el botón de exportar a CSV.
     * Llama al método de ExportCSVController para exportar los datos de la tabla actual a un archivo CSV.
     *
     */
    @FXML
    private void exportToCSV() {
        ExportCSVController.exportToCSV(tableData);
    }

    /**
     * Método llamado al hacer clic en el botón de exportar a XML.
     * Llama al método de ExportXMLController para exportar los datos de la tabla actual a un archivo XML.
     *
     */
    @FXML
    private void exportToXML() {
        Connection databaseConnection = LoginApplication.getDatabaseConnection();
        String tableName = LoginApplication.getSelectedTable();
        ExportXMLController.exportToXML(tableData, databaseConnection, tableName);
    }
}