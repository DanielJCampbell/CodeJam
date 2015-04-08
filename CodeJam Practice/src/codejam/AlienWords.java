package codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

public class AlienWords {
	
	public static void main(String[] args) {
		Set<String> dict = new HashSet<String>();
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("AWOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			String[] nums = read.readLine().split(" ");
			int numWords = Integer.parseInt(nums[1]);
			int numCases = Integer.parseInt(nums[2]);
			
			for (int i = 0; i < numWords; i++) {
				dict.add(read.readLine());
			}
			
			for (int i = 0; i < numCases; i++) {
				int result = 0;
				String pattern = read.readLine().replaceAll("\\(", "\\[").replaceAll("\\)", "\\]");
				for (String s : dict) {
					if (s.matches(pattern)) result++;
				}
				write.write("Case #" + (i+1) + ": " + result);
				if (i+1 != numCases) write.newLine();
			}
			write.close();
			read.close();
		}
		catch (IOException e) { throw new RuntimeException (e); }
	}
}
