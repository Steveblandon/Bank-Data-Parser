package proj.core.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OpenCsvAgentTest.class, ParsedTransactionBuilderTest.class, ParsedTransactionTest.class,
		TransactionParserTest.class })
public class FullTest {

}
