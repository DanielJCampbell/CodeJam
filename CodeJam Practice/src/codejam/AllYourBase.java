package codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class AllYourBase {
	
	//The logic for this one is straight forward - to minimise the value, the base should be equal to the number of distinct characters.
	//And values should be assigned to characters in a way that ensures the most significant digits have the least value
	// i.e. cats turns into 1023, base 4
	public static void main(String[] args) {
		
		try {
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("AYBOutput.txt"), "utf-8"));
			BufferedReader read = new BufferedReader(new FileReader(new File(args[0])));
			
			int numCases = Integer.parseInt(read.readLine());
			
			for (int i = 1; i <= numCases; i++) {
				char[] number = read.readLine().toCharArray();
				StringBuilder result = new StringBuilder();
				HashMap<Character, String> mapping = new HashMap<Character, String>();
				
				for (int j = 0; j < number.length; j++) {
					if (mapping.containsKey(number[j])) {
						result.append(mapping.get(number[j]).charAt(0)); //Works because all the strings are length 1
					}
					else {
						//Special cases for 1st & second unique elements - all elements after can be handled together
						if (mapping.isEmpty()) {
							mapping.put(number[j], "1");
							result.append("1");
						}
						else if (mapping.size() == 1) {
							mapping.put(number[j], "0");
							result.append("0");
						}
						else {
							String num = Integer.toString(mapping.size(), mapping.size()+1);
							mapping.put(number[j], num);
							result.append(num);
						}
					}
				}
				write.write("Case #" + i + ": " + Long.parseLong(result.toString(), Math.max(2, mapping.size())));
				if (i != numCases) write.newLine();
			}
			write.close();
			read.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
