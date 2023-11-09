/**
 * This is the module-info.java file that is required for Java 9+.
 */
module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires com.opencsv;


    opens com.example.jdbclogin to javafx.fxml, java.xml.bind;
    exports com.example.jdbclogin;
}