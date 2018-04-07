package crungeonDawler;

import javax.swing.JFrame;

public class Window extends JFrame{
	public static void main(String[] p){
		new Window(1388,768);//screen dimensions
	}
	Window(int width, int height){
		this.setSize(width, height);
		this.add(new Screen());
		this.setVisible(true);
	}
}
