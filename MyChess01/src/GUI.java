import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.lang.model.element.Element;
import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {

	
	Image BoardImg;
	
	Container container;
	Menu menu;
	MenuBar bar;
	MenuItem itemStart;
	MenuItem itemExit;
	
	Canvas canvas;
	
	public GUI(String title) {
		canvas = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(BoardImg, 0, 0, this);
			}
		};
		initUI(title);
	}
	
	public void initUI(String title) {
		container = getContentPane();
		container.setLayout(null);
		canvas.setBounds(0, 0, 558, 620);
		add(canvas);
		menu = new Menu("menu");
		bar = new MenuBar();
		bar.add(menu);
		setMenuBar(bar);
		itemStart = new MenuItem("start");
		itemExit = new MenuItem("exit");
		
		menu.add(itemStart);
		menu.add(itemExit);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);;
			}
		});
		//java.net.URL url = Element.class.getResource("E:/workPlace/JavaWorkPlace/MyChess01/main.gif");
		File filePath = new File("boards/main.gif");
		//BoardImg = getToolkit().createImage(url);
		try {
			BoardImg = ImageIO.read(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if(frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if(frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		setLocation((screenSize.width - frameSize.width) / 2 - 280, (screenSize.height - frameSize.height) / 2 - 350);
		setResizable(false);
		setTitle(title);
		setSize(600, 700);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == itemExit)  {
			
		}
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GUI("test");
	}

	
}
