package codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class StoreCredit {
	
	public static void main(String[] args) {
		List<TestCase> cases = readInput(args[0]);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SCOutput.txt"), "utf-8"));
		
			for (int i = 0; i < cases.size(); i++) {
				writer.write("Case #" + (i+1) + ": ");
				solveCase(cases.get(i), writer);
				if (i+1 != cases.size()) writer.newLine();
			}
			writer.close();
		}
		catch (IOException e) {
			System.err.println("This has gone horribly wrong");
			throw new RuntimeException(e);
		}
	}
	
	private static List<TestCase> readInput(String filename) {
		List<TestCase> cases = new ArrayList<TestCase>();
		try{
			BufferedReader read = new BufferedReader(new FileReader(new File(filename)));
			int numCases = Integer.parseInt(read.readLine());
			for (int i = 0; i < numCases; i++) {
				
				int credit = Integer.parseInt(read.readLine());
				int num = Integer.parseInt(read.readLine());
				int[] items = new int[num];
				String[] itemList = read.readLine().split(" ");
				for (int j = 0; j < num; j++) {
					items[j] = Integer.parseInt(itemList[j]);
				}
				cases.add(new TestCase(credit, num, items));
			}
			read.close();
		}
		catch(IOException e) {
			System.err.println("This has gone horribly wrong");
			throw new RuntimeException(e);
		}
		return cases;
	}
	
	private static void solveCase(TestCase t, BufferedWriter writer) throws IOException {
		for (int i = 0; i < t.numItems-1; i++) {
			for (int j = i+1; j < t.numItems; j++) {
				if (t.items[i] + t.items[j] == t.credit) {
					writer.write(String.format("%d %d", i+1, j+1));
					return;
				}
			}
		}
		throw new RuntimeException("Unable to solve a case");
	}
	
	private static class TestCase {
		
		public final int credit;
		public final int numItems;
		public int[] items;
		
		public TestCase(int c, int n, int[] i) {
			credit = c;
			numItems = n;
			items = i;
		}
	}
}



