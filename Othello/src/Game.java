import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {

	private static Player player1;
	private static Player player2;
	private static Player current;
	static Board board = null;

	boolean goMenu = false;

	public Game(Player p1, Player p2) {
		Game.player1 = p1;
		Game.player2 = p2;
	}

	public static Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		Game.player1 = player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		Game.player2 = player2;
	}

	public static Player getCurrent() {
		return current;
	}

	public static void setCurrent(Player current) {
		Game.current = current;
	}

	public Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		Game.board = board;
	}

	// --------------------------------------------------
	public void start() {
		current = new Player(player1.getName());
		current.setSymbol(Position.BLACK);
		boolean startValid = true;
		Scanner kb = new Scanner(System.in);
		while (startValid) {
			System.out.println("Please select the starting position(1 or 2): ");
			System.out.println("	1. Standard");
			System.out.println("	2. Four-by-fourStandard");
			
			int start_p = kb.nextInt();

			if (start_p == 1) {
				board = new Board(Board.Standard());
				board.drawBoard();
				play();
				startValid = false;
				kb.close();
			} else if (start_p == 2) {
				board = new Board(Board.FourByFour());
				board.drawBoard();
				play();
				startValid = false;
				System.out.println(startValid);
				kb.close();

			} else {
				System.out.println("Invalid numebr. Please try again.");
			}
		}
	}

	// here
	public void play() {
		while ((valid_left(player1) || valid_left(player2)) && !goMenu) {
			if (valid_left(current))
				option_can();
			else
				option_cannot();
		}
		gameOver();
	}

	//load the existing boards
	public static Board load() {
		Board board = null;
		boolean exist = false;

		while (!exist) {
			System.out.println("=========================================");
			System.out.println("Enter the file name you want to load: ");
			System.out.println("-----------------------------------------");
			File f = new File("C:\\Users\\sooah\\eclipse\\c249\\Othello");
			File[] files = f.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt");
				}
			});

			for (File file : files) {
				System.out.println(file.getName());
			}

			Scanner kb = new Scanner(System.in);
			System.out.print("\nWhich file?(exclude '.txt'): ");
			String fileName = kb.next();
			Scanner openFile;
			try {
				openFile = new Scanner(new File(fileName + ".txt"));
				int count = 0;

				while (openFile.hasNext()) {
					String s = openFile.nextLine();
					count++;
					switch (count) {
					case 1:
						player1 = new Player(s);
						player1.setSymbol(Position.BLACK);
						break;

					case 2:
						player2 = new Player(s);
						player2.setSymbol(Position.WHITE);
						break;

					case 3:
						current = new Player(s);
						if (current.getName().equals(player1.getName())) {
							current.setSymbol(player1.getSymbol());
							setCurrent(player1);
						} else {
							current.setSymbol(player2.getSymbol());
							setCurrent(player2);
						}
						break;
					case 4:
						board = new Board(s);
						// board.drawBoard();
					}
				}
				exist = true;
			} catch (FileNotFoundException e) {
				System.out.println("Error! There is no such file. Please try again.");
			}
		}
		return board;
	}

	private boolean valid_left(Player player) {
		boolean validLeft = false;
		outerLoop: for (int r = 0; r < Board.posB[0].length; r++) {
			for (int c = 0; c < Board.posB.length; c++) {
				if (Board.posB[r][c].getPiece() == Position.EMPTY) {
					if (Board.posB[r][c].canPlay(r, c, player.getSymbol())) {
						validLeft = true;
						break outerLoop;
					}
				}else
					validLeft = false;
			}
		}
		return validLeft;
	}

	private void save() {
		// convert array of Position to String
		Scanner kb = new Scanner(System.in);
		String p1 = player1.getName();
		String p2 = player2.getName();
		String cur = current.getName();
		System.out.println(cur);
		String board = "";
		for (int i = 0; i < Board.posB[0].length; i++) {
			for (int j = 0; j < Board.posB.length; j++) {
				board += String.valueOf(Board.posB[i][j]);
			}
		}
		// save as a file
		boolean exists = true;
		System.out.println("=========================================");
		System.out.println("Let's save.");
		System.out.println("1. Save as new game / 2. Overwrite");
		int howToSave = kb.nextInt();
		if (howToSave == 1) {
			while (exists) {
				System.out.print("Enter the file name: ");
				try {
					String fileName = kb.next();
					File newF = new File(fileName + ".txt");
					if (newF.createNewFile()) {
						try {
							PrintWriter writer = new PrintWriter(newF);
							writer.println(p1);
							writer.println(p2);
							writer.println(cur);
							writer.print(board);
							writer.close();
						} catch (IOException e) {
						}
						exists = false;
					} else {
						System.out.println("File with the same name already exists. Try another name.");
					}
				} catch (IOException e) {
				}
			}
		} else {
			File f = new File("C:\\Users\\sooah\\eclipse\\c249\\Othello");
			File[] files = f.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt");
				}
			});

			for (File file : files) {
				System.out.println(file.getName());
			}

			System.out.println("\nWhich file do you want to overwrite?(exclude '.txt'): ");
			String fileName = kb.next();
			try {
				File oldF = new File(fileName + ".txt");
				while (exists) {
					oldF.delete();
					System.out.print("Enter the new file name: ");
					String newFileName = kb.next();
					File file = new File(newFileName + ".txt");
					if (file.createNewFile()) {
						//oldF.delete();
						File newF = new File(newFileName + ".txt");
						try {
							PrintWriter writer = new PrintWriter(newF);
							writer.println(p1);
							writer.println(p2);
							writer.println(cur);
							writer.print(board);
							writer.close();
						} catch (IOException e) {
						}
						exists = false;
					} else {
						System.out.println("File with the same name already exists. Try another name.");
					}
				}
			} catch (IOException e) {
			}
			System.out.println("========= The game has been saved =========\n");

		}
	}

	public void makeMove() {
		// this method makes move AND flip.
		boolean IsValidSpace = false;
		Scanner sc = new Scanner(System.in);
		while (!IsValidSpace) {
			board.drawBoard();
			System.out.println("Enter row and column (0~7): ");
			System.out.print("Row : ");
			int r = sc.nextInt();
			System.out.print("Column : ");
			int c = sc.nextInt();

			if (r < 0 || r > 7 || c < 0 || c > 7) {
				System.out.println("Please enter between 0 and 7.");
			} else {
				if (Board.posB[r][c].canPlay(r, c, current.getSymbol())) {
					Board.posB[r][c].flip(r, c, current.getSymbol());
					board.drawBoard();
					System.out.println("---Nice!\n");
					board.takeTurn(current);
					IsValidSpace = true;
				} else {
					if (Board.posB[r][c].getPiece() == Position.EMPTY)
						System.out.println("Error! This space has no flips available. Please try again.\n");
					else
						System.out.println("Error! The space is already taken. Choose an empty space\n");
				}
			}
		}
	}

	private void option_can() {
		Scanner kb = new Scanner(System.in);
		boolean valid = true;
		while (valid) {
			System.out.println("=========================================");
			System.out.println(
					"Current player: " + Game.getCurrent() + " (symbol = " + Game.getCurrent().getSymbol() + ")");
			System.out.println("-----------------------------------------");
			System.out.println("Select one of the below: ");
			System.out.println("	1. Save");
			System.out.println("	2. Concede(give up)");
			System.out.println("	3. Make move");
			int option = kb.nextInt();

			switch (option) {
			case 1:
				save();
				goMenu = true;
				valid = false;
				break;
			case 2:
				goMenu = true;
				valid = false;
				break;
			case 3:
				makeMove();
				valid = false;
				break;
			default:
				System.out.println("Invalid number. Please enter again.\n");
			}
		}
	}

	private void option_cannot() {
		Scanner kb = new Scanner(System.in);
		boolean valid = true;
		while (valid) {
			System.out.println("=========================================");
			System.out.println(
					"Current player: " + Game.getCurrent() + " (symbol = " + Game.getCurrent().getSymbol() + ")");
			System.out.println("-----------------------------------------");
			System.out.println("You cannot make a move.");
			System.out.println("Select one of the below: ");
			System.out.println("	1. Save");
			System.out.println("	2. Concede(give up)");
			System.out.println("	3. Forfeit the turn");
			int option = kb.nextInt();

			switch (option) {
			case 1:
				save();
				goMenu = true;
				valid = false;
				break;
			case 2:
				goMenu = true;
				valid = false;
				break;
			case 3:
				board.takeTurn(current);
				valid = false;
				break;
			default:
				System.out.println("Invalid number. Please enter again.\n");
				break;
			}
		}
	}

	private void gameOver() {
		checkWon();
		System.out.println("\n+++++++++ Game over ++++++++++");
	}

	private int sum(char symbol) {
		int sum = 0;
		for (int r = 0; r < Board.posB[0].length; r++) {
			for (int c = 0; c < Board.posB.length; c++) {
				if (Board.posB[r][c].getPiece() == symbol)
					sum++;
			}
		}
		return sum;
	}

	private void checkWon() {
		System.out.println("- Black: " + sum(Position.BLACK));
		System.out.println("- White: " + sum(Position.WHITE));
		if (sum(Position.BLACK) > sum(Position.WHITE))
			System.out.println("Player 1(" + player1 + ") has won!\n");
		else if (sum(Position.BLACK) < sum(Position.WHITE))
			System.out.println("Player 2(" + player2 + ") has won!\n");
		else
			System.out.println("It's a draw\n");
	}

}
