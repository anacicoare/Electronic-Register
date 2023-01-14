package electronicRegister;

public class Notification{
	Grade grade;
	Student student;

	Notification(Grade grade) {
		this.grade = grade;
		this.student = grade.getStudent();
	}
	public String toString() {
		String result = new String();

		String examString, partialString, totalString;

		result = result + "Student is : " + student;
		result = result + "\n";

		if(grade.getExamScore() == null)
			result += student + "'s exam score has not been added!";
		else result += student + "'s exam score is: " + grade.getExamScore();

		result = result + "\n";
		if(grade.getPartialScore() == null)
			result += student + "'s partial score has not been added!";
		else result += student + "'s partial score is: " + grade.getPartialScore();

		result = result + "\n";


		return result;
	}

}
