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

import model.Artikal;


public class ArtikalDAO {
	private HashMap<String, Artikal> artikli = new HashMap<String, Artikal>(); 
	private ArrayList<Artikal> lista = new ArrayList<Artikal>();
	
	public ArtikalDAO()
	{
		
	}
	public ArtikalDAO(String path)
	{
		load(path + "/artikli.txt");	
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
				
				Artikal a = gson.fromJson(line, Artikal.class);
				artikli.put(a.getId(), a);
				lista.add(a);
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
		
		for(Artikal a : getListaArtikala())
		{
			jsons.add(gson.toJson(a));
		}
		
	  File file = new File(putanja + "/artikli.txt");
	    
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
	
	public Artikal find(String id) {
		
		if(!artikli.containsKey(id)) {
			return null;
		}
		Artikal a = artikli.get(id);
		
		return a;
		
	}
	public boolean findName(String name) {
		
		for(int i=0; i < lista.size(); i++)
		{
			if(lista.get(i).getNaziv().equals(name))
				return true;
		}
		
		return false;
	}
	
	public HashMap<String, Artikal> getArtikli() {
		return artikli;
	}

	public ArrayList<Artikal> getListaArtikala() {
		return lista;
	}



}
