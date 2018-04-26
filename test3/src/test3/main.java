package test3;

import java.util.Base64;

import io.jsonwebtoken.*;

public class main {

	final static String SECRET = Base64.getEncoder().encodeToString("SecretKeyForProjectVerySecure".getBytes());

	public static void main(String[] args) {
		String s = "[0.007935, 0.022699, 0.045869, 0.022232, 0.002134, 0.001101, 7.48E-4, 5.55E-4, 4.37E-4, 3.58E-4, 3.02E-4, 2.61E-4, 2.3E-4, 2.06E-4, 1.87E-4, 1.71E-4, 1.59E-4, 1.48E-4, 1.39E-4, 1.31E-4, 1.25E-4, 1.19E-4, 1.15E-4, 1.1E-4, 1.07E-4, 1.04E-4, 1.01E-4, 9.9E-5, 9.7E-5, 9.5E-5, 9.4E-5, 9.2E-5, 9.1E-5]";
		int spatie=0;
		for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                System.out.println((s.substring(spatie + 1, i - 1)));
                System.out.println((Float.parseFloat(s.substring(spatie + 1, i - 1))));

                spatie = i;

            } else if (s.charAt(i) == ']') {
            	System.out.println((s.substring(spatie + 1, i )));
                spatie = i;
            }
        }
        
	}

	public static String getToken() {

		String compactJws = Jwts.builder().claim("id", 5).setSubject("abc")
				/*
				 * .setIssuedAt(cal.getTime()) .setExpiration(cal2.getTime())
				 */.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		checkKey(compactJws);
		return compactJws;

	}

	private static Jws<Claims> checkKey(String t) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(t);

			Jws<Claims> claims = Jwts.parser().requireSubject("admin").setSigningKey(SECRET).parseClaimsJws(t);
			return claims;
		} catch (SignatureException e) {
			System.out.println(e);
			System.out.println("SignatureException");
			return null;
		} catch (MissingClaimException e) {
			System.out.println("MissingClaimException");
			return null;
		} catch (IncorrectClaimException e) {
			System.out.println("IncorrectClaimException");
			return null;
		}

	}

}
