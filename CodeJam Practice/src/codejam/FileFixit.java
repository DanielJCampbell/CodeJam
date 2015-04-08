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

public class FileFixit {

	public static void main(String[] args) {
		
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("FFOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			int numCases = Integer.parseInt(read.readLine());
			
			for (int i = 1; i <= numCases; i++) {
				write.write("Case #" + i + ": " + solve(read));
				if (i != numCases) write.newLine();
			}
			
			write.close();
			read.close();
		}
		
		catch (IOException e) { throw new RuntimeException(e); }

	}
	
	public static long solve(BufferedReader read) throws IOException {
		Set<String> dirs = new HashSet<String>();
		String[] params = read.readLine().split(" ");
		int dirSize = Integer.parseInt(params[0]);
		int numPaths = Integer.parseInt(params[1]);
		
		for (int i = 0; i < dirSize; i++) {
			dirs.add(read.readLine());
		}
		
		long result = 0;
		
		for (int i = 0; i < numPaths; i++) {
			String path = read.readLine();
			while (!path.isEmpty()) {
				if (dirs.contains(path)) break;
				result++;
				dirs.add(path);
				path = path.substring(0, path.lastIndexOf('/'));
			}
		}
		
		return result;
	}
}
