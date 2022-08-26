
public class Position {

	private char piece;
	public final static char EMPTY = ' ';
	public final static char UNPLAYABLE = '*';
	public final static char BLACK = 'B';
	public final static char WHITE = 'W';

	public Position() {
	}

	public String toString() {
		return String.valueOf(getPiece());
	}

	public char getPiece() {
		return piece;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}

	public static char getEmpty() {
		return EMPTY;
	}

	public static char getUnplayable() {
		return UNPLAYABLE;
	}

	public static char getBlack() {
		return BLACK;
	}

	public static char getWhite() {
		return WHITE;
	}

	public boolean canPlay(int row, int column, char symbol) {
		return false;
		// Board.posB[row][column].getPiece() == EMPTY;
	}

	public boolean canFlip(int row, int column, char symbol) {
		boolean canFlip = false;
		if (Board.posB[row][column].getPiece() != EMPTY) {
			canFlip = false;
		} else {
			// check all directions
			outerLoop: for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					int count = 1;
					if (x == 0 && y == 0)
						continue; // when it points current space, ignore.

					int newX = row + x; // next space to check
					int newY = column + y; // next space to check
					// canFlip = false;
					while (newX >= 0 && newX < Board.SIZE && newY >= 0 && newY < Board.SIZE
							&& Board.posB[newX][newY].getPiece() != EMPTY) {

						if (Board.posB[newX][newY].getPiece() == UNPLAYABLE) {
							break;
						} else if (Board.posB[newX][newY].getPiece() == symbol) {
							if (count == 1)
								break; //
							else {
								canFlip = true;
								break outerLoop;// stops when it finds the same piece with current player's piece.(go
												// flip)
							}
						} else {
							// move on to the next space(with same direction)
							newX += x;
							newY += y;
							count++;
						}
					}

				}
			}
		}
		return canFlip;
	}

	public void flip(int row, int column, char symbol) {
		// check all directions
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				int count = 1;
				if (x == 0 && y == 0)
					continue; // when it points current space, ignore.

				int newX = row + x; // next space to check
				int newY = column + y; // next space to check

				boolean findEnd = false;
				while (newX >= 0 && newX < Board.SIZE && newY >= 0 && newY < Board.SIZE
						&& Board.posB[newX][newY].getPiece() != EMPTY) {

					if (Board.posB[newX][newY].getPiece() == UNPLAYABLE) {
						break;
					} else if (Board.posB[newX][newY].getPiece() == symbol) {
						if (count == 1)
							break;
						else {
							findEnd = true;
							break;
						}
					} else {
						// if it's still opponent's piece, move on to the next space(with same
						// direction)
						newX += x;
						newY += y;
						count++;
					}
				}

				// flip
				while (findEnd) {
					Board.posB[newX][newY] = new Position();
					Board.posB[newX][newY].setPiece(symbol);
					newX -= x;
					newY -= y;

					if (newX == row && newY == column) {
						Board.posB[newX][newY] = new Position();
						Board.posB[newX][newY].setPiece(symbol);
						break;// stops when it comes back to the original position
					}
				}
			}
		}
	}

}
