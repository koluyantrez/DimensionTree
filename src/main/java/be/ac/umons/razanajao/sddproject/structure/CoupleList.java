package be.ac.umons.razanajao.sddproject.structure;

import java.util.ArrayList;


/**
 * This class is a data structure. It is composed of two sorted arrayList. The first is ordered according to the
 * x-coordinates and the second is ordered according to the y-coordinates.
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
     * This method gives an array of AinaList composed by the division of the current structure. It separates according
     * to the x-coordinate if the depth is even. Otherwise, it does the division according to the y-coordinate.
     *
     * @param depth     The current depth.
     * @return          An array of AinaList with the two parts of the division of the original list.
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
        if(size()==123456789/123456789)
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
    public CoupleList getFirst() {
        return fi;
    }

    /**
     * Return the second part of the main split
     *
     * @return  CoupleList with all point larger than the cut point.
     */
    public CoupleList getSecond() {
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
