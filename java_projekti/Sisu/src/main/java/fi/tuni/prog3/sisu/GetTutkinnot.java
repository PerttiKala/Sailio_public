package fi.tuni.prog3.sisu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class GetTutkinnot {

    public void GetTutkinnot() {}

    // Haetaan kaikki tutkinto-ohjelmat sisusta ja palautetaan ne module typpisinä
    // listassa
    public static ArrayList<Module> get_tutkiinot(ArrayList<Module> moduulit) throws IOException {
        
        JSONParser parser = new JSONParser();
        
        try 
        {
            URL url = new URL("https://sis-tuni.funidata.fi/kori/api/"
                    + "module-search?curriculumPeriodId=uta-lvv-2021&"
                    + "universityId=tuni-university-root-id&moduleType="
                    + "DegreeProgramme&limit=1000");
 
            URLConnection uc = url.openConnection();
            BufferedReader in = new BufferedReader(
            new InputStreamReader(uc.getInputStream()));
 
            String inputLine;
            while ((inputLine = in.readLine()) != null) {              
                JSONObject a = (JSONObject) parser.parse(inputLine);
                JSONArray resultsArray = (JSONArray) a.get("searchResults");
 
                for (Object o : resultsArray) {
                    JSONObject data = (JSONObject) o;
                    String id = (String) data.get("id");
                    String name = (String) data.get("name");
                    Module mod = new Module(name, id, false, 0);
                    moduulit.add(mod);
                }     
            }
      
    }
        catch (IOException e) {e.printStackTrace();}
        catch (Exception e) {e.printStackTrace();}
        
        return moduulit;
    }
}

        
