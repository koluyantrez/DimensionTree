package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.frontend.Hermes;

/**
 * This class manage the user request. We consider that the user are allowed to remove, rename, select et put some
 * data.
 *
 */
public class InputMaster {
    private final String NAME = "[a-zA-Z0-9_]+";
    private final String NUMBER = "-?\\d+(\\.\\d+)?";
    private final String CONDITION = "("+NAME+"\\s*(>=|<=)\\s*"+NUMBER+"|"+NAME+"\\s+in\\s+\\["+NUMBER+",\\s*"+NUMBER+"\\])";
    private final String REGEX_SELECTOR = "^SELECT\\s+"+NAME+
                                          "(\\s+,\\s*"+NAME+")?"+
                                          "\\s+FROM\\s+"+NAME+
                                          "\\s+WHERE\\s+"+CONDITION+
                                          "(\\s+AND\\s+"+CONDITION+")?$";

    /**
     * This function is the first step of the input analysis. It checks the first word and apply the good function if
     * it manages. Otherwise, the request are invalid.
     *
     * @param input The user request from the TextField.
     */
    public static boolean fireWall(String input, String desti, Table t){
        boolean touch = false;
        if(!desti.endsWith(".txt"))
            desti=desti+".txt";
        if(input.length()>2) {
            String first = input.split(" ")[0];
            switch (first){
                case "SELECT":
                    //
                    break;
                case "REMOVE":
                    remove(input, desti, t);
                    break;
                case "ADD":
                    add(input,desti,t);
                    break;
                case "RENAME":
                    rename(input);
                    touch = true;
                    break;
                default:
                    Hermes.red(first+" is invalid !");
                    break;
            }
            return touch;
        }else{
            Hermes.red("Your request is invalid");
            return touch;
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
            Hermes.red("Your RENAME request is invalid");
        }
    }

    /**
     * This function allows to add some data in the current table. But it does not write in the text file.
     *
     * @param input     The user input.
     * @param target    The target file.
     * @param t         The current table.
     */
    public static void add(String input, String target, Table t){

        String desti = input.split("IN")[1].trim();
        if(!input.split("IN")[0].trim().endsWith("IN"))
            Hermes.red("Syntax error : it must be IN");

        if(!desti.endsWith(".txt")){
            desti=desti+".txt";
        }

        if(desti.equals(target)){
            t.add(input);
        }else{
            Hermes.red("The file " + desti + " does not exist");
        }
    }

    /**
     * This function allows to remove a row from the current table. But it does not write in the text file.
     *
     * @param input
     * @param target
     * @param t
     */
    public static void remove(String input, String target, Table t){
        String[] parser = input.split(" ");
        if(parser.length!=4 || !parser[2].equals("FROM"))
            Hermes.red("Syntax error, please read the help");

        if(!parser[parser.length-1].endsWith(".txt")){
            parser[parser.length-1]=parser[parser.length-1]+".txt";
        }

        try{
            t.remove(Double.parseDouble(parser[1]));
        } catch (NumberFormatException e) {
            Hermes.red("Syntax error : number");
        }
    }
}
