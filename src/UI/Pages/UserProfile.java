package UI.Pages;

import java.awt.*;
import javax.swing.*;

import UI.Home;

// Feature: Change user password here.
// Make page responsive.

@SuppressWarnings("serial")
public class UserProfile extends JPanel {
	
	public UserProfile() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		
		JLabel title = new JLabel("User Profile");
		title.setFont(new Font("Arial", Font.BOLD, 24));
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(1, 2));
		content.setMaximumSize(new Dimension(750, 420));
		content.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		String markupLeft = String.format(
			"<html>"
			+ "<b>Username</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "<b>Group</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "<b>Status</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "<b>Name</b><br>"
			+ "<font size=+1>%s</font><br><br>"
			+ "<b>Gender</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "<b>Date of Employment</b><br>"
			+ "<font size=+1>%s</font><br><br>"
			+ "</html>", 
			Home.account.username, 
			Home.account.group,
			Home.account.active ? "Active" : "Inactive",
			Home.account.name,
			Home.account.sex ? "Female" : "Male",
			Home.account.employmentDate.toString()
		);
		
		String markupRight = String.format(
			"<html>"
			+ "<b>Address</b><br>"
			+ "<font size=+1>%s<br>%s, %s</font><br><br>"
			+ "<b>Home Number</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "<b>Mobile Number</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "<b>Birth Place</b><br>"
			+ "<font size=+1>%s</font><br><br>"
			+ "<b>Birthday</b><br>"
			+ "<font size=+1>%s</font><br><br>"
			+ "<b>Religion</b><br>"
			+ "<font size=+2>%s</font><br><br>"
			+ "</html>", 
			Home.account.address.address, 
			Home.account.address.kelurahan, 
			Home.account.address.city,
			Home.account.phone,
			Home.account.mobile,
			Home.account.birthplace,
			Home.account.birthday,
			Home.account.religion
		);
		
		content.add(new JLabel(markupLeft));
		content.add(new JLabel(markupRight));
		
		add(title, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
	}
}
