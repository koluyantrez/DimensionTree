package be.ac.umons.razanajao.sddproject.structure;

import java.util.ArrayList;
import java.util.Set;

/**
 * Describes a kD-tree. Here, it was designed to manage data in 2D.
 *
 *
 */

public class KdTree<D> extends BSTree<D> {

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
            KdTree<D> kdtLeft = buildKdTree(cl.getFirst(), 1 + depth);
            KdTree<D> kdtRight = buildKdTree(cl.getSecond(), 1 + depth);
            return new KdTree(cl, kdtLeft, kdtRight);
        }
    }


    public ArrayList<Point> searchKdTreeX(KdTree<CoupleList> root, double c1, double c2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (root.isLeaf()) {
            Point p = root.getData().singlePoint();
            p.inXray(c1, c2);
            listing.add(p);
            return listing;
        } else {
            CoupleList current = root.getData();
            if(current.getFirstHalfX().getFirst().getX() >= c1 && current.getFirstHalfX().getLast().getX() <= c2) { //line 4
                listing = root.getFromLeaf();
                return listing;
            } else {
                if (!(current.getFirstHalfX().getFirst().getX() > c2 && current.getFirstHalfX().getLast().getX() < c1)) { //line 7
                    return searchKdTreeX(root.getLeft(), c1, c2);
                } else {
                    if (current.getSecondHalfX().getFirst().getX() >= c1 && current.getSecondHalfX().getLast().getX() <= c2) { //line 10
                        listing = root.getFromLeaf();
                        return listing;
                    } else {
                        if (!(current.getSecondHalfX().getFirst().getX() > c2 && current.getSecondHalfX().getLast().getX() < c1)) { //line 13
                            return searchKdTreeX(root.getRight(), c1, c2);
                        } else {
                            return listing;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Point> searchKdTreeY(KdTree<CoupleList> root, double c1, double c2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (root.isLeaf()) {
            Point p = root.getData().singlePoint();
            p.inYankee(c1, c2);
            listing.add(p);
            return listing;
        } else {
            CoupleList current = root.getData();
            if (current.getFirstHalfY().getFirst().getY() >= c1 && current.getFirstHalfY().getLast().getY() <= c2) {
                listing = root.getFromLeaf();
                return listing;
            } else {
                if (!(current.getFirstHalfY().getFirst().getY() > c2 && current.getFirstHalfY().getLast().getY() < c1)) {
                    return searchKdTreeY(root.getLeft(), c1, c2);
                } else {
                    if (current.getSecondHalfY().getFirst().getY() >= c1 && current.getSecondHalfY().getLast().getY() <= c2) {
                        listing = root.getFromLeaf();
                        return listing;
                    } else {
                        if (!(current.getSecondHalfY().getFirst().getY() > c2 && current.getSecondHalfY().getLast().getY() < c1)) {
                            return searchKdTreeY(root.getRight(), c1, c2);
                        } else {
                            return listing;
                        }
                    }
                }
            }
        }
    }



    public ArrayList<Point> searchKdTree(KdTree<CoupleList> root, double a1,double a2,double b1,double b2) {
        ArrayList<Point> alx = new ArrayList<>();
        ArrayList<Point> aly = new ArrayList<>();
        if(a1!=0 && a2!=0)
            alx = searchKdTreeX(root,a1,a2);

        if(b1!=0 && b2!=0)
            aly = searchKdTreeY(root,b1,b2);

        if(alx==null){
            return aly;
        }else if(aly==null){
            return aly;
        }else{
            for(Point p : alx){
                if(!aly.contains(p))
                    alx.add(p);
            }
            return alx;
        }
    }


    public ArrayList<Point> getFromLeaf() {
        ArrayList<Point> result = new ArrayList<>();
        catcherPoint((KdTree<CoupleList>) this, result);
        return result;
    }

    private void catcherPoint(KdTree<CoupleList> node, ArrayList<Point> pointResult) {
        if (node.isLeaf()) {
            pointResult.add(node.getData().singlePoint());
        } else {
            catcherPoint(node.getLeft(), pointResult);
            catcherPoint(node.getRight(), pointResult);
        }
    }
}
