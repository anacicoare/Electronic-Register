package electronicRegister;

import java.util.*;

public interface Strategy {
	public Student doOperation(ArrayList<Grade> students);
}