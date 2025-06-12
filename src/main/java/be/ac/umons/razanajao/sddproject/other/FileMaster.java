package be.ac.umons.razanajao.sddproject.other;


import java.io.File;
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
}
