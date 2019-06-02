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
import model.Restoran;

public class RestoranDAO {
	
	private HashMap<Integer, Restoran> restorani = new HashMap<Integer, Restoran>(); 
	private ArrayList<Restoran> lista = new ArrayList<Restoran>();
	
	public RestoranDAO()
	{
		
	}
	public RestoranDAO(String path)
	{
		load(path + "/restorani.txt");	
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
				
				Restoran r = gson.fromJson(line, Restoran.class);
				restorani.put(r.getID(), r);
				lista.add(r);
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
		
		for(Restoran r : getListOfRestaurants())
		{
			jsons.add(gson.toJson(r));
		}
		
	  File file = new File(putanja + "/restorani.txt");
	    
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


	public ArrayList<Restoran> getListOfRestaurants() {
		
		return lista;
	}
	
	public HashMap<Integer, Restoran> getRestaurants(){
		
		return restorani;
	}
	public Restoran find(int id)
	{
		if(!restorani.containsKey(id))
			return null;
		
		Restoran r = restorani.get(id);
		return r;
	}
}
