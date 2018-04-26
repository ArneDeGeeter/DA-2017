import java.util.*;

public class Main {

	public static void main(String[] args) {
		BinaireBoom<String> boom = new BinaireBoom<String>();
		ArrayList<Integer> a = new ArrayList<Integer>();
		// a.addAll(Arrays.asList("8","1", "0", "2", "3", "4", "5", "6",
		// "7","33","55","44"));
		for (int i = 0; i < 15; i++) {
			a.add((int) (Math.random() * 100));
		}
		for (int i = 0; i < a.size(); i++) {
			// System.out.println(a.get(i));
		}
		voegToe(a, boom);
		print("", boom.getRoot(), false);
		System.out.println(boom.getDiepte(0));
//		boom.schrijfInOrder();

		GebalanceerdeBinaireBoom gBoom = new GebalanceerdeBinaireBoom();
		voegToe(a, gBoom);
		print("", gBoom.getRoot(), false);
//		System.out.println(gBoom.getDiepte(0));

	}

	private static void voegToe(ArrayList<Integer> a, BinaireBoom b) {
		for (int i = 0; i < a.size(); i++) {
			b.voegToe(new Element(a.get(i)));
			// System.out.println(a.get(i));
			// print("",b.getRoot(),false);

		}
	}

	public static void print(String prefix, Element n, boolean isLeft) {
		if (n != null) {
			print(prefix + (isLeft ? "|   " : "|   "), n.getChildL(), true);
			System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + n.getData());
			print(prefix + (isLeft ? "|   " : "    "), n.getChildR(), false);
		}
	}

}

class GebalanceerdeBinaireBoom extends BinaireBoom {
	void voegToe(Element e) {
		super.voegToe(e);
		if (find(e.getData()) == null) {
			return;
		}
		if (find(e.getData()).getParent() == null) {
			return;
		}
		balance(find(e.getData()).getParent());

	}

	private void balance(Element e) {
        if( e == null )
            return;
        
        if( e.getChildL().getDiepte(0) - e.getChildR().getDiepte(0)> 1 )
            if(e.getChildL().getChildL().getDiepte(0)>= e.getChildL().getChildR().getDiepte(0) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;		
	}
    private int height( Element e )
    {
        return e == null ? -1 : e.height;
    }
}

class BinaireBoom<T extends Comparable<T>> {
	Element root;

	BinaireBoom() {
		root = null;
	}

	public int getDiepte(int i) {
		return root.getDiepte(i);
	}

	public Element getRoot() {
		return root;
	}

	void voegToe(Element e) {
		if (root == null) {
			root = e;
		} else {
			int result = root.compareTo(e);
			if (result == 0) {
				return;
			} else if (result > 0) {
				if (root.getChildL() != null) {
					root.getChildL().voegToe(e);
				} else {
					root.setChildL(e);
					e.setParent(root);
				}
			} else if (result < 0) {
				if (root.getChildR() != null) {
					root.getChildR().voegToe(e);
				} else {
					root.setChildR(e);
					e.setParent(root);
				}
			}

		}
	}

	public Element find(int data) {
		Element current = root;
		while (current != null && data != current.getData()) {
			if (data - current.getData() < 0) {
				current = current.getChildL();
			} else {
				current = current.getChildR();
			}
		}

		return current;
	}

	void schrijfInOrder() {
		root.printInOrder();

	}

	void schrijfPreOrder() {
		root.printPreOrder();
	}

	void schrijfPostOrder() {
		root.printPostOrder();

	}

	int diepte() {
		return root.getDiepte(0);
	}

	BinaireBoom(Element e) {
		root = e;
	}
}

class Element<T extends Comparable<T>> {
	private Element<T> parent;
	private T data;
	private Element<T> childL;
	private Element<T> childR;

	public int compareTo(Element e) {
		return data.compareTo((T) e.getData());
	}

	Element(T s) {
		data = s;
		parent = null;
		childL = null;
		childR = null;
	}

	public void voegToe(Element e) {
		int result = this.compareTo(e);

		if (result == 0) {
			return;
		} else if (result > 0) {
			if (this.childL != null) {
				this.childL.voegToe(e);
			} else {
				this.childL = e;
				e.setParent(this);
			}
		} else if (result < 0) {
			if (this.childR != null) {
				this.childR.voegToe(e);
			} else {
				this.childR = e;
				e.setParent(this);

			}
		}

	}

	public int getDiepte(int i) {
		i++;
		if (childL == null && childR != null) {
			return childR.getDiepte(i);
		} else if (childL != null && childR == null) {
			return childL.getDiepte(i);
		} else if (childL != null && childR != null) {
			if (childL.getDiepte(i) > childR.getDiepte(i)) {
				return childL.getDiepte(i);
			} else
				return childR.getDiepte(i);
		} else {
			return i;
		}
	}

	public void printPostOrder() {
		if (childL != null) {
			childL.printPostOrder();
		}
		if (childR != null) {
			childR.printPostOrder();
		}
		System.out.print(data + " ");
	}

	public void printPreOrder() {
		System.out.print(data + " ");
		if (childL != null) {
			childL.printPreOrder();
		}
		if (childR != null) {
			childR.printPreOrder();
		}
	}

	public void printInOrder() {
		try {
			if (childL != null) {
				childL.printInOrder();
			}
			System.out.print(data + " ");
			if (childR != null) {
				childR.printInOrder();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Element getParent() {
		return parent;
	}

	public T getData() {
		return data;
	}

	public Element<T> getChildL() {
		return childL;
	}

	public Element<T> getChildR() {
		return childR;
	}

	public void setParent(Element<T> parent) {
		this.parent = parent;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setChildL(Element<T> childL) {
		this.childL = childL;
	}

	public void setChildR(Element<T> childR) {
		this.childR = childR;
	}

}

/*
 * class Comparable implements Comparator<Element> {
 * 
 * @Override public int compare(Element arg0, Element arg1) { return
 * arg0.getData().compareTo(arg1.getData()); }
 * 
 * }
 */