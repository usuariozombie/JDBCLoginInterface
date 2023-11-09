package com.example.jdbclogin;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;

/**
 * Clase que proporciona métodos para exportar datos de un TableView a un archivo XML.
 * Esta clase utiliza JAXB para serializar los datos a XML.
 *
 * @author edupime
 */
public class ExportXMLController {

    /**
     * Exporta los datos de un TableView a un archivo XML.
     *
     * @param tableView TableView que contiene los datos a exportar.
     */
    public static void exportToXML(TableView<ObservableList<String>> tableView) {

        // Configurar el selector de archivos
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("data.xml");
        File selectedFile = fileChooser.showSaveDialog(null);

        // Verificar si se seleccionó un archivo
        if (selectedFile != null) {
            try {
                // Crear un contexto JAXB para la clase TableData
                JAXBContext context = JAXBContext.newInstance(TableData.class);
                Marshaller marshaller = context.createMarshaller();

                // Configurar el marshaller para formatear la salida XML
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                // Obtener las columnas de la tabla
                ObservableList<TableColumn<ObservableList<String>, ?>> columns = tableView.getColumns();

                // Crear un objeto TableData para almacenar los datos
                TableData tableData = new TableData();
                tableData.setRows(new ArrayList<>()); // Usar ArrayList en lugar de ObservableList

                // Obtener las filas de la tabla
                ObservableList<ObservableList<String>> rows = tableView.getItems();

                // Recorrer las filas y almacenarlas en el objeto TableData
                for (ObservableList<String> row : rows) {
                    // Crear un objeto TableRow para cada fila
                    TableRow tableRow = new TableRow();

                    // Crear una lista de celdas para almacenar los valores de la fila
                    ObservableList<String> cells = row;
                    tableRow.setCells(cells);

                    // Agregar la fila a la lista de filas
                    tableData.getRows().add(tableRow);
                }

                // Marshalling (serialización) de los datos a XML y guardar en el archivo seleccionado
                marshaller.marshal(tableData, selectedFile);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }
}
