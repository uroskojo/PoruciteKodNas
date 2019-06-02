package model;

import java.util.UUID;

public class Stavka {

	private String artikal; //id artikla
	private int kolicina;
	private double cena;
	private String id;
	public Stavka()
	{		
		this.setId(UUID.randomUUID().toString());
		
	}

	public String getArtikal() {
		return artikal;
	}

	public void setArtikal(String artikal) {
		this.artikal = artikal;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
