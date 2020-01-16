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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	game currentGame;
	
	int pieceSelected = 0;
	int placePlaced = 0;
	boolean ifselectedAPiece = false;
	
	public GUI(String title) {
		currentGame = new game();
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
						if(pieceSelected != 0) {
							int tx = (pieceSelected) % 9;
							int ty = (pieceSelected) / 9;
							tx = 24 + tx * 57;
							ty = 24 + ty * 57;
							g.drawImage(selectImg, tx, ty, this);
						}
						if(placePlaced != 0) {
							int tx = (placePlaced) % 9;
							int ty = (placePlaced) / 9;
							tx = 24 + tx * 57;
							ty = 24 + ty * 57;
							g.drawImage(selectImg, tx, ty, this);
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
		canvas.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseevent) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int x = e.getX();
				int y = e.getY();
				if(x >= 24 && x <= 530 && y >= 24 && y <= 596) {
					if(!ifselectedAPiece) {
						int tempx = ((x - 24) / 57);
						int tempy = ((y - 24) / 57);
						if(currentGame.selectPiece(tempx, tempy)) {
							pieceSelected = tempy * 9 + tempx;
							ifselectedAPiece = true;
							System.out.println(pieceSelected);
							canvas.repaint();
						}
					}
					else {
						int tempx = ((x - 24) / 57);
						int tempy = ((y - 24) / 57);
						if(currentGame.playerMove(pieceSelected, tempx, tempy) == 1) {
							placePlaced = tempy * 9 + tempx;
							canvas.repaint();
						}
						else if(currentGame.playerMove(pieceSelected, tempx, tempy) == 2) {
							pieceSelected = tempy * 9 + tempx;
							canvas.repaint();
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
			System.out.println("lala");
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
