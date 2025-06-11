package be.ac.umons.razanajao.sddproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TestOrthogonalRangeSearching extends Application {

    private final Pane root = new Pane();


    @Override
    public void start(Stage stage) throws IOException {





        Scene scene = new Scene(root, 1400, 870);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}