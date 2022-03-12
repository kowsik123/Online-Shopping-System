package HelperPack;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.TableCellRenderer;

public class TableButton extends JButton implements TableCellRenderer {

	  public TableButton() {
	    setOpaque(true);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	    boolean isSelected, boolean hasFocus, int row, int column) {
	    render();
	    setText((value == null) ? "" : value.toString());
	    return this;
	  }
	  
	  public void render() {
			setUI(new BasicButtonUI());
			setBorder(null);
			setBackground(Color.GREEN.brighter());
			setForeground(Color.white);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setBackground(new Color(Color.green.getRed(),Color.green.getGreen()-30,Color.green.getBlue()));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(Color.GREEN.brighter());
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					setBackground(Color.GREEN.darker());
				}
			});
		}
	  
}