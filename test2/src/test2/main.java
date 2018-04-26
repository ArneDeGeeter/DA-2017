package test2;

import java.awt.geom.GeneralPath;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws NoSuchAlgorithmException {
			test.test2("foo");
			}



}
class test{
	String test;
	public test(){
		
	}
	public test(String a){
		test=a;
	}
	public static test test2(String a){
		return new test(a);
	}
}

/*	public static String generateToken() {
		StringBuilder s = new StringBuilder();

		for (int j = 0; j < 3; j++) {
			int i = (int) (Math.random() * 36);
			if (i < 10) {
				s.append(String.valueOf(i));

			} else {
				s.append((char) (i - 10 + 65));
			}
		}
		s.append("-");
		for (int j = 0; j < 3; j++) {
			int i = (int) (Math.random() * 36);
			if (i < 10) {
				s.append(String.valueOf(i));

			} else {
				s.append((char) (i - 10 + 65));
			}
		}

		return s.toString();
	}*/