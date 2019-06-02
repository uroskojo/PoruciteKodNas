package servisi;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import Enumerations.Uloga;
import dao.KorisnikDAO;
import model.Korisnik;

@Path("/logovanje")
public class LogovanjeServis {

	@Context
	ServletContext ctx;
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(Korisnik korisnik, @Context HttpServletRequest request)
	{
		
		KorisnikDAO korisnikDAO = (KorisnikDAO)ctx.getAttribute("korisnikDAO");
		Korisnik ulogovanKorisnik = korisnikDAO.find(korisnik.getKorisnickoIme(), korisnik.getLozinka());
		
		if(ulogovanKorisnik == null)
		{
			return "Pogresno korisnicko ime i/ili lozinka";
		}
		request.getSession().setAttribute("korisnik", ulogovanKorisnik);
					
		return "";
		
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		
		return "";
	}
	
	@PostConstruct
	public void init()
	{
		if(ctx.getAttribute("korisnikDAO") == null)
		{
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
	}
	
}
