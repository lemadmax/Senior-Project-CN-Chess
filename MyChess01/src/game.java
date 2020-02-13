/*
 * Game class
 * 		1. Keep track on the game situation (record game board distribution)
 * 		2. Implement different game mode
 * 		3. Embed game rules
 * 		4. Implement AI algorithm
 * */

public class game {
	
	/*
	 * 1: King		Jiang
	 * 2: Guard		Shi
	 * 3: Minister	Xiang
	 * 4: Knight	Ma
	 * 5: Rook		Che
	 * 6: Cannon	Pao
	 * 7: Pawn		Bing
	 * (negative represents red side
	 * positive represents black side)
	 * */
	
	// Define minimax search depth
	// Graduately increase depth as fewer
	// pieces remain on the board
	private int MAX_ITER = 4;
	private int MAX_ITER1 = 4;
	private int MAX_ITER2 = 5;
	private int MAX_ITER3 = 6;
	private int MAX_ITER4 = 8;
	
	private int AIpx = 0; 	// AI selected piece x
	private int AIpy = 0;	// y
	private int AItx = 0;	// AI place placed x
	private int AIty = 0;	// y
	
	// the value of each piece
	// used to evaluate a situation
	// King has the greatest value, 
	// (index + 1) represent each piece, given at the beginning
	private int pieceValue[] = {
		1000, 50, 50, 55, 65, 60, 45	
	};
	// The number of a piece's move options
	private int pieceMoveOption[] = {
			4, 4, 4, 8, 34, 34, 4
	};
	
	// possible move directions and distances
	private int pieceMovex[][] = {
			{0, 0, 1, -1},
			{1, 1, -1, -1},
			{2, 2, -2, -2},
			{1, 1, 2, 2, -1, -1, -2, -2},
			{1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4, -5, -6, -7, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4, -5, -6, -7, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, -1}
	};
	private int pieceMovey[][] = {
			{1, -1, 0, 0},
			{1, -1, 1, -1},
			{2, -2, 2, -2},
			{2, -2, 1, -1, 2, -2, 1, -1},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9},
			{1, -1, 0, 0}
	};
	
	public int[][][] boardsave = new int [100][10][9];  //used to save current gameboard
	public int step  = 0;								//used to save how many steps
	
	// constructor
	public game() {
		
	}
	
	// default game board
	// the start condition of the game board
	public static final int defBoard[][] = {
			{ 5,  4,  3,  2,  1,  2,  3,  4,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  6,  0,  0,  0,  0,  0,  6,  0},
			{ 7,  0,  7,  0,  7,  0,  7,  0,  7},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-7,  0, -7,  0, -7,  0, -7,  0, -7},
			{ 0, -6,  0,  0,  0,  0,  0, -6,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-5, -4, -3, -2, -1, -2, -3, -4, -5}
	};
	public int gameBoard[][] = {
			{ 5,  4,  3,  2,  1,  2,  3,  4,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  6,  0,  0,  0,  0,  0,  6,  0},
			{ 7,  0,  7,  0,  7,  0,  7,  0,  7},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-7,  0, -7,  0, -7,  0, -7,  0, -7},
			{ 0, -6,  0,  0,  0,  0,  0, -6,  0},
			{ 0,  0,  0,  0,  0,  0,  0,  0,  0},
			{-5, -4, -3, -2, -1, -2, -3, -4, -5}
	};

	
	// red side selects a piece
	// (px, py) is the coordinate of the clicked piece
	public boolean selectPiece(int px, int py) {
		if(gameBoard[py][px] < 0) {
			return true;
		}
		else return false;
	}
	// black side selects a piece
	public boolean selectPiece2(int px, int py) {
		if(gameBoard[py][px] > 0) {
			return true;
		}else 
			return false;
	}
	
	
	
	// select pieces
	public int getPieceSelected() {
		return AIpy * 9 + AIpx;
	}
	// place pieces
	public int getPlacePlaced() {
		return AIty * 9 + AItx;
	}
	//AI makes a move
	public void AIMakeMove() {
		
		// calculate search depth
		int pieceNumber = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(gameBoard[i][j] != 0) {
					pieceNumber ++;
				}
			}
		}
		if(pieceNumber > 16) MAX_ITER = MAX_ITER1;
		else if(pieceNumber > 10) MAX_ITER = MAX_ITER2;
		else if(pieceNumber > 8) MAX_ITER = MAX_ITER3;
		else MAX_ITER = MAX_ITER4;
		
		int v = maxValue(-100000, 100000, MAX_ITER);
		
		// refresh game board
		gameBoard[AIty][AItx] = gameBoard[AIpy][AIpx];
		gameBoard[AIpy][AIpx] = 0;
		System.out.println("current: " + evaluation(gameBoard));
	}
	//determine who wins
	public int isGameOver() {
		boolean bk = false;
		boolean rk = false;
		for(int i = 0; i < 3; i++) {
			for(int j = 3; j < 6; j++) {
				if(gameBoard[i][j] == 1) {
					bk = true;
					break;
				}
			}
			if(bk) break;
		}
		for(int i = 7; i < 10; i++) {
			for(int j = 3; j < 6; j++) {
				if(gameBoard[i][j] == -1) {
					rk = true;
					break;
				}
			}
			if(rk) break;
		}
		// cannot find red king, black wins
		if(rk == false) return -1;
		// cannot find black king, red wins
		else if(bk == false) return 1;
		else return 0;
	}
	
	//minimax algorithm 
	private int maxValue(int alpha, int beta, int maxIteration) {
		
		//maxIteration is current depth, 0 represents bottom layer
		//terminal means game is over
		if(maxIteration == 0 || terminal(gameBoard)) return evaluation(gameBoard);
		
		//The optimal value of the current node
		int v = -100000;
		
		//Traverse the game board
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				//traverse all solutions of AI 
				if(gameBoard[i][j] > 0) {
					for(int e = 0; e < pieceMoveOption[gameBoard[i][j] - 1]; e++) {
						int tx = j + pieceMovex[gameBoard[i][j] - 1][e];
						int ty = i + pieceMovey[gameBoard[i][j] - 1][e];
						
						//skip the invalid move
						if(tx < 0 || tx > 8 || ty < 0 || ty > 9) continue;
						int temp = gameBoard[ty][tx];
						if(gameBoard[ty][tx] <= 0) {
							if(checkMoveLegitimacy(gameBoard[i][j], j, i, tx, ty)) {
								gameBoard[ty][tx] = gameBoard[i][j];
								gameBoard[i][j] = 0;
								
								if(isGameOver() != 0) {
									
									v = evaluation(gameBoard);
									if(maxIteration == MAX_ITER) {
										AIpx = j;
										AIpy = i;
										AItx = tx;
										AIty = ty;
									}
									gameBoard[i][j] = gameBoard[ty][tx];
									gameBoard[ty][tx] = temp;
									return v;
								}
								
								//tv: the heuristic value of the current game board returned by the next level 
								//temporary value
								int tv = minValue(alpha, beta, maxIteration - 1);
								
								gameBoard[i][j] = gameBoard[ty][tx];
								gameBoard[ty][tx] = temp;
								
								//System.out.println("tv: " + tv);
								//find the max value of the next level and assign it to v
								if(tv > v) {
									v = tv;
									if(maxIteration == MAX_ITER) {
										AIpx = j;
										AIpy = i;
										AItx = tx;
										AIty = ty;
									}
								}
								
								//alpha beta pruning when the beta of the next layer less than or equal to alpha
								if(v >= beta) return v;
								//update alpha
								if(tv > alpha) alpha = tv;
							}
						}
					}
				}
			}
		}
		return v;
	}
	
	private int minValue(int alpha, int beta, int maxIteration) {
		
		//maxIteration is current depth, 0 represents bottom layer
		//terminal means game is over
		if(maxIteration == 0 || terminal(gameBoard)) return evaluation(gameBoard);
		int v = 100000;
		//Traverse the game board
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				//traverse all solutions of red pieces
				if(gameBoard[i][j] < 0) {
					for(int e = 0; e < pieceMoveOption[-gameBoard[i][j] - 1]; e++) {
						
						int tx = j + pieceMovex[-gameBoard[i][j] - 1][e];
						int ty = i + pieceMovey[-gameBoard[i][j] - 1][e];
						
						//skip the invalid move
						if(tx < 0 || tx > 8 || ty < 0 || ty > 9) continue;
						int temp = gameBoard[ty][tx];
						if(gameBoard[ty][tx] >= 0) {
							if(checkMoveLegitimacy(-gameBoard[i][j], j, i, tx, ty)) {
								gameBoard[ty][tx] = gameBoard[i][j];
								gameBoard[i][j] = 0;
								
								if(isGameOver() != 0) {
									v = evaluation(gameBoard);
									gameBoard[i][j] = gameBoard[ty][tx];
									gameBoard[ty][tx] = temp;
									return v;
								}
								
								int tv = maxValue(alpha, beta, maxIteration - 1);
								
								gameBoard[i][j] = gameBoard[ty][tx];
								gameBoard[ty][tx] = temp;
							
								//find the min value of the next level and assign it to v
								if(tv < v) v = tv;
								//alpha beta pruning when the alpha of the next layer greater than or equal to v
								if(v <= alpha) return v;
								//update beta
								if(tv < beta) beta = tv;
							}
						}
						
					}
				}
			}
		}
		return v;
	}
	
	
	// red side move a piece
	public int playerMove(int piece, int plx, int ply) {//placed x, placed y
		//the coordinate of the piece
		int px = (piece) % 9;
		int py = (piece) / 9;
		if(gameBoard[ply][plx] >= 0) { //black 
			//check if this move is valid
			if(checkMoveLegitimacy(-gameBoard[py][px], px, py, plx, ply)) {
				gameBoard[ply][plx] = gameBoard[py][px];
				gameBoard[py][px] = 0;
				return 1;
			}
			else return 0;
		}
		else if(gameBoard[ply][plx] < 0) {//red
			return 2;//change selected piece
		}
		else return 0;
	}
	
	public int playerMove2(int piece, int plx, int ply) {
		int px = (piece) % 9;
		int py = (piece) / 9;
		if(gameBoard[ply][plx] <= 0) {
			if(checkMoveLegitimacy(gameBoard[py][px], px, py, plx, ply)) {
				gameBoard[ply][plx] = gameBoard[py][px];
				gameBoard[py][px] = 0;
				return 1;
			}
			else return 0;
		}
		else if(gameBoard[ply][plx] > 0) {
			return 2;
		}
		else return 0;
	}
	
	//save the current gameboard
		public void saveGameboard(int step) { 
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j< 9; j++) {
						boardsave[step][i][j] = gameBoard[i][j];
					}
				}
		}
		
		//regret movement for Player vs. Player
		public void regretMove() {
			for(int i = 0; i<10; i++) {
				for(int j = 0; j<9; j++) {
					
					gameBoard[i][j] = boardsave[step -2][i][j];
				}			
			}
			step = step - 2;
		}
	
	
	//determine if game is over
	private boolean terminal(int board[][]) {
		int bk = 0;
		int rk = 0;
		//traverse the game board to find black king or red king
		for(int i = 0 ; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(board[i][j] == 1) bk++;
				else if(board[i][j] == -1) rk++;
				
			}
		}
		if(bk == 1 && rk == 1) return false; //find red king or black king
		else return true;
	}
	
	// check if general aligns
	// The two Kings in the board must never be on the same file (vertical line) 
	// without any pieces in between them.
	private boolean checkIfGeneralAlign() {
		
		for(int i = 0; i < 3; i++) {
			for(int j = 3; j < 6; j++) {
				if(gameBoard[i][j] == 1) {
					for(int k = 1; k < 10; k++) {
						if(i + k > 9) {
							return false;
						}
						else if(gameBoard[i + k][j] == -1) {
							return true;
						}
						else if(gameBoard[i + k][j] != 0) {
							return false;
						}
						else {
							continue;
						}
					}
					return false;
				}
			}
		}
		
		return false;
	}
	
	// check if pieces under attack
	private boolean checkIfPieceUnderAtt(int p, int px, int py) {
		//result: Attack coefficient 
		int res = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if((gameBoard[i][j] < 0 && p < 0) || (gameBoard[i][j] > 0 && p > 0)) {
					if(checkMoveLegitimacy(Math.abs(gameBoard[i][j]), px, py, j, i)) {
						res--; //not under attack
					}
				}
				if((gameBoard[i][j] < 0 && p > 0) || (gameBoard[i][j] > 0 && p < 0)) {
					if(checkMoveLegitimacy(Math.abs(gameBoard[i][j]), px, py, j, i)) {
						res++;//under attack
					}
				}
			}
		}
		if(res > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// The situation analysis
	private int evaluation(int board[][]) {
		
		int res = 0;
		int redPower = 0;
		int blackPower = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 9; j++) {
				if(board[i][j] != 0) {
					
					int currentPiece = Math.abs(board[i][j]);
					int tempRes = 0;
					
					//
					if(currentPiece != 1) {//not king
						//not under attack, the sum of piece value
						if(!checkIfPieceUnderAtt(board[i][j], j, i)) {
							tempRes += pieceValue[currentPiece - 1];
						}
					}
					else {
						tempRes += pieceValue[currentPiece - 1];
					}
//					for(int e = 0; e < pieceMoveOption[currentPiece - 1]; e++) {
//						int tx = j + pieceMovex[currentPiece - 1][e];
//						int ty = i + pieceMovey[currentPiece - 1][e];
//						if(tx < 0 || tx > 8 || ty < 0 || ty > 9) continue;
//						if(!checkMoveLegitimacy(currentPiece, j, i, tx, ty)) continue;
//						if(board[ty][tx] == 0) 
//							tempRes += 1;
//					}
					//black					
					if(board[i][j] > 0) blackPower += tempRes;
					//red
					if(board[i][j] < 0) redPower += tempRes;
				}
			}
		}
		res = blackPower - redPower;
		return res;
	}
	
	//check if a move is legitimacy 
	public boolean checkMoveLegitimacy(int p, int px, int py, int tx, int ty) {
		int temp = gameBoard[ty][tx];
		gameBoard[ty][tx] = gameBoard[py][px];
		gameBoard[py][px] = 0;
		if(checkIfGeneralAlign()) {
			gameBoard[py][px] = gameBoard[ty][tx];
			gameBoard[ty][tx] = temp;
			return false;
		}
		gameBoard[py][px] = gameBoard[ty][tx];
		gameBoard[ty][tx] = temp;
		//System.out.println("Piece: " + p);
		switch(p) {
		//the rule of moving the King
		case 1:
			if(Math.abs(tx - px) + Math.abs(ty - py) == 1 && tx >= 3 && tx <= 5 && ((ty >= 7 && ty <= 9) || (ty >= 0 && ty <= 2))) {
				return true;
			}
			else return false;
		//the rule of moving the Guard (Advisor)
		case 2:
			if(Math.abs(tx - px) == 1 && Math.abs(ty - py) == 1 && tx >= 3 && tx <= 5 && ((ty >= 7 && ty <= 9) || (ty >= 0 && ty <= 2))) {
				return true;
			}
			else return false;
		//the rule of moving the Minister(Elephants) 
		case 3:
			if(py <= 4) {
				if(ty <= 4 && Math.abs(tx - px) == 2 && Math.abs(ty - py) == 2 && gameBoard[(ty - py) / 2 + py][(tx - px) / 2 + px] == 0) {
					return true;
				}
				else return false;
			}
			else {
				if(ty >= 5 && Math.abs(tx - px) == 2 && Math.abs(ty - py) == 2 && gameBoard[(ty - py) / 2 + py][(tx - px) / 2 + px] == 0) {
					return true;
				}
				else return false;
			}
		//the rule of moving the Knights(Horses)
		case 4:
			if(Math.abs(tx - px) == 2 && Math.abs(ty - py) == 1) {
				if(gameBoard[py][(tx - px) / 2 + px] == 0) return true;
				else return false;
			}
			if(Math.abs(tx - px) == 1 && Math.abs(ty - py) == 2) {
				if(gameBoard[(ty - py) / 2 + py][px] == 0) return true;
				else return false;
			}
			break;
		//the rule of moving the Rooks(Cars)
		case 5:
			if(tx - px == 0) { //column
				int st = Math.min(ty, py); //start
				int en = Math.max(ty, py); //end
				for(int i = st + 1; i < en; i++) {
					if(gameBoard[i][px] != 0) return false;
				}
				return true;
			}
			else if(ty - py == 0) { //row
				int st = Math.min(tx, px);
				int en = Math.max(tx, px);
				for(int i = st + 1; i < en; i++) {
					if(gameBoard[py][i] != 0) return false;
				}
				return true;
			}
		//the rule of moving the Cannon
		case 6:
			if(gameBoard[ty][tx] == 0) {
				if(tx - px == 0) {
					int st = Math.min(ty, py);
					int en = Math.max(ty, py);
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[i][px] != 0) return false;
					}
					return true;
				}
				else if(ty - py == 0) {
					int st = Math.min(tx, px);
					int en = Math.max(tx, px);
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[py][i] != 0) return false;
					}
					return true;
				}
			}
			else {
				if(tx - px == 0) {
					int st = Math.min(ty, py);
					int en = Math.max(ty, py);
					int cnt = 0;
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[i][px] != 0) cnt++;
					}
					if(cnt == 1)
						return true;
					else return false;
				}
				else if(ty - py == 0) {
					int st = Math.min(tx, px);
					int en = Math.max(tx, px);
					int cnt = 0;
					for(int i = st + 1; i < en; i++) {
						if(gameBoard[py][i] != 0) cnt++;
					}
					if(cnt == 1)
						return true;
					else return false;
				}
			}
		//the rule of moving Pawns(or Soldiers)
		case 7:
			if(gameBoard[py][px] < 0) {
				if(py >= 5) {
					if(tx == px && ty - py == -1) return true;
					else return false;
				}
				else {
					if((Math.abs(tx - px) == 1 && ty - py == 0) || (tx == px && ty - py == -1)) return true;
					else return false;
				}
			}
			else {
				if(py <= 4) {
					if(tx == px && ty - py == 1) return true;
					else return false;
				}
				else {
					if((Math.abs(tx - px) == 1 && ty - py == 0) || (tx == px && ty - py == 1)) return true;
					else return false;
				}
			}
		default:
			break;
		}
			
		return false;
	}
	
	//start function
	public void start() {
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 9; j++) {
				gameBoard[i][j] = defBoard[i][j];
			}
	}
	
}
