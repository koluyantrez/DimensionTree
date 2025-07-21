package be.ac.umons.razanajao.sddproject.structure;

/**
 * Describes a binary tree. Taken from moodle (2023-2024).
 *
 * @param <D> the type of the data stored in the tree.
 */
public class Tree<D> {
	private D data;
	private Tree<D> left, right;
	
//constructeurs
	public Tree(D d, Tree<D> l, Tree<D> r) {
		data = d;
		left = l;
		right = r;
	}
	public Tree() {
		this(null,null,null);
	}

//get 
	public D getData() {
		return data;
	}
	public Tree<D> getLeft() {
		return left;
	}
	public Tree<D> getRight() {
		return right;
	}

//set
	public void setData(D d) {
		data = d;
	}
	public void setLeft(Tree<D> l) {
		left = l;
	}
	public void setRight(Tree<D> r) {
		right = r;
	}

//test de l'arbre vide, c'est-a-dire du "noeud vide"
	public boolean isEmpty() {
		if (data == null && left == null && right == null) 
			return true;
		else
			return false;
	}
	
//remplit un noeud vide avec la donnee d et 2 sous-arbres vides
	public void insertEmpty(D d) {
		data = d;
		left = new Tree();
		right = new Tree();
	}
	
//calcul de la hauteur
	public int height() {
		if (isEmpty()) 
			return 0;
		else
			return 1 + Math.max(left.height(),right.height());
	}

//affichage recursif
	public void print() {
		if (!isEmpty()) {
			System.out.println(data);
			System.out.println();
			left.print();
			right.print();
		}
	}	
}