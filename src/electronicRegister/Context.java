package electronicRegister;

import java.util.ArrayList;

public class Context {
	private Strategy strategy;

	public Context(Strategy strategy){
	   this.strategy = strategy;
	}

	public Student executeStrategy(ArrayList<Grade> grades){
	   return strategy.doOperation(grades);
	}
}
