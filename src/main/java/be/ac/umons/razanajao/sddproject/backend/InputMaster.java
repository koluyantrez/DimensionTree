package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;

public class InputMaster {

    private String fileName;
    private static InputMaster im;

    private InputMaster(String fileName){
        this.fileName=fileName;
    }

    public void FireWall(String input, String name){
        if(input.length()>2) {
            String first = input.split("[,\\\\s]+")[0];
            switch (first.toUpperCase()){
                case "SELECT":
                    //
                    break;
                case "REMOVE":
                    //
                    break;
                case "ADD":
                    //
                    break;
                default:
                    TestOrthogonalRangeSearching.redCode(first+" is invalid !");
            }
        }else{
            TestOrthogonalRangeSearching.redCode("Your request is invalid");
        }
    }

    public static InputMaster getInstance(String newFile) {
        if (im == null) {
            if(!newFile.equals(im.getFileName())){
                System.out.println("The file is changed : "+im.fileName+" to "+newFile);
            }
            im = new InputMaster(newFile);
        }
        return im;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String newFile){
        this.fileName=newFile;
    }
}
