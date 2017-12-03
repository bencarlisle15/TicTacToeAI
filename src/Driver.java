import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		AIPlayer john = new AIPlayer("John", true);
		AIPlayer donald = new AIPlayer("Donald", false);
		int val = 1;
		for (int i = 0; i < 1000; i++) {
			if (i % 10000 == 0 || i % (val = val * 10 == i ? i : val) == 0) {
				System.out.println(i);
			}
			new Controller().startGame(john, donald);
		}
		System.out.println(john.getWins() + ", " + john.getTies() + ", " + john.getLosses());
		System.out.println("Size: " + john.getPositions().size());
		Support.preserveData(john);
		System.exit(0);
	}
}
