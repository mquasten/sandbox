package de.mq.pattern.gof.observer.gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import de.mq.pattern.gof.command.GunControl;
import de.mq.pattern.gof.command.GunControlMacroFactoryImpl;
import de.mq.pattern.gof.observer.Aircraft;
import de.mq.pattern.gof.observer.AircraftObserverImpl;
import de.mq.pattern.gof.observer.AircraftSubjectImpl;
import de.mq.pattern.gof.observer.Angle;
import de.mq.pattern.gof.observer.LocationImpl;
import de.mq.pattern.gof.observer.Mig29;
import de.mq.pattern.gof.observer.Observer;
import de.mq.pattern.gof.observer.Position;
import de.mq.pattern.gof.observer.PositionCalculatorImpl;
import de.mq.pattern.gof.observer.Subject;
import de.mq.pattern.gof.observer.TargetsAware;



public class DemoFrame extends JFrame{

	private static final int NO_SELECTION = -1;
	private List<String> commands = new ArrayList<String>();
	
	private Angle towerAngel= new Angle();
	private Angle gunElevation= new Angle();
	
	private GunControl gunControl = new GunControlMacroFactoryImpl().create(commands, towerAngel, gunElevation);
	
	
	private static final String[] COLS_RADAR = new String[] {"Aircraft","Latitude", "Longitude" , "Alititude"};
	private static final String[] COLS_GUN_TABLE = new String[] {"Aircraft","DistanceOverGround", "DistanceToTarget" ,"Azimuth" , "Elevation"};
	private static final long serialVersionUID = 1L;
	
	private final JPanel gunButtonPanel = new JPanel();
	private final JPanel radarButtonPanel = new JPanel();

	
	private final JButton addButton = new JButton("hinzufügen");
	private final JButton changeButton =new JButton("ändern");
	
	private final JButton fireButton = new JButton("bekämpfen");
	
	
	
	private final JPanel tablePanel = new JPanel(new GridLayout(3,1));
	
	private final JPanel radarPanel = new JPanel(new BorderLayout());
	private final JPanel gunPanel = new JPanel(new BorderLayout());
	
	private final JPanel hydraulicPanel = new JPanel(new BorderLayout());
	
	
	
	private final JTable table = new JTable(new AircraftTableModel( new ArrayList<Aircraft>()));
	
	private final JTable hardwareControlTable = new JTable(new HardwareControlTableModel(commands));
	
	private final Observer observer = new AircraftObserverImpl(new PositionCalculatorImpl(),new LocationImpl(new Number[] { 51, 19, 2 }, new Number[] { 6, 34, 16 }),100e3);
	
	private final JTable gunTable = new JTable(new GunTableModel(((TargetsAware)observer).targets()));

	private final JLabel towerPosition = new JLabel("Angle tower to north: " + towerAngel.toString() );
	private final JLabel gunPosition =  new JLabel("Elevation gun: " + gunElevation.toString());
	
	
	DemoFrame() {
		initGUI();
	}

	
	
	private void initGUI() {
			
				table.getSelectionModel().addListSelectionListener(new EnableComponentsSelectionListenerImpl(changeButton));
				gunTable.getSelectionModel().addListSelectionListener(new EnableComponentsSelectionListenerImpl(fireButton));
				initTable(table, NO_SELECTION,  COLS_RADAR);
				initTable(gunTable, NO_SELECTION, COLS_GUN_TABLE);
				initTable(hardwareControlTable, NO_SELECTION, new String[] {"Hardware-Actions" }) ;
				hardwareControlTable.setEnabled(false);
			   this.setTitle("USS Erich Gamma");
		     
			   this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
				addButton.addActionListener(new AddAircraftActionListener());
			
				
				changeButton.addActionListener(new ChangeAircraftActionListener());
		
				fireButton.addActionListener(new FireActionListener());
				
				buttonPanel(gunButtonPanel,  towerPosition, gunPosition, fireButton);
				buttonPanel(radarButtonPanel, addButton, changeButton);
				
				radarPanel.add(headLinePanel("Radar"), BorderLayout.NORTH);
				radarPanel.add(new JScrollPane(table), BorderLayout.CENTER);
				radarPanel.add(radarButtonPanel, BorderLayout.SOUTH);
				
				gunPanel.add(headLinePanel("Gun"), BorderLayout.NORTH);
				gunPanel.add(new JScrollPane(gunTable), BorderLayout.CENTER);
				gunPanel.add(gunButtonPanel, BorderLayout.SOUTH);
				
				
				tablePanel.add(gunPanel);
				hydraulicPanel.add(headLinePanel("Hydraulic"), BorderLayout.NORTH);
				hydraulicPanel.add(new JScrollPane(hardwareControlTable),BorderLayout.CENTER);
				
				tablePanel.add( hydraulicPanel/*new JScrollPane(hardwareControlTable), new FlowLayout()*/);
				
				tablePanel.add(radarPanel);
				
				 
				this.getContentPane().add(tablePanel,BorderLayout.CENTER);
			  
				
				this.setSize(800, 800);
			   center(this);
			   this.setVisible(true);
	}



	private JPanel headLinePanel(final String text) {
		final JPanel panel = new  JPanel();
		final JLabel label = new JLabel(text);
		label.setFont(new Font(label.getFont().getFontName() , Font.BOLD , 24 ));
		panel.add(label, new FlowLayout());
		return panel;
	}



	private void buttonPanel(final JPanel panel, final Component ... buttons) {
		
		for(Component button : buttons){
			panel.add(button);
		}
	
	}

	private void initTable(JTable table, int selectedRow,  String[] cols) {
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
	
		int i=0;
		for(Enumeration<TableColumn> e = table.getColumnModel().getColumns(); e.hasMoreElements(); i++) {
			TableColumn col = e.nextElement();
			col.setHeaderValue(cols[i]);
			col.setResizable(false);
		}
		
		if( selectedRow >=0 ){
		   table.setRowSelectionInterval(selectedRow, selectedRow);
		}
		
	}
	
	private final void center(Frame frame) {	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	class AddAircraftActionListener implements ActionListener {

		@Override
		public final void actionPerformed(final ActionEvent e) {
			final Subject subject = new AircraftSubjectImpl(new Mig29((int) Math.round(Math.random()*1000)));
			((AircraftTableModel)table.getModel()).assign((Aircraft) subject);
			observer.assign(subject);
		
			table.updateUI();
		}
		
	}
	
	class ChangeAircraftActionListener implements ActionListener {

		@Override
		public final  void actionPerformed(final ActionEvent e) {
		
			if ( ((AircraftTableModel)table.getModel()).aircrafts().isEmpty()) {
				return; 
			}
			
			if( table.getSelectedRow() < 0){
				return;
			}
			final Locations result = (Locations) JOptionPane.showInputDialog(null, "Message", "Title",JOptionPane.QUESTION_MESSAGE, null, Locations.values(), Locations.DUE);
			if( result == null){
				return;
			}
			
		
			final Aircraft aircraft = ((AircraftTableModel)table.getModel()).aircrafts().get(table.getSelectedRow());
			
			aircraft.assignLocation(result.location());
			table.updateUI();
			Entry<Aircraft,Position> selectedTarget=null;
			if( gunTable.getSelectedRow()>=0){
				selectedTarget=((GunTableModel) gunTable.getModel()).targets().get(gunTable.getSelectedRow());
			}
			gunTable.setModel(new GunTableModel(((TargetsAware)observer).targets()));
			
			int index =((GunTableModel) gunTable.getModel()).targets().indexOf(selectedTarget);
			
			initTable(gunTable,index,COLS_GUN_TABLE);
			
			if( index < 0 ){
				fireButton.setEnabled(false);
			}
			
			
		}
		
	}
	
   
	
	class FireActionListener implements ActionListener {

		@Override
		public final void actionPerformed(ActionEvent e) {
			if( gunTable.getSelectedRow() < 0 ) {
				return;
			}
			
			commands.clear();
			
			gunControl.execute(((GunTableModel)gunTable.getModel()).targets().get(gunTable.getSelectedRow()).getValue());
			
			hardwareControlTable.updateUI();
			
			
			final Subject subject = (Subject) ((GunTableModel)gunTable.getModel()).targets().get(gunTable.getSelectedRow()).getKey();
			observer.unassign(subject);
			gunTable.setModel(new GunTableModel(((TargetsAware)observer).targets()));
			initTable(gunTable, NO_SELECTION, COLS_GUN_TABLE);
			
			
			Aircraft  selectedAircraft = null;
			if( table.getSelectedRow() >= 0 )	{
			   selectedAircraft=((AircraftTableModel)table.getModel()).aircrafts().get(table.getSelectedRow());
			}
			((AircraftTableModel)table.getModel()).aircrafts().remove(subject);
			
			fireButton.setEnabled(false);
			
			changeButton.setEnabled(false);
			table.setModel(new AircraftTableModel(((AircraftTableModel)table.getModel()).aircrafts()));
			int index = ((AircraftTableModel)table.getModel()).aircrafts().indexOf(selectedAircraft);
			initTable(table, index,COLS_RADAR);	
			
			if( index < 0 ){
				changeButton.setEnabled(false);
			}
			
			gunPosition.setText("Elevation gun: " + gunElevation);
			towerPosition.setText("Angle tower to north: " + towerAngel);
			
		
		}
		
	}
	
  
	class EnableComponentsSelectionListenerImpl implements ListSelectionListener{


		private final Component[] components  ; 
		
		EnableComponentsSelectionListenerImpl(final Component... components) {
			this.components=components;
			for(Component component : components){
			 component.setEnabled(false);
			}
		}
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			for(Component component : components){
				 component.setEnabled(true);
				}
			
		}
		
	}
	
	

}
