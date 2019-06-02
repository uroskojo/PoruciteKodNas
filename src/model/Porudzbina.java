package model;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Enumerations.StatusPorudzbine;

public class Porudzbina {
	

	private ArrayList<Stavka> stavke;
	private String datumVreme;
	private Kupac kupac;
	private String dostavljac;
	private StatusPorudzbine status;
	private String napomena;
	private ArrayList<String> listaKomentara;
	private boolean obrisana;
	private String id;
	private boolean imaDostavljaca;

	public Porudzbina() {
		
		this.id  =  UUID.randomUUID().toString();
		this.status = StatusPorudzbine.PORUCENO;
		this.stavke = new ArrayList<Stavka>();
		this.imaDostavljaca = false;
	}
	public ArrayList<Stavka> getStavke() {
		return stavke;
	}
	public void setStavke(ArrayList<Stavka> stavke) {
		this.stavke = stavke;
	}

	public String getDatumVreme() {
		return datumVreme;
	}

	public void setDatumVreme(String datumVreme) {
		this.datumVreme = datumVreme;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public String getDostavljac() {
		return dostavljac;
	}

	public void setDostavljac(String dostavljac) {
		this.dostavljac = dostavljac;
	}

	public StatusPorudzbine getStatus() {
		return status;
	}

	public void setStatus(StatusPorudzbine status) {
		this.status = status;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}


	public ArrayList<String> getListaKomentara() {
		return listaKomentara;
	}

	public void setListaKomentara(ArrayList<String> listaKomentara) {
		this.listaKomentara = listaKomentara;
	}

	public boolean isObrisana() {
		return obrisana;
	}

	public void setObrisana(boolean obrisana) {
		this.obrisana = obrisana;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isImaDostavljaca() {
		return imaDostavljaca;
	}



	public void setImaDostavljaca(boolean imaDostavljaca) {
		this.imaDostavljaca = imaDostavljaca;
	}
	
	

}
