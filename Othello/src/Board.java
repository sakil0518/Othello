
public class Board {
	
	final static int SIZE = 8;
	public static Position[][] posB;	
	String positions;

	public Board(String positions) {
		super();
		posB = new Position[SIZE][SIZE];
		int a = 0;
		for(int r = 0;r < posB[0].length; r++) {
			for(int c = 0; c < posB.length; c++) {
				posB[r][c] = new PlayablePosition();
				posB[r][c].setPiece(positions.charAt(a));
				if(posB[r][c].getPiece() != Position.EMPTY) {
					posB[r][c] = new Position();
					posB[r][c].setPiece(positions.charAt(a));
				}
				a++;
			}
		}
	}

	public void drawBoard() {
		System.out.println(" 01234567");
		for(int r = 0;r < posB[0].length; r++) {
			System.out.print(r);
			for(int c = 0; c < posB.length; c++) {
				System.out.print(posB[r][c]);
			}
			System.out.println();
		}
	}
	
	public void takeTurn(Player current) {
		if(current.getName().equals(Game.getPlayer1().getName())) 
			Game.setCurrent(Game.getPlayer2());
		else 
			Game.setCurrent(Game.getPlayer1());
	}
		
	public static String Standard(){
		String s = "";
		char[][] charArr = new char[SIZE][SIZE];
		for(int i = 0;i < charArr[0].length;i++) {			
			for(int j = 0; j<charArr.length;j++) {
				charArr[i][j] = Position.getEmpty();	
			}
		}
		
		charArr[3][3] = charArr[4][4] = Position.getWhite();
		charArr[3][4] = charArr[4][3] = Position.getBlack();
		charArr[3][0] = charArr[4][0] = Position.getUnplayable();
		
		for(int i = 0;i < charArr[0].length;i++) {			
			for(int j = 0; j<charArr.length;j++) {
				s += String.valueOf(charArr[i][j]);
			}
		}
		return s;
	}
	
	public static String FourByFour(){
		String s = "";
		char[][] charArr = new char[SIZE][SIZE];
		for(int i = 0;i < charArr[0].length;i++) {
			for(int j = 0; j<charArr.length;j++) {
				charArr[i][j] = Position.getEmpty();
			}
		}
		
		charArr[2][2] = charArr[2][3] = charArr [3][2] = charArr [3][3]
				= charArr[4][4] = charArr[4][5] = charArr[5][4] = charArr[5][5] = Position.getWhite();
		
		charArr[2][4] = charArr[2][5] = charArr [3][4] = charArr [3][5]
				= charArr[4][2] = charArr[4][3] = charArr[5][2] = charArr[5][3] = Position.getBlack();

		charArr[3][0] = charArr[4][0] = Position.getUnplayable();

		for(int i = 0;i < charArr[0].length;i++) {			
			for(int j = 0; j<charArr.length;j++) {
				s += String.valueOf(charArr[i][j]);
			}
		}
		return s;
				
	}
}
