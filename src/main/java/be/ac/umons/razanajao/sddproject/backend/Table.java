package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.frontend.Hermes;
import be.ac.umons.razanajao.sddproject.structure.CoupleList;
import be.ac.umons.razanajao.sddproject.structure.Point;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is made to recover data from a file in backend. We distinct the names of columns and data.
 *
 */
public class Table {

    private int x,y;
    private String[] header;
    private String[][] data;

    /**
     * A table is defined by dimension (row and column). With this parameter, we can initialise the array for header
     * and data. The row with the name of colums is not counted for x.
     *
     * @param x The number of rows for data
     * @param y The number of columns for data. It is also the size of the header.
     */
    public Table(int x, int y){
        this.x=x;
        this.y=y;
        this.header = new String[y];
        this.data = new String[x][y];
    }

    /**
     * If there are already the arrays, we can use them to initialise the table. the case that dimension does not
     * match is managed in another file.
     *
     * @param header    String[] with the names of colums.
     * @param data      String[][] with data.
     */
    public Table(String[] header, String[][] data){
        this.header=header;
        this.data=data;
        this.x=data.length;
        this.y=header.length;
    }

    /**
     * This method show the content of table in the terminal, this is like the content of the text file.
     *
     */
    public void display(){
        for(String s : this.header){
            System.out.print(s+" ");
        }
        System.out.println();
        for(String[] r : this.data){
            for(String d : r){
                System.out.print(d+" ");
            }
            System.out.println();
        }
    }

    /**
     * This method allows to put new data in the table, the format must be respected.
     *
     * @param request   The new data to put in the table.
     * @param cl        The CoupleList linked to the table.
     */
    public void add(String request, CoupleList cl){
        String[] arr = request.split("[,\\s]+");
        if(arr.length!=6){
            Hermes.red("The query data is invalid (size)");
            return;
        }
        try{
            int newX = this.x+1;
            String[][] update = new String[newX][this.y];

            for(int i=0;i<this.x;i++) {
                update[i] = getData()[i];
            }
            String[] newData= new String[this.y];
            for (int j = 0; j < this.y; j++) {
                newData[j] = arr[j+1];
            }
            update[this.x] = newData;
            Point injection = new Point(Double.parseDouble(arr[1]),Double.parseDouble(arr[2]),arr[3]);

            setData(update);
            setX(newX);
            cl.add(injection);
            Hermes.green(arr[3]+" are added");

        } catch (NumberFormatException e) {
            Hermes.green("The query data is invalid (data)");
        }
    }

    /**
     * This method allows to remove a rows of data with his index.
     *
     * @param number    The index of the rows with the data that the user want to remove.
     * @param cl        The CoupleList linked to the table.
     */
    public void remove(double number, CoupleList cl){
        if(number>this.getX()){
            Hermes.green("The index must be lower than "+this.getX());
            return;
        }else{
            int newX = this.x-1;
            String[][] update = new String[newX][this.y];

            if(number==this.x){
                String removed = String.join(", ", getData()[this.x-1]);
                Hermes.green(removed+" are removed");
            }
            int p=0;
            for(int i=0;i<this.x;i++) {
                if(i!=number-1) {
                    update[i-p] = getData()[i];
                }else{
                    ++p;
                    String removed = String.join(", ", getData()[i]);
                    Hermes.green(removed+" are removed");
                    String[] bye = removed.split(",\\s*");
                    Point delete = new Point(Double.parseDouble(bye[0]),Double.parseDouble(bye[1]),bye[2]);
                    cl.remove(delete);
                }
            }
            setData(update);
            setX(newX);
        }
    }


    /**
     * This method initializes a CoupleList with the current content.
     *
     * @return CoupleList with the current content
     */
    public CoupleList giveDataset(){
        Point recruiter;
        ArrayList<Point> xavi = new ArrayList<>();
        ArrayList<Point> yamal = new ArrayList<>();
        for(String[] l : getData()){
            recruiter = new Point(Double.parseDouble(l[0]),Double.parseDouble(l[1]),l[2]);
            xavi.add(recruiter);
            yamal.add(recruiter);
        }
        return new CoupleList(xavi,yamal);
    }

    /**
     * This method checks if the parameter are a title of column.
     *
     * @param target    The name that you want to know if it is a title of column
     * @return          True if it is the case.
     */
    public boolean contains(String target){
        for (String s : getHeader()){
            if (s.equals(target))
                return true;
        }
        return false;
    }

    /**
     * It builds a Table by an ArrayList of Point.
     *
     * @param which The part of the input before "FROM".
     * @param al    The list of data to put in a Table.
     */
    public void buildFromList(String which, ArrayList<Point> al){

        String[] col = which.split("\\s*SELECT\\s*|,\\s*|\\s");
        String[][] newData;
        if(!(col.length==3)){
            if(col[1].equals(getHeader()[0])){
                setHeader(new String[]{getHeader()[0],getHeader()[2]});
                newData = new String[al.size()][2];
                for(int i=0;i<al.size();i++){
                    newData[i] = new String[]{String.valueOf(al.get(i).getX()),al.get(i).getInfo()};
                }
            }else{
                setHeader(new String[]{getHeader()[1],getHeader()[2]});
                newData = new String[al.size()][2];
                for(int i=0;i<al.size();i++){
                    newData[i] = new String[]{String.valueOf(al.get(i).getY()),al.get(i).getInfo()};
                }
            }
        }else{
            newData = new String[al.size()][3];
            for(int i=0;i<al.size();i++){
                newData[i] = new String[]{String.valueOf(al.get(i).getX()),String.valueOf(al.get(i).getY()),al.get(i).getInfo()};
            }
        }
        setData(newData);
    }



    /**
     * getters & setters
     *
     */
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public String[] getHeader(){
        return this.header;
    }

    public String[][] getData(){
        return this.data;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeader(String[] header) {
        setY(header.length);
        this.header = header;
    }

    public void setData(String[][] data) {
        setX(data.length);
        setY(data[0].length);
        this.data = data;
    }


}
