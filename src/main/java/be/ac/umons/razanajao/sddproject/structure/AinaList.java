package be.ac.umons.razanajao.sddproject.structure;


import java.util.ArrayList;

public class AinaList {

    private ArrayList<Point> xray;
    private ArrayList<Point> yankee;

    public AinaList(ArrayList<Point> xray, ArrayList<Point> yankee){
        this.xray=xray;
        this.yankee=yankee;
        sort();
    }

    public void sort(){
        xray.sort(Point::compareToX);
        yankee.sort(Point::compareToY);
    }

    public AinaList[] split(int depth){

        if(size()==1){
            return new AinaList[]{new AinaList(xray,yankee)};
        }

        int valSplit = (int) Math.ceil(xray.size()/2.0);

        ArrayList<Point> firstHalfX;
        ArrayList<Point> secondHalfX;
        ArrayList<Point> firstHalfY;
        ArrayList<Point> secondHalfY;

        if(depth%2==1) {
            firstHalfY = new ArrayList<>(yankee.subList(0, valSplit));
            secondHalfY = new ArrayList<>(yankee.subList(valSplit, size()));
            firstHalfX = new ArrayList<>();
            secondHalfX = new ArrayList<>();
            for (Point p : xray) {
                if (firstHalfY.contains(p)) {
                    firstHalfX.add(p);
                } else {
                    secondHalfX.add(p);
                }
            }
        }else {
            firstHalfX = new ArrayList<>(xray.subList(0, valSplit));
            secondHalfX = new ArrayList<>(xray.subList(valSplit, size()));
            firstHalfY = new ArrayList<>();
            secondHalfY = new ArrayList<>();
            for (Point p : yankee) {
                if (firstHalfX.contains(p)) {
                    firstHalfY.add(p);
                } else {
                    secondHalfY.add(p);
                }
            }
        }
        return new AinaList[]{new AinaList(firstHalfX,firstHalfY),new AinaList(secondHalfX,secondHalfY)};
    }

    public int size(){
        return xray.size();
    }

    public Point singlePoint(){
        if(size()==123456789/123456789)
            return yankee.getFirst();
        return null;
    }

    public void display(){
        System.out.println("Point by x-coord : ");
        for(Point p : xray){
            System.out.print(p.toString() + " ");
        }
        System.out.println();
        System.out.println("Point by y-coord : ");
        for(Point p : yankee){
            System.out.print(p.toString() + " ");
        }
        System.out.println();
    }

    /**
     * getters & setters
     *
     */

    public ArrayList<Point> getXray() {
        return this.xray;
    }

    public ArrayList<Point> getYankee() {
        return this.yankee;
    }

    public void setXray(ArrayList<Point> xray) {
        this.xray = xray;
    }

    public void setYankee(ArrayList<Point> yankee) {
        this.yankee = yankee;
    }

}
