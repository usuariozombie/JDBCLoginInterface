package com.example.jdbclogin;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.opencsv.CSVWriter;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que proporciona métodos para exportar datos de un TableView a un archivo CSV.
 * Esta clase utiliza OpenCSV para escribir los datos en un archivo CSV.
 *
 * @author edupime
 */
public class ExportCSVController {

    /**
     * Exporta los datos de un TableView a un archivo CSV.
     *
     * @param tableView TableView que contiene los datos a exportar.
     */
    public static void exportToCSV(TableView<ObservableList<String>> tableView) {
        // Configurar el selector de archivos
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("data.csv");
        File selectedFile = fileChooser.showSaveDialog(null);

        // Verificar si se seleccionó un archivo
        if (selectedFile != null) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(selectedFile))) {
                // Obtener las columnas de la tabla
                ObservableList<TableColumn<ObservableList<String>, ?>> columns = tableView.getColumns();

                // Escribir los encabezados de las columnas en el archivo CSV
                String[] headers = columns.stream()
                        .map(TableColumn::getText)
                        .toArray(String[]::new);
                writer.writeNext(headers);

                // Escribir los datos de la tabla en el archivo CSV
                for (ObservableList<String> row : tableView.getItems()) {
                    String[] rowData = row.toArray(String[]::new);
                    writer.writeNext(rowData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
