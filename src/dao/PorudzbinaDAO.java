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
import model.Porudzbina;

public class PorudzbinaDAO {

	private HashMap<String, Porudzbina> porudzbine = new HashMap<String, Porudzbina>(); 
	private ArrayList<Porudzbina> listaPorudzbina = new ArrayList<Porudzbina>();
	
	public PorudzbinaDAO(){}
	
	public PorudzbinaDAO(String path)
	{
		load(path + "/porudzbine.txt");
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
				System.out.println(line);
				Porudzbina p = gson.fromJson(line, Porudzbina.class);
				porudzbine.put(p.getId(), p);
				listaPorudzbina.add(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void save(String putanja)
	{
	
		Gson gson = new Gson();
		ArrayList<String> jsons = new ArrayList<String>();
		
		for(Porudzbina p : getListaPorudzbina())
		{
			jsons.add(gson.toJson(p));
		}
		
	  File file = new File(putanja + "/porudzbine.txt");
	    
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

	public ArrayList<Porudzbina> getListaPorudzbina() {
		return listaPorudzbina;
	}
	
	public HashMap<String, Porudzbina> getPorudzbine()
	{
		return porudzbine;
	}
		
}
