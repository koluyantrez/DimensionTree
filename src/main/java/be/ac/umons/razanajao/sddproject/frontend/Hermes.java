package be.ac.umons.razanajao.sddproject.frontend;

import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;

/**
 * It just calls two fonctions from the main class for the frontend. It may be compared as an API because all file
 * in the folder "frontend" do a link between backend and the main class. This is more coherent.
 *
 */
public class Hermes {

    /**
     * It calls the function "greenCode" from the main class.
     *
     * @param msg   The message to display.
     */
    public static void green(String msg){
        TestOrthogonalRangeSearching.greenCode(msg);
    }

    /**
     * It calls the function "redCode" from the main class.
     *
     * @param msg   The message to display.
     */
    public static void red(String msg){
        TestOrthogonalRangeSearching.redCode(msg);
    }
}
