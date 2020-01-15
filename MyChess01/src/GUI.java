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

	public static final String pieces_name[] = {
			"oos", "bk", "ba", "bb", "bn", "br", "bc", "bp",
					"rk", "ra", "rb", "rn", "rr", "rc", "rp"
	};
	
	Image BoardImg;
	Image piecesImg[] = new Image[15];
	Image selectImg;
	
	Container container;
	Menu menu;
	MenuBar bar;
	MenuItem itemStart;
	MenuItem itemExit;
	
	Canvas canvas;
	
	public GUI(String title) {
		game currentGame = new game();
		canvas = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(BoardImg, 0, 0, this);
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 9; j++) {
						int x = 24 + j * 57;
						int y = 24 + i * 57;
						int pieceId;
						if(currentGame.gameBoard[i][j] > 0) pieceId = currentGame.gameBoard[i][j];
						else if(currentGame.gameBoard[i][j] < 0) pieceId = -currentGame.gameBoard[i][j] + 7;
						else pieceId = 0;
						if(pieceId != 0) {
							g.drawImage(piecesImg[pieceId], x, y, this);
						}
					}
				}
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
		loadPieces();
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

	private void loadPieces() {
		String path = "pieces/wood/";
		for(int i = 1; i < 15; i++) {
			File file = new File(path + pieces_name[i] + ".gif");
			try {
				piecesImg[i] = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File imgFile = new File(path + pieces_name[0] + ".gif");
		try {
			selectImg = ImageIO.read(imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		new GUI("test");
	}

	
}
