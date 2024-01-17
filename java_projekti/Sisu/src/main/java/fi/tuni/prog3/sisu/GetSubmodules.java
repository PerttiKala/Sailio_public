package fi.tuni.prog3.sisu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GetSubmodules {
    
    // Haetaan alimoduulit ja kurssit rekursiivisesti  
    public static void get_submodules(Module parent, Module this_module, int depth) {
 
        String moduleID = this_module.get_id();
        JSONParser parser = new JSONParser();
        boolean is_groupid = false;
        if (moduleID.length() < 20) {
            is_groupid = true;
        }
        
        // Haetaan moduulin id:n avulla sille tiedot
        try {
 
            URL moduleUrl;
 
            if (!is_groupid) {
                moduleUrl = new URL ("https://sis-tuni.funidata.fi/kori/api/modules/" 
             +  moduleID);
            }
 
            else {
                moduleUrl = new URL ("https://sis-tuni.funidata.fi/kori/api/"
                        + "modules/by-group-id?groupId=" 
                        + moduleID + "&universityId=tuni-university-root-id");
            }
 
        JSONObject a;
        URLConnection connection = moduleUrl.openConnection();
        BufferedReader input = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));
 
        String inputLine;
 
        // kuljetaan rule:itten läpi
        inputLine = input.readLine();
 
        if (is_groupid) {
            JSONArray b = (JSONArray) parser.parse(inputLine);
            a = (JSONObject) b.get(0);
        }
 
        else {
            a = (JSONObject) parser.parse(inputLine);
        }
 
        JSONObject names = (JSONObject) a.get("name");
        String name = (String) names.get("fi");
        if (name == null) {
            name = (String) names.get("en");
        }
        
        String vali = "  ";
 
        Module new_module = new Module(name, moduleID, false, depth);
        parent.add_children(new_module);
        
        // Etsitään alimoduulien id:t
        while (a.containsKey("rule")) {
            a = (JSONObject) a.get("rule");
        }
 
        // Löydettiin rules
        JSONArray rules = (JSONArray) a.get("rules"); 
 
        JSONArray rulesParent = rules;
        ArrayList<JSONObject> id_lista = new ArrayList<>();
        get_rules(rulesParent, id_lista); 
 
 
        // Käydään alimoduulit läpi ja tarkastetaan alimoduulin tyyppi
        for (JSONObject o : id_lista) {
            String type = (String) o.get("type");
 
            if (type.equals("ModuleRule")) {
                String id = (String) o.get("moduleGroupId");
                Module child = new Module("nimi", id, false, depth);
                get_submodules(new_module, child, depth+1);
            }
 
            if (type.equals("CourseUnitRule")) {
                JSONParser parser2 = new JSONParser();
                String id = (String) o.get("courseUnitGroupId");
                URL courseURL = new URL ("https://sis-tuni.funidata.fi/kori/api/"
                        + "course-units/by-group-id?groupId=" + id + 
                        "&universityId=tuni-university-root-id");
 
                URLConnection con = courseURL.openConnection();
                BufferedReader in = new BufferedReader(
                 new InputStreamReader(con.getInputStream()));
 
                String inputStr = in.readLine();
                JSONArray courseJSONA = (JSONArray) parser2.parse(inputStr);
 
                if (courseJSONA.size() > 0) {
                    JSONObject courseJSON = (JSONObject) courseJSONA.get(0);
                    JSONObject course_names =(JSONObject) courseJSON.get("name");
                    String course_name = (String) course_names.get("fi");
                    String course_id = (String) courseJSON.get("groupId");
                    Module child = new Module(course_name, course_id, false, depth+1);
                    child.is_course = true;
                    parent.add_children(child);                  
                }      
            }
        }
        input.close();
        }
 
        catch (FileNotFoundException e) {e.printStackTrace();}   
        catch (IOException e) {e.printStackTrace();}
        catch (Exception e) {e.printStackTrace();} 
     }
 
    
    // funktion get_submodules apufunktio, etsii moduulin listan rules ja lisää sen 
    // parametrinä olevaan listaan 'list'
    public static void get_rules(JSONArray parent, ArrayList<JSONObject> list) {
 
        for (Object o : parent) {
            JSONObject data = (JSONObject) o;
        if  (data.containsKey("rules")) {
                    JSONArray child = (JSONArray)data.get("rules");
                    get_rules(child, list);  
            }
 
        else {
            list.add(data);
        }
 
        }    
    
}
}
