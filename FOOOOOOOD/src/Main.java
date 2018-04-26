import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		// long time = System.currentTimeMillis();
		for (int g = 0; g < gevallen; g++) {
			s = sc.nextLine();
			FoodFest fest = new FoodFest(s, sc);
			fest.work();
			fest.printen(g);
		}

	}

}

class FoodFest {
	private ArrayList<Integer> budget;
	private ArrayList<Trucks> trucks;
	private int aantalTrucks;
	private ArrayList<Integer> oplossingen;

	public FoodFest(String s, Scanner sc) {
		trucks = new ArrayList<Trucks>();
		budget = new ArrayList<Integer>();
		oplossingen = new ArrayList<Integer>();
		ArrayList<Integer> spaties = new ArrayList<Integer>();
		spaties.add(-1);
		s = s.concat(" ");
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				spaties.add(i);
			}
		}
		for (int i = 1; i < spaties.size() - 1; i++) {
			budget.add(Integer.parseInt(s.substring(spaties.get(i) + 1, spaties.get(i + 1))));
		}
		aantalTrucks = Integer.parseInt(sc.nextLine());
		for (int i = 0; i < aantalTrucks; i++) {
			s = sc.nextLine();
			trucks.add(new Trucks(s));
		}
	}

	public void printen(int g) {
		HashSet<Integer> hs = new HashSet<Integer>();
		hs.addAll(oplossingen);
		oplossingen.clear();
		oplossingen.addAll(hs);
		oplossingen.sort(null);
		System.out.print(g + 1);
		if (oplossingen.size() == 0) {
			System.out.println(" GEEN");
		} else {
			for (int i = 0; i < oplossingen.size(); i++) {
				System.out.print(" " + oplossingen.get(i));
			}
			System.out.println();
		}
	}

	public void work() {
		for (int i = 0; i < budget.size(); i++) {
			ArrayList<Integer> totaalPrijzen = new ArrayList<Integer>();
			int a = 0;
			for (int j = 0; j < trucks.size(); j++) {
				trucks.get(j).getPrijzen();
				a = trucks.get(0).recusief(budget.get(i), trucks, 0);
				//System.out.println(a + "####");

				if (a == 0) {
					oplossingen.add(budget.get(i));
				}
			}
		}
	}
}

class Trucks {
	private int aantal;
	private ArrayList<Integer> prijzen;

	public Trucks(String s) {
		ArrayList<Integer> spaties = new ArrayList<Integer>();
		prijzen = new ArrayList<Integer>();
		spaties.add(-1);
		s = s.concat(" ");
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				spaties.add(i);
			}
		}
		aantal = Integer.parseInt(s.substring(spaties.get(0) + 1, spaties.get(1)));
		for (int i = 1; i < spaties.size() - 1; i++) {
			prijzen.add(Integer.parseInt(s.substring(spaties.get(i) + 1, spaties.get(i + 1))));
		}
		prijzen.sort(null);
	}

	public int recusief(Integer integer, ArrayList<Trucks> trucks, int j) {
		int a = integer;
		int b = j;
		//System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB " + j);

		if (j + 1 == trucks.size()) {
		//	System.out.println("test");
			for (int i = 0; i < aantal; i++) {
			//	System.out.println(a - prijzen.get(i) + "   ++++++");
				if (a - prijzen.get(i) == 0) {

					return 0;
				} else if (a - prijzen.get(i) < 0) {
					return a;
				}

			}
			return a;
		} else {
			for (int i = 0; i < aantal; i++) {
				//System.out.println(a + " " + integer + " " + "___");
				a = integer;
				a = a - prijzen.get(i);
				////System.out.println(prijzen.get(i));
				//System.out.println("AAAAAAAAAAAAAAAAAAAA " + a + " " + i + " " + j);
				if (a < 0) {
				//	System.out.println(a + " " + prijzen.get(i));
					return a;
				}
				if (a == 0) {
					return -1;
				} else {

					b = j + 1;
					a = trucks.get(b).recusief(a, trucks, b);
				//	System.out.println(a + " 123456");
					if (a == 0) {
						return a;
					} /* && i == aantal - 1) */
					a = integer;

				}
			}
		}

		return a;
	}

	public ArrayList<Integer> getPrijzen() {
		return prijzen;
	}

	public void setPrijzen(ArrayList<Integer> prijzen) {
		this.prijzen = prijzen;
	}

	public int getAantal() {
		return aantal;
	}

	public void setAantal(int aantal) {
		this.aantal = aantal;
	}
}
