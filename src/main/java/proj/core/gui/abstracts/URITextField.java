package proj.core.gui.abstracts;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class URITextField extends JTextField implements TextSettable {
	
	public URITextField() {
		super();
	} 
	
	public URITextField(String text) {
		super(text);
	}
}
