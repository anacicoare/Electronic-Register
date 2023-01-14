package electronicRegister;


public class Grade implements Comparable<Grade>, Cloneable{
	private Double partialScore;
	private Double examScore;
	private Student student;
	private String course; // Numele cursului
	
	public int compareTo(Grade o) {
		if(this.getTotal() > o.getTotal())
			return 1;
		
		if(this.getTotal() == o.getTotal())
			return 0;
		
			return -1;
		
	}

	public Grade clone(){
		Grade clonedGrade = new Grade();
		
		clonedGrade.setCourse(this.course);
		clonedGrade.setExamScore(this.examScore);
		clonedGrade.setPartialScore(this.partialScore);
		clonedGrade.setStudent(this.student);
		
		return clonedGrade;
	}
	
	public void setPartialScore(Double score) {
		this.partialScore = score;
	}
	
	public Double getPartialScore() {
		return partialScore;
	}
	
	
	public void setExamScore(Double score) {
		this.examScore = score;
	}
	
	public Double getExamScore() {
		return examScore;
	}
	
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Student getStudent() {
		return student;
	}
	
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getCourse() {
		return course;
	}
	
	
	public Double getTotal() {
		return partialScore + examScore;
	}

	public String toString() {
		String result = new String();

		String examString, partialString, totalString;

		result = result + "Student is; " + getStudent();
		result = result + "\n";
		result = result + "Course name is: " + course;
		result = result + "\n";

		if(examScore == null)
			result += "Your ExamScore has not been added!";
		else result += "ExamScore is: " + examScore;

		result = result + "\n";
		if(partialScore == null)
			result += "Your PartialScore has not been added!";
		else result += "PartialScore is: " + partialScore;

		result = result + "\n";

		
		return result;
	}
	
}
