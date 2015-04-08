package codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScalarProduct {
	
	public static void main(String[] args) {
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SPOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			List<TestCase> cases = readCases(read);
			
			for (int i = 0; i < cases.size(); i++) {
				write.write("Case #" + (i+1) + ": " + cases.get(i).solve());
				if (i+1 != cases.size()) write.newLine();
			}
			read.close();
			write.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static List<TestCase> readCases(BufferedReader read) throws IOException {
		List<TestCase> cases = new ArrayList<TestCase>();
		int numCases = Integer.parseInt(read.readLine());
		for (int i = 0; i < numCases; i++) {
			int size = Integer.parseInt(read.readLine());
			List<Long> vecA = new ArrayList<Long>(size);
			List<Long> vecB = new ArrayList<Long>(size);
			String[] a = read.readLine().split(" ");
			String[] b = read.readLine().split(" ");
			
			for (int j = 0; j < a.length; j++) {
				vecA.add(Long.parseLong(a[j]));
				vecB.add(Long.parseLong(b[j]));
			}
			cases.add(new TestCase(vecA, vecB));
		}
		return cases;
	}
	
	private static class TestCase {
		
		private List<Long> vecA;
		private List<Long> vecB;
		
		public TestCase(List<Long> a, List<Long> b) {
			vecA = a;
			vecB = b;
		}
		
		//The minimum scalar product will always be the scalar product of one vector in ascending order and the other in descending order
		public long solve() {
			Collections.sort(vecA);
			Collections.sort(vecB, Collections.reverseOrder());
			long result = 0;
			for (int i = 0; i < vecA.size(); i++) {
				result += vecA.get(i) * vecB.get(i);
			}
			return result;
		}
	}
}
