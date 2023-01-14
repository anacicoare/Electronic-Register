package electronicRegister;

import java.util.ArrayList;

public class BestExamScore implements Strategy{
	public Student doOperation(ArrayList<Grade> grades) {
		Grade bestGrade = grades.get(0);
		for(Grade currentGrade : grades)
			if(currentGrade.getExamScore() > bestGrade.getExamScore())
				bestGrade = currentGrade;
		
		return bestGrade.getStudent();
	}
}
