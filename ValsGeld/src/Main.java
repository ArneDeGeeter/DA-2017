import java.util.*;

public class Main {
	public static void main(String[] args) {
		// Aanmaken basis variabelen
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		long time=System.currentTimeMillis();
		for (int g = 0; g < gevallen; g++) {
			s = sc.nextLine();
			/*
			 * 4 lijsten, lichter zullen alle munten inkomen waarbij de
			 * weegschaal op lichter sprong, zwaarder waarbij de weegschaal
			 * zwaarder is, evenwicht komen alle munten die echt moeten zijn, en
			 * dan nog een combinatie van lichter en zwaarder, verschillend
			 * gewicht komen alle munten bij een bepaalde meting waar de
			 * weegschaal niet op evenwicht stond.
			 */
			Munten lichter = new Munten();
			Munten zwaarder = new Munten();
			Munten evenwicht = new Munten();
			Munten verschillendGewicht = new Munten();
			int vragen = Integer.parseInt(s);
			for (int v = 0; v < vragen; v++) {
				Munten nietGevraagd = new Munten();
				int eersteSpatie = 0;
				int tweedeSpatie = 0;
				s = sc.nextLine();
				char[] c = s.toCharArray();
				// Opsporen van de spaties, hierdoor is de imput op te splitsen.
				for (int i = 0; i < c.length; i++) {
					if (' ' == c[i] && eersteSpatie == 0) {
						eersteSpatie = i;
					} else if (' ' == c[i] && eersteSpatie != 0 && tweedeSpatie == 0) {
						tweedeSpatie = i;
					}
				}
				/*
				 * Aangezien de 3de karakter van evenwicht, omhoog en omlaag
				 * verschillend is, is het genoeg dat karakter te controleren.
				 */
				// Bij evenwicht zijn we zeker dat alle munten juist zijn.
				if (c[tweedeSpatie + 3] == 'e') {
					for (int i = 0; i < eersteSpatie; i++) {
						evenwicht.add(new Munt(c[i]));
						evenwicht.add(new Munt(c[i + eersteSpatie + 1]));
					}
				}
				/*
				 * De munten worden respectievelijk toegevoegd in hun lijsten,
				 * maar ook in de nietgevraagd lijst. Deze lijst zal, tijdens
				 * een weging die uitslaat, elke munt die niet gewogen is, in de
				 * lijst van evenwicht steken.
				 */
				if (c[tweedeSpatie + 3] == 'h') {
					for (int i = 0; i < eersteSpatie; i++) {
						zwaarder.add(new Munt(c[i]));
						lichter.add(new Munt(c[i + eersteSpatie + 1]));
						nietGevraagd.add(new Munt(c[i]));
						nietGevraagd.add(new Munt(c[i + eersteSpatie + 1]));
						verschillendGewicht.add(new Munt(c[i]));
						verschillendGewicht.add(new Munt(c[i + eersteSpatie + 1]));
					}
					for (int i = 0; i < 26; i++) {
						if (-1 == nietGevraagd.zoekMunt(new Munt((char) ('a' + i)))) {
							evenwicht.add(new Munt((char) ('a' + i)));
						}
					}
				}
				if (c[tweedeSpatie + 3] == 'l') {
					for (int i = 0; i < eersteSpatie; i++) {
						lichter.add(new Munt(c[i]));
						zwaarder.add(new Munt(c[i + eersteSpatie + 1]));
						nietGevraagd.add(new Munt(c[i]));
						nietGevraagd.add(new Munt(c[i + eersteSpatie + 1]));
						verschillendGewicht.add(new Munt(c[i]));
						verschillendGewicht.add(new Munt(c[i + eersteSpatie + 1]));
					}
					for (int i = 0; i < 26; i++) {
						if (-1 == nietGevraagd.zoekMunt(new Munt((char) ('a' + i)))) {
							evenwicht.add(new Munt((char) ('a' + i)));
						}
					}
				}
				/*
				 * Hier worden de munten die zowel in evenwicht en in
				 * zwaarder/lichter zitten, uitgehaald uit zwaarder/lichter.
				 */
				zwaarder.check(evenwicht);
				lichter.check(evenwicht);
			}
			int aantalL = lichter.getMunten().size();
			int aantalZ = zwaarder.getMunten().size();
			int aantalV = verschillendGewicht.getMunten().size();

			if (aantalL == 0 && aantalZ == 0 && aantalV != 0) {
				/*
				 * Als er geen zwaardere, noch lichtere munten zijn na de check,
				 * maar er is wel ooit een verschil bij de balans opgemeten, dan
				 * is er iets fout gegaan.
				 */
				System.out.println("Inconsistente gegevens.");
			} else if (aantalL == 0 && aantalZ == 0 && aantalV == 0) {
				/*
				 * Als er nooit een uitwijking van de weegschaal is opgemerkt,
				 * dan zijn er geen valse munten gewogen.
				 */
				System.out.println("Te weinig gegevens.");
			} else if (aantalL == 1 && aantalZ == 0 && aantalV != 0) {
				/*
				 * Als er juist 1 afwijkende munt is, dan zal dat wel de foute
				 * munt zijn.
				 */
				System.out.println("Het valse geldstuk " + lichter.getMunten().get(0).getChar() + " is lichter.");
			} else if (aantalL == 0 && aantalZ == 1 && aantalV != 0) {
				System.out.println("Het valse geldstuk " + zwaarder.getMunten().get(0).getChar() + " is zwaarder.");
			} else if ((aantalL > 1 && aantalZ == 0) || (aantalZ > 1 && aantalL == 0)) {
				/*
				 * Als er teveel munten aan 1 kant zitten, dan is er te weining
				 * info om hieruit de valse munt te halen.
				 */
				System.out.println("Te weinig gegevens.");
			} else if (!lichter.compare(zwaarder)) {
				// Zie uitleg functie
				System.out.println("Te weinig gegevens.");
			} else if (lichter.compare(zwaarder)) {
				System.out.println("Inconsistente gegevens.");
			}
		}
		System.out.println(System.currentTimeMillis()-time);
	}
}

class Munten {
	private List<Munt> munten;

	public void add(Munt m) {
		if (-1 == zoekMunt(m)) {
			munten.add(m);
		}
	}

	/*
	 * Hier worden de lijsten lichter en zwaarder vergeleken. Als er een munt is
	 * die zowel lichter als zwaarder is, zullen het foute metingen zijn, en
	 * hierdoor verkeerde uitkomst geven. Indien dat niet het geval is, zijn er
	 * gewoon te weinig gegevens.
	 */
	public boolean compare(Munten zwaarder) {
		for (int i = 0; i < munten.size(); i++) {
			if (-1 != zwaarder.zoekMunt(munten.get(i))) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Hier worden alle munten verwijderd uit de lijst als deze munten ook in de
	 * lijst evenwicht zitten.
	 */
	public void check(Munten evenwicht) {
		for (int i = 0; i < evenwicht.munten.size(); i++) {
			int zoek = zoekMunt(evenwicht.munten.get(i));
			if (-1 != zoek) {
				munten.remove(zoek);
			}
		}
	}

	public Munten() {
		munten = new ArrayList<Munt>();
	}

	public List<Munt> getMunten() {
		return munten;
	}

	public String toString() {
		StringBuffer munt = new StringBuffer();
		for (int i = 0; i < munten.size(); i++) {
			munt.append(munten.get(i).getChar());
		}
		return munt.toString();
	}

	public int zoekMunt(Munt k) {
		int aantal = munten.size();
		boolean gevonden = false;
		int i = 0;
		while (i < aantal && !gevonden) {
			Munt munt = munten.get(i);
			if (munt.compare(k))
				gevonden = true;
			else
				i++;
		}
		if (gevonden)
			return i;
		else
			return -1;
	}
}

class Munt {
	private char c;

	public Munt(char d) {
		c = d;
	}

	public boolean compare(Munt k) {
		if (c == k.getChar()) {
			return true;
		}
		return false;
	}

	public char getChar() {
		return c;
	}
}
