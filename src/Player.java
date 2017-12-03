
public interface Player {

	public Move getMove();

	public int getWins();

	public int getTies();

	public int getLosses();

	public void resetScores();

	public void alertToChange();

	public void alertEnd(Result result);

	public void alertPlayerToMove(Letter[][] board);
}