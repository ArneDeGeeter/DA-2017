import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		int orientatie = 0;
		Coordinaat coord = new Coordinaat();
		char[] c = null;
		long time=System.currentTimeMillis();
		// aanmaken basisvariabelen
		for (int i = 0; i < gevallen; i++) {
			Plaatsen plaatsen = new Plaatsen();
			s = sc.nextLine();
			// Afhankelijk van hoelang de achtbaan is, zal de imput later
			// beginnen
			if (s.charAt(1) == ' ') {
				c = s.substring(2).toCharArray();
			} else if (s.charAt(2) == ' ') {
				c = s.substring(3).toCharArray();
			} else {
				c = s.substring(4).toCharArray();
			}
			for (int j = 0; j < c.length; j++) {
				Plaats p = new Plaats();
				/*
				 * Switch, afhankelijk van de ingelezen char, zal hij een van de
				 * bewerkingen doen. Ik werk met een 'kompas' om aan te houden
				 * welke richting de achtbaan gaat, en met behulp daarvan zal ik
				 * de volgende coordinaat aanmaken. Hierdoor hoef ik ook nooit
				 * een nieuwe variabele coordinaat aan te maken, aangezien de
				 * achtbaan en lus is, zal hij steeds terugkomen op 0,0,0.
				 */
				switch (c[j]) {
				case 'S':
					p = new Plaats(coord, '=');
				//	System.out.println(coord.getx() +" " +coord.gety()+" "+coord.getz());
					coord.verander(orientatie, 0);
					plaatsen.toevoegen(p);
					p.check();
					break;
				case 'V':
					p = new Plaats(coord, '_');
				//	System.out.println(coord.getx() +" " +coord.gety()+" "+coord.getz());

					coord.verander(orientatie, 0);
					plaatsen.toevoegen(p);
					p.check();
					break;
				case 'U':
					p.check();
					if (orientatie == 0) {
						p = new Plaats(coord, '/');
					} else if (orientatie == 2) {
						p = new Plaats(coord, '\\');
					} else {
						p = new Plaats(coord, '#');
					}
//					System.out.println(coord.getx() +" " +coord.gety()+" "+coord.getz());

					coord.verander(orientatie, 1);
					plaatsen.toevoegen(p);
					p.check();
					break;
				case 'D':
					coord.verander(orientatie, -1);
					coord.verander((orientatie + 2) % 4, 0);
					p.check();
//					System.out.println(coord.getx() +" " +coord.gety()+" "+coord.getz());

					if (orientatie == 0) {
						p = new Plaats(coord, '\\');
					} else if (orientatie == 2) {
						p = new Plaats(coord, '/');
					} else {
						p = new Plaats(coord, '#');
					}
					coord.verander(orientatie, 0);
					plaatsen.toevoegen(p);
					p.check();
					break;
				case 'L':
					p = new Plaats(coord, '_');
//					System.out.println(coord.getx() +" " +coord.gety()+" "+coord.getz());

					orientatie = (orientatie + 3) % 4;
					coord.verander(orientatie, 0);
					plaatsen.toevoegen(p);
					p.check();
					break;
				case 'R':
					p = new Plaats(coord, '_');
//					System.out.println(coord.getx() +" " +coord.gety()+" "+coord.getz());

					orientatie = (orientatie + 1) % 4;
					coord.verander(orientatie, 0);
					plaatsen.toevoegen(p);
					p.check();
					break;
				}
			}
			// Aanmaken van de uit te printen matrix
			int a = Plaats.getMaximumHoogte() - Plaats.getMinimumHoogte() + 1;
			int b = Plaats.getMaximumLengte() - Plaats.getMinimumLengte() + 1;
			int[][] layout = new int[a][b];
			// Alles wordt op -1 gezet, 'Arrays.fill(layout,-1)' werkte niet.
			for (int x = 0; x < layout.length; x++) {
				for (int y = 0; y < layout[0].length; y++) {
					layout[x][y] = -1;
				}
			}
			/*
			 * De matrix wordt ingevuld met de waarde die hun plaats geeft in de
			 * array. Ook wordt er gekeken dat de meest naar voorgelegen de
			 * andere zou kunnen overschrijven.
			 */
			for (int j = 0; j < plaatsen.getPlaatsArray().size(); j++) {
				int x = plaatsen.get(j).getx() + Plaats.getMinimumLengte() * -1; //y=xcoord-min
				int y = plaatsen.get(j).gety() + (Plaats.getMinimumHoogte() * -1);
				int z = plaatsen.get(j).getz();
				if (layout[y][x] == -1) {
					layout[y][x] = j;
				} else if (z < plaatsen.get(layout[y][x]).getz()) {
					layout[y][x] = j;
				}
			}
			
			/*
			 * Afprinten, via de waardes uit de matrix weten we wanneer we de
			 * char moeten ophalen of een punt moeten zetten.
			 */
			for (int x = layout.length - 1; x >= 0; x--) {
				System.out.print(i + 1 + " ");
				for (int y = 0; y < layout[0].length; y++) {
					if (layout[x][y] != -1) {
						System.out.print(plaatsen.get(layout[x][y]).getChar());
					} else {
						System.out.print(".");
					}
				}
				System.out.println();
			}
			Plaats.setMaximumHoogte(0);
			Plaats.setMinimumHoogte(0);
			Plaats.setMaximumLengte(0);
			Plaats.setMinimumLengte(0);
		}
		System.out.println(System.currentTimeMillis()-time);
	}
}

class Plaatsen {
	private List<Plaats> plaats;

	public Plaatsen() {
		plaats = new ArrayList<Plaats>();
	}

	public Plaats get(int i) {
		return plaats.get(i);
	}

	public List<Plaats> getPlaatsArray() {
		return plaats;
	}

	public void toevoegen(Plaats p) {
		plaats.add(p);
	}
}

class Plaats {
	private static int minimumLengte = 0; // x-richting horizontaal
	private static int maximumLengte = 0;
	private static int minimumHoogte = 0; // Y richting Verticaal
	private static int maximumHoogte = 0;
	private char c;
	private int x;
	private int y;
	private int z;

	// Het was gemakelijker gewoon de waarden op te slaan ipv de coordinaat.
	public Plaats(Coordinaat coordinaat2, char d) {
		x = coordinaat2.getx();
		y = coordinaat2.gety();
		z = coordinaat2.getz();
		c = d;
	}

	// hier worden de grenzen aangepast.
	public void check() {
		if (x > maximumLengte) {
			maximumLengte++;
		}
		if (x < minimumLengte) {
			minimumLengte--;
		}
		if (y > maximumHoogte) {
			maximumHoogte++;
		}
		if (y < minimumHoogte) {
			minimumHoogte--;
		}
	}

	public Plaats() {
	}

	public char getChar() {
		return c;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public int getz() {
		return z;
	}

	public static int getMinimumLengte() {
		return minimumLengte;
	}

	public static int getMinimumHoogte() {
		return minimumHoogte;
	}

	public static int getMaximumHoogte() {
		return maximumHoogte;
	}

	public static int getMaximumLengte() {
		return maximumLengte;
	}

	public static void setMinimumLengte(int i) {
		minimumLengte = i;
	}

	public static void setMinimumHoogte(int i) {
		minimumHoogte = i;
	}

	public static void setMaximumHoogte(int i) {
		maximumHoogte = i;
	}

	public static void setMaximumLengte(int i) {
		maximumLengte = i;
	}
}

class Coordinaat {
	private int x;
	private int y;
	private int z;

	public Coordinaat() {
		x = 0;
		y = 0;
		z = 0;
	}

	/*
	 * Hier wordt de volgende coordinaat berekend, afhankelijk van wat de vorige
	 * input was.
	 */
	public void verander(int orientatie, int i) {
		switch (orientatie) {
		case 0:
			x++;
			y = y + i;
			break;
		case 1:
			z--;
			y = y + i;
			break;
		case 2:
			x--;
			y = y + i;
			break;
		case 3:
			z++;
			y = y + i;
			break;
		// .....^3
		// .....| (in de diepte gezien/bovenaanzicht)
		// .2<--.--> 0 (nul is de beginwaarde)
		// .....|
		// .....~1
		}
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	public int getz() {
		return z;
	}
}
