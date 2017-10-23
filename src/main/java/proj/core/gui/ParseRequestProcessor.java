package proj.core.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.List;
import javax.swing.text.JTextComponent;

import proj.core.IdentifierRegistry;
import proj.core.OpenCsvAgent;
import proj.core.TransactionParser;
import proj.core.beans.Identifier;
import proj.core.beans.ParsedTransaction;
import proj.core.beans.Transaction;
import proj.core.gui.abstracts.Publisher;
import proj.core.gui.abstracts.Subscriber;

public class ParseRequestProcessor extends MouseAdapter implements Publisher {

	private String DEFAULT_WRITE_FILENAME = "parsedTransactions";
	private String DEFAULT_FILE_EXT = ".csv";
	private JTextComponent accountNameComp;
	private JTextComponent transactionsURIComp;
	private JTextComponent identifiersURIComp;
	private List<Transaction> transactions;
	private List<Identifier> identifiers;
	private IdentifierRegistry identifierRegistry;
	private Subscriber subscriber;
	
	public ParseRequestProcessor(JTextComponent accountNameComp, JTextComponent transactionsURIComp, JTextComponent identifiersURIComp) {
		this.accountNameComp = accountNameComp;
		this.transactionsURIComp = transactionsURIComp;
		this.identifiersURIComp = identifiersURIComp;
	}

	@Override
	public ParseRequestProcessor setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
		return this;
	}
	
	@Override
    public void mouseClicked(MouseEvent e) {
		loadData();
		TransactionParser parser = new TransactionParser(identifierRegistry, accountNameComp.getText());
		List<ParsedTransaction> parsedTransactions = parser.parseAll(transactions); 
		subscriber.update(parser.getSuccessRatioMessage());
		String writeFileName = getTimestampedFileName();
		OpenCsvAgent.write(writeFileName, ParsedTransaction.class, parsedTransactions);
	}
	
	private void loadData() {
		transactions = OpenCsvAgent.read(transactionsURIComp.getText(), Transaction.class);
		identifiers = OpenCsvAgent.read(identifiersURIComp.getText(), Identifier.class);
		identifierRegistry = new IdentifierRegistry(identifiers);
	}
	
	private String getTimestampedFileName() {
		return new StringBuilder()
				.append(DEFAULT_WRITE_FILENAME)
				.append(Calendar.YEAR)
				.append(Calendar.MONTH)
				.append(Calendar.DAY_OF_MONTH)
				.append('_')
				.append(Calendar.HOUR)
				.append(Calendar.MINUTE)
				.append(Calendar.SECOND)
				.append(DEFAULT_FILE_EXT)
				.toString();
	}
}
