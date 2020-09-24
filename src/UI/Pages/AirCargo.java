package UI.Pages;

import javax.swing.*;

import UI.Layouts.QueryLayout;

@SuppressWarnings("serial")
public class AirCargo extends QueryLayout {
	
	public AirCargo() {
		titleLabel.setText("Air Cargo");
		addTitleLabel.setText("Create an Air Cargo Record");
		modifyTitleLabel.setText("Modify an Air Cargo Record");
	}

	@Override
	protected void setDatabaseView(JTable table) {
		
	}

	@Override
	protected void searchFunction(String query) {
		
	}

	@Override
	protected void setAddPage(JPanel addView) {
		addView.setOpaque(false);
		
	}

	@Override
	protected void setModifyPage(JPanel modifyView) {
		modifyView.setOpaque(false);
		
	}

	@Override
	protected void deleteFunction() {
		
	}
}
