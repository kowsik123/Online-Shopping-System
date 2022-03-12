package HelperPack;
import java.awt.Point;
import java.awt.Component;
import java.awt.Dimension;

public class Center {
	public static int getCenter(int parent,int child) {
		return (parent-child)/2;
	}
	public static float getCenter(float parent,float child) {
		return (parent-child)/2;
	}
	public static Point getCenter(int parentWidth,int parentHeight,int childWidth,int childHeight) {
		return new Point(getCenter(parentWidth,childWidth),getCenter(parentHeight,childHeight));
	}
	public static Point getCenter(Dimension parent,Dimension child) {
		return new Point(getCenter(parent.width,child.width),getCenter(parent.height,child.height));
	}
	public static Point getCenter(Component child) {
		return getCenter(child.getParent().getSize(),child.getSize());
	}
	public static void align(Component c) {
		c.setLocation(getCenter(c.getParent().getSize(),c.getSize()));
	}
	public static void alignX(Component a,Component b) {
		Dimension d=new Dimension(a.getParent().getWidth()/2,a.getParent().getHeight());
		a.setLocation(getCenter(d,a.getSize()));
		Point p=getCenter(d,b.getSize());
		b.setLocation(p.x+d.width,p.y);
	}
}