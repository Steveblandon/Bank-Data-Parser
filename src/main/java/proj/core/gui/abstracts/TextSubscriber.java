package proj.core.gui.abstracts;

public class TextSubscriber implements Subscriber {

	TextSettable textSettable;
	
	public TextSubscriber(TextSettable textSettable) {
		this.textSettable = textSettable;
	}
	
	@Override
	public void update(Object... args) {
		if (args.length < 1) {
			//log error, something should've been passed
			return;
		}
		if (args[0] instanceof String) {
			String status = (String) args[0];
			textSettable.setText(status);
		}
	}
}

