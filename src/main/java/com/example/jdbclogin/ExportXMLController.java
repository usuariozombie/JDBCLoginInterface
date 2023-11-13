package com.example.jdbclogin;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
     * @param databaseConnection Conexión a la base de datos.
     * @param tableName Nombre de la tabla a exportar.
     */
    public static void exportToXML(TableView<ObservableList<String>> tableView, Connection databaseConnection, String tableName) {
        // Configurar el selector de archivos
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(tableName + ".xml");
        File selectedFile = fileChooser.showSaveDialog(null);

        // Verificar si se seleccionó un archivo
        if (selectedFile != null) {
            try {
                // Crear un contexto JAXB para la clase TableData
                JAXBContext context = JAXBContext.newInstance(TableData.class);
                Marshaller marshaller = context.createMarshaller();

                // Configurar el marshaller para formatear la salida XML
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                // Obtener los nombres de las columnas desde la base de datos
                String query = "SELECT column_name FROM information_schema.columns WHERE table_name = '" + tableName + "'";
                Statement columnStatement = databaseConnection.createStatement();
                ResultSet columnResultSet = columnStatement.executeQuery(query);

                List<String> columnNames = new ArrayList<>();
                while (columnResultSet.next()) {
                    columnNames.add(columnResultSet.getString("column_name"));
                }

                // Obtener las filas de la tabla
                ObservableList<ObservableList<String>> rows = tableView.getItems();

                // Generar el archivo XML con los nombres de columnas dinámicos
                StringBuilder xmlContent = new StringBuilder();
                xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
                xmlContent.append("<data>\n");

                for (ObservableList<String> row : rows) {
                    xmlContent.append("    <row>\n");
                    int columnIndex = 0;
                    for (String columnName : columnNames) {
                        String cellValue = row.get(columnIndex);
                        xmlContent.append("        <").append(columnName).append(">").append(cellValue).append("</").append(columnName).append(">\n");
                        columnIndex++;
                    }
                    xmlContent.append("    </row>\n");
                }

                xmlContent.append("</data>\n");

                // Guardar el contenido en un archivo XML
                FileWriter fileWriter = new FileWriter(selectedFile);
                fileWriter.write(xmlContent.toString());
                fileWriter.close();

                // Cerrar recursos
                columnResultSet.close();
                columnStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
