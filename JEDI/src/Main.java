import java.util.*;
import java.util.Map.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		long time = System.currentTimeMillis();
		for (int i = 0; i < gevallen; i++) {
			System.out.print((i + 1) + " ");
			int knopen = sc.nextInt();
			int aantalLijnen = sc.nextInt();
			sc.nextLine();
			Graaf graaf = new Graaf(knopen);
			for (int j = 0; j < aantalLijnen; j++) {
				graaf.addVerbinding(sc.nextInt(), sc.nextInt(), sc.nextInt());
				sc.nextLine();
				// System.out.println(i+" "+j+" ");
			}
			graaf.bellmanFord();
			int afstand = graaf.getNodes().get(graaf.getNodes().size() - 1).getAfstand();
			int afstand2 = graaf.getNodes().get(graaf.getNodes().size() - 1).getAfstand2();
			// System.out.println(afstand+" "+afstand2);
			System.out.println((afstand != afstand2) ? "min oneindig" : afstand > 99999 ? "plus oneindig" : afstand);

		}
		// System.out.println(System.currentTimeMillis()-time);

	}

}

class Graaf {
	private ArrayList<Node> nodes;

	public Graaf(int knopen) {
		nodes = new ArrayList<Node>();
		for (int i = 0; i < knopen; i++) {
			nodes.add(new Node(i + 1));
		}
	}

	public void bellmanFord() {
		for (int i = 0; i < 10 * nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				for (Entry<Node, Integer> setNode : nodes.get(j).getVerbindingen().entrySet()) {
					if (nodes.get(j).getAfstand() + setNode.getValue() < setNode.getKey().getAfstand()) {
						setNode.getKey().setAfstand(nodes.get(j).getAfstand() + setNode.getValue());
						setNode.getKey().setAfstand2(nodes.get(j).getAfstand() + setNode.getValue());
					}
				}

			}
		}
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				for (Entry<Node, Integer> setNode : nodes.get(j).getVerbindingen().entrySet()) {
					if (nodes.get(j).getAfstand2() + setNode.getValue() < setNode.getKey().getAfstand2()) {
						setNode.getKey().setAfstand2(nodes.get(j).getAfstand2() + setNode.getValue());
					}
				}

			}
		}
	}

	public void addVerbinding(int start, int einde, int gewicht) {
		nodes.get(start - 1).addVerbinding(nodes.get(einde - 1), gewicht);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
}

class Node {
	private int knoopNr;
	private Map<Node, Integer> verbindingen;
	private int afstand;
	private int afstand2;

	public Node(int i) {
		verbindingen = new HashMap<Node, Integer>();
		knoopNr = i;
		afstand = i == 1 ? 0 : 999999;
		afstand2 = i == 1 ? 0 : 999999;
	}

	public String toString() {
		return "node: " + Integer.toString(knoopNr);
	}

	public void addVerbinding(Node node, int gewicht) {
		verbindingen.put(node, gewicht);
	}

	public int getKnoopNr() {
		return knoopNr;
	}

	public void setKnoopNr(int knoopNr) {
		this.knoopNr = knoopNr;
	}

	public Map<Node, Integer> getVerbindingen() {
		return verbindingen;
	}

	public int getAfstand() {
		return afstand;
	}

	public void setVerbindingen(Map<Node, Integer> verbindingen) {
		this.verbindingen = verbindingen;
	}

	public void setAfstand(int afstand) {
		this.afstand = afstand;
	}

	public int getAfstand2() {
		return afstand2;
	}

	public void setAfstand2(int afstand2) {
		this.afstand2 = afstand2;
	}
}
//this is not the code you're looking for