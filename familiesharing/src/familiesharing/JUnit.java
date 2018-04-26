package familiesharing;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Period;

import org.junit.Test;

public class JUnit {

	@Test
	public void testGeboorteJaar() {
		LocalDate birthDate = LocalDate.of(1998, 07, 03);

		Persoon p = new Persoon();
		p.setGeboorteJaar(birthDate);
		assertTrue(!Period.between(p.getGeboorteJaar(), LocalDate.now()).isNegative());
	}

	@Test
	public void testEmail() throws WrongEmailException {

		Persoon p = new Persoon();
		String email = "arne.de.geeter0@gmail.com";
		p.setEmail(email);
		assertSame(email, p.getEmail());
	}

	@Test
	public void testLeeftijd() {
		LocalDate birthDate = LocalDate.of(1998, 07, 03);

		Persoon p = new Persoon();
		p.setGeboorteJaar(birthDate);
		assertSame(19, p.berekenLeeftijd());
	}

	@Test
	public void testLeeftijdpositief() {
		LocalDate birthDate = LocalDate.of(1998, 07, 03);

		Persoon p = new Persoon();
		p.setGeboorteJaar(birthDate);
		assertTrue(p.berekenLeeftijd() >= 0);
	}

	@Test
	public void testSendEmail() throws WrongEmailException {

		Persoon p = new Persoon();
		String email = "arne.de.geeter0@gmail.com";
		p.setVoornaam("Arne");
		p.setNaam("De Geeter");
		p.setEmail(email);
		assertTrue(p.sendEmail("brian@Marioman.be", "Beste Brian\nAlles kits?\nCiao", "yooooooow"));
	}

}
