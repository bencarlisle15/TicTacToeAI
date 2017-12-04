import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import javafx.application.Platform;

public class Support {

	public static ArrayList<Move> findLegalMoves(Letter[][] board) {
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				if (board[r][c] == Letter.EMPTY) {
					legalMoves.add(new Move(r, c));
				}
			}
		}
		return legalMoves;
	}

	public static boolean checkEquals(Letter[][] board1, Letter[][] board2) {
		for (int r = 0; r < board1.length; r++) {
			for (int c = 0; c < board1[r].length; c++) {
				if (board1[r][c] != board2[r][c]) {
					return false;
				}
			}
		}
		return true;
	}

	public static void preserveData(AIPlayer player) throws IOException {
		String fileName = "Positions" + player.getName() + ".txt";
		FileWriter fileWriter = new FileWriter(fileName);
		StringBuffer toWrite = new StringBuffer("");
		HashSet<Position> positions = player.getPositions();
		HashMap<Move, HashMap<Result, Long>> moves;
		HashMap<Result, Long> results;
		for (Position position : positions) {
			toWrite.append("[");
			toWrite.append(position.toHashString());
			moves = position.getMoves();
			toWrite.append("{");
			for (Move move : moves.keySet()) {
				toWrite.append(move.getRow() + "," + move.getCol());
				results = moves.get(move);
				toWrite.append("(");
				for (Result result : results.keySet()) {
					toWrite.append(result + ",");
					toWrite.append(results.get(result));
					toWrite.append(":");
				}
				toWrite.append(")");
			}
			toWrite.append("}]\n");
		}
		fileWriter.write(toWrite.toString());
		fileWriter.close();
	}

	public static HashSet<Position> readFile(AIPlayer player) {
		HashSet<Position> tempPositions = new HashSet<Position>();
		if (player.isIntelligent()) {
			try {
				String fileName = "Positions" + player.getName() + ".txt";
				FileInputStream fileStream = new FileInputStream(fileName);
				Scanner scanner = new Scanner(fileStream);
				String currentLine;
				Letter[][] newBoard = new Letter[3][3];
				Position position;
				int row;
				int col;
				Result result;
				long number;
				Move move;
				int newPos;
				while (scanner.hasNextLine()) {
					currentLine = scanner.nextLine();
					int i = 1;
					for (int r = 0; r < newBoard.length; r++) {
						for (int c = 0; c < newBoard[r].length; c++) {
							if (currentLine.charAt(i) == '0') {
								newBoard[r][c] = Letter.EMPTY;
							} else if (currentLine.charAt(i) == '1') {
								newBoard[r][c] = Letter.O;
							} else {
								newBoard[r][c] = Letter.X;
							}
							i++;
						}
					}
					position = new Position(null, newBoard);
					i++;
					while (currentLine.charAt(i) != '}') {
						row = Integer.parseInt(String.valueOf(currentLine.charAt(i++)));
						i++;
						col = Integer.parseInt(String.valueOf(currentLine.charAt(i++)));
						i++;
						move = new Move(row, col);
						position.getMoves().put(move, new HashMap<Result, Long>());
						while (currentLine.charAt(i) != ')') {
							newPos = currentLine.indexOf(",", i);
							result = Result.valueOf(currentLine.substring(i++, newPos));
							i = newPos + 1;
							number = Long.parseLong(currentLine.substring(i++, i = currentLine.indexOf(":", i)));
							position.getMoves().get(move).put(result, number);
							i++;
						}
						i++;
					}
					tempPositions.add(position);
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return tempPositions;
	}

	public static void printBoard(Letter[][] board) {
		synchronized (board) {
			Platform.runLater(()->Driver.addToTextArea("\n"));
			for (Letter[] element : board) {
				for (Letter element2 : element) {
					if (element2 == Letter.X) {
						Platform.runLater(()->Driver.addToTextArea("X "));
					} else if (element2 == Letter.O) {
						Platform.runLater(()->Driver.addToTextArea("O "));
					} else {
						Platform.runLater(()->Driver.addToTextArea("- "));
					}
				}
				Platform.runLater(()->Driver.addToTextArea("\n"));
			}
		}
	}
}
