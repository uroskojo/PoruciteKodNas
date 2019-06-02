package servisi;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import Enumerations.Uloga;
import dao.KorisnikDAO;
import model.Admin;
import model.Korisnik;
import model.Kupac;


@Path("/registrovanje")
public class RegistracijaServis {

	@Context
	ServletContext ctx;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String registruj(Korisnik korisnik) throws IOException
	{
		KorisnikDAO kDAO = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		Kupac kupac = new Kupac(korisnik.getKorisnickoIme(), korisnik.getLozinka(),
				korisnik.getIme(), korisnik.getPrezime(), korisnik.getKontaktTelefon(),
				korisnik.getEmail(), korisnik.getDatumRegistracije()); 
		
		
		if(kDAO == null)
		{
			kDAO = new KorisnikDAO(ctx.getRealPath(""));
			ctx.setAttribute("korisnikDAO", kDAO);
		}
		
		if(kDAO.find(korisnik.getKorisnickoIme(), korisnik.getLozinka()) == null)
		{	
			kDAO.getUsers().put(kupac.getKorisnickoIme(), kupac);
			kDAO.getListOfUsers().add(kupac);
			kDAO.save(ctx.getRealPath(""));
			
			ctx.setAttribute("korisnikDAO", kDAO);
		}
		else {

			return "Vec postoji korisnik s unetim korisnickim imenom.";//valja ispraviti ovo;imejl treba da bude jedinstven
		}
		
		return "Uspesno ste se registrovali";
	}

	
	
	
	
}
