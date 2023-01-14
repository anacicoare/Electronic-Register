package electronicRegister;

import java.util.*;

class ExistsCourse extends Exception{
	public ExistsCourse() {
		super("Course already exists!");
	}
}

class NoCourse extends Exception {
	public NoCourse() {
		super("Course does not exist!");
	}
}

public class Catalog implements Subject{
	ArrayList<Course> courses = new ArrayList<Course>();
	ArrayList<Observer> observers = new ArrayList<Observer>();
	
	// singleton design pattern
	private static Catalog instance = new Catalog();
	private Catalog() {	}
	public static Catalog getInstance() {
		return instance;
	}
	
	
	// adds a course to the register
	public void addCourse(Course course) throws ExistsCourse { 
		if(courses.contains(course))
			throw new ExistsCourse();
		
		courses.add(course);
	}
	
	
	// removes a course from the register
	public void removeCourse(Course course) throws NoCourse {
		if(!courses.contains(course))
			throw new NoCourse();
		
		courses.remove(course);
	}
	
	public String toString() {
		String result = new String();
		for(Course currentCourse : courses)
			result = result + currentCourse.getName() + " ";
		return result;
	}
	
	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.add(observer);
	}
	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);
	}
	@Override
	public void notifyObservers(Grade grade) {
		// TODO Auto-generated method stub
		Notification notify = new Notification(grade);
		for(Observer currentObserver : observers)
			currentObserver.update(notify);
	}
}
