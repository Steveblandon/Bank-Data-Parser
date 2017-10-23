package proj.core.testDataHelpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class SampleDataCodeGenerator {

	public static void main(String[] args) throws URISyntaxException, IOException {
		String testDataFileName = "simpletransactionTestData.CSV";
		URI resourceID = ClassLoader.getSystemResource(testDataFileName).toURI();
		String filePath = Paths.get(resourceID).toString();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		String line = "";
		String[] values = {};
		while(line != null) {
			line = reader.readLine();
			values = line.split(",");
			System.out.print("expectedTransactions.add(new Transaction(df.parse(\"" + values[0]);
			System.out.print("\"), \"" + values[1]);
			System.out.print("\", " + values[2] + "));\n");
		}
		reader.close();
	}

}
