import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Driver extends Application {

	private Player player1 = new AIPlayer("John", true);
	private Player player2 = new AIPlayer("Donald", false);
	private boolean started;
	private static TextArea textArea = new TextArea();

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane mainPane = new BorderPane();
		NumberTextField trials = new NumberTextField(100000);
		trials.setOnAction(value -> {
			boolean tempStarted;
			synchronized (this) {
				tempStarted = started;
			}
			if (!tempStarted) {
				if (player1 instanceof HumanPlayer || player2 instanceof HumanPlayer) {
					Platform.runLater(
							() -> submit(trials.getText().length() == 0 ? 0 : Integer.parseInt(trials.getText())));
				} else {
					new Thread(() -> submit(trials.getText().length() == 0 ? 0 : Integer.parseInt(trials.getText())))
							.start();
				}
			}
		});
		HBox options = new HBox();
		ComboBox<String> player1Options = new ComboBox<String>();
		ComboBox<String> player2Options = new ComboBox<String>();
		player1Options.getItems().addAll("Intelligent AI", "Dumb AI", "Human");
		player2Options.getItems().addAll("Intelligent AI", "Dumb AI", "Human");
		player1Options.setValue("Intelligent AI");
		player2Options.setValue("Dumb AI");
		player1Options.setOnAction(value -> {
			if (player1Options.getValue().equals("Intelligent AI")) {
				player1 = new AIPlayer("John", true);
			} else if (player1Options.getValue().equals("Dumb AI")) {
				player1 = new AIPlayer("John", false);
			} else {
				player1 = new HumanPlayer();
			}
		});
		player2Options.setOnAction(value -> {
			if (player2Options.getValue().equals("Intelligent AI")) {
				player2 = new AIPlayer("Donald", true);
			} else if (player2Options.getValue().equals("Dumb AI")) {
				player2 = new AIPlayer("Donald", false);
			} else {
				player2 = new HumanPlayer();
			}
		});
		Button submit = new Button("Submit");
		submit.setOnAction(value -> {
			new Thread(()->{
				if (started) {
					while (started) {
						try {
							Thread.sleep(0);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
				if (player1 instanceof HumanPlayer || player2 instanceof HumanPlayer) {
					Platform.runLater(
							() -> submit(trials.getText().length() == 0 ? 0 : Integer.parseInt(trials.getText())));
				} else {
					new Thread(() -> submit(trials.getText().length() == 0 ? 0 : Integer.parseInt(trials.getText())))
							.start();
				}
			}).start();
		});
		options.getChildren().add(trials);
		options.getChildren().add(player1Options);
		options.getChildren().add(player2Options);
		options.getChildren().add(submit);
		mainPane.setTop(options);
		mainPane.setCenter(textArea);
		stage.setTitle("TicTacToe AI");
		stage.setScene(new Scene(mainPane, 500, 500));
		stage.show();
	}

	private void submit(int trials) {
		synchronized (this) {
			started = true;
		}
		int val = 1;
		for (int i = 0; i < trials; i++) {
			if (i % 10000 == 0 || i % (val = val * 10 == i ? i : val) == 0) {
				final int tempVal = i;
				Platform.runLater(()->addToTextArea(String.valueOf(tempVal + "\n")));
			}
			new Controller().startGame(player1, player2);
		}
		int wins = player1.getWins();
		int ties = player1.getTies();
		int losses = player1.getLosses();
		Platform.runLater(()->addToTextArea("Player 1 (wins, ties, losses) " + wins + ", " + ties + ", "
				+ losses + "\n"));
		if (player1 instanceof AIPlayer) {
			AIPlayer aiPlayer1 = (AIPlayer) player1;
			if (aiPlayer1.isIntelligent()) {
				try {
					Support.preserveData(aiPlayer1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (player2 instanceof AIPlayer) {
			AIPlayer aiPlayer2 = (AIPlayer) player2;
			if (aiPlayer2.isIntelligent()) {
				try {
					Support.preserveData(aiPlayer2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		player1.resetScores();
		player2.resetScores();
		synchronized (this) {
			started = false;
		}
	}
	
	public static void addToTextArea(String message) {
		textArea.appendText(message);
	}
}
