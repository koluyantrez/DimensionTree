package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.backend.FileMaster;

import be.ac.umons.razanajao.sddproject.backend.Table;
import be.ac.umons.razanajao.sddproject.frontend.FrontGrid;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class TestOrthogonalRangeSearching extends Application {

    private final Pane root = new Pane();
    private final ScrollPane sp = new ScrollPane();
    GridPane gp = new GridPane();

    private final TextField inputUser = new TextField();
    private final TextField newFileName = new TextField("Name your file.txt");
    private final Button importFile = new Button("import your file");
    private final Button killer = new Button("Remove this file");
    private final Button help = new Button("?");
    private final Button displayGrid = new Button("Show as a table");
    private final Button displayTree = new Button("Show as a tree");
    private final Button overw = new Button("Overwrite the file");
    private final Button save = new Button("⬇");
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


    private void helpScene(Stage stage,Scene mainScene) {
        Button back = new Button("←");
        Label label = new Label();
        label.setWrapText(true);
        label.setMaxWidth(1300);  // Définit une largeur max pour éviter un affichage trop large


        FileMaster.toHelp(label);


        back.setOnAction(e -> stage.setScene(stage.getScene()));
        label.getStyleClass().add("descri");
        back.getStyleClass().add("help");
        Pane h = new Pane();
        h.getChildren().addAll(label,back);

        label.setLayoutX(50);
        label.setLayoutY(50);

        back.setLayoutX(1420);
        back.setLayoutY(25);
        Scene scene2 = new Scene(h, 1500, 900);
        scene2.getStylesheets().add(getClass().getResource("/css/queulorior.css").toExternalForm());

        stage.setScene(scene2);
        back.setOnAction(
                e -> {
                    stage.setScene(mainScene);
                });
    }


    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Project of SDD2 : Orthogonal Range Searching");
        Scene scene = new Scene(root, 1500, 900);

        sp.setVisible(false);
        sp.setPrefViewportWidth(620);
        sp.setPrefViewportHeight(800);
        sp.setPannable(true);
        sp.setLayoutX(800);
        sp.setLayoutY(50);
        sp.setContent(gp);


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
        importFile.setLayoutX(370);
        importFile.setLayoutY(180);

        killer.getStyleClass().add("button");
        killer.setLayoutX(50);
        killer.setLayoutY(180);

        displayTree.getStyleClass().add("button");
        displayTree.setLayoutX(370);
        displayTree.setLayoutY(250);

        data.getStyleClass().add("choice-box");
        data.setLayoutX(50);
        data.setLayoutY(20);

        help.getStyleClass().add("help");
        help.setLayoutX(550);
        help.setLayoutY(20);

        displayGrid.getStyleClass().add("button");
        displayGrid.setLayoutX(50);
        displayGrid.setLayoutY(250);

        overw.getStyleClass().add("button");
        overw.setLayoutX(50);
        overw.setLayoutY(320);

        save.getStyleClass().add("help");
        save.setLayoutX(580);
        save.setLayoutY(320);

        newFileName.getStyleClass().add("custom-input");
        newFileName.setLayoutX(370);
        newFileName.setLayoutY(320);

        root.getChildren().addAll(

                inputUser,
                displayTree,
                notifGreen,
                newFileName,
                notifRed,
                displayGrid,
                importFile,
                data,
                save,
                help,
                overw,
                killer,
                sp
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

        displayGrid.setOnAction(
                e -> {
                    sp.setVisible(true);
                    Table t = FileMaster.createTable(data.getValue().toString());
                    t.display();
                    gp = FrontGrid.onGridPane(t);
                    gp.setHgap(80);
                    gp.setVgap(100);
                    gp.getStyleClass().add("grid");
                    sp.setContent(gp);
                    greenCode(data.getValue()+" is displayed as a table");
                }
        );

        displayTree.setOnAction(
                e -> {
                    greenCode(data.getValue()+" is displayed as a tree");
                }
        );

        help.setOnAction(
                e -> helpScene(stage,scene)
        );

        scene.getStylesheets().add(getClass().getResource("/css/queulorior.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}