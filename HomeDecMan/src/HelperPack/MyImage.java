package HelperPack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class MyImage {
	public Color[][] img;
	public int width;
	public int height;
	public MyImage(int width,int height) {
		this.width=width;
		this.height=height;
		img=new Color[width][height];
	}
	
	public MyImage(String path) {
		try {
			BufferedImage bg=ImageIO.read(new File(path));
			img=new Color[bg.getWidth()][bg.getHeight()];
			this.width=bg.getWidth();
			this.height=bg.getHeight();
			for(int i=0;i<bg.getWidth();i++) {
				for(int j=0;j<bg.getHeight();j++) {
					
					int color=bg.getRGB(i, j);
					int alpha=color >>>24;
					int red=color >>>16 & (0xff);
					int green=color >>>8 & (0xff);
					int blue=color & (0xff);
//					System.out.println(red+" "+green+" "+blue);
					img[i][j]=new Color(red, green, blue, alpha);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setRandomColor() {
		for(int i=0;i<img.length;i++) {
			for(int j=0;j<img[0].length;j++) {
				img[i][j]=new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			}
		}
	}
	
	public void setColor(Color c) {
		for(int i=0;i<img.length;i++) {
			for(int j=0;j<img[0].length;j++) {
				img[i][j]=c;
			}
		}
	}
	
	public void makeCircTrans() {
		for(int i=img.length/4;i<=3*(img.length)/4;i++) {
			for(int j=img.length/4;j<=3*(img.length)/4;j++) {
				img[i][j]=new Color(0f,0f,0f,0f);
			}
		}
	}
	public void makeTrans() {
		for(int i=0;i<img.length;i++) {
			for(int j=0;j<img[0].length;j++) {
				img[i][j]=new Color(0f,0f,0f,0.0f);
			}
		}
	}
	public void rotate90()  {
		int n=img.length;
		for(int i=0;i<=(n-1)/2;i++) {
			for(int j=i;j<n-i-1;j++) {
				Color tmp=img[i][j];
				img[i][j]=img[n-1-j][i];
				img[n-1-j][i]=img[n-1-i][n-1-j];
				img[n-1-i][n-1-j]=img[j][n-1-i];
				img[j][n-1-i]=tmp;
			}
		}
	}
	public void horizontalFlip() {
		int n=img.length;
		for(int i=0;i<n/2;i++) {
			Color[] tmp=img[i];
			img[i]=img[n-1-i];
			img[n-1-i]=tmp;
		}
	}
	
	public void CCrop() {
		int r=img.length/2;
		for(int i=0;i<img.length;i++) {
			for(int j=0;j<img[0].length;j++) {
				if(((i-r)*(i-r))+((j-r)*(j-r)) > r*r) {
					img[i][j]=new Color(0,0,0,0);
				}
			
			}
		}
	}
	
	public Color average(Color p1,Color p2,Color p3,Color p4) {
		int alpha=(p1.getAlpha()+p2.getAlpha()+p3.getAlpha()+p4.getAlpha())/4;
		int red=(p1.getRed()+p2.getRed()+p3.getRed()+p4.getRed())/4;
		int green=(p1.getGreen()+p2.getGreen()+p3.getGreen()+p4.getGreen())/4;
		int blue=(p1.getBlue()+p2.getBlue()+p3.getBlue()+p4.getBlue())/4;
		
		return new Color(red, green, blue, alpha);
		
	}
	public Color average(Color p1,Color p2) {
		int alpha=(p1.getAlpha()+p2.getAlpha())/2;
		int red=(p1.getRed()+p2.getRed())/2;
		int green=(p1.getGreen()+p2.getGreen())/2;
		int blue=(p1.getBlue()+p2.getBlue())/2;
		
		return new Color(red, green, blue, alpha);
		
	}
	public MyImage halfsize() {
		MyImage nimg = new MyImage(img.length/2,img[0].length/2);
		for(int i=0;i<img.length-2;i=i+2) {
			for(int j=0;j<img[0].length-2;j=j+2) {
				nimg.img[i/2][j/2]=average(img[i][j], img[i][j+1], img[i+1][j], img[i+1][j+1]);
			}
		}
		return nimg;
	}
	public MyImage resize(int ratio) {
		MyImage img1=halfsize();
		
		for(int i=0;i<ratio-1;i++) {
			img1=img1.halfsize();
		}
		return img1;
	}
	
	public void overlay(MyImage src,int x,int y) {
		for(int i=x,si=0;i<Integer.min(src.img.length+x, img.length); i++,si++) {
			for(int j=y,sj=0;j<Integer.min(src.img[0].length+y, img[0].length); j++,sj++) {
				img[i][j]=src.img[si][sj];
			}
		}
	}
	
	public void write(Graphics p1) {
		
		for(int i=0;i<img.length;i++) {
			for(int j=0;j<img[0].length;j++) {
				p1.setColor(img[i][j]);
				p1.fillRect(i, j, 1, 1);
			}
		}
	}
	
	
}
