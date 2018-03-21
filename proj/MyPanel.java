package proj;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.print.PrinterException;
import java.util.LinkedList;

public class MyPanel extends JPanel
{
	private int editingRow = -1, selectedRow = -1;
	private Point pEditingRow;
    private final MyJTable table;
	private String[] sEditingRow, columns;
	private final String tableName, // used in update and insert statements
						 timePattern = "Last update:    %s";
	private JLabel timeLabel;
	private boolean isEditable, inserting;
	private final DefaultTableModel model;

	public MyPanel(String[] columns, LinkedList<String[]> rows, String tableName, boolean isEditable)
    {
		this.tableName = tableName;
		this.isEditable = isEditable;
		this.columns = columns;
		String[][] data = new String[rows.size()][columns.length];
		sEditingRow = new String[columns.length];
		for (int i = 0; i < rows.size(); i++) {
			String[] arr = rows.get(i);
			data[i] = arr;
			int n = 0;
			while (n < columns.length)
				System.out.format("%s\t", arr[n++]);
			System.out.format("%n");
		}
		System.out.format("Total %d rows%n", rows.size());
		
		model = new DefaultTableModel(data, columns);
        table = new MyJTable(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(4); // column "id" receives minumum space when space is limited
        // Set size of table
        table.setPreferredScrollableViewportSize(new Dimension(1025, 725));

        // This will resize the height of the table automatically
        // to all data without scrolling.
        table.setFillsViewportHeight(true);

        JScrollPane jps = new JScrollPane(table);
        add(jps);
		timeLabel = new JLabel(String.format(timePattern, Main.connector.getDate()));
		add(timeLabel);
		
		final JPopupMenu menu1 = new JPopupMenu();
		JMenuItem item11 = new JMenuItem("редагувати");
		item11.addActionListener((e) -> {
			editingRow = table.rowAtPoint(pEditingRow);
			for (int i = 0; i < model.getColumnCount(); ++i)
				sEditingRow[i] = (String) model.getValueAt(editingRow, i);
		} );
		
		JMenuItem item12 = new JMenuItem("видалити");
		item12.addActionListener((e) -> { delete(selectedRow); } );
		
		JMenuItem item14 = new JMenuItem("друкувати таблицю");
		item14.addActionListener((e) -> {
			try {
				table.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		} );
		
		JMenuItem item13 = new JMenuItem("додати новий запис");
		item13.addActionListener((e) -> {
			inserting = true;
			model.addRow(new String[] {"(id)"});
			table.changeSelection( model.getRowCount() - 1, 1, false, false);
		} );
		
		final JPopupMenu menu3 = new JPopupMenu();
		JMenuItem item31 = new JMenuItem("завершити додання нового запису");
		item31.addActionListener((e) -> {
			insert(model.getRowCount()-1);
			inserting = false;
			editingRow = -1;
		} );
		menu3.add(item31);

		final JPopupMenu menu2 = new JPopupMenu();
		JMenuItem item21 = new JMenuItem("завершити редагування");
		item21.addActionListener((e) -> { update(editingRow); editingRow = -1;} );
		menu2.add(item21);
		if (this.isEditable) {
			menu1.add(item11);
			menu1.add(item12);
			menu1.add(item13);
			menu1.add(item14);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX(),
						y = e.getY();
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (editingRow == -1 && !inserting) {
							pEditingRow = new Point(x, y);
							table.changeSelection( table.rowAtPoint(pEditingRow), 1, false, false);
							menu1.show(e.getComponent(), x, y);
						} else if (inserting) {
							//editingRow = model.getRowCount() - 1;
							menu3.show(e.getComponent(), x, y);
						} else {
							menu2.show(e.getComponent(), x, y);
						}
					}
					if (editingRow != -1)
						table.changeSelection( editingRow, editingRow + 1, false, false);
					if (inserting)
						table.changeSelection( model.getRowCount() - 1, 1, false, false);
					selectedRow = table.rowAtPoint( new Point(x, y) );
				}
			});
		} else {
			menu1.add(item14);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX(),
						y = e.getY();
					if (e.getButton() == MouseEvent.BUTTON3)
							menu1.show(e.getComponent(), x, y);
				}
			});
		}
    }
	
	private void delete(final int row)
	{
		//System.out.format("DELETE SELECTED ROW: %d%n", row);
		int status = getConnector().delete(tableName, (String) model.getValueAt(row, 0));
		if (status == -1) {
			JOptionPane.showMessageDialog(null,
				"Не вдалося видалити запис",
				"Помилка",
				JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			table.clearSelection();
			JOptionPane.showMessageDialog(null,
				"Запис видалено",
				"Успіх",
				JOptionPane.INFORMATION_MESSAGE);
			model.removeRow(row);
			return;
		}
	}

	private void update(final int row)
	{
		//System.out.format("UPDATE SELECTED ROW: %d%n", row);
		String[] currentRow = new String[model.getColumnCount()];
		for (int i = 0; i < currentRow.length-1; ++i)
			currentRow[i] = (String) model.getValueAt(row, i + 1);
		currentRow[currentRow.length-1] = (String) model.getValueAt(row, 0); // placing id (unique identifier) at the end of array
		int status = getConnector().update(tableName, currentRow); 
		if (status == -1) {
			JOptionPane.showMessageDialog(null,
				"Не вдалося редагувати запис",
				"Помилка",
				JOptionPane.ERROR_MESSAGE);
				 // in case of failure we refresh table with old data!!!
				for (int i = 0; i < table.getColumnCount(); ++i) {
					String value = sEditingRow[i];
					model.setValueAt(value, row, i);
					//System.out.format("%s%n", value);
				}
		} else {
			JOptionPane.showMessageDialog(null,
				"Запис було редаговано",
				"Успіх",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void insert(final int row) {
		System.out.format("INSERT SELECTED ROW: %d%n", row);
		String[] currentRow = new String[model.getColumnCount() - 1];
		for (int i = 0; i < currentRow.length; ++i)
			currentRow[i] = (String) model.getValueAt(row, i+1);
		int status = getConnector().insert(tableName, currentRow);
		if (status == -1) {
			JOptionPane.showMessageDialog(null,
				"Не вдалося додати новий запис",
				"Помилка",
				JOptionPane.ERROR_MESSAGE);
				 // in case of failure we refresh table with old data!!!
				model.removeRow(row);
		} else {
			JOptionPane.showMessageDialog(null,
				"Новий запис було додано",
				"Успіх",
				JOptionPane.INFORMATION_MESSAGE);
				//todo refresh row (all rows) from the database!!!
				refreshModel();
		}
	}
	
	private void refreshModel() {
		while (model.getRowCount() > 0)
			model.removeRow(0);
		LinkedList<String[]> rows = getConnector().selectTable(tableName);
		for (int i = 0; i < rows.size(); i++) {
			String[] arr = rows.get(i);
			model.addRow( arr );
			int n = 0;
			while (n < columns.length)
				System.out.format("%s\t", arr[n++]);
			System.out.format("%n");
		}
		System.out.format("Total %d rows%n", rows.size());
	}
	
	private Connector getConnector() {
		timeLabel.setText(String.format(timePattern, Main.connector.getDate())); // update label with new date
		return Main.connector;
	}
	
    class MyJTable extends JTable {
        MyJTable (TableModel model) {
			super(model);
			this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		
		// Determines whether data can be entered by user
		@Override
        public boolean isCellEditable(int row, int column) {
			return 	MyPanel.this.isEditable &&
					column != 0 &&
					(row == MyPanel.this.editingRow || 
						(MyPanel.this.inserting && row == MyPanel.this.model.getRowCount()-1) )
			;
        }
		
    }


}