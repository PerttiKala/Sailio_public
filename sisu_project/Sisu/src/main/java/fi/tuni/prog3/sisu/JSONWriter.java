package fi.tuni.prog3.sisu;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
 
public class JSONWriter{
 
    public String filename;
    public JSONWriter(String filename){
        this.filename = filename;
    }
 
    public void addStudent(String nimi, String opiskelijanumero) throws IOException, ParseException{
        JSONObject student = new JSONObject();
        student.put("Nimi", nimi);
        student.put("Opnum", opiskelijanumero);
        JSONArray courses = new JSONArray();
        student.put("Kurssit", courses);
 
        JSONReader reader = new JSONReader(filename);
        JSONArray studentlist = reader.getStudents();
        studentlist.add(student);
 
        JSONObject obj = new JSONObject();
        obj.put("opiskelijat", studentlist);
 
        try (FileWriter file = new FileWriter(filename)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void saveData(String nimi, String opiskelijanumero,
            String tutkintoId, JSONArray kurssit) throws IOException, ParseException{
 
 
        JSONReader reader = new JSONReader(filename);
        JSONArray studentlist = reader.getStudents();
 
        JSONArray newstudentlist = new JSONArray();
        for (Object o : studentlist){
            JSONObject data = (JSONObject) o;
            String name = (String)data.get("Nimi");
            String number = (String)data.get("Opnum");
            if (name.equals(nimi) && number.equals(opiskelijanumero)){
                data.put("Kurssit", kurssit);
                if (tutkintoId != null){
                    data.put("Tutkinto", tutkintoId);
                }
            }
            newstudentlist.add(data);
        }
 
        JSONObject obj = new JSONObject();
        obj.put("opiskelijat", newstudentlist);
 
        try (FileWriter file = new FileWriter(filename)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}
 
