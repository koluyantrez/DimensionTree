package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;

/**
 * This class manage the user request. We consider that the user are allowed to remove, rename, select et put some
 * data.
 *
 */
public class InputMaster {

    /**
     * This function is the first step of the input analysis. It checks the first word and apply the good function if
     * it manages. Otherwise, the request are invalid.
     *
     * @param input The user request from the TextField.
     */
    public static void FireWall(String input){
        if(input.length()>2) {
            String first = input.split(" ")[0];
            System.out.println(first);
            switch (first){
                case "SELECT":
                    //
                    break;
                case "REMOVE":
                    //
                    break;
                case "ADD":
                    //
                    break;
                case "RENAME":
                    rename(input);
                    break;
                default:
                    TestOrthogonalRangeSearching.redCode(first+" is invalid !");
                    break;
            }
        }else{
            TestOrthogonalRangeSearching.redCode("Your request is invalid");
        }
    }

    /**
     * This function allows to rename the file if the syntax is respected :
     * -> RENAME oldname TO newName
     *
     * @param input The user request from the TextField.
     */
    public static void rename(String input){
        String[] arInput = input.split("\\s+");
        if(arInput[2].equals("TO") && arInput.length==4){
            FileMaster.rename(arInput[3],arInput[1]);
        }else{
            TestOrthogonalRangeSearching.redCode("Your RENAME request is invalid");
        }
    }


}
