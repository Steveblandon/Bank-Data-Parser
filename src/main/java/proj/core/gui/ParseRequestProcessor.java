package proj.core.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.List;
import javax.swing.text.JTextComponent;

import proj.core.IdentifierRegistry;
import proj.core.Main;
import proj.core.OpenCsvAgent;
import proj.core.TransactionParser;
import proj.core.beans.Identifier;
import proj.core.beans.ParsedTransaction;
import proj.core.beans.Transaction;
import proj.core.gui.abstracts.Publisher;
import proj.core.gui.abstracts.Subscriber;
import proj.core.utils.PropertiesUtils;

public class ParseRequestProcessor extends MouseAdapter implements Publisher {

	protected JTextComponent accountNameComp;
	private JTextComponent transactionsURIComp;
	private JTextComponent identifiersURIComp;
	private List<Transaction> transactions;
	private List<Identifier> identifiers;
	private IdentifierRegistry identifierRegistry;
	private Subscriber subscriber;
	private TransactionParser parser;
	
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
		saveParsedData(parseData());
		subscriber.update(parser.getSuccessRatioMessage());
		saveConfig();
	}
	
	private void loadData() {
		transactions = OpenCsvAgent.read(transactionsURIComp.getText(), Transaction.class);
		identifiers = OpenCsvAgent.read(identifiersURIComp.getText(), Identifier.class);
		identifierRegistry = new IdentifierRegistry(identifiers);
	}
	
	private List<ParsedTransaction> parseData() {
		parser = new TransactionParser(identifierRegistry, accountNameComp.getText());
		List<ParsedTransaction> parsedTransactions = parser.parseAll(transactions); 
		return parsedTransactions;
	}

	private void saveParsedData(List<ParsedTransaction> parsedTransactions) {
		String writeFileName = getTimestampedFileName();
		OpenCsvAgent.write(writeFileName, ParsedTransaction.class, parsedTransactions);
		
	}

	protected void saveConfig() {
		Main.properties.setProperty(Main.PROP_ACCOUNT, accountNameComp.getText());
		Main.properties.setProperty(Main.PROP_IDENTIFIERS, identifiersURIComp.getText());
		Main.properties.setProperty(Main.PROP_TRANSACTIONS, transactionsURIComp.getText());
		PropertiesUtils.storeProperties(Main.properties, Main.CONFIG_FILE);
	}
	
	private String getTimestampedFileName() {
		Calendar currentDate = Calendar.getInstance();
		return new StringBuilder()
				.append(Main.properties.getProperty(Main.PROP_PARSED))
				.append(currentDate.get(Calendar.YEAR))
				.append(currentDate.get(Calendar.MONTH))		//TODO: seems like the wrong month is being printed?
				.append(currentDate.get(Calendar.DAY_OF_MONTH))
				.append('_')
				.append(currentDate.get(Calendar.HOUR))
				.append(currentDate.get(Calendar.MINUTE))
				.append(currentDate.get(Calendar.SECOND))
				.append(Main.properties.getProperty(Main.PROP_PARSED_EXT))
				.toString();
	}
}
