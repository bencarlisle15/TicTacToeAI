import java.util.HashMap;

public class Position {

	private HashMap<Move, HashMap<Result, Long>> moves = new HashMap<Move, HashMap<Result, Long>>();
	private Letter[][] board;
	private Move toMove;

	public Position(Move move, Letter[][] board) {
		if (move != null) {
			populate(move);
			toMove = new Move(move.getRow(), move.getCol());
		} else {
			toMove = move;
		}
		this.board = new Letter[board.length][board[0].length];
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				this.board[r][c] = board[r][c];
			}
		}
	}

	private void populate(Move move) {
		moves.put(move, new HashMap<Result, Long>());
		moves.get(move).put(Result.WIN, 0L);
		moves.get(move).put(Result.LOSS, 0L);
		moves.get(move).put(Result.TIE, 0L);
	}

	public HashMap<Move, HashMap<Result, Long>> getMoves() {
		return moves;
	}

	public Letter[][] getBoard() {
		return board;
	}

	public void addResult(Result result) {
		moves.get(toMove).put(result, moves.get(toMove).get(result) + 1);
		toMove = null;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(toHashString());
	}

	public void setToMove(Move move) {
		if (move != null) {
			toMove = new Move(move.getRow(), move.getCol());
			if (!moves.containsKey(move)) {
				populate(move);
			} else {
				toMove = move;
			}
		} else {
			toMove = move;
		}
	}

	public String toHashString() {
		StringBuffer hashString = new StringBuffer("");
		for (Letter[] element : board) {
			for (Letter element2 : element) {
				if (element2 == Letter.EMPTY) {
					hashString.append("0");
				} else if (element2 == Letter.O) {
					hashString.append("1");
				} else {
					hashString.append("2");
				}
			}
		}
		return hashString.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		} else if (!(object instanceof Position)) {
			return false;
		}
		return hashCode() == ((Position) object).hashCode();
	}

	public boolean isReady() {
		return toMove != null;
	}
}