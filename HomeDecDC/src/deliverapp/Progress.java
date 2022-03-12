package deliverapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class Progress implements ActionListener{
	JFrame panel_progess;
	JProgressBar pbar_process;
	JLabel lab_updating;
	final static String path="E:\\img\\sta.wav";
	int i=0;
	int rowidd;
	Login parent=null;
	public Progress(int rowid,Login p) {
		parent=p;
		rowidd=rowid;
		panel_progess=new JFrame();
		panel_progess.setUndecorated(true);
		pbar_process=new JProgressBar(0,20);
		pbar_process.setBounds(82, 82, 150, 30);
		pbar_process.setStringPainted(true);
		Timer t=new Timer(100, this);
		t.start();
		
		lab_updating=new JLabel("Updating Status");
		lab_updating.setBounds(82, 55, 150, 30);
		
		panel_progess.add(lab_updating);
		panel_progess.add(pbar_process);
		panel_progess.setLayout(null);
		panel_progess.setBounds(250,200,400,300);
		panel_progess.setVisible(true);
	}@Override
	public void actionPerformed(ActionEvent e) {
		if(i==100) {
			new StatusUpdated(rowidd,parent);
			panel_progess.dispose();
		}
		pbar_process.setValue(i);
        i++;	
	}
}
