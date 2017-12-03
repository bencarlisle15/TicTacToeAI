
public interface Player {

	public Move getMove();

	public void alertToChange();

	public void alertEnd(Result result);

	public void alertPlayerToMove(Letter[][] board);
}