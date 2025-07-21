package be.ac.umons.razanajao.sddproject.structure;

/**
 * Describes a binary search tree. It extends Tree class. Taken from moodle and adapted for KdTree (2023-2024).
 *
 * @param <D> the type of the data stored in the tree that extends Comparable.
 */
public class BSTree<D> extends Tree<D>{

    // Constructors
    public BSTree() {
        super();
    }

    public BSTree(D d, BSTree<D> l, BSTree<D> r) {
        super(d, l, r);
    }

    // Redefine to avoid constant casting
    public BSTree<D> getLeft() {
        return (BSTree<D>) super.getLeft();
    }

    public BSTree<D> getRight() {
        return (BSTree<D>) super.getRight();
    }

}
