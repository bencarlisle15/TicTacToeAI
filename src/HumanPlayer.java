import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class HumanPlayer implements Player {

	private Move move = Move.getInstance();
	private int wins = 0;
	private int ties = 0;
	private int losses = 0;

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
		synchronized (this) {
			if (result == Result.WIN) {
				System.out.append("You win!\n");
				wins++;
			} else if (result == Result.TIE) {
				System.out.append("It's a tie.\n");
				ties++;
			} else {
				System.out.append("You Lose!\n");
				losses++;
			}
		}
	}

	@Override
	public void alertPlayerToMove(Letter[][] board) {
		synchronized (this) {
			Support.printBoard(board);
			System.out.append("\n");
		}
		dialog();
	}

	public void dialog() {
		Dialog<String> dialog = new Dialog<>();
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> System.exit(0));
		dialog.setTitle("Enter Attributes");
		dialog.setHeaderText("Enter Attributes");
		Label rowLabel = new Label("Row: ");
		Label colLabel = new Label("Col: ");
		NumberTextField textRow = new NumberTextField(0);
		NumberTextField textCol = new NumberTextField(0);
		GridPane grid = new GridPane();
		grid.add(rowLabel, 1, 1);
		grid.add(textRow, 2, 1);
		grid.add(colLabel, 1, 2);
		grid.add(textCol, 2, 2);
		dialog.getDialogPane().setContent(grid);
		ButtonType button = new ButtonType("Submit", ButtonData.OK_DONE);
		textRow.setOnAction(value -> {
			move = new Move(Integer.parseInt(textRow.getText()), Integer.parseInt(textCol.getText()));
			dialog.close();
		});
		textRow.setOnAction(value -> {
			move = new Move(Integer.parseInt(textRow.getText()), Integer.parseInt(textCol.getText()));
			dialog.close();
		});
		dialog.getDialogPane().getButtonTypes().add(button);
		dialog.showAndWait();
		if (textRow.getText().length() == 0 || textCol.getText().length() == 0) {
			dialog();
			return;
		}
		move = new Move(Integer.parseInt(textRow.getText()), Integer.parseInt(textCol.getText()));
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
	public int getTies() {
		return ties;
	}

	@Override
	public int getLosses() {
		return losses;
	}
}
