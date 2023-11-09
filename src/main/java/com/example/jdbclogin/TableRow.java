package com.example.jdbclogin;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Clase que representa una fila de datos de la tabla en formato XML.
 * Esta clase se utiliza para almacenar los datos de una fila de la tabla
 */
public class TableRow {

    /**
     * Lista que almacena los valores de las celdas de la fila
     */
    private List<String> cells;

    /**
     * Método getter para obtener la lista de celdas.
     *
     * @return La lista de valores de las celdas en la fila.
     */
    @XmlElement(name = "cell")
    public List<String> getCells() {
        return cells;
    }

    /**
     * Método setter para establecer la lista de celdas.
     *
     * @param cells La lista de valores de las celdas a establecer.
     */
    public void setCells(List<String> cells) {
        this.cells = cells;
    }
}
