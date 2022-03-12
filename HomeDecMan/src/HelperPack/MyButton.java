package HelperPack;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

public class MyButton extends JButton {
	
	Color bg=null;
	
	public MyButton() {
		super();
		render();
	}
	
	public MyButton(String text) {
		super(text);
		render();
	}
	
	public void render() {
		setSize(100,30);
		setUI(new BasicButtonUI());
		setBorder(null);
		setBackground(Color.GREEN.brighter());
		setForeground(Color.white);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bg=getBackground();
				setBackground(bg.darker());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(bg);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				setBackground(bg.darker().darker());
			}
		});
	}
}