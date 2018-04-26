import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// gebruikelijke variabelen aanmaken
		int persoon = 0, locatie = 0, wapens = 0, vragen = 0, gevallen = 0;
		String s = sc.nextLine();
		Personen p = new Personen();
		gevallen = Integer.parseInt(s);
		// for-loop voor het aantal gevallen dat eerst wordt ingelezen
		for (int g = 1; g <= gevallen; g++) {
			System.out.print(g + " ");
			s = sc.nextLine();
			/*
			 * Het aantal kaarten wordt ingelezen en dan toegekend aan de
			 * variabelen De If-statement is voor het geval er een verkeerde
			 * imput wordt gegeven, zal er niks van het programma worden
			 * uitgevoerd, bijvoorbeeld wanneer er na het aantal gevallen direct
			 * met het aantal vragen wordt begonnen
			 */
			if (s.length() == 5) {
				persoon = Integer.parseInt(s.substring(0, 1));
				locatie = Integer.parseInt(s.substring(2, 3));
				wapens = Integer.parseInt(s.substring(4));
				/*
				 * Als er 1 verdachte, 1 locatie en 1 wapen is, dan moeten de
				 * andere kaarten gelijk verdeeld worden voor de anderen.
				 * Aangezien het een int is, zal het het kleinste veelvoud van 4
				 * nemen dat kleiner is dan alle kaarten tesamen.
				 */
				int kaartenpp = (persoon + locatie + wapens) / 4;
				/*
				 * Voor iedere speler wordt het object en zijn lijst met alle
				 * kaarten aangemaakt.
				 */
				for (int j = 1; j < 5; j++) {
					Kaarten k0 = new Kaarten();
					for (int i = 0; i < persoon; i++) {
						k0.kaartToevoegen((char) ('1' + i));
					}
					for (int i = 0; i < locatie; i++) {
						k0.kaartToevoegen((char) ('A' + i));
					}
					for (int i = 0; i < wapens; i++) {
						k0.kaartToevoegen((char) ('a' + i));
					}
					Persoon persoon0 = new Persoon(k0, j);
					p.persoonToevoegen(persoon0);
				}
				vragen = Integer.parseInt(sc.nextLine());
				// For-Loop voor alle vragen.
				for (int j = 0; j < vragen; j++) {
					s = sc.nextLine();
					/*
					 * Hier wordt er gekeken wie de vraag stelt, en wie
					 * antwoord, en dus bij wie er kaarten kunnen verwijderd
					 * worden. Dit zou waarschijnlijk beknopter en efficienter
					 * kunnen met een modulo-teller.
					 */
					if (s.substring(6).equals("X")) {
						for (int i = 0; i < 4; i++) {
							if (i + 1 != Integer.parseInt(s.substring(0, 1))) {
								p.getPersoon(i).verwijderKaart(s);
							}
						}
					} else if (Integer.parseInt(s.substring(0, 1)) == 1) {
						if (Integer.parseInt(s.substring(6)) == 2) {
							p.getPersoon(1).kaartToevoegen(s);
							p.getPersoon(1).vraagToevoegen(s);
						} else if (Integer.parseInt(s.substring(6)) == 3) {
							p.getPersoon(2).kaartToevoegen(s);
							p.getPersoon(2).vraagToevoegen(s);
							p.getPersoon(1).verwijderKaart(s);
						} else {
							p.getPersoon(3).kaartToevoegen(s);
							p.getPersoon(3).vraagToevoegen(s);
							p.getPersoon(1).verwijderKaart(s);
							p.getPersoon(2).verwijderKaart(s);
						}
					} else if (Integer.parseInt(s.substring(0, 1)) == 2) {
						if (Integer.parseInt(s.substring(6)) == 3) {
							p.getPersoon(2).vraagToevoegen(s);
							p.getPersoon(2).kaartToevoegen(s);
						} else if (Integer.parseInt(s.substring(6)) == 4) {
							p.getPersoon(3).vraagToevoegen(s);
							p.getPersoon(3).kaartToevoegen(s);
							p.getPersoon(2).verwijderKaart(s);
						} else {
							p.getPersoon(0).vraagToevoegen(s);
							p.getPersoon(0).kaartToevoegen(s);
							p.getPersoon(2).verwijderKaart(s);
							p.getPersoon(3).verwijderKaart(s);
						}
					} else if (Integer.parseInt(s.substring(0, 1)) == 3) {
						if (Integer.parseInt(s.substring(6)) == 4) {
							p.getPersoon(3).kaartToevoegen(s);
							p.getPersoon(3).vraagToevoegen(s);
						} else if (Integer.parseInt(s.substring(6)) == 1) {
							p.getPersoon(0).kaartToevoegen(s);
							p.getPersoon(0).vraagToevoegen(s);
							p.getPersoon(3).verwijderKaart(s);
						} else {
							p.getPersoon(1).kaartToevoegen(s);
							p.getPersoon(1).vraagToevoegen(s);
							p.getPersoon(0).verwijderKaart(s);
							p.getPersoon(3).verwijderKaart(s);
						}
					} else if (Integer.parseInt(s.substring(0, 1)) == 4) {
						if (Integer.parseInt(s.substring(6)) == 1) {
							p.getPersoon(0).kaartToevoegen(s);
							p.getPersoon(0).vraagToevoegen(s);
						} else if (Integer.parseInt(s.substring(6)) == 2) {
							p.getPersoon(1).kaartToevoegen(s);
							p.getPersoon(1).vraagToevoegen(s);
							p.getPersoon(0).verwijderKaart(s);
						} else {
							p.getPersoon(2).kaartToevoegen(s);
							p.getPersoon(2).vraagToevoegen(s);
							p.getPersoon(0).verwijderKaart(s);
							p.getPersoon(1).verwijderKaart(s);
						}
					}
				}
				/*
				 * Deze lus zal duplicaten halen uit bepaalde lijsten, en ze
				 * sorteren.
				 */
				for (int i = 0; i < 4; i++) {
					p.getPersoon(i).getKansKaarten().sorteren();
					p.getPersoon(i).getKanNietHebben().sorteren();
				}

				/*
				 * In deze lus wordt er gekeken naar vorige vragen, en waarbij
				 * hij 2 van de 3 kaarten niet heeft, moet hij wel de 3e kaart
				 * hebben, en kan dus de rest die niet hebben.
				 */
				for (int i = 0; i < 4; i++) {
					String kaarten = p.getPersoon(i).overlopenVragen();

					for (int j = 0; j < 4; j++) {
						if (i != j && kaarten != "#") {
							p.getPersoon(j).verwijderAlleKaarten(kaarten);
						}
					}

				}


				/*
				 * Als een persoon het minimum aantal kaarten heeft, dan kan de
				 * rest geen van deze kaarten hebben, dus die worden ook
				 * verwijderd.
				 */
				for (int i = 0; i < 4; i++) {
					if (p.getPersoon(i).getMogelijkeKaarten().getAantal() == kaartenpp) {
						p.getPersoon((i + 1) % 4).verwijderAlleKaarten(p.getPersoon(i).toString());
						p.getPersoon((i + 2) % 4).verwijderAlleKaarten(p.getPersoon(i).toString());
						p.getPersoon((i + 3) % 4).verwijderAlleKaarten(p.getPersoon(i).toString());
					}
					p.getPersoon(i).combineren();


				}

				for (int i = 0; i < 4; i++) {
					String kaarten = p.getPersoon(i).overlopenVragen();
					for (int j = 0; j < 4; j++) {
						if (i != j && kaarten != "#") {
							p.getPersoon(j).verwijderAlleKaarten(kaarten);
						}
					}

				}
				
				for (int i = 0; i < 4; i++) {
					if (p.getPersoon(i).getMogelijkeKaarten().getAantal() > kaartenpp) {
						StringBuffer string=new StringBuffer();
						Set<Character> set=new HashSet<Character>();
						String a=p.getPersoon(i).overlopenVragen();
						for(int q=0;q<a.length();q++){
							if(!set.contains(a.toCharArray()[q])){
								set.add(a.toCharArray()[q]);
								string.append(a.toCharArray()[q]);
							}
							
						}
						if(string.length()==kaartenpp){
							p.getPersoon(i).verwijderAlleKaarten(p.getPersoon(i).toString());
							
							p.getPersoon(i).alleKaartenToevoegen(string.toString());
						}
						
					}

				}
				for (int i = 0; i < 4; i++) {
					String kaarten = p.getPersoon(i).toString();
					for (int j = 0; j < 4; j++) {
						if (i != j && kaarten != "#") {
							p.getPersoon(j).verwijderAlleKaarten(kaarten);
						}
					}

				}

				/*
				 * Voor de laatste gevallen eruit te halen, wordt er gekeken dat
				 * er van elke soort kaart (personen, locaties en wapens) er
				 * maar een onbekende is. Dit maakt het mogelijk om een kleine
				 * selectie kaarten eruit te halen, alhoewel het niet volledig
				 * waterdicht is. De eerste lus kijkt naar personen die al het
				 * minimum aantal kaarten hebben, en waarbij we dus zeker zijn
				 * van hun kaarten.
				 */
				int gevondenPersonen = 0, gevondenLocaties = 0, gevondenWapens = 0;
				char[] kaarten = null;
				for (int i = 0; i < 4; i++) {
					kaarten = p.getPersoon(i).toString().toCharArray();
					for (int j = 0; j < kaarten.length; j++) {
						if (p.getPersoon(i).getMogelijkeKaarten().getAantal() == kaartenpp) {
							if (kaarten[j] >= '1' && kaarten[j] <= '9') {
								gevondenPersonen++;
							} else if (kaarten[j] >= 'A' && kaarten[j] <= 'I') {
								gevondenLocaties++;
							} else if (kaarten[j] >= 'a' && kaarten[j] <= 'i') {
								gevondenWapens++;
							}
						}
					}
					p.getPersoon(i).combineren();

				}
				/*
				 * Deze zal kijken naar de personen die kaarten te veel hebben,
				 * en eenmaal er nog maar 1 onbekende kaart overblijft, worden
				 * de andere kaarten van dezelfde soort verwijderd.
				 */
				for (int i = 0; i < 4; i++) {
					kaarten = p.getPersoon(i).toString().toCharArray();
					for (int j = 0; j < kaarten.length; j++) {
						if (p.getPersoon(i).getMogelijkeKaarten().getAantal() != kaartenpp) {
							if (kaarten[j] >= '1' && kaarten[j] <= '9') {
								if (gevondenPersonen + 1 < persoon) {
									gevondenPersonen++;
								} else {
									p.getPersoon(i).verwijderAlleKaarten(Character.toString(kaarten[j]));
								}
							} else if (kaarten[j] >= 'A' && kaarten[j] <= 'I') {
								if (gevondenLocaties + 1 < locatie) {
									gevondenLocaties++;
								} else {
									p.getPersoon(i).verwijderAlleKaarten(Character.toString(kaarten[j]));
								}
							} else if (kaarten[j] >= 'a' && kaarten[j] <= 'i') {
								if (gevondenWapens + 1 < wapens) {
									gevondenWapens++;
								} else {
									p.getPersoon(i).verwijderAlleKaarten(Character.toString(kaarten[j]));
								}
							}
						}
					}

				}
				for (int i = 0; i < 4; i++) {
					p.getPersoon(i).getKansKaarten().sorteren();
					p.getPersoon(i).getKanNietHebben().sorteren();
					p.getPersoon(i).getMogelijkeKaarten().sorteren();
				}



				// Uitprinten van de kaarten

				System.out.print(p.getPersoon(0).toString() + " " + p.getPersoon(1).toString() + " ");
				System.out.print(p.getPersoon(2).toString() + " " + p.getPersoon(3).toString());
				System.out.print(System.lineSeparator());
				// opkuisen voor het volgende geval.F
				for (int i = 4; i > 0; i--) {
					p.verwijder(0);
				}
				
			}
		}
	}
}

class Personen {
	private List<Persoon> personen;

	public void persoonToevoegen(Persoon P) {
		personen.add(P);
	}

	public void verwijder(int i) {
		if (!personen.isEmpty()) {
			personen.remove(0);
		}
	}

	public Persoon getPersoon(int i) {
		return personen.get(i);
	}

	public Personen() {
		personen = new ArrayList<Persoon>();
	}
}

class Persoon {
	private int naam; // niet nodig, de personen zijn niet meer dan en nummer,
						// maar voor volledigheid zit het erin.
	private Kaarten mogelijkeKaarten;
	private Kaarten kansKaarten;
	private Kaarten kanNietHebben;
	private List<String> vragen;

	public Persoon(Kaarten k, int i) {
		naam = i;
		mogelijkeKaarten = k;
		kansKaarten = new Kaarten();
		kanNietHebben = new Kaarten();
		vragen = new ArrayList<String>();
	}

	/*
	 * Hierbij wordt er gekeken als er bij een bepaalde vraag 2 kaarten zitten
	 * die hij zeker niet kan hebben, en indien dit het geval is, wordt de 3e
	 * kaart, die hij zeker moet hebben bij de rest verwijderd. Daarom wordt er
	 * een string gereturned, zodat deze kan gebruikt worden om de kaarten te
	 * verwijderen.
	 */
	public String overlopenVragen() {
		StringBuffer zekerheden = new StringBuffer();
		for (int i = 0; i < vragen.size(); i++) {
			int a = 0, b = 0, c = 0;
			if (kanNietHebben.zoekKaart(vragen.get(i).substring(0, 1)) == -1) {
				a = -1;
			}
			if (kanNietHebben.zoekKaart(vragen.get(i).substring(1, 2)) == -1) {
				b = -1;
			}
			if (kanNietHebben.zoekKaart(vragen.get(i).substring(2)) == -1) {
				c = -1;
			}
			if (a + b + c == -1) {
				zekerheden.append(vragen.get(i).substring(a * 0 + b * -1 + c * -2, a * 0 + b * -1 + c * -2 + 1));
			}
		}
		return zekerheden.toString();
	}

	/*
	 * Hier worden kaarten toegevoegd aan de "kansKaarten" Array. Dit zijn
	 * allemaal kaarten van vragen waarop ze positief hebben geantwoord.
	 */
	public void kaartToevoegen(String s) {
		for (int i = 2; i < 5; i++) {
			if (kanNietHebben.zoekKaart(s.substring(i, i + 1)) == -1) {
			}
			kansKaarten.kaartToevoegen(s.charAt(i));
		}
	}
	public void alleKaartenToevoegen(String s) {
		for (int i = 0; i < s.length(); i++) {
			mogelijkeKaarten.kaartToevoegen(s.charAt(i));
		}
	}

	// Vragen worden toegevoegd, om later te overlopen
	public void vraagToevoegen(String s) {
		vragen.add(s.substring(2, 5));
	}

	/*
	 * Als een persoon niet kan antwoorden, wordt de kaarten van de vraag
	 * verwijderd, en toegevoegd aan de array van kaarten dat hij niet kan
	 * hebben. De input is hier gewoon de lijn van de vraag.
	 */
	public void verwijderKaart(String s) {
		for (int i = 2; i < 5; i++) {
			mogelijkeKaarten.verwijderKaart(s.substring(i, i + 1));
			kanNietHebben.kaartToevoegen(s.charAt(i));
		}
	}

	/*
	 * Hierbij wordt hetzelfde gedaan als hierboven, maar de imput zijn gewoon
	 * kaarten.
	 */
	public void verwijderAlleKaarten(String s) {
		for (int i = 0; i < s.length(); i++) {
			mogelijkeKaarten.verwijderKaart(s.substring(i, i + 1));
			kanNietHebben.kaartToevoegen(s.charAt(i));
		}
	}

	public Kaarten getMogelijkeKaarten() {
		return mogelijkeKaarten;
	}

	public Kaarten getKansKaarten() {
		return kansKaarten;
	}

	public Kaarten getKanNietHebben() {
		return kanNietHebben;
	}

	public String toString() {
		StringBuffer mogelijkeKaart = new StringBuffer();
		kanNietHebben.sorteren();
		for (String kaart2 : mogelijkeKaarten.getLijstKaarten(1))
			mogelijkeKaart.append(kaart2);
		return mogelijkeKaart.toString();
	}

	/*
	 * Als een bepaalde kaart niet aan een persoon wordt gevraagd, zal de kans
	 * dat hij die kaart hebben verkleinen, daarom dat ik ze verwijder.
	 */
	public void combineren() {
		for (int i = 0; i < mogelijkeKaarten.getAantal(); i++) {
			if (kansKaarten.zoekKaart(mogelijkeKaarten.getKaart(i)) == -1) {
				mogelijkeKaarten.verwijderKaart(mogelijkeKaarten.getKaart(i));
			}
		}
	}
}

class Kaarten {
	private List<String> kaarten;
	private int grootte;

	public Kaarten() {
		kaarten = new ArrayList<String>();
	}

	public int getAantal() {
		return grootte;
	}

	public String getKaart(int i) {
		return kaarten.get(i);
	}

	// Gewoon kaarten toevoegen, een kijken alse we geen duplicaten maken.
	public void kaartToevoegen(char c) {
		if (!kaarten.contains(c)) {
			kaarten.add(String.valueOf(c));
			grootte++;
		}
	}

	/*
	 * Sorteren en duplicaten eruit halen. Aangezien hashsets geen duplicaten
	 * toelaten is dit gemakkelijk gedaan.
	 */
	public void sorteren() {
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(kaarten);
		kaarten.clear();
		kaarten.addAll(hs);
		kaarten.sort(null);
	}

	public List<String> getLijstKaarten(int i) {
		return kaarten;
	}

	/*
	 * zoeken voor een kaart op kaartnaam. indien deze niet gevonden wordt,
	 * geeft het -1 terug, anders geeft het de index terug.
	 */
	public int zoekKaart(String k) {
		int aantal = kaarten.size();
		boolean gevonden = false;
		int i = 0;
		while (i < aantal && !gevonden) {
			String kaart = kaarten.get(i);
			if (kaart.equals(k))
				gevonden = true;
			else
				i++;
		}
		if (gevonden)
			return i;
		else
			return -1;
	}

	public String toString() {
		StringBuffer kaart = new StringBuffer();
		for (String kaart2 : kaarten)
			kaart.append(kaart2 + ", ");
		return kaart.toString();
	}

	/*
	 * Deze verwijderd een kaart, gebruik makende van de zoekfunctie die vroeger
	 * is aangemaakt.
	 */
	public void verwijderKaart(String k) {
		int index = this.zoekKaart(k);
		if (index >= 0) {
			kaarten.remove(index);
			grootte--;
		}
	}
}
