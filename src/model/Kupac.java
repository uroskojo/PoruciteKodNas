package model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Enumerations.Uloga;

public class Kupac extends Korisnik{

	@SerializedName("porudzbine")
	@Expose
	//private ArrayList<Porudzbina> porudzbine;
	private ArrayList<String> porudzbine;	//cuva listu id-jeva porudzbina
	@SerializedName("omiljeniRestorani")
	@Expose
	private ArrayList<Restoran> omiljeniRestorani;
	
	
	public Kupac(String korisnickoIme, String lozinka, String ime, String prezime, String kontaktTelefon, String email, String datumRegistracije) {
		super(korisnickoIme, lozinka, ime, prezime, kontaktTelefon, email, datumRegistracije);
		
		setUloga(Uloga.KUPAC);
		porudzbine = new ArrayList<String>();
		omiljeniRestorani = new ArrayList<Restoran>();
	}

	public Kupac() {}

	
	
	public ArrayList<String> getPorudzbine() {
		return porudzbine;
	}


	public void setPorudzbine(ArrayList<String> porudzbine) {
		this.porudzbine = porudzbine;
	}


	public ArrayList<Restoran> getOmiljeniRestorani() {
		return omiljeniRestorani;
	}


	public void setOmiljeniRestorani(ArrayList<Restoran> omiljeniRestorani) {
		this.omiljeniRestorani = omiljeniRestorani;
	}

	
	
}
