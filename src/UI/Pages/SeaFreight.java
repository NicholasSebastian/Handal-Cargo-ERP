package UI.Pages;

import javax.swing.*;

import UI.Components.QueryLayout;

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
		System.out.println(query);
	}

	@Override
	protected void setAddPage(JPanel addContent) {
		
	}

	@Override
	protected void setModifyPage(JPanel modifyContent) {
		
	}

	@Override
	protected void deleteFunction(Object selectedRowValue) {
		
	}
}
