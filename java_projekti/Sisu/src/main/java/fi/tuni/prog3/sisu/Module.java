package fi.tuni.prog3.sisu;

import java.util.ArrayList;


public class Module {
    String name;
    String id;
    boolean completed;
    ArrayList<Module> submodules;
    int depth;
    boolean is_course = false;
    
    public Module(String name, String id, boolean completed, int depth) {
        this.name = name;
        this.id = id;
        this.completed = completed;
        this.submodules = new ArrayList<>();
        this.depth = depth;
        
    }
    
    public String get_name() {
        return this.name;
}
    public String get_id() {
        return this.id;
    }
    
    public Boolean is_completed() {
        return this.completed;
    }
    
    public void mark_done() {
        this.completed = true;
    }
    
    public void mark_undone() {
        this.completed = false;
    }
    
    public void add_children(Module child) {
        this.submodules.add(child);
    }
    
    public int get_depth() {
        return this.depth;
    }
}
