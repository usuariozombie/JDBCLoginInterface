package com.example.jdbclogin;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Clase que representa un conjunto de datos de la tabla en formato XML.
 * Esta clase se utiliza para almacenar los datos de la tabla.
 */
@XmlRootElement(name = "data")
public class TableData {

    /**
     * Lista que almacena las filas de la tabla
     */
    private List<TableRow> rows;

    /**
     * Método getter para obtener la lista de filas.
     *
     * @return La lista de filas de la tabla.
     */
    @XmlElement(name = "row")
    public List<TableRow> getRows() {
        return rows;
    }

    /**
     * Método setter para establecer la lista de filas.
     *
     * @param rows La lista de filas a establecer.
     */
    public void setRows(List<TableRow> rows) {
        this.rows = rows;
    }
}
