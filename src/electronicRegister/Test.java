package electronicRegister;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
	public static void main(String[] args) throws ExistsCourse, NoCourse, UserTypeException, CloneNotSupportedException, IOException, ParseException {
		Catalog catalog = Catalog.getInstance();
		UserFactory userFactory = new UserFactory();
		JSONParser parser = new JSONParser();

		//parsing JSON file
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\Ana\\Desktop\\teme\\tema_poo_intellij\\src\\electronicRegister\\catalog.json"));

		// fill courses
		JSONArray coursesArray = (JSONArray) jsonObject.get("courses");
		for(Object currentObj : coursesArray) {
			FullCourse fullCourse = null;
			PartialCourse partialCourse;

			JSONObject currentCourse = (JSONObject) currentObj;

			String type = (String) currentCourse.get("type");
			String strategy = (String) currentCourse.get("strategy");
			String name = (String) currentCourse.get("name");


			JSONObject teacherNames = (JSONObject) currentCourse.get("teacher");
			Teacher courseTeacher = (Teacher) userFactory.initUser("teacher",
										(String) teacherNames.get("firstName"),
										(String) teacherNames.get("lastName"));


			JSONArray assistantsJsonArray = (JSONArray) currentCourse.get("assistants");
			HashSet<Assistant> assistants = new HashSet<>();
			for(Object i : assistantsJsonArray) {
				JSONObject currentAssistantJson = (JSONObject) i;
				Assistant currentAssistant = (Assistant) userFactory.initUser("assistant",
											     (String) currentAssistantJson.get("firstName"),
											     (String) currentAssistantJson.get("lastName"));
				assistants.add(currentAssistant);
			}


			HashMap<String, Group> groups = new HashMap<String, Group>();
			JSONArray groupsJsonArray = (JSONArray) currentCourse.get("groups");
			for(Object g : groupsJsonArray) {
				JSONObject currentGroupJSON = (JSONObject) g;
				String groupID = (String) currentGroupJSON.get("ID");

				JSONObject currentAssistantJson = (JSONObject) currentGroupJSON.get("assistant");
				Assistant currentAssistant = (Assistant) userFactory.initUser("assistant",
							(String) currentAssistantJson.get("firstName"),
							(String) currentAssistantJson.get("lastName"));

				Group currentGroup = new Group(groupID, currentAssistant);

				JSONArray studentArrayJSON = (JSONArray) currentGroupJSON.get("students");
				for(Object s : studentArrayJSON) {
					JSONObject currentStudentDetails = (JSONObject) s;
					String firstName = (String) currentStudentDetails.get("firstName");
					String lastName = (String) currentStudentDetails.get("lastName");

					JSONObject motherJSON = (JSONObject) currentStudentDetails.get("mother");
					JSONObject fatherJSON = (JSONObject) currentStudentDetails.get("father");
					Parent mother;
					Parent father;

					if (motherJSON != null)
						mother = (Parent) userFactory.initUser("parent",
								(String) motherJSON.get("firstName"),
								(String) motherJSON.get("lastName"));
					else mother = null;

					if (fatherJSON != null)
						father = (Parent) userFactory.initUser("parent",
								(String) fatherJSON.get("firstName"),
								(String) fatherJSON.get("lastName"));
					else father = null;

					Student student = (Student) userFactory.initUser("student", firstName, lastName);
					student.setMother(mother);
					student.setFather(father);

					currentGroup.add(student);

					groups.put(groupID, currentGroup);
				}

			}

			if(type.equals("FullCourse")) {
				FullCourse.FullCourseBuilder builder = new FullCourse.FullCourseBuilder(name);
				fullCourse = ((FullCourse.FullCourseBuilder) builder
							.teacher(courseTeacher)
							.assistants(assistants)
							.groups(groups))
							.build();

				if(strategy.equals("BestExamScore"))
					fullCourse.strategy = new BestExamScore();
				if(strategy.equals("BestPartialScore"))
					fullCourse.strategy = new BestPartialScore();
				if(strategy.equals("BestTotalScore"))
					fullCourse.strategy = new BestTotalScore();


				catalog.addCourse(fullCourse);
			}

			if(type.equals("PartialCourse")) {
				PartialCourse.PartialCourseBuilder builder = new PartialCourse.PartialCourseBuilder(name);
				partialCourse = ((PartialCourse.PartialCourseBuilder) builder
							.teacher(courseTeacher)
							.assistants(assistants)
							.groups(groups))
							.build();

				if(strategy.equals("BestExamScore"))
					partialCourse.strategy = new BestExamScore();
				if(strategy.equals("BestPartialScore"))
					partialCourse.strategy = new BestPartialScore();
				if(strategy.equals("BestTotalScore"))
					partialCourse.strategy = new BestTotalScore();

				catalog.addCourse(partialCourse);
			}
		}

		ScoreVisitor allScores = new ScoreVisitor();

		// fill examScores
		JSONArray examJSON = (JSONArray) jsonObject.get("examScores");
		for(Object currentExamJSON : examJSON) {
			JSONObject currentExam = (JSONObject) currentExamJSON;


			JSONObject currentTeacher = (JSONObject) currentExam.get("teacher");
			Teacher teacher = (Teacher) userFactory.initUser("teacher",
							(String)currentTeacher.get("firstName"),
							(String)currentTeacher.get("lastName"));


			JSONObject currentStudentJSON = (JSONObject) currentExam.get("student");
			Student student = (Student) userFactory.initUser("student",
					(String)currentStudentJSON.get("firstName"),
					(String)currentStudentJSON.get("lastName"));


			String courseName = (String) currentExam.get("course");

			Double grade = (Double) currentExam.get("grade");

			Course currentCourse = null;
			for(Course courseItr : catalog.courses)
				if(courseItr.toString().equals(courseName))
					currentCourse = courseItr;

			Student currentStudent = null;
			for(Student studentItr : currentCourse.getAllStudents())
				if(studentItr.toString().equals(student.toString()))
					currentStudent = studentItr;

			if(allScores.existsExamTeacher(teacher.toString()))
				allScores.addExamGrade(teacher.toString(), currentStudent, courseName, grade);
			else allScores.addExamTeacher(teacher.toString(), currentStudent, courseName, grade);

		}


		// fill partialScores
		JSONArray partialJSON = (JSONArray) jsonObject.get("partialScores");
		for(Object currentPartialJSON : partialJSON) {
			JSONObject currentPartial = (JSONObject) currentPartialJSON;


			JSONObject currentAssistant = (JSONObject) currentPartial.get("assistant");
			Assistant assistant = (Assistant) userFactory.initUser("assistant",
					(String) currentAssistant.get("firstName"),
					(String) currentAssistant.get("lastName"));


			JSONObject currentStudentJSON = (JSONObject) currentPartial.get("student");
			Student student = (Student) userFactory.initUser("student",
					(String) currentStudentJSON.get("firstName"),
					(String) currentStudentJSON.get("lastName"));


			String courseName = (String) currentPartial.get("course");

			Double grade = (Double) currentPartial.get("grade");

			Course currentCourse = null;
			for (Course courseItr : catalog.courses)
				if (courseItr.toString().equals(courseName))
					currentCourse = courseItr;

			Student currentStudent = null;
			for (Student studentItr : currentCourse.getAllStudents())
				if (studentItr.toString().equals(student.toString()))
					currentStudent = studentItr;

			if(allScores.existsPartialAssistant(assistant.toString()))
				allScores.addPartialGrade(assistant.toString(), currentStudent, courseName, grade);
			else allScores.addPartialAssistant(assistant.toString(), currentStudent, courseName, grade);


		}

		//finished parsing JSON file
		// getting the courses a student is enrolled in

		// testing :


		// test swing pages

//		StudentPage studentPage = new StudentPage(enrolledCourses.getEnrolledCourses(catalog.courses.get(1).getAllStudents().get(5).toString()),
//				catalog.courses.get(0).getAllStudents().get(4));


//		InstructorPage instructorPage = new InstructorPage(allScores);

//		ParentPage parentPage = new ParentPage(catalog.courses.get(0));


		// test best strategy & memento:

//		Teacher course0Teacher = catalog.courses.get(0).getTeacher();
//
//		Iterator<Assistant> assistantItr = catalog.courses.get(0).getAssistants().iterator();
//		Assistant assistant0 = assistantItr.next();
//		allScores.visit(assistant0);
//
//		assistant0 = assistantItr.next();
//		allScores.visit(course0Teacher);
//		allScores.visit(assistant0);
//

//		// test strategy
//		System.out.println(catalog.courses.get(0).getBestStudent());
//

//		// test memento
//		catalog.courses.get(0).makeBackup();
//		catalog.courses.get(0).getGrades().get(0).setExamScore(99999.0);
//		System.out.println(catalog.courses.get(0).getGrades());
//
//		catalog.courses.get(0).undo();
//		System.out.println(catalog.courses.get(0).getGrades());
	}
}
