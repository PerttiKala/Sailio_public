package fi.tuni.prog3.sisu;
 
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
 

import org.json.simple.parser.ParseException;


public class Sisu extends Application {
 
    
    public String nimi;
    public String opnum;
    public String tutkinto;
    public JSONArray courses;
    public ArrayList<Module> tutkintoNimet; 
    ObservableList<String> items;
    TreeItem root_item;
    Module root;
    
    
    public static void main(String[] args) throws IOException {
        launch();
    }
    
    @Override
    public void start(Stage stage) throws IOException {

        ArrayList<Module> nimet = new ArrayList<>();
        GetTutkinnot x = new GetTutkinnot();
        tutkintoNimet = x.get_tutkiinot(nimet);
        
        stage.setTitle("SISU");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 280, 130);
        TextField nameField = new TextField();
        nameField.setPrefWidth(200);
        TextField numberField = new TextField();
        numberField.setPrefWidth(200);
        Label nameLabel = new Label("Nimi");
        Label numberLabel = new Label("Opiskelijanumero");
        Label spaceCreated = new Label("      ");
        Button saveButton = new Button("Tallenna");
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 0, 1);
        grid.add(numberLabel, 0, 2);
        grid.add(numberField, 0, 3);
        grid.add(saveButton, 2, 5);
        grid.add(spaceCreated, 1, 4); 
        stage.setScene(scene);
 
        saveButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                try{
                    String name = nameField.getText();
                    String number = numberField.getText();
                    JSONReader reader = new JSONReader("opiskelijatiedot.json");
                    JSONWriter writer = new JSONWriter("opiskelijatiedot.json");
 
                    if (!reader.check(name, number)){
                        System.out.println("False");
                        writer.addStudent(name, number);
                    }
                    nimi = name;
                    opnum = number;
                    courses = reader.getCourses(nimi, opnum);
                    tutkinto = reader.getTutkinto(nimi, opnum);                
                }
                catch(Exception x){
 
                }
                createStage(stage);           
            }
        });
        stage.show();
    }
    

 
    
    public void createStage(Stage stage){
 

        Button quitButton = new Button("Tallenna ja lopeta");
        Button tutkintoButton = new Button("Valitse Tutkinto");
        quitButton.setPrefWidth(150);
        tutkintoButton.setPrefWidth(150);
 
        GridPane grid = new GridPane();
        Scene valikko = new Scene(grid, 480, 600);
        Label nameLabel = new Label("Nimi: " + nimi);
        nameLabel.setPrefWidth(150);
        Label numberLabel = new Label("Opiskelijanumero: " + opnum);
        numberLabel.setPrefWidth(180);
        Label tutkintoLabel = new Label("");
 
        grid.add(nameLabel, 0, 0);
        grid.add(numberLabel, 1, 0);
        grid.add(quitButton, 2, 0);
        grid.add(tutkintoLabel, 1, 1);
        grid.add(tutkintoButton, 2, 1);
 
        if (tutkinto == null){
            tutkintoLabel.setText("Tutkintoa ei määritelty");
        }
        else {
            tutkintoLabel.setText(tutkinto);
            for (Module mod : tutkintoNimet) {
                if (tutkinto.equals(mod.get_name())) {
                    root = new Module("Tutkinto:", "1", false, 0);
                    GetSubmodules x = new GetSubmodules();
                    x.get_submodules(root, mod, 0);
                    createSisu(grid, root);
                }
                
            }
            
        }
        stage.setScene(valikko);
        

        
        quitButton.setOnAction((ActionEvent e) -> {
            JSONArray j = new JSONArray();
            courses = get_completed(root.submodules, j);
            JSONWriter writer = new JSONWriter("opiskelijatiedot.json");
            try {
                writer.saveData(nimi, opnum, tutkinto, courses);
                Platform.exit();
            } catch (ParseException ex) {
            } catch (IOException ex) {
                Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
 
        tutkintoButton.setOnAction(new EventHandler<ActionEvent>(){
        @Override
            public void handle(ActionEvent e) {

                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("valitse tutkinto");
                ChoiceBox choiceBox = new ChoiceBox();
                Button etene = new Button("Tallenna");
 
                for (Module item : tutkintoNimet){
                    choiceBox.getItems().add(item.get_name());
                }
                HBox hbox = new HBox(choiceBox, etene);
 
                Scene tutkintoScene = new Scene(hbox, 200, 200);
                secondaryStage.setScene(tutkintoScene);
                secondaryStage.show();
 
                etene.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                    public void handle(ActionEvent e) {
                        tutkinto = (String) choiceBox.getValue();
                        secondaryStage.close();
                        createStage(stage);
                    }});
        }
        });
    }
    
    
    public void createSisu(GridPane grid, Module root){
        
        TreeView list = new TreeView();
        root_item = new TreeItem("o_0");
        createModule(root.submodules.get(0), root_item);
        list.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        grid.add(list,0,3,3,1);
        TreeItem parent = (TreeItem) root_item.getChildren().get(0);       
        list.setRoot(parent);
    }
    
    public void createModule(Module mod, TreeItem root){
        String name = mod.get_name();
        
        if (!mod.is_course) {
            TreeItem child = new TreeItem(name);            
            for (Module o : mod.submodules){
                createModule(o, child);
            }     
            root.getChildren().add(child);
        }   
        else {
            CheckBoxTreeItem child = new CheckBoxTreeItem(name);
            child.addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), 
                    new EventHandler() {
                @Override
                public void handle(Event t) {
                    if (mod.completed) {
                        mod.completed = false;                        
                    }
                    else {
                        mod.completed = true;
                    }
                }                        
                    });
            
            if (courses.contains(mod.get_id())) {
                child.setSelected(true);
                mod.completed = true;
            }
            
            root.getChildren().add(child);
        }
    }
    
    
    public JSONArray get_completed(ArrayList<Module> lista, JSONArray j) {
 
        for (Module a : lista) {                      
            if (!a.is_course) {
                get_completed(a.submodules, j);
            }       
            else {
                if (a.completed) {
                    j.add(a.get_id());
                }
            }
        }   
        return j;
    }
    }
