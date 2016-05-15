package de.mq.pattern.gof.observer.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import de.mq.pattern.gof.observer.Aircraft;
import de.mq.pattern.gof.observer.Position;

public class GunTableModel extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	private final List<Entry<Aircraft,Position>>  targets = new ArrayList<Entry<Aircraft,Position>>();
	GunTableModel(final Map<Aircraft, Position> targets){
		this.targets.addAll(targets.entrySet());
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		
		return targets.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		
		switch (columnIndex) {
		case 0:
			return targets.get(rowIndex).getKey().id();
		
		case 1:
				return targets.get(rowIndex).getValue().distanceOverGround();
		case 2:
			return targets.get(rowIndex).getValue().distanceToTarget();
		case 3:
			return targets.get(rowIndex).getValue().azimuth();
		case 4:
			return targets.get(rowIndex).getValue().elevation();

		default:
			throw new IllegalArgumentException();
		}
	}
	
	public final List<Entry<Aircraft,Position>> targets() {
		return Collections.unmodifiableList(targets);
	}

}
