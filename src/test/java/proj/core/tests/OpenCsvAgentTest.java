package proj.core.tests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import proj.core.OpenCsvAgent;
import proj.core.beans.Transaction;

public class OpenCsvAgentTest {

	List<Transaction> expectedTransactions;
	
	@Before
	public void setUp() throws Exception {
		expectedTransactions = new ArrayList<>();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		expectedTransactions.add(new Transaction(df.parse("08/18/2017"), "AMAZON MKTPLACE PMTS", -22.23));
		expectedTransactions.add(new Transaction(df.parse("08/13/2017"), "TRADER JOE'S #248 QPS", -40.96));
		expectedTransactions.add(new Transaction(df.parse("08/13/2017"), "LYFT   *RIDE SAT 8AM", -5.32));
		expectedTransactions.add(new Transaction(df.parse("08/11/2017"), "LYFT   *RIDE WED 10PM", -12.2));
		expectedTransactions.add(new Transaction(df.parse("08/06/2017"), "TRADER JOE'S #034  QPS", -13.85));
		expectedTransactions.add(new Transaction(df.parse("08/06/2017"), "RALPHS #0166", -81.43));
		expectedTransactions.add(new Transaction(df.parse("08/05/2017"), "FAT BURGER #104", -16.66));
	}

	@Test
	public void testWrite() {
		OpenCsvAgent.write("writeDataTemplate.CSV", Transaction.class, expectedTransactions);
		List<Transaction> actualTransactions = OpenCsvAgent.read("writeDataTemplate.CSV", Transaction.class);
		
		assertEquals(expectedTransactions, actualTransactions);
	}

	@Test
	public void testRead() {
		List<Transaction> actualTransactions = OpenCsvAgent.read("transactionData.CSV", Transaction.class);
		
		assertEquals(expectedTransactions, actualTransactions);
	}

}
