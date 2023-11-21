package com.example.jdbclogin;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Clase que proporciona métodos para exportar datos de un TableView a un archivo CSV.
 * Esta clase utiliza OpenCSV para escribir los datos en un archivo CSV.
 *
 * @author edupime
 */
public class ExportCSVController {

    /**
     * Exporta los datos de un TableView a un archivo CSV con punto y coma como delimitador.
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
            try (Writer writer = new FileWriter(selectedFile);
                 CSVWriter csvWriter = (CSVWriter) new CSVWriterBuilder(writer)
                         .withSeparator(';') // Establecer el delimitador como punto y coma
                         .build()) {

                // Obtener las columnas de la tabla
                ObservableList<TableColumn<ObservableList<String>, ?>> columns = tableView.getColumns();

                // Escribir los encabezados de las columnas en el archivo CSV
                String[] headers = columns.stream()
                        .map(TableColumn::getText)
                        .toArray(String[]::new);
                csvWriter.writeNext(headers);

                // Escribir los datos de la tabla en el archivo CSV
                for (ObservableList<String> row : tableView.getItems()) {
                    String[] rowData = row.toArray(String[]::new);
                    csvWriter.writeNext(rowData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}