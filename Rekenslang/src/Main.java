import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		long time=System.currentTimeMillis();
		for (int i = 0; i < gevallen; i++) {
			s = sc.nextLine();
			int aantalOnbekenden = (s.length() + 1) / 2;
			int[] mogelijkheden = new int[aantalOnbekenden];
			for (int j = 0; j < aantalOnbekenden; j++) {
				mogelijkheden[j] = s.charAt(2 * j) - '0';
			}
			String rekenslangString = sc.nextLine();
			Rekenslang rekenslang = new Rekenslang(rekenslangString, mogelijkheden, i + 1);
			//System.out.println(System.currentTimeMillis()-time+" 1");

			rekenslang.toPostfix();
			//System.out.println(System.currentTimeMillis()-time);

			rekenslang.print();

		}
		System.out.println(System.currentTimeMillis()-time);
	}

}

class Rekenslang {
	private String rekenslang;
	private List<Oplossing> oplossingen;
	private ArrayList<Integer> mogelijkheden2;
	private int geval;

	public Rekenslang(String rekenslangString, int[] mogelijkheden, int geval2) {
		rekenslang = rekenslangString;
		mogelijkheden2 = new ArrayList<Integer>();
		oplossingen = new ArrayList<Oplossing>();
		for (int i = 0; i < mogelijkheden.length; i++) {
			mogelijkheden2.add(mogelijkheden[i]);
		}
		geval = geval2;

	}

	public void print() {
		List<Integer> sorteren = new ArrayList<Integer>();
		for (int i = 0; i < oplossingen.size(); i++) {
			sorteren.add(oplossingen.get(i).getOrdening());
		}
		sorteren.sort(null);
		for (int i = 0; i < sorteren.size(); i++) {
		}
		for (int i = 0; i < oplossingen.size(); i++) {
			int a=-1;
			for(int j=0;j<oplossingen.size();j++){
				if(sorteren.get(i)==oplossingen.get(j).getOrdening()){
					a=j;
					j=oplossingen.size();
				}
			}
			System.out.print(geval);
			for (int j = 0; j < mogelijkheden2.size(); j++) {
				System.out.print(" " + String.valueOf((char) ('A' + j)) + "="+oplossingen.get(a).getWaarde()[j]);
			}
			System.out.println();
		}

	}

	public void toPostfix() {
		int plaatsSpatie = 0;
		ArrayList<String> operator = new ArrayList<String>();
		ArrayList<String> output = new ArrayList<String>();
		ArrayList<String> outputBackup = new ArrayList<String>();
		ArrayList<Integer> operatorPlaatsen = new ArrayList<Integer>();
		ArrayList<Integer> variabelePlaatsen = new ArrayList<Integer>();

		int[] spaties = new int[rekenslang.length() / 2];
		int aantalspaties = 1;
		spaties[0] = -1;
		for (int i = 0; i < rekenslang.length(); i++) {
			if (rekenslang.charAt(i) == '=') {
				plaatsSpatie = i;
			} else if (rekenslang.charAt(i) == ' ') {
				spaties[aantalspaties++] = i;

			}
		}
		String rekenslangPF = rekenslang.substring(0, plaatsSpatie).concat("-")
				.concat(rekenslang.substring(plaatsSpatie + 1).concat(" "));
		int resultaat = -1;
		for (int i = 0; i < spaties.length - 1; i++) {
			if (spaties[i] == 0) {
				spaties[i] = rekenslangPF.length() - 1;
			}
			if (spaties[i + 1] == 0) {
				spaties[i + 1] = rekenslangPF.length() - 1;
			}
			if (spaties[i + 1] - spaties[i] > 2) {
				output.add(rekenslangPF.substring(spaties[i] + 1, spaties[i + 1]));
			} else if (rekenslangPF.charAt(spaties[i + 1] - 1) >= '0' && rekenslangPF.charAt(spaties[i + 1] - 1) <= '9'
					&& (spaties[i + 1] != rekenslangPF.length()-1||spaties[i] ==rekenslangPF.length()-3)) {
				output.add((rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1])));
			} else if (rekenslangPF.charAt(spaties[i + 1] - 1) >= 'A'
					&& rekenslangPF.charAt(spaties[i + 1] - 1) <= 'Z') {
				output.add((rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1])));
				variabelePlaatsen.add(output.size() - 1);
			} else if ((rekenslangPF.charAt(spaties[i + 1] - 1) >= '*'
					&& rekenslangPF.charAt(spaties[i + 1] - 1) <= '-')
					|| (rekenslangPF.charAt(spaties[i + 1] - 1) == ':')) {
				boolean solved = false;
				while (!solved) {
					if (!operator.isEmpty()) {
						if (operator.get(0).equals("*")) {
							output.add(operator.get(0));
							operatorPlaatsen.add(output.size() - 1);
							operator.remove(0);
						} else if (operator.get(0).equals(":")) {
							output.add(operator.get(0));
							operatorPlaatsen.add(output.size() - 1);

							operator.remove(0);

						} else if (operator.get(0).equals("=")) {
							operator.remove(0);
							operator.add(rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]));

							solved = true;

						} else if (operator.get(0).equals("+")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals("*")) {
							solved = true;
						} else if (operator.get(0).equals("+")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals(":")) {
							solved = true;
						} else if (operator.get(0).equals("+")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals("+")) {
							output.add(operator.get(0));
							operatorPlaatsen.add(output.size() - 1);

							operator.remove(0);
						} else if (operator.get(0).equals("+")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals("-")) {
							output.add(operator.get(0));
							operatorPlaatsen.add(output.size() - 1);

							operator.remove(0);
						} else if (operator.get(0).equals("-")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals("*")) {
							solved = true;
						} else if (operator.get(0).equals("-")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals(":")) {
							solved = true;
						} else if (operator.get(0).equals("-")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals("+")) {
							output.add(operator.get(0));
							operatorPlaatsen.add(output.size() - 1);

							operator.remove(0);
						} else if (operator.get(0).equals("-")
								&& rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1]).equals("-")) {
							output.add(operator.get(0));
							operatorPlaatsen.add(output.size() - 1);
							operator.remove(0);
						} 
					} else {
						solved = true;
					}
				}
				operator.add(0, (rekenslangPF.substring(spaties[i + 1] - 1, spaties[i + 1])));
			}
		}
		for (int i = 0; i < operator.size(); i++) {
			output.add(operator.get(i));
			operatorPlaatsen.add(output.size() - 1);
		}

		for (int i = 0; i < output.size(); i++) {
			outputBackup.add(output.get(i));
		}
		int getal1 = 0, getal2 = 0, aantalkeren = 0;

		output.clear();
		for (int i = 0; i < outputBackup.size(); i++) {
			output.add(outputBackup.get(i));
		}
		if (mogelijkheden2.size() == 1) {
			int[] v = { 0 };
			invullenwaarden(v, output, outputBackup, mogelijkheden2, variabelePlaatsen, operatorPlaatsen, geval);

		} else {
			for (int a = 0; a < 2; a++) {
				if (mogelijkheden2.size() == 2) {
					int[] v = { a, 0 };
					invullenwaarden(v, output, outputBackup, mogelijkheden2, variabelePlaatsen, operatorPlaatsen,
							geval);
				} else {
					for (int b = 0; b < 3; b++) {
						if (mogelijkheden2.size() == 3) {
							int[] v = { b, a, 0 };
							invullenwaarden(v, output, outputBackup, mogelijkheden2, variabelePlaatsen,
									operatorPlaatsen, geval);

						} else {
							for (int c = 0; c < 4; c++) {
								if (mogelijkheden2.size() == 4) {
									int[] v = { c, b, a, 0 };
									invullenwaarden(v, output, outputBackup, mogelijkheden2, variabelePlaatsen,
											operatorPlaatsen, geval);

								} else {
									for (int d = 0; d < 5; d++) {
										if (mogelijkheden2.size() == 5) {
											int[] v = { d, c, b, a, 0 };
											invullenwaarden(v, output, outputBackup, mogelijkheden2, variabelePlaatsen,
													operatorPlaatsen, geval);

										} else {
											for (int e = 0; e < 6; e++) {
												if (mogelijkheden2.size() == 6) {
													int[] v = { e, d, c, b, a, 0 };
													invullenwaarden(v, output, outputBackup, mogelijkheden2,
															variabelePlaatsen, operatorPlaatsen, geval);

												} else {
													for (int f = 0; f < 7; f++) {
														if (mogelijkheden2.size() == 7) {
															int[] v = { f, e, d, c, b, a, 0 };
															invullenwaarden(v, output, outputBackup, mogelijkheden2,
																	variabelePlaatsen, operatorPlaatsen, geval);

														} else {
															for (int g = 0; g < 8; g++) {
																if (mogelijkheden2.size() == 8) {
																	int[] v = { g, f, e, d, c, b, a, 0 };
																	invullenwaarden(v, output, outputBackup,
																			mogelijkheden2, variabelePlaatsen,
																			operatorPlaatsen, geval);

																} else {
																	for (int h = 0; h < 9; h++) {
																		int[] v = { h, g, f, e, d, c, b, a, 0 };
																		invullenwaarden(v, output, outputBackup,
																				mogelijkheden2, variabelePlaatsen,
																				operatorPlaatsen, geval);
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}


	private void invullenwaarden(int[] volgorde, ArrayList<String> output, ArrayList<String> outputBackup,
			ArrayList<Integer> mogelijkheden2, ArrayList<Integer> plaatsen, ArrayList<Integer> opPlaatsen, int geval2) {
		int resultaat = 0, getal1 = 0, getal2 = 0;
		ArrayList<Integer> mogelijkheden3 = new ArrayList<Integer>();
		for (int i = 0; i < mogelijkheden2.size(); i++) {
			mogelijkheden3.add(mogelijkheden2.get(i));
		}

		boolean[] test = new boolean[mogelijkheden2.size()];
		output.clear();
		ArrayList<String> output2 = new ArrayList<String>();
		for (int i = 0; i < outputBackup.size(); i++) {
			output.add(outputBackup.get(i));
			output2.add(outputBackup.get(i));
		}

		for (int j = 0; j < mogelijkheden2.size(); j++) {

			int q = plaatsen.get(j);
			output.remove(q);
			output.add(plaatsen.get(j), Integer.toString(mogelijkheden3.get(volgorde[j])));
			mogelijkheden3.remove(volgorde[j]);
		}
		ArrayList<String> output3 = new ArrayList<String>();
		for (int i = 0; i < output.size(); i++) {
			output3.add(output.get(i));
		}
		
		for (int s = 0; s < opPlaatsen.size(); s++) {
			if (output.get(opPlaatsen.get(s) - 1 - 2 * s).charAt(0) < 'A') {
				getal2 = Integer.parseInt(output.get(opPlaatsen.get(s) - 1 - 2 * s));
			}
			if (output.get(opPlaatsen.get(s) - 2 - 2 * s).charAt(0) < 'A') {
				getal1 = Integer.parseInt(output.get(opPlaatsen.get(s) - 2 - 2 * s));
			}
			if (output.get(opPlaatsen.get(s) - 2 * s).equals("*")) {
				resultaat = getal1 * getal2;
			} else if (output.get(opPlaatsen.get(s) - 2 * s).equals("+")) {
				resultaat = getal1 + getal2;
			} else if (output.get(opPlaatsen.get(s) - 2 * s).equals("-")) {
				resultaat = getal1 - getal2;
			} else if (output.get(opPlaatsen.get(s) - 2 * s).equals(":")) {
				resultaat = getal1 / getal2;
			}
			output.remove(opPlaatsen.get(s) - 2 - 2 * s);
			output.remove(opPlaatsen.get(s) - 2 - 2 * s);
			output.remove(opPlaatsen.get(s) - 2 - 2 * s);
			output.add(opPlaatsen.get(s) - 2 - 2 * s, Integer.toString(resultaat));

		}
		if (resultaat == 0) {
			int[] oplossing = new int[mogelijkheden2.size()];
			mogelijkheden3.clear();
			for (int i = 0; i < mogelijkheden2.size(); i++) {
				mogelijkheden3.add(mogelijkheden2.get(i));
			}

			for (int j = 0; j < volgorde.length; j++) {
				for (int i = 0; i < output2.size(); i++) {
					if (output2.get(i).equals(String.valueOf((char) ('A' + j)))) {
						oplossing[j] = Integer.parseInt(output3.get(i));

					}
				}
			}
			oplossingen.add(new Oplossing(oplossing));
		}


	}

}

class Oplossing {
	int[] waarde;
	int ordening;

	public Oplossing(int[] oplossing) {
		waarde = new int[oplossing.length];
		ordening = 0;
		for (int i = 0; i < oplossing.length; i++) {
			waarde[i] = oplossing[i];
			ordening = (int) (ordening + oplossing[i] * Math.pow(10, oplossing.length - i - 1));
		}
	}

	public void print() {
		for (int i = 0; i < waarde.length; i++) {
			System.out.print(waarde[i]);
		}
	}

	public int[] getWaarde() {
		return waarde;
	}

	public void setWaarde(int[] waarde) {
		this.waarde = waarde;
	}

	public int getOrdening() {
		return ordening;
	}

	public void setOrdening(int ordening) {
		this.ordening = ordening;
	}

	public int compare(Oplossing oplossing) {
		return ordening - oplossing.ordening;
	}
}
