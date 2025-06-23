package be.ac.umons.razanajao.sddproject.backend;

import be.ac.umons.razanajao.sddproject.TestOrthogonalRangeSearching;

public class Table {

    int x,y;
    String[] header;
    String[][] data;

    public Table(int x, int y){
        this.x=x;
        this.y=y;
        this.header = new String[y];
        this.data = new String[x][y];
    }

    public Table(String[] header, String[][] data){
        this.header=header;
        this.data=data;
        this.x=data[0].length;
        this.y=header.length;
    }


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

    public void add(String request){
        String[] arr = request.split(",");
        if(arr.length!=this.header.length){
            TestOrthogonalRangeSearching.redCode("The query data is invalid (size)");
        }
        try{
            for(int i=0;i<arr.length-1;i++){
                Integer.parseInt(arr[i]);
            }
            int newX = this.x+1;
            String[][] update = new String[newX][this.y];

            for(int i=0;i<this.x;i++) {
                update[i] = getData()[i];
            }
            update[this.x] = arr;
            setData(update);
            setX(newX);
            TestOrthogonalRangeSearching.greenCode(request+" are added");

        } catch (NumberFormatException e) {
            TestOrthogonalRangeSearching.redCode("The query data is invalid (data)");
        }
    }

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
