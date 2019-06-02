package model;

import Enumerations.Uloga;

public class Admin extends Korisnik{

	public Admin(String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTelefon, String email,
			String datumRegistracije) {
		super(korisnickoIme, lozinka, ime, prezime, kontaktTelefon, email, datumRegistracije);
		
		setUloga(Uloga.ADMIN);
	}

	
}
