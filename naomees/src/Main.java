import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		int gevallen = Integer.parseInt(s);
		for (int i = 0; i < gevallen; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < 5; j++) {
				if (naomees(sc.nextLine())) {
					System.out.print(" naomees");
				} else {
					System.out.print(" onzin");
				}
			}
			System.out.println();
		}
	}

	private static boolean naomees(String naomees) {
		if (naomees.length() == 2) {
			if (naomees.equals("ba") || naomees.equals("di") || naomees.equals("du")) {
				return true;
			} else {
				return false;
			}
		} else if (naomees.length() == 4 && naomees.substring(0, 2).equals(naomees.substring(2))) {
			return true;
		} else {
			if (naomees.substring(0, 2).equals(naomees.substring(naomees.length() - 2))) {
				return naomees(naomees.substring(2, naomees.length() - 2));
			}
		}
		return false;
	}
}
