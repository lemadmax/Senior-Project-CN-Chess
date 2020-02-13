/*
 * 
 * GUI class
 * 		1. initiate program (main function)
 * 		2. define and build graphic user interface
 * 		3. start game
 * 
 * */

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

	// Array that store the names of the images of each piece
	// index from 1 to 14 represent 14 different pieces
	public static final String pieces_name[] = { 
			"oos", "bk", "ba", "bb", 
			"bn", "br", "bc", "bp", 
			"rk", "ra", "rb", "rn", 
			"rr", "rc", "rp" };

	Image BoardImg;						// Image variable that stores the Board picture
	Image piecesImg[] = new Image[15];	// Image array that stores the Piece pictures
	Image selectImgr;					// Blue square
	Image selectImgb;					// Red square
	Image avaPlaceImg;

	/**********************
	 * start: GUI frame
	 * *******************/

	Container container;				
	Menu menu,GameMode,help;
	MenuBar bar;
	MenuItem itemStart;
	MenuItem itemExit;
	MenuItem itemRegret;
	MenuItem Player_vs_Player,Player_vs_AI, Help;

	Canvas canvas;
	game currentGame;

	/**********************
	 * end: GUI frame
	 * *******************/

	int rpieceSelected = -1;		// record the place of the selected piece (-1 represent none)			
	int rplacePlaced = -1;			// record the place red side placed a piece
	int bpieceSelected = -1;		// black side selected piece
	int bplacePlaced = -1;			// black side placed position
	
	int avaPlaces[][] = {
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0}
	};

	/*
	 * record the moves of the previous
	 * round (for repaint)
	 * */

	int preRPieceSelectedx = 0;		
	int preRPieceSelectedy = 0;
	int preRPlacePlacedx = 0;
	int preRPlacePlacedy = 0;
	int preBPieceSelectedx = 0;
	int preBPieceSelectedy = 0;
	int preBPlacePlacedx = 0;
	int preBPlacePlacedy = 0;

	/*********************************
	 * *******************************/

	boolean ifselectedAPiece = false;	// check if already selected a piece
	boolean AIThinking = false;			// check if AI is thinking
	boolean red = false;				// check which side is moving
	int gameMode = 1;					// 1 stands for player vs. player; 2 stands for player vs. AI

	// constructor
	public GUI(String title) {
		currentGame = new game();	// initiate game object

		// define canvas
		canvas = new Canvas() {

			// overwrite paint function
			public void paint(Graphics g) {

				// display game board
				g.drawImage(BoardImg, 0, 0, this);

				// draw pieces according to the current
				// game board distribution
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
						if(avaPlaces[i][j] == 1) {
							g.drawImage(avaPlaceImg, x, y, this);
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

		// load ui resources
		initUI(title);
	}

	// function that initiate UI
	public void initUI(String title) {

		container = getContentPane();
		container.setLayout(null);

		canvas.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				// gameNode = 1, player vs. player
				if(gameMode == 1) {
					int x = e.getX();
					int y = e.getY();
					if (x >= 24 && x <= 530 && y >= 24 && y <= 596) {
						int tempx = ((x - 24) / 57);
						int tempy = ((y - 24) / 57);
						int xx = tempx * 57 + 24;
						int yy = tempy * 57 + 24;
						
						// if haven't selected a piece yet
						// proceed to piece selection
						if (!ifselectedAPiece) {
							// if is red side's turn
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
									checkAvaPlaces(tempx, tempy);
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
									checkAvaPlaces(tempx, tempy);
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
									currentGame.step++;
									currentGame.saveGameboard(currentGame.step);
									System.out.println("step: "+currentGame.step);
									clearAvaPlaces();
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
									clearAvaPlaces();
									checkAvaPlaces(tempx, tempy);
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
									currentGame.step++;
									currentGame.saveGameboard(currentGame.step);
									System.out.println("step: "+currentGame.step);
									clearAvaPlaces();
									if (currentGame.isGameOver() == -1) {
										System.out.println("black win");
										JOptionPane.showMessageDialog(null, "Black has won!", "Alpha-Bob 1.0",
												JOptionPane.INFORMATION_MESSAGE);
									}
									ifselectedAPiece = false;
									red = false;

								} else if (currentGame.playerMove2(bpieceSelected, tempx, tempy) == 2) {
									bpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
									clearAvaPlaces();
									checkAvaPlaces(tempx, tempy);
								}
								canvas.repaint(sx, sy, 57, 57);
							}
						}
					}
				}
				if(gameMode == 2) {
					if (!AIThinking) {
						int x = e.getX();
						int y = e.getY();
						if (x >= 24 && x <= 530 && y >= 24 && y <= 596) {
							int tempx = ((x - 24) / 57);
							int tempy = ((y - 24) / 57);
							int xx = tempx * 57 + 24;
							int yy = tempy * 57 + 24;
							if (!ifselectedAPiece) {

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
									checkAvaPlaces(tempx, tempy);
								}
							}
							else {

								int sx = rpieceSelected % 9;
								int sy = rpieceSelected / 9;
								sx = sx * 57 + 24;
								sy = sy * 57 + 24;
								if (currentGame.playerMove(rpieceSelected, tempx, tempy) == 1) {
									rplacePlaced = tempy * 9 + tempx;
									preRPlacePlacedx = xx;
									preRPlacePlacedy = yy;
									canvas.repaint(xx, yy, 57, 57);
									currentGame.step++;
									currentGame.saveGameboard(currentGame.step);
									System.out.println("step: "+currentGame.step);
									clearAvaPlaces();
									if (currentGame.isGameOver() == 1) {
										System.out.println("red win");
										JOptionPane.showMessageDialog(null, "Red has won!", "Alpha-Bob 1.0",
												JOptionPane.INFORMATION_MESSAGE);
									}
									AIMove();

								} else if (currentGame.playerMove(rpieceSelected, tempx, tempy) == 2) {
									rpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
									clearAvaPlaces();
									checkAvaPlaces(tempx, tempy);
								}
								canvas.repaint(sx, sy, 57, 57);
							}
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
		itemRegret = new MenuItem("regret");

		menu.add(itemStart);
		menu.add(itemExit);
		menu.add(itemRegret);
		GameMode =new Menu("GameMode");
		help =new Menu ("Help");
		bar.add(GameMode);
		bar.add(help);
		setMenuBar(bar);
		Player_vs_Player = new MenuItem("Player vs Player");
		Player_vs_AI = new MenuItem("Player vs AI");
		Help = new MenuItem("Help");

		GameMode.add(Player_vs_Player);
		GameMode.add(Player_vs_AI);
		help.add(Help);

		Player_vs_Player.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				gameMode = 1;

				System.out.println("Player vs Player");
			}
		}
				);
		Player_vs_AI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				gameMode = 2;
				System.out.println("Player vs AI");
			}
		}
				);
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
				clearAvaPlaces();
				rpieceSelected = -1;
				rplacePlaced = -1;
				bpieceSelected = -1;
				bplacePlaced = -1;
				gameMode = 1;
				ifselectedAPiece = false;
				AIThinking = false;
				red=false;
				canvas.repaint();
			}
		});

		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		itemRegret.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				currentGame.regretMove();


				canvas.repaint();
				currentGame.saveGameboard(currentGame.step);
				System.out.println("regret move");

			}



		});

		// load images
		File filePath = new File("boards/main.gif");
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
		setSize(1200, 700);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == itemExit) {
			System.out.println("lala");
		}
	}

	// function that load piece images
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
		File imgAva = new File(path + "avp.gif");
		try {
			avaPlaceImg = ImageIO.read(imgAva);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkAvaPlaces(int px, int py) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				boolean flag = currentGame.checkMoveLegitimacy(Math.abs(currentGame.gameBoard[py][px]), px, py, j, i);
				if(flag && ((currentGame.gameBoard[i][j] >= 0 && currentGame.gameBoard[py][px] <= 0) || (currentGame.gameBoard[i][j] <= 0 && currentGame.gameBoard[py][px] >= 0))) {
					avaPlaces[i][j] = 1;
					canvas.repaint(j * 57 + 24, i * 57 + 24, 57, 57);
				}
			}
		}
	}
	
	private void clearAvaPlaces() {
		for(int i =0 ; i < 10; i++) {
			for(int j =0 ; j < 9; j++) {
				if(avaPlaces[i][j] == 1) {
					avaPlaces[i][j] = 0;
					canvas.repaint(j * 57 + 24, i * 57 + 24, 57, 57);
				}
			}
		}
	}

	// function that create a AI thread
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
				//				bpieceSelected = -1;
				//				bplacePlaced = -1;
				//				canvas.repaint(preBPieceSelectedx, preBPieceSelectedy, 57, 57);
				//				canvas.repaint(preBPlacePlacedx, preBPlacePlacedy, 57, 57);

				currentGame.AIMakeMove();

				bpieceSelected = currentGame.getPieceSelected();
				bplacePlaced = currentGame.getPlacePlaced();
				//				int px = bpieceSelected % 9;
				//				int py = bpieceSelected / 9;
				//				int tx = bplacePlaced % 9;
				//				int ty = bplacePlaced / 9;
				//				px = px * 57 + 24;
				//				py = py * 57 + 24;
				//				tx = tx * 57 + 24;
				//				ty = ty * 57 + 24;
				//				preBPieceSelectedx = px;
				//				preBPieceSelectedy = py;
				//				preBPlacePlacedx = tx;
				//				preBPlacePlacedy = ty;
				//				canvas.repaint(px, py, 57, 57);
				//				canvas.repaint(tx, ty, 57, 57);
				canvas.repaint();
				currentGame.step++;
				currentGame.saveGameboard(currentGame.step);
				System.out.println("step: "+currentGame.step);
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
