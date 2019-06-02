package model;

import java.util.ArrayList;

import Enumerations.Uloga;

public class Dostavljac extends Korisnik{

	
	private Vozilo vozilo;
	private String id_vozila;
	private ArrayList<String> porudzbineDostavljacu;	//id-ijebi
	private boolean trenutnoPosedujeVozilo;
		
	
	public Dostavljac(String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTelefon,
			String email, String datumRegistracije) {
		super(korisnickoIme, lozinka, ime, prezime, kontaktTelefon, email, datumRegistracije);
		
		setUloga(Uloga.DOSTAVLJAC);
		vozilo = new Vozilo();
		trenutnoPosedujeVozilo = false;
		porudzbineDostavljacu  = new ArrayList<String>();
	}
	
	public Dostavljac() {}
	
	public Vozilo getVozilo() {
		return vozilo;
	}

	public void setVozilo(Vozilo vozilo) {
		this.vozilo = vozilo;
	}

	public ArrayList<String> getPorudzbineDostavljacu() {
		return porudzbineDostavljacu;
	}

	public void setPorudzbineDostavljacu(ArrayList<String> porudzbineDostavljacu) {
		this.porudzbineDostavljacu = porudzbineDostavljacu;
	}

	public boolean isTrenutnoPosedujeVozilo() {
		return trenutnoPosedujeVozilo;
	}

	public void setTrenutnoPosedujeVozilo(boolean trenutnoPosedujeVozilo) {
		this.trenutnoPosedujeVozilo = trenutnoPosedujeVozilo;
	}

	public String getId_vozila() {
		return id_vozila;
	}

	public void setId_vozila(String id_vozila) {
		this.id_vozila = id_vozila;
	}

}
