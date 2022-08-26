
public class PlayablePosition extends Position {
	
	public PlayablePosition() {
		super();
	}

	@Override
	public boolean canPlay(int row, int column, char symbol) {
		boolean canPlay = false;
		if(canFlip(row, column, symbol)) {
			canPlay = true;
		}
		return canPlay;
	}
	
}
