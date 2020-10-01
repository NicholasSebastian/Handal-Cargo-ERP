package UI.Components;

import java.sql.Date;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DatePicker extends JPanel {

	public DatePicker() {
		
	}
	
	public Date getDate() {
		return new Date(System.currentTimeMillis());
	}
}
