package be.ac.umons.razanajao.sddproject.backend;


import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

public class FileMaster {

    private static final File DATA_ACCESS =
            new File(
                    System.getProperty("user.dir")
                            + File.separator
                            + "src"
                            + File.separator
                            + "main"
                            + File.separator
                            + "resources"
                            + File.separator
                            + "data");
    private static final File HELP_FILE =
            new File(
                    System.getProperty("user.dir")
                            + File.separator
                            + "src"
                            + File.separator
                            + "main"
                            + File.separator
                            + "resources"
                            + File.separator
                            + "other"+ File.separator
                            + "help.txt");


    /**
     * This function gives an arraylist with the name of all data from resources.
     *
     * @return  ArrayList<String> with the name of all database.
     */
    public static ArrayList<String> initDefault() {
        String[] dataListed = DATA_ACCESS.list();
        ArrayList<String> aldl = new ArrayList<>();
        if(dataListed != null) {
            aldl.addAll(Arrays.asList(dataListed));
        }
        return aldl;
    }

    /**
     * It allows the user to import his data.
     *
     * @param s The main stage of the project
     * @return  True if it was added.
     */
    public static boolean importFile(Stage s) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a text file");

        File selectedFile = fileChooser.showOpenDialog(s);

        if (selectedFile != null && selectedFile.getName().toLowerCase().endsWith(".txt")) {
            try {
                Path source = selectedFile.toPath();
                Path destination = new File(DATA_ACCESS, selectedFile.getName()).toPath();
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                TestOrthogonalRangeSearching.imp = selectedFile.getName();
                TestOrthogonalRangeSearching.greenCode("Your file called "+selectedFile.getName()+" was successfully added");
                return true;
            } catch (IOException e) {
                TestOrthogonalRangeSearching.redCode("The import has failed");
            }
        } else {
            TestOrthogonalRangeSearching.redCode("Your file was not detected, it must be a text file.");
            return false;
        }
        return false;
    }

    /**
     * Deletes a file in the map directory.
     *
     * @param name The name of the file to delete.
     */
    public static void deleteFile(String name) {
        File target = new File(DATA_ACCESS + File.separator + name);
        try {
            if (target.isFile()) Files.delete(target.toPath());
        } catch (IOException e) {
            TestOrthogonalRangeSearching.redCode("No such file found");
        }
    }

    /**
     * This function puts the content of "help.txt" in a label. This is for the help scene.
     *
     * @param label The label to put the text.
     */
    public static void toHelp(Label label){
        try (BufferedReader reader = new BufferedReader(new FileReader(HELP_FILE))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            label.setText(content.toString());
        } catch (IOException e) {
            label.setText("Error help file : " + e.getMessage());
        }
    }

    /**
     * It is designed to put the names of the columns in an array of string.
     *
     * @param name  The name of the file.
     * @return      String[] with the names of the columns
     */
    public static String[] readHeader(String name) {
        String[] header = null;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_ACCESS + File.separator + name))) {
            String line = br.readLine();
            if (line != null) {
                int k = Integer.parseInt(line);
                header = new String[k];
                for (int i = 0; i < k; i++) {
                    header[i] = br.readLine();
                }
            }
        } catch (IOException e) {
            TestOrthogonalRangeSearching.redCode("Something wrong with the header");
        } catch (NumberFormatException e){
            TestOrthogonalRangeSearching.redCode("Something wrong with the number indication");
        }
        return header;
    }

    /**
     * It is designed to put the data in an 2D-array of string.
     *
     * @param name  The name of the file.
     * @return      String[][] with data.
     */
    public static String[][] readData(String name) {
        String[][] data = null;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_ACCESS + File.separator + name))) {
            String line = br.readLine();
            if (line == null)
                return null;

            int y = Integer.parseInt(line);
            for (int i=0;i<y;i++) {
                br.readLine();
            }


            line = br.readLine();
            if (line == null)
                return null;

            int x = Integer.parseInt(line);

            data = new String[x][];

            for (int i=0; i<x;i++) {
                line = br.readLine();
                if (line != null) {
                    data[i] = line.split(" ");
                }
            }

        } catch (IOException e) {
            TestOrthogonalRangeSearching.redCode("Something wrong with the file "+name);
        } catch (NumberFormatException e) {
            TestOrthogonalRangeSearching.redCode("Something wrong with the number indication");
        }
        return data;
    }

    /**
     * This function gives a Table object.
     *
     * @param name  The name of the file
     * @return      Table with names of columns and data
     */
    public static Table createTable(String name){
        return new Table(readHeader(name),readData(name));
    }


    /**
     * It allows to overwrite a file.
     *
     * @param t     The table with data.
     * @param name  The name of the file.
     */
    public static void overwrite(Table t, String name){
        if(t==null){
            TestOrthogonalRangeSearching.redCode("The table does not exist. Show the data as table or tree.");
            return;
        }
        try (FileWriter fw = new FileWriter(DATA_ACCESS+File.separator+name, false)) {
            fw.write(t.getY() + '\n');
            for (int i = 0; i < t.getY(); i++) {
                fw.write(t.getHeader()[i] + '\n');
            }
            fw.write(t.getX() + '\n');
            for (String[] babeth : t.getData()){
                for (int i = 0; i < t.getX(); i++) {
                    fw.write(babeth[i] + '\n');
                }
            }
            TestOrthogonalRangeSearching.greenCode("Overwriting of the file done");
        } catch (IOException e) {
            TestOrthogonalRangeSearching.redCode("Something wrong during the overwriting");
        }
    }

    /**
     * This function allows to create a file with the content of the current table.
     *
     * @param t     The current table.
     * @param name  The name of the new file.
     */
    public static void save(Table t, String name){  //TODO
        if(!name.endsWith(".txt"))
            name = name+".txt";
        if(t==null){
            TestOrthogonalRangeSearching.redCode("The table does not exist. Show the data as table or tree.");
            return;
        }
        try (FileWriter fw = new FileWriter(DATA_ACCESS+File.separator+name)) {
            fw.write(t.getY() + '\n');
            for (int i = 0; i < t.getY(); i++) {
                fw.write(t.getHeader()[i] + '\n');
            }
            fw.write(t.getX() + '\n');
            for (String[] gemarie : t.getData()){
                for (int i = 0; i < t.getX(); i++) {
                    fw.write(gemarie[i] + '\n');
                }
            }
            TestOrthogonalRangeSearching.greenCode("Your file has created");
        } catch (IOException e) {
            TestOrthogonalRangeSearching.redCode("Something wrong during the creation");
        }
    }

    /**
     * it allows to rename a file.
     *
     * @param newName   The new name of the file.
     * @param oldName   The old name of the file.
     */
    public static void rename(String newName, String oldName){
        if(!newName.endsWith(".txt"))
            newName = newName+".txt";
        if(!oldName.endsWith(".txt"))
            oldName = oldName+".txt";

        Path oldFile = Paths.get(DATA_ACCESS+File.separator+oldName);
        Path newFile = Paths.get(DATA_ACCESS+File.separator+newName);
        try {
            Files.move(oldFile, newFile);
            TestOrthogonalRangeSearching.greenCode("The file has been renamed successfully");
        } catch (Exception e) {
            TestOrthogonalRangeSearching.redCode("Failure of the rename");
        }
    }

}
