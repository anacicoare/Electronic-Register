package electronicRegister;

import java.util.*;

public class BestPartialScore implements Strategy{
	public Student doOperation(ArrayList<Grade> grades) {
		Grade bestGrade = grades.get(0);
		for(Grade currentGrade : grades)
			if(currentGrade.getPartialScore() > bestGrade.getPartialScore())
				bestGrade = currentGrade;
		
		return bestGrade.getStudent();
	}
}
