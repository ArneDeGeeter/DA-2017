import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		BinaireBoom<Integer> boom = new BinaireBoom<Integer>();
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			a.add((int) (Math.random() * 90 + 10));
		}
		boolean b = true;
		GebalanceerdeBinaireBoom gBoom = new GebalanceerdeBinaireBoom();
		voegToe(a, gBoom);
		voegToe(a, boom);

		print("", boom.root, false);
		System.out.println();
		System.out.println();
		System.out.println();
		print("", gBoom.root, false);
	}

	
	
	
	// gevonden op het internet
	// https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
	public static void print(String prefix, Element n, boolean isLeft) {
		if (n != null) {
			print(prefix + (isLeft ? "|   " : "    "), n.getChildR(), false);
			System.out.println(prefix + (isLeft ? "\\-- " : "/-- ") + n.getData());
			print(prefix + (isLeft ? "    " : "|   "), n.getChildL(), true);
		}
	}

	private static void voegToe(ArrayList<Integer> a, BinaireBoom b) {
		for (int i = 0; i < a.size(); i++) {
			b.voegToe(new Element(a.get(i)));

		}
	}

}

class GebalanceerdeBinaireBoom extends BinaireBoom {
	private int balance2(Element element, Element element2) {
		return element.getDiepte(0) - element2.getDiepte(0);
	}

	private void balance(Element e) {
		Element f = find(e.getData()).getParent();

		if (e.compareTo(root) == 0) {
			return;
		}

		while (f.getData().compareTo(root.getData()) != 0) {
			int a = 0;
			if (f != null) {
				int left = 0;
				int right = 0;
				try {
					right = f.getChildR().getDiepte(0);

				} catch (NullPointerException n) {
				}
				try {
					left = f.getChildL().getDiepte(0);

				} catch (NullPointerException n) {
				}
				a = left - right;
			}
			if (a > 1) {
				int left = 0;
				int right = 0;
				try {
					right = f.getChildL().getChildR().getDiepte(0);

				} catch (NullPointerException n) {
				}
				try {
					left = f.getChildL().getChildL().getDiepte(0);

				} catch (NullPointerException n) {
				}

				if (left >= right) {

					rightRotate(f);
					f = f.getParent();

				} else {

					leftRightRotate(f);
					f = f.getParent();

				}
			} else if (a < -1) {
				int left = 0;
				int right = 0;
				try {
					right = f.getChildR().getChildR().getDiepte(0);

				} catch (NullPointerException n) {
				}
				try {
					left = f.getChildR().getChildL().getDiepte(0);

				} catch (NullPointerException n) {
				}

				if (right >= left) {
					leftRotate(f);
					f = f.getParent();

				} else {
					rightLeftRotate(f);
					f = f.getParent();

				}
			}

			f = f.getParent();

		}
		int a = 0;
		if (f != null) {

			if (f.getChildL() != null && f.getChildR() != null) {
				a = f.getChildL().getDiepte(0) - f.getChildR().getDiepte(0);
			} else if (f.getChildL() == null && f.getChildR() != null) {
				a = 0 - f.getChildR().getDiepte(0);
			} else if (f.getChildL() != null && f.getChildR() == null) {
				a = f.getChildL().getDiepte(0);
			}
		}
		if (a > 1) {
			int LR = 0;
			try {
				LR = f.getChildL().getChildR().getDiepte(0);
			} catch (NullPointerException n) {
			}
			int LL = 0;
			try {
				LL = f.getChildL().getChildL().getDiepte(0);
			} catch (NullPointerException n) {
			}
			if (LL >= LR) {
				rightRotate(f);
			} else {
				leftRightRotate(f);
			}
		} else if (a < -1) {
			int RR = 0;
			try {
				RR = f.getChildR().getChildR().getDiepte(0);
			} catch (NullPointerException n) {
			}
			int RL = 0;
			try {
				RL = f.getChildR().getChildL().getDiepte(0);
			} catch (NullPointerException n) {
			}
			if (RR >= RL) {
				leftRotate(f);
			} else {
				rightLeftRotate(f);// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			}
		}
		a = 0;
		if (root != null) {

			if (root.getChildL() != null && root.getChildR() != null) {
				a = root.getChildL().getDiepte(0) - root.getChildR().getDiepte(0);

			} else if (root.getChildL() == null) {
				// rightRotate(root);
			} else if (root.getChildR() == null) {
				// leftRotate(root);
			}
		}
		if (a > 1) {
			if (root.getChildL().getChildL().getDiepte(0) >= root.getChildL().getChildR().getDiepte(0)) {
				rightRotate(root);
			} else {
				leftRightRotate(root);
			}
		} else if (a < -1) {
			if (root.getChildR().getChildR().getDiepte(0) >= root.getChildR().getChildL().getDiepte(0)) {
				leftRotate(root);
			} else {
				rightLeftRotate(root);// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			}
		}

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

	private void rightRotate(Element e) {

		Element t1 = null;
		Element t2 = null;
		Element t3 = null;
		Element t4 = null;
		Element y = e.getChildL();
		Element x = e.getChildL().getChildL();
		try {
			t1 = e.getChildL().getChildL().getChildL();
		} catch (NullPointerException n) {
		}
		try {
			t2 = e.getChildL().getChildL().getChildR();
		} catch (NullPointerException n) {
		}
		try {
			t3 = e.getChildL().getChildR();
		} catch (NullPointerException n) {
		}
		try {
			t4 = e.getChildR();
		} catch (NullPointerException n) {
		}

		Element p = e.getParent();
		if (p == null) {
			super.setRoot(y);
			// e.getChildR().setParent(p);
			y.setChildR(e);
			e.setParent(y);
			y.setParent(null);
			e.setChildL(t3);
		} else {
			if (p.getChildL() != null) {
				if (p.getChildL().getData().equals(e.getData())) {
					p.setChildL(y);
					y.setParent(p);

				} else {
					p.setChildR(y);
					y.setParent(p);

				}
			} else {
				p.setChildR(y);
				y.setParent(p);

			}
			y.setParent(p);
			y.setChildR(e);
			e.setParent(y);
			e.setChildL(t3);
		}
	}

	private void leftRotate(Element e) {

		Element t1 = null;
		Element t2 = null;
		Element t3 = null;
		Element t4 = null;
		try {
			t1 = e.getChildL();

		} catch (NullPointerException n) {
			t1 = null;
		}
		try {
			t2 = e.getChildR().getChildL();

		} catch (NullPointerException n) {
			// Element<T> t2 = null;
		}
		try {
			t3 = e.getChildR().getChildR().getChildL();

		} catch (NullPointerException n) {
			// Element<T> t3 = null;
		}
		try {

			t4 = e.getChildR().getChildR().getChildR();

		} catch (NullPointerException n) {
			// Element<T> t4 = null;
		}

		Element p = e.getParent();
		if (p == null) {
			super.setRoot(e.getChildR());
			// e.getChildR().setParent(p);
			e.getChildR().setChildL(e);
			e.setParent(e.getChildR());
			e.getChildR().setParent(null);
			e.setChildR(t2);
		} else {
			if (p.getChildL() != null) {
				if (p.getChildL().getData().equals(e.getData())) {
					p.setChildL(e.getChildR());
					e.getChildR().setParent(p);

				} else {
					p.setChildR(e.getChildR());
					e.getChildR().setParent(p);

				}
			} else {
				p.setChildR(e.getChildR());
				e.getChildR().setParent(p);

			}
			e.getChildR().setParent(p);
			e.getChildR().setChildL(e);
			e.setParent(e.getChildR());
			e.setChildR(t2);

			/*
			 * Element<T> newRoot = e.getChildR();
			 * e.setChildR(e.getChildR().getChildL()); newRoot.setChildL(e);
			 */
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void verwijder(Element a) {
		Element oorspronkelijk = find(a.getData());
		Element parent = oorspronkelijk.getParent();
		Element z = find(a.getData());
		if (z != null) {
			/*
			 * if (z.compareTo(root) == 0) {
			 * 
			 * } else
			 */ if (z.getChildR() != null) {
				z = z.getChildR();
				if (z.getChildL() != null) {
					while (z.getChildL() != null) {
						z = z.getChildL();
					}
					oorspronkelijk.setData(z.getData());
					z.getParent().setChildL(z.getChildR());
				} else {
					oorspronkelijk.setData(z.getData());
					oorspronkelijk.setChildR(oorspronkelijk.getChildR().getChildR());
				}
			} else if (z.getChildL() != null) {
				z = z.getChildL();

				if (z.getChildR() != null) {
					while (z.getChildR() != null) {
						z = z.getChildR();
					}

					oorspronkelijk.setData(z.getData());
					z.getParent().setChildR(z.getChildL());
				} else {
					oorspronkelijk.setData(z.getData());
					oorspronkelijk.setChildL(oorspronkelijk.getChildL().getChildL());
				}

			} else {
				if (z.getParent().getChildL() != null) {
					if (z.getParent().getChildL().getData().equals(z.getData())) {
						z.getParent().setChildL(null);
					} else {
						z.getParent().setChildR(null);

					}
				} else {
					z.getParent().setChildR(null);

				}
			}
			if (parent != null) {
				balance(parent);
			}
		}
	}

	private void rightLeftRotate(Element<?> e) {

		Element t2 = null;
		Element t3 = null;
		Element y = e.getChildR();
		Element x = e.getChildR().getChildL();
		try {
			t2 = e.getChildR().getChildL().getChildL();

		} catch (NullPointerException n) {
		}
		try {
			t3 = e.getChildR().getChildL().getChildR();

		} catch (NullPointerException n) {
		}
		Element p = e.getParent();
		if (p == null) {
			super.setRoot(x);
			x.setChildL(e);
			x.setChildR(y);
			y.setParent(getRoot());
			y.setChildL(t3);
			e.setParent(getRoot());
			e.setChildR(t2);
			x.setParent(null);

		} else {
			if (p.getChildL() != null) {
				if (p.getChildL().getData().equals(e.getData())) {
					p.setChildL(x);
				} else {
					p.setChildR(x);

				}
			} else {
				p.setChildR(x);
			}
			x.setParent(p);
			x.setChildL(e);
			x.setChildR(y);
			y.setParent(x);
			e.setChildR(t2);
			e.setParent(x);
			y.setChildL(t3);
		}
	}

	private void leftRightRotate(Element e) {
		Element t1 = null;
		Element t2 = null;
		Element t3 = null;
		Element t4 = null;
		Element y = e.getChildL();
		Element x = e.getChildL().getChildR();
		try {
			t1 = e.getChildR();

		} catch (NullPointerException n) {
			t1 = null;
		}
		try {
			t2 = e.getChildR().getChildL().getChildL();

		} catch (NullPointerException n) {
		}
		try {
			t3 = e.getChildR().getChildL().getChildR();

		} catch (NullPointerException n) {
		}
		try {

			t4 = e.getChildR().getChildR();
		} catch (NullPointerException n) {
		}
		Element p = e.getParent();
		if (p == null) {
			super.setRoot(x);
			x.setChildL(y);
			x.setChildR(e);
			y.setParent(x);
			y.setChildR(t2);
			e.setParent(x);
			e.setChildL(t3);
			x.setParent(null);

		} else {
			if (p.getChildL() != null) {
				if (p.getChildL().getData().equals(e.getData())) {
					p.setChildL(x);
				} else {
					p.setChildR(x);

				}
			} else {
				p.setChildR(x);
			}
			x.setParent(p);
			x.setChildL(y);
			x.setChildR(e);
			y.setParent(x);
			e.setParent(x);
			e.setChildL(t3);
			y.setChildR(t2);
		}
	}

	public static void print(String prefix, Element n, boolean isLeft) {
		if (n != null) {
			print(prefix + (isLeft ? "|   " : "|   "), n.getChildL(), true);
			System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + n.getData());
			print(prefix + (isLeft ? "|   " : "    "), n.getChildR(), false);
		}
	}

	void voegToe(Element e) {
		super.voegToe(e);
		if (find(e.getData()) == null) {
			return;
		}
		if (find(e.getData()).getParent() == null) {
			return;
		}
		balance(find(e.getData()));

	}
}

class BinaireBoom<T extends Comparable<T>> {
	Element<T> root;

	BinaireBoom() {
		root = null;
	}

	BinaireBoom(Element<T> e) {
		root = e;
	}

	public int compareTo(BinaireBoom o) {
		return root.compareTo(o.root);
	}

	void voegToe(Element<T> e) {
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

	public static void print(String prefix, Element n, boolean isLeft) {
		if (n != null) {
			print(prefix + (isLeft ? "|   " : "|   "), n.getChildL(), true);
			System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + n.getData());
			print(prefix + (isLeft ? "|   " : "    "), n.getChildR(), false);
		}
	}

	void verwijder(Element<T> e) {

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

	void print() {
		root.print(1);
	}

	int diepte() {
		return root.getDiepte(0);
		/*
		 * if (e == null) { return 0; } else { int diepteL =
		 * diepte(e.getChildL()); int diepteR = diepte(e.getChildR()); if
		 * (diepteL > diepteR) return (diepteL + 1); else return (diepteR + 1);
		 * }
		 */
	}

	public Element<T> find(T data) {
		Element<T> current = root;
		while (current != null && data != current.getData()) {
			if (data.compareTo(current.getData()) < 0) {
				current = current.getChildL();
			} else {
				current = current.getChildR();
			}
		}
		/*
		 * if (current == null) { return null; }
		 */
		return current;
	}

	public Element<T> getRoot() {
		return root;
	}

	public void setRoot(Element<T> root) {
		this.root = root;
	}
}

class Element<T extends Comparable<T>> implements Comparable<T> {
	private Element<T> parent;
	private T data;
	private Element<T> childL;
	private Element<T> childR;

	Element(T s) {
		data = s;
		parent = null;
		childL = null;
		childR = null;
	}

	public int compareTo(Element<T> root) {

		return this.data.compareTo(root.data);
	}

	@Override
	public int compareTo(T arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDiepte(int i) {
		i++;
		if (childL == null && childR != null) {
			return childR.getDiepte(i);
		} else if (childL != null && childR == null) {
			return childL.getDiepte(i);
		} else if (childL != null && childR != null) {
			int l = childL.getDiepte(i);
			int r = childR.getDiepte(i);
			if (l > r) {
				return childL.getDiepte(i);
			} else
				return childR.getDiepte(i);
		} else {
			return i;
		}
	}

	public void print(int i) {
		System.out.print(data + " ");
		if (i == 1) {
			System.out.println();
		}
		if (childL != null) {
			childL.print(0);
		}
		if (childR != null) {
			childR.print(i);
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

	public void voegToe(Element<T> e) {
		int result = this.compareTo(e);
		// int result = cmp.compare(this, e);

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

	public Element<T> getParent() {
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
