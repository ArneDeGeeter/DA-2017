import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		ArrayList<Auto> autos = new ArrayList<Auto>();
		try {
			Scanner sc = new Scanner(new File("autos.txt"));

			while (sc.hasNextLine()) {
				// System.out.println(sc.nextLine());
				String s = sc.nextLine();
				if (!s.isEmpty()) {
					Auto a = new Auto(s);
					autos.add(a);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SearchCriteria search = SearchCriteria.getInstance();
		Scanner sc = new Scanner(System.in);
		System.out.println("Geef de aankoopprijs in:");
		System.out.println("U kan kiezen uit: vhigh, high, med, low");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		String s = sc.nextLine();
		String aankoopprijs = null;
		if (!s.isEmpty()) {
			aankoopprijs = "aankoopPrijs".concat(s.toLowerCase());
		}

		printEnters(20);
		System.out.println("Geef de onderhoudprijs in:");
		System.out.println("U kan kiezen uit: vhigh, high, med, low");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		s = sc.nextLine();
		String onderhoudprijs = null;
		if (!s.isEmpty()) {
			onderhoudprijs = "onderhoudsprijs".concat(s.toLowerCase());
		}
		printEnters(20);

		System.out.println("Geef de aantalDeuren in:");
		System.out.println("U kan kiezen uit: 2, 3, 4, 5more");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		s = sc.nextLine();
		String aantalDeuren = null;
		if (!s.isEmpty()) {
			aantalDeuren = "aantalDeuren".concat(s.toLowerCase());
		}
		printEnters(20);
		System.out.println("Geef de aantalPersonen in:");
		System.out.println("U kan kiezen uit:  2, 4, more");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		s = sc.nextLine();
		String aantalPersonen = null;
		if (!s.isEmpty()) {
			aantalPersonen = "aantalPersonen".concat(s.toLowerCase());
		}
		printEnters(20);
		System.out.println("Geef de kofferruimte in:");
		System.out.println("U kan kiezen uit: small, med, big");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		s = sc.nextLine();
		String kofferruimte = null;
		if (!s.isEmpty()) {
			kofferruimte = "kofferruimte".concat(s.toLowerCase());
		}
		printEnters(20);
		System.out.println("Geef de veiligheid in:");
		System.out.println("U kan kiezen uit: low, med, high");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		s = sc.nextLine();
		String veiligheid = null;
		if (!s.isEmpty()) {
			veiligheid = "veiligheid".concat(s.toLowerCase());
		}
		printEnters(20);
		System.out.println("Geef de klasse in:");
		System.out.println("U kan kiezen uit: unacc, acc, good, vgood");
		System.out.println("Indien u deze Categorie wil overslaan, druk gewoon op enter");
		s = sc.nextLine();
		String klasse = null;
		if (!s.isEmpty()) {
			klasse = "klasse".concat(s.toLowerCase());
		}

		Stream<Auto> autos1=filter(autos.stream(), aankoopprijs, search);
		autos1=filter(autos1, onderhoudprijs, search);
		autos1=filter(autos1, aantalDeuren, search);
		autos1=filter(autos1, aantalPersonen, search);
		autos1=filter(autos1, kofferruimte, search);
		autos1=filter(autos1, veiligheid, search);
		autos1=filter(autos1, klasse, search);
		autos1.forEach(Auto::print);

	}

	static Stream<Auto> filter(Stream<Auto> autos, String pred, SearchCriteria search) {
		if (pred != null) {
			return autos.filter(search.getCriteria(pred));
		} else {
			return autos;
		}
	}

	static void printEnters(int j) {
		for (int i = 0; i < j; i++) {
			System.out.println();
		}
	}
}

class SearchCriteria {
	private final Map<String, Predicate<Auto>> searchMap = new HashMap<>();

	private SearchCriteria() {
		initSearchMap();
	}

	private void initSearchMap() {
		Predicate<Auto> aankoopPrijsVhigh = p -> p.getAankoopprijs().equals("vhigh");
		Predicate<Auto> aankoopPrijsHigh = p -> p.getAankoopprijs().equals("high");
		Predicate<Auto> aankoopPrijsMed = p -> p.getAankoopprijs().equals("med");
		Predicate<Auto> aankoopPrijsLow = p -> p.getAankoopprijs().equals("low");
		Predicate<Auto> onderhoudsprijsVhigh = p -> p.getOnderhoudprijs().equals("vhigh");
		Predicate<Auto> onderhoudsprijsHigh = p -> p.getOnderhoudprijs().equals("high");
		Predicate<Auto> onderhoudsprijsMed = p -> p.getOnderhoudprijs().equals("med");
		Predicate<Auto> onderhoudsprijsLow = p -> p.getOnderhoudprijs().equals("low");
		Predicate<Auto> aantalDeuren2 = p -> p.getAantalDeuren().equals("2");
		Predicate<Auto> aantalDeuren3 = p -> p.getAantalDeuren().equals("3");
		Predicate<Auto> aantalDeuren4 = p -> p.getAantalDeuren().equals("4");
		Predicate<Auto> aantalDeuren5more = p -> p.getAantalDeuren().equals("5more");
		Predicate<Auto> aantalPersonen2 = p -> p.getAantalPersonen().equals("2");
		Predicate<Auto> aantalPersonen4 = p -> p.getAantalPersonen().equals("4");
		Predicate<Auto> aantalPersonenMore = p -> p.getAantalPersonen().equals("more");
		Predicate<Auto> kofferruimteSmall = p -> p.getKofferruimte().equals("small");
		Predicate<Auto> kofferruimteMed = p -> p.getKofferruimte().equals("med");
		Predicate<Auto> kofferruimteBig = p -> p.getKofferruimte().equals("big");
		Predicate<Auto> veiligheidLow = p -> p.getVeiligheid().equals("low");
		Predicate<Auto> veiligheidMed = p -> p.getVeiligheid().equals("med");
		Predicate<Auto> veiligheidHigh = p -> p.getVeiligheid().equals("high");
		Predicate<Auto> klasseUnacc = p -> p.getKlasse().equals("unacc");
		Predicate<Auto> klasseAcc = p -> p.getKlasse().equals("acc");
		Predicate<Auto> klasseGood = p -> p.getKlasse().equals("good");
		Predicate<Auto> klasseVgood = p -> p.getKlasse().equals("vgood");

		searchMap.put("aankoopPrijsvhigh", aankoopPrijsVhigh);
		searchMap.put("aankoopPrijshigh", aankoopPrijsHigh);
		searchMap.put("aankoopPrijsmed", aankoopPrijsMed);
		searchMap.put("aankoopPrijslow", aankoopPrijsLow);
		searchMap.put("onderhoudsprijsvhigh", onderhoudsprijsVhigh);
		searchMap.put("onderhoudsprijshigh", onderhoudsprijsHigh);
		searchMap.put("onderhoudsprijsmed", onderhoudsprijsMed);
		searchMap.put("onderhoudsprijslow", onderhoudsprijsLow);
		searchMap.put("aantalDeuren2", aantalDeuren2);
		searchMap.put("aantalDeuren3", aantalDeuren3);
		searchMap.put("aantalDeuren4", aantalDeuren4);
		searchMap.put("aantalDeuren5more", aantalDeuren5more);
		searchMap.put("aantalPersonen2", aantalPersonen2);
		searchMap.put("aantalPersonen4", aantalPersonen4);
		searchMap.put("aantalPersonenmore", aantalPersonenMore);
		searchMap.put("kofferruimtesmall", kofferruimteSmall);
		searchMap.put("kofferruimtemed", kofferruimteMed);
		searchMap.put("kofferruimtebig", kofferruimteBig);
		searchMap.put("veiligheidlow", veiligheidLow);
		searchMap.put("veiligheidmed", veiligheidMed);
		searchMap.put("veiligheidhigh", veiligheidHigh);
		searchMap.put("klasseunacc", klasseUnacc);
		searchMap.put("klasseacc", klasseAcc);
		searchMap.put("klassegood", klasseGood);
		searchMap.put("klassevgood", klasseVgood);
	}

	public Predicate<Auto> getCriteria(String PredicateName) {
			Predicate<Auto> target;
			target = searchMap.get(PredicateName);
			System.out.println(PredicateName);

			if (target == null) {
				System.out.println("Search Criteria not found..." + PredicateName);
				throw new NullPointerException();
			}
			return target;
	}

	public static SearchCriteria getInstance() {
		return new SearchCriteria();
	}
}

class Auto {
	private String aankoopprijs;
	private String onderhoudprijs;
	private String aantalDeuren;
	private String aantalPersonen;
	private String kofferruimte;
	private String veiligheid;
	private String klasse;

	public Auto(String s) {
		int[] kommas = new int[8];
		int aantalKommas = 1;
		kommas[7] = s.length();
		kommas[0] = -1;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				kommas[aantalKommas++] = i;
			}
		}

		aankoopprijs = s.substring(kommas[0] + 1, kommas[1]);
		onderhoudprijs = s.substring(kommas[1] + 1, kommas[2]);
		aantalDeuren = s.substring(kommas[2] + 1, kommas[3]);
		aantalPersonen = s.substring(kommas[3] + 1, kommas[4]);
		kofferruimte = s.substring(kommas[4] + 1, kommas[5]);
		veiligheid = s.substring(kommas[5] + 1, kommas[6]);
		klasse = s.substring(kommas[6] + 1, kommas[7]);

	}

	public void print() {
		System.out.println(aankoopprijs+","+onderhoudprijs+","+aantalDeuren+","+aantalPersonen+","+kofferruimte+","+veiligheid+","+klasse);
	}

	public String getAankoopprijs() {
		return aankoopprijs;
	}

	public String getOnderhoudprijs() {
		return onderhoudprijs;
	}

	public String getAantalDeuren() {
		return aantalDeuren;
	}

	public String getAantalPersonen() {
		return aantalPersonen;
	}

	public String getKofferruimte() {
		return kofferruimte;
	}

	public String getVeiligheid() {
		return veiligheid;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setAankoopprijs(String aankoopprijs) {
		this.aankoopprijs = aankoopprijs;
	}

	public void setOnderhoudprijs(String onderhoudprijs) {
		this.onderhoudprijs = onderhoudprijs;
	}

	public void setAantalDeuren(String aantalDeuren) {
		this.aantalDeuren = aantalDeuren;
	}

	public void setAantalPersonen(String aantalPersonen) {
		this.aantalPersonen = aantalPersonen;
	}

	public void setKofferruimte(String kofferruimte) {
		this.kofferruimte = kofferruimte;
	}

	public void setVeiligheid(String veiligheid) {
		this.veiligheid = veiligheid;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}
}