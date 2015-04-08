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

public class RopeIntranet {

	public static void main(String[] args) {
		
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("RIOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			List<TestCase> cases = readCases(read);
			
			for (int i = 1; i <= cases.size(); i++) {
				write.write("Case #" + i + ": " + cases.get(i-1).solve());
				if (i != cases.size()) write.newLine();
			}
			read.close();
			write.close();
		}
		catch (IOException e) { throw new RuntimeException(e); }

	}
	
	private static List<TestCase> readCases(BufferedReader read) throws IOException {
		List<TestCase> cases = new ArrayList<TestCase>();
		int numCases = Integer.parseInt(read.readLine());
		
		for (int i = 0; i < numCases; i++) {
			int numWires = Integer.parseInt(read.readLine());
			int[] left = new int[numWires];
			int[] right = new int[numWires];
			
			for (int j = 0; j < numWires; j++) {
				String[] wire = read.readLine().split(" ");
				left[j] = Integer.parseInt(wire[0]);
				right[j] = Integer.parseInt(wire[1]);
			}
			cases.add(new TestCase(left, right));
		}
		return cases;
	}
	
	private static class TestCase {
		
		private int[] leftWires;
		private int[] rightWires;
		
		public TestCase(int[] left, int[] right) {
			leftWires = left;
			rightWires = right;
		}
		
		//An intersection occurs between two wires if the start point of one is lower than the other,
		//and the end point of that same one is higher than the other
		public long solve() {
			long result = 0;
			
			for (int i = 0; i < leftWires.length-1; i++) {
				for (int j = i+1; j < leftWires.length; j++) {
					if (leftWires[i] < leftWires[j] && rightWires[i] > rightWires[j])
						result++;
					if (leftWires[i] > leftWires[j] && rightWires[i] < rightWires[j])
						result++;
				}
			}
			return result;
		}
	}

}
