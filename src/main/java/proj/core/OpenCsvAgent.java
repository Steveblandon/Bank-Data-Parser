package proj.core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class OpenCsvAgent {
	
	public static <T> void write(String filename, Class<T> cls, List<T> records) {
		try {
			writeRecordsToCsvFile(filename, cls, records);
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static <T> List<T> read(String filename, Class<T> cls) {
		List<T> records = new ArrayList<>();
		try {
			records = readRecordsFromCsvFile(filename, cls);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
	
	
	private static <T> void writeRecordsToCsvFile(String filename, Class<T> cls, List<T> records) 
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		Writer writer = new FileWriter(filename);
		CustomOrderMappingStrategy<T> mappingStrategy = new CustomOrderMappingStrategy<T>(cls);
		StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
				.withMappingStrategy(mappingStrategy)
				.build();
		beanToCsv.write(records);
		writer.close();
	}
	
	
	private static <T> List<T> readRecordsFromCsvFile(String filename, Class<T> cls) 
			throws URISyntaxException, IllegalStateException, IOException {
		URI resourceID = ClassLoader.getSystemResource(filename).toURI();
		String filePath = Paths.get(resourceID).toString();
		Reader reader = new FileReader(filePath);
		List<T> records = new CsvToBeanBuilder<T>(reader)
				.withType(cls)
				.build()
				.parse();
		reader.close();
		return records;
	}
}
