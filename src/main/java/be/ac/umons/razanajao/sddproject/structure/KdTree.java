package be.ac.umons.razanajao.sddproject.structure;

/**
 * Describes a kD-tree. Here, it was designed to manage data in 2D.
 *
 *
 */

public class KdTree<D> extends BSTree<D>{

    public KdTree() {
        super();
    }

    public KdTree(D d, KdTree<D> l, KdTree<D> r){
        super(d,l,r);
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
     * @param cl        The source of the tree.
     * @param depth     The current depth in the main tree.
     * @return          A KdTree with the data contained in the CoupleList given.
     */
    public KdTree buildKdTree(CoupleList cl, int depth){
        if(cl.size()==1){
            return new KdTree(cl,new KdTree(),new KdTree());
        }else{
            cl.split(depth);
            KdTree<D> kdtLeft = buildKdTree(cl.getFirst(), 1+depth);
            KdTree<D> kdtRight = buildKdTree(cl.getSecond(), 1+depth);
            return new KdTree(cl,kdtLeft,kdtRight);
        }
    }
}
