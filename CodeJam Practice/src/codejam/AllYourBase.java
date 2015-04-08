package codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

//This currently fails on small input, although it passes on the sample data.
//Will fix tomorrow
public class AllYourBase {

	public static void main(String[] args) {
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("AYBOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			List<TestCase> cases = readCases(read);
			for (int i = 1; i <= cases.size(); i++) {
				write.write("Case #" + i + ": " + cases.get(i-1).solve());
				if (i != cases.size()) write.newLine();
			}
			
			write.close();
			read.close();
		}
		catch (IOException e) { throw new RuntimeException(e); }
	}
	
	private static List<TestCase> readCases(BufferedReader read) throws IOException {
		List<TestCase> cases = new ArrayList<TestCase>();
		int numCases = Integer.parseInt(read.readLine());
		
		for (int i = 0; i < numCases; i++) {
			String[] params = read.readLine().split(" ");
			int size = Integer.parseInt(params[0]);
			int k = Integer.parseInt(params[1]);
			
			char[][] board = new char[size][size];
			for (int j = 0; j < size; j++) {
				board[j] = read.readLine().toCharArray();
			}
			cases.add(new TestCase(board, k));
		}
		
		return cases;
	}
	
	private static class TestCase {
		private char[][] board;
		private int k;
		
		public TestCase(char[][] b, int k) {
			board = b;
			this.k = k;
		}
		
		private String solve() {
			//First we rotate - the formula is transpose, then reverse every column
			char[][] newBoard = new char[board.length][board.length];
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					newBoard[j][board.length-1-i] = board[i][j];
				}
			}
			//Now we apply 'gravity'
			for (int j = 0; j < board.length; j++) {
				int lastFree = -1;
				for (int i = board.length-1; i >= 0; i--) {
					if (newBoard[i][j] == '.' && lastFree == -1) lastFree = i; //Found first free spot
					else if (lastFree > i && newBoard[i][j] != '.') { //Move it down to the last free spot, make current spot empty, move last free slot up one
						newBoard[lastFree][j] = newBoard[i][j];
						lastFree--;
						newBoard[i][j] = '.';
					}
				}
			}
			//Now we check if there are k of a kind
			//For the sake of my sanity we split it up into two checks - diagonal case is awkward
			boolean kRed = false;
			boolean kBlue = false;
			for (int i = 0; i < board.length; i++) {
				System.out.println(newBoard[i]);
			}
			
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					char piece = newBoard[i][j];
					
					if (piece != '.' && ((piece == 'B' && !kBlue) || (piece == 'R' && !kRed))) {
						if (j <= board.length - k) { //Check vertical
							boolean connected = true;
							for (int a = (j-1)+k; a>j; a--) {
								if (newBoard[i][a] != piece) {
									connected = false;
									break;
								}
							}
							if (connected && piece == 'B') kBlue = true;
							else if (connected) kRed = true;
						}
						if (i + k <= board.length && ((piece == 'B' && !kBlue) || (piece == 'R' && !kRed))) { //Check horizontal
							boolean connected = true;
							for (int a = (i-1)+k; a>i; a--) {
								if (newBoard[a][j] != piece) {
									connected = false;
									break;
								}
							}
							if (connected && piece == 'B') kBlue = true;
							else if (connected) kRed = true;
						}
					}
				}
			}
			if (kRed && kBlue) return "Both";
			
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					char piece = newBoard[i][j];
					
					if (piece != '.' && ((piece == 'B' && !kBlue) || (piece == 'R' && !kRed))) { //Check sloping up
						if (i + k <= board.length) {
							if (j + k <= board.length) {
								boolean connected = true;
								for (int a = 1; a < k; a++) {
									if(newBoard[i+a][j+a] != piece) {
										connected = false;
										break;
									}
								}
								if (connected && piece == 'B') kBlue = true;
								else if (connected) kRed = true;
							}
							if (j-k >= 0 && ((piece == 'B' && !kBlue) || (piece == 'R' && !kRed))) { //Check sloping down
								boolean connected = true;
								for (int a = 1; a < k; a++) {
									if(newBoard[i+a][j-a] != piece) {
										connected = false;
										break;
									}
								}
								if (connected && piece == 'B') kBlue = true;
								else if (connected) kRed = true;
							}
						}
					}
				}
			}
			
			if (kRed && kBlue) return "Both";
			if (kRed) return "Red";
			if (kBlue) return "Blue";
			return "Neither";
		}
	}

}
