package be.ac.umons.razanajao.sddproject.structure;

import java.util.ArrayList;


/**
 * This class is a data structure. It is composed of two sorted arrayList. The first is ordered according to the
 * x-coordinates and the second is ordered according to the y-coordinates. We cannot make any modifications.
 *
 */
public class CoupleList {

    private ArrayList<Point> xray;
    private ArrayList<Point> yankee;
    private CoupleList fi,se;

    private ArrayList<Point> firstHalfX;
    private ArrayList<Point> secondHalfX;
    private ArrayList<Point> firstHalfY;
    private ArrayList<Point> secondHalfY;

    /**
     * Composed by two arrayList following x-axis and y-axis.
     *
     * @param xray      ArrayList of Point sorted by x-coordinate.
     * @param yankee    ArrayList of Point sorted by y-coordinate.
     */
    public CoupleList(ArrayList<Point> xray, ArrayList<Point> yankee){
        this.xray=xray;
        this.yankee=yankee;
        sort();
    }

    /**
     * This method sorts the ainaList using compareToX and compareToY.
     *
     */
    public void sort(){
        xray.sort(Point::compareToX);
        yankee.sort(Point::compareToY);
    }

    /**
     * It initializes each ArrayList and the CoupleList from the split formed with the ArrayList.
     *
     * @param depth     The current depth.
     */
    public void split(int depth){
        if(secondHalfY!=null){
            return;
        }
        if(size()==1){
            return;
        }

        int valSplit = (int) Math.ceil(xray.size()/2.0);


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
        fi = new CoupleList(firstHalfX,firstHalfY);
        se = new CoupleList(secondHalfX,secondHalfY);
    }

    /**
     * The size of an AinaList is the length of one of the ArrayList. We assume that the both ArrayList has the same
     * size.
     *
     * @return  The size of the AinaList
     */
    public int size(){
        return xray.size();
    }

    /**
     * If there is anly one data in the CoupleList, this method gives this last data.
     *
     * @return  Point the only data from the ArrayList.
     */
    public Point singlePoint(){
        if(size()==1)
            return yankee.getFirst();
        return null;
    }

    /**
     * It allows to see the content of the AinaList in the terminal.
     *
     */
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

    public void add(Point p){
        getXray().add(p);
        getYankee().add(p);
        sort();
    }

    public void remove(Point p){
        getXray().remove(p);
        getYankee().remove(p);
        sort();
    }


    /**
     * Show on the terminal the content of the structure. The data is displayed according to their place in the two
     * lists (sorted by x-coordinate and y-coordinate).
     *
     * @return  The content of the structure.
     */
    @Override
    public String toString(){
        String out = "Point by x-coordinate : \n";
        for(Point p : xray){
            out = out+p.toString() + "|";
        }
        out = out+'\n';
        out = out+"Point by y-coordinate : \n";
        for(Point p : yankee){
            out = out+p.toString() + "|";
        }
        return out;
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

    public ArrayList<Point> getFirstHalfX() {
        return firstHalfX;
    }

    public ArrayList<Point> getSecondHalfX() {
        return secondHalfX;
    }

    public ArrayList<Point> getFirstHalfY() {
        return firstHalfY;
    }

    public ArrayList<Point> getSecondHalfY() {
        return secondHalfY;
    }

    /**
     * Return the first part of the main split
     *
     * @return  CoupleList with all point smaller than the cut point included.
     */
    public CoupleList getFirstPart() {
        return fi;
    }

    /**
     * Return the second part of the main split
     *
     * @return  CoupleList with all point larger than the cut point.
     */
    public CoupleList getSecondPart() {
        return se;
    }

    public Point getCut(int depth){
        int id = (int) Math.ceil(xray.size()/2.0);
        if(depth%2==0){
            return xray.get(id);
        }else{
            return yankee.get(id);
        }
    }


}
