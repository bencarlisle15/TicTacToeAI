
public class Move {

	private int row;
	private int col;
	private static Move singleton = new Move();

	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	private Move() {

	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public static Move getInstance() {
		return singleton;
	}

	@Override
	public int hashCode() {
		String hashString = String.valueOf(row) + String.valueOf(col);
		return Integer.parseInt(hashString);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Move)) {
			return false;
		}
		return hashCode() == ((Move) o).hashCode();
	}
}
