module com.product.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.product.app to javafx.fxml;
    exports com.product.app;
}