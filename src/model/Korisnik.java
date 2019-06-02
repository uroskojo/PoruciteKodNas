package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Enumerations.Uloga;

public class Korisnik {
	
	@SerializedName("uloga")
	@Expose
	private Uloga uloga;
	@SerializedName("korisnickoIme")
	@Expose
	private String korisnickoIme;
	@SerializedName("lozinka")
	@Expose
	private String lozinka;
	@SerializedName("ime")
	@Expose
	private String ime;
	@SerializedName("prezime")
	@Expose
	private String prezime;
	@SerializedName("kontaktTelefon")
	@Expose
	private String kontaktTelefon;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("datumRegistracije")
	@Expose
	private String datumRegistracije;


	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTelefon, String email, String datum) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.kontaktTelefon = kontaktTelefon;
		this.email = email;
		this.datumRegistracije = datum;
		//setUloga(Uloga.KUPAC);
		
	}
	public Korisnik() {}
	public Uloga getUloga() {
		return uloga;
	}
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getKontaktTelefon() {
		return kontaktTelefon;
	}
	public void setKontaktTelefon(String kontaktTelefon) {
		this.kontaktTelefon = kontaktTelefon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDatumRegistracije() {
		return datumRegistracije;
	}
	public void setDatumRegistracije(String datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}
	


}
