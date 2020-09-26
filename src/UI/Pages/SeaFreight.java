package UI.Pages;

import javax.swing.*;
import javax.swing.table.TableModel;

import UI.Components.QueryLayout;

@SuppressWarnings("serial")
public class SeaFreight extends QueryLayout {

	public SeaFreight() {
		setTitles("Sea Freight", "Create a Sea Freight Record", "Modify a Sea Freight Record");
	}

	@Override
	protected TableModel setTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void searchFunction(String query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected JPanel setAddPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected JPanel setModifyPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteFunction(Object selectedRowValue) {
		// TODO Auto-generated method stub
		
	}
}
