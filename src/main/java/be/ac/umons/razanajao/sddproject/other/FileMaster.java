package be.ac.umons.razanajao.sddproject.other;


import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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


    public static ArrayList<String> initDefault() {
        String[] dataListed = DATA_ACCESS.list();
        ArrayList<String> aldl = new ArrayList<>();
        if(dataListed != null) {
            aldl.addAll(Arrays.asList(dataListed));
        }
        return aldl;
    }

    public static boolean mapChooser(Stage s) {
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
}
