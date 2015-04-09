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
public class Rotate {
	
	//array of vectors indicating direction to search
	private static int[][] vectors = {{1,0}, {0,1}, {1,1}, {-1,1}};

	public static void main(String[] args) {
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ROutput.txt"), "utf-8"));
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
			//Only need to apply gravity - rotating unnecessary
			for (int i = 0; i < board.length; i++) {
				int lastFree = 0;
				for (int j = board.length-1; j >=0; j--) {
					if (board[i][j] == '.' && j > lastFree) lastFree = j;
					if (board[i][j] != '.' && lastFree != 0) {
						board[i][lastFree--] = board[i][j];
						board[i][j] = '.';
					}
				}
			}
			
			//Now we check if there are k of a kind, checking each of the directions in turn
			boolean kRed = false;
			boolean kBlue = false;
			
			for (int[] vector : vectors) {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board.length; j++) {
						 
						char piece = board[i][j];
						if (piece != '.') {
							int endI = i + vector[0] * (k-1);
							int endJ = j + vector[1] * (k-1);
							
							//Test a connection is possible at current pos
							if (endI < 0 || endI >= board.length || endJ < 0 || endJ >= board.length)
								break;
							boolean connected = true;
							for (int a = 1; a < k; a++) {
								if (board[i+vector[0]*a][j+vector[1]*a] != piece) {
									connected = false;
									break;
								}
							}
							if (connected && piece == 'R') kRed = true;
							if (connected && piece == 'B') kBlue = true;
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
