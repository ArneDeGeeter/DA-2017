import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		long time = System.currentTimeMillis();

		for (int i = 0; i < gevallen; i++) {
			String a = sc.nextLine();
			int knopen = Integer.parseInt(a);
			Graaf graaf = new Graaf(knopen);
			for (int j = 0; j < knopen; j++) {
				graaf.add(Integer.parseInt(sc.nextLine()));
			}
			int paden = Integer.parseInt(sc.nextLine());
			for (int j = 0; j < paden; j++) {
				graaf.addPad(Integer.parseInt(sc.next()) - 1, Integer.parseInt(sc.next()) - 1);
			}
			sc.nextLine();
			graaf.werk();
		}
		//System.out.println(System.currentTimeMillis() - time);

	}

}

class Graaf {
	private int[][] wegen;
	private ArrayList<Integer> aantalWegenPerKnoop;
	private ArrayList<Integer> keienStart;

	public Graaf(int a) {
		wegen = new int[a][a];
		keienStart = new ArrayList<Integer>();
		aantalWegenPerKnoop = new ArrayList<Integer>();
		for (int i = 0; i < a; i++) {
			aantalWegenPerKnoop.add(0);
		}

	}

	public void addPad(int i, int j) {
		wegen[i][j] = 1;
		wegen[j][i] = 1;
		int a = aantalWegenPerKnoop.get(i);
		aantalWegenPerKnoop.remove(i);
		aantalWegenPerKnoop.add(i, ++a);
		a = aantalWegenPerKnoop.get(j);
		aantalWegenPerKnoop.remove(j);
		aantalWegenPerKnoop.add(j, ++a);

	}

	public void add(int kei) {
		keienStart.add(kei);
	}

	public void werk() throws Exception {
		ArrayList<String> opdelingKeien = new ArrayList<String>();
		boolean b = true;
		add(opdelingKeien, keienStart);
		ArrayList<Integer> verplaatsen = new ArrayList<Integer>();
		ArrayList<Integer> keienEinde = new ArrayList<Integer>();
		int aantalcycly = 0;
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();

		while (b) {
			aantalcycly++;
			keienEinde.clear();
			for (int i = 0; i < aantalWegenPerKnoop.size(); i++) {
				keienEinde.add(0);
			}
			for (int i = 0; i < aantalWegenPerKnoop.size(); i++) {
				int aantal = keienStart.get(i);
				int knopen = aantalWegenPerKnoop.get(i);
				int aantalTeVerplaatsen = (int) Math.floor(aantal / knopen);
				verplaatsen.add(aantalTeVerplaatsen);
			}
			for (int i = 0; i < aantalWegenPerKnoop.size(); i++) {
				int a = keienStart.get(i);
				keienStart.remove(i);
				keienStart.add(i, a - verplaatsen.get(i) * aantalWegenPerKnoop.get(i));
				for (int j = 0; j < wegen.length; j++) {
					if (wegen[i][j] == 1) {
						int c = keienEinde.get(j);
						keienEinde.remove(j);
						keienEinde.add(j, c + verplaatsen.get(i));

					}
				}
			}
			for (int i = 0; i < aantalWegenPerKnoop.size(); i++) {
				int a = keienStart.get(i);
				keienStart.remove(i);
				keienStart.add(i, a + keienEinde.get(i));
			}
			StringBuffer string = new StringBuffer();
			for (int i = 0; i < keienStart.size(); i++) {
				string.append(keienStart.get(i)).append(",");
			}

			if (hmap.containsKey(string.toString())) {
				b = false;
				System.out.println((aantalcycly - hmap.get(string.toString())));
			} else {
				hmap.put(string.toString(), aantalcycly);

			}

			verplaatsen.clear();
		}
	}

	private void setNull(ArrayList<Integer> a) {
		a.clear();
		for (int i = 0; i < a.size(); i++) {
			a.add(0);
		}
	}

	private void add(ArrayList<String> opdelingKeien, ArrayList<Integer> keien) {
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < keien.size(); i++) {
			string.append(keien.get(i)).append(",");
		}
		opdelingKeien.add(string.toString());
	}
}