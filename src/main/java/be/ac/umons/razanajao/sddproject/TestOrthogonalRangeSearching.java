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
    private final Button killer = new Button("Remove this file");
    private ChoiceBox data;


    private static final Label notifGreen = new Label("");
    private static final Label notifRed = new Label("");

    public static ArrayList<String> dataList = new ArrayList<>();
    public static String imp;


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
        inputUser.setLayoutY(100);

        notifGreen.getStyleClass().add("notifGreen");
        notifGreen.setLayoutX(55);
        notifGreen.setLayoutY(820);

        notifRed.getStyleClass().add("notifRed");
        notifRed.setLayoutX(55);
        notifRed.setLayoutY(820);

        importFile.getStyleClass().add("button");
        importFile.setLayoutX(300);
        importFile.setLayoutY(180);

        killer.getStyleClass().add("button");
        killer.setLayoutX(50);
        killer.setLayoutY(180);

        data.getStyleClass().add("choice-box");
        data.setLayoutX(50);
        data.setLayoutY(20);



        root.getChildren().addAll(
                inputUser,
                notifGreen,
                notifRed,
                importFile,
                data,
                killer
        );

        importFile.setOnAction(
                e -> {
                    if (FileMaster.mapChooser(stage)) {
                        dataList.add(imp);
                        data.setItems(FXCollections.observableArrayList(dataList));
                        data.getSelectionModel().selectLast();
                    }
                });

        killer.setOnAction(
                e -> {
                    String file = data.getValue().toString();
                    FileMaster.deleteFile(file);
                    dataList.remove(file);
                    data.setItems(FXCollections.observableArrayList(dataList));
                    data.getSelectionModel().selectLast();
                    greenCode("The file "+file+" was successfully removed");
                });

        Scene scene = new Scene(root, 1500, 900);
        scene.getStylesheets().add(getClass().getResource("/css/queulorior.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}