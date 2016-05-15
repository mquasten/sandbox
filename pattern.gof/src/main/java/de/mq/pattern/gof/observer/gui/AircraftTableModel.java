package de.mq.pattern.gof.observer.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.mq.pattern.gof.observer.Aircraft;

public class AircraftTableModel extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	
	private final List<Aircraft> aircraftList; // = new ArrayList<Aircraft>();

	AircraftTableModel(List<Aircraft> aircraftList) {
		this.aircraftList=aircraftList;
	}
	
	
	
	@Override
	public int getColumnCount() {
		
		return 4;
	}

	@Override
	public int getRowCount() {
		 return aircraftList.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		
		
		
		final Aircraft aircraft = aircraftList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return aircraft.id();
		case 1: 
			return aircraft.location().latitude();
		case 2: 
			return aircraft.location().longitude();
		case 3: 
			return aircraft.location().altitude();
		default:
			throw new IllegalArgumentException("ColumIndex " + columnIndex + " out of bounds");
		}
	}

	
	

	
	public final void assign(final Aircraft aircraft ) {
		aircraftList.add(aircraft);
	}
	
	public final List<Aircraft> aircrafts() {
		return aircraftList;
	}

}
