package HelperPack;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.*;

public class ProductLayout implements LayoutManager{
	
	ArrayList<Component> products=new ArrayList<>();
	Container parent=null;
	int x,y;
	int width;
	int height;
	int n;
	int gap;
	int minwidth;
	int maxwidth;
	public static final String PRODUCT="product";
	
	boolean isScrollbarAdded=false;
		
	public ProductLayout(int gap,int n,int height) {
		super();
		x=gap;
		y=gap;
		this.gap=gap;
		width= (Toolkit.getDefaultToolkit().getScreenSize().width - 10 -(n+1)*gap)/n;
		this.n=n;
		this.height=height;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		comp.setSize(width,height);
//		comp.setBackground(new Color(0,0,0,50));
		products.add(comp);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		products.remove(comp);
		render();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {return null;}

	@Override
	public Dimension minimumLayoutSize(Container parent) {return null;}

	@Override
	public void layoutContainer(Container par) {
		if(!isScrollbarAdded) {
			parent=par;
			parent.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					render();
					super.componentResized(e);
				}
			});
			isScrollbarAdded=true;
		}
	}
	private void render() {
		x=gap;
		y=gap;
		int max=parent.getWidth();
		int wsize=width+gap;
		int hsize=height+gap;
		for(Component i:products) {
			
			i.setLocation(x,y);
			x+=wsize;
			if(x>max-wsize) {
				y+=hsize;
				x=gap;
			}
		}
		if(x==gap) parent.setSize(parent.getWidth(),y);
		else parent.setSize(parent.getWidth(),y+hsize);
	}
}