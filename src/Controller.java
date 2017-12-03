public class Controller {

	private Player player1;
	private Player player2;
	private Status gameStatus;
	private Board board = new Board();

	public void startGame(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		gameStatus = Status.IN_PROGRESS;
		waitForPlayer(player1);
	}

	private void waitForPlayer(Player player) {
		player.alertPlayerToMove(board.getBoard());
		Move move = player.getMove();
		if (move.getRow() > 2 || move.getCol() > 2 || board.getValue(move.getRow(), move.getCol()) != Letter.EMPTY) {
			waitForPlayer(player);
			return;
		}
		player.alertToChange();
		board.setValue(move.getRow(), move.getCol(), player == player1 ? Letter.X : Letter.O);
		gameStatus = analyzeStatus();
		if (gameStatus == Status.IN_PROGRESS) {
			if (player == player1) {
				waitForPlayer(player2);
			} else {
				waitForPlayer(player1);
			}
		}
	}

	private Status analyzeStatus() {
		Status gameStatus = board.getGameStatus();
		if (gameStatus == Status.XWIN) {
			player1.alertEnd(Result.WIN);
			player2.alertEnd(Result.LOSS);
		} else if (gameStatus == Status.OWIN) {
			player1.alertEnd(Result.LOSS);
			player2.alertEnd(Result.WIN);
		} else if (gameStatus == Status.TIE) {
			player1.alertEnd(Result.TIE);
			player2.alertEnd(Result.TIE);
		}
		return gameStatus;
	}
}
