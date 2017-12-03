public class Board {

	private Letter[][] board = new Letter[3][3];
	private Status gameStatus = Status.IN_PROGRESS;

	public Board() {
		resetBoard();
	}

	public Board(Board newBoard) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				board[r][c] = newBoard.board[r][c];
			}
		}
	}

	public Letter[][] getBoard() {
		return board;
	}

	public Status getGameStatus() {
		return gameStatus;
	}

	public void setValue(int row, int col, Letter letter) {
		board[row][col] = letter;
		gameStatus = updateStatus();
	}

	public Letter getValue(int row, int col) {
		return board[row][col];
	}

	public void resetBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				board[r][c] = Letter.EMPTY;
			}
		}
	}

	private Status updateStatus() {
		for (Letter[] element : board) {
			if (element[0] == element[1] && element[2] == element[0] && element[0] != Letter.EMPTY) {
				if (element[0] == Letter.O) {
					return Status.OWIN;
				} else {
					return Status.XWIN;
				}
			}
		}
		for (int i = 0; i < board.length; i++) {
			if (board[0][i] == board[1][i] && board[2][i] == board[0][i] && board[0][i] != Letter.EMPTY) {
				if (board[0][i] == Letter.O) {
					return Status.OWIN;
				} else {
					return Status.XWIN;
				}
			}
		}
		if (board[0][0] == board[1][1] && board[2][2] == board[0][0] && board[0][0] != Letter.EMPTY) {
			if (board[0][0] == Letter.O) {
				return Status.OWIN;
			} else {
				return Status.XWIN;
			}
		}
		if (board[0][2] == board[1][1] && board[2][0] == board[0][2] && board[0][2] != Letter.EMPTY) {
			if (board[0][2] == Letter.O) {
				return Status.OWIN;
			} else {
				return Status.XWIN;
			}
		}
		for (Letter[] element : board) {
			for (Letter element2 : element) {
				if (element2 == Letter.EMPTY) {
					return Status.IN_PROGRESS;
				}
			}
		}
		return Status.TIE;
	}
}
