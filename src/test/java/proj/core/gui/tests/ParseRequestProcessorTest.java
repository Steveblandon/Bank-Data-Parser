package proj.core.gui.tests;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import org.junit.Before;
import org.junit.Test;

import proj.core.gui.ParseRequestProcessor;
import proj.core.gui.abstracts.StatusLabel;
import proj.core.gui.abstracts.TextSettable;
import proj.core.gui.abstracts.TextSubscriber;
import proj.core.gui.abstracts.URITextField;
import proj.core.mockups.ParseRequestProcessorMock;
import proj.core.utils.PropertiesUtils;

public class ParseRequestProcessorTest {

	public static final String CONFIG_FILE = "testConfig.properties";
	public static final String ACCOUNT_PROP = "ACCOUNT";
	private static final String TRANSACTIONS_URI = "TRANSACTIONS";
	private static final String PARSED_TRANSACTIONS_URI = "PARSED_TRANSACTIONS";
	private static final String IDENTIFIERS_URI = "IDENTIFIERS";
	public static Properties properties = PropertiesUtils.loadProperties(CONFIG_FILE);
	private JTextComponent accountTextField;
	private JTextComponent transactionsTextField;
	private JTextComponent identifiersTextField;
	private JLabel statusLabel;
	private ParseRequestProcessor parseRequestProcessor;

	@Before
	public void setUp() throws Exception {
		accountTextField = new URITextField(properties.getProperty(ACCOUNT_PROP));
		transactionsTextField = new URITextField(properties.getProperty(TRANSACTIONS_URI));
		identifiersTextField = new URITextField(properties.getProperty(IDENTIFIERS_URI));
		statusLabel = new StatusLabel("test-randomNumber:" + Math.random());
		parseRequestProcessor = new ParseRequestProcessorMock(accountTextField, transactionsTextField, identifiersTextField)
				.setSubscriber(new TextSubscriber((TextSettable) statusLabel));
	}

	@Test
	public void testSubscriptionWithStatusLabel() {
		String preClickLabelText = statusLabel.getText();
		parseRequestProcessor.mouseClicked(new MouseEvent(new JButton(), 0, 0, 0, 0, 0, 0, false));
		String postClickLabelText = statusLabel.getText();
		
		assertNotSame(preClickLabelText, postClickLabelText);	//if not the same then the subsciption update went through
	}
	

	@Test	//note: the issue with properties file not updating is probably that we're not caling store().
	// then however saveConfig() is not easily testable as the ParseRequestProcessor calls on main's config.properties, but
	// for the test to succeed we would need to call on testConfig.properties. Also, don't forget to reset and testConfig.properties
	// back to preset defaults.
	public void testAccountPropChangeOnMouseClicked() {
		String preChangeAccountValue = properties.getProperty(ACCOUNT_PROP);
		String newAccountValue = "test acc - " + Math.random();
		accountTextField.setText(newAccountValue);
		parseRequestProcessor.mouseClicked(new MouseEvent(new JButton(), 0, 0, 0, 0, 0, 0, false));
		String postChangeAccountValue = PropertiesUtils.loadProperties(CONFIG_FILE).getProperty(ACCOUNT_PROP);
		
		assertNotSame(preChangeAccountValue, postChangeAccountValue);
	}
}
