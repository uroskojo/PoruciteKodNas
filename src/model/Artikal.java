package model;

import java.util.UUID;



import Enumerations.TipArtikla;

public class Artikal{

	private TipArtikla tip; 
	private String naziv;
	private Double jedinicnaCena;
	private String opis;
	private int kolicina;//grami za jelo, mililitri za pice
    private String id;
	private boolean obrisan;
	
	private String restoran;	//naziv restorana kome pripada
    
	public Artikal(TipArtikla tip, String naziv, Double jedinicnaCena, String opis, int kolicina) {
		super();
		
		this.tip = tip;
		this.naziv = naziv;
		this.jedinicnaCena = jedinicnaCena;
		this.opis = opis;
		this.kolicina = kolicina;
		this.id  =  UUID.randomUUID().toString();
		obrisan = false;
	}
	public Artikal() {}
	public TipArtikla getTip() {
		return tip;
	}
	public void setTip(TipArtikla tip) {
		this.tip = tip;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Double getJedinicnaCena() {
		return jedinicnaCena;
	}
	public void setJedinicnaCena(Double jedinicnaCena) {
		this.jedinicnaCena = jedinicnaCena;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	public String getRestoran() {
		return restoran;
	}
	public void setRestoran(String restoran) {
		this.restoran = restoran;
	}
	
}
