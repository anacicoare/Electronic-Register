package electronicRegister;

class UserTypeException extends Exception {
	public UserTypeException() {
		super("Could not find the type of user you wish to create!");
	}
}

public abstract class User {
	private String firstName, lastName;
	
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String toString() {
			return firstName + " " + lastName;
		
	}
}

class Parent extends User implements Observer{
	Notification currentNotification;
	
	public Parent(String firstName, String lastName) {
		super(firstName, lastName);
	}

	@Override
	public void update(Notification notification) {
		this.currentNotification = notification;
		
	}
	
}

class Student extends User {
	private Parent mother, father;
	
	public Student(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public void setMother(Parent mother) {
		this.mother = mother;
	}
	
	public void setFather(Parent father) {
		this.father = father;
	}
	
	public Parent getMother() {
		return mother;
	}
	
	public Parent getFather() {
		return father;
	}

	public int compareTo(Student o2) {
		return this.getFather().toString().charAt(0) - o2.getFather().toString().charAt(0);
	}
}

class Assistant extends User implements Element{

	public Assistant(String firstName, String lastName) {
		super(firstName, lastName);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

class Teacher extends User implements Element{

	public Teacher(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}

class UserFactory{
	public User initUser(String userType, String firstName, String lastName) throws UserTypeException {
		if(userType.equalsIgnoreCase("PARENT"))
			return new Parent(firstName, lastName);
		
		if(userType.equalsIgnoreCase("STUDENT"))
			return new Student(firstName, lastName);
		
		if(userType.equalsIgnoreCase("ASSISTANT"))
			return new Assistant(firstName, lastName);
		
		if(userType.equalsIgnoreCase("TEACHER"))
			return new Teacher(firstName, lastName);
		
		throw new UserTypeException();
	}
	
}
