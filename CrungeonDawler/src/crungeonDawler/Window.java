package crungeonDawler;

import javax.swing.JFrame;

public class Window extends JFrame{
	public static void main(String[] p){
		new Window(1388,768);//screen dimensions
	}
	static JFrame frame;//needed to close the frame
	Window(int width, int height){
		Window.frame=this;
		this.setSize(width, height);
		this.add(new Screen());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
