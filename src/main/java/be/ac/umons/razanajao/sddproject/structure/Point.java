package be.ac.umons.razanajao.sddproject.structure;

/**
 * This class represent a point in 2D space. It provides some methods according to the specificities of orthogonal
 * range search. The accuracy of comparaison is set to four digits behind the comma.
 */
public class Point{

    private double x;
    private double y;
    private final double ACCURACY = 10e-4;

    /**
     * initializes a point with the given x-coordinate and y-coordinate
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * Compares this point with another based on the x-coordinate.
     * If the x-coordinates are equal, it compares by y-coordinate.
     *
     * @param p The point to compare with.
     * @return 1 if its x-coordinate is greater, -1 if smaller, or comparison by y-coordinate if x-coordinate is equal.
     */
    public int compareToX(Point p){
        if(this.equalsX(p)){
            return this.compareToY(p);
        }
        if(this.getX() > p.getX()){
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
        if(this.equalsY(p)){
            return this.compareToX(p);
        }
        if(this.getY() > p.getY()){
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

    @Override
    public String toString(){
        return "("+x + ";" + y+")";
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
