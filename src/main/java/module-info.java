module be.ac.umons.razanajao.sddproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens be.ac.umons.razanajao.sddproject to javafx.fxml;
    exports be.ac.umons.razanajao.sddproject;
}