package be.ac.umons.razanajao.sddproject.other;

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
