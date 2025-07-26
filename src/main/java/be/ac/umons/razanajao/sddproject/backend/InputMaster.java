package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.frontend.Hermes;
import be.ac.umons.razanajao.sddproject.structure.CoupleList;
import be.ac.umons.razanajao.sddproject.structure.KdTree;
import be.ac.umons.razanajao.sddproject.structure.Point;

import java.util.ArrayList;

/**
 * This class manage the user request. We consider that the user are allowed to remove, rename, select et put some
 * data.
 *
 */
public class InputMaster {
    private static final String NAME = "[a-zA-Z0-9_\\.]+";
    private static final String NUMBER = "-?\\d+(\\.\\d+)?";
    private static final String CONDITION = "("+NAME+"\\s*(>=|<=)\\s*"+NUMBER+"|"+NAME+"\\s+in\\s+\\["+NUMBER+",\\s*"+NUMBER+"\\])";
    private static final String REGEX_SELECTOR = "^SELECT\\s+"+NAME+
                                          "(\\s*,\\s*"+NAME+")?"+
                                          "\\s+FROM\\s+"+NAME+
                                          "\\s+WHERE\\s+"+CONDITION+
                                          "(\\s+AND\\s+"+CONDITION+")?$";

    /**
     * It ensures that there are the extension for thie file.
     *
     * @param desti     The file name in the input.
     * @return          The name with the extension.
     */
    public static String extension(String desti){
        if(!desti.endsWith(".txt"))
            desti=desti+".txt";
        return desti;
    }

    /**
     * This function is the first step of the input analysis. It checks the first word and apply the good function if
     * it manages. Otherwise, the request are invalid.
     *
     * @param input The user request from the TextField.
     */
    public static boolean fireWall(String input, String desti, Table t,CoupleList cl, KdTree<CoupleList> kdt){
        boolean touch = false;
        desti=extension(desti);
        if(input.length()>2) {
            String first = input.split(" ")[0];
            switch (first){
                case "SELECT":
                    if(input.trim().matches(REGEX_SELECTOR)){
                        t.buildFromList(selectOne(input,desti,t,kdt));
                    }else{
                        Hermes.red(first+" is invalid !");
                    }
                    break;
                case "REMOVE":
                    remove(input,desti, t,cl);
                    break;
                case "ADD":
                    add(input,desti,t,cl);
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
    public static void add(String input, String target, Table t, CoupleList cl){

        String desti = input.split("IN")[1].trim();
        if(!input.split("IN")[0].trim().endsWith("IN"))
            Hermes.red("Syntax error : it must be IN");

        desti=extension(desti);

        if(desti.equals(target)){
            t.add(input,cl);
        }else{
            Hermes.red("The file " + desti + " does not exist");
        }
    }

    /**
     * This function allows to remove a row from the current table. But it does not write in the text file.
     *
     * @param input     The input to consider
     * @param t         The current table.
     */
    public static void remove(String input, String target,Table t, CoupleList cl){
        String[] parser = input.split(" ");
        if(parser.length!=4 || !parser[2].equals("FROM"))
            Hermes.red("Syntax error, please read the help");

        if(!parser[parser.length-1].endsWith(".txt")){
            parser[parser.length-1]=parser[parser.length-1]+".txt";
        }

        try{
            String desti = input.split("FROM")[1].trim();
            desti=extension(desti);
            if(desti.equals(target)){
                t.remove(Double.parseDouble(parser[1]),cl);
            }else{
                Hermes.red("The file " + desti + " does not exist");
            }
        } catch (NumberFormatException e) {
            Hermes.red("Syntax error : number");
        }
    }

    public static ArrayList<Point> selectOne(String input, String target, Table t, KdTree<CoupleList> kdt){
        String[] parser = input.split("[,\\s\\[\\]]+");
        String desti = "";
        if(t.contains(parser[1])) {
            if(parser[3].equals("FROM")) {
                if(t.contains(parser[2])) {
                    desti = extension(parser[4]);
                }else{
                    Hermes.red("Check the spelling 1");
                    return null;
                }
            }else{
                desti = extension(parser[3]);
            }
        }else{
            Hermes.red("Check the spelling 2");
            return null;
        }
        if(desti.equals(target)){
            return selectTwo(input.split("WHERE")[1].trim(),t,kdt);
        }else{
            Hermes.red("The file " + desti + " does not exist");
            return null;
        }
    }

    public static ArrayList<Point> selectTwo(String afterWhere, Table t, KdTree<CoupleList> kdt){
        String[] allCond = afterWhere.split("\\s+AND\\s+");
        double[] compare = new double[4];
        for(String c : allCond){
            String[] parser = c.split("[,\\s\\[\\]]+");
            if(parser[0].equals(t.getHeader()[0])){
                switch(parser[1]){
                    case "<=":
                        compare[0] = Double.NEGATIVE_INFINITY;
                        compare[1] = Double.parseDouble(parser[2]);
                        break;
                    case ">=":
                        compare[1] = Double.POSITIVE_INFINITY;
                        compare[0] = Double.parseDouble(parser[2]);
                        break;
                    case "in":
                        compare[0] = Double.parseDouble(parser[2]);
                        compare[1] = Double.parseDouble(parser[3]);
                        break;
                }
            }else if(parser[0].equals(t.getHeader()[1])){
                switch(parser[1]){
                    case "<=":
                        compare[2] = Double.NEGATIVE_INFINITY;
                        compare[3] = Double.parseDouble(parser[2]);
                        break;
                    case ">=":
                        compare[3] = Double.POSITIVE_INFINITY;
                        compare[2] = Double.parseDouble(parser[2]);
                        break;
                    case "in":
                        compare[2] = Double.parseDouble(parser[2]);
                        compare[3] = Double.parseDouble(parser[3]);
                        break;
                }
            }else{
                Hermes.red("Check the spelling 3");
            }
        }
        System.out.println("InputMaster 207 |"+compare[0]+" "+compare[1]+" "+compare[2]+" "+compare[3]);
        return kdt.searchKdTree(kdt,compare[0],compare[1],compare[2],compare[3]);
    }
}
