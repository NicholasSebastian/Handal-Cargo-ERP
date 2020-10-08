package UI.Components;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.IntStream;

@SuppressWarnings("serial")
public class DatePicker extends JPanel {
	
	private final int
		dateWidth = 20,
		monthWidth = 80,
		yearWidth = 40;
	
	private CustomComboBox date;
	private CustomComboBox month;
	private CustomComboBox year;

	public DatePicker() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setOpaque(false);
		
		LocalDate currentDate = LocalDate.now();
		
		int[] dates = IntStream.rangeClosed(1, 31).toArray();
		Integer[] convertedDates = Arrays.stream(dates).boxed().toArray(Integer[]::new);
		date = new CustomComboBox(convertedDates, dateWidth, 25);
		date.setSelectedItem(currentDate.getDayOfMonth());
		add(date);
		
		add(Box.createHorizontalStrut(5));
		
		month = new CustomComboBox(Month.values(), monthWidth, 25);
		month.setSelectedItem(currentDate.getMonth());
		add(month);
		
		add(Box.createHorizontalStrut(5));
		
		int currentYear = currentDate.getYear();
		int[] years = IntStream.rangeClosed(currentYear - 25, currentYear + 25).toArray();
		Integer[] convertedYears = Arrays.stream(years).boxed().toArray(Integer[]::new);
		year = new CustomComboBox(convertedYears, yearWidth, 25);
		year.setSelectedItem(currentYear);
		add(year);
	}
	
	public Date getDate() {
		Integer selectedDate = (Integer) date.getSelectedItem();
		Month selectedMonth = (Month) month.getSelectedItem();
		Integer selectedYear = (Integer) year.getSelectedItem();
		
		String dateString = String.format("%d-%d-%d", selectedYear, selectedMonth.getValue(), selectedDate);
		return Date.valueOf(dateString);
	}
}
