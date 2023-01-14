package electronicRegister;

import java.util.*;

public abstract class Course {
	private String name = new String();
	private Teacher teacher;
	private HashSet<Assistant> assistants = new HashSet<Assistant>();
	private ArrayList<Grade> grades = new ArrayList<Grade>();
	private HashMap<String, Group> groups = new HashMap<String, Group>();
	private int credit;
	public Strategy strategy;
	private Snapshot snapshot = new Snapshot(grades);

	public Course() { }
	public Course(CourseBuilder builder) {
		this.name = builder.name;
		this.teacher = builder.teacher;
		this.assistants = builder.assistants;
		this.grades = builder.grades;
		this.groups = builder.groups;
		this.credit = builder.credit;
	}
	
	//getter & setter for name
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	//getter &  setter for teacher
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	//getter & setter for assistants
	public void setAssistants(HashSet<Assistant> assistants) {
		this.assistants = assistants;
	}
	
	public HashSet<Assistant> getAssistants() {
		return assistants;
	}
	
	//getter & setter for grades
	public void setGrades(ArrayList<Grade> grades) {
		this.grades = grades;
	}
	
	public ArrayList<Grade> getGrades() {
		return grades;
	}
	
	//getter & setter for groups
	public void setGroups(HashMap<String, Group> groups) {
		this.groups = groups;
	}
	
	public HashMap<String, Group> getGroups() {
		return groups;
	}
	
	//getter & setter for credit
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	public int getCredit() {
		return credit;
	}
	
	// set assistant in the group with indicated ID
	// if assistant is not in assistants, add it there
	public void addAssistant(String ID, Assistant assistant) {
		Group modify = groups.get(ID);
		modify.assistant = assistant;
		
		if(!assistants.contains(assistant))
			assistants.add(assistant);
	}
	
	// add the student in the group with indicated ID
	public void addStudent(String ID, Student student) {
		Group modify = groups.get(ID);
		modify.add(student);
	}
	
	// adds the group to the hashmap
	public void addGroup(Group group) {
		groups.put(group.ID, group);
	}
	
	// creates a group and adds it
	public void addGroup(String ID, Assistant assistant) {
		Group newGroup = new Group(ID, assistant);
		groups.put(ID, newGroup);
	}
	
	// creates a group and adds it (2nd constructor)
	public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
		Group newGroup = new Group(ID, assist, comp);
		groups.put(ID, newGroup);
	}
	
	// returns the grade of a student or null
	public Grade getGrade(Student student) {
		int i;
		for(i = 0; i < grades.size(); i++)
			if(grades.get(i).getStudent().equals(student))
				return grades.get(i);
		return null;
	}
	
	// adds a grade
	public void addGrade(Grade grade) {
		grades.add(grade);
	}
	
	// returns a list with all of the students
	public ArrayList<Student> getAllStudents() {
		ArrayList<Student> allStudents = new ArrayList<Student>();
		
		for(Map.Entry<String, Group> entry : groups.entrySet()) {
			Group currentGroup = entry.getValue();
			for(Student currentStudent : currentGroup)
				allStudents.add(currentStudent);
		}
		
		return allStudents;
	}
	
	// returns a hashmap with all student's grades
	public HashMap<Student, Grade> gettAllStudentGrades() {
		HashMap<Student, Grade> studentHashMap = new HashMap<Student, Grade>();

		for(Grade currentGrade : grades)
			studentHashMap.put(currentGrade.getStudent(), currentGrade);

		return studentHashMap;
	}
	
	public Student getBestStudent() {
		return strategy.doOperation(grades);
	}
	
	public void makeBackup() {
		snapshot = new Snapshot(grades);
	}
	public void undo() { 
		grades = snapshot.getState();
	}

	public String toString() { return this.name; }

	// abstract method to be implemented
	public abstract ArrayList<Student> getGraduatedStudents();

	public ArrayList<Course> getEnrolledCourses(String student) {
		Catalog catalog = Catalog.getInstance();
		ArrayList<Course> result = new ArrayList<Course>();

		for(Course currentCourse : catalog.courses)
			for(Student currentStudent : currentCourse.getAllStudents())
				if(currentStudent.toString().equals(student))
					result.add(currentCourse);

		return result;
	}
	
	private class Snapshot {
		private ArrayList<Grade> grades = new ArrayList<Grade>();
		public Snapshot(ArrayList<Grade> grades) {
			for(Grade currentGrade : grades)
				this.grades.add(currentGrade.clone());
		}
		
		public ArrayList<Grade> getState() { return this.grades; }
	}
	
	
	
	public static abstract class CourseBuilder {
		private String name = new String();
		private Teacher teacher;
		private HashSet<Assistant> assistants = new HashSet<Assistant>();
		private ArrayList<Grade> grades = new ArrayList<Grade>();
		private HashMap<String, Group> groups = new HashMap<String, Group>();
		private int credit;
		
		CourseBuilder(String name) {
			this.name = name;
		}
		
		public CourseBuilder teacher(Teacher teacher) {
			this.teacher = teacher;
			return this;
		}
		
		public CourseBuilder assistants(HashSet<Assistant> assistants) {
			this.assistants = assistants;
			return this;
		}
		
		public CourseBuilder grades(ArrayList<Grade> grades) {
			this.grades = grades;
			return this;
		}
		
		public CourseBuilder groups(HashMap<String, Group> groups) {
			this.groups = groups;
			return this;
		}
		
		public CourseBuilder credit(int credit) {
			this.credit = credit;
			return this;
		}
	}
}
