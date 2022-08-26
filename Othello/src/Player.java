
public class Player {
	
	private String name;
	private char symbol;
	
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	public String toString() {
		return getName();
	}

}
