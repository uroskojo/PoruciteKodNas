package servisi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import Enumerations.StatusPorudzbine;
import Enumerations.TipArtikla;
import Enumerations.Uloga;
import dao.ArtikalDAO;
import dao.KorisnikDAO;
import dao.PorudzbinaDAO;
import dao.RestoranDAO;
import dao.VoziloDAO;
import model.Admin;
import model.Artikal;
import model.Dostavljac;
import model.Korisnik;
import model.Kupac;
import model.Porudzbina;
import model.Restoran;
import model.Stavka;
import model.Vozilo;

@Path("/servis")
public class Servis {

	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	@Path("/promenaUloge")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void promenaUloge(Korisnik k) throws IOException
	{
		KorisnikDAO korisnikDAO = (KorisnikDAO)ctx.getAttribute("korisnikDAO");
		ArrayList<Korisnik> tempList = korisnikDAO.getListOfUsers();
		Korisnik tempUser;
		String tempKey;
		int i;
			for(i=0; i < tempList.size(); i++)
			{
				if(tempList.get(i).getKorisnickoIme().equals(k.getKorisnickoIme()) &&
						tempList.get(i).getLozinka().equals(k.getLozinka()))
				{
					break;
				}
			}
			
			tempUser = tempList.get(i);
			tempKey = tempUser.getKorisnickoIme();
			
			//u slucaju promene uloge kad je dostavljacu dodeljeno vozilo
			ArrayList<Vozilo> vozila = getVehicles();
			if(tempUser.getUloga() == Uloga.DOSTAVLJAC)
			{
				for(int j=0; j < vozila.size(); j++)
				{
					if(tempUser.getKorisnickoIme().equals(vozila.get(j).getDostavljac()))
					{
						vozila.get(j).setDostavljac("/");
						vozila.get(j).setVoziloUpotreba(false);
						break;
					}
				}
			}
						
			if(k.getUloga() == Uloga.DOSTAVLJAC) {
			
				Dostavljac d = new Dostavljac(tempUser.getKorisnickoIme(), tempUser.getLozinka(), tempUser.getIme(),
						tempUser.getPrezime(), tempUser.getKontaktTelefon(), tempUser.getEmail(), tempUser.getDatumRegistracije());
				
				
				korisnikDAO.getUsers().replace(tempKey, korisnikDAO.getUsers().get(tempKey), d);
				
				korisnikDAO.getListOfUsers().remove(i);
				korisnikDAO.getListOfUsers().add(i, d);
				korisnikDAO.save(ctx.getRealPath(""));
			}
			else if(k.getUloga() == Uloga.KUPAC)
			{
				Kupac kupac = new Kupac(tempUser.getKorisnickoIme(), tempUser.getLozinka(), tempUser.getIme(),
						tempUser.getPrezime(), tempUser.getKontaktTelefon(), tempUser.getEmail(), tempUser.getDatumRegistracije());
				
				korisnikDAO.getUsers().replace(tempKey, korisnikDAO.getUsers().get(tempKey), kupac);
				
				korisnikDAO.getListOfUsers().remove(i);
				korisnikDAO.getListOfUsers().add(i, kupac);
				korisnikDAO.save(ctx.getRealPath(""));
			}else {
				
				Admin admin = new Admin(tempUser.getKorisnickoIme(), tempUser.getLozinka(), tempUser.getIme(),
						tempUser.getPrezime(), tempUser.getKontaktTelefon(), tempUser.getEmail(), tempUser.getDatumRegistracije());
				
				korisnikDAO.getUsers().replace(tempKey, korisnikDAO.getUsers().get(tempKey), admin);
				
				korisnikDAO.getListOfUsers().remove(i);
				korisnikDAO.getListOfUsers().add(i, admin);
				korisnikDAO.save(ctx.getRealPath(""));
			}
			
	}
	
	
	@Path("/getUsers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> getUsers()
	{
		KorisnikDAO korisnici = (KorisnikDAO)ctx.getAttribute("korisnikDAO");
		return korisnici.getListOfUsers();	
	}

	@Path("/getDostavljaci")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Dostavljac> getDostavljaci()
	{
	
		KorisnikDAO korisnici = (KorisnikDAO)ctx.getAttribute("korisnikDAO");
		ArrayList<Dostavljac> dostavljaci = new ArrayList<Dostavljac>();
		
		for(int i=0; i < korisnici.getListOfUsers().size(); i++)
		{
			if(korisnici.getListOfUsers().get(i).getUloga() == Uloga.DOSTAVLJAC && !((Dostavljac) korisnici.getListOfUsers().get(i)).isTrenutnoPosedujeVozilo())
				dostavljaci.add((Dostavljac) korisnici.getListOfUsers().get(i));
		}

		return dostavljaci;
	}

	
	@Path("/getDostFormaPorudzbina")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Dostavljac> getDostPorudz()
	{
		KorisnikDAO korisnici = (KorisnikDAO)ctx.getAttribute("korisnikDAO");
		ArrayList<Dostavljac> dostavljaci = new ArrayList<Dostavljac>();
		
		for(int i=0; i < korisnici.getListOfUsers().size(); i++)
		{
			if(korisnici.getListOfUsers().get(i).getUloga() == Uloga.DOSTAVLJAC && ((Dostavljac) korisnici.getListOfUsers().get(i)).isTrenutnoPosedujeVozilo())
				dostavljaci.add((Dostavljac) korisnici.getListOfUsers().get(i));
		}

		return dostavljaci;
	}
	
	@Path("/dodelaVozila")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sacuvajDodeluVozila(Dostavljac d) throws IOException	
	{
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Dostavljac dostavljac = (Dostavljac) korisnici.getUsers().get(d.getKorisnickoIme());
		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		
		String idVozila = d.getId_vozila();
		ArrayList<Vozilo> listaVozila = vozila.getListOfVehicles();
		int j;
		for(j=0; j < listaVozila.size(); j++)
		{
			if(listaVozila.get(j).getID().equals(idVozila))
				break;
		}
		listaVozila.get(j).setDostavljac(dostavljac.getKorisnickoIme());
		Vozilo tempV = listaVozila.get(j);
		tempV.setVoziloUpotreba(true);
		dostavljac.setVozilo(tempV);
		dostavljac.setTrenutnoPosedujeVozilo(true);
		vozila.save(ctx.getRealPath(""));
		ctx.setAttribute("voziloDAO", vozila);		
		korisnici.save(ctx.getRealPath(""));
		ctx.setAttribute("korisnikDAO", korisnici);
		
		
	}
	
	
	@Path("/addRestaurant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Restoran addRestaurant(Restoran r)
	{
		
	 RestoranDAO restorani = (RestoranDAO) ctx.getAttribute("restoranDAO");
     Restoran  restoran = new Restoran(r.getKateg(), r.getNaziv(), r.getAdresa());
			
	//uzmem indeks poslednjeg u listi, uzmem njegov ID, inkrementiram ga i dodelim novom restoranu
	   int idxPoslednjeg = restorani.getListOfRestaurants().size() - 1;   
	   
	   if(idxPoslednjeg>=0) {	//u suprotnom je lista prazna
		   int idLast = restorani.getListOfRestaurants().get(idxPoslednjeg).getID();
		   restoran.setID(idLast+1); //ako je prethodni imao ID=1, ID sledeceg ce biti 2
	   }
	  
		   restorani.getListOfRestaurants().add(restoran);
		   restorani.getRestaurants().put(restoran.getID(), restoran);
		   restorani.save(ctx.getRealPath(""));
		   ctx.setAttribute("restoranDAO", restorani);
	   
	   
		return restoran;
	}
	
	@Path("/getRestaurants")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restoran> getRestaurants()
	{
		
		RestoranDAO restorani = (RestoranDAO) ctx.getAttribute("restoranDAO");
		ArrayList<Restoran> listaNeobrisanih = new ArrayList<Restoran>();
		
		
		for(Restoran r:restorani.getListOfRestaurants())
		{
			if(!r.isObrisan())
				listaNeobrisanih.add(r);
		}

		return listaNeobrisanih;
	}
	
	@Path("/saveChangedRestaurant")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void save(Restoran r) {
		
		RestoranDAO restorani = (RestoranDAO)ctx.getAttribute("restoranDAO");
		
		ArrayList<Restoran> tempList = restorani.getListOfRestaurants();
		int tempKey = r.getID();
		int i;
		for(i = 0; i < tempList.size(); i++)
		{
			if(tempList.get(i).getID() == tempKey)
				break;
		}
		
		tempList.remove(i);
		tempList.add(i, r);
		restorani.getRestaurants().replace(tempKey, restorani.getRestaurants().get(tempKey), r);
		restorani.save(ctx.getRealPath(""));
		
	}
	
	@Path("/deleteRestaurant")//logicko brisanje;restoran ostaje u bazi
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void obrisiRestoran(Restoran r) {
		
		RestoranDAO restorani = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran temp = restorani.find(r.getID());
		temp.setObrisan(true);
		
		//Uzmem temp.getPica i getJela pa prolazim kroz sve artikle koji postoje,
		//kad utvrdim poklapanje brisem
		
		ArtikalDAO artikli = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		ArrayList<Artikal> listaArtikala = artikli.getListaArtikala();
		
		for(int i=0; i < listaArtikala.size(); i++)
		{
			if(listaArtikala.get(i).getTip() == TipArtikla.HRANA)
			{
				//prolaz kroz sva jela ovog restorana
				for(int j=0; j<temp.getListaJela().size(); j++)
				{
					if(listaArtikala.get(i).getId().equals(temp.getListaJela().get(j).getId()))
						listaArtikala.get(i).setObrisan(true);
					
				}
			}else//ako je pice
			{
				for(int k=0; k<temp.getListaPica().size(); k++)
				{
					if(listaArtikala.get(i).getId().equals(temp.getListaPica().get(k).getId()))
						listaArtikala.get(i).setObrisan(true);
					
				}
			}
		
		}
		artikli.save(ctx.getRealPath(""));
		ctx.setAttribute("artikalDAO", artikli);
		
		ArrayList<Restoran> tempList = restorani.getListOfRestaurants();
		int tempKey = r.getID();
		int i;
		for(i = 0; i < tempList.size(); i++)
		{
			if(tempList.get(i).getID() == tempKey)
				break;
		}
		
		tempList.remove(i);
		tempList.add(i, temp);
		restorani.getRestaurants().replace(tempKey, restorani.getRestaurants().get(tempKey), temp);
		restorani.save(ctx.getRealPath(""));
		
	}
	/////////////////// 	ARTIKLI		/////////////////
	@Path("/addArticle")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addArticle(Artikal a) {
		
	
		ArtikalDAO artikli = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		Artikal zaDodavanje;
		
		if(!artikli.findName(a.getNaziv())) {//nije u bazi; dodaj
			
			zaDodavanje = new Artikal(a.getTip(), a.getNaziv(), a.getJedinicnaCena(), a.getOpis(),a.getKolicina());
			zaDodavanje.setRestoran(a.getRestoran());
			
			RestoranDAO restorani = (RestoranDAO) ctx.getAttribute("restoranDAO");
			ArrayList<Restoran> tempList = restorani.getListOfRestaurants();
			Restoran izmenjenR = null;
			int i;
			for(i=0; i < tempList.size(); i++)
			{
				if(zaDodavanje.getRestoran().equals(tempList.get(i).getNaziv()))//trazim u koji restoran treba da ubacim artikal
				{
					izmenjenR = tempList.get(i);
					
					if(zaDodavanje.getTip() == TipArtikla.HRANA)
					{
						izmenjenR.getListaJela().add(zaDodavanje);					
						break;
					}else
					{
						izmenjenR.getListaPica().add(zaDodavanje);
						break;
					}			
				}
			}
			restorani.getRestaurants().replace(tempList.get(i).getID(), tempList.get(i), izmenjenR);
			restorani.save(ctx.getRealPath(""));
			ctx.setAttribute("restoranDAO", restorani);
			
			artikli.getListaArtikala().add(zaDodavanje);
			artikli.getArtikli().put(zaDodavanje.getId(), zaDodavanje);
			artikli.save(ctx.getRealPath(""));
			ctx.setAttribute("artikalDAO", artikli);
		}else {
			return "Vec postoji artikal s tim nazivom(Dodavanje)";
		}
		
		return "ok";
	}
	
	@Path("/narucivanje")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void naruci(Porudzbina p) throws IOException
	{
			
		Kupac kupac = (Kupac) request.getSession().getAttribute("korisnik");
		PorudzbinaDAO porudzbine = (PorudzbinaDAO) ctx.getAttribute("porudzbinaDAO");
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
	
		p.setKupac(kupac);	
		kupac.getPorudzbine().add(p.getId());
		korisnici.getUsers().put(kupac.getKorisnickoIme(), kupac);
		
		porudzbine.getPorudzbine().put(p.getId(), p);
		porudzbine.getListaPorudzbina().add(p);
		
		korisnici.save(ctx.getRealPath(""));
		ctx.setAttribute("korisnikDAO", korisnici);
		porudzbine.save(ctx.getRealPath(""));
		ctx.setAttribute("porudzbinaDAO", porudzbine);
		
	}
	
	
	@Path("/preuzimanjePorudzbine")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String preuzmi(Porudzbina p) throws IOException
	{
		Dostavljac d = (Dostavljac) request.getSession().getAttribute("korisnik");	
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		PorudzbinaDAO porudzbine = (PorudzbinaDAO) ctx.getAttribute("porudzbinaDAO");
		ArrayList<Porudzbina> tempList = porudzbine.getListaPorudzbina();
	
		if(p.getStatus().equals(StatusPorudzbine.DOSTAVLJENO))
			return "Vec je isporuceno";
		else if(!d.getVozilo().getDostavljac().equals(d.getKorisnickoIme()))		
			return "Nemas vozilo u posedu";
		else if(!d.getPorudzbineDostavljacu().isEmpty())
			return "Vec si zauzet dostavom";
		
		d.getPorudzbineDostavljacu().add(p.getId());
		p.setDostavljac(d.getKorisnickoIme());
		p.setStatus(StatusPorudzbine.DOSTAVA_U_TOKU);
		p.setImaDostavljaca(true);
				
		int i;
		for(i=0; i < tempList.size(); i++)
		{
			if(tempList.get(i).getId().equals(p.getId()))
				break;
		}
		
		tempList.remove(i);
		tempList.add(i,p);
		
		 korisnici.save(ctx.getRealPath(""));
		 ctx.setAttribute("korisnikDAO", korisnici);
		 porudzbine.save(ctx.getRealPath(""));
		 ctx.setAttribute("porudzbinaDAO", porudzbine);
		 
		 return "Uspesno si preuzeo dostavu za kupca " + p.getKupac().getIme();
	}
	

	@Path("/dostaviPorudzbinu")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String dostavi(Porudzbina p, @Context HttpServletRequest request)
	{
		Dostavljac d = (Dostavljac) request.getSession().getAttribute("korisnik");
		PorudzbinaDAO porudzbine = (PorudzbinaDAO) ctx.getAttribute("porudzbinaDAO");
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		
		if(p.getStatus().equals(StatusPorudzbine.DOSTAVLJENO))
			return "Vec je isporuceno";
		else if(p.getStatus().equals(StatusPorudzbine.PORUCENO)) 
			return "Nisi preuzeo porudzbinu";
		else if(!d.isTrenutnoPosedujeVozilo())
			return "Nemas vozilo u posedu";
		
			
		ArrayList<Vozilo> tempVozila =  vozila.getListOfVehicles();
		ArrayList<Porudzbina> tempPorudzbine =  porudzbine.getListaPorudzbina();
		
		int i;
		for(i=0; i < tempVozila.size(); i++)
		{
			if(tempVozila.get(i).getID().equals(d.getVozilo().getID()))
				break;
		}
		tempVozila.get(i).setDostavljac("/");
		tempVozila.get(i).setVoziloUpotreba(false);
		
		for(i=0; i < tempPorudzbine.size(); i++)
		{
			if(tempPorudzbine.get(i).getId().equals(p.getId()))
				break;
		}
		tempPorudzbine.get(i).setStatus(StatusPorudzbine.DOSTAVLJENO);
		tempPorudzbine.get(i).setImaDostavljaca(false);
		p.setStatus(StatusPorudzbine.DOSTAVLJENO);
		
		d.getPorudzbineDostavljacu().remove(p.getId());
		d.setVozilo(new Vozilo());
		d.setTrenutnoPosedujeVozilo(false);
		korisnici.getUsers().replace(d.getKorisnickoIme(), d);
		
		korisnici.save(ctx.getRealPath(""));
		 ctx.setAttribute("korisnikDAO", korisnici);
		 porudzbine.save(ctx.getRealPath(""));
		 ctx.setAttribute("porudzbinaDAO", porudzbine);
		 vozila.save(ctx.getRealPath(""));
		 ctx.setAttribute("voziloDAO", vozila); 
		 return "Dostava je uspesno izvrsena";
	}
	
	@Path("/getArticles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Artikal> getArticles()
	{
		ArtikalDAO artikli = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		ArrayList<Artikal> listaNeobrisanih = new ArrayList<Artikal>();
		
		for(Artikal a:artikli.getListaArtikala())
		{
			if(!a.isObrisan())
				listaNeobrisanih.add(a);
		}

		return listaNeobrisanih;
		

}
	
	@Path("/popular")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Artikal> getPopular(){
		
		HashMap<Artikal, Integer> popularno = new HashMap<>();
		PorudzbinaDAO porudzbine = (PorudzbinaDAO) ctx.getAttribute("porudzbinaDAO");
		ArtikalDAO artikli = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		ArrayList<Porudzbina> listaPorudzbina = porudzbine.getListaPorudzbina();
		Artikal tempArtikal;
		int i, j;

		for(i = 0; i < listaPorudzbina.size(); i++)	// uzme porudzbinu
		{
			for(j = 0; j < listaPorudzbina.get(i).getStavke().size(); j++) // prolazi kroz njene stavke
			{
				tempArtikal = artikli.getArtikli().get(listaPorudzbina.get(i).getStavke().get(j).getId());
				
				if(tempArtikal == null)
					continue;
				if(popularno.containsKey(tempArtikal))
				{
					int brPojavljivanja = popularno.get(tempArtikal)+1;
					popularno.put(tempArtikal, brPojavljivanja);
				}else
					popularno.put(tempArtikal, 1);
			}
		}
				
		List<Artikal> popArtikli = new ArrayList<Artikal>(popularno.keySet());
		List<Integer> brPonavljanja = new ArrayList<Integer>(popularno.values());
		
		int tmpBr;
		
		for(i = 0; i < brPonavljanja.size(); i++)
		{
			for(j = 1; j < (brPonavljanja.size() - i); j++)
			{
				if(brPonavljanja.get(j-1) < brPonavljanja.get(j))
				{
					tmpBr = brPonavljanja.get(j-1);
					brPonavljanja.set(j-1, brPonavljanja.get(j));
					brPonavljanja.set(j, tmpBr);
					
					tempArtikal = popArtikli.get(j-1);
					popArtikli.set(j-1, popArtikli.get(j));
					popArtikli.set(j, tempArtikal);
				}
			}
		}
		if(popArtikli.size() < 10)
			return popArtikli;
		else
			return popArtikli.subList(0, 10);
	}
	
	@Path("/getArticlesOfRestaurants")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Artikal> getArticlesOfRestaurant(Restoran r)
	{
		
		RestoranDAO restorani = (RestoranDAO) ctx.getAttribute("restoranDAO");
		Restoran temp = restorani.getRestaurants().get(r.getID());
		ArrayList<Artikal> listaNeobrisanih = new ArrayList<Artikal>();
		
		for(int j=0; j < temp.getListaJela().size(); j++)
		{
			if(!temp.getListaJela().get(j).isObrisan())
				listaNeobrisanih.add(temp.getListaJela().get(j));
		}
		
		for(int k=0; k < temp.getListaPica().size(); k++)
		{
			if(!temp.getListaPica().get(k).isObrisan())
				listaNeobrisanih.add(temp.getListaPica().get(k));
		}
		
		return listaNeobrisanih;
	}
	
	@Path("/saveChangedArticle")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveChangedArticle(Artikal a)
	{
	
		ArtikalDAO artikli = (ArtikalDAO) ctx.getAttribute("artikalDAO");
				
		ArrayList<Artikal> tempList = artikli.getListaArtikala();
		
		String tempKey = a.getId();
		int i;
		for(i = 0; i < tempList.size(); i++)
		{
			if(tempList.get(i).getId().equals(tempKey))
				break;
		}
		
			tempList.remove(i);
			tempList.add(i, a);
			artikli.getArtikli().replace(tempKey, artikli.getArtikli().get(tempKey), a);
			artikli.save(ctx.getRealPath(""));
				
		return "Sacuvano";
	}
	
	@Path("/deleteArticle")//logicko brisanje;artikal ostaje u bazi
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteArticle(Artikal a)
	{

		ArtikalDAO artikli = (ArtikalDAO) ctx.getAttribute("artikalDAO");
		Artikal temp = artikli.find(a.getId());
		temp.setObrisan(true);
		ArrayList<Artikal> tempList = artikli.getListaArtikala();
		String tempKey = a.getId();
		
		
		String nazivRestorana = a.getRestoran(); 
		RestoranDAO restorani = (RestoranDAO) ctx.getAttribute("restoranDAO");
		ArrayList<Restoran> listaRestorana = restorani.getListOfRestaurants();
		
		
		for(int i=0; i < listaRestorana.size(); i++)
		{
			if(listaRestorana.get(i).getNaziv().equals(nazivRestorana)) //kad nadje taj restoran, obrisi artikal iz njega
			{
				Restoran tempRest = listaRestorana.get(i);
				listaRestorana.get(i).deleteArticle(a);
				restorani.getRestaurants().replace(tempRest.getID(), tempRest, listaRestorana.get(i));
				
				restorani.save(ctx.getRealPath(""));
				ctx.setAttribute("restoranDAO", restorani);
				break;
			}
		}
		
		int i;
		for(i = 0; i < tempList.size(); i++)
		{
			if(tempList.get(i).getId().equals(tempKey))
				break;
		}
		
		tempList.remove(i);
		tempList.add(i, temp);
		artikli.getArtikli().replace(tempKey, artikli.getArtikli().get(tempKey), temp);
		artikli.save(ctx.getRealPath(""));
	}
	
		
	
/////////////////// 	VOZILA	/////////////////
	
	
	@Path("/addVehicle")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addVehicle(Vozilo v)
	{
		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		Vozilo zaDodavanje;
		
		if(!vozila.findRegNum(v.getRegOznaka())) {//nije u bazi; dodaj
			
			zaDodavanje = new Vozilo(v.getTip(), v.getMarka(), v.getModel(), v.getGodProizv(), v.getNapomena(),
					v.getRegOznaka());
					
			vozila.getListOfVehicles().add(zaDodavanje);
			vozila.getVozila().put(zaDodavanje.getID(), zaDodavanje);
			vozila.save(ctx.getRealPath(""));
			ctx.setAttribute("voziloDAO", vozila);
		}else {
			return "Vec postoji vozilo s tom reg. oznakom";
		}
		
		return "ok, dodato je vozilo";
	}

	@Path("/izborVozilaDostavljac")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String izborVozila(Vozilo v)
	{
		Dostavljac d =  (Dostavljac) request.getSession().getAttribute("korisnik");
		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Vozilo tempV = vozila.getVozila().get(v.getID());
		
		if(d.isTrenutnoPosedujeVozilo())
			return "Vec imate vozilo u posedu!";
		else if(tempV.isVoziloUpotreba())
			return "Vozilo je u upotrebi!";
		
		vozila.getVozila().get(v.getID()).setDostavljac(d.getKorisnickoIme());
		vozila.getVozila().get(v.getID()).setVoziloUpotreba(true);
		
		d.setVozilo(vozila.getVozila().get(v.getID()));
		d.setTrenutnoPosedujeVozilo(true);
		korisnici.getUsers().put(d.getKorisnickoIme(), d);
		
		vozila.save(ctx.getRealPath(""));
		ctx.setAttribute("voziloDAO", vozila);
		korisnici.save(ctx.getRealPath(""));
		ctx.setAttribute("korisnikDAO", korisnici);
		return "Uspesno si preuzeo vozilo " + vozila.getVozila().get(v.getID()).getMarka() + " " + 
		vozila.getVozila().get(v.getID()).getModel() + ".";
	}
	
	@Path("/getVehicles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vozilo> getVehicles()
	{
		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		ArrayList<Vozilo> listaNeobrisanih = new ArrayList<Vozilo>();
		
		for(Vozilo v : vozila.getListOfVehicles())
		{
			if(!v.isObrisano())
				listaNeobrisanih.add(v);
		}

		return listaNeobrisanih;

	}
			
	@Path("/saveChangedVehicle")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveChangedVehicle(Vozilo v) {
		
		VoziloDAO vozila = (VoziloDAO)ctx.getAttribute("voziloDAO");	
		ArrayList<Vozilo> tempList = vozila.getListOfVehicles();
		String tempKey = v.getID();

		int i;
		for(i = 0; i < tempList.size(); i++)
		{
			if(tempList.get(i).getID().equals(tempKey))
				break;
		}
		
			tempList.remove(i);
			tempList.add(i, v);
			vozila.getVozila().replace(tempKey, vozila.getVozila().get(tempKey), v);
			vozila.save(ctx.getRealPath(""));
			
		return "uspesno izmenjeno vozilo";
	}
	

	@Path("/uklanjanjeDostavljaca")	//dostavljac dodeljen vozilu
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Vozilo ukloniDostavljaca(Vozilo v) throws IOException
	{
		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		Vozilo tempVozilo = vozila.findVehicle(v.getID());
		tempVozilo.setDostavljac("/");
		tempVozilo.setVoziloUpotreba(false);
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		Dostavljac tempKorisnik = (Dostavljac) korisnici.getUsers().get(v.getDostavljac());
		tempKorisnik.setVozilo(new Vozilo());
		tempKorisnik.setTrenutnoPosedujeVozilo(false);
		vozila.save(ctx.getRealPath(""));
		ctx.setAttribute("voziloDAO", vozila);
		korisnici.save(ctx.getRealPath(""));
		ctx.setAttribute("korisnikDAO", korisnici);
		
		return tempVozilo;
	}
		
	@Path("/deleteVehicle")//logicko brisanje;artikal ostaje u bazi
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteVehicle(Vozilo v) throws IOException
	{

		VoziloDAO vozila = (VoziloDAO) ctx.getAttribute("voziloDAO");
		Vozilo temp = vozila.findVehicle(v.getID());
				
		temp.setObrisano();

		String dostavljacIme = temp.getDostavljac();
		temp.setDostavljac("/");
		
		
		vozila.save(ctx.getRealPath(""));
		
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		ArrayList<Korisnik> listaKorisnika = korisnici.getListOfUsers();
		PorudzbinaDAO porudzbine = (PorudzbinaDAO) ctx.getAttribute("porudzbinaDAO");
		ArrayList<Porudzbina> listaPorudzbi = porudzbine.getListaPorudzbina();
		int j;
		for(j=0; j < listaKorisnika.size(); j++)
		{
			if(listaKorisnika.get(j).getUloga() == Uloga.DOSTAVLJAC && listaKorisnika.get(j).getKorisnickoIme().equals(dostavljacIme))
			{
				((Dostavljac) listaKorisnika.get(j)).setVozilo(new Vozilo());
				((Dostavljac) listaKorisnika.get(j)).getVozilo().setDostavljac("/");//razduzuje vozilo
				((Dostavljac) listaKorisnika.get(j)).setTrenutnoPosedujeVozilo(false);
				((Dostavljac) listaKorisnika.get(j)).setPorudzbineDostavljacu(new ArrayList<String>());
				break;
			}
		}
		
		for(int k=0; k < listaPorudzbi.size(); k++)
		{
			if(listaPorudzbi.get(k).getDostavljac().equals(dostavljacIme))
			{	listaPorudzbi.get(k).setDostavljac("");
				listaPorudzbi.get(k).setStatus(StatusPorudzbine.OTKAZANO);
				break;
			}
		}

		korisnici.save(ctx.getRealPath(""));
		ctx.setAttribute("korisnikDAO", korisnici);
		porudzbine.save(ctx.getRealPath(""));
		ctx.setAttribute("porudzbinaDAO", porudzbine);
	}
		
	@Path("/getPorudzbine")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Porudzbina> getPorudzbine()
	{
		PorudzbinaDAO porudzbine = (PorudzbinaDAO) ctx.getAttribute("porudzbinaDAO");
		
		return porudzbine.getListaPorudzbina();
	}
	
	@Path("/getKupci")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Kupac> getKupci()
	{
		KorisnikDAO korisnici = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		ArrayList<Korisnik> listaKorisnika = korisnici.getListOfUsers();
		ArrayList<Kupac> kupci = new ArrayList<Kupac>();
		
		for(int i=0; i < listaKorisnika.size(); i++)
		{
			if(listaKorisnika.get(i).getUloga() == Uloga.KUPAC)
				kupci.add((Kupac) listaKorisnika.get(i));
		}
		return kupci;
	}
	
	@Path("/addPorudzbinaAdmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addPorudzbina(Porudzbina p)
	{
		return "";
	}
		
	@Path("/getTypeOfUser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTypeOfUser(@Context HttpServletRequest request)
	{
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("korisnik");	
		
		if(korisnik == null)
		{
			return "gost";
		
		}else if(korisnik.getUloga() == Uloga.ADMIN) {
			return "admin";
		}else if(korisnik.getUloga() == Uloga.DOSTAVLJAC) {
			return "dostavljac";
		}else {
			return "kupac";
		}
	}
	
	@PostConstruct
	public void init()
	{
		
		if(ctx.getAttribute("restoranDAO") == null)
		{
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("restoranDAO", new RestoranDAO(contextPath));
		}
		
		if(ctx.getAttribute("artikalDAO") == null)
		{
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("artikalDAO", new ArtikalDAO(contextPath));
		}
		
		if(ctx.getAttribute("voziloDAO") == null)
		{
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("voziloDAO", new VoziloDAO(contextPath));
		}
		
		if(ctx.getAttribute("korisnikDAO") == null)
		{
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
		
		if(ctx.getAttribute("porudzbinaDAO") == null)
		{
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("porudzbinaDAO", new PorudzbinaDAO(contextPath));
		}
	}
	
	
}
