package be.ac.umons.razanajao.sddproject.structure;

/**
 * This class represent a point in 2D space. It provides some methods according to the specificities of orthogonal
 * range search. The accuracy of comparaison is set to four digits behind the comma.
 */
public class Point{

    private double x,y;
    private String info;
    private final double ACCURACY = 10e-4;

    /**
     * Initializes a point with the given x-coordinate and y-coordinate
     *
     * @param x     The x-coordinate of the point.
     * @param y     The y-coordinate of the point.
     * @param info  Some short information.
     */
    public Point(double x, double y, String info){
        this.x=x;
        this.y=y;
        this.info=info;
    }

    /**
     * Compares this point with another based on the x-coordinate.
     * If the x-coordinates are equal, it compares by y-coordinate.
     *
     * @param p The point to compare with.
     * @return 1 if its x-coordinate is greater, -1 if smaller, or comparison by y-coordinate if x-coordinate is equal.
     */
    public int compareToX(Point p){
        if(equals(p)){
            return 0;
        }
        if(this.equalsX(p)){
            return this.compareToY(p);
        }else if(this.getX() > p.getX()){
            return 1;
        }else{
            return -1;
        }
    }

    /**
     * Compares this point with another based on the y-coordinate.
     * If the x-coordinates are equal, it compares by x-coordinate.
     *
     * @param p The point to compare with.
     * @return 1 if its y-coordinate is greater, -1 if smaller, or comparison by x-coordinate if y-coordinate is equal.
     */
    public int compareToY(Point p){
        if(equals(p)){
            return 0;
        }
        if(this.equalsY(p)){
            return this.compareToX(p);
        }else if(this.getY() > p.getY()){
            return 1;
        }else{
            return -1;
        }
    }

    /**
     * Make x-coordinate comparaison with the given point following the accuracy.
     *
     * @param p The point to compare with.
     * @return  True if the difference is negligible.
     */
    public boolean equalsX(Point p) {
        return Math.abs(this.x - p.x) < ACCURACY;
    }

    /**
     * Make x-coordinate comparaison with the given point following the accuracy.
     *
     * @param p The point to compare with.
     * @return  True if the difference is negligible.
     */
    public boolean equalsY(Point p) {
        return Math.abs(this.y - p.y) < ACCURACY;
    }

    /**
     *  Make x-coordinate and y-coordinate comparaison with the given point following the accuracy.
     *
     * @param p The point to compare with.
     * @return  True if they are same coordinate according to a certain precision.
     */
    public boolean equals(Point p){
        return equalsX(p) && equalsY(p);
    }

    /**
     * It checks if the point is in the interval [x1;x2] and [y1;y2].
     *
     * @param x1    The shorter value to compare according to x-coordinate.
     * @param x2    The greater value to compare according to x-coordinate.
     * @param y1    The shorter value to compare according to y-coordinate.
     * @param y2    The greater value to compare according to y-coordinate.
     * @return      True if x is in [x1;x2] and y is in [y1;y2].
     */
    public boolean in(double x1,double x2,double y1,double y2){
        return inXray(x1,x2) && inYankee(y1,y2);
    }

    /**
     * It checks if the point is in the interval [x1;x2].
     *
     * @param x1    The shorter value to compare according to x-coordinate.
     * @param x2    The greater value to compare according to x-coordinate.
     * @return      True if x is in [x1;x2].
     */
    public boolean inXray(double x1,double x2){
        return x1<=getX() && x2>=getX();
    }

    /**
     * It checks if the point is in the interval [y1;y2].
     *
     * @param y1    The shorter value to compare according to y-coordinate.
     * @param y2    The greater value to compare according to y-coordinate.
     * @return      True if x is in [y1;y2].
     */
    public boolean inYankee(double y1,double y2){
        return y1<=getY() && y2>=getY();
    }

    /**
     * It gives the string format of a point.
     *
     * @return String -> (x;y)->...
     */
    @Override
    public String toString(){
        return "("+x + ";" + y+")->"+info;
    }


    /**
     * Getters & Setters
     *
     */

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getInfo(){
        return info;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public void setY(double newY) {
        this.y = newY;
    }

    public void newInfo(String ni){
        this.info=ni;
    }
}
