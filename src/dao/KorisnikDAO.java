package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import Enumerations.Uloga;
import model.Admin;
import model.Dostavljac;
import model.Korisnik;
import model.Kupac;


public class KorisnikDAO {

	//@Expose
	private HashMap<String, Korisnik> users = new HashMap<String, Korisnik>();
	@Expose
	private ArrayList<Korisnik> lista = new ArrayList<Korisnik>();
	
	public KorisnikDAO() {
		
	}
	
	public KorisnikDAO(String path) {
		loadUsers(path + "/registrovani.txt");
	}
	public Korisnik find(String username, String password)
	{
		
		if (!users.containsKey(username)) {
			return null;
		}
		Korisnik user = users.get(username);
		if (!user.getLozinka().equals(password)) {
			return null;
		}
		return user;
		
	}

	/**
	 * 
	 * @param contextPath-putanja do aplikacije u tomcatu
	 * @throws IOException 
	 */
	
	private void loadUsers(String contextPath)
	{
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Gson gson = new Gson();
		BufferedReader bfr = null;
		String line = "";
		File file = new File(contextPath);
		if (!file.exists()){
            try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bfr = new BufferedReader(new FileReader(contextPath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//Sone://lista = gson.fromJson(jsonString, new TypeToken<ArrayList<KorisnikAdministrator>>(){}.getType());
		
		try {
			while((line = bfr.readLine()) != null) {
				
				 Korisnik k = gson.fromJson(line, Korisnik.class);
				 if(k.getUloga() == Uloga.ADMIN) {
					 
					 Admin admin = gson.fromJson(line, Admin.class);
					 users.put(admin.getKorisnickoIme(), admin);			
					 lista.add(admin);
					 
				 }else if(k.getUloga() == Uloga.DOSTAVLJAC)
				 {
					 Dostavljac dostavljac = gson.fromJson(line, Dostavljac.class);
					 users.put(dostavljac.getKorisnickoIme(), dostavljac);			
					 lista.add(dostavljac);
				 
				 }else {
					 Kupac kupac = gson.fromJson(line, Kupac.class);
					 users.put(kupac.getKorisnickoIme(), kupac);			
					 lista.add(kupac);
				 }

				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String putanja)
	{
		//za serijalizaciju
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Gson gson = new Gson();
		
		ArrayList<String> jsons = new ArrayList<String>();
		
		for(Korisnik k : getListOfUsers())
		{
			jsons.add(gson.toJson(k));
		}
		
	  File file = new File(putanja + "/registrovani.txt");
	    
	    try{
	        if (!file.exists()){
	            file.createNewFile();
	        }

	        PrintWriter writer = new PrintWriter(file);
	        for(String json : jsons)
	        {
	         writer.println(json);
	        }
	    	writer.flush();
 		    writer.close();	
		    
	    }catch(IOException e){
	       e.printStackTrace();
	    }
	}
		

	
	
	
	
	public HashMap<String, Korisnik> getUsers() {
		return users;
	}
	
	public ArrayList<Korisnik> getListOfUsers() {
		
		return lista;
	}

	public void setUsers(HashMap<String, Korisnik> users) {
		this.users = users;
	}

	public void setLista(ArrayList<Korisnik> lista) {
		this.lista = lista;
	}
	
	
}





