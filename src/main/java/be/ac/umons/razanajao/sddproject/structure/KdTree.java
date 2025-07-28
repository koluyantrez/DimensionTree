package be.ac.umons.razanajao.sddproject.structure;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Describes a kD-tree. Here, it was designed to manage data in 2D.
 *
 *
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

    public ArrayList<Point> searchKdTreeX(KdTree<CoupleList> root, double c1, double c2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (root.isLeaf()) {
            Point p = root.getData().singlePoint();
            if(p.inXray(c1, c2)) {
                listing.add(p);
            }
        } else {
            CoupleList current = root.getData();
            if(current.getFirstPart().getXray().getFirst().getX() >= c1 && current.getFirstPart().getXray().getLast().getX() <= c2) { //line 4
                listing.addAll(root.getLeft().getFromLeaf());
            }else if(current.getFirstPart().getXray().getLast().getX() >= c1 && current.getFirstPart().getXray().getFirst().getX() <= c2) { //line 7
                    listing.addAll(searchKdTreeX(root.getLeft(), c1, c2));
            }
            if(current.getSecondPart().getXray().getFirst().getX() >= c1 && current.getSecondPart().getXray().getLast().getX() <= c2) { //line 10
                listing.addAll(root.getRight().getFromLeaf());
            } else if(current.getSecondPart().getXray().getLast().getX() >= c1 && current.getSecondPart().getXray().getFirst().getX() <= c2)  { //line 13
                listing.addAll(searchKdTreeX(root.getRight(), c1, c2));
            }
        }
        return listing;
    }

    public ArrayList<Point> searchKdTreeY(KdTree<CoupleList> root, double c1, double c2) {
        ArrayList<Point> listing = new ArrayList<>();
        if (root.isLeaf()) {
            Point p = root.getData().singlePoint();
            if (p.inYankee(c1, c2)) {
                listing.add(p);
            }
        } else {
            CoupleList current = root.getData();
            if (current.getFirstPart().getYankee().getFirst().getY() >= c1 && current.getFirstPart().getYankee().getLast().getY() <= c2) { //line 4
                listing.addAll(root.getLeft().getFromLeaf());
            } else if (current.getFirstPart().getYankee().getLast().getY() >= c1 && current.getFirstPart().getYankee().getFirst().getY() <= c2) { //line 7
                listing.addAll(searchKdTreeY(root.getLeft(), c1, c2));
            }
            if (current.getSecondPart().getYankee().getFirst().getY() >= c1 && current.getSecondPart().getYankee().getLast().getY() <= c2) { //line 10
                listing.addAll(root.getRight().getFromLeaf());
            } else if (current.getSecondPart().getYankee().getLast().getY() >= c1 && current.getSecondPart().getYankee().getFirst().getY() <= c2) { //line 13
                listing.addAll(searchKdTreeY(root.getRight(), c1, c2));
            }
        }
        return listing;
    }



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
