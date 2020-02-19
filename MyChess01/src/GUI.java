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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

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
	Menu menu,GameMode;
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
	int buttonValue =0;
	
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
	//private JTextField txtCaptureWhenA;
	private JTextArea txtrCaptureWhenA2;
	String piecePath="wood/";
	String filePath2 = "main";
    boolean tips= false;
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

		canvas.setBounds(0, 0, 558, 620);
		getContentPane().add(canvas);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(566, 0, 607, 620);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 607, 43);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox chckbxMoveTips = new JCheckBox("Move Tips");
		chckbxMoveTips.setBounds(479, 8, 99, 23);
		panel_1.add(chckbxMoveTips);
		
		canvas.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				// gameNode = 1, player vs. player
				if(gameMode == 1) {
					if(chckbxMoveTips.isSelected()) {
					    tips =true ;}
					else {
						tips=false;
					}
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
									if(tips) {
									checkAvaPlaces(tempx, tempy);}
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
									if(tips) {
									checkAvaPlaces(tempx, tempy);}
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
										JOptionPane.showMessageDialog(null, "Red has won!", "Alpha-Bob 2.7",
												JOptionPane.INFORMATION_MESSAGE);
									}
									// AIMove();
									red = true;
									ifselectedAPiece = false;

								} else if (currentGame.playerMove(rpieceSelected, tempx, tempy) == 2) {
									rpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
									clearAvaPlaces();
									if(tips) {
									checkAvaPlaces(tempx, tempy);}
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
										JOptionPane.showMessageDialog(null, "Black has won!", "Alpha-Bob 2.7",
												JOptionPane.INFORMATION_MESSAGE);
									}
									ifselectedAPiece = false;
									red = false;

								} else if (currentGame.playerMove2(bpieceSelected, tempx, tempy) == 2) {
									bpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
									clearAvaPlaces();
									if(tips) {
									checkAvaPlaces(tempx, tempy);}
								}
								canvas.repaint(sx, sy, 57, 57);
							}
						}
					}
				}
				if(gameMode == 2) {
					if(chckbxMoveTips.isSelected()) {
					    tips =true ;}
					else {
						tips=false;}
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
									if(tips) {
									checkAvaPlaces(tempx, tempy);}
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
										JOptionPane.showMessageDialog(null, "Red has won!", "Alpha-Bob 2.7",
												JOptionPane.INFORMATION_MESSAGE);
									}
									AIMove();

								} else if (currentGame.playerMove(rpieceSelected, tempx, tempy) == 2) {
									rpieceSelected = tempy * 9 + tempx;
									canvas.repaint(xx, yy, 57, 57);
									clearAvaPlaces();
								if(tips) {
										 checkAvaPlaces(tempx, tempy) ;}
									
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

		
		JLabel lblCurrentGameMode = new JLabel("Current Game Mode:");
		lblCurrentGameMode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCurrentGameMode.setBounds(10, 11, 139, 15);
		panel_1.add(lblCurrentGameMode);
		
		JLabel CurrentGameLabel = new JLabel("Player vs Player");
		CurrentGameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		CurrentGameLabel.setBounds(159, 11, 139, 15);
		panel_1.add(CurrentGameLabel);
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 43, 607, 589);
		panel.add(panel_2);
		panel_2.setLayout(null);
				
		JPanel panel_3 = new JPanel();
        panel_3.setForeground(Color.WHITE);
        panel_3.setBackground(Color.WHITE);
        panel_3.setBounds(0, -21, 607, 510);
        panel_2.add(panel_3);
		

        JLabel lblNewLabel=new JLabel();
        lblNewLabel.setIcon(new ImageIcon("help/BasicBoard.jpg"));
        lblNewLabel.setBounds(5, 28, 296, 364);
	    panel_3.add(lblNewLabel);
	    
        JLabel lblNewLabel_1 = new JLabel();
        lblNewLabel_1.setIcon(new ImageIcon("help/BasicBoard.jpg"));
        lblNewLabel_1.setBounds(301, 28, 296, 364);
        panel_3.add(lblNewLabel_1);
       
        txtrCaptureWhenA2= new JTextArea();
       // panel_3.add(txtrCaptureWhenA2);
        txtrCaptureWhenA2.setWrapStyleWord(true);
        txtrCaptureWhenA2.setLineWrap(true);
        txtrCaptureWhenA2.setRows(10);
        txtrCaptureWhenA2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
        txtrCaptureWhenA2.setEditable(false);
        txtrCaptureWhenA2.setBounds(10, 394, 587, 105);
        panel_3.add(txtrCaptureWhenA2);
        JScrollPane jsp=new JScrollPane(txtrCaptureWhenA2);        
        Dimension size=txtrCaptureWhenA2.getSize();    
        jsp.setBounds(10,394,size.width,size.height);
        panel_3.add(jsp);   
       
	
		JButton btnNewButton = new JButton("");
		//btnNewButton.setToolTipText("The Guards (Advisor) move only one space at a time diagonally. Similar to the King, the guards must stay within the palace.");

		btnNewButton.setBounds(42, 500, 52, 52);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.GRAY);
		btnNewButton.setIcon(new ImageIcon("pieces/wood/ba.gif"));
		btnNewButton.setBorderPainted(false);
		//btnNewButton.setContentAreaFilled(false);
		//btnNewButton.setBounds(87, 473, 52, 52);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/shi1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/shi2.jpg"));
				txtrCaptureWhenA2.setText("The Guards (Advisor) move only one space at a time diagonally. Similar to the King, the guards must stay within the palace.");
			}
		});
		
		panel_2.add(btnNewButton);
		
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setForeground(Color.GRAY);
		btnNewButton_2.setIcon(new ImageIcon("pieces/wood/bb.gif"));
		btnNewButton_2.setBorderPainted(false);

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/xiang1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/BasicBoard.jpg"));
				txtrCaptureWhenA2.setText("The Ministers (Elephants) move two spaces at a time diagonally (i.e. 2 spaces left/right and 2 spaces up/down in a move). They must stay within their own side of the river. If there is a piece midway between the original and final intended position of a minister, the minister is blocked and the move is not allowed.");
			}
			
		});
		btnNewButton_2.setBounds(104, 500, 52, 52);
		panel_2.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setBounds(166, 500, 52, 52);
		btnNewButton_3.setBackground(Color.WHITE);
		btnNewButton_3.setForeground(Color.GRAY);
		btnNewButton_3.setIcon(new ImageIcon("pieces/wood/bc.gif"));
		btnNewButton_3.setBorderPainted(false);

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/pao1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/BasicBoard.jpg"));
				txtrCaptureWhenA2.setText("The Cannons move one or more spaces horizontally or vertically like a Rook. However, in a capture move, there must be exactly one non-empty space in between the original and final position. In a non-capture move, all spaces in between must be empty。");
			}
			
		});
		panel_2.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setBounds(228, 500, 52, 52);
		btnNewButton_4.setBackground(Color.WHITE);
		btnNewButton_4.setForeground(Color.GRAY);
		btnNewButton_4.setIcon(new ImageIcon("pieces/wood/bk.gif"));
		btnNewButton_4.setBorderPainted(false);

		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/jiang1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/jiang2.jpg"));
				txtrCaptureWhenA2.setText("The King moves only one space at a time, either horizontally or vertically. Further more, the King must always stay within the palace, which is a square marked with an X.");
			}
			
		});
		panel_2.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setBounds(290, 500, 52, 52);
		btnNewButton_5.setBackground(Color.WHITE);
		btnNewButton_5.setForeground(Color.GRAY);
		btnNewButton_5.setIcon(new ImageIcon("pieces/wood/bn.gif"));
		btnNewButton_5.setBorderPainted(false);

		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/ma1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/BasicBoard.jpg"));
				txtrCaptureWhenA2.setText("The Knights (Horses) move two spaces horizontally and one space vertically (or respectively 2 spaces vertically and one space horizontally). If there is a piece next to the horse in the horizontal (vertical) direction, the horse is blocked and the move is not allowed.");
			}
			
		});
		panel_2.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("");
		btnNewButton_6.setBounds(352, 500, 52, 52);
		btnNewButton_6.setBackground(Color.WHITE);
		btnNewButton_6.setForeground(Color.GRAY);
		btnNewButton_6.setIcon(new ImageIcon("pieces/wood/bp.gif"));
		btnNewButton_6.setBorderPainted(false);

		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/zu1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/zu2.jpg"));
				txtrCaptureWhenA2.setText("The Pawns (or Soldiers) move one space at a time. If a pawn does not cross the river yet, it can only move forward vertically. Once crossing the river, the pawn can also move horizontally.");
			}
			
		});
		panel_2.add(btnNewButton_6);
        panel_3.setLayout(null);
          
       
		//panel_3.add(txtrCaptureWhenA);
		JButton btnBasicRule = new JButton("");
		btnBasicRule.setIcon(new ImageIcon("help/basic1.jpg"));
		btnBasicRule.setBorderPainted(false);
		btnBasicRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtrCaptureWhenA2.setText("                                                Basic Rule\r\nCapture: When a piece moves to a position currently held by an opponent's piece, it captures that opponent's piece. The captured piece is removed from the board.\r\n\r\nKing safety: One must never leave the King to be captured by the opponent in the next move. Any moves that put the King in such a setting is illegal.\r\n\r\nEnd game condition: The game ends when one of the following situations happens:\r\nCheckmate: If one threatens to capture the opponent's King and the opponent has no way to resolve the threat, one wins.\r\nStalemate: If one does not have any valid move, one loses.");
			    lblNewLabel.setIcon(new ImageIcon("help/help.jpg"));
			    lblNewLabel_1.setIcon(new ImageIcon("help/BasicBoard.jpg"));
				
				}
		});
		btnBasicRule.setBounds(476, 500, 110, 52);
		panel_2.add(btnBasicRule);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBounds(414, 500, 52, 52);
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setForeground(Color.GRAY);
		btnNewButton_1.setIcon(new ImageIcon("pieces/wood/br.gif"));
		btnNewButton_1.setBorderPainted(false);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setIcon(new ImageIcon("help/che1.jpg"));
				lblNewLabel_1.setIcon(new ImageIcon("help/BasicBoard.jpg"));
				txtrCaptureWhenA2.setText("The Rooks (Cars) move one or more spaces horizontally or vertically provided that all positions between the original and final positions are empty.");
			}
			
		});
		panel_2.add(btnNewButton_1);
		

	
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnStart.setBounds(285, 8, 89, 23);
		panel_1.add(btnStart);
		
		JButton btnRegret = new JButton("Regret");
		btnRegret.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegret.setBounds(384, 8, 89, 23);
		panel_1.add(btnRegret);
		

		
		
		
		btnStart.addActionListener(new ActionListener() {
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
				if(chckbxMoveTips.isSelected()) {
				    tips =true ;}
				else {
					tips=false;
				}
				canvas.repaint();
				CurrentGameLabel.setText("Player vs Player");
			}
		});
		
		btnRegret.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				currentGame.regretMove();


				canvas.repaint();
				currentGame.saveGameboard(currentGame.step);
				System.out.println("regret move");

			}



		});
		
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
		bar.add(GameMode);
		setMenuBar(bar);
		Player_vs_Player = new MenuItem("Player vs Player");
		Player_vs_AI = new MenuItem("Player vs AI");

		GameMode.add(Player_vs_Player);
		GameMode.add(Player_vs_AI);	
		
		Menu Customize, PieceChoice,BoardChoice;
	    MenuItem Wood,Cartoon,Delicate,Polish,WoodBoard,Elephant,Horse,BlueBoard,PinkBoard;

	        Customize=new Menu("Customize");
	        PieceChoice = new Menu("PieceChoice");
	        BoardChoice = new Menu("BoardChoice");
	        
	        Customize.add(PieceChoice);
	        Customize.add(BoardChoice);
	        bar.add(Customize);
	        
	        Wood = new MenuItem("Wood");
	        PieceChoice.add(Wood);
	        
	        Wood.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					piecePath = "wood/";
					loadPieces();
					canvas.repaint();
				}
			}
					);
	        
	        Cartoon = new MenuItem("Cartoon");
	        PieceChoice.add(Cartoon);
	        Cartoon.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					piecePath = "cartoon/";
					loadPieces();
					canvas.repaint();
				}
			}
					);
	        
	        Delicate = new MenuItem("Delicate");
	        PieceChoice.add(Delicate);
	        Delicate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					piecePath = "delicate/";
					loadPieces();
					canvas.repaint();
				}
			}
					);
	        
	        Polish = new MenuItem("Polish");
	        PieceChoice.add(Polish);
	        Polish.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					piecePath = "polish/";
					loadPieces();
					canvas.repaint();
				}
			}
					);
	        
	        WoodBoard = new MenuItem("WoodBoard");
	        BoardChoice.add(WoodBoard);
	        WoodBoard.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					filePath2 = "main";
					loadBoard();
					canvas.repaint();
				}
			}
					);
	        
	        Elephant = new MenuItem("Elephant Cartoon Board");
	        BoardChoice.add(Elephant);
	        Elephant.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					filePath2 = "main01";
					
					loadBoard();
					canvas.repaint();
				}
			}
					);
	        
	        Horse = new MenuItem("Horse Cartoon Board");
	        BoardChoice.add(Horse);
	        Horse.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					filePath2 = "main09";
					
					loadBoard();
					canvas.repaint();
				}
			}
					);
	        
	        BlueBoard = new MenuItem("BlueBoard");
	        BoardChoice.add(BlueBoard);
	        BlueBoard.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					filePath2 = "main04";
					
					loadBoard();
					canvas.repaint();
				}
			}
					);
	        
	        PinkBoard = new MenuItem("PinkBoard");
	        BoardChoice.add(PinkBoard);
	        PinkBoard.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					filePath2 = "main05";
					
					loadBoard();
					canvas.repaint();
				}
			}
					);
		
	        
		

		Player_vs_Player.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				gameMode = 1;
				CurrentGameLabel.setText("Player vs Player");
				System.out.println("Player vs Player");
			}
		}
				);
		Player_vs_AI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				gameMode = 2;
				CurrentGameLabel.setText("Player vs AI");
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
				if(chckbxMoveTips.isSelected()) {
				    tips =true ;}
				else {
					tips=false;
				}
				canvas.repaint();
				CurrentGameLabel.setText("Player vs Player");
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
		loadBoard();
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
    private void loadBoard() {
    	String filePath1 = "boards/";
    	File filePath = new File(filePath1 + filePath2 + ".gif");
    	try {
    		BoardImg = ImageIO.read(filePath);
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    }

	// function that load piece images
	private void loadPieces() {
		String path = "pieces/";
		for (int i = 1; i < 15; i++) {
			File file = new File(path +piecePath + pieces_name[i] + ".gif");
			try {
				piecesImg[i] = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File imgFile = new File(path +piecePath+ pieces_name[0] + ".gif");
		File imgFileb = new File(path +piecePath+ "boos.gif");
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
		File imgAva = new File(path+piecePath + "avp.gif");
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
					JOptionPane.showMessageDialog(null, "Black has won!", "Alpha-Bob 2.7",
							JOptionPane.INFORMATION_MESSAGE);
				}
				AIThinking = false;
			}
		}).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		new GUI("Alpha-Bob 2.7");
	}
}
