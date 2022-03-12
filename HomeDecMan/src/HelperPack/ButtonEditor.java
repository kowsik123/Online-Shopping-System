package HelperPack;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ButtonEditor extends DefaultCellEditor {
	  protected MyButton button;

	  private String label;
	  private boolean isPushed;
	  
	  ActionListener ac;
	  public int i=0,j=0;
	  
	  public ButtonEditor(ActionListener ac) {
	    super(new JCheckBox());
	    this.ac=ac;
	    button = new MyButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
//	    System.out.println(row+" "+column+" "+value);
		if (isSelected) {
	    	button.setBackground(new Color(Color.green.getRed(),Color.green.getGreen()-30,Color.green.getBlue()));
	    }else {
	      button.setBackground(Color.GREEN.brighter());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    isPushed = true;
	    i=row;
	    j=column;
	    return button;
	  }

	  public Object getCellEditorValue() {
		  System.out.println(label);
		  if (isPushed) { // On Click Listener
			button.setBackground(Color.GREEN.darker());
			ActionEvent ae=new ActionEvent(this, 1, i+" "+j);
			ac.actionPerformed(ae);
	    }
	    isPushed = false;
	    return new String(label);
	  }

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }
	}