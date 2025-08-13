package be.ac.umons.razanajao.sddproject.structure;

import java.util.ArrayList;

/**
 * Describes a kD-tree. Here, it was designed to manage data in 2D.
 *
 * NOTHING is the information when the result of a request is null.
 */

public class KdTree<D> extends BSTree<D> {

    public KdTree() {
        super();
    }

    public KdTree(D d, KdTree<D> l, KdTree<D> r) {
        super(d, l, r);
    }


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
        if (cl.size() <= 1) {
            return new KdTree(cl, new KdTree(), new KdTree());
        } else {
            cl.split(depth);
            KdTree<D> kdtLeft = buildKdTree(cl.getFirstPart(), 1 + depth);
            KdTree<D> kdtRight = buildKdTree(cl.getSecondPart(), 1 + depth);
            return new KdTree(cl, kdtLeft, kdtRight);
        }
    }

    /**
     * It gives an ArrayList with all points satisfying the request.
     *
     * @param node  The start of the research.
     * @param x1    The lower bound of x-coordinate
     * @param x2    The lower bound of y-coordinate
     * @param y1    The lower bound of y-coordinate
     * @param y2    The higher bound of y-coordinate
     * @return      An ArrayList with all points satisfying the request. Here, it may be empty.
     */
    public ArrayList<Point> searchKdTree(KdTree<CoupleList> node,double x1,double x2,double y1,double y2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (node.isLeaf()) {
            Point p = node.getData().singlePoint();
            if (p.inYankee(y1, y2) && p.inXray(x1, x2)) {
                listing.add(p);
            }
        }else{
            CoupleList current = node.getData();
            boolean leftCompletelyIn = current.getFirstPart().getXray().getFirst().getX() >= x1 &&
                                       current.getFirstPart().getXray().getLast().getX() <= x2 &&
                                       current.getFirstPart().getYankee().getFirst().getY() >= y1 &&
                                       current.getFirstPart().getYankee().getLast().getY() <= y2;

            boolean leftPartiallyIn = current.getFirstPart().getXray().getLast().getX() >= x1 &&
                                      current.getFirstPart().getXray().getFirst().getX() <= x2 &&
                                      current.getFirstPart().getYankee().getLast().getY() >= y1 &&
                                      current.getFirstPart().getYankee().getFirst().getY() <= y2;

            if(leftCompletelyIn){
                listing.addAll(node.getLeft().getFromLeaf());
            }else if(leftPartiallyIn) {
                listing.addAll(searchKdTree(node.getLeft(), x1, x2, y1, y2));
            }

            boolean rightCompletelyIn = current.getSecondPart().getXray().getFirst().getX() >= x1 &&
                                        current.getSecondPart().getXray().getLast().getX() <= x2 &&
                                        current.getSecondPart().getYankee().getFirst().getY() >= y1 &&
                                        current.getSecondPart().getYankee().getLast().getY() <= y2;

            boolean rightPartiallyIn = current.getSecondPart().getXray().getLast().getX() >= x1 &&
                                       current.getSecondPart().getXray().getFirst().getX() <= x2 &&
                                       current.getSecondPart().getYankee().getLast().getY() >= y1 &&
                                       current.getSecondPart().getYankee().getFirst().getY() <= y2;

            if(rightCompletelyIn){
                listing.addAll(node.getRight().getFromLeaf());
            }else if(rightPartiallyIn) {
                listing.addAll(searchKdTree(node.getRight(), x1, x2, y1, y2));
            }
        }
        return listing;
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


    public void print(KdTree<CoupleList> node, int gap, int wide){
        if(node.isEmpty())
            return;

        if(node.isLeaf()) {
            for(int i=0;i<gap;i++){
                System.out.print(" ");
            }
            System.out.print("("+gap/wide+") "+node.getData().singlePoint());
        }else {
            print(node.getRight(), gap+wide,wide);

            System.out.println();
            if(wide==14)
                System.out.println();

            for(int i=0;i<gap;i++){
                System.out.print(" ");
            }
            if (node.height() % 2 == 0) {
                System.out.print("("+gap/wide+") "+node.getData().getFirstHalfX().getLast().getInfo());
            }else{
                System.out.print("("+gap/wide+") "+node.getData().getFirstHalfY().getLast().getInfo());
            }
            System.out.println();
            if(wide==14)
                System.out.println();

            print(node.getLeft(), gap+wide,wide);
        }
    }
}
