package proj.core;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Main {

	private static final String TRANSACTIONS_FILENAME = "transactions_2017aug.csv";
	private static final String PARSED_TRANSACTIONS_FILENAME = "transactions_PARSED_2017aug.csv";
	private static final String IDENTIFIERS_FILENAME = "identifiers.csv";
	
	
	public static void main (String[] args)  {
		List<Transaction> transactions = loadCsvFile(TRANSACTIONS_FILENAME, Transaction.class);
		List<Identifier> identifiers = loadCsvFile(IDENTIFIERS_FILENAME, Identifier.class);
		List<ParsedTransaction> parsedTransactions = new ArrayList<>();
		System.out.println("lists created");
		
		ParsedTransaction sample = new ParsedTransaction();
		sample.setAccount("chase checking");
		sample.setAmount(transactions.get(0).getAmount());
		sample.setCategory("groceries");
		sample.setType("UNKNOWN");
		sample.setDate(transactions.get(0).getDate());
		sample.setDescription(transactions.get(0).getDescription());
		parsedTransactions.add(sample);
		System.out.println("sample transaction created");
		
		try {
			Writer writer = new FileWriter(PARSED_TRANSACTIONS_FILENAME);
			StatefulBeanToCsv<ParsedTransaction> beanToCsv = new StatefulBeanToCsvBuilder<ParsedTransaction>(writer)
					.withMappingStrategy(new CustomOrderMappingStrategy<ParsedTransaction>(ParsedTransaction.class)).build();
			beanToCsv.write(parsedTransactions);
			writer.close();
			System.out.println("parsed data posted");
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> loadCsvFile(String filename, Class<T> cla) {
		try {
			String csvPath = Paths.get(ClassLoader.getSystemResource(filename).toURI()).toString();
			@SuppressWarnings("rawtypes")
			List<T> records = new CsvToBeanBuilder(new FileReader(csvPath))
									.withType(cla).build().parse(); 
			return records;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	//TODO: revise this function (binary search algorithm) to work for identifying records
	public static int identifyTransaction(String[] arr, String key) {
		int low = 0;
		int mid = 0;
		int high = arr.length;
		int comparisons = 0;
		
		while (low < high) {
			mid = high / 2;
			comparisons++;
			if (key.compareTo(arr[mid]) == 0 || key.contains(arr[mid])) {
				System.out.println("comparisons: " + comparisons);
				return mid;
			}
			else if (key.compareTo(arr[mid]) > 0) {
				low = mid + 1;
			}
			else {
				high = mid - 1;
			}
		}
		System.out.println("comparisons: " + comparisons);
		return -1;
	}
}
