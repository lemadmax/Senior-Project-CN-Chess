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
import javax.swing.JOptionPane;

public class GUI extends JFrame implements ActionListener {

	public static final String pieces_name[] = { "oos", "bk", "ba", "bb", "bn", "br", "bc", "bp", "rk", "ra", "rb",
			"rn", "rr", "rc", "rp" };

	Image BoardImg;
	Image piecesImg[] = new Image[15];
	Image selectImgr;
	Image selectImgb;

	Container container;
	Menu menu;
	MenuBar bar;
	MenuItem itemStart;
	MenuItem itemExit;

	Canvas canvas;
	game currentGame;

	int rpieceSelected = -1;
	int rplacePlaced = -1;
	int bpieceSelected = -1;
	int bplacePlaced = -1;
	int preRPieceSelectedx = 0, preRPieceSelectedy = 0;
	int preRPlacePlacedx = 0, preRPlacePlacedy = 0;
	int preBPieceSelectedx = 0, preBPieceSelectedy = 0;
	int preBPlacePlacedx = 0, preBPlacePlacedy = 0;
	boolean ifselectedAPiece = false;
	boolean AIThinking = false;
	boolean red = false;

	public GUI(String title) {
		currentGame = new game();
		canvas = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(BoardImg, 0, 0, this);
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 9; j++) {
						int x = 24 + j * 57;
						int y = 24 + i * 57;
						int pieceId;
						if (currentGame.gameBoard[i][j] > 0)
							pieceId = currentGame.gameBoard[i][j];
						else if (currentGame.gameBoard[i][j] < 0)
							pieceId = -currentGame.gameBoard[i][j] + 7;
						else
							pieceId = 0;
						if (pieceId != 0) {
							g.drawImage(piecesImg[pieceId], x, y, this);
						}
						if (rpieceSelected != -1) {
							int tx = (rpieceSelected) % 9;
							int ty = (rpieceSelected) / 9;
							tx = 24 + tx * 57;
							ty = 24 + ty * 57;
							g.drawImage(selectImgr, tx, ty, this);
						}
						if (rplacePlaced != -1) {
							int tx = (rplacePlaced) % 9;
							int ty = (rplacePlaced) / 9;
							tx = 24 + tx * 57;
							ty = 24 + ty * 57;
							g.drawImage(selectImgr, tx, ty, this);
						}
						if (bpieceSelected != -1) {
							int tx = (bpieceSelected) % 9;
							int ty = (bpieceSelected) / 9;
							tx = 24 + tx * 57;
							ty = 24 + ty * 57;
							g.drawImage(selectImgb, tx, ty, this);
						}
						if (bplacePlaced != -1) {
							int tx = (bplacePlaced) % 9;
							int ty = (bplacePlaced) / 9;
							tx = 24 + tx * 57;
							ty = 24 + ty * 57;
							g.drawImage(selectImgb, tx, ty, this);
						}
					}
				}
			}

		};
		initUI(title);
	}

//	public void initUI(String title) {
//		container = getContentPane();
//		container.setLayout(null);
//		canvas.addMouseListener(new MouseListener() {
//			public void mouseClicked(MouseEvent mouseevent) {
//				
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if(!AIThinking) {
//					int x = e.getX();
//					int y = e.getY();
//					if(x >= 24 && x <= 530 && y >= 24 && y <= 596) {
//						int tempx = ((x - 24) / 57);
//						int tempy = ((y - 24) / 57);
//						int xx = tempx * 57 + 24;
//						int yy = tempy * 57 + 24;
//						if(!ifselectedAPiece) {
//							
//							if(currentGame.selectPiece(tempx, tempy)) {
//								pieceSelected = tempy * 9 + tempx;
//								ifselectedAPiece = true;
//								
//								canvas.repaint(xx, yy, 57, 57);
//							}
//							
//							
//						
//						}
//						else {
//							int sx = pieceSelected % 9;
//							int sy = pieceSelected / 9;
//							sx = sx * 57 + 24;
//							sy = sy * 57 + 24;
//							if(currentGame.playerMove(pieceSelected, tempx, tempy) == 1) {
//								placePlaced = tempy * 9 + tempx;
//								canvas.repaint(xx, yy, 57, 57);
//								ifselectedAPiece = false;
//								if(currentGame.isGameOver() == 1) {
//									System.out.println("red win");
//									JOptionPane.showMessageDialog(null, "Red has won!" , "Alpha-Bob 1.0", JOptionPane.INFORMATION_MESSAGE);
//								}
//								AIMove();
//								
//							}
//							else if(currentGame.playerMove(pieceSelected, tempx, tempy) == 2) {
//								pieceSelected = tempy * 9 + tempx;
//								canvas.repaint(xx, yy, 57, 57);
//							}
//							canvas.repaint(sx, sy, 57, 57);
//							
//							
//						}
//					}
//				}
//			}

	public void initUI(String title) {
		container = getContentPane();
		container.setLayout(null);
		canvas.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (!AIThinking) {
					int x = e.getX();
					int y = e.getY();
					if (x >= 24 && x <= 530 && y >= 24 && y <= 596) {
						int tempx = ((x - 24) / 57);
						int tempy = ((y - 24) / 57);
						int xx = tempx * 57 + 24;
						int yy = tempy * 57 + 24;
						if (!ifselectedAPiece) {
							if (!red) {
								if (currentGame.selectPiece(tempx, tempy)) {
									rpieceSelected = -1;
									rplacePlaced = -1;
									ifselectedAPiece = true;
									canvas.repaint(preRPieceSelectedx, preRPieceSelectedy, 57, 57);
									canvas.repaint(preRPlacePlacedx, preRPlacePlacedy, 57, 57);
									rpieceSelected = tempy * 9 + tempx;
									preRPieceSelectedx = xx;
									preRPieceSelectedy = yy;
									canvas.repaint(xx, yy, 57, 57);
								}

							} else {
								if (currentGame.selectPiece2(tempx, tempy)) {
									bpieceSelected = -1;
									bplacePlaced = -1;
									ifselectedAPiece = true;
									canvas.repaint(preBPieceSelectedx, preBPieceSelectedy, 57, 57);
									canvas.repaint(preBPlacePlacedx, preBPlacePlacedy, 57, 57);
									bpieceSelected = tempy * 9 + tempx;
									preBPieceSelectedx = xx;
									preBPieceSelectedy = yy;
									canvas.repaint(xx, yy, 57, 57);
								}
							}

						} else {
							

							if (!red) {
								int sx = rpieceSelected % 9;
								int sy = rpieceSelected / 9;
								sx = sx * 57 + 24;
								sy = sy * 57 + 24;
								if (currentGame.playerMove(rpieceSelected, tempx, tempy) == 1) {
									rplacePlaced = tempy * 9 + tempx;
									preRPlacePlacedx = xx;
									preRPlacePlacedy = yy;
									canvas.repaint(xx, yy, 57, 57);
									if (currentGame.isGameOver() == 1) {
										System.out.println("red win");
										JOptionPane.showMessageDialog(null, "Red has won!", "Alpha-Bob 1.0",
												JOptionPane.INFORMATION_MESSAGE);
									}
									// AIMove();
									red = true;
									ifselectedAPiece = false;

								} else if (currentGame.playerMove(rpieceSelected, tempx, tempy) == 2) {
									rpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
								}
								canvas.repaint(sx, sy, 57, 57);
							}

							else {
								int sx = bpieceSelected % 9;
								int sy = bpieceSelected / 9;
								sx = sx * 57 + 24;
								sy = sy * 57 + 24;
								
								if (currentGame.playerMove2(bpieceSelected, tempx, tempy) == 1) {
									bplacePlaced = tempy * 9 + tempx;
									preBPlacePlacedx = xx;
									preBPlacePlacedy = yy;
									canvas.repaint(xx, yy, 57, 57);
									if (currentGame.isGameOver() == -1) {
										System.out.println("black win");
										JOptionPane.showMessageDialog(null, "Black has won!", "Alpha-Bob 1.0",
												JOptionPane.INFORMATION_MESSAGE);
									}
									// AIMove();
									ifselectedAPiece = false;
									red = false;

								} else if (currentGame.playerMove2(bpieceSelected, tempx, tempy) == 2) {
									bpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
								}
								canvas.repaint(sx, sy, 57, 57);
							}
							
							//canvas.repaint();

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
				System.exit(0);
				;
			}
		});

		itemStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentGame.start();
				rpieceSelected = -1;
				rplacePlaced = -1;
				bpieceSelected = -1;
				bplacePlaced = -1;
				ifselectedAPiece = false;
				AIThinking = false;
				canvas.repaint();
			}
		});

		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// java.net.URL url =
		// Element.class.getResource("E:/workPlace/JavaWorkPlace/MyChess01/main.gif");
		File filePath = new File("boards/main.gif");
		// BoardImg = getToolkit().createImage(url);
		try {
			BoardImg = ImageIO.read(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadPieces();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if (frameSize.width > screenSize.width)
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
		if (e.getSource() == itemExit) {
			System.out.println("lala");
		}
	}

	private void loadPieces() {
		String path = "pieces/wood/";
		for (int i = 1; i < 15; i++) {
			File file = new File(path + pieces_name[i] + ".gif");
			try {
				piecesImg[i] = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File imgFile = new File(path + pieces_name[0] + ".gif");
		File imgFileb = new File(path + "boos.gif");
		try {
			selectImgr = ImageIO.read(imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			selectImgb = ImageIO.read(imgFileb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void AIMove() {
		AIThinking = true;
		(new Thread() {
			public void run() {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentGame.AIMakeMove();

				rpieceSelected = currentGame.getPieceSelected();
				rplacePlaced = currentGame.getPlacePlaced();
//				int px = pieceSelected % 9;
//				int py = pieceSelected / 9;
//				int tx = placePlaced % 9;
//				int ty = placePlaced / 9;
//				px = px * 57 + 24;
//				py = py * 57 + 24;
//				tx = tx * 57 + 24;
//				ty = ty * 57 + 24;
				canvas.repaint();
				if (currentGame.isGameOver() == -1) {
					System.out.println("black win");
					JOptionPane.showMessageDialog(null, "Black has won!", "Alpha-Bob 1.0",
							JOptionPane.INFORMATION_MESSAGE);
				}
				AIThinking = false;
			}
		}).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		new GUI("Alpha-Bob 1.0");
	}

}
