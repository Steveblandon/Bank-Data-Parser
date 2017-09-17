package proj.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	public static void main (String[] args)  {
		final String TRANSACTIONS_FILENAME = "transactions_2017aug.csv";
		final String PARSED_TRANSACTIONS_FILENAME = "transactions_PARSED_2017aug.csv";
		final String IDENTIFIERS_FILENAME = "identifiers.csv";
		final String GENERIC_ACCOUNT = "debit";
		
		List<Transaction> transactions = readCsvFile(TRANSACTIONS_FILENAME, Transaction.class);
		List<Identifier> identifiers = readCsvFile(IDENTIFIERS_FILENAME, Identifier.class);
		sortIdentifiers(identifiers);
		List<ParsedTransaction> parsedTransactions = new ArrayList<>();
		
		//iterate through list of transactions, get each transaction's description, search for a match on identifiers
		//NOTE: certain transactions have a varying number (like lyft, so do stores to identify different branches).
		//therefore, must come up with a better way for the user to identify such discrepancies.
		//writing an algorithm to remove the number wont work as certain classification like ally bank transfers
		//are identifiable via account numbers.
		Identifier identifier = new Identifier();
		ParsedTransaction parsedTransaction = null;
		for (Transaction transaction : transactions) {
			identifier = matchTransaction(identifiers, transaction);
			parsedTransaction = new ParsedTransaction();
			parsedTransaction.setNotes("");
			parsedTransaction.setAccount(GENERIC_ACCOUNT);
			parsedTransaction.setAmount(transaction.getAmount());
			parsedTransaction.setDate(transaction.getDate());
			parsedTransaction.setDescription(identifier.getDescriptor());
			parsedTransaction.setCategory(identifier.getCategory());
			parsedTransaction.setType(identifier.getType());
			parsedTransactions.add(parsedTransaction);
		}
		
		writeToCsvFile(PARSED_TRANSACTIONS_FILENAME, ParsedTransaction.class, parsedTransactions);
	}
	
	
	public static void sortIdentifiers(List<Identifier> identifiers){
		Collections.sort(identifiers, new Comparator<Identifier>(){

			@Override
			public int compare(Identifier currentIdentifier, Identifier nextIdentifier) {
				String firstIdentification = currentIdentifier.getId().toUpperCase();
				String secondIdentification = nextIdentifier.getId().toUpperCase(); 
				return firstIdentification.compareTo(secondIdentification);
			}
			
		});
	}
	
	
	public static <T> void writeToCsvFile(String filename, Class<T> cls, List<T> content) {
		try {
			Writer writer = new FileWriter(filename);
			StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
					.withMappingStrategy(new CustomOrderMappingStrategy<T>(cls)).build();
			beanToCsv.write(content);
			writer.close();
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static <T> List<T> readCsvFile(String filename, Class<T> cls) {
		List<T> records = new ArrayList<>();
		try {
			String csvPath = Paths.get(ClassLoader.getSystemResource(filename).toURI()).toString();
			records = new CsvToBeanBuilder<T>(new FileReader(csvPath))
									.withType(cls).build().parse(); 
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return records;
	}
	
	
	public static Identifier matchTransaction(List<Identifier> identifiers, Transaction transaction) {
		int low = 0;
		int mid = 0;
		int high = identifiers.size() - 1;
		int comparisons = 0;
		Identifier identifier = null;
		String description = transaction.getDescription().toUpperCase();
		String identification = "";
		
		while (low <= high) {
			mid = (low + high) / 2;
			comparisons++;
			identifier = identifiers.get(mid);
			identification = identifier.getId().toUpperCase();
			if (description.compareTo(identification) == 0 || description.contains(identification)) {
				return identifier;
			}
			else if (description.compareTo(identification) > 0) {
				low = mid + 1;
			}
			else {
				high = mid - 1;
			}
		}
		identifier = new Identifier();		//return default identifier instead?
		return identifier;
	}
}
