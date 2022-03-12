package HelperPack;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class just extends JFrame {
	    
    just(){  
    	
    	setSize(300,400);
	    String data[][]={ {"101","Amit","670000"},    
	                          {"102","Jai","780000"},    
	                          {"101","Sachin","700000"}};    
	    String column[]={"ID","NAME","SALARY"};         
	    JTable jt=new JTable(data,column);    
	    jt.setBounds(30,40,200,300);          
	    JScrollPane sp=new JScrollPane(jt);    
	    add(sp);          
	        
	    setVisible(true);    
    }     
	public static void main(String[] args) {
		new just();
	}

}
