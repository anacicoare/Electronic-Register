package electronicRegister;

import java.util.*;

public class ScoreVisitor implements Visitor{
	
	private class Tuple<arg1, arg2, arg3> {
		arg1 student;
		arg2 courseName;
		arg3 grade;
		Tuple (arg1 student, arg2 courseName, arg3 grade) {
			this.student = student;
			this.courseName = courseName;
			this.grade = grade;
		}
	}
	
	HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores = new HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>>();
	HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores = new HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>>();

	public boolean existsExamTeacher(String teacher) {
		for(Teacher teacherItr : examScores.keySet())
			if(teacherItr.toString().equals(teacher))
				return true;
		return false;
	}

	public void addExamTeacher(String teacher, Student student, String courseName, Double grade) {
		Tuple<Student, String, Double> tuple = new Tuple<Student, String, Double>(student, courseName, grade);
		ArrayList<Tuple<Student, String, Double>> arrGrades = new ArrayList<Tuple<Student, String, Double>>();
		arrGrades.add(tuple);

		Catalog catalog = Catalog.getInstance();

		Teacher currentTeacher = null;
		for(Course courseItr : catalog.courses)
			if(courseItr.getTeacher().toString().equals(teacher.toString()))
				currentTeacher = courseItr.getTeacher();

		examScores.put(currentTeacher, arrGrades);
	}

	public void addExamGrade(String teacher, Student student, String courseName, Double grade) {
		Tuple<Student, String, Double> tuple = new Tuple<Student, String, Double>(student, courseName, grade);

		Catalog catalog = Catalog.getInstance();

		Teacher currentTeacher = null;
		for(Course courseItr : catalog.courses)
			if(courseItr.getTeacher().toString().equals(teacher))
				currentTeacher = courseItr.getTeacher();

		examScores.get(currentTeacher).add(tuple);
	}

	public void addPartialAssistant(String assistant, Student student, String courseName, Double grade) {
		Tuple<Student, String, Double> tuple = new Tuple<Student, String, Double>(student, courseName, grade);
		ArrayList<Tuple<Student, String, Double>> arrGrades = new ArrayList<Tuple<Student, String, Double>>();
		arrGrades.add(tuple);

		Catalog catalog = Catalog.getInstance();
		Assistant currentAssistant = null;
		for(Course courseItr : catalog.courses)
			for(Assistant assistantItr : courseItr.getAssistants())
				if(assistant.equals(assistantItr.toString()))
					currentAssistant = assistantItr;

		partialScores.put(currentAssistant, arrGrades);
	}

	public void addPartialGrade(String assistant, Student student, String courseName, Double grade) {
		Tuple<Student, String, Double> tuple = new Tuple<Student, String, Double>(student, courseName, grade);

		Catalog catalog = Catalog.getInstance();
		Assistant currentAssistant = null;
		for(Course courseItr : catalog.courses)
			for(Assistant assistantItr : courseItr.getAssistants())
				if(assistant.equals(assistantItr.toString()))
					currentAssistant = assistantItr;

		partialScores.get(currentAssistant).add(tuple);
	}

	public boolean existsPartialAssistant(String assistant) {
		for(Assistant assistantItr : partialScores.keySet())
			if(assistantItr.toString().equals(assistant))
				return true;
		return false;
	}

	public ArrayList<Student> getStudents(Teacher teacher, String courseName) {
		ArrayList<Student> result = new ArrayList<Student>();

		for(Tuple<Student, String, Double> tuple : examScores.get(teacher))
			if(tuple.courseName.equals(courseName))
				result.add(tuple.student);

		return result;
	}

	public ArrayList<Student> getStudents(Assistant assistant, String courseName) {
		ArrayList<Student> result = new ArrayList<Student>();

		for(Tuple<Student, String, Double> tuple : partialScores.get(assistant))
			if(tuple.courseName.equals(courseName))
				result.add(tuple.student);

		return result;
	}

	public ArrayList<Double> getGrades(Teacher teacher, String courseName) {
		ArrayList<Double> result = new ArrayList<Double>();

		for(Tuple<Student, String, Double> tuple : examScores.get(teacher))
			if(tuple.courseName.equals(courseName))
				result.add(tuple.grade);

		return result;
	}

	public ArrayList<Double> getGrades(Assistant assistant, String courseName) {
		ArrayList<Double> result = new ArrayList<Double>();

		for(Tuple<Student, String, Double> tuple : partialScores.get(assistant))
			if(tuple.courseName.equals(courseName))
				result.add(tuple.grade);

		return result;
	}

	public void visit(Assistant assistant) {
		Catalog catalog = Catalog.getInstance();
		ArrayList<Tuple<Student, String, Double>> listTuples = partialScores.get(assistant);
	
		for(Tuple<Student, String, Double> currentTuple : listTuples) {
			Student currentStudent = currentTuple.student;
			String currentCourseName = currentTuple.courseName;
			Double currentPartialGrade = currentTuple.grade;
			
			Course currentCourse = null;
			
			//get course from currentCourseName
			for(Course itr : catalog.courses) 
				if(itr.getName().equals(currentCourseName))
					currentCourse = itr;
			
			//get grade that contains the student
			ArrayList<Grade> updatedGrades = currentCourse.getGrades();
			
			Grade currentGrade = null;
			for(Grade itr : updatedGrades)
				if(itr.getStudent().equals(currentStudent))			
					currentGrade = itr;
			
			//the student was not enrolled in the course
			if(currentGrade == null) {
				currentGrade = new Grade();
				currentGrade.setStudent(currentStudent);
				currentGrade.setCourse(currentCourseName);
				currentGrade.setPartialScore(currentPartialGrade);
				
				updatedGrades.add(currentGrade);
			}
			else {
				//modify only partial grade
				currentGrade.setPartialScore(currentPartialGrade);
			}
			
			currentCourse.setGrades(updatedGrades);
			
			catalog.notifyObservers(currentGrade);
		}
	}

	public void visit(Teacher teacher) {
		Catalog catalog = Catalog.getInstance();
		ArrayList<Tuple<Student, String, Double>> listTuples = examScores.get(teacher);
	
		for(Tuple<Student, String, Double> currentTuple : listTuples) {
			Student currentStudent = currentTuple.student;
			String currentCourseName = currentTuple.courseName;
			Double currentExamGrade = currentTuple.grade;
			
			Course currentCourse = null;
			
			//get course from currentCourseName
			for(Course itr : catalog.courses) 
				if(itr.getName().equals(currentCourseName))
					currentCourse = itr;
			
			//get grade that contains the student
			ArrayList<Grade> updatedGrades = currentCourse.getGrades();
			
			Grade currentGrade = null;
			for(Grade itr : updatedGrades)
				if(itr.getStudent().equals(currentStudent))			
					currentGrade = itr;
			
			//the student was not enrolled in the course
			if(currentGrade == null) {
				currentGrade = new Grade();
				currentGrade.setStudent(currentStudent);
				currentGrade.setCourse(currentCourseName);
				currentGrade.setExamScore(currentExamGrade);
				
				updatedGrades.add(currentGrade);
			}
			else {
				//modify only partial grade
				currentGrade.setExamScore(currentExamGrade);
			}
			
			currentCourse.setGrades(updatedGrades);
			
			catalog.notifyObservers(currentGrade);
		}
	}

}
