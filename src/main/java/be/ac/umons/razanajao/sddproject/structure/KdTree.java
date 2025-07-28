package be.ac.umons.razanajao.sddproject.structure;

import java.util.ArrayList;

/**
 * Describes a kD-tree. Here, it was designed to manage data in 2D.
 *
 * NOTHING is the information when the result of a request is null.
 */

public class KdTree<D> extends BSTree<D> {

    private final String NOTHING = "NONE";

    public KdTree() {
        super();
    }

    public KdTree(D d, KdTree<D> l, KdTree<D> r) {
        super(d, l, r);
    }

    // Redefine to avoid constant casting
    public KdTree<D> getLeft() {
        return (KdTree<D>) super.getLeft();
    }

    public KdTree<D> getRight() {
        return (KdTree<D>) super.getRight();
    }


    /**
     * This method builds a kD-tree with a dataset contained in a CoupleList.
     *
     * @param cl    The source of the tree.
     * @param depth The current depth in the main tree.
     * @return A KdTree with the data contained in the CoupleList given.
     */
    public KdTree buildKdTree(CoupleList cl, int depth) {
        if (cl.size() == 1) {
            return new KdTree(cl, new KdTree(), new KdTree());
        } else {
            cl.split(depth);
            KdTree<D> kdtLeft = buildKdTree(cl.getFirstPart(), 1 + depth);
            KdTree<D> kdtRight = buildKdTree(cl.getSecondPart(), 1 + depth);
            return new KdTree(cl, kdtLeft, kdtRight);
        }
    }

    /**
     * This method gives an ArrayList with all points satisfying the request following to x-coordinate.
     *
     * @param node  The current position in the KdTree.
     * @param c1    The shorter value to compare according to x-coordinate.
     * @param c2    The greater value to compare according to x-coordinate.
     * @return      An ArrayList with all points satisfying the request following to x-coordinate.
     */
    public ArrayList<Point> searchKdTreeX(KdTree<CoupleList> node, double c1, double c2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (node.isLeaf()) {
            Point p = node.getData().singlePoint();
            if(p.inXray(c1, c2)) {
                listing.add(p);
            }
        } else {
            CoupleList current = node.getData();
            if(current.getFirstPart().getXray().getFirst().getX() >= c1 && current.getFirstPart().getXray().getLast().getX() <= c2) { //line 4
                listing.addAll(node.getLeft().getFromLeaf());
            }else if(current.getFirstPart().getXray().getLast().getX() >= c1 && current.getFirstPart().getXray().getFirst().getX() <= c2) { //line 7
                    listing.addAll(searchKdTreeX(node.getLeft(), c1, c2));
            }
            if(current.getSecondPart().getXray().getFirst().getX() >= c1 && current.getSecondPart().getXray().getLast().getX() <= c2) { //line 10
                listing.addAll(node.getRight().getFromLeaf());
            } else if(current.getSecondPart().getXray().getLast().getX() >= c1 && current.getSecondPart().getXray().getFirst().getX() <= c2)  { //line 13
                listing.addAll(searchKdTreeX(node.getRight(), c1, c2));
            }
        }
        return listing;
    }

    /**
     * This method gives an ArrayList with all points satisfying the request following to y-coordinate.
     *
     * @param node  The current position in the KdTree.
     * @param c1    The shorter value to compare according to y-coordinate.
     * @param c2    The greater value to compare according to y-coordinate.
     * @return      An ArrayList with all points satisfying the request following to y-coordinate.
     */
    public ArrayList<Point> searchKdTreeY(KdTree<CoupleList> node, double c1, double c2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (node.isLeaf()) {
            Point p = node.getData().singlePoint();
            if (p.inYankee(c1, c2)) {
                listing.add(p);
            }
        } else {
            CoupleList current = node.getData();
            if (current.getFirstPart().getYankee().getFirst().getY() >= c1 && current.getFirstPart().getYankee().getLast().getY() <= c2) { //line 4
                listing.addAll(node.getLeft().getFromLeaf());
            } else if (current.getFirstPart().getYankee().getLast().getY() >= c1 && current.getFirstPart().getYankee().getFirst().getY() <= c2) { //line 7
                listing.addAll(searchKdTreeY(node.getLeft(), c1, c2));
            }
            if (current.getSecondPart().getYankee().getFirst().getY() >= c1 && current.getSecondPart().getYankee().getLast().getY() <= c2) { //line 10
                listing.addAll(node.getRight().getFromLeaf());
            } else if (current.getSecondPart().getYankee().getLast().getY() >= c1 && current.getSecondPart().getYankee().getFirst().getY() <= c2) { //line 13
                listing.addAll(searchKdTreeY(node.getRight(), c1, c2));
            }
        }
        return listing;
    }


    /**
     * It searches all points in its leaves such as they are in the interval [x1;x2] and [y1;y2]
     *
     * @param kdt   The KdTree(root) that we want to do some research.
     * @param a1    The shorter value to compare according to x-coordinate.
     * @param a2    The greater value to compare according to x-coordinate.
     * @param b1    The shorter value to compare according to y-coordinate.
     * @param b2    The greater value to compare according to y-coordinate.
     * @return      A resulting ArrayList from the merger between "searchKdTreeY" and "searchKdTreeX".
     */
    public ArrayList<Point> searchKdTree(KdTree<CoupleList> kdt,double a1,double a2,double b1,double b2) {
        ArrayList<Point> alx = new ArrayList<>();
        ArrayList<Point> aly = new ArrayList<>();
        if(a1!=0 || a2!=0) {
            alx = searchKdTreeX(kdt, a1, a2);
        }

        if(b1!=0 || b2!=0)
            aly = searchKdTreeY(kdt,b1,b2);


        if(alx.size()==0 && aly.size()==0){
            aly.add(new Point(0,0,NOTHING));
            return aly;
        }

        if(alx.size()==0){
            return aly;
        }else if(aly.size()==0){
            return alx;
        }else{
            for(Point p : new ArrayList<>(alx)) {
                if(!aly.contains(p)) {
                    alx.remove(p);
                }
            }

            return alx;
        }
    }

    /**
     * This method collects all points coming from leaves.
     *
     * @return  An ArrayList of point taht were in leaves.
     */
    public ArrayList<Point> getFromLeaf() {
        ArrayList<Point> result = new ArrayList<>();
        catcherPoint((KdTree<CoupleList>) this, result);
        return result;
    }

    /**
     * It walks the tree (node) to the leaves, and it puts them in "pointResult".
     *
     * @param node          The tree, or subtree, that we want to collect the data in its leaves.
     * @param pointResult   The collector of data.
     */
    public void catcherPoint(KdTree<CoupleList> node, ArrayList<Point> pointResult) {
        if(node.isLeaf()) {
            pointResult.add(node.getData().singlePoint());
        } else {
            catcherPoint(node.getLeft(), pointResult);
            catcherPoint(node.getRight(), pointResult);
        }
    }
}
