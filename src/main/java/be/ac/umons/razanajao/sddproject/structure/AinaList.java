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

        int valSplit = (int) Math.ceil(xray.size()/2);
        ArrayList<Point> firstHalfX = new ArrayList<>();
        ArrayList<Point> secondHalfX = new ArrayList<>();
        ArrayList<Point> firstHalfY = new ArrayList<>();
        ArrayList<Point> secondHalfY = new ArrayList<>();

        if(depth%2==1) {
            firstHalfY = new ArrayList<>(this.yankee.subList(0, valSplit));
            secondHalfY = new ArrayList<>(this.yankee.subList(valSplit, yankee.size()));
            firstHalfX = new ArrayList<>();
            secondHalfX = new ArrayList<>();
            for (Point p : this.xray) {
                if (firstHalfY.contains(p)) {
                    firstHalfX.add(p);
                } else {
                    secondHalfX.add(p);
                }
            }
        }else {
            firstHalfX = new ArrayList<>(this.xray.subList(0, valSplit));
            secondHalfX = new ArrayList<>(this.xray.subList(valSplit, xray.size()));
            firstHalfY = new ArrayList<>();
            secondHalfY = new ArrayList<>();
            for (Point p : this.yankee) {
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
