package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JMeter extends JPanel{
	Image segment;
	int maxValue;
	int currentValue;
	int t = 0;
	public JMeter(int maxValue,String path){
		File segmentFile = new File("resources/ActorSpriteSheets/"+path+".png");
		try {
			segment = ImageIO.read(segmentFile);
		} catch (IOException e) {e.printStackTrace();
		}
		this.maxValue = maxValue;
		this.currentValue = maxValue;
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(segment, 0, 0, currentValue*this.getWidth()/maxValue, this.getHeight(),null);
		t++;
		this.currentValue -= (t+1)/10;
		t%=10;
	}
}
