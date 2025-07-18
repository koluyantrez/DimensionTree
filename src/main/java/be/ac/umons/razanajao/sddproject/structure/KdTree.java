package be.ac.umons.razanajao.sddproject.structure;

public class KdTree<D> extends BSTree<D>{

    private final KdTree<D> krRoot;

    public KdTree() {
        this.krRoot = new KdTree<D>();
    }

    public KdTree(CoupleList d, KdTree<D> l, KdTree<D> r){
        this.krRoot = new KdTree<D>();
    }

    // Redefine to avoid constant casting
    public KdTree<D> getLeft() {
        return (KdTree<D>) super.getLeft();
    }

    public KdTree<D> getRight() {
        return (KdTree<D>) super.getRight();
    }


    public KdTree buildKdTree(CoupleList cl, int depth){
        if(cl.size()==1){
            return new KdTree(cl,null,null);
        }else{
            cl.split(depth);
            KdTree<D> kdtLeft = buildKdTree(cl.getFirst(), 1+depth);
            KdTree<D> kdtRight = buildKdTree(cl.getSecond(), 1+depth);
            return new KdTree(cl,kdtLeft,kdtRight);
        }
    }
}
