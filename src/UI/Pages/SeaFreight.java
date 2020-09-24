package UI.Pages;

import javax.swing.*;

import UI.Layouts.QueryLayout;

@SuppressWarnings("serial")
public class SeaFreight extends QueryLayout {

	public SeaFreight() {
		titleLabel.setText("Sea Freight");
		addTitleLabel.setText("Create a Sea Freight Record");
		modifyTitleLabel.setText("Modify a Sea Freight Record");
	}

	@Override
	protected void setDatabaseView(JTable table) {
		
	}

	@Override
	protected void searchFunction(String query) {
		
	}

	@Override
	protected void setAddPage(JPanel addView) {
		
	}

	@Override
	protected void setModifyPage(JPanel modifyView) {
		
	}

	@Override
	protected void deleteFunction() {
		
	}
}
