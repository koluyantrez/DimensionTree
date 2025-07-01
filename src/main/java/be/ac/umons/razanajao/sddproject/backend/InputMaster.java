package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;

public class InputMaster {


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

    public static void rename(String input){
        String[] arInput = input.split("\\s+");
        if(arInput[2].equals("TO") && arInput.length==4){
            FileMaster.rename(arInput[3],arInput[1]);
        }else{
            TestOrthogonalRangeSearching.redCode("Your RENAME request is invalid");
        }
    }


}
