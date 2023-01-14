package electronicRegister;

import java.util.ArrayList;

public class PartialCourse extends Course {

	public PartialCourse() { }
	public PartialCourse(PartialCourseBuilder builder) {
		super(builder);
	}
	
	public ArrayList<Student> getGraduatedStudents() {
		ArrayList<Student> graduatedStudents = new ArrayList<Student>();
		ArrayList<Student> allStudents = getAllStudents();
		
		for(Student currentStudent : allStudents)
			if(getGrade(currentStudent).getTotal() > 5)
				graduatedStudents.add(currentStudent);
		
		return graduatedStudents;
	}
	
	public static class PartialCourseBuilder extends CourseBuilder {	
		PartialCourseBuilder(String name) {
			super(name);
		}

		public PartialCourse build() {
			return new PartialCourse(this);
		}
	}
	
}
