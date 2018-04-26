import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		long time = System.currentTimeMillis();
		for (int i = 0; i < gevallen; i++) {
			int aantal = 0;
			ArrayList<Knoop> boom = new ArrayList<Knoop>();
			ArrayList<String> takken = new ArrayList<String>();
			int vertakkingen = Integer.parseInt(sc.nextLine());
			for (int j = 0; j < vertakkingen; j++) {
				takken.add(sc.nextLine());
			}
			boom.add(new Knoop(takken.get(0)));
			Knoop.setStam(boom.get(0));
			for (int j = 1; j < takken.size(); j++) {
				Knoop k = new Knoop(takken.get(j));
				boom.add(k);
			}
			setparent(boom);

			for (int j = 0; j < boom.size(); j++) {
				for (int q = 0; q < boom.get(j).getChildNaam().size(); q++) {
					if (boom.get(j).getChildNaam().get(q).equals("*")) {
						boom.get(j).add(new Knoop("*", boom.get(j)));
					} else {
						for (int d = 0; d < boom.size(); d++) {
							if (boom.get(j).getChildNaam().get(q).equals(boom.get(d).getNaam())) {
								boom.get(j).add(boom.get(d));
							}
						}

					}

				}
			}
			int[] a = new int[2];
			boom.get(0).recursief2(true, null);

			System.out.println(boom.get(0).getAfgehakteHoofden());
		System.out.println(System.currentTimeMillis() - time);
		}
	}

	private static void setparent(ArrayList<Knoop> boom) {
		for (int j = 0; j < boom.size(); j++) {
			if (!boom.get(j).getNaam().equals("stam")) {

				for (int q = 0; q < boom.size(); q++) {
					for (int d = 0; d < boom.get(q).getChildNaam().size(); d++) {
						if (boom.get(j).getNaam().equals(boom.get(q).getChildNaam().get(d))) {
							boom.get(j).setParent(boom.get(q));
							boom.get(j).setParentNaam(boom.get(q).getNaam());
						}

					}
				}
			}

		}

	}
}

class Knoop {
	private static Knoop stam;
	private String naam;
	private String parentNaam;
	private Knoop parent;
	private ArrayList<String> childNaam;
	private ArrayList<Knoop> child;
	private int afgehakteHoofden;

	public Knoop(String s) {
		afgehakteHoofden = 0;
		ArrayList<Integer> spaties = new ArrayList<Integer>();
		childNaam = new ArrayList<String>();
		child = new ArrayList<Knoop>();
		spaties.add(-1);
		s = s.concat(" ");
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				spaties.add(i);
			}
		}
		naam = s.substring(spaties.get(0) + 1, spaties.get(1));
		for (int i = 2; i < spaties.size() - 1; i++) {
			if (s.substring(spaties.get(i) + 1, spaties.get(i + 1)).equals("afgekaptHoofd")) {
				childNaam.add("afgekaptHoofd");
				afgehakteHoofden++;
			} else {
				childNaam.add(s.substring(spaties.get(i) + 1, spaties.get(i + 1)));
			}
		}
	}

	public Knoop(String s, Knoop k) {
		parent = k;
		afgehakteHoofden = 0;
		ArrayList<Integer> spaties = new ArrayList<Integer>();
		childNaam = new ArrayList<String>();
		child = new ArrayList<Knoop>();
		naam = s;
	}

	public Knoop(Knoop k) {
		afgehakteHoofden = 0;
		childNaam = new ArrayList<String>();
		child = new ArrayList<Knoop>();

		naam = k.naam;
		for (int i = 0; i < k.child.size(); i++) {
			child.add(new Knoop(k.getChild().get(i)));
		}
	}

	public void recursief2(boolean original, Knoop subtak) {

		while (child.size() != 0) {
			if (original) {
				if (stam.child.get(0).child.size() == 0) {
					stam.child.remove(0);
					stam.afgehakteHoofden++;
				} else {
					stam.child.get(0).recursief2(false, stam.child.get(0));
				}
			} else {
				for (int i = child.size() - 1; i >= 0; i--) {
					if (child.get(i).getNaam().equals("*")) {
						child.remove(i);
						stam.child.add(new Knoop(subtak));
						stam.afgehakteHoofden++;
					} else if (child.get(i).child.size() == 0) {
						child.remove(i);
						stam.afgehakteHoofden++;
						stam.child.add(new Knoop(subtak));
					} else {
						child.get(i).recursief2(false, subtak);
					}
				}
			}
		}
	}

	public void add(Knoop k) {
		child.add(k);
	}

	public void inc() {
		afgehakteHoofden++;
	}

	public String getNaam() {
		return naam;
	}

	public String getParentNaam() {
		return parentNaam;
	}

	public Knoop getParent() {
		return parent;
	}

	public ArrayList<String> getChildNaam() {
		return childNaam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setParentNaam(String parentNaam) {
		this.parentNaam = parentNaam;
	}

	public void setParent(Knoop parent) {
		this.parent = parent;
	}

	public void setChildNaam(ArrayList<String> childNaam) {
		this.childNaam = childNaam;
	}

	public int getAfgehakteHoofden() {
		return afgehakteHoofden;
	}

	public void setAfgehakteHoofden(int afgehakteHoofden) {
		this.afgehakteHoofden = afgehakteHoofden;
	}

	public ArrayList<Knoop> getChild() {
		return child;
	}

	public void setChild(ArrayList<Knoop> child) {
		this.child = child;
	}

	public static Knoop getStam() {
		return stam;
	}

	public static void setStam(Knoop stam) {
		Knoop.stam = stam;
	}

}