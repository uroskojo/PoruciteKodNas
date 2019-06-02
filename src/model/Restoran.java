package model;

import java.util.ArrayList;

import Enumerations.KategorijaRestorana;
import Enumerations.TipArtikla;

public class Restoran {

	private static int count = 0;
	private KategorijaRestorana kateg;
	private String naziv;
	private String adresa;
	private ArrayList<Artikal> listaJela;
	private ArrayList<Artikal> listaPica;
	
	private int id;
	
	private boolean obrisan;
	
	public Restoran(KategorijaRestorana kateg, String naziv, String adresa) {
		super();
		this.kateg = kateg;
		this.naziv = naziv;
		this.adresa = adresa;
		this.listaJela = new ArrayList<Artikal>();
		this.listaPica = new ArrayList<Artikal>();
		this.obrisan = false;
		
		id = ++count;
	}
	
	public Restoran() {
	
	}

	
	public void deleteArticle(Artikal a)
	{
	
		if(a.getTip() == TipArtikla.HRANA)
		{
				for(int i=0; i<listaJela.size(); i++)
				{
					if(listaJela.get(i).getId().equals(a.getId()))
					{
						listaJela.remove(i);
						break;
					}
				}
			
		}else {
			for(int i=0; i<listaPica.size(); i++)
			{
				if(listaPica.get(i).getId().equals(a.getId()))
				{
					listaPica.remove(i);
					break;
				}
			}
		}
	}
	/*
	public void deleteAllArticles() //LOGICKO
	{
		for(int i=0; i < listaJela.size(); i++)
			listaJela.get(i).setObrisan();
		
		for(int i=0; i < listaPica.size(); i++)
			listaPica.get(i).setObrisan();
		
	}*/
	
	
	public KategorijaRestorana getKateg() {
		return kateg;
	}

	public void setKateg(KategorijaRestorana kateg) {
		this.kateg = kateg;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public ArrayList<Artikal> getListaJela() {
		return listaJela;
	}

	public void setListaJela(ArrayList<Artikal> listaJela) {
		this.listaJela = listaJela;
	}

	public ArrayList<Artikal> getListaPica() {
		return listaPica;
	}

	public void setListaPica(ArrayList<Artikal> listaPica) {
		this.listaPica = listaPica;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	
	public int getID()
	{
		return id;
	}
	public void setID(int id)//nije dobar pristup
	{
		this.id = id;
	}
	
	
}
