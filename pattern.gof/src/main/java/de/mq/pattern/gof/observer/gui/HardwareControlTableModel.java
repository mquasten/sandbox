package de.mq.pattern.gof.observer.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class HardwareControlTableModel extends   AbstractTableModel{


	private static final long serialVersionUID = 1L;
	private final List<String> commands;
	
	HardwareControlTableModel(final List<String> commands){
		this.commands=commands;
	}
	
	
	
	@Override
	public final int getColumnCount() {
		return 1;
	}

	@Override
	public final  int getRowCount() {
		return commands.size();
	}

	@Override
	public final Object getValueAt(int rowIndex, int columnIndex) {
		return commands.get(rowIndex);
	}

}
