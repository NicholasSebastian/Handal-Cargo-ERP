package UI.Pages;

import javax.swing.*;

import UI.Components.QueryLayout;

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
