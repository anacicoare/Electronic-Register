package electronicRegister;

public interface Element {
	void accept(Visitor visitor);
}
