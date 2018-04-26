import java.util.*;
import java.util.Map.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		long time = System.currentTimeMillis();
		for (int i = 0; i < gevallen; i++) {
			int verdiepingen = Integer.parseInt(sc.next());
			Graaf graaf = new Graaf(verdiepingen);
			int liften = Integer.parseInt(sc.next());
			sc.nextLine();
			for (int j = 1; j < 2 * liften; j = j + 2) {
				graaf.add(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), j);
				sc.nextLine();
			}
			int begin = sc.nextInt();
			int einde = sc.nextInt();
			dijkstra(graaf, graaf.getNodes().get(begin), graaf.getNodes().get(einde));
			sc.nextLine();
			ArrayList<Node> resultaat = graaf.getNodes().get(einde).getKorstePad();
			ArrayList<Integer> liftenVolgorde = graaf.getNodes().get(einde).getLift();
			ArrayList<Integer> veranderenVanLift = new ArrayList<Integer>();
			ArrayList<Integer> indexVerander = new ArrayList<Integer>();
			veranderenVanLift.add(liftenVolgorde.get(0));
			for (int j = 0; j < liftenVolgorde.size(); j++) {
				if (veranderenVanLift.get(veranderenVanLift.size() - 1) != liftenVolgorde.get(j)) {
					veranderenVanLift.add(liftenVolgorde.get(j));
					indexVerander.add(j);
				}
			}
			System.out.print((i + 1) + " ");
			for (int j = 0; j < veranderenVanLift.size() - 1; j++) {
				System.out.print("(" + veranderenVanLift.get(j) + "," + resultaat.get(indexVerander.get(j)) + ")");
			}
			System.out.println("(" + veranderenVanLift.get(veranderenVanLift.size() - 1) + "," + einde + ")");
			graaf = null;
		}
		// System.out.println(System.currentTimeMillis() - time);

	}

	public static Graaf dijkstra(Graaf graaf, Node begin, Node einde) {
		begin.setAfstand(0);

		ArrayList<Node> gebruikteNodes = new ArrayList<Node>();
		ArrayList<Node> nietGebruikteNodes = new ArrayList<Node>();

		nietGebruikteNodes.add(begin);

		while (nietGebruikteNodes.size() != 0) {
			Node node = getKleinsteAfstand(nietGebruikteNodes);
			nietGebruikteNodes.remove(node);
			{
				for (Entry<Node, Integer> setNode : node.getVerbindingen().entrySet()) {
					Node aanliggendeNode = setNode.getKey();
					int gewicht = setNode.getValue();
					for (Lift l : node.getLiften()) {
						if (l.getNode() == aanliggendeNode) {
							int lift = l.getLiftnr();
							if (!gebruikteNodes.contains(aanliggendeNode)) {

								berekenKleinsteAfstand(aanliggendeNode, gewicht, node, lift);
								nietGebruikteNodes.add(aanliggendeNode);
							}
						}
					}
					// int lift = node.getLiften().get(aanliggendeNode);
					// if(aanliggendeNode.getVerdiep()==40||node.getVerdiep()==40)System.out.println(aanliggendeNode
					// +" "+node);
				}
				gebruikteNodes.add(node);

			}
		}
		return graaf;
	}

	public static Node getKleinsteAfstand(ArrayList<Node> nietGebruikteNodes) {
		Node kleinsteNode = null;
		int kleinsteAfstand = Integer.MAX_VALUE;
		for (Node node : nietGebruikteNodes) {
			int nodeDistance = node.getAfstand();
			if (nodeDistance < kleinsteAfstand) {
				kleinsteAfstand = nodeDistance;
				kleinsteNode = node;
			}
		}
		return kleinsteNode;
	}

	public static void berekenKleinsteAfstand(Node aanliggendeNode, int gewicht, Node node, int liftNr) {
		int afstand = node.getAfstand();
		/*
		 * if (aanliggendeNode.getVerdiep() == 80) {
		 * 
		 * System.out.println("____________________________________");
		 * System.out.println(node.getLift());
		 * System.out.println(aanliggendeNode.getLift());
		 * System.out.println(node.getKorstePad());
		 * System.out.println(aanliggendeNode.getKorstePad()); }
		 */
		if (afstand + gewicht < aanliggendeNode.getAfstand()) {

			/*
			 * if (aanliggendeNode.getVerdiep() == 80) {
			 * 
			 * System.out.println("@@@@@@@@@@@@@@@@@@@@@");
			 * System.out.println(node.getLift());
			 * System.out.println(aanliggendeNode.getLift());
			 * System.out.println(node.getKorstePad());
			 * System.out.println(aanliggendeNode.getKorstePad()); }
			 */

			aanliggendeNode.setAfstand(afstand + gewicht);
			ArrayList<Node> korstePad = new ArrayList<Node>(node.getKorstePad());
			ArrayList<Integer> lift = new ArrayList<Integer>(node.getLift());
			korstePad.add(node);
			lift.add(liftNr);
			/*
			 * System.out.println("@@@@@@@@@@@@@@@@@@@@@");
			 * System.out.println(lift);
			 * System.out.println(aanliggendeNode.getLift());
			 * System.out.println(korstePad);
			 * System.out.println(aanliggendeNode.getKorstePad());
			 */
			aanliggendeNode.setLift(lift);
			aanliggendeNode.setKorstePad(korstePad);

		} else if (afstand + gewicht == aanliggendeNode.getAfstand()) {
			aanliggendeNode.setAfstand(afstand + gewicht);
			ArrayList<Node> korstePad = new ArrayList<Node>(node.getKorstePad());
			ArrayList<Integer> lift = new ArrayList<Integer>(node.getLift());
			korstePad.add(node);
			lift.add(liftNr);

			if (lift.size() < aanliggendeNode.getLift().size()) {
				aanliggendeNode.setLift(lift);
				aanliggendeNode.setKorstePad(korstePad);

			} else if (lift.size() == aanliggendeNode.getLift().size()) {
				/*
				 * if (aanliggendeNode.getVerdiep() == 80) {
				 * 
				 * System.out.println("######@@@@@@@@@@@#######");
				 * System.out.println(lift);
				 * System.out.println(aanliggendeNode.getLift());
				 * System.out.println(korstePad);
				 * System.out.println(aanliggendeNode.getKorstePad()); }
				 */
				int aantalLiften1 = 0;
				int aantalLiften2 = 0;
				int vorigeLift1 = 0;
				int vorigeLift2 = 0;

				for (int i = 0; i < lift.size(); i++) {
					if (vorigeLift1 != lift.get(i)) {
						aantalLiften1++;
						vorigeLift1 = lift.get(i);
					}
					if (vorigeLift2 != aanliggendeNode.getLift().get(i)) {
						aantalLiften2++;
						vorigeLift2 = aanliggendeNode.getLift().get(i);
					}
				}
				Set<Integer> set = new HashSet<Integer>();
				set.addAll(lift);
				Set<Integer> set2 = new HashSet<Integer>();
				set2.addAll(aanliggendeNode.getLift());

				if (aantalLiften1 == aantalLiften2) {
					if (set.size() == set2.size()) {
						boolean b = true;
						int c = 0;
						while (b && c < lift.size()) {
							if (lift.get(c) < aanliggendeNode.getLift().get(c)) {
								aanliggendeNode.setLift(lift);
								aanliggendeNode.setKorstePad(korstePad);
								b = false;

							} else if (lift.get(c) > aanliggendeNode.getLift().get(c)) {
								/*
								 * if (aanliggendeNode.getVerdiep() == 80) {
								 * System.out.println();
								 * System.out.println(lift);
								 * System.out.println(); }
								 */
								b = false;
							}
							c++;
						}
					} else if (aantalLiften1 < aantalLiften2) {
						aanliggendeNode.setLift(lift);
						aanliggendeNode.setKorstePad(korstePad);

					}
				} else if (aantalLiften1 < aantalLiften2) {
					aanliggendeNode.setLift(lift);
					aanliggendeNode.setKorstePad(korstePad);

				}
			}
			/*
			 * if (aanliggendeNode.getVerdiep() == 80) {
			 * 
			 * System.out.println("##############"); System.out.println(lift);
			 * System.out.println(aanliggendeNode.getLift());
			 * System.out.println(korstePad);
			 * System.out.println(aanliggendeNode.getKorstePad()); }
			 */

		}
	}

}

class Lift {
	private Node node;
	private int liftnr;

	public Lift(Node n, int l) {
		node = n;
		liftnr = l;
	}

	public Node getNode() {
		return node;
	}

	public int getLiftnr() {
		return liftnr;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public void setLiftnr(int liftnr) {
		this.liftnr = liftnr;
	}
}

class Node {
	private int verdiep;
	private Map<Node, Integer> verbindingen;
	private ArrayList<Lift> liften;
	private ArrayList<Node> korstePad;
	private ArrayList<Integer> lift;
	private int afstand;

	public Node(int naam) {
		verbindingen = new HashMap<Node, Integer>();
		liften = new ArrayList<Lift>();
		korstePad = new ArrayList<Node>();
		lift = new ArrayList<Integer>();
		verdiep = naam;
		afstand = 999999;

	}

	void verbindingToevoegen(Node node, int lift, int tussenstap) {
		// System.out.println(verdiep+" "+node.verdiep);
		verbindingen.put(node, /* (10010 / tussenstap) - 10 */1);
		// System.out.println(10010/tussenstap-10);
		// System.out.println(tussenstap+"@@@");
		// System.out.println(this.verdiep + "\t" + node + "\t" + lift + "\t" +
		// tussenstap + "\t" + liften);
		// if (!liften.containsKey(node)) {
		liften.add(new Lift(node, lift));

		// }
		// System.out.println(this.verdiep + "\t" + node + "\t" + lift + "\t" +
		// tussenstap + "\t" + liften);

	}

	public String toString() {
		return Integer.toString(verdiep);
	}

	public int getVerdiep() {
		return verdiep;
	}

	public Map<Node, Integer> getVerbindingen() {
		return verbindingen;
	}

	public ArrayList<Node> getKorstePad() {
		return korstePad;
	}

	public ArrayList<Integer> getLift() {
		return lift;
	}

	public int getAfstand() {
		return afstand;
	}

	public void setVerdiep(int verdiep) {
		this.verdiep = verdiep;
	}

	public void setVerbindingen(Map<Node, Integer> verbindingen) {
		this.verbindingen = verbindingen;
	}

	public void setKorstePad(ArrayList<Node> korstePad) {
		this.korstePad = korstePad;
	}

	public void setLift(ArrayList<Integer> lift) {
		this.lift = lift;
	}

	public void setAfstand(int afstand) {
		this.afstand = afstand;
	}

	public ArrayList<Lift> getLiften() {
		return liften;
	}

	public void setLiften(ArrayList<Lift> liften) {
		this.liften = liften;
	}

}

class Graaf {
	private ArrayList<Node> nodes;

	public Graaf(int verdiepingen) {
		nodes = new ArrayList<Node>();
		for (int i = 0; i < verdiepingen; i++) {
			nodes.add(new Node(i));
		}
	}

	void add(int start, int einde, int tussenstap, int nummerLift) {
		for (int i = start; i < einde; i = i + tussenstap) {
			nodes.get(i).verbindingToevoegen(nodes.get(i + tussenstap), nummerLift, tussenstap);
			nodes.get(i + tussenstap).verbindingToevoegen(nodes.get(i), nummerLift + 1, tussenstap);
		}

	}

	public String toString() {
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < nodes.size(); i++) {
			string.append(nodes.get(i).getVerdiep()).append(",");
		}
		return string.toString();

	}
	/*
	 * private int[][] wegen; private int[][] verbindingen; private int begin;
	 * private int einde;
	 * 
	 * ArrayList<Node> nodes=new ArrayList<Node>(); wegen = new
	 * int[verdiepingen][verdiepingen]; verbindingen = new
	 * int[verdiepingen][verdiepingen]; for(int i=0;i<wegen[0].length;i++){
	 * for(int j=0;j<wegen[0].length;j++){ wegen[i][j]=999999; } } }
	 * 
	 * public void werk() { int totaleAfstand=0; ArrayList<Integer> edges=new
	 * ArrayList<Integer>(); ArrayList<Integer> volgorde=new
	 * ArrayList<Integer>(); for(int i=0;i<wegen[0].length;i++){
	 * edges.add(wegen[begin][i]); } boolean b=true; while(b){ int minIndex =
	 * edges.indexOf(Collections.min(edges)); int minimum=edges.get(minIndex);
	 * volgorde.add(minimum);
	 * 
	 * } }
	 * 
	 * public void add(int f, int k, int l, int j) { System.out.println(k +
	 * "###" + f); for (int i = f; i < k; i = i + l) {
	 * if(wegen[i][i+l]!=999999){ wegen[i][i + l] = j; wegen[i + l][i] = j + 1;
	 * verbindingen[i][i + l] = 1; verbindingen[i + l][i] = 1;}
	 * 
	 * }
	 * 
	 * }
	 * 
	 * public int[][] getWegen() { return wegen; }
	 * 
	 * public void setWegen(int[][] wegen) { this.wegen = wegen; }
	 * 
	 * public int getBegin() { return begin; }
	 * 
	 * public int getEinde() { return einde; }
	 * 
	 * public void setBegin(int begin) { this.begin = begin; }
	 * 
	 * public void setEinde(int einde) { this.einde = einde; }
	 */

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
}