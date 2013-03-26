package edu.wpi.cs.wpisuitetng.modules.RequirementManager.view.overview;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementManager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementManager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementManager.view.ViewEventController;

public class OverviewTable extends JTable
{
	private DefaultTableModel tableModel = null;
	private boolean initialized;
	/**
	 * Sets initial table view
	 * 
	 * @param data	Initial data to fill OverviewTable
	 * @param columnNames	Column headers of OverviewTable
	 */
	public OverviewTable(Object[][] data, String[] columnNames)
	{
		this.tableModel = new DefaultTableModel(columnNames, 0);
		this.setModel(tableModel);
		setFillsViewportHeight(true);
		ViewEventController.getInstance().setOverviewTable(this);
		initialized = false;
	}
	
	/**
	 * updates OverviewTable with the contents of the requirement model
	 * 
	 */
	public void refresh() {
		tableModel.setRowCount(0); //clear the table
		
		List<Requirement> requirements = RequirementModel.getInstance().getRequirements();
		for (int i = 0; i < requirements.size(); i++) {
			Requirement req = requirements.get(i);
			if (!req.isDeleted()) {
				tableModel.addRow(new Object[]{ req.getId(), 
												req.getName(),
												req.getStatus(),
												req.getPriority(),
												req.getRelease() }
				);
			}
		}
			
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(!initialized)
		{
			try 
			{
				GetRequirementsController.getInstance().retrieveRequirements();
				initialized = true;
			}
			catch (Exception e)
			{

			}
		}

		super.paintComponent(g);
	}
}
