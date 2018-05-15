import javax.swing.JFrame;

public class Window extends JFrame{
	public static void main(String[] p){
		new Window(800,600);
	}
	static JFrame frame;
	Window(int width, int height){
		Window.frame=this;
		this.setContentPane(new Screen());
        this.setUndecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
