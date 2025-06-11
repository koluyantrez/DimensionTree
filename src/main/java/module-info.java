module be.ac.umons.razanajao.sddproject {

    opens be.ac.umons.razanajao.sddproject to javafx.fxml;
    exports be.ac.umons.razanajao.sddproject;
    requires javafx.controls;
    requires javafx.base;
}