package electronicRegister;

import java.util.ArrayList;

public class BestTotalScore implements Strategy{
	public Student doOperation(ArrayList<Grade> grades) {
		Grade bestGrade = grades.get(0);
		for(Grade currentGrade : grades)
			if(currentGrade.getTotal() > bestGrade.getTotal())
				bestGrade = currentGrade;
		
		return bestGrade.getStudent();
	}
}
