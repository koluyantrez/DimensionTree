package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.backend.FileMaster;

import be.ac.umons.razanajao.sddproject.backend.InputMaster;
import be.ac.umons.razanajao.sddproject.backend.Table;
import be.ac.umons.razanajao.sddproject.frontend.FrontGrid;
import be.ac.umons.razanajao.sddproject.structure.CoupleList;
import be.ac.umons.razanajao.sddproject.structure.KdTree;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

public class TestOrthogonalRangeSearching extends Application {

    private final Pane root = new Pane();
    private final ScrollPane sp = new ScrollPane();
    GridPane gp = new GridPane();
    Table t;
    KdTree<CoupleList> kdt = new KdTree<>();
    CoupleList cl;

    private final TextField inputUser = new TextField("SELECT weight FROM for_test.txt WHERE weight in [70,100]");
    private final TextField newFileName = new TextField("Name your file.txt");
    private final Button importFile = new Button("Import your file");
    private final Button killer = new Button("Remove this file");
    private final Button help = new Button("?");
    private final Button displayGrid = new Button("Show as a table");
    private final Button displayTree = new Button("Show as a tree");
    private final Button overw = new Button("Overwrite the file");
    private final Button save = new Button("⬇");
    private ChoiceBox data;


    private static Label notifGreen = new Label();
    private static Label notifRed = new Label();

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

    /**
     * Composition of the help scene. The text comes from other/help.txt.
     *
     * @param stage         The stage of the project.
     * @param mainScene     The scene to change.
     */
    private void helpScene(Stage stage, Scene mainScene) {
        Button back = new Button("←");
        Label label = new Label();
        label.setWrapText(true);
        label.setMaxWidth(1300);  // pour éviter un affichage trop large

        FileMaster.toHelp(label);  // charge le texte

        // ScrollPane pour le texte
        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.setPrefSize(1400, 800);
        scrollPane.setLayoutX(50);
        scrollPane.setLayoutY(50);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("help-scene");

        back.setOnAction(e -> stage.setScene(mainScene));
        label.getStyleClass().add("descri");
        back.getStyleClass().add("help");

        Pane root = new Pane();
        root.getChildren().addAll(scrollPane, back);

        back.setLayoutX(1420);
        back.setLayoutY(25);

        Scene scene2 = new Scene(root, 1500, 900);
        scene2.getStylesheets().add(getClass().getResource("/css/queulorior.css").toExternalForm());

        stage.setScene(scene2);
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
        sp.getStyleClass().add("table-scroll");


        dataList = FileMaster.initDefault();
        data = new ChoiceBox(FXCollections.observableArrayList(dataList));
        data.getSelectionModel().selectFirst();





        inputUser.getStyleClass().add("leana");
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
                    if (FileMaster.importFile(stage)) {
                        dataList.add(imp);
                        data.setItems(FXCollections.observableArrayList(dataList));
                        data.getSelectionModel().selectLast();
                    }
                });

        overw.setOnAction(
                e -> {
                    FileMaster.writer(t,data.getValue().toString(),true);
                }
        );

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
                    t = FileMaster.createTable(data.getValue().toString());
                    gp = FrontGrid.onGridPane(t);
                    gp.setHgap(80);
                    gp.setVgap(100);
                    gp.getStyleClass().add("grid");
                    sp.setContent(gp);
                    greenCode(data.getValue()+" is displayed as a table");
                    cl = t.giveDataset();
                    kdt = kdt.buildKdTree(cl,0);
                }
        );

        help.setOnAction(
                e -> helpScene(stage,scene)
        );

        inputUser.setOnKeyPressed(
                e -> {
                    if(e.getCode() == KeyCode.ENTER){
                        if(InputMaster.fireWall(inputUser.getText(), data.getValue().toString(),t,cl,kdt)) {
                            dataList = FileMaster.initDefault();
                            data.setItems(FXCollections.observableArrayList(dataList));
                            data.getSelectionModel().selectFirst();
                        }
                        gp = FrontGrid.onGridPane(t);
                        gp.setHgap(80);
                        gp.setVgap(100);
                        gp.getStyleClass().add("grid");
                        sp.setContent(gp);
                    }
                }
        );

        save.setOnAction(
                e -> {
                    FileMaster.writer(t,newFileName.getText(),false);
                    dataList = FileMaster.initDefault();
                    data.setItems(FXCollections.observableArrayList(dataList));
                    data.getSelectionModel().selectLast();
                }
        );

        displayTree.setOnAction(
                e -> {
                    kdt.print();
                }
        );



        scene.getStylesheets().add(getClass().getResource("/css/queulorior.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}