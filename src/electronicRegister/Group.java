package electronicRegister;

import java.util.*;


public class Group extends ArrayList<Student>{
	String ID;
	Assistant assistant;
	Comparator<Student> comp;
	
	public Group(String ID, Assistant assistant, Comparator<Student> comp) {
		super(new ArrayList<Student>());
		this.ID = ID;
		this.assistant = assistant;
		this.comp = comp;
	}
	public Group(String ID, Assistant assistant) {
		super(new ArrayList<Student>());
		this.ID = ID;
		this.assistant = assistant;
	}
	
}
