package HelperPack;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class Holder {
	public JPanel p=null;
	public Holder() {}
	public Holder(int w,int h,LayoutManager lm) {
		p=new JPanel(lm);
		p.setSize(w,h);
	}
	public Holder(LayoutManager lm) {
		if(p==null) p=new JPanel(lm);
		else p.setLayout(lm);
	}
	public Holder(JPanel p,LayoutManager lm) {
		p.setLayout(lm);
		this.p=p;
	}
	public Holder(LayoutManager lm,Color c) {
		p=new JPanel(lm);
		p.setBackground(c);
	}
	public JPanel get() {
		return p;
	}
	public JPanel add(Component a) {
		p.add(a);
		return p;
	}
	public JPanel addWithC(Component a) {
		p.add(a);
		Center.align(a);
		return p;
	}
	public JPanel add(Component a,Component b) {
		p.add(a);
		p.add(b);
		return p;
	}
	public JPanel add(Component a,Component b,Component c) {
		p.add(a);
		p.add(b);
		p.add(c);
		return p;
	}
	public JPanel add(Component a,Component b,Component c,Component d) {
		p.add(a);
		p.add(b);
		p.add(c);
		p.add(d);
		return p;
	}
	public JPanel add(Component[] arr) {
		for(Component i:arr) p.add(i);
		return p;
	}
}
