package com.example.jdbclogin;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controlador que maneja la vista de resultados.
 * Muestra los resultados de las consultas en un área de texto.
 */
public class ResultController {

    /**
     * Área de texto en la que se mostrarán los resultados
     */
    @FXML
    private TextArea resultTextArea;

    /**
     * Método para establecer el texto en el área de resultados.
     *
     * @param text El texto a establecer en el área de resultados.
     */
    public void setResultText(String text) {
        resultTextArea.setText(text);
    }
}
