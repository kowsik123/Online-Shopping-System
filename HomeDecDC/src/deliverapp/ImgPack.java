package deliverapp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImgPack{
	public BufferedImage img;
	int height;
	int width;
	public ImgPack(String path) {
		try {
		    img = ImageIO.read(new File(path));
		    height=img.getHeight();
			width=img.getWidth();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public void LoadImage(String path) {
		try {
		    BufferedImage nimg = ImageIO.read(new File(path));
		    height=nimg.getHeight();
			width=nimg.getWidth();
		    img=new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		    img.getGraphics().drawImage(nimg, 0, 0, null);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public ImgPack(int w,int h) {
		img=new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
	    height=h;
		width=w;
	}
	public Image getImage(int width,int height) {
		return img.getScaledInstance(width, height,Image.SCALE_SMOOTH);
	}
	
	public Image getImage() {
		return img.getScaledInstance(width, height,Image.SCALE_SMOOTH);
	}
	
	public void CCrop() {
		int len=Integer.min(width,height);
		int r=len/2;
		for(int i=0;i<len;i++) {
			for(int j=0;j<len;j++) {
				int c=((i-r)*(i-r))+((j-r)*(j-r));
				if( c> r*r) {
					img.setRGB(i, j, new Color(0,0,0,0).getRGB());
				}
			}
		}
	}
	public void cCrop() {
		int width = img.getWidth();
		BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = circleBuffer.createGraphics();
		g2.setClip(new Ellipse2D.Float(0, 0, width, width));
		g2.drawImage(img, 0, 0, width, width, null);
		img=circleBuffer;
	}
	public void setRandomColor() {
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				img.setRGB(i, j, new Color((int)(Math.random()*255),(int) (Math.random()*255), (int)(Math.random()*255)).getRGB());
			}
		}
	}
	public void setColor(Color c) {
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				img.setRGB(i, j, c.getRGB());
			}
		}
	}
	public void makeTrans() {
		for(int i=0;i<width;i++) {
			for(int j=0;j<height;j++) {
				img.setRGB(i, j, new Color(0,0,0,0).getRGB());
			}
		}
	}
	public void rotate()  {
		for(int i=0;i<=(width-1)/2;i++) {
			for(int j=i;j<width-i-1;j++) {
				int tmp=img.getRGB(i, j);
				img.setRGB(i, j, img.getRGB(width-1-j, i));
				img.setRGB(width-1-j, i, img.getRGB(width-1-i,width-1-j));
				img.setRGB(width-1-i,width-1- j, img.getRGB(j,width-1- i));
				img.setRGB(j, width-1-i,tmp);
			}
		}
	}
	public void overlay(ImgPack src,int x,int y) {
		for(int i=x,si=0;i<Integer.min(src.width+x, width); i++,si++) {
			for(int j=y,sj=0;j<Integer.min(src.height+y, height); j++,sj++) {
				img.setRGB(i,j, overlay( img.getRGB(i,j),src.img.getRGB(si, sj)) );
			}
		}
	}
	public void fill(ImgPack src,int x,int y) {
		for(int i=x,si=0;i<Integer.min(src.width+x, width); i++,si++) {
			for(int j=y,sj=0;j<Integer.min(src.height+y, height); j++,sj++) {
				img.setRGB(i,j, src.img.getRGB(si, sj));
			}
		}
	}
	public void resize(int w,int h) {
		Image i=getImage(w,h);
		BufferedImage d=new BufferedImage(w,h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g=d.getGraphics();
		g.drawImage(i, 0, 0, null);
		img=d;
		height=h;
		width=w;
	}
	
	private int overlay(int i1,int i2) {
		Color c1=new Color(i1);
		Color c2=new Color(i2);
		int alpha=c2.getAlpha();
		int red=((int) ((c2.getAlpha()/255.0)*c1.getRed())+c2.getRed())/2;
		int blue=((int) ((c2.getAlpha()/255.0)*c1.getBlue())+c2.getBlue())/2;
		int green=((int) ((c2.getAlpha()/255.0)*c1.getGreen())+c2.getGreen())/2;
		
		return new Color(red,green,blue,alpha).getRGB();
	}
}