package proj.core.mockups;

import javax.swing.text.JTextComponent;

import proj.core.gui.ParseRequestProcessor;
import proj.core.gui.tests.ParseRequestProcessorTest;
import proj.core.utils.PropertiesUtils;

public class ParseRequestProcessorMock extends ParseRequestProcessor {
	
	public ParseRequestProcessorMock(JTextComponent accountNameComp, JTextComponent transactionsURIComp,
			JTextComponent identifiersURIComp) {
		super(accountNameComp, transactionsURIComp, identifiersURIComp);
	}

	
	@Override
	protected void saveConfig() {
		ParseRequestProcessorTest.properties.setProperty(ParseRequestProcessorTest.PROP_ACCOUNT, accountNameComp.getText());
		PropertiesUtils.storeProperties(ParseRequestProcessorTest.properties, ParseRequestProcessorTest.CONFIG_FILE);
	}

}
