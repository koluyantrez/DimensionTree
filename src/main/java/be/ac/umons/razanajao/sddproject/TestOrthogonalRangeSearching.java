package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.other.FileMaster;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrthogonalRangeSearching extends Application {

    private final Pane root = new Pane();
    private Canvas canvas;

    private final TextField inputUser = new TextField();
    private final Button importFile = new Button("import your file");
    private ChoiceBox data;


    private static final Label notifGreen = new Label("");
    private static final Label notifRed = new Label("");

    private ArrayList<String> dataList = new ArrayList<>();


    /**
     * Show a message in case of error
     *
     * @param msg the message to display
     */
    public static void redCode(String msg) {
        notifGreen.setText("");
        notifRed.setText(msg);
    }

    /**
     * Show a notification
     *
     * @param msg the message to display
     */
    public static void greenCode(String msg) {
        notifRed.setText("");
        notifGreen.setText(msg);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Project of SDD2 : Orthogonal Range Searching");
        canvas = new Canvas(870, 870);
        dataList = FileMaster.initDefault();
        data = new ChoiceBox(FXCollections.observableArrayList(dataList));
        data.getSelectionModel().selectFirst();

        inputUser.getStyleClass().add("input");
        inputUser.setLayoutX(50);
        inputUser.setLayoutY(50);

        notifGreen.getStyleClass().add("notifGreen");
        notifGreen.setLayoutX(55);
        notifGreen.setLayoutY(800);

        notifRed.getStyleClass().add("notifRed");
        notifRed.setLayoutX(55);
        notifRed.setLayoutY(800);

        importFile.getStyleClass().add("button");
        importFile.setLayoutX(50);
        importFile.setLayoutY(150);

        data.getStyleClass().add("choice-box");
        data.setLayoutX(300);
        data.setLayoutY(150);



        root.getChildren().addAll(
                inputUser,
                notifGreen,
                notifRed,
                importFile,
                data
        );

        Scene scene = new Scene(root, 1500, 900);
        scene.getStylesheets().add(getClass().getResource("/css/queulorior.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}