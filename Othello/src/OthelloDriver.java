import java.util.Scanner;

public class OthelloDriver {

	public static void main(String[] args) {

		boolean valid = true;
		while (valid) {
			System.out.println("\n++++++++ Welcome to Othello game ++++++++\n");
			System.out.println("Select one of the below: ");
			System.out.println("	1. Quit");
			System.out.println("	2. Load a Game");
			System.out.println("	3. Start a New Game");

			Scanner kb = new Scanner(System.in);
			int menu = kb.nextInt();
			if (menu == 1) {
				// quit the game.
				System.out.println("The program has ended.");
				System.exit(0);
			} else if (menu == 2) {
				// load the game.
				Game.setBoard(Game.load());
				Game game = new Game(Game.getPlayer1(), Game.getPlayer2());
				game.play();
				
			} else if (menu == 3) {
				// start the new game
				System.out.println("Please enter the players'name (first player, second player): ");
				String p1Name = kb.next();
				String p2Name = kb.next();

				Player player1 = new Player(p1Name);
				player1.setSymbol(Position.BLACK);
				Player player2 = new Player(p2Name);
				player2.setSymbol(Position.WHITE);
				Game game = new Game(player1, player2);
				game.start();
			} else {
				System.out.println("Invalid number. Please enter again.");
			}

		}
	}

}
