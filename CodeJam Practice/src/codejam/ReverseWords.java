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

public class ReverseWords {

	public static void main(String[] args) {
		try{
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("RWOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			int numCases = Integer.parseInt(read.readLine());
			for (int i = 0; i < numCases; i++) {
				write.write("Case #" + (i+1) + ": ");
				List<String> words = new ArrayList<String>();
				for (String s : read.readLine().split(" ")) {
					words.add(0, s);
				}
				boolean first = true;
				for (String s : words) {
					if (!first) write.write(" ");
					first = false;
					write.write(s);
				}
				if (i+1 != numCases) write.newLine();
			}
			read.close();
			write.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
