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
import model.Vozilo;

public class VoziloDAO {

	private HashMap<String, Vozilo> vozila = new HashMap<String, Vozilo>(); 
	private ArrayList<Vozilo> lista = new ArrayList<Vozilo>();
	
	public VoziloDAO(){}
	
	public VoziloDAO(String path)
	{
		load(path + "/vozila.txt");	
	}
	
	public void load(String contextPath)
	{
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
		
		try {
			while((line = bfr.readLine()) != null) {
				
				Vozilo v = gson.fromJson(line, Vozilo.class);
				vozila.put(v.getID(), v);
				lista.add(v);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void save(String putanja)
	{
		//za serijalizaciju
		Gson gson = new Gson();
		//String json = gson.toJson(korisnik);
		ArrayList<String> jsons = new ArrayList<String>();
		
		for(Vozilo v : getListOfVehicles())
		{
			jsons.add(gson.toJson(v));
		}
		
	  File file = new File(putanja + "/vozila.txt");
	    
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

	public boolean findRegNum(String reg) {
		
		for(int i=0; i < lista.size(); i++)
		{
			if(lista.get(i).getRegOznaka().equals(reg))
				return true;
		}
		return false;
	}
	
	public Vozilo findVehicle(String id)
	{
		if(!vozila.containsKey(id))
			return null;
		
		Vozilo v = vozila.get(id);
		return v;
	}
	
	public HashMap<String, Vozilo> getVozila()
	{
		return vozila;
	}
	public ArrayList<Vozilo> getListOfVehicles()
	{
		return lista;
	}
}
