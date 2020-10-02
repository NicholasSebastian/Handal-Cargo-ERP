package UI.Components;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NumberField extends JTextField {

	@Override
	public void processKeyEvent(KeyEvent e) {
		if (Character.isDigit(e.getKeyChar()) || // numbers
			e.getKeyChar() == '.' ||	// periods
			e.getKeyCode() == 8 || 		// backspaces
			e.getKeyCode() == 127 ||	// delete
			e.getKeyCode() == 37 ||		// left arrow
			e.getKeyCode() == 39		// right arrow
		) {
			super.processKeyEvent(e);
		}
		e.consume();
		return;
	}
	
	public float getValue() {
		String text = getText();
		if (!text.isEmpty()) {
			return Float.parseFloat(text);
		}
		return 0;
	}
}
