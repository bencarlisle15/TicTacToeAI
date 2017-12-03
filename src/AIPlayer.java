import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class AIPlayer implements Player {

	private int wins = 0;
	private int losses = 0;
	private int ties = 0;
	private Random random = new Random();
	private Move move = Move.getInstance();
	private HashSet<Position> totalPositions;
	private boolean isReading;
	private String name;

	public AIPlayer(String name, boolean isReading) {
		this.name = name;
		this.isReading = isReading;
		totalPositions = Support.readFile(this);
	}

	public boolean isReading() {
		return isReading;
	}

	public String getName() {
		return name;
	}

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
			wins++;
		} else if (result == Result.TIE) {
			ties++;
		} else {
			losses++;
		}
		for (Position position : totalPositions) {
			if (position.isReady()) {
				position.addResult(result);
			}
		}
	}

	@Override
	public void resetScores() {
		wins = 0;
		ties = 0;
		losses = 0;
	}

	@Override
	public int getWins() {
		return wins;
	}

	@Override
	public int getLosses() {
		return losses;
	}

	@Override
	public int getTies() {
		return ties;
	}

	public HashSet<Position> getPositions() {
		return totalPositions;
	}

	@Override
	public void alertPlayerToMove(Letter[][] board) {
		ArrayList<Move> legalMoves = Support.findLegalMoves(board);
		Move newMove = legalMoves.get(random.nextInt(legalMoves.size()));
		Move potentialMove;
		if (name.equals("John")) {
			for (Position position : totalPositions) {
				if (Support.checkEquals(position.getBoard(), board)) {
					potentialMove = getMove(position, legalMoves);
					if (potentialMove != null) {
						newMove = potentialMove;
					}
					position.setToMove(newMove);
					move = newMove;
					break;
				}
			}
		}
		Position newPosition = new Position(newMove, board);
		totalPositions.add(newPosition);
		move = newMove;
	}

	private Move getMove(Position position, ArrayList<Move> legalMoves) {
		ArrayList<Move> goodMoves = getGoodMoves(position, legalMoves);
		if (goodMoves.size() == 0) {
			return null;
		}
		return goodMoves.get(new Random().nextInt(goodMoves.size()));
	}

	private ArrayList<Move> getGoodMoves(Position position, ArrayList<Move> legalMoves) {
		ArrayList<Move> goodMoves = new ArrayList<Move>();
		Move tempMove;
		for (int r = 0; r < position.getBoard().length; r++) {
			for (int c = 0; c < position.getBoard()[r].length; c++) {
				tempMove = new Move(r, c);
				if (!position.getMoves().containsKey(tempMove) && legalMoves.contains(tempMove)) {
					goodMoves.add(tempMove);
				}
			}
		}
		for (Move move : position.getMoves().keySet()) {
			if (legalMoves.contains(move)
					&& position.getMoves().get(move).get(Result.WIN) > 3
							* position.getMoves().get(move).get(Result.LOSS)
					|| position.getMoves().get(move).get(Result.LOSS) == 0) {
				goodMoves.add(move);
			}
		}
		return goodMoves;
	}
}
