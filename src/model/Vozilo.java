package model;

import java.util.UUID;

import Enumerations.TipVozila;

public class Vozilo {

	private TipVozila tip;
	private String marka;
	private String model;
	private String regOznaka; 
	private int godProizv; 
	private boolean voziloUpotreba; 
	private String napomena;
	private String id;
	private boolean obrisano;
	private String dostavljac;
	
	
	
	public Vozilo(TipVozila tip, String marka, String model, int godProizv,
			String napomena, String regOznaka) {
		super();
		
		this.tip = tip;
		this.marka = marka;
		this.model = model;
		this.regOznaka = regOznaka;
		this.godProizv = godProizv;
	    this.voziloUpotreba = false;	//inicijalno nije u koriscenju
		this.napomena = napomena;
		this.id  =  UUID.randomUUID().toString();
		this.obrisano = false;
		this.dostavljac = "/";
	}
	
	public Vozilo(){
		this.dostavljac = "/";
		
	}
			
	public TipVozila getTip() {
		return tip;
	}

	public void setTip(TipVozila tip) {
		this.tip = tip;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getGodProizv() {
		return godProizv;
	}

	public void setGodProizv(int godProizv) {
		this.godProizv = godProizv;
	}

	public boolean isVoziloUpotreba() {
		return voziloUpotreba;
	}

	public void setVoziloUpotreba(boolean voziloUpotreba) {
		this.voziloUpotreba = voziloUpotreba;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}
	
	
	public String getRegOznaka()
	{
		return this.regOznaka;
	}
	public void setRegOznaka(String rn)
	{
		this.regOznaka = rn;
	}
	public String getID()
	{
		return id;
	}
	public boolean isObrisano()
	{
		return this.obrisano;
	}
	public void setObrisano()
	{
		this.obrisano = true;
	}

	public String getDostavljac() {
		return dostavljac;
	}

	public void setDostavljac(String dostavljac) {
		this.dostavljac = dostavljac;
	}
	
}
