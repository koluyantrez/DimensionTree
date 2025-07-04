package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;


/**
 * This class is made to recover data from a file in backend. We distinct the names of columns and data.
 *
 */
public class Table {

    int x,y;
    String[] header;
    String[][] data;

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
     */
    public void add(String request){
        String[] arr = request.split("[,\\s]+");
        if(arr.length!=6){
            TestOrthogonalRangeSearching.redCode("The query data is invalid (size)");
            return;
        }
        try{
            int newX = this.x+1;
            System.out.println(this.x);
            String[][] update = new String[newX][this.y];

            for(int i=0;i<this.x;i++) {
                update[i] = getData()[i];
            }
            String[] newData= new String[this.y];
            for (int j = 0; j < this.y; j++) {
                newData[j] = arr[j+1];
            }
            update[this.x] = newData;

            setData(update);
            setX(newX);
            display();
            TestOrthogonalRangeSearching.greenCode(arr[3]+" are added");

        } catch (NumberFormatException e) {
            TestOrthogonalRangeSearching.redCode("The query data is invalid (data)");
        }
    }

    /**
     * This method allows to remove a rows of data with his index.
     *
     * @param number    The index of the rows with the data that the user want to remove.
     */
    public void remove(int number){
        if(number>this.getX()){
            TestOrthogonalRangeSearching.redCode("The index must be lower than "+this.getX());
        }else{
            int newX = this.x-1;
            String[][] update = new String[newX][this.y];
            for(int i=0;i<this.x;i++) {
                if(i!=number) {
                    update[i] = getData()[i];
                }else{
                    String removed = String.join(", ", getData()[i]);
                    TestOrthogonalRangeSearching.greenCode(removed+" are removed");
                }
            }
            setData(update);
            setX(newX);
        }

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
        this.header = header;
    }

    public void setData(String[][] data) {
        this.data = data;
    }


}
