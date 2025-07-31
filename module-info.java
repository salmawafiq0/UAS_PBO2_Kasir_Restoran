module com.mycompany.kasir_restoran {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 
    requires java.base;
    
    opens com.mycompany.kasir_restoran to javafx.fxml;
    exports com.mycompany.kasir_restoran;
}
