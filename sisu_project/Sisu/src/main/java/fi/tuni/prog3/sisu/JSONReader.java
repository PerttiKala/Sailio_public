package fi.tuni.prog3.sisu;
 
 
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
public class JSONReader{
 
    public String filename;
 
    public JSONReader(String filename) {
        this.filename = filename;
    }
 
    public boolean check(String nimi, String opiskelijanumero) {
        JSONParser parser = new JSONParser();
 
        try (Reader reader = new FileReader(filename)) {
 
            JSONObject a = (JSONObject) parser.parse(reader);
 
            JSONArray resultsArray = (JSONArray) a.get("opiskelijat");
 
            for (Object o : resultsArray) {
                JSONObject data = (JSONObject) o;
                String name = (String)data.get("Nimi");
                if (name.equals(nimi)){
                    String number = (String)data.get("Opnum");
                    if(number.equals(opiskelijanumero)){
                        return true;
                    }
                }
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public JSONArray getStudents() {
        JSONParser parser = new JSONParser();
        JSONArray opis = new JSONArray();
        try (Reader reader = new FileReader(filename)) {
 
            JSONObject a = (JSONObject) parser.parse(reader);           
            opis = (JSONArray) a.get("opiskelijat");
 
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (opis == null){
            return new JSONArray();            
        }else{
            return opis;
        }
    }
 
    public JSONArray getCourses(String nimi, String opiskelijanumero) throws IOException, ParseException{
 
 
        JSONReader reader = new JSONReader(filename);
        JSONArray studentlist = reader.getStudents();
 
        for (Object o : studentlist){
            JSONObject data = (JSONObject) o;
            String name = (String)data.get("Nimi");
            String number = (String)data.get("Opnum");
            if (name.equals(nimi) && number.equals(opiskelijanumero)){
                JSONArray courses = (JSONArray) data.get("Kurssit");  
                return courses;
                }
        }
        return null;
    }
 
    public String getTutkinto(String nimi, String opiskelijanumero) throws IOException, ParseException{
 
 
        JSONReader reader = new JSONReader(filename);
        JSONArray studentlist = reader.getStudents();
 
        for (Object o : studentlist){
            JSONObject data = (JSONObject) o;
            String name = (String)data.get("Nimi");
            String number = (String)data.get("Opnum");
            if (name.equals(nimi) && number.equals(opiskelijanumero)){
                String tut = (String) data.get("Tutkinto");
 
                return tut;
            }
 
        }
        return null;
    }
 
 
}