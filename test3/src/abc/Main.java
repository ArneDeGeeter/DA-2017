package abc;

import java.util.Stack;

public class Main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String s = "ABCDEF0";
		String[] a = split(s);
		for(int i=0;i<s.length();i++){
			System.out.println(a[i]);
		}
	}

	public static String[] split(String s) {
		String[] a = new String[s.length()];
		for (int i = 0; i < s.length(); i++) {
			a[i] = s.substring(i, i + 1);
		}
		return a;
	}
}
