package familiesharing;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Persoon {


    private String naam;
    private String voornaam;
    private int toegang;
    private LocalDate geboorteJaar;
    private String wachtwoord;
    private String email;
    private boolean geslachtMan;//True is man, false is vrouw

    public Persoon() {

    }

    public Persoon(String naam, String voornaam, int toegang, LocalDate geboorteJaar, String wachtwoord, String email, boolean geslachtMan) throws WrongEmailException {
        this.naam = naam;
        this.voornaam = voornaam;
        this.toegang = toegang;
        this.geboorteJaar = geboorteJaar;
        this.wachtwoord = wachtwoord;
        if (isValidEmailAddress(email)) {
            this.email = email;
        } else {
            throw new WrongEmailException((email + "is not a valid email"));
        }
        this.geslachtMan = geslachtMan;
    }

    public int berekenLeeftijd() {
        return Period.between(geboorteJaar, LocalDate.now()).getYears();
    }

    public boolean sendEmail(String emailOtherPerson, String message, String subject) throws WrongEmailException {
        if (isValidEmailAddress(emailOtherPerson)) {
            System.out.println("From: " + naam + " " + voornaam);
            System.out.println("To: " + emailOtherPerson.toLowerCase());
            System.out.println("Subject: " + subject);
            System.out.println();
            System.out.println(message);
            return true;
        } else {
            throw new WrongEmailException((emailOtherPerson + "is not a valid email"));
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    //Getter and setters
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public int getToegang() {
        return toegang;
    }

    public void setToegang(int toegang) {
        this.toegang = toegang;
    }

    public LocalDate getGeboorteJaar() {
        return geboorteJaar;
    }

    public void setGeboorteJaar(LocalDate geboorteJaar) {
        this.geboorteJaar = geboorteJaar;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws WrongEmailException {
        if (isValidEmailAddress(email)) {
            this.email = email;
        } else {
            throw new WrongEmailException((email + "is not a valid email"));
        }
    }

    public boolean isGeslachtMan() {
        return geslachtMan;
    }

    public void setGeslachtMan(boolean geslachtMan) {
        this.geslachtMan = geslachtMan;
    }


}
class WrongEmailException extends Exception {
    public WrongEmailException(String e) {
        super(e);

    }
}

