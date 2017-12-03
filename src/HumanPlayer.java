import java.util.Scanner;

public class HumanPlayer implements Player {

	private Move move = Move.getInstance();
	private Scanner scanner = new Scanner(System.in);

	@Override
	public Move getMove() {
		return move;
	}

	@Override
	public void alertToChange() {
		move = Move.getInstance();
	}

	@Override
	public void alertEnd(Result result) {
		if (result == Result.WIN) {
			System.out.println("You win!");
		} else if (result == Result.TIE) {
			System.out.println("It's a tie.");
		} else {
			System.out.println("You Lose!");
		}
	}

	@Override
	public void alertPlayerToMove(Letter[][] board) {
		Support.printBoard(board);
		System.out.println();
		System.out.println("Enter row: ");
		int row = scanner.nextInt();
		System.out.println("Enter col");
		int col = scanner.nextInt();
		move = new Move(row, col);
	}
}
