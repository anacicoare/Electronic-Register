package electronicRegister;

import java.util.ArrayList;

public class FullCourse extends Course {

	public FullCourse(FullCourseBuilder fullCourseBuilder) {
		super(fullCourseBuilder);
		
	}

	public ArrayList<Student> getGraduatedStudents() {
		ArrayList<Student> graduatedStudents = new ArrayList<Student>();
		ArrayList<Student> allStudents = getAllStudents();
		
		for(Student currentStudent : allStudents)
			if(getGrade(currentStudent).getExamScore() >= 2 && getGrade(currentStudent).getPartialScore() >= 3)
				graduatedStudents.add(currentStudent);
		
		return graduatedStudents;
	}

	public static class FullCourseBuilder extends CourseBuilder {
		FullCourseBuilder(String name) {
			super(name);
		}

		public FullCourse build() {
			return new FullCourse(this);
		}
		
	}
	
}
