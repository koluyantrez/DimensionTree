package be.ac.umons.razanajao.sddproject.structure;

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
